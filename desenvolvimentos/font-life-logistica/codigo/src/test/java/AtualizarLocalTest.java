import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import br.com.sankhya.bhz.central.AtualizarLocal;
import br.com.sankhya.bhz.utils.AcessoBanco;
import br.com.sankhya.extensions.actionbutton.*;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.*;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.comercial.CentralFinanceiro;

/** Teste isolado do fluxo, com APIs externas substituidas; nao comprova transacoes do servidor. */
public class AtualizarLocalTest {
    static final List<String> events = new ArrayList<String>();
    static final Map<BigDecimal, DynamicVO> notes = new HashMap<BigDecimal, DynamicVO>();
    static final Map<BigDecimal, DynamicVO> orders = new HashMap<BigDecimal, DynamicVO>();
    static final Map<DynamicVO, Map<String,Object>> values = new IdentityHashMap<DynamicVO, Map<String,Object>>();
    static final List<Timestamp> stamps = new ArrayList<Timestamp>();
    static boolean failItem;
    static BigDecimal failOrder;
    static BigDecimal targetCompany;

    @SuppressWarnings("unchecked")
    static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler);
    }
    static BigDecimal num(int n) { return BigDecimal.valueOf(n); }
    static DynamicVO vo(Object... pairs) {
        final Map<String,Object> fields = new HashMap<String,Object>();
        for (int i=0; i<pairs.length; i+=2) fields.put((String)pairs[i], pairs[i+1]);
        DynamicVO vo = proxy(DynamicVO.class, (p,m,a) -> {
            if (m.getName().equals("asBigDecimalOrZero")) {
                Object v = fields.get(a[0]); return v == null ? BigDecimal.ZERO : v;
            }
            if (m.getName().startsWith("as")) return fields.get(a[0]);
            if (m.getName().equals("toString")) return fields.toString();
            throw new UnsupportedOperationException(m.getName());
        });
        values.put(vo, fields);
        return vo;
    }
    static void reset() {
        notes.clear(); orders.clear(); values.clear(); stamps.clear(); events.clear();
        failItem = false; failOrder = null;
        targetCompany = num(2);
        CentralFinanceiro.fail = false; CentralFinanceiro.events = events; AcessoBanco.closed = 0;
        for (final String name : Arrays.asList("EmpresaFinanceiro", "CabecalhoNota", "ItemNota", "TipoOperacao", "Usuario", "OrdemCarga")) {
            JapeFactory.DAOS.put(name, proxy(JapeWrapper.class, (p,m,a) -> {
                if (m.getName().equals("findByPK")) {
                    Object key = ((Object[]) a[0])[0];
                    return name.equals("Usuario") ? vo("AD_CODLOCAL", num(300)) : notes.get(key);
                }
                if (m.getName().equals("findOne")) {
                    if (name.equals("EmpresaFinanceiro")) return vo("AD_CODLOCAL", num(100));
                    if (name.equals("TipoOperacao")) return vo("ATUALFIN", BigDecimal.ONE);
                    check(a[0].equals("CODEMP = ? AND ORDEMCARGA = ?"), "Filtro da ordem incorreto");
                    Object[] params = (Object[]) a[1];
                    check(targetCompany.equals(params[0]), "Deve buscar pela NOVA empresa");
                    return orders.get(((BigDecimal)params[1]).stripTrailingZeros());
                }
                if (m.getName().equals("find")) return Arrays.asList(vo("CODEMP", num(1)));
                if (m.getName().equals("prepareToUpdate")) {
                    final DynamicVO target = (DynamicVO) a[0];
                    final Map<String,Object> changes = new HashMap<String,Object>();
                    return proxy(FluidUpdateVO.class, (up,method,args) -> {
                        if (method.getName().equals("set")) { changes.put((String)args[0],args[1]); return up; }
                        if (method.getName().equals("update")) {
                            events.add(name);
                            if (name.equals("ItemNota") && failItem) throw new Exception("Falha em item simulada");
                            if (name.equals("OrdemCarga")) {
                                check(changes.size()==1 && changes.get("DTALTER") instanceof Timestamp, "Somente DTALTER deve mudar");
                                check(values.get(target).get("CODEMP").equals(targetCompany), "Ordem da empresa errada");
                                if (failOrder != null && failOrder.equals(values.get(target).get("ORDEMCARGA")))
                                    throw new Exception("Falha em ordem simulada");
                                stamps.add((Timestamp)changes.get("DTALTER"));
                            }
                            values.get(target).putAll(changes);
                            return null;
                        }
                        throw new UnsupportedOperationException(method.getName());
                    });
                }
                throw new UnsupportedOperationException(m.getName());
            }));
        }
    }
    static void add(int nota, int ordem) {
        notes.put(num(nota), vo("NUNOTA",num(nota), "CODEMP",num(1), "TIPMOV","P", "PENDENTE","S",
                "ORDEMCARGA",num(ordem), "CODTIPOPER",num(10), "DHTIPOPER",new Timestamp(0)));
        orders.put(num(ordem).stripTrailingZeros(), vo("CODEMP",targetCompany, "ORDEMCARGA",num(ordem)));
    }
    static void run(int... selected) throws Exception {
        final Registro[] rows = new Registro[selected.length];
        for(int i=0;i<selected.length;i++) {
            final BigDecimal id = num(selected[i]);
            rows[i] = proxy(Registro.class, (p,m,a) -> id);
        }
        ContextoAcao ctx = proxy(ContextoAcao.class, (p,m,a) -> {
            if(m.getName().equals("getParam")) return targetCompany.toPlainString();
            if(m.getName().equals("getLinhas")) return rows;
            if(m.getName().equals("setMensagemRetorno")) return null;
            throw new UnsupportedOperationException(m.getName());
        });
        new AtualizarLocal().doAction(ctx);
    }
    static void check(boolean condition, String message) { if(!condition) throw new AssertionError(message); }
    static void expectFailure(String part, int... selected) throws Exception {
        try { run(selected); throw new AssertionError("Era esperado erro: "+part); }
        catch (Exception e) { check(e.getMessage().contains(part), "Erro inesperado: "+e); }
    }
    public static void main(String[] args) throws Exception {
        reset(); add(1,10); add(2,10); add(3,20);
        long before = System.currentTimeMillis(); run(1,2,3); long after = System.currentTimeMillis();
        check(stamps.size()==2, "Deduplicacao das ordens");
        check(stamps.get(0).equals(stamps.get(1)), "Instante unico do lote");
        check(stamps.get(0).getTime()>=before && stamps.get(0).getTime()<=after, "Timestamp da execucao");
        check(events.indexOf("OrdemCarga")>events.lastIndexOf("financeiro"), "DTALTER deve ser a ultima etapa");
        check(values.get(notes.get(num(1))).get("CODEMP").equals(num(2)), "Troca de empresa preservada");
        reset(); add(1,10); CentralFinanceiro.fail=true;
        expectFailure("Falha financeira",1); check(stamps.isEmpty(),"Nao marcar erro financeiro");
        check(AcessoBanco.closed==1,"Fechar recurso em erro");
        reset(); add(1,10); failItem=true;
        expectFailure("Falha em item",1); check(stamps.isEmpty(),"Nao marcar erro em item");
        reset(); add(1,10); orders.clear();
        run(1); check(stamps.isEmpty(),"Destino ausente nao deve gerar timestamp");
        check(values.get(notes.get(num(1))).get("CODEMP").equals(targetCompany),"Destino ausente nao bloqueia troca");
        reset(); add(1,0); run(1);
        check(stamps.isEmpty() && events.contains("financeiro"),"Sem ordem deve concluir fluxo sem DTALTER");
        reset(); add(1,10); add(2,20); values.get(notes.get(num(2))).put("PENDENTE","N");
        expectFailure("não atende",1,2); check(events.isEmpty(),"Lote invalido nao deve gravar");
        reset(); run(); check(events.isEmpty(),"Selecao vazia nao deve gravar");
        reset(); add(1,10); failOrder=num(10);
        expectFailure("Falha em ordem",1); check(stamps.isEmpty(),"Propagar erro de persistencia");
        int regressionCases = 0;
        for (int destination : new int[]{1,100}) {
            for (BigDecimal orderNumber : new BigDecimal[]{null, BigDecimal.ZERO, num(10)}) {
                reset(); targetCompany = num(destination); add(1,10);
                values.get(notes.get(num(1))).put("CODEMP",num(destination==1 ? 100 : 1));
                values.get(notes.get(num(1))).put("ORDEMCARGA",orderNumber);
                run(1);
                check(values.get(notes.get(num(1))).get("CODEMP").equals(targetCompany),"Troca 1/100 deve concluir");
                check(events.contains("ItemNota") && events.contains("financeiro"),"Fluxo original deve concluir");
                check(stamps.size()==(orderNumber!=null && orderNumber.signum()>0 ? 1 : 0),"DTALTER somente com ordem");
                regressionCases++;
            }
        }
        reset(); targetCompany=num(100); add(1,0); add(2,20); run(1,2);
        check(stamps.size()==1,"Lote misto atualiza apenas a ordem existente");
        check(values.get(notes.get(num(1))).get("CODEMP").equals(num(100)),"Sem ordem deve mudar em lote misto");
        check(events.indexOf("OrdemCarga")>events.lastIndexOf("financeiro"),"Lote misto atualiza data somente ao final");
        System.out.println("PASS: " + (9+regressionCases) + " cenarios de fluxo; rollback real depende de homologacao.");
    }
}

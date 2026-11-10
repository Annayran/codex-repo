package br.com.zaya.belmicro.rateio;

import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.dominio.*;
import br.com.zaya.belmicro.rateio.infraestrutura.jdbc.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/** JDBC protocol tests with a fake driver; these do NOT execute SQL on Oracle/Sankhya. */
public final class TesteJdbc {
    private static int cenarios;
    public static void main(String[] args) {
        executar("SQL Server bloqueia origem e tabela sem sintaxe Oracle e restaura timeout",()->{
            for(OrigemCanonica.Tipo tipo:OrigemCanonica.Tipo.values()){
                BancoSqlServer b=new BancoSqlServer();BloqueioRateioJdbc.adquirir(b.conexao(),new OrigemCanonica(tipo,42));
                verdadeiro(b.sqlOrigem.contains(tipo==OrigemCanonica.Tipo.NOTA?"TGFCAB":"TGFFIN"));
                verdadeiro(b.sqlOrigem.contains("WITH (UPDLOCK, HOLDLOCK)"));igual(new BigDecimal("42"),b.parametro);
                verdadeiro(b.tabelaBloqueada);igual(Arrays.asList("SELECT @@LOCK_TIMEOUT","SET LOCK_TIMEOUT 0","ORIGEM","TABELA","SET LOCK_TIMEOUT 5000"),b.ordem);
                igual(0,b.recursos);igual(5000,b.timeout);
            }
        });
        executar("SQL Server concorrencia nas duas etapas restaura timeout",()->{
            for(boolean tabela:Arrays.asList(false,true)){
                BancoSqlServer b=new BancoSqlServer();if(tabela)b.erroTabela=1222;else b.erroOrigem=1222;
                erro("CONCORRENCIA",()->BloqueioRateioJdbc.adquirir(b.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.TITULO,42)));
                igual(5000,b.timeout);igual(0,b.recursos);
            }
        });
        executar("SQL Server origem ausente e falha de restauracao nao passam silenciosamente",()->{
            BancoSqlServer b=new BancoSqlServer();b.existe=false;b.timeout=-1;
            erro("BASE_INCOMPLETA",()->BloqueioRateioJdbc.adquirir(b.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.NOTA,42)));
            igual(-1,b.timeout);verdadeiro(!b.tabelaBloqueada);igual(0,b.recursos);
            for(int codigo:Arrays.asList(50000,1222)){
                BancoSqlServer falha=new BancoSqlServer();falha.erroOrigem=1222;falha.falharRestauracao=true;falha.erroRestauracao=codigo;
                try{BloqueioRateioJdbc.adquirir(falha.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.TITULO,42));throw new AssertionError();}
                catch(FalhaConsultaRateio e){igual("RESTAURAR_LOCK_TIMEOUT",e.getOperacao());igual("SQLSERVER",e.getBancoTipo());verdadeiro(e.getSuppressed().length==1);}
                igual(0,falha.recursos);
            }
        });
        executar("dialeto desconhecido falha fechado e SQL Server preserva motor no erro",()->{
            BancoBloqueio desconhecido=new BancoBloqueio();desconhecido.produto="Outro";
            try{BloqueioRateioJdbc.adquirir(desconhecido.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.TITULO,42));throw new AssertionError();}
            catch(FalhaConsultaRateio e){igual("DIALETO_NAO_SUPORTADO",e.getOperacao());}
            igual(null,desconhecido.sql);
            BancoSqlServer b=new BancoSqlServer();b.erroOrigem=155;
            try{BloqueioRateioJdbc.adquirir(b.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.TITULO,42));throw new AssertionError();}
            catch(FalhaConsultaRateio e){igual("SQLSERVER",e.getBancoTipo());igual(Integer.valueOf(155),e.getCodigoBanco());}
            igual(5000,b.timeout);igual(0,b.recursos);
        });
        executar("bloqueio usa chamada Oracle parametrizada sem ResultSet",()->{
            for(OrigemCanonica.Tipo tipo:OrigemCanonica.Tipo.values()){
                BancoBloqueio banco=new BancoBloqueio();BloqueioRateioJdbc.adquirir(banco.conexao(),new OrigemCanonica(tipo,42));
                verdadeiro(banco.sql.contains(tipo==OrigemCanonica.Tipo.NOTA?"TGFCAB":"TGFFIN"));
                verdadeiro(banco.sql.startsWith("DECLARE v_origem NUMBER; BEGIN SELECT "));
                verdadeiro(banco.sql.contains(" INTO v_origem FROM "));
                verdadeiro(banco.sql.endsWith(" = ? FOR UPDATE NOWAIT; END;"));igual(new BigDecimal("42"),banco.parametro);
                igual("LOCK TABLE AD_RATSIM IN EXCLUSIVE MODE NOWAIT",banco.comando);
                verdadeiro(banco.consultaFechada&&banco.executou&&banco.comandoFechado);
            }
        });
        executar("falha no bloqueio da origem preserva etapa sem executar lock de tabela",()->{
            BancoBloqueio banco=new BancoBloqueio();banco.erroOrigem=1003;
            try{BloqueioRateioJdbc.adquirir(banco.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.TITULO,42));throw new AssertionError();}
            catch(FalhaConsultaRateio e){igual("BLOQUEAR_ORIGEM",e.getOperacao());igual(Integer.valueOf(1003),e.getCodigoBanco());}
            igual(null,banco.comando);verdadeiro(banco.consultaFechada);
        });
        executar("falha no LOCK TABLE preserva codigo e fecha Statement",()->{
            BancoBloqueio banco=new BancoBloqueio();banco.erroTabela=1003;
            try{BloqueioRateioJdbc.adquirir(banco.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.NOTA,42));throw new AssertionError();}
            catch(FalhaConsultaRateio e){igual("BLOQUEAR_SIMULACOES",e.getOperacao());igual(Integer.valueOf(1003),e.getCodigoBanco());}
            verdadeiro(banco.comandoFechado&&banco.consultaFechada&&banco.executou);
        });
        executar("NOWAIT ocupado e origem ausente nao liberam simulacao",()->{
            BancoBloqueio banco=new BancoBloqueio();banco.erroTabela=54;
            erro("CONCORRENCIA",()->BloqueioRateioJdbc.adquirir(banco.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.NOTA,42)));
            BancoBloqueio ausente=new BancoBloqueio();ausente.erroOrigem=1403;
            erro("BASE_INCOMPLETA",()->BloqueioRateioJdbc.adquirir(ausente.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.NOTA,42)));
            igual(null,ausente.comando);verdadeiro(ausente.consultaFechada);
            BancoBloqueio ocupada=new BancoBloqueio();ocupada.erroOrigem=54;
            erro("CONCORRENCIA",()->BloqueioRateioJdbc.adquirir(ocupada.conexao(),new OrigemCanonica(OrigemCanonica.Tipo.NOTA,42)));
            igual(null,ocupada.comando);verdadeiro(ocupada.consultaFechada);
        });
        executar("criterios usam PK de versao e binding autoritativo", () -> {
            Banco banco = new Banco(); banco.adicionar(criterio("1","8","2","M"));
            List<CriterioDisponivel> criterios = new CriterioRepositoryJdbc(banco::conexao).aplicaveis(origem());
            igual("1",criterios.get(0).getId()); igual(8,criterios.get(0).getVersao());
            igual("Matriz (v2)",criterios.get(0).getDescricao());
            Consulta consulta=banco.consultas.get(0);
            igual(1,consulta.parametros.get(1)); igual(100L,consulta.parametros.get(2));
            igual(200L,consulta.parametros.get(3)); igual(300L,consulta.parametros.get(4));
            for(int i=5;i<=8;i++)igual(java.sql.Date.valueOf("2026-10-01"),consulta.parametros.get(i));
            verdadeiro(consulta.sql.contains("AD_RATCRI") && consulta.sql.contains("AD_RATVER"));
            fechado(banco);
        });
        executar("parceiro e TOP ausentes usam SQL NULL", () -> {
            Banco banco=new Banco(); banco.adicionar();
            DadosOrigem dados=new DadosOrigem(origem().getOrigem(),1,100,null,new BigDecimal("100"),
                    LocalDate.of(2026,10,1),"1",Collections.<String,String>emptyMap(),false);
            new CriterioRepositoryJdbc(banco::conexao).aplicaveis(dados);
            verdadeiro(banco.consultas.get(0).parametros.containsKey(3));
            igual(null,banco.consultas.get(0).parametros.get(3)); igual(null,banco.consultas.get(0).parametros.get(4));
        });
        executar("matriz usa CODCRITERIO SEQUENCIA e evidencia por destino", () -> {
            Banco banco=matriz("40","60");
            List<DestinoPeso> pesos=new CriterioRepositoryJdbc(banco::conexao).carregarPesos("1",8,origem());
            igual(2,pesos.size()); decimal("40",pesos.get(0).getPeso());
            igual("AD_RATDES:1:8:1",pesos.get(0).getEvidencia());
            igual(new BigDecimal("1"),banco.consultas.get(1).parametros.get(1));
            igual(8,banco.consultas.get(1).parametros.get(2)); fechado(banco);
        });
        executar("versoes vigentes sobrepostas rejeitam", () -> {
            Banco banco=new Banco(); banco.adicionar(criterio("1","8","2","M"),criterio("1","9","3","M"));
            erro("REGRA_CONFLITANTE",() -> new CriterioRepositoryJdbc(banco::conexao).aplicaveis(origem())); fechado(banco);
        });
        executar("criterio nao aplicavel ou numero visivel nao substituem chave", () -> {
            Banco banco=new Banco(); banco.adicionar(criterio("1","8","2","M"));
            erro("CRITERIO_NAO_SELECIONADO",() -> new CriterioRepositoryJdbc(banco::conexao).carregarPesos("1",2,origem()));
            igual(1,banco.consultas.size()); fechado(banco);
        });
        executar("analitico sem fonte nao usa matriz", () -> {
            Banco banco=new Banco(); banco.adicionar(criterio("1","8","2","A"));
            erro("BASE_INCOMPLETA",() -> new CriterioRepositoryJdbc(banco::conexao).carregarPesos("1",8,origem()));
            igual(1,banco.consultas.size());
        });
        executar("matriz com soma diferente de 100 rejeita", () -> {
            Banco banco=matriz("30","60");
            erro("COMPOSICAO_INVALIDA",() -> new CriterioRepositoryJdbc(banco::conexao).carregarPesos("1",8,origem())); fechado(banco);
        });
        executar("matriz nao aceita percentual zero nem precisao excedente", () -> {
            Banco banco=matriz("0","100");
            erro("COMPOSICAO_INVALIDA",() -> new CriterioRepositoryJdbc(banco::conexao).carregarPesos("1",8,origem()));
            Banco outro=matriz("0.0000001","99.9999999");
            erro("COMPOSICAO_INVALIDA",() -> new CriterioRepositoryJdbc(outro::conexao).carregarPesos("1",8,origem()));
        });
        executar("identificador fora de intervalo nao sofre truncamento", () -> {
            Banco banco=new Banco(); banco.adicionar(criterio("1","2147483648","2","M"));
            erro("CADASTRO_INVALIDO",() -> new CriterioRepositoryJdbc(banco::conexao).aplicaveis(origem())); fechado(banco);
        });
        executar("sem competencia nao consulta banco", () -> {
            Banco banco=new Banco();
            DadosOrigem dados=new DadosOrigem(origem().getOrigem(),1,100,null,new BigDecimal("100"),null,"1",Collections.<String,String>emptyMap(),false);
            erro("BASE_INCOMPLETA",() -> new CriterioRepositoryJdbc(banco::conexao).aplicaveis(dados));
            igual(0,banco.consultas.size());
        });
        executar("marcador consulta AD_RATFIN por NUFIN", () -> {
            Banco banco=new Banco(); banco.adicionar(linha("NUFIN",99));
            verdadeiro(new MarcadorAuxiliarRepositoryJdbc(banco::conexao).marcado(99));
            igual(99L,banco.consultas.get(0).parametros.get(1)); fechado(banco);
            Banco vazio=new Banco(); vazio.adicionar();
            igual(false,new MarcadorAuxiliarRepositoryJdbc(vazio::conexao).marcado(99)); fechado(vazio);
        });
        executar("execucao separa identidade de nota e titulo direto", () -> {
            Banco banco=new Banco(); banco.adicionar(linha("SEQUENCIA",1)); banco.adicionar();
            ExecucaoRepositoryJdbc repo=new ExecucaoRepositoryJdbc(banco::conexao);
            verdadeiro(repo.concluidaDe(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,9)));
            igual(false,repo.concluidaDe(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,9)));
            verdadeiro(banco.consultas.get(0).sql.contains("S.NUNOTA = ?"));
            verdadeiro(banco.consultas.get(1).sql.contains("S.NUNOTA IS NULL")); fechado(banco);
        });
        executar("falha SQL nao expoe mensagem privada e fecha statement", () -> {
            Banco banco=new Banco(); banco.adicionar(); banco.falhar=true;
            try { new CriterioRepositoryJdbc(banco::conexao).aplicaveis(origem()); throw new AssertionError("Falha esperada"); }
            catch(FalhaConsultaRateio e) {
                igual("CRITERIOS_APLICAVEIS",e.getOperacao());
                verdadeiro(!e.getMessage().contains("privado")); igual(null,e.getCause());
            }
            verdadeiro(banco.consultas.get(0).statementFechado); igual(false,banco.conexaoFechada);
        });
        executar("TOP material muda hash mantendo valores", () -> {
            EntradaSimulacao entrada=EntradaSimulacao.manual(Arrays.asList(new EntradaSimulacao.Percentual(1,10,new BigDecimal("100"))));
            ResultadoCalculo resultado=new CalculadoraRateio().calcular(new BigDecimal("100"),entrada.destinosManuais(),1);
            DadosOrigem a=origem();
            DadosOrigem b=new DadosOrigem(a.getOrigem(),1,100,200L,301L,new BigDecimal("100"),LocalDate.of(2026,10,1),"1",Collections.<String,String>emptyMap(),false);
            verdadeiro(!HashComposicao.calcular(a.contextoHash(entrada,resultado)).equals(HashComposicao.calcular(b.contextoHash(entrada,resultado))));
        });
        executar("origem por nota preserva cabecalho nativo e mapeamento aprovado",()->{
            Banco banco=new Banco();banco.adicionar(cabecalho(9));
            DadosOrigem dados=repoOrigem(banco).resolver(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,9));
            igual(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,9),dados.getOrigem());igual(1,dados.getCodEmp());
            igual(100L,dados.getCodNat());igual(200L,dados.getCodParc());igual(300L,dados.getCodTipOper());
            decimal("1000",dados.getBase());igual(LocalDate.of(2026,10,1),dados.getCompetencia());fechado(banco);
        });
        executar("parcelas financeiras convergem para nota sem usar valores parciais",()->{
            Banco banco=new Banco();
            for(int fin:Arrays.asList(11,12)){banco.adicionar();banco.adicionar(linha("NUFIN",fin,"NUNOTA",9));banco.adicionar(cabecalho(9));}
            OrigemRepositoryJdbc repo=repoOrigem(banco);
            DadosOrigem um=repo.resolver(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11));
            DadosOrigem dois=repo.resolver(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,12));
            igual(um.getOrigem(),dois.getOrigem());igual(OrigemCanonica.Tipo.NOTA,um.getOrigem().getTipo());
            igual(9L,banco.consultas.get(2).parametros.get(1));igual(6,banco.consultas.size());fechado(banco);
        });
        executar("auxiliar financeiro bloqueia antes de ler nota e calcular base",()->{
            Banco banco=new Banco();banco.adicionar(linha("NUFIN",11));
            erro("ORIGEM_AUXILIAR",()->repoOrigem(banco).resolver(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11)));
            igual(1,banco.consultas.size());fechado(banco);
        });
        executar("financeiro sem nota ou NUNOTA zero mantem origem de titulo",()->{
            for(Object nunota:Arrays.asList(null,0)){
                Banco banco=new Banco();banco.adicionar();banco.adicionar(linha("NUFIN",11,"NUNOTA",nunota,"CODEMP",1,"CODNAT",100,"CODPARC",null,"CODTIPOPER",300));
                DadosOrigem dados=repoOrigem(banco).resolver(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11));
                igual(OrigemCanonica.Tipo.TITULO,dados.getOrigem().getTipo());igual(11L,dados.getOrigem().getId());
                igual(null,dados.getCodParc());igual(2,banco.consultas.size());fechado(banco);
            }
        });
        executar("origem inexistente nao inventa dados",()->{
            Banco banco=new Banco();banco.adicionar();
            erro("BASE_INCOMPLETA",()->repoOrigem(banco).resolver(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,9)));fechado(banco);
            Banco financeiro=new Banco();financeiro.adicionar();financeiro.adicionar();
            erro("BASE_INCOMPLETA",()->repoOrigem(financeiro).resolver(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11)));fechado(financeiro);
        });
        executar("identidade nativa divergente ou duplicada e rejeitada",()->{
            Banco banco=new Banco();banco.adicionar(cabecalho(10));
            erro("CADASTRO_INVALIDO",()->repoOrigem(banco).resolver(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,9)));fechado(banco);
            Banco duplicado=new Banco();duplicado.adicionar(cabecalho(9),cabecalho(9));
            erro("REGRA_CONFLITANTE",()->repoOrigem(duplicado).resolver(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,9)));fechado(duplicado);
        });
        executar("mapeamento ausente conserva base e competencia vazias",()->{
            Banco banco=new Banco();banco.adicionar(cabecalho(9));
            DadosOrigem d=new OrigemRepositoryJdbc(banco::conexao,(doc,c)->null).resolver(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,9));
            igual(null,d.getBase());igual(null,d.getCompetencia());igual("NAO_HOMOLOGADO",d.getVersaoAlgoritmoMapeamento());fechado(banco);
        });
        executar("cadastros sao consultados por conjuntos sem duplicar consultas",()->{
            Banco banco=new Banco();banco.adicionar(linha("CODEMP",1),linha("CODEMP",2));
            banco.adicionar(linha("CODCENCUS",10),linha("CODCENCUS",20));
            List<DestinoPeso> destinos=Arrays.asList(new DestinoPeso(2,20,"1",BigDecimal.ONE,"a"),new DestinoPeso(2,20,"2",BigDecimal.ONE,"b"),new DestinoPeso(1,10,"1",BigDecimal.ONE,"c"));
            repoOrigem(banco).validarDestinos(origem(),destinos);igual(2,banco.consultas.size());
            igual(2,banco.consultas.get(0).parametros.size());igual(2,banco.consultas.get(1).parametros.size());
            verdadeiro(banco.consultas.get(1).sql.contains("ATIVO = 'S'"));fechado(banco);
        });
        executar("CR ausente ou inativo retorna pendencia sem omitir destino",()->{
            Banco banco=new Banco();banco.adicionar(linha("CODEMP",1),linha("CODEMP",2));banco.adicionar();
            erro("CADASTRO_INVALIDO",()->repoOrigem(banco).validarDestinos(origem(),Arrays.asList(new DestinoPeso(2,20,"1",BigDecimal.ONE,"ref"))));fechado(banco);
        });
        executar("validacao divide 501 codigos em lotes abaixo do limite Oracle",()->{
            Banco banco=new Banco();List<DestinoPeso> destinos=new ArrayList<DestinoPeso>();
            List<Map<String,Object>> empresas=new ArrayList<Map<String,Object>>(),crs=new ArrayList<Map<String,Object>>();
            for(int codigo=1;codigo<=501;codigo++){
                destinos.add(new DestinoPeso(codigo,codigo,"1",BigDecimal.ONE,"ref"));empresas.add(linha("CODEMP",codigo));crs.add(linha("CODCENCUS",codigo));
            }
            adicionarLista(banco,empresas.subList(0,500));adicionarLista(banco,empresas.subList(500,501));
            adicionarLista(banco,crs.subList(0,500));adicionarLista(banco,crs.subList(500,501));
            repoOrigem(banco).validarDestinos(origem(),destinos);igual(4,banco.consultas.size());
            for(Consulta consulta:banco.consultas)verdadeiro(consulta.parametros.size()<=500);fechado(banco);
        });
        executar("modal le financeiro selecionado com binding e sem confundir base",()->{
            Banco b=new Banco();b.adicionar(linha("NUFIN","123.00","NUNOTA",456,"NUMNOTA",789,"CODEMP",1,"NOMEFANTASIA","Empresa real","CODNAT",100,"CODPARC",200,"NOMEPARC","Fornecedor real","CODTIPOPER",300,"VLRDESDOB","250.00","DTVENC","2026-11-01"));
            Map<String,Object> f=new ConsultaModalRateioJdbc(b::conexao).financeiro(123);
            igual("123",f.get("NUFIN"));igual("250.00",f.get("VLRDESDOB"));igual("2026-11-01",f.get("DTVENC"));
            verdadeiro(!f.containsKey("base"));igual(123L,b.consultas.get(0).parametros.get(1));fechado(b);
        });
        executar("catalogos usam TSIEMP e TSICUS ativos sem conexao nova",()->{
            Banco b=new Banco();b.adicionar(linha("CODIGO",1,"NOME","Empresa nativa"));b.adicionar(linha("CODIGO",10,"NOME","Centro nativo"));
            Map<String,Object> c=new ConsultaModalRateioJdbc(b::conexao).catalogos();
            verdadeiro(c.get("empresas").toString().contains("Empresa nativa"));verdadeiro(c.get("centros").toString().contains("Centro nativo"));
            verdadeiro(b.consultas.get(0).sql.contains("TSIEMP"));verdadeiro(b.consultas.get(1).sql.contains("TSICUS")&&b.consultas.get(1).sql.contains("ATIVO='S'"));
            igual(Boolean.FALSE,c.get("limiteAtingido"));fechado(b);
        });
        executar("modal rejeita financeiro divergente e identificador invalido",()->{
            Banco b=new Banco();b.adicionar(linha("NUFIN",999));erro("CADASTRO_INVALIDO",()->new ConsultaModalRateioJdbc(b::conexao).financeiro(123));fechado(b);
            Banco vazio=new Banco();erro("BASE_INCOMPLETA",()->new ConsultaModalRateioJdbc(vazio::conexao).financeiro(0));igual(0,vazio.consultas.size());
        });
        executar("falha de catalogo e sanitizada",()->{
            Banco b=new Banco();b.adicionar();b.falhar=true;
            try{new ConsultaModalRateioJdbc(b::conexao).catalogos();throw new AssertionError();}
            catch(FalhaConsultaRateio e){igual("CATALOGOS_MODAL",e.getOperacao());verdadeiro(!e.getMessage().contains("privado"));}
            verdadeiro(b.consultas.get(0).statementFechado);verdadeiro(!b.conexaoFechada);
        });
        executar("resumo soma tres parcelas da nota incluindo baixadas sem agrupar NUMNOTA",()->{
            Banco b=new Banco();b.adicionar(parcela(11,"2026-10-15"),parcela(12,"2026-11-15"),parcela(13,"2026-12-15"));
            ResumoFinanceiroJdbc.Resumo r=new ResumoFinanceiroJdbc(b::conexao).carregar(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,456));
            igual(new BigDecimal("300.00"),r.getTotal());igual(3,r.getParcelas().size());igual(456L,b.consultas.get(0).parametros.get(1));
            String sql=b.consultas.get(0).sql;verdadeiro(sql.contains("F.NUNOTA=?"));verdadeiro(!sql.contains("NUMNOTA")&&!sql.contains("DHBAIXA"));verdadeiro(sql.contains("F.RECDESP=-1")&&sql.contains("PROVISAO")&&sql.contains("AD_RATFIN"));fechado(b);
        });
        executar("titulo sem nota representa uma parcela sem inferir grupo por numero",()->{
            Banco b=new Banco();b.adicionar(parcela(11,"2026-10-15"));
            ResumoFinanceiroJdbc.Resumo r=new ResumoFinanceiroJdbc(b::conexao).carregar(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11));
            igual(1,r.getParcelas().size());igual(new BigDecimal("100.00"),r.getTotal());verdadeiro(b.consultas.get(0).sql.contains("F.NUFIN=? AND (F.NUNOTA IS NULL OR F.NUNOTA=0)"));fechado(b);
        });
        executar("mapeamento usa total da nota e vencimento da parcela selecionada",()->{
            Banco b=new Banco();b.adicionar(referencia(12,"2026-11-15",-1,"N",0));b.adicionar(parcela(11,"2026-10-15"),parcela(12,"2026-11-15"),parcela(13,"2026-12-15"));
            DocumentoOrigem doc=docNota();
            MapeamentoOrigem.BaseCompetencia r=new MapeamentoFinanceiroTgffin().calcular(doc,b::conexao,new OrigemCanonica(OrigemCanonica.Tipo.TITULO,12));
            igual(new BigDecimal("300.00"),r.getBase());igual(LocalDate.of(2026,11,15),r.getCompetencia());igual("12",r.getCamposMateriais().get("NUFIN_REFERENCIA"));igual("3",r.getCamposMateriais().get("QTD_PARCELAS"));fechado(b);
        });
        executar("parcela fora do grupo conserva vencimento sem inventar base",()->{
            Banco b=new Banco();b.adicionar(referencia(999,"2026-10-15",-1,"N",0));b.adicionar(parcela(11,"2026-10-15"));
            MapeamentoOrigem.BaseCompetencia r=new MapeamentoFinanceiroTgffin().calcular(docNota(),b::conexao,new OrigemCanonica(OrigemCanonica.Tipo.TITULO,999));
            igual(null,r.getBase());igual(LocalDate.of(2026,10,15),r.getCompetencia());igual("REFERENCIA_FORA_DO_GRUPO",r.getCamposMateriais().get("MOTIVO_PENDENCIA_FINANCEIRO"));fechado(b);
        });
        executar("sem DTVENC nao inventa competencia",()->{
            Banco b=new Banco();b.adicionar(referencia(11,null,-1,"N",0));
            MapeamentoOrigem.BaseCompetencia r=new MapeamentoFinanceiroTgffin().calcular(docNota(),b::conexao,new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11));
            igual(null,r.getCompetencia());igual(null,r.getBase());igual("VENCIMENTO_SELECIONADO_AUSENTE",r.getCamposMateriais().get("MOTIVO_PENDENCIA_FINANCEIRO"));fechado(b);
        });
        executar("base sem parcelas conserva DTVENC e motivo especifico",()->{
            Banco b=new Banco();b.adicionar(referencia(11,"2026-02-02",-1,"N",0));b.adicionar();
            MapeamentoOrigem.BaseCompetencia r=new MapeamentoFinanceiroTgffin().calcular(docNota(),b::conexao,new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11));
            igual(LocalDate.of(2026,2,2),r.getCompetencia());igual(null,r.getBase());igual("SEM_PARCELAS_ELEGIVEIS",r.getCamposMateriais().get("MOTIVO_PENDENCIA_FINANCEIRO"));fechado(b);
        });
        executar("tipo provisao e renegociacao sao distintos e nao apagam competencia",()->{
            Object[][] casos={{1,"N",0,"TIPO_NAO_DESPESA"},{-1,"S",0,"TITULO_PROVISIONADO"},{-1,"N",999,"DOCUMENTO_RENEGOCIADO"}};
            for(Object[] caso:casos){
                Banco b=new Banco();b.adicionar(referencia(11,"2026-02-02",(Integer)caso[0],(String)caso[1],(Integer)caso[2]));
                MapeamentoOrigem.BaseCompetencia r=new MapeamentoFinanceiroTgffin().calcular(docNota(),b::conexao,new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11));
                igual(LocalDate.of(2026,2,2),r.getCompetencia());igual(null,r.getBase());igual(caso[3],r.getCamposMateriais().get("MOTIVO_PENDENCIA_FINANCEIRO"));igual(1,b.consultas.size());fechado(b);
            }
        });
        executar("referencia fora da origem ou vencimento alterado nao libera base",()->{
            Banco b=new Banco();Map<String,Object> outra=referencia(11,"2026-02-02",-1,"N",0);outra.put("NUNOTA",999);b.adicionar(outra);
            erro("SIMULACAO_DESATUALIZADA",()->new MapeamentoFinanceiroTgffin().calcular(docNota(),b::conexao,new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11)));fechado(b);
            Banco c=new Banco();c.adicionar(referencia(11,"2026-02-02",-1,"N",0));c.adicionar(parcela(11,"2026-03-02"));
            MapeamentoOrigem.BaseCompetencia r=new MapeamentoFinanceiroTgffin().calcular(docNota(),c::conexao,new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11));
            igual(null,r.getBase());igual("VENCIMENTO_ALTERADO_NA_CONSULTA",r.getCamposMateriais().get("MOTIVO_PENDENCIA_FINANCEIRO"));fechado(c);
        });
        executar("renegociacao bloqueia total ambiguo e falha tecnica nao vira base",()->{
            Banco b=new Banco();Map<String,Object> linha=parcela(11,"2026-10-15");linha.put("NURENEG",999);b.adicionar(linha);
            erro("BASE_INCOMPLETA",()->new ResumoFinanceiroJdbc(b::conexao).carregar(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11)));fechado(b);
            Banco c=new Banco();c.adicionar();c.falhar=true;
            try{new MapeamentoFinanceiroTgffin().calcular(new DocumentoOrigem(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11),1,100,200L,300L),c::conexao);throw new AssertionError();}
            catch(FalhaConsultaRateio e){igual("REFERENCIA_FINANCEIRA",e.getOperacao());}
            verdadeiro(c.consultas.get(0).statementFechado);verdadeiro(!c.conexaoFechada);
        });
        System.out.println("PASS: " + cenarios + " cenarios JDBC (driver simulado, sem banco real).");
    }

    private static DocumentoOrigem docNota(){return new DocumentoOrigem(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,456),1,100,200L,300L);}
    private static Map<String,Object> referencia(long id,String venc,int tipo,String provisao,int reneg){return linha("NUFIN",id,"NUNOTA",456,"DTVENC",venc,"RECDESP",tipo,"PROVISAO",provisao,"NURENEG",reneg);}
    private static Map<String,Object> parcela(long id,String venc){return linha("NUFIN",id,"VLRDESDOB","100.00","DTVENC",venc,"DESDOBRAMENTO",String.valueOf(id),"NURENEG",0);}
    private static DadosOrigem origem() {
        return new DadosOrigem(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,1),1,100,200L,300L,
                new BigDecimal("100"),LocalDate.of(2026,10,1),"1",Collections.<String,String>emptyMap(),false);
    }
    private static OrigemRepositoryJdbc repoOrigem(Banco banco){return new OrigemRepositoryJdbc(banco::conexao,(doc,c)->
            new MapeamentoOrigem.BaseCompetencia(new BigDecimal("1000"),LocalDate.of(2026,10,1),"aprovado-teste",Collections.<String,String>emptyMap()));}
    private static Map<String,Object> cabecalho(int nota){return linha("NUNOTA",nota,"CODEMP",1,"CODNAT",100,"CODPARC",200,"CODTIPOPER",300);}
    @SuppressWarnings({"unchecked","rawtypes"}) private static void adicionarLista(Banco banco,List<Map<String,Object>> linhas){banco.adicionar(linhas.toArray(new Map[0]));}
    private static Map<String,Object> criterio(String cod,String seq,String numero,String modalidade) {
        return linha("CODCRITERIO",cod,"SEQUENCIA",seq,"NROVERSAO",numero,"MODALIDADE",modalidade,"DESCRICAO","Matriz");
    }
    private static Banco matriz(String um,String dois) {
        Banco banco=new Banco(); banco.adicionar(criterio("1","8","2","M"));
        banco.adicionar(linha("SEQDESTINO",1,"CODEMP",1,"CODCENCUS",10,"PERCGLOBAL",um),
                linha("SEQDESTINO",2,"CODEMP",2,"CODCENCUS",20,"PERCGLOBAL",dois)); return banco;
    }
    private static Map<String,Object> linha(Object... campos) {
        Map<String,Object> r=new HashMap<String,Object>(); for(int i=0;i<campos.length;i+=2)r.put((String)campos[i],campos[i+1]); return r;
    }
    private static void fechado(Banco banco) {
        igual(false,banco.conexaoFechada);
        for(Consulta consulta:banco.consultas){verdadeiro(consulta.statementFechado);verdadeiro(consulta.resultadoFechado);}
    }
    private static void executar(String nome,Runnable acao) { try{acao.run();cenarios++;System.out.println("OK "+nome);}catch(Throwable e){throw new AssertionError(nome,e);} }
    private static void verdadeiro(boolean valor){if(!valor)throw new AssertionError("Condicao nao atendida");}
    private static void igual(Object a,Object b){if(!Objects.equals(a,b))throw new AssertionError(a+" != "+b);}
    private static void decimal(String a,BigDecimal b){verdadeiro(new BigDecimal(a).compareTo(b)==0);}
    private static void erro(String codigo,Runnable acao){try{acao.run();throw new AssertionError("Erro esperado");}catch(ErroRateio e){igual(codigo,e.getCodigo());}}

    private static final class Banco {
        final Queue<List<Map<String,Object>>> respostas=new ArrayDeque<List<Map<String,Object>>>();
        final List<Consulta> consultas=new ArrayList<Consulta>();
        boolean conexaoFechada, falhar;
        @SafeVarargs final void adicionar(Map<String,Object>... linhas){respostas.add(Arrays.asList(linhas));}
        Connection conexao(){return proxy(Connection.class,(objeto,metodo,args)->{
            if("prepareStatement".equals(metodo.getName())){
                String sql=(String)args[0];
                if(!sql.trim().startsWith("SELECT"))throw new AssertionError("Somente leitura");
                List<Map<String,Object>> resposta=respostas.poll();
                if(resposta==null)throw new AssertionError("Consulta inesperada");
                Consulta consulta=new Consulta(sql,resposta,this);consultas.add(consulta);return consulta.statement();
            }
            if("close".equals(metodo.getName())){conexaoFechada=true;return null;}
            throw new UnsupportedOperationException(metodo.getName());
        });}
    }
    private static final class Consulta {
        final String sql;
        final List<Map<String,Object>> linhas;
        final Map<Integer,Object> parametros=new HashMap<Integer,Object>();
        final Banco banco;
        boolean statementFechado,resultadoFechado;
        int indice=-1;
        Consulta(String sql,List<Map<String,Object>> linhas,Banco banco){this.sql=sql;this.linhas=linhas;this.banco=banco;}
        PreparedStatement statement(){return proxy(PreparedStatement.class,(objeto,metodo,args)->{
            String nome=metodo.getName();
            if(nome.startsWith("set")){parametros.put((Integer)args[0],"setNull".equals(nome)?null:args[1]);return null;}
            if("close".equals(nome)){statementFechado=true;return null;}
            if("executeQuery".equals(nome)){
                if(banco.falhar)throw new SQLException("conteudo privado do driver");
                return proxy(ResultSet.class,(r,m,a)->{
                    if("next".equals(m.getName()))return ++indice<linhas.size();
                    if("close".equals(m.getName())){resultadoFechado=true;return null;}
                    Object valor=linhas.get(indice).get((String)a[0]);
                    if("getBigDecimal".equals(m.getName()))return valor==null?null:new BigDecimal(valor.toString());
                    if("getString".equals(m.getName()))return valor==null?null:valor.toString();
                    if("getDate".equals(m.getName()))return valor==null?null:java.sql.Date.valueOf(valor.toString());
                    throw new UnsupportedOperationException(m.getName());
                });
            }
            throw new UnsupportedOperationException(nome);
        });}
    }
    private static final class BancoSqlServer {
        final List<String> ordem=new ArrayList<String>();String sqlOrigem;Object parametro;
        int timeout=5000,recursos,erroOrigem,erroTabela,erroRestauracao=50000;boolean existe=true,tabelaBloqueada,falharRestauracao;
        Connection conexao(){return proxy(Connection.class,(o,m,a)->{
            if("getMetaData".equals(m.getName()))return proxy(DatabaseMetaData.class,(d,f,v)->{
                if("getDatabaseProductName".equals(f.getName()))return "Microsoft SQL Server";throw new AssertionError(f.getName());
            });
            if("prepareStatement".equals(m.getName())){
                sqlOrigem=(String)a[0];verdadeiro(!sqlOrigem.contains("FOR UPDATE"));recursos++;
                return proxy(PreparedStatement.class,(p,f,v)->{
                    if("close".equals(f.getName())){recursos--;return null;}
                    if("setBigDecimal".equals(f.getName())){igual(1,v[0]);parametro=v[1];return null;}
                    if("executeQuery".equals(f.getName())){ordem.add("ORIGEM");if(erroOrigem!=0)throw new SQLException("privado","",erroOrigem);return resultado(existe,0);}
                    throw new AssertionError(f.getName());
                });
            }
            if("createStatement".equals(m.getName())){
                recursos++;return proxy(Statement.class,(s,f,v)->{
                    if("close".equals(f.getName())){recursos--;return null;}
                    String sql=(String)v[0];
                    if("execute".equals(f.getName())){
                        ordem.add(sql);verdadeiro(sql.startsWith("SET LOCK_TIMEOUT "));
                        int novo=Integer.parseInt(sql.substring("SET LOCK_TIMEOUT ".length()));
                        if(novo!=0&&falharRestauracao)throw new SQLException("privado","",erroRestauracao);
                        timeout=novo;return false;
                    }
                    if("executeQuery".equals(f.getName())){
                        if(sql.equals("SELECT @@LOCK_TIMEOUT")){ordem.add(sql);return resultado(true,timeout);}
                        igual("SELECT TOP (1) NUSIMULACAO FROM AD_RATSIM WITH (TABLOCKX, HOLDLOCK)",sql);ordem.add("TABELA");
                        if(erroTabela!=0)throw new SQLException("privado","",erroTabela);
                        tabelaBloqueada=true;return resultado(false,0);
                    }
                    throw new AssertionError(f.getName());
                });
            }
            throw new AssertionError(m.getName());
        });}
        ResultSet resultado(boolean existe,int valor){recursos++;return proxy(ResultSet.class,(r,m,a)->{
            if("next".equals(m.getName()))return existe;
            if("close".equals(m.getName())){recursos--;return null;}
            if("getInt".equals(m.getName()))return valor;
            if("wasNull".equals(m.getName()))return false;
            throw new AssertionError(m.getName());
        });}
    }
    private static final class BancoBloqueio {
        String produto="Oracle";
        String sql,comando;Object parametro;int erroOrigem,erroTabela;
        boolean executou,consultaFechada,comandoFechado;
        Connection conexao(){return proxy(Connection.class,(o,m,a)->{
            if("getMetaData".equals(m.getName()))return proxy(DatabaseMetaData.class,(d,f,v)->{
                if("getDatabaseProductName".equals(f.getName()))return produto;throw new AssertionError(f.getName());
            });
            if("prepareCall".equals(m.getName())){
                sql=(String)a[0];return proxy(CallableStatement.class,(p,f,v)->{
                    if("setBigDecimal".equals(f.getName())){igual(1,v[0]);parametro=v[1];return null;}
                    if("close".equals(f.getName())){consultaFechada=true;return null;}
                    if("execute".equals(f.getName())){
                        executou=true;
                        if(erroOrigem!=0)throw new SQLException("privado","",erroOrigem);
                        return false;
                    }
                    throw new AssertionError(f.getName());
                });
            }
            if("createStatement".equals(m.getName()))return proxy(Statement.class,(s,f,v)->{
                if("close".equals(f.getName())){comandoFechado=true;return null;}
                if("executeUpdate".equals(f.getName())){comando=(String)v[0];if(erroTabela!=0)throw new SQLException("privado","",erroTabela);return 0;}
                throw new AssertionError(f.getName());
            });
            // Closing the borrowed connection or managing commit/rollback is a regression.
            throw new AssertionError(m.getName());
        });}
    }
    @SuppressWarnings("unchecked") private static <T> T proxy(Class<T> tipo,InvocationHandler handler){
        return (T)Proxy.newProxyInstance(TesteJdbc.class.getClassLoader(),new Class<?>[]{tipo},handler);
    }
}

package br.com.zaya.belmicro.rateio.aplicacao;

import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.dominio.*;
import java.math.BigDecimal;
import java.util.*;

/** Explicit wire projection: decimals are strings; internal evidence/material mappings are omitted. */
public final class DtoModalRateio {
    private DtoModalRateio(){}
    public static Map<String,Object> contexto(ContextoModal c){
        List<Object> criterios=new ArrayList<Object>();for(CriterioDisponivel cr:c.getCriterios())
            criterios.add(mapa("id",cr.getId(),"versao",cr.getVersao(),"descricao",cr.getDescricao()));
        DadosOrigem d=c.getOrigem();return mapa("origem",origem(d.getOrigem()),"empresaOrigem",d.getCodEmp(),
                "natureza",String.valueOf(d.getCodNat()),"parceiro",d.getCodParc()==null?null:d.getCodParc().toString(),
                "competencia",d.getCompetencia()==null?null:d.getCompetencia().toString(),"base",decimal(d.getBase()),
                "motivoBase",d.getCamposMateriais().get("MOTIVO_PENDENCIA_FINANCEIRO"),
                "criterios",Collections.unmodifiableList(criterios),"simulacao",c.getSimulacao()==null?null:simulacao(c.getSimulacao()),
                "efetivada",c.isEfetivada(),"podeSimular",c.isPodeSimular(),"podeEfetivar",false,"pendencias",c.getPendencias());
    }
    public static Map<String,Object> simulacao(SimulacaoRateio s){
        List<Object> parcelas=new ArrayList<Object>(),grupos=new ArrayList<Object>();ResultadoCalculo r=s.getResultado();
        if(r!=null){for(ParcelaSimulacao p:r.getParcelas())parcelas.add(mapa("codEmp",p.getCodEmp(),"codCencus",p.getCodCencus(),
                "componente",p.getComponente(),"percentualGlobal",decimal(p.getParticipacaoGlobal().multiply(new BigDecimal("100"))),
                "valor",decimal(p.getValor()),"origem",p.isOrigem()));
            for(GrupoPrevisto g:r.getGrupos()){
                List<Object> linhas=new ArrayList<Object>();for(LinhaRateio l:g.getLinhasPorCr())linhas.add(mapa("codCencus",l.getCodCencus(),
                        "valor",decimal(l.getValor()),"percentual",decimal(l.getPercentual())));
                grupos.add(mapa("codEmp",g.getCodEmp(),"valor",decimal(g.getValorTotal()),"linhas",Collections.unmodifiableList(linhas)));
            }
        }
        return mapa("id",String.valueOf(s.getId()),"revisao",s.getRevisao(),"origem",origem(s.getOrigem()),
                "base",decimal(s.getDadosOrigem().getBase()),"competencia",s.getDadosOrigem().getCompetencia()==null?null:s.getDadosOrigem().getCompetencia().toString(),
                "motivoBase",s.getDadosOrigem().getCamposMateriais().get("MOTIVO_PENDENCIA_FINANCEIRO"),
                "nufinReferencia",s.getDadosOrigem().getCamposMateriais().get("NUFIN_REFERENCIA"),
                "entrada",entrada(s.getEntrada()),"situacao",s.getSituacao().name(),"pendencia",s.getPendencia(),"hash",s.getHash(),
                "usuario",String.valueOf(s.getUsuario()),"data",s.getData().toString(),
                "resultado",r==null?null:mapa("base",decimal(r.getBase()),"parcelas",Collections.unmodifiableList(parcelas),"grupos",Collections.unmodifiableList(grupos)));
    }
    private static Map<String,Object> entrada(EntradaSimulacao e){
        if(e.getModalidade()==EntradaSimulacao.Modalidade.CRITERIO)return mapa("modalidade","CRITERIO","criterioId",e.getCriterioId(),"criterioVersao",e.getCriterioVersao());
        List<Object> linhas=new ArrayList<Object>();for(EntradaSimulacao.Percentual p:e.getPercentuais())linhas.add(mapa("codEmp",p.getCodEmp(),"codCencus",p.getCodCencus(),"percentual",decimal(p.getPercentual())));
        return mapa("modalidade","MANUAL","percentuais",Collections.unmodifiableList(linhas));
    }
    private static Map<String,Object> origem(OrigemCanonica o){return mapa("tipo",o.getTipo().name(),"id",String.valueOf(o.getId()));}
    private static String decimal(BigDecimal v){return v==null?null:v.toPlainString();}
    private static Map<String,Object> mapa(Object... p){Map<String,Object> m=new LinkedHashMap<String,Object>();for(int i=0;i<p.length;i+=2)m.put((String)p[i],p[i+1]);return Collections.unmodifiableMap(m);}
}

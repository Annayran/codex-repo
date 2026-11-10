package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import java.time.LocalDate;
import java.util.*;

/** Approved 29/10/2026: full document nominal total; selected installment due date. */
public final class MapeamentoFinanceiroTgffin implements MapeamentoOrigem {
    @Override public BaseCompetencia calcular(DocumentoOrigem origem,ConexaoRateio conexao){
        return origem==null?BaseCompetencia.pendente():calcular(origem,conexao,origem.getOrigem());
    }
    @Override public BaseCompetencia calcular(DocumentoOrigem documento,ConexaoRateio conexao,OrigemCanonica referencia){
        if(documento==null||referencia==null||referencia.getTipo()!=OrigemCanonica.Tipo.TITULO)return BaseCompetencia.pendente();
        Map<String,String> materiais=new TreeMap<String,String>();
        materiais.put("NUFIN_REFERENCIA",String.valueOf(referencia.getId()));
        materiais.put("CAMPO_COMPETENCIA","TGFFIN.DTVENC");
        ReferenciaFinanceiraJdbc.Referencia selecionado=new ReferenciaFinanceiraJdbc(conexao).carregar(documento.getOrigem(),referencia.getId());
        LocalDate competencia=selecionado.getVencimento();
        if(selecionado.getMotivo()!=null)return pendente(competencia,selecionado.getMotivo(),materiais);
        ResumoFinanceiroJdbc.Resumo resumo;
        try{resumo=new ResumoFinanceiroJdbc(conexao).carregar(documento.getOrigem());}
        catch(ErroRateio e){
            if(!"BASE_INCOMPLETA".equals(e.getCodigo())&&!"CADASTRO_INVALIDO".equals(e.getCodigo()))throw e;
            return pendente(competencia,e.getMessage(),materiais);
        }
        Map<String,String> selecionada=null;
        for(Map<String,String> parcela:resumo.getParcelas()){
            if(String.valueOf(referencia.getId()).equals(parcela.get("nufin")))selecionada=parcela;
            String prefixo="PARCELA_"+parcela.get("nufin")+"_";
            materiais.put(prefixo+"VALOR",parcela.get("valor"));
            materiais.put(prefixo+"VENCIMENTO",parcela.get("vencimento")==null?"":parcela.get("vencimento"));
            materiais.put(prefixo+"DESDOBRAMENTO",parcela.get("desdobramento")==null?"":parcela.get("desdobramento"));
        }
        if(selecionada==null||selecionada.get("vencimento")==null){
            return pendente(competencia,selecionada==null?"REFERENCIA_FORA_DO_GRUPO":"VENCIMENTO_SELECIONADO_AUSENTE",materiais);
        }
        if(!competencia.equals(LocalDate.parse(selecionada.get("vencimento"))))return pendente(competencia,"VENCIMENTO_ALTERADO_NA_CONSULTA",materiais);
        materiais.put("QTD_PARCELAS",String.valueOf(resumo.getParcelas().size()));
        materiais.put("CAMPO_COMPETENCIA","TGFFIN.DTVENC");
        return new BaseCompetencia(resumo.getTotal(),competencia,"TGFFIN_TOTAL_DTVENC_SELECIONADO_V1",materiais);
    }
    private static BaseCompetencia pendente(LocalDate competencia,String motivo,Map<String,String> materiais){
        Set<String> aceitos=new HashSet<String>(Arrays.asList("TIPO_NAO_DESPESA","TITULO_PROVISIONADO","DOCUMENTO_RENEGOCIADO","VENCIMENTO_SELECIONADO_AUSENTE","IDENTIDADE_PARCELA_INVALIDA","VALOR_PARCELA_INVALIDO","SEM_PARCELAS_ELEGIVEIS","REFERENCIA_FORA_DO_GRUPO","VENCIMENTO_ALTERADO_NA_CONSULTA"));
        materiais.put("PENDENCIA_FINANCEIRO","BASE_INCOMPLETA");
        materiais.put("MOTIVO_PENDENCIA_FINANCEIRO",aceitos.contains(motivo)?motivo:"BASE_NAO_APURADA");
        return new BaseCompetencia(null,competencia,"TGFFIN_TOTAL_DTVENC_SELECIONADO_V1",materiais);
    }
}

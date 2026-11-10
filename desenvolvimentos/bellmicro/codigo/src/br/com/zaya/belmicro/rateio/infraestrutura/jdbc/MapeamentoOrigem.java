package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/** Approved category-specific mapping from server data. Never infer current date or gross amount. */
public interface MapeamentoOrigem {
    BaseCompetencia calcular(DocumentoOrigem origem,ConexaoRateio conexao);
    default BaseCompetencia calcular(DocumentoOrigem origem,ConexaoRateio conexao,
                                    br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica referencia){
        return calcular(origem,conexao);
    }

    final class BaseCompetencia {
        private final BigDecimal base;
        private final LocalDate competencia;
        private final String versao;
        private final Map<String,String> camposMateriais;
        public BaseCompetencia(BigDecimal base,LocalDate competencia,String versao,Map<String,String> materiais){
            this.base=base;this.competencia=competencia;
            if(versao==null||versao.trim().isEmpty())throw new IllegalArgumentException("Versao do mapeamento obrigatoria.");
            this.versao=versao;this.camposMateriais=Collections.unmodifiableMap(new TreeMap<String,String>(Objects.requireNonNull(materiais,"materiais")));
        }
        public BigDecimal getBase(){return base;}
        public LocalDate getCompetencia(){return competencia;}
        public String getVersao(){return versao;}
        public Map<String,String> getCamposMateriais(){return camposMateriais;}
        public static BaseCompetencia pendente(){return new BaseCompetencia(null,null,"NAO_HOMOLOGADO",Collections.<String,String>emptyMap());}
    }
}

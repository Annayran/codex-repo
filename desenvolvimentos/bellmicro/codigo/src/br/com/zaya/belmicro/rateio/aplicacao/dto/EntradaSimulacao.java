package br.com.zaya.belmicro.rateio.aplicacao.dto;

import br.com.zaya.belmicro.rateio.dominio.DestinoPeso;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Browser input has no final amounts, evidence, header keys, base or arbitrary dates. */
public final class EntradaSimulacao {
    public enum Modalidade { MANUAL, CRITERIO }
    public static final class Percentual {
        private final int codEmp;
        private final int codCencus;
        private final BigDecimal percentual;
        public Percentual(int codEmp, int codCencus, BigDecimal percentual) {
            this.codEmp = codEmp;
            this.codCencus = codCencus;
            this.percentual = percentual;
        }
        public int getCodEmp() { return codEmp; }
        public int getCodCencus() { return codCencus; }
        public BigDecimal getPercentual() { return percentual; }
    }
    private final Modalidade modalidade;
    private final String criterioId;
    private final Integer criterioVersao;
    private final List<Percentual> percentuais;

    private EntradaSimulacao(Modalidade modalidade, String criterioId, Integer criterioVersao,
                             List<Percentual> percentuais) {
        this.modalidade = modalidade;
        this.criterioId = criterioId;
        this.criterioVersao = criterioVersao;
        this.percentuais = Collections.unmodifiableList(new ArrayList<Percentual>(percentuais));
    }
    public static EntradaSimulacao manual(List<Percentual> percentuais) {
        if (percentuais == null || percentuais.contains(null)) {
            throw new ErroRateio("COMPOSICAO_INVALIDA", "Composicao manual ausente ou incompleta.");
        }
        return new EntradaSimulacao(Modalidade.MANUAL, null, null, percentuais);
    }
    public static EntradaSimulacao criterio(String id, Integer versao) {
        if (id == null || id.trim().isEmpty() || versao == null || versao <= 0) {
            throw new ErroRateio("CRITERIO_NAO_SELECIONADO", "Selecione criterio e versao.");
        }
        return new EntradaSimulacao(Modalidade.CRITERIO, id, versao, Collections.<Percentual>emptyList());
    }
    public Modalidade getModalidade() { return modalidade; }
    public String getCriterioId() { return criterioId; }
    public Integer getCriterioVersao() { return criterioVersao; }
    public List<Percentual> getPercentuais() { return percentuais; }

    public List<DestinoPeso> destinosManuais() {
        List<DestinoPeso> destinos = new ArrayList<DestinoPeso>();
        for (Percentual percentual : percentuais) {
            if (percentual.percentual == null || percentual.percentual.signum() <= 0
                    || percentual.percentual.stripTrailingZeros().scale() > 6) {
                throw new ErroRateio("COMPOSICAO_INVALIDA", "Percentual deve ser positivo, com ate seis casas.");
            }
            destinos.add(new DestinoPeso(percentual.codEmp, percentual.codCencus, "MANUAL",
                    percentual.percentual, "composicao-manual"));
        }
        return destinos;
    }
}

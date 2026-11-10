package br.com.zaya.proauto.compensacao;

import br.com.sankhya.mgefin.model.utils.CompensacaoFinanceiraHelper;
import br.com.sankhya.modelcore.financeiro.util.DadosCompensacao;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;

/** A transacao pertence a confirmacao da nota. Nao efetua DML direto em financeiros. */
public final class SankhyaCompensacaoGateway implements CompensacaoGateway {
    private final BigDecimal usuario;
    private final BigDecimal topReceita;
    private final BigDecimal topDespesa;
    private final Timestamp data;

    public SankhyaCompensacaoGateway(BigDecimal usuario, BigDecimal topReceita,
                                    BigDecimal topDespesa, Timestamp data) {
        this.usuario = usuario;
        this.topReceita = topReceita;
        this.topDespesa = topDespesa;
        this.data = new Timestamp(data.getTime());
    }

    @Override
    public BigDecimal compensar(BigDecimal credito, BigDecimal receita, BigDecimal valor) throws Exception {
        DadosCompensacao dados = new DadosCompensacao();
        dados.setChave("br.com.sankhya.mgefin.mov.CompensacaoFinanceira_prefs");
        dados.setTipo("T");
        dados.setUserID(usuario);
        dados.setDtBaixa(data);
        dados.setVlrAcerto(valor);
        dados.setTopRec(topReceita);
        dados.setTopDesp(topDespesa);
        dados.setTitulosDesp(Collections.singletonList(credito));
        dados.setTitulosRec(Collections.singletonList(receita));
        dados.setUsarDtVencBaixa(false);
        dados.setCompensaBolRapido(false);
        dados.setValorAtualMoeda(BigDecimal.ZERO);
        dados.setCodNatJuros(BigDecimal.ZERO);
        dados.setVlrIOF(BigDecimal.ZERO);
        dados.setVlrAntecipacao(BigDecimal.ZERO);
        return CompensacaoFinanceiraHelper.acertar(dados);
    }
}

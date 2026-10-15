package br.zaya.telecontrol.faturamento.application;

import java.math.BigDecimal;

/** Facade de aplicacao para faturamento. */
public class EnvioFaturamentoService {
    public static void setFaturamento(BigDecimal pedidoSelecionado) throws Exception {
        br.zaya.telecontrol.services.EnvioFaturamento.setFaturamento(pedidoSelecionado);
    }

    public static void setFaturamento() throws Exception {
        br.zaya.telecontrol.services.EnvioFaturamento.setFaturamento();
    }
}


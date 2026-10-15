package br.zaya.telecontrol.ordemservico.application;

import java.math.BigDecimal;

/** Facade de aplicacao para ordem de servico. */
public class BuscaOrdemServicoService {
    public static void getOrdemServico(String os) throws Exception {
        br.zaya.telecontrol.services.BuscaOrdemServico.getOrdemServico(os);
    }

    public static void getOrdemServico(String os, BigDecimal nuNota) throws Exception {
        br.zaya.telecontrol.services.BuscaOrdemServico.getOrdemServico(os, nuNota);
    }

    public static void getGeralNaoIntegradas() throws Exception {
        br.zaya.telecontrol.services.BuscaOrdemServico.getGeralNaoIntegradas();
    }

    public static void gravarOrdemServico(br.zaya.telecontrol.models.OrdemServico ordemServico, BigDecimal nuNota) throws Exception {
        br.zaya.telecontrol.services.BuscaOrdemServico.gravarOrdemServico(ordemServico, nuNota);
    }

    public static void syncOrdemServicoByNota(BigDecimal nuNota) throws Exception {
        br.zaya.telecontrol.services.BuscaOrdemServico.syncOrdemServicoByNota(nuNota);
    }
}


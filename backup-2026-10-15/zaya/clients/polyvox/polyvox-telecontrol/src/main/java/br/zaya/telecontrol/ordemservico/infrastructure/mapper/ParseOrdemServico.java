package br.zaya.telecontrol.ordemservico.infrastructure.mapper;

import br.zaya.telecontrol.models.OrdemServico;
import br.zaya.telecontrol.models.OrdemServicoList;

/** Bridge de compatibilidade para o parser legado de ordem de servico. */
public class ParseOrdemServico {
    public static OrdemServicoList getOrdemServicoResultado(String result) throws Exception {
        return br.zaya.telecontrol.parsers.ParseOrdemServico.getOrdemServicoResultado(result);
    }

    public static String toPrettyJson(OrdemServico result) {
        return br.zaya.telecontrol.parsers.ParseOrdemServico.toPrettyJson(result);
    }
}


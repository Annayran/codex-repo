package br.com.zaya.teleworld.flow.controller;

import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.zaya.teleworld.flow.model.service.LiberacaoProcessoService;

public class LiberacaoProcessoController {
    private final LiberacaoProcessoService service;

    public LiberacaoProcessoController(LiberacaoProcessoService service) {
        this.service = service;
    }

    public void atualizar(PersistenceEvent event) throws Exception {
        DynamicVO atual = (DynamicVO) event.getVo();
        // getOldVO e exclusivo de beforeUpdate; este controller roda no afterUpdate.
        ModifingFields campos = event.getModifingFields();
        service.processar(atual.asBigDecimal("NUNOTA"), atual.getProperty("AD_IDNEGOCIO"),
                atual.asString("AD_SOLMIN"), atual.asString("AD_LIBPROC"), campos != null,
                valorAnterior(campos, atual, "AD_SOLMIN"),
                valorAnterior(campos, atual, "AD_LIBPROC"));
    }

    private String valorAnterior(ModifingFields campos, DynamicVO atual, String campo) {
        if (campos == null) {
            return null;
        }
        if (!campos.isModifing(campo)) {
            return atual.asString(campo);
        }
        Object anterior = campos.getOldValue(campo);
        return anterior == null ? null : anterior.toString();
    }
}

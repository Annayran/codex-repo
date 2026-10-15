package br.zaya.telecontrol.peca.application;

import br.zaya.telecontrol.services.BuscaCadastrosTelecontrolService;

public class AtualizaPecaService {
    public static BuscaCadastrosTelecontrolService.Resultado atualizarPecas() throws Exception {
        return BuscaCadastrosTelecontrolService.atualizarPecas();
    }
}

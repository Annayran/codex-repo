package br.zaya.telecontrol.produto.application;

import br.zaya.telecontrol.services.BuscaCadastrosTelecontrolService;

public class AtualizaProdutoService {
    public static BuscaCadastrosTelecontrolService.Resultado atualizarProdutos() throws Exception {
        return BuscaCadastrosTelecontrolService.atualizarProdutos();
    }
}

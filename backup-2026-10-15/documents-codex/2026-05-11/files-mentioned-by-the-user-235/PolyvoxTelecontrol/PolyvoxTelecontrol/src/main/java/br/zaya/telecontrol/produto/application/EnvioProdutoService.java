package br.zaya.telecontrol.produto.application;

/** Facade de aplicacao para produto. */
public class EnvioProdutoService {
    public static void postProduto() throws Exception {
        br.zaya.telecontrol.services.EnvioProduto.postProduto();
    }

    public static void postProduto(String where) throws Exception {
        br.zaya.telecontrol.services.EnvioProduto.postProduto(where);
    }
}


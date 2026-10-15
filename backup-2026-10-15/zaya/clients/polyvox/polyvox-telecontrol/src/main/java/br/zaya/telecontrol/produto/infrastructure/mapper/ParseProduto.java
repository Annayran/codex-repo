package br.zaya.telecontrol.produto.infrastructure.mapper;

import br.com.sankhya.jape.vo.DynamicVO;

/** Bridge de compatibilidade para o parser legado de produto. */
public class ParseProduto {
    public static String toJson(DynamicVO produtoVO) {
        return br.zaya.telecontrol.parsers.ParseProduto.toJson(produtoVO);
    }

    public static String toJsonPut(DynamicVO produtoVO) {
        return br.zaya.telecontrol.parsers.ParseProduto.toJsonPut(produtoVO);
    }
}


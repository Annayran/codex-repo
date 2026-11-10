package br.com.zaya.portomag.importador.model;

import java.util.*;

public final class PedidoRecebido {
    public final Map<String,Object> cabecalho = new LinkedHashMap<String,Object>();
    public final List<Map<String,Object>> itens = new ArrayList<Map<String,Object>>();
    public final List<String> alertas = new ArrayList<String>();
}

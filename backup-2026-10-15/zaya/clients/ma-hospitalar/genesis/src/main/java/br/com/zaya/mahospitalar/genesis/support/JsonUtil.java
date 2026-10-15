package br.com.zaya.mahospitalar.genesis.support;

import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDTO;

public final class JsonUtil {
    private JsonUtil() {
    }

    public static String toJson(EstoqueGenesisDTO dto) {
        StringBuilder json = new StringBuilder();
        json.append('{');
        append(json, "altura", dto.getAltura());
        append(json, "cep", dto.getCep());
        append(json, "cfop", dto.getCfop());
        append(json, "codigo", dto.getCodigo());
        append(json, "codigoExterno", dto.getCodigoExterno());
        append(json, "consumo", dto.getConsumo());
        append(json, "controlarEstoque", dto.getControlarEstoque());
        append(json, "descServico", dto.getDescServico());
        append(json, "descTecnica", dto.getDescTecnica());
        append(json, "dtAlteracao", dto.getDtAlteracao());
        append(json, "dtEntrada", dto.getDtEntrada());
        append(json, "dtVencimento", dto.getDtVencimento());
        append(json, "envioTransp", dto.getEnvioTransp());
        append(json, "id", dto.getId());
        append(json, "idCategoria", dto.getIdCategoria());
        append(json, "idFabricante", dto.getIdFabricante());
        append(json, "idFornecedor", dto.getIdFornecedor());
        append(json, "idSubCategoria", dto.getIdSubCategoria());
        append(json, "idUsuario", dto.getIdUsuario());
        append(json, "idUsuarioAlteracao", dto.getIdUsuarioAlteracao());
        append(json, "ipi", dto.getIpi());
        append(json, "largura", dto.getLargura());
        append(json, "localizacao", dto.getLocalizacao());
        append(json, "maximo", dto.getMaximo());
        append(json, "medio", dto.getMedio());
        append(json, "minimo", dto.getMinimo());
        append(json, "ncm", dto.getNcm());
        append(json, "nserie", dto.getNserie());
        append(json, "partNumber", dto.getPartNumber());
        append(json, "percVenda", dto.getPercVenda());
        append(json, "periodoGarantia", dto.getPeriodoGarantia());
        append(json, "peso", dto.getPeso());
        append(json, "profundidade", dto.getProfundidade());
        append(json, "qtd", dto.getQtd());
        append(json, "rgAnvisa", dto.getRgAnvisa());
        append(json, "servico", dto.getServico());
        append(json, "status", dto.getStatus());
        append(json, "tag", dto.getTag());
        append(json, "venda", dto.getVenda());
        append(json, "vlrCusto", dto.getVlrCusto());
        append(json, "vlrCustoTotal", dto.getVlrCustoTotal());
        append(json, "vlrVenda", dto.getVlrVenda());
        append(json, "vlrVendaTotal", dto.getVlrVendaTotal());
        if (json.charAt(json.length() - 1) == ',') json.deleteCharAt(json.length() - 1);
        json.append('}');
        return json.toString();
    }

    private static void append(StringBuilder json, String name, Object value) {
        if (value == null) return;
        json.append('"').append(name).append('"').append(':');
        if (value instanceof Number || value instanceof Boolean) {
            json.append(value);
        } else {
            json.append('"').append(escape(String.valueOf(value))).append('"');
        }
        json.append(',');
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
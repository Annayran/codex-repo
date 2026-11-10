package org.ImportadorPedido;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import java.math.BigDecimal;
import org.ImportadorPedido.helper.PlanilhaJsonHelper;
import org.ImportadorPedido.models.DadosPlanilha;

public class PlanilhaJsonRegressionTest {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        JsonNode limpo = PlanilhaJsonHelper.limparCabecalhos(mapper.readTree(
            "[{\"PVX\":\"TESTE\",\"Qty\":1.5,\"Unit Price (USD)\":0.000801,"
            + "\"total Price (USD)\":0.001201,\"__EMPTY\":123,\" __EMPTY_1 \":\"extra\"}]"), mapper);
        DadosPlanilha[] linhas = mapper.treeToValue(limpo, DadosPlanilha[].class);
        if (limpo.get(0).has("__EMPTY") || limpo.get(0).has("__EMPTY_1")
                || linhas[0].getQty().compareTo(new BigDecimal("1.5")) != 0
                || linhas[0].getUnitPrice().compareTo(new BigDecimal("0.000801")) != 0
                || linhas[0].getTotalPrice().compareTo(new BigDecimal("0.001201")) != 0) {
            throw new AssertionError("Colunas artificiais ou decimais incorretos");
        }
        try {
            mapper.treeToValue(PlanilhaJsonHelper.limparCabecalhos(
                mapper.readTree("{\"UNITPRICERRADO\":1}"), mapper), DadosPlanilha.class);
            throw new AssertionError("Campo desconhecido real deveria ser rejeitado");
        } catch (UnrecognizedPropertyException expected) {
            System.out.println("PlanilhaJsonRegressionTest: OK");
        }
    }
}

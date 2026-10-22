package br.com.zaya.mahospitalar.genesis.importacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapeadorPlanilhaGenesisTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MapeadorPlanilhaGenesis mapeador = new MapeadorPlanilhaGenesis();

    @Test
    void deveMapearClienteComCabecalhosDaPlanilhaOriginal() throws Exception {
        RegistroBaseGenesis registro = mapeador.mapear(AbaBaseGenesis.CLIENTE, objectMapper.readTree(
                "{\"id\":12,\"codigoExterno\":\"CLI-12\",\"razaoSocial\":\"Hospital X\","
                        + "\"nomeFantasia\":\"Hospital\",\"cnpj\":\"12.345.678/0001-90\","
                        + "\"enderecoUF\":\"GO\",\"status\":\"ATIVO\"}"), 2);

        Map<String, Object> campos = registro.getCampos();
        assertEquals(new BigDecimal("12"), registro.getIdGenesis());
        assertEquals("CLI-12", campos.get("CODIGOEXTERNO"));
        assertEquals("12345678000190", campos.get("CNPJ"));
        assertEquals("GO", campos.get("ENDERECOUF"));
    }

    @Test
    void deveImportarVinculoClienteFornecidoPelaPlanilha() throws Exception {
        RegistroBaseGenesis registro = mapeador.mapear(AbaBaseGenesis.CLIENTE, objectMapper.readTree(
                "{\"id\":12,\"codigoExterno\":\"0047250001\",\"CODPARC\":70349}"), 2);

        Map<String, Object> campos = registro.getCampos();
        assertEquals(new BigDecimal("70349"), campos.get("CODPARC"));
        assertEquals("CONCILIADO", campos.get("STATUSCONC"));
        assertEquals("VINCULO_IMPORTADO_PLANILHA: CODPARC_INFORMADO", campos.get("MENSAGEMCONC"));
        assertTrue(campos.get("DHCONCILIACAO") instanceof Timestamp);
    }

    @Test
    void deveLimparVinculoClienteQuandoPlanilhaInformarNaoLocalizado() throws Exception {
        RegistroBaseGenesis registro = mapeador.mapear(AbaBaseGenesis.CLIENTE, objectMapper.readTree(
                "{\"id\":13,\"codigoExterno\":\"SEM-VINCULO\",\"CODPARC\":\"#N/A\"}"), 3);

        Map<String, Object> campos = registro.getCampos();
        assertTrue(campos.containsKey("CODPARC"));
        assertNull(campos.get("CODPARC"));
        assertEquals("PENDENTE", campos.get("STATUSCONC"));
        assertEquals("VINCULO_IMPORTADO_PLANILHA: CODPARC_NAO_LOCALIZADO", campos.get("MENSAGEMCONC"));
    }

    @Test
    void devePreservarVinculoQuandoPlanilhaAntigaNaoPossuirCodParc() throws Exception {
        RegistroBaseGenesis registro = mapeador.mapear(AbaBaseGenesis.CLIENTE, objectMapper.readTree(
                "{\"id\":14,\"codigoExterno\":\"CLIENTE-14\"}"), 4);

        assertTrue(!registro.getCampos().containsKey("CODPARC"));
        assertTrue(!registro.getCampos().containsKey("STATUSCONC"));
    }

    @Test
    void deveAceitarNomePluralDaAbaClientes() {
        assertEquals(AbaBaseGenesis.CLIENTE, AbaBaseGenesis.porNome("Clientes"));
    }

    @Test
    void deveRecusarCodParcFracionado() throws Exception {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class, () ->
                mapeador.mapear(AbaBaseGenesis.CLIENTE,
                        objectMapper.readTree("{\"id\":15,\"CODPARC\":\"10.5\"}"), 7));
        assertTrue(erro.getMessage().contains("Linha 7"));
    }

    @Test
    void deveMapearItemMaterialEConverterDataIso() throws Exception {
        RegistroBaseGenesis registro = mapeador.mapear(AbaBaseGenesis.ITEM_MATERIAL, objectMapper.readTree(
                "{\"id\":99,\"idModelo\":8,\"idCliente\":7,\"idSetor\":6,"
                        + "\"nSerie\":\"SER-99\",\"valorUnitario\":\"1500.25\","
                        + "\"DataAquisicao\":\"2026-08-27T10:15:30Z\","
                        + "\"id_propriedade_equipamento\":2,\"id_situacao\":3}"), 3);

        Map<String, Object> campos = registro.getCampos();
        assertEquals(new BigDecimal("8"), campos.get("IDMODELOGEN"));
        assertEquals("SER-99", campos.get("NSERIE"));
        assertEquals(new BigDecimal("1500.25"), campos.get("VALORUNITARIO"));
        assertTrue(campos.get("DATAAQUISICAO") instanceof Timestamp);
    }

    @Test
    void deveAceitarCabecalhosFisicos() throws Exception {
        RegistroBaseGenesis registro = mapeador.mapear(AbaBaseGenesis.MODELO, objectMapper.readTree(
                "{\"IDGENESIS\":4,\"IDEMPRESAGEN\":2,\"IDMATERIALGEN\":3,"
                        + "\"CODIGOEXTERNO\":\"MOD-4\",\"NOME\":\"Modelo 4\",\"NRANVISA\":\"ANV-4\"}"), 2);
        assertEquals("ANV-4", registro.getCampos().get("NRANVISA"));
    }

    @Test
    void deveRecusarIdAusente() throws Exception {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class, () ->
                mapeador.mapear(AbaBaseGenesis.CLIENTE,
                        objectMapper.readTree("{\"razaoSocial\":\"Sem ID\"}"), 8));
        assertTrue(erro.getMessage().contains("Linha 8"));
    }

    @Test
    void deveRecusarAbaForaDaAllowlist() {
        assertThrows(IllegalArgumentException.class, () -> AbaBaseGenesis.porNome("TABELA_ARBITRARIA"));
    }
}

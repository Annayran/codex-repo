package br.com.crmbonus.produtos.query;

import br.com.crmbonus.produtos.dto.ProdutoCRMRequestDTO;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;

import java.sql.ResultSet;

public class ProdutoCRMQueryHelper {

    public static ProdutoCRMRequestDTO buscarProduto(JdbcWrapper jdbc, String codProd) throws Exception {

        System.out.println("[CRM-BONUS] Iniciando busca do produto no Sankhya...");
        System.out.println("[CRM-BONUS] CODPROD recebido: " + codProd);

        NativeSql sql = new NativeSql(jdbc);

        sql.appendSql(
                "SELECT " +
                        "    GRU.DESCRGRUPOPROD, " +
                        "    PRO.ATIVO, " +
                        "    PRO.MARCA, " +
                        "    PRO.CODPROD, " +
                        "    PRO.DESCRPROD, " +
                        "    PRO.CODVOL, " +
                        "    EXC.VLRVENDA " +
                        "FROM TGFPRO PRO " +
                        "JOIN TGFEXC EXC ON EXC.CODPROD = PRO.CODPROD " +
                        "JOIN TGFTAB TAB ON TAB.NUTAB = EXC.NUTAB " +
                        "JOIN TGFGRU GRU ON GRU.CODGRUPOPROD = PRO.CODGRUPOPROD " +
                        "WHERE TAB.NUTAB = (SELECT MAX(T.NUTAB) FROM TGFTAB T) " +
                        "AND PRO.CODPROD = :CODPROD"
        );

        sql.setNamedParameter("CODPROD", codProd);

        ResultSet rset = sql.executeQuery();

        if (!rset.next()) {

            System.out.println("[CRM-BONUS] ❌ Nenhum registro retornado para CODPROD: " + codProd);
            System.out.println("[CRM-BONUS] Verifique:");
            System.out.println(" - Se o produto existe na TGFPRO");
            System.out.println(" - Se possui preço na última TGFTAB");
            System.out.println(" - Se o join com TGFEXC está retornando dados");

            throw new Exception("Produto não encontrado no Sankhya para CODPROD: " + codProd);
        }

        System.out.println("[CRM-BONUS] ✅ Produto encontrado. Montando DTO...");

        ProdutoCRMRequestDTO dto = new ProdutoCRMRequestDTO();

        dto.sku = rset.getString("CODPROD");
        dto.name = rset.getString("DESCRPROD");
        dto.unit_measurement = rset.getString("CODVOL");

        String ativo = rset.getString("ATIVO");
        dto.status = "S".equalsIgnoreCase(ativo);

        ProdutoCRMRequestDTO.Price price = new ProdutoCRMRequestDTO.Price();
        price.value_of = rset.getBigDecimal("VLRVENDA");
        price.value_per = rset.getBigDecimal("VLRVENDA");
        price.currency = "BRL";
        dto.price = price;

        dto.categories.put("brand",
                rset.getString("MARCA") != null
                        ? rset.getString("MARCA")
                        : "SEM MARCA"
        );

        dto.categories.put("group",
                rset.getString("DESCRGRUPOPROD") != null
                        ? rset.getString("DESCRGRUPOPROD")
                        : "SEM GRUPO"
        );

        dto.metadata.put("origem", "SANKHYA");

        System.out.println("[CRM-BONUS] DTO montado com sucesso para SKU: " + dto.sku);

        return dto;
    }
}
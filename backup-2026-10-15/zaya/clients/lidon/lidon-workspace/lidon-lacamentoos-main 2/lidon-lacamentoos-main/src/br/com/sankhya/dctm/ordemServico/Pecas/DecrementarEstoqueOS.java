package br.com.sankhya.dctm.ordemServico.Pecas;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class DecrementarEstoqueOS {
    private JdbcWrapper getJdbc() {
        return EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
    }

    public void decrementarReservadoTgfEst(BigDecimal codProd, BigDecimal codLocal, BigDecimal qtd, BigDecimal codEmp) throws Exception {
        System.out.println("[decrementarReservadoTgfEst] Iniciando decremento em TGFEST → CODPROD=" + codProd +
                ", CODLOCAL=" + codLocal + ", Decremento=" + qtd + ", CODEMP=" + codEmp);

        JdbcWrapper jdbc = getJdbc();
        NativeSql sql = new NativeSql(jdbc);

        String query =
                "SELECT RESERVADO FROM TGFEST WHERE CODPROD = " + codProd +
                        " AND CODLOCAL = " + codLocal +
                        " AND CODEMP = " + codEmp;

        ResultSet rs = sql.executeQuery(query);

        if (rs.next()) {
            BigDecimal qtdReservado = rs.getBigDecimal("RESERVADO");

            // Subtrai a quantidade
            BigDecimal novoReservado = (qtdReservado != null ? qtdReservado : BigDecimal.ZERO)
                    .subtract(qtd != null ? qtd : BigDecimal.ZERO);

            // Garante que nunca fique negativo
            if (novoReservado.compareTo(BigDecimal.ZERO) < 0) {
                novoReservado = BigDecimal.ZERO;
            }

            JapeFactory.dao(DynamicEntityNames.ESTOQUE)
                    .prepareToUpdateByPK(codEmp, codProd, codLocal, " ", BigDecimal.ZERO, "P")
                    .set("RESERVADO", novoReservado)
                    .update();

            System.out.println("[decrementarReservadoTgfEst] RESERVADO atualizado para " + novoReservado);
        } else {
            System.out.println("[decrementarReservadoTgfEst] Nenhum estoque encontrado para CODPROD=" + codProd + " no CODLOCAL=" + codLocal);
        }

        rs.close();
    }
}

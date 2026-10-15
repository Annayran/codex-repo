package ReservaEstoque.repository;



import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import java.math.BigDecimal;
import java.sql.ResultSet;

public class ReservaRepository {

    private JdbcWrapper getJdbc() {
        return EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
    }

    // DTO interno para carregar os dados da TCSPRO
    public static class TcProDados {
        private BigDecimal quantidade;
        private BigDecimal codLocal;
        private BigDecimal codEmp;

        public TcProDados(BigDecimal quantidade, BigDecimal codLocal, BigDecimal codEmp) {
            this.quantidade = quantidade;
            this.codLocal = codLocal;
            this.codEmp = codEmp;
        }
        public BigDecimal getQuantidade() { return quantidade; }
        public BigDecimal getCodLocal() { return codLocal; }
        public BigDecimal getCodEmp() { return codEmp; }
    }

    /**
     * Busca QUANTIDADE, CODLOCAL do último registro (MAX DTALTER) da TCSPRO para o produto
     */
    public TcProDados buscarUltimoRegistroTcPro(BigDecimal codProd) throws Exception {
        System.out.println("[ReservaRepository] Iniciando busca na AD_TCSPRO para CODPROD=" + codProd);

        JdbcWrapper jdbc = getJdbc();
        NativeSql sql = new NativeSql(jdbc);

        String query =
                "SELECT PRO.QUANTIDADE AS QUANTIDADE, " +
                        "NVL(PRO.CODLOCAL, P.CODLOCALPADRAO) AS CODLOCAL, " +
                        "OS.CODEMP AS CODEMP, " +
                        "OS.CODPARC AS CODPARC " +
                        "FROM AD_TCSOSE OS " +
                        "JOIN AD_TCSPRO PRO ON PRO.NUMOS = OS.NUMOS " +
                        "JOIN TGFPRO P ON P.CODPROD = PRO.CODPROD " +
                        "WHERE PRO.CODPROD = " + codProd + " " +
                        "AND NVL(PRO.DTALTER, P.DTALTER) = ( " +
                        "   SELECT MAX(NVL(PROX.DTALTER, P2.DTALTER)) " +
                        "   FROM AD_TCSPRO PROX " +
                        "   JOIN TGFPRO P2 ON P2.CODPROD = PROX.CODPROD " +
                        "   WHERE PROX.CODPROD = " + codProd + " )";

        sql.appendSql(query);

        System.out.println("[ReservaRepository] Executando SQL: " + query);

        ResultSet rs = sql.executeQuery();
        TcProDados dados = null;
        if (rs.next()) {
            BigDecimal qtd = rs.getBigDecimal("QUANTIDADE");
            BigDecimal codLocal = rs.getBigDecimal("CODLOCAL");
            BigDecimal codEmp = rs.getBigDecimal("CODEMP");

            System.out.println("[ReservaRepository] Registro encontrado → QUANTIDADE=" + qtd + ", CODLOCAL=" + codLocal);

            dados = new TcProDados(qtd, codLocal,  codEmp);
        } else {
            System.out.println("[ReservaRepository] Nenhum registro encontrado para CODPROD=" + codProd);
        }
        rs.close();

        System.out.println("[ReservaRepository] Finalizada busca na AD_TCSPRO para CODPROD=" + codProd);
        return dados;
    }

    /**
     * Incrementa o RESERVADO na TGFEST de forma segura (RESERVADO = RESERVADO + qtd)
     */
    public void incrementarReservadoTgfEst(BigDecimal codProd, BigDecimal codLocal, BigDecimal qtd, BigDecimal codEmp) throws Exception {
        System.out.println("[ReservaRepository] Iniciando atualização em TGFEST → CODPROD=" + codProd +
                ", CODLOCAL=" + codLocal + ", Incremento=" + qtd + ", CODEMP=" + codEmp);

        JdbcWrapper jdbc = getJdbc();
        NativeSql sql = new NativeSql(jdbc);

        String query =
                "SELECT EST.RESERVADO AS RESERVADO " +
                        ", EST.ESTOQUE AS ESTOQUE " +
                        "FROM TGFEST EST " +
                        "JOIN TGFPRO PRO ON PRO.CODPROD = EST.CODPROD " +
                        "WHERE EST.CODPROD = " + codProd +
                        " AND EST.CODEMP = " + codEmp +
                        " AND EST.CODLOCAL = PRO.CODLOCALPADRAO";

        ResultSet rs = sql.executeQuery(query);

        if (rs.next()) {
            BigDecimal estoque = rs.getBigDecimal("ESTOQUE");
            BigDecimal reservadoAtual = rs.getBigDecimal("RESERVADO");

            BigDecimal qtdEstoque = estoque != null ? estoque : BigDecimal.ZERO;
            BigDecimal qtdReservada = reservadoAtual != null ? reservadoAtual : BigDecimal.ZERO;
            BigDecimal qtdSolicitada = qtd != null ? qtd : BigDecimal.ZERO;

            // validação
            if (qtdEstoque.compareTo(qtdSolicitada) < 0) {
                throw new RuntimeException("<b>[ERRO]:</b> Estoque insuficiente! Disponível="
                        + qtdEstoque + ", Solicitado=" + qtdSolicitada);
            }

            // calculando novo reservado
            BigDecimal novoReservado = qtdReservada.add(qtdSolicitada);

            // atualiza TGFEST
            JapeFactory.dao(DynamicEntityNames.ESTOQUE)
                    .prepareToUpdateByPK(codEmp, codProd, codLocal, " ", BigDecimal.ZERO, "P")
                    .set("RESERVADO", novoReservado)
                    .update();

            System.out.println("[ReservaRepository] Atualização realizada com sucesso. Reservado=" + novoReservado);
        } else {
            throw new Exception("[ReservaRepository] ERRO: Registro de estoque não encontrado em TGFEST!");
        }

    }

    /**
     * Decrementa o RESERVADO na TGFEST (RESERVADO = RESERVADO - qtd)
     */
    public void decrementarReservadoTgfEst(BigDecimal codProd, BigDecimal codLocal, BigDecimal qtd, BigDecimal codEmp) throws Exception {
        System.out.println("[decrementarReservadoTgfEst] Iniciando decremento em TGFEST → CODPROD=" + codProd +
                ", CODLOCAL=" + codLocal + ", Decremento=" + qtd + ", CODEMP=" + codEmp);

        JdbcWrapper jdbc = getJdbc();
        NativeSql sql = new NativeSql(jdbc);

        String query =
                "SELECT EST.RESERVADO AS RESERVADO " +
                        "FROM TGFEST EST " +
                        "JOIN TGFPRO PRO ON PRO.CODPROD = EST.CODPROD " +
                        "WHERE EST.CODPROD = " + codProd +
                        " AND EST.CODEMP = " + codEmp +
                        " AND EST.CODLOCAL = PRO.CODLOCALPADRAO";

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


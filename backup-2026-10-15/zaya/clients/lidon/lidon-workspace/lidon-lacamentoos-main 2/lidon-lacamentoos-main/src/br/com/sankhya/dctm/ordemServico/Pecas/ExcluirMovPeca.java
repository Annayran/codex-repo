package br.com.sankhya.dctm.ordemServico.Pecas;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.QueryExecutor;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ExcluirMovPeca implements AcaoRotinaJava {
        private String msgRetorno = "";
        private String msgErro = "";

        public void doAction(ContextoAcao ctx) {
            System.out.println("[ExcluirMovPeca]");

            for(int i = 0; i < ctx.getLinhas().length; ++i) {
                Registro line = ctx.getLinhas()[i];
                this.excluirNota(line, ctx);
            }

            if (this.msgErro.equals("")) {
                ctx.setMensagemRetorno(this.msgRetorno);
            } else {
                ctx.setMensagemRetorno(this.msgErro);
            }

        }

        private void excluirNota(Registro line, ContextoAcao ctx) {
            QueryExecutor query = ctx.getQuery();
            QueryExecutor queryUpd = ctx.getQuery();
            StringBuffer sql = new StringBuffer();
            String sqlUpt = "";
            BigDecimal numOs = (BigDecimal)line.getCampo("NUMOS");
            BigDecimal nuNota = BigDecimal.ZERO;
            BigDecimal nunotaInt = (BigDecimal)line.getCampo("NUNOTAINT");
            System.out.println("[ExcluirMovPeca]: Iniciando rotina de exclusão das notas de serviço geradas pela OS " + numOs);
            sql.append("SELECT NUNOTA FROM AD_TCSPRO WHERE NUMOS = " + numOs);
            this.msgRetorno = this.msgRetorno + "<b>Sucesso!</b><br/><br/>Nro. Único das notas removidas: ";

            try {
                Set<BigDecimal> nuNotaSet = new HashSet();
                query.nativeSelect(sql.toString());

                while(query.next()) {
                    nuNota = query.getBigDecimal("NUNOTA");
                    if (nuNota != null && nuNotaSet.add(nuNota)) {
                        sqlUpt = "UPDATE AD_TCSPRO SET NUNOTA = NULL, STATUS = 'P' WHERE NUMOS = " + numOs;
                        queryUpd.update(sqlUpt);
                        sqlUpt = "DELETE TGFCAB WHERE NUNOTA = " + nuNota;
                        queryUpd.update(sqlUpt);
                        System.out.println("[ExcluirMovPeca]: NUNOTA removida: " + nuNota + " da OS: " + numOs);


                    }
                }

                System.out.println("ATUALIZANDO NOTA INTERNA");
                JapeWrapper atualizaNota = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
                atualizaNota.prepareToUpdateByPK(nunotaInt).set("PENDENTE", "S").update();

                JdbcWrapper jdbc = null;
                NativeSql sql2 = null;
                System.out.println("Atualizando TGFITE para PENDENTE = N | NUNOTA=" + nunotaInt);

                jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
                jdbc.openSession();

                sql2 = new NativeSql(jdbc);
                sql2.appendSql(
                        "UPDATE TGFITE " +
                                "   SET PENDENTE = 'S' " +
                                " WHERE NUNOTA = :NUNOTA"
                );

                sql2.setNamedParameter("NUNOTA", nunotaInt);

                sql2.executeUpdate();

                if (nuNotaSet.isEmpty()) {

                    this.msgRetorno = "Nenhuma nota a ser removida!";
                } else {
                    this.msgRetorno = this.msgRetorno + String.join(",", (CharSequence[])nuNotaSet.stream().map(BigDecimal::toString).toArray((var0) -> new String[var0]));
                }
            } catch (Exception e) {
                this.msgErro = String.valueOf(this.msgErro) + e.getMessage();
                e.printStackTrace();
            } finally {
                query.close();
            }

        }
    }

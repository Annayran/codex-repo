package br.com.sankhya.bhz.central;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.sankhya.bhz.utils.AcessoBanco;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.CentralFinanceiro;

/** Fonte reconstruido a partir do modulo fornecido, com atualizacao final da ordem. */
public class AtualizarLocal implements AcaoRotinaJava {
    @Override
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            String empresa = (String) ctx.getParam("CODEMP");
            BigDecimal codEmp = new BigDecimal(empresa.trim());
            JapeWrapper empresaDAO = JapeFactory.dao("EmpresaFinanceiro");
            DynamicVO empresaVO = empresaDAO.findOne("CODEMP = ?", codEmp);
            if (empresaVO == null) {
                ctx.setMensagemRetorno("Empresa não encontrada: " + codEmp);
                return;
            }
            BigDecimal localPadrao = empresaVO.asBigDecimal("AD_CODLOCAL");
            if (localPadrao == null) {
                ctx.setMensagemRetorno("A empresa " + codEmp + " não possui Local Padrão configurado.");
                return;
            }
            JapeWrapper cabDAO = JapeFactory.dao("CabecalhoNota");
            JapeWrapper itemDAO = JapeFactory.dao("ItemNota");
            JapeWrapper topDAO = JapeFactory.dao("TipoOperacao");
            JapeWrapper usuarioDAO = JapeFactory.dao("Usuario");
            Registro[] registros = ctx.getLinhas();
            if (registros == null || registros.length == 0) {
                ctx.setMensagemRetorno("Nenhum registro selecionado na TGFCAB.");
                return;
            }

            // A ausencia de ordem nao impede a troca de empresa do fluxo original.
            JapeWrapper ordemDAO = JapeFactory.dao("OrdemCarga");
            Map<BigDecimal, DynamicVO> ordens = new LinkedHashMap<BigDecimal, DynamicVO>();
            for (Registro registro : registros) {
                BigDecimal nuNota = (BigDecimal) registro.getCampo("NUNOTA");
                DynamicVO cab = cabDAO.findByPK(nuNota);
                if (cab == null) {
                    throw new Exception("Pedido não encontrado: " + nuNota);
                }
                validarPedido(cab, nuNota);
                BigDecimal numero = cab.asBigDecimal("ORDEMCARGA");
                if (numero == null || numero.signum() <= 0) {
                    continue;
                }
                BigDecimal chave = numero.stripTrailingZeros();
                if (!ordens.containsKey(chave)) {
                    DynamicVO ordem = ordemDAO.findOne("CODEMP = ? AND ORDEMCARGA = ?", codEmp, numero);
                    if (ordem != null) {
                        ordens.put(chave, ordem);
                    }
                }
            }

            for (Registro registro : registros) {
                BigDecimal nuNota = (BigDecimal) registro.getCampo("NUNOTA");
                DynamicVO cab = cabDAO.findByPK(nuNota);
                validarPedido(cab, nuNota);
                cabDAO.prepareToUpdate(cab).set("CODEMP", codEmp).update();
                BigDecimal usuario = AuthenticationInfo.getCurrent().getUserID();
                DynamicVO usuarioVO = usuarioDAO.findByPK(usuario);
                BigDecimal localUsuario = usuarioVO.asBigDecimalOrZero("AD_CODLOCAL");
                BigDecimal local = localUsuario != null && localUsuario.compareTo(BigDecimal.ZERO) > 0
                        ? localUsuario : localPadrao;
                for (DynamicVO item : itemDAO.find("NUNOTA = ?", nuNota)) {
                    itemDAO.prepareToUpdate(item).set("CODLOCALORIG", local).set("CODEMP", codEmp).update();
                }
                BigDecimal codTipoOper = cab.asBigDecimal("CODTIPOPER");
                Timestamp dhTipoOper = cab.asTimestamp("DHTIPOPER");
                if (codTipoOper != null && dhTipoOper != null) {
                    DynamicVO top = topDAO.findOne("CODTIPOPER = ? AND DHALTER = ?", codTipoOper, dhTipoOper);
                    BigDecimal atualFin = top == null ? null : top.asBigDecimal("ATUALFIN");
                    if (atualFin != null && atualFin.intValue() == 1) {
                        CentralFinanceiro central = new CentralFinanceiro();
                        central.inicializaNota(nuNota);
                        AcessoBanco banco = new AcessoBanco();
                        banco.openSession();
                        try {
                            banco.update("DELETE FROM TGFFIN WHERE NUNOTA = ?", cab.asBigDecimalOrZero("NUNOTA"));
                            central.refazerFinanceiro();
                        } finally {
                            banco.closeSession();
                        }
                    }
                }
            }

            // Somente depois de concluir todos os pedidos, itens e financeiros.
            Timestamp executadoEm = new Timestamp(System.currentTimeMillis());
            for (DynamicVO ordem : ordens.values()) {
                ordemDAO.prepareToUpdate(ordem).set("DTALTER", executadoEm).update();
            }
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar empresa/local padrão: " + e.getMessage(), e);
        }
    }

    private static void validarPedido(DynamicVO cab, BigDecimal nuNota) throws Exception {
        if (!"P".equalsIgnoreCase(cab.asString("TIPMOV"))
                || !"S".equalsIgnoreCase(cab.asString("PENDENTE"))) {
            throw new Exception("A nota " + nuNota
                    + " não atende aos critérios. (Tipo de Movimento = 'Pedido de Venda' e Pendente = 'Sim').");
        }
    }
}

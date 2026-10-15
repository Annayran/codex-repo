package tech.skala.events;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;

public class FilaMensagemEvent implements EventoProgramavelJava {
	
	final private Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private String msg;

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		logger.info("FilaMensagemEvent.beforeInsert");

		DynamicVO vo = (DynamicVO) event.getVo();

		BigDecimal codSmtp = vo.asBigDecimal("CODSMTP");
		String tipoDoc = vo.asString("TIPODOC");
		String assunto = vo.asString("ASSUNTO");
		boolean hasBoleto = false;

		if (vo.asString("EMAIL") != null && vo.asInt("CODCON") == 0 && vo.asBigDecimal("CODMSG") == null
				&& "Pendente".equalsIgnoreCase(vo.asString("STATUS")) && "E".equalsIgnoreCase(vo.asString("TIPOENVIO"))
				&& vo.asString("ASSUNTO") != null && "text/html".equalsIgnoreCase(vo.asString("MIMETYPE"))
				&& vo.asBigDecimal("NUCHAVE") != null && "N".equalsIgnoreCase(tipoDoc)
				&& vo.asBigDecimal("CODSMTP") != null) {

			if (assunto.indexOf("Envio da NF-e - N.Doc") < 0)
				return;

			String message = vo.asString("MENSAGEM");
			int start = message.indexOf("<b>Seguem dados da NF-e em anexo.</b><br/><br/>");

			BigDecimal nuChave = vo.asBigDecimal("NUCHAVE");
			String nomeArquivo = String.format("boleto_%s", nuChave);

			/*
			 * Tratamento para assunto - verifica os anexos - verifica smtp para determinar
			 * empresa
			 */
			assunto += " | XML | DANFE";

			JapeWrapper anexoMensagemDAO = JapeFactory.dao(DynamicEntityNames.ANEXO_MENSAGEM);
			DynamicVO anexoMensagemVO = anexoMensagemDAO.findOne("this.NOMEARQUIVO LIKE '?%'", nomeArquivo);

			msg = "Assunto";

			hasBoleto = anexoMensagemVO != null;

			if (hasBoleto) {
				assunto += " | BOLETO";

				StringBuilder aviso = new StringBuilder();
				aviso.append("<b>Seguem dados da NF-e em anexo.</b><br/><br/>");
				aviso.append("<b>**ATENÇÃO EVITE FRAUDE**</b><br>");
				aviso.append(
						"AO EFETUAR O PAGAMENTO DE BOLETO EMITIDO PELA PLATINA COSMÉTICOS OBSERVAR O BENEFICIÁRIO OU SACADOR/AVALISTA, QUE SEMPRE SERÁ A PLATINA.<br>");
				aviso.append(
						"CASO A RAZÃO SOCIAL ESTEJA DIFERENTE <b>NÃO PAGUE</b> E ENTRE EM CONTATO CONOSCO IMEDIATAMENTE.<br><br>");

				StringBuilder newMessageSB = new StringBuilder(message);
				newMessageSB.replace(start, start + 47, aviso.toString());

				// alterar mensagem
				vo.setProperty("MENSAGEM", newMessageSB.toString().toCharArray());

				msg += " e mensagem";
			}

			if (codSmtp.intValue() == 1) {
				assunto += " | MASTER LINE";
			} else if (codSmtp.intValue() == 2) {
				assunto += " | PLATINA COSMETICOS";
			}
			
			String salesOrderNumber = this.getSalesOrderNumber2(nuChave);
			
			if (!"X".equalsIgnoreCase(salesOrderNumber)) {
				assunto += String.format(" | Número do Pedido: %s", salesOrderNumber);
			}

			// alterar assunto
			vo.setProperty("ASSUNTO", assunto);

			msg = String.format("%s para e-mail NF-e alterado(s). NUNOTA %s", msg, nuChave);
			logger.info(msg);
		}

		if ("B".equalsIgnoreCase(tipoDoc)) {

			if (codSmtp.intValue() == 1) {
				assunto = "Boleto(s) MASTER LINE";
			} else if (codSmtp.intValue() == 2) {
				assunto = "Boleto(s) PLATINA COSMETICOS";
			}

			// alterar assunto
			vo.setProperty("ASSUNTO", assunto);

			msg = "Assunto do boleto alterado.";
			logger.info(msg);
		}

	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}
	
	private String getSalesOrderNumber2(BigDecimal invoiceId) {
		String salesOrderNumber2 = "";
		
		try {
			salesOrderNumber2 = NativeSql.getString("COALESCE(NUMPEDIDO2, 'X')", "TGFCAB", "NUNOTA = ?", new Object[] { invoiceId });
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		
		return salesOrderNumber2;
	}

}

package br.com.zaya.like.helper.email;

import java.math.BigDecimal;

import javax.ejb.FinderException;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.email.FilaMsgUtil.Email;

public class EmailHelper {
	
	
	public static void EnviarEmailHelper(Email em ,EntityFacade dwfFacade) throws Exception {
		
		if (em == null || em.getDestinatarios() == null || em.getDestinatarios().isEmpty()) {
			return; // Não há destinatários para enviar o email
		}
		
		
		String emailArr[] = em.getDestinatarios().split("[,;]");
		
		for(String destinatario : emailArr) {
			criarEmailNaFila(dwfFacade, destinatario, em.getAssunto(), em.getMensagem(), em.getContaSMTP());
			
		}
		
	}

	public static DynamicVO getCorpoModelo(BigDecimal codModeloEmail, EntityFacade dwfFacade) throws Exception{
		DynamicVO modeloEmail = getModeloEmailVo(codModeloEmail, dwfFacade);
		return modeloEmail;
	}


	private static DynamicVO getModeloEmailVo(BigDecimal codModeloEmail, EntityFacade dwfFacade) throws Exception {
		DynamicVO modeloEmailVO = null;
		try {
			modeloEmailVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO(DynamicEntityNames.MODELO_EMAIL, new Object[] { codModeloEmail });
		} catch (FinderException e) {
			throw e;
		}
	
		return modeloEmailVO;
	}
	
	private static void criarEmailNaFila(EntityFacade dwfFacade, String destinatario, String assunto, String mensagem, BigDecimal codSMTP) throws Exception {
		DynamicVO filaVO = (DynamicVO) dwfFacade.getDefaultValueObjectInstance(DynamicEntityNames.FILA_MSG);

		filaVO.setProperty("EMAIL", destinatario);
		filaVO.setProperty("CODCON", BigDecimal.ZERO);
		filaVO.setProperty("CODMSG", null);
		filaVO.setProperty("STATUS", "Pendente");
		filaVO.setProperty("TIPOENVIO", "E");
		filaVO.setProperty("MAXTENTENVIO", BigDecimalUtil.valueOf(3));
		filaVO.setProperty("ASSUNTO", assunto);
		filaVO.setProperty("MIMETYPE", "text/html");
		filaVO.setProperty("MENSAGEM", mensagem.toCharArray());
		filaVO.setProperty("TIPODOC", "K"); //Evento
		filaVO.setProperty("CODSMTP", codSMTP);

		dwfFacade.createEntity(DynamicEntityNames.FILA_MSG, (EntityVO) filaVO);
	}


}

package br.com.zaya.like.Actions.services;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class IncluirAcompanhamentoService {

	public static void incluir(Registro linha, BigDecimal nunota, BigDecimal codUsuLogado) throws Exception {

	    BigDecimal sequencia =	(BigDecimal) linha.getCampo("SEQUENCIA");
		String ocorrencia = (String) linha.getCampo("OCORRENCIAS");
		dadosAcompanhamentoNota(nunota, ocorrencia, sequencia);
		
	}
	
	public static void incluir(BigDecimal nunota, BigDecimal sequencia, String ocorrencia, BigDecimal codUsuLogado) throws Exception {
		dadosAcompanhamentoNota(nunota, ocorrencia, sequencia);
		
	}
	
	
	private static void dadosAcompanhamentoNota(BigDecimal nuNota, String ocorrencia, BigDecimal sequencia) throws Exception {
		
		try {
			
			String referencia = BigDecimal.ZERO.equals(sequencia) ? "N" : "R";
			
			long now = System.currentTimeMillis();
			DynamicVO acompNotaVO = (DynamicVO) EntityFacadeFactory.getDWFFacade().getDefaultValueObjectInstance("AcompanhamentoNota");
			
			acompNotaVO.setProperty("SEQUENCIA", sequencia);  
			acompNotaVO.setProperty("NUNOTA", nuNota);
			acompNotaVO.setProperty("DHOCOR", new Timestamp(now));
			acompNotaVO.setProperty("HRACT", TimeUtils.timestamp2BigDecimal(new Timestamp(now)));
			acompNotaVO.setProperty("CODUSU", (BigDecimal) JapeSessionContext.getRequiredProperty("usuario_logado"));
			acompNotaVO.setProperty("REFERENCIA", referencia);
			acompNotaVO.setProperty("OCORRENCIAS", ocorrencia);
			acompNotaVO.setProperty("DIGITADO", "S");
			acompNotaVO.setProperty("AD_ITEMCANCELADO", "S");
			
			EntityFacadeFactory.getDWFFacade().createEntity("AcompanhamentoNota", (EntityVO) acompNotaVO);
		} catch (Exception e) {
			throw new Exception("Erro ao incluir o acompanhamento da nota: " + e.getMessage());
		}
	}




}

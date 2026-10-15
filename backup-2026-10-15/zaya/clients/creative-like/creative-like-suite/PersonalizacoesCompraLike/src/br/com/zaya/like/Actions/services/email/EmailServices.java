package br.com.zaya.like.Actions.services.email;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.DynamicVOPojo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.email.FilaMsgUtil.Email;
import br.com.zaya.like.helper.email.EmailHelper;

public class EmailServices {

	public void enviarEmailAcompanhamento(BigDecimal nunota, BigDecimal sequencia, String ocorrencias, BigDecimal codUsu, EntityFacade dwfFacade) throws Exception {

		try {
			Collection<DynamicVO> acompanhamentos = buscarAcompanhamentos(nunota, codUsu);
			
			
			if (acompanhamentos.isEmpty()) {
				return; // Nenhum acompanhamento encontrado, não enviar email
			}
			
			DynamicVO acompanhamento = acompanhamentos.iterator().next();

			Email em = formatarEmail(acompanhamento, ocorrencias, codUsu, dwfFacade);

			EmailHelper.EnviarEmailHelper(em, dwfFacade);
		} catch (Exception e) {
			throw new Exception("Erro ao enviar email de acompanhamento: " + e.getMessage(), e);
		}

	}

	private Email formatarEmail(DynamicVO acompanhamento, String ocorrencias, BigDecimal codUsu, EntityFacade dwfFacade)
			throws Exception {

		try {
			DynamicVO emailVo = EmailHelper.getCorpoModelo(acompanhamento.asBigDecimal("MODELO"), dwfFacade);

			String assunto = emailVo.asString("ASSUNTO");
			assunto = assunto.replace("${NUNOTA}", acompanhamento.asBigDecimal("NUNOTA").toString());
			assunto = assunto.replace("${CODTIPOPER}", acompanhamento.asBigDecimal("CODTIPOPER").toString());
			assunto = assunto.replace("${DESCROPER}", acompanhamento.asString("DESCROPER"));
			
			String conteudo = emailVo.asString("CONTEUDO");
			conteudo = conteudo.replace("${NOMEUSU}", acompanhamento.asString("REMETENTE"));
			conteudo = conteudo.replace("${MSG}", ocorrencias);
			conteudo = conteudo.replace("${NUNOTA}", acompanhamento.asBigDecimal("NUNOTA").toString());
			conteudo = conteudo.replace("${CODTIPOPER}", acompanhamento.asBigDecimal("CODTIPOPER").toString());
			conteudo = conteudo.replace("${DESCROPER}",acompanhamento.asString("DESCROPER"));
			
			
			String destinatarios = acompanhamento.asString("EMAILDESTINARIO");

			Email em = new Email();
			em.setContaSMTP(acompanhamento.asBigDecimal(""));
			em.setAssunto(assunto);
			em.setMensagem(conteudo);
			em.setDestinatarios(destinatarios);
			em.setCodUsuRemet(codUsu);

			return em;
		} catch (Exception e) {
			throw new Exception("Erro ao formatar o email: " + e.getMessage(), e);
		}
	}

	public Collection<DynamicVO> buscarAcompanhamentos(BigDecimal nunota, BigDecimal codusu) throws Exception {
		Collection<DynamicVO> acompanhamento = new ArrayList<DynamicVO>();
		JdbcWrapper jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();

		ResultSet resultSet = null;
		NativeSql sql = null;

		try {
			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "queBuscaTGFACT.sql");

			sql.setNamedParameter("NUNOTA", nunota);
			sql.setNamedParameter("CODUSU", codusu);

			resultSet = sql.executeQuery();

			DynamicVO actVO = null;
			while (resultSet.next()) {
				actVO = new DynamicVOPojo();
				actVO.setProperty("NUNOTA", nunota);
				actVO.setProperty("REMETENTE", resultSet.getString("REMETENTE"));
				actVO.setProperty("EMAILDESTINARIO", resultSet.getString("EMAILDESTINARIO"));
				actVO.setProperty("MODELO", resultSet.getBigDecimal("AD_MODELOEMAIL"));
				actVO.setProperty("CODTIPOPER", resultSet.getBigDecimal("CODTIPOPER"));
				actVO.setProperty("DESCROPER", resultSet.getString("DESCROPER"));

				acompanhamento.add(actVO);
			}

			return acompanhamento;

		} catch (Exception e) {
			throw new Exception("Erro ao buscar acompanhamento: " + e.getMessage(), e);
		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
	}

}

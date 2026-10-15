package tech.skala.modules.logistica.callcenter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.logistica.callcenter.CallCenterExceptions.CallCenterNaoGeradoException;
import tech.skala.modules.logistica.ordemcarga.OrdemCarga;

/**
 * Classe utilitária contendo métodos para manipulação de dados da classe
 * {@code CallCenter}, que representa a tabela {@code AD_TMSCAL}
 * 
 * @author hiagoalexandre
 * @since 2023-02-17
 */
public class CallCenterProcessor {

	private CallCenterProcessor() {

	}

	/**
	 * Método cuja responsabilidade é criar um registro de Call Center, na tabela
	 * {@code AD_TMSCAL}, com todas as informações presentes no Documento de
	 * Conhecimento de Transportes informado como parâmetro.
	 * 
	 * <b>Observação:</b>
	 * 
	 * O método NÃO permite a inclusão de um novo Call Center, se a CHAVECTE já
	 * existir em um Call Center.
	 * 
	 * @param cteVO Representa uma instância da interface {@code DynamicVO}
	 *              referente à um registro da tabela {@code TGFCAB}.Detalhe, este
	 *              registro deve ser de um lançamento utilizando a TOP 2122.
	 * 
	 * @return Retorna um objeto da classe {@code CallCenter} devidamente criado e
	 *         com todas as informações preenchidas.
	 */
	public static CallCenter criaCallCenterCompleto(DynamicVO cteVO) throws SkalaModelException {
		CallCenter callCenter = null;

		Collection<DynamicVO> callCenterVOs = new ArrayList<>();
		JapeWrapper dao = JapeFactory.dao(SklEntityName.CALL_CENTER);

		try {
			callCenterVOs = dao.find("this.CHAVECTE = ?", cteVO.asString("CHAVECTE"));

			if (callCenterVOs.isEmpty()) {
				callCenter = new CallCenter();
				callCenter.setChaveCte(cteVO.asString("CHAVECTE"));
				callCenter.setNumCte(cteVO.asBigDecimal(SklFieldName.NUMNOTA));
				callCenter.setSerieCte(cteVO.asString("SERIENOTA"));
				callCenter.setCodParcCliente(cteVO.asBigDecimal("CODPARCDEST"));
				callCenter.setCodParcTransp(cteVO.asBigDecimal("CODPARC"));
				callCenter.setQtdReagendas(BigDecimal.ZERO);
				callCenter.persists();
			} else {
				String msg = String.format(
						"Não foi possível importar o CT-e. Já existe pelo menos um Call Center a chave %s.%n",
						cteVO.asString("CHAVECTE"));
				throw new SkalaModelException(msg);
			}

		} catch (Exception e) {
			throw new CallCenterExceptions.CallCenterNaoGeradoException(e);
		}

		return callCenter;

	}

	/**
	 * Método cuja responsabilidade é criar um registro de Call Center, na tabela
	 * {@code AD_TMSCAL}, com APENAS as informações necessárias. Portanto, não
	 * preenche campos referentes à informações do Documento de Conhecimento de
	 * Transportes.
	 * 
	 * <b>Observação:</b>
	 * 
	 * O método NÃO permite a inclusão de um novo Call Center, se a CHAVECTE já
	 * existir em um Call Center.
	 * @param codParcTransp 
	 * 
	 * @param cteVO Representa uma instância da interface {@code DynamicVO}
	 *              referente à um registro da tabela {@code TGFCAB}. Detalhe, este
	 *              registro deve ser de um lançamento utilizando a TOP 2122.
	 * 
	 * @return Retorna um objeto da classe {@code CallCenter} devidamente criado e
	 *         com todas as informações preenchidas.
	 */
	public static CallCenter criaCallCenterBasico(DynamicVO vo, BigDecimal codParcTransp) throws CallCenterNaoGeradoException {
		CallCenter callCenter = null;
		try {
			callCenter = new CallCenter();
			callCenter.setSerieCte("0");
			callCenter.setQtdReagendas(BigDecimal.ZERO);
			callCenter.setCodParcCliente(vo.asBigDecimal(SklFieldName.CODPARC));
			callCenter.setCodParcTransp(codParcTransp);
			callCenter.persists();

		} catch (SkalaModelException e) {
			throw new CallCenterExceptions.CallCenterNaoGeradoException(e);
		}
		return callCenter;
	}

	/**
	 * Método cuja responsabilidade é atualizar um registro existente de Call
	 * Center, na tabela {@code AD_TMSCAL}, com todas as informações presentes no
	 * Documento de Conhecimento de Transportes informado como parâmetro.
	 * 
	 * @param callCenter Representa uma instância da classe {@code CallCenter}
	 * @param cteVO      Representa uma instância da interface {@code DynamicVO}
	 *                   referente à um registro da tabela {@code TGFCAB}. Detalhe,
	 *                   este registro deve ser de um lançamento utilizando a TOP
	 *                   2122.
	 * @throws SkalaModelException
	 */
	public static void atualizaCallCenter(CallCenter callCenter, DynamicVO cteVO) throws SkalaModelException {

		try {
			callCenter.setChaveCte(cteVO.asString("CHAVECTE"));
			callCenter.setNumCte(cteVO.asBigDecimal("NUMNOTA"));
			callCenter.setSerieCte(cteVO.asString("SERIENOTA"));
			callCenter.setCodParcCliente(cteVO.asBigDecimal("CODPARCDEST"));
			callCenter.setCodParcTransp(cteVO.asBigDecimal("CODPARC"));

			callCenter.persists();
		} catch (InsertNotPossibleException | UpdateNotPossibleException e) {
			SkalaModelException.throwMe("Não foi possível atualizar Call Center.", e);
		}
	}

	/**
	 * Método cuja responsabilidade é buscar um Call Center que transporte a NF-e
	 * informada como parâmetro.
	 * 
	 * <p>
	 * Caso o sistema <b>encontre</b> um Call Center que carregue a NF-e informada,
	 * um objeto da Classe {@code Call Center} será retornado. <br>
	 * Caso o sistema <b>não encontre</b> será criado um Call Center com informações
	 * básicas, e a NF-e informada como parâmetro, será relacionada ao mesmo. Após
	 * este processo, o objeto da Classe {@code Call Center} será retornado.
	 * </p>
	 * 
	 * @param nfeVO Representa uma instância da interface {@code DynamicVO}
	 *              referente à um registro da tabela {@code TGFCAB}.
	 * @return callCenter Retorna uma instância da Classe {@code Call Center}
	 * 
	 * @throws SkalaModelException
	 * @see criaCallCenterBasico
	 * @see relacionaNotaCallCenter
	 */
	public static CallCenter buscaCallCenterNoSistema(DynamicVO nfeVO) throws SkalaModelException {

		CallCenter callCenter = null;

		BigDecimal codEmp = nfeVO.asBigDecimal(SklFieldName.CODEMP);
		BigDecimal nuNota = nfeVO.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal ordemCargaNFe = nfeVO.asBigDecimal(SklFieldName.ORDEMCARGA);

		try {
			OrdemCarga ordemCarga = new OrdemCarga();
			ordemCarga.loadByPk(codEmp, ordemCargaNFe);

			BigDecimal codParcTransp = ordemCarga.getCodParcTransp();

			Collection<DynamicVO> callNotasVO = NotasCallCenterDAO.buscarNotasCallCenterPorNuNotaParcTransp(nuNota,
					codParcTransp);

			if (callNotasVO.isEmpty()) {
				callCenter = CallCenterProcessor.criaCallCenterBasico(nfeVO, codParcTransp);
				NotaCallCenterProcessor.relacionaNotaCallCenter(callCenter, nuNota);
			} else {
				BigDecimal codCal = callNotasVO.iterator().next().asBigDecimal(SklFieldName.CODCAL);

				callCenter = new CallCenter();
				callCenter.loadByPk(codCal);
			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível buscar/gerar um Call Center para a NF-e.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

		return callCenter;
	}
	
	public static ResultSet getChavesNFe(String chaveCTe) throws SkalaModelException {

		SessionHandle hnd = null;
		NativeSql sql = null;
		ResultSet rs = null;

		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			hnd = JapeSession.open();
			sql = new NativeSql(jdbc);

			sql.setNamedParameter("CHAVECTE", chaveCTe);

			sql.loadSql(CallCenterProcessor.class, "CallCenterProcessor_queNFePorCTe.sql");

			rs = sql.executeQuery();
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível encontrar encontrar a ChaveNF-E.", e);
		} finally {
			JapeSession.close(hnd);
		}

		return rs;
	}

}

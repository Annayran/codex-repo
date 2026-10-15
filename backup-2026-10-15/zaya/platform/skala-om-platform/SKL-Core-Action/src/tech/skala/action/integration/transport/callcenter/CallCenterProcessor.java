package tech.skala.action.integration.transport.callcenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.integration.transport.CallCenterExceptions;
import tech.skala.action.integration.transport.CallCenterExceptions.CallCenterNaoGeradoException;
import tech.skala.action.integration.transport.ocorrencianaentrega.Entregas;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaDeEntregaProcessor;
import tech.skala.action.modules.centrais.CabecalhoNotaDAO;
import tech.skala.action.modules.logistica.OrdemCargaDAO;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * Classe utilitária contendo métodos para manipulação de dados da classe
 * {@code CallCenter}, que representa a tabela {@code AD_TMSCAL}
 * 
 * @author hiagoalexandre
 * @since 2023-02-17
 */
public class CallCenterProcessor {

	private CallCenterProcessor() {
		throw new IllegalStateException("Utility class");
	}

	private final Logger logger = new LoggerFactory(CallCenterProcessor.class.getCanonicalName()).getLogger();

	/**
	 * Método responsável por processar um arquivo de Conhecimento de Transporte
	 * Eletrônico, CT-e.
	 * 
	 * O Método deve atualizar um Call center, caso as notas transportadas pelo
	 * Cocumento CT-e já constem em um determinado Call Center. Caso não estejam
	 * relacionadas, o método criará um novo Call Center com todas as informações
	 * necessárias.a
	 * 
	 * @param cabVO - representa o registro do lançamento do CT-e (TOP 2122)
	 * @throws SkalaModelException
	 */
	public static void processarCTe(DynamicVO cabVO) throws SkalaModelException {
		DynamicVO cabecalhoNotaVO;

		try {
			String chaveNfe;

			Collection<DynamicVO> nfeVOs = CabecalhoNotaDAO
					.getLancamentosNFeFromChaveCTe(cabVO.asString("CHAVECTE"));

			BigDecimal nuNota;
			Collection<NotaCallCenter> notasCallCenter;
			CallCenter callCenter = null;

			OcorrenciaDeEntregaProcessor entregaProcessor = new OcorrenciaDeEntregaProcessor();
			for (DynamicVO nfeVO : nfeVOs) {
				chaveNfe = nfeVO.asString("CHAVENFE");

				if (!cnpjIguais(chaveNfe))
					return;

				cabecalhoNotaVO = CabecalhoNotaDAO.getCabecalhoFromChaveNFe(chaveNfe);
				/*
				nuNota = cabecalhoNotaVO.asBigDecimal(SklFieldName.NUNOTA);

				notasCallCenter = NotasCallCenterDAO.buscarNotasPorNuNotaEChaveCTeVazia(nuNota);
				if (notasCallCenter.isEmpty()) {

					if (callCenter == null) {
						callCenter = CallCenterProcessor.criaCallCenterCompleto(cabVO);
						OcorrenciaCallCenterProcessor.criarOcorrenciaBase(callCenter);
					}

					NotaCallCenterProcessor.relacionaNotaCallCenter(callCenter, nuNota);

				} else {
					if (callCenter == null) {
						NotaCallCenter notaCallCenter = notasCallCenter.iterator().next();

						callCenter = new CallCenter();
						callCenter.loadByPk(notaCallCenter.getCodCal());
						CallCenterProcessor.atualizaCallCenter(callCenter, cabVO);
					}

					NotaCallCenterProcessor.relacionaNotaCallCenter(callCenter, nuNota);

				}
*/
				BigDecimal nuNotaCTe = cabVO.asBigDecimal(SklFieldName.NUNOTA);

				Entregas entrega = entregaProcessor.buildEntregaOcorrencia(cabecalhoNotaVO);
				
				if(entrega != null)
					entregaProcessor.gravaConhecimentoTransporte(entrega, nuNotaCTe);

			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] - [CTE %s] Não foi possível processar CT-e.",
					cabVO.asBigDecimal(SklFieldName.NUNOTA), cabVO.asString("CHAVECTE"));

			SkalaModelException.throwMe(msg, e);
		}
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
	 * 
	 * @param codParcTransp
	 * 
	 * @param cteVO         Representa uma instância da interface {@code DynamicVO}
	 *                      referente à um registro da tabela {@code TGFCAB}.
	 *                      Detalhe, este registro deve ser de um lançamento
	 *                      utilizando a TOP 2122.
	 * 
	 * @return Retorna um objeto da classe {@code CallCenter} devidamente criado e
	 *         com todas as informações preenchidas.
	 */
	public static CallCenter criaCallCenterBasico(DynamicVO vo, BigDecimal codParcTransp)
			throws CallCenterNaoGeradoException {
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
	public static CallCenter localizaOuCriaNovoCallCenter(DynamicVO nfeVO) throws SkalaModelException {

		CallCenter callCenter = null;

		BigDecimal codEmp = nfeVO.asBigDecimal(SklFieldName.CODEMP);
		BigDecimal nuNota = nfeVO.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal ordemCarga = nfeVO.asBigDecimal(SklFieldName.ORDEMCARGA);

		try {
			BigDecimal codParcTransp = OrdemCargaDAO.findCodParcTranspOrdemCarga(codEmp, ordemCarga);

			callCenter = CallCenterDAO.findCallCenterByNuNotaETransportador(nuNota, codParcTransp);

			if (callCenter == null) {
				callCenter = CallCenterProcessor.criaCallCenterBasico(nfeVO, codParcTransp);
				NotaCallCenterProcessor.relacionaNotaCallCenter(callCenter, nuNota);
			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível buscar/gerar um Call Center para a NF-e.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

		return callCenter;
	}

	/**
	 * Verifica se o CNPJ que consta no documento de CT-e é igual ao CNPJ da Platina
	 * Cosméticos.
	 */
	private static boolean cnpjIguais(String chaveNfe) {
		final String CNPJ_PLATINA = "66288002000136";

		String cnpjCTe = chaveNfe.substring(6, 20);

		return CNPJ_PLATINA.equals(cnpjCTe);
	}

}

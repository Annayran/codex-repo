package tech.skala.modules.vendas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class CabecalhoNotaDAO {

	private CabecalhoNotaDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static DynamicVO buscaProximoLancamentoFaturadoPelaNuNota(BigDecimal nuNota) throws SkalaModelException {

		if (nuNota == null)
			return null;

		SessionHandle hnd = null;
		DynamicVO cabecalhoNFVO = null;

		try {
			hnd = JapeSession.open();

			JapeWrapper varDAO = JapeFactory.dao(DynamicEntityNames.COMPRA_VENDA_VARIOS_PEDIDO);
			JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

			DynamicVO varVO = varDAO.findOne("this.NUNOTAORIG = ?", nuNota);
			BigDecimal nuNotaNFe;

			if (varVO != null) {
				nuNotaNFe = varVO.asBigDecimal(SklFieldName.NUNOTA);
				cabecalhoNFVO = cabDAO.findByPK(nuNotaNFe);
			}
		} catch (Exception e) {
			String msg = String.format(
					"[NUNOTA %s] Não foi possível localizar o próximo lançamento faturado no Lançamento informado.",
					nuNota);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

		return cabecalhoNFVO;
	}

	public static DynamicVO getCabecalhoFromChaveNFe(String chaveNFe) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

		DynamicVO vo = null;
		try {
			vo = dao.findOne("this.CHAVENFE = ?", chaveNFe);

		} catch (Exception e) {
			String msg = String.format("Não foi possível localizar uma nota com a chave <b>%s</b>%n", chaveNFe);
			SkalaModelException.throwMe(msg, e);
		}

		if (vo == null) {
			String msg = String.format("Não existe uma nota com a chave <b>%s</b>", chaveNFe);
			throw new SkalaModelException(msg);
		}

		return vo;
	}

	public static Collection<DynamicVO> getCabecalhoFromChaveCTe(String chaveCTe) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		String msg;

		Collection<DynamicVO> notasList = new ArrayList<>();
		try {
			Collection<DynamicVO> notaVO = dao.find("this.CHAVECTE = ?", chaveCTe);

			if (notaVO.size() > 1) {
				msg = String.format("[CHAVECTE %s] encontrou %s Notas.", chaveCTe, notaVO.size());
				throw new SkalaModelException(msg);
			}

			notasList.addAll(notaVO);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível encontrar encontrar o lançamento do CT-e.", e);
		}

		return notasList;
	}

}

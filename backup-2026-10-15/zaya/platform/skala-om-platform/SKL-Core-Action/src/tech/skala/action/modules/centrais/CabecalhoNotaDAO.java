package tech.skala.action.modules.centrais;

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

	public static CabecalhoNota getCabecalhoPorNumNotaSerieNotaECGC(BigDecimal numNota, BigDecimal serieNota,
			String cgc) throws SkalaModelException {

		JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		JapeWrapper empDAO = JapeFactory.dao(DynamicEntityNames.EMPRESA);

		DynamicVO empVO = null;
		DynamicVO cabVO = null;

		CabecalhoNotaCompra cabecalhoNota = null;
		try {
			cabVO = cabDAO.findOne("this.NUMNOTA = ? AND this.TIPMOV IN ('V') AND this.SERIENOTA = ?", numNota,
					serieNota);

			empVO = empDAO.findOne("this.CGC = ?", cgc);

		} catch (Exception e) {
			SkalaModelException.throwMe("Ocorreu um erro ao buscar as informações.", e);
		}

		if (empVO != null && cabVO != null) {
			if (empVO.asBigDecimal(SklFieldName.CODEMP).equals(cabVO.asBigDecimal(SklFieldName.CODEMP))) {
				cabecalhoNota = new CabecalhoNotaCompra();
				cabecalhoNota.loadByVO(cabVO);
			}

		}

		return cabecalhoNota;
	}

	public static CabecalhoNota getCabecalhoPorNuNota(BigDecimal nuNota) throws SkalaModelException {

		JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

		DynamicVO cabVO = null;
		CabecalhoNotaCompra cabecalhoNota = null;
		try {
			cabVO = cabDAO.findByPK(nuNota);

			cabecalhoNota = new CabecalhoNotaCompra();
			cabecalhoNota.loadByVO(cabVO);

		} catch (Exception e) {
			SkalaModelException.throwMe("Ocorreu um erro ao buscar as informações.", e);
		}

		return cabecalhoNota;
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
//				throw new SkalaModelException(msg);
			}

			DynamicVO vo = notaVO.iterator().next();

			notasList.add(vo);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível encontrar encontrar o lançamento do CT-e.", e);
		}

		return notasList;
	}

	public static Collection<DynamicVO> getLancamentosNFeFromChaveCTe(String chaveCTe) throws SkalaModelException {

		JapeWrapper cteDAO = JapeFactory.dao("AD_PLVGFIXNCTE");
		JapeWrapper nfeDAO = JapeFactory.dao("AD_PLVGFIXNCTENFE");

		Collection<DynamicVO> nfeVOs = new ArrayList<>();
		try {
			DynamicVO cteVO = cteDAO.findOne("this.CHAVECTE = ?", chaveCTe);

			if (cteVO != null)
				nfeVOs.addAll(nfeDAO.find("this.NUARQUIVO = ?", cteVO.asBigDecimal("NUARQUIVO")));

		} catch (Exception e) {
			String msg = String.format("[CHAVECTE %s] Não foi possível buscar as NF-e do CT-e", chaveCTe);
			SkalaModelException.throwMe(msg, e);
		}

		return nfeVOs;
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
}

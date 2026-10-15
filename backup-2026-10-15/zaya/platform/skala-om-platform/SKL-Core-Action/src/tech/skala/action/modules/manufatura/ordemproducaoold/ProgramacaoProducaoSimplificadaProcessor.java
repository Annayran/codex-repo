package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;
import java.util.Collection;

import com.cbmsoft.modelcore.CbmModelException;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;

public class ProgramacaoProducaoSimplificadaProcessor {

	public void atualizarStatusProgramacao(ProgramacaoProducaoSimplificada programacao) throws CbmModelException {
		JapeWrapper dao = JapeFactory.dao(ProgramacaoProducaoSimplificada.ENTITY_NAME);
		
		DynamicVO vo;
		try {
			vo = dao.findByPK(programacao.getNuProgP());
		} catch (Exception e) {
			String errMsg = String.format("[Programação %06d] Não foi possível localizar a programação.%n", programacao.getNuProgP().longValue());
			errMsg = CbmModelException.buildErrorMessage(e, errMsg);
			throw new CbmModelException(errMsg, e);
		}
		FluidUpdateVO updVO = dao.prepareToUpdate(vo);
		updVO.set("STATUS", ProgramacaoProducaoSimplificadaStatusEnum.PROGRAMADA.getValue());
		try {
			updVO.update();
		} catch (Exception e) {
			String errMsg = String.format("[Programação %06d] Não foi possível atualizar o status da programação.%n", programacao.getNuProgP().longValue());
			errMsg = CbmModelException.buildErrorMessage(e, errMsg);
			throw new CbmModelException(errMsg, e);
		}
	}

	public void incluirOrdensDeProducao(ProgramacaoProducaoSimplificada programacao) throws CbmModelException {
		String msg;
		
		try {
			JapeWrapper produtoAcabadoAProduzirDAO = JapeFactory.dao(DynamicEntityNames.PRODUTO_ACABADO_A_PRODUZIR);
			Collection<DynamicVO> produtosAcabadosVO;
			ProgramacaoProducaoSimplificadaOrdemProducao ordemProducao;
			
			for (BigDecimal idiProc : programacao.getOrdensDeProducaoLancadas()) {
				produtosAcabadosVO = produtoAcabadoAProduzirDAO.find("this.IDIPROC = ?", idiProc);	
				for (DynamicVO produtoAcabadoVO:produtosAcabadosVO) {
					ordemProducao = new ProgramacaoProducaoSimplificadaOrdemProducao();
					ordemProducao.setFromProdutoAcabadoAProduzirVO(produtoAcabadoVO);
					ordemProducao.setNuProgP(programacao.getNuProgP());
					ordemProducao.persist();
					
					programacao.getOrdensDeProducao().add(ordemProducao);
				}
			}
		} catch (Exception e) {
			msg = "Não foi possível incluir as Ordens de Produção na Programação.";
			msg = CbmModelException.buildErrorMessage(e, msg);
			throw new CbmModelException(msg,e);
		}
	}
	
	public static void atualizarStatusProc(BigDecimal idiProc, String status) throws CbmModelException {
		String msg;
		
		try {
			JapeWrapper ordemProducaoProgramacaoDAO = JapeFactory.dao("AD_PLTPRPRGPOP");
			DynamicVO ordemProducaoProgramacaoVO = ordemProducaoProgramacaoDAO.findOne("this.IDIPROC = ?", idiProc);
			
			if (ordemProducaoProgramacaoVO == null)
				return;
			
			FluidUpdateVO updVO = ordemProducaoProgramacaoDAO.prepareToUpdate(ordemProducaoProgramacaoVO);
			updVO.set("STATUSPROC", status);
			updVO.update();
			
		} catch (Exception e) {
			msg = "Não foi possível incluir atualizar o Status das Ordens de Produção na Programação.";
			msg = CbmModelException.buildErrorMessage(e, msg);
			throw new CbmModelException(msg,e);
		}
	}
	
}

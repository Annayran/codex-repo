package tech.skala.modules.manufatura.apontamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.manufatura.atividade.InstanciaAtividade;
import tech.skala.modules.manufatura.ordemproducao.OrdemProducao;

/**
 * A classe {@code ApontamentoProducaoFinder} prove recursos para busca de informacoes relacionadas a 
 * uma {@link ApontamentoProducao}.
 * 
 * <p>
 * A classe {@code ApontamentoProducaoFinder} prove metodos diversos especializados em localizar uma {@link ApontamentoProducao} 
 * (ou varias) segundo os mais diversos criterios, sendo uma classe especializada para este fim.
 * </p>
 * 
 * @author hiagoalexandre
 * @since 2022-08-31
 *
 */
public class ApontamentoProducaoFinder {

	/**
	 * O metodo {@code buscaApontamentoPorCaixa} retorna um Apontamento
	 * 
	 * <p>
	 * O método realiza a busca de um determinado apontamento a partir do
	 * Identificador Único de uma Ordem de Produção e pelo Número Único de Produção.
	 * </p>
	 * 
	 * @param nuvOP é o identificador único de um Volume de Produção.
	 * @param ordemProducao é o identificador único de uma Ordem de Produção.
	 *
	 */
	public static ApontamentoProducao buscaApontamentoPorCaixa(OrdemProducao ordemProducao, BigDecimal nuvOP)
			throws SkalaModelException {

		JapeWrapper volumeProducaoDAO = JapeFactory.dao("AD_PLTPRIVOP");

		ApontamentoProducao apontamentoProducao = null;
		DynamicVO volumeProducaoVO = null;
		BigDecimal nuApo;
		try {
			volumeProducaoVO = volumeProducaoDAO.findByPK(ordemProducao.getIdiProc(), nuvOP);
			nuApo = volumeProducaoVO.asBigDecimal("NUAPO");

			apontamentoProducao = new ApontamentoProducao();
			apontamentoProducao.loadByPk(nuApo);

		} catch (Exception e) {
			String msg = String.format(
					"[OP %s] Não foi possível buscar Apontamentos abertos para a Ordem de Producao.%n",
					ordemProducao.getIdiProc());
			SkalaModelException.throwMe(msg, e);
		}

		return apontamentoProducao;
	}
	
	public static List<ApontamentoProducao> buscaApontamentosPendentesPorAtividade(InstanciaAtividade atividade) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_APONTAMENTO);

		Collection<DynamicVO> vos = null;
		List<ApontamentoProducao> apontamentosPendentes = null;
		ApontamentoProducao apontamentoProducao;
		try {
			apontamentosPendentes = new ArrayList<>();
			vos = dao.find("this.IDIATV = ? and this.SITUACAO = 'P'", atividade.getIdiAtv());

			for (DynamicVO vo : vos) {
				apontamentoProducao = new ApontamentoProducao();
				apontamentoProducao.loadByVO(vo);

				apontamentosPendentes.add(apontamentoProducao);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar os apontamentos da atividade.", e);
		}
		
		return apontamentosPendentes;
	}
	
	public static List<ApontamentoProducao> buscaApontamentosPorAtividade(InstanciaAtividade atividade) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_APONTAMENTO);

		Collection<DynamicVO> vos = null;
		List<ApontamentoProducao> apontamentos = null;
		ApontamentoProducao apontamentoProducao;
		try {
			apontamentos = new LinkedList<>();
			vos = dao.find("this.IDIATV = ?", atividade.getIdiAtv());

			for (DynamicVO vo : vos) {
				apontamentoProducao = new ApontamentoProducao();
				apontamentoProducao.loadByVO(vo);

				apontamentos.add(apontamentoProducao);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar os apontamentos da atividade.", e);
		}
		
		return apontamentos;
	}
}

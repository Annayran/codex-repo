package tech.skala.modules.manufatura.atividade;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * A classe {@code ApontamentoProducaoFinder} prove recursos para busca de informacoes relacionadas a 
 * uma {@link InstanciaAtividade}.
 * 
 * <p>
 * A classe {@code ApontamentoProducaoFinder} prove metodos diversos especializados em localizar uma {@link InstanciaAtividade} 
 * (ou varias) segundo os mais diversos criterios, sendo uma classe especializada para este fim.
 * </p>
 * 
 * @author hiagoalexandre
 * @since 2022-08-18
 *
 */
public class InstanciaAtividadeFinder {

	/**
	 * O metodo {@code carregarAtividadesPorIdiProc} retorna uma lista de Instancia
	 * de Atividades de Apontamentos para uma Ordem de Produção
	 * 
	 * 
	 * @param idiProc Identificador de uma Ordem de Produção {@code TPRIPROC}
	 * @return lista de Instancia de Atividades.
	 * @throws SkalaModelException Caso ocorra um erro ao buscar as Atividades por
	 *                             uma determinada Ordem de Produção
	 * 
	 */
	public static List<InstanciaAtividade> buscarAtividadesPorIdiProc(BigDecimal idiProc) throws SkalaModelException {
		JapeWrapper atividadeDAO = JapeFactory.dao(DynamicEntityNames.ATIVIDADE);
		JapeWrapper instanciaAtividadeDAO = JapeFactory.dao(DynamicEntityNames.INSTANCIA_ATIVIDADE);

		InstanciaAtividade instanciaAtividade;
		DynamicVO atividadeVO = null;
		List<InstanciaAtividade> instanciaAtividades = new LinkedList<>();
		try {
			Collection<DynamicVO> vos = instanciaAtividadeDAO.find("this.IDIPROC = ? AND this.IDEXECWFLOW > 0",
					idiProc);
			
			BigDecimal ideFx;
			for (DynamicVO vo : vos) {
				ideFx = vo.asBigDecimal("IDEFX");
				
				atividadeVO = atividadeDAO.findByPK(ideFx);
				
				if("N".equals(atividadeVO.asString("OPERCQ"))) {
					instanciaAtividade = new InstanciaAtividade();
					instanciaAtividade.loadByVO(vo);
					
					instanciaAtividades.add(instanciaAtividade);
				}
			}
		} catch (Exception e) {
			String msg = String.format(
					"[OP %s] Não foi possível carregar as Atividades de Apontamento da Ordem de Produção.%n", idiProc);
			SkalaModelException.throwMe(msg, e);
		}

		return instanciaAtividades;
	}

}

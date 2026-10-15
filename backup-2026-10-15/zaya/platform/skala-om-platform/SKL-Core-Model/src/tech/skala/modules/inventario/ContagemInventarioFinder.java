package tech.skala.modules.inventario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * A classe {@code ContagemInventarioFinder} prove recursos para busca de
 * informacoes relacionadas a uma {@link ContagemInventario}.
 * 
 * <p>
 * A classe {@code ContagemInventarioFinder} prove metodos diversos
 * especializados em localizar um {@link ContagemInventario} (ou varias) segundo
 * os mais diversos criterios, sendo uma classe especializada para este fim.
 * </p>
 * 
 * @author hiagoalexandre
 * @since 2022-11-03
 *
 */
public class ContagemInventarioFinder {

	/**
	 * O metodo {@code tarefasPendentesPorUsuario} retorna uma lista de Tarefas de
	 * contagem pendentes por determinado usuário executante.
	 * 
	 * <p>
	 * O método realiza a busca de tarefas de contagem com o Status Pendente.
	 * </p>
	 * 
	 * @param codExecutante é o identificador do usuário executante.
	 *
	 */
	public static List<ContagemInventario> tarefasPendentesPorUsuario(BigDecimal codExecutante)
			throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(SklEntityName.CONTAGEM_INVENTARIO);
		
		List<ContagemInventario> tarefas = new ArrayList<>();
		Collection<DynamicVO> tarefasVO = null;

		try {
			tarefasVO = dao.find("this.CODEXECUTANTE = ? AND this.STATUS NOT IN ('F', 'C')", codExecutante);
		} catch (Exception e) {
			String msg = String.format("[CODUSU %s] Não foi possível localizar tarefas para o usuário.", codExecutante);
			SkalaModelException.throwMe(msg, e);
		}

		if (tarefasVO.isEmpty()) {
			String msg = "Não há tarefas de contagem pendentes. Favor procurar seu supervisor.";
			throw new SkalaModelException(msg);
		}

		
		ContagemInventario tarefa;
		for (DynamicVO vo : tarefasVO) {
			tarefa = new ContagemInventario();
			tarefa.loadByVO(vo);
			tarefas.add(tarefa);
		}
		
		return tarefas;
	}
	
	/**
	 * O metodo {@code tarefasPendentesPorUsuario} retorna uma lista de Tarefas de
	 * contagem pendentes.
	 * 
	 * <p>
	 * O método realiza a busca de tarefas de contagem com o Status Pendente.
	 * </p>
	 * 
	 */
	public static List<ContagemInventario> tarefasPendentes()
			throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(SklEntityName.CONTAGEM_INVENTARIO);

		List<ContagemInventario> tarefas = new ArrayList<>();
		Collection<DynamicVO> tarefasVO = null;

		try {
			tarefasVO = dao.find("this.STATUS NOT IN ('F', 'C')");
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar tarefas pendentes.", e);
		}

		ContagemInventario tarefa;
		for (DynamicVO vo : tarefasVO) {
			tarefa = new ContagemInventario();
			tarefa.loadByVO(vo);
			tarefas.add(tarefa);
		}

		return tarefas;
	}

}

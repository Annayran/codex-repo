package tech.skala.modules.inventario;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * A classe {@code InventarioFinder} prove recursos para busca de informacoes
 * relacionadas a uma {@link Inventario}.
 * 
 * <p>
 * A classe {@code InventarioFinder} prove metodos diversos especializados em
 * localizar um {@link Inventario} (ou varias) segundo os mais diversos
 * criterios, sendo uma classe especializada para este fim.
 * </p>
 * 
 * @author hiagoalexandre
 * @since 2022-11-03
 *
 */
public class InventarioFinder {

	/**
	 * O metodo {@code buscaInventariosPorUsuarioExecutante} retorna uma lista de Inventários preenchido
	 * 
	 * <p>
	 * O método realiza a busca de um determinado inventário a partir do
	 * Código de Usuário executante.
	 * </p>
	 * 
	 * @param codExecutante é o Código do usuário executante.
	 *
	 */
	public static List<Inventario> buscaInventariosPorUsuarioExecutante(BigDecimal codExecutante)
			throws SkalaModelException {

		Set<Inventario> inventarios = new HashSet<>();
		Inventario inventario = null;
		List<ContagemInventario> tarefas = ContagemInventarioFinder.tarefasPendentesPorUsuario(codExecutante);

		for (ContagemInventario tarefa : tarefas) {
			inventario = new Inventario();
			inventario.loadByPk(tarefa.getIdIvt());
			inventarios.add(inventario);

			ContagemInventarioProcessor.atualizaStatusTarefa(tarefa, ContagemInventarioStatusEnum.SINCRONIZADA);
			tarefa.setProdutos(ProdutoContagemFinder.buscaProdutosContagem(tarefa));
		}

		for (Inventario i : inventarios) {
			List<ContagemInventario> contagens = tarefas.stream().filter(e -> e.getIdIvt().equals(i.getIdIvt()))
					.collect(Collectors.toList());
			i.setContagens(contagens);
		}

		LinkedList<Inventario> list = new LinkedList<>(inventarios);
		Collections.sort(list, Comparator.comparingLong(e -> e.getIdIvt().longValue()));
		return list;
	}
}

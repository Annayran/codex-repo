package tech.skala.modules.manufatura.atividade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.mgeprod.model.helper.OperacaoProducaoHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * A classe {@code ExecucaoInstanciaAtividadeFinder} prove recursos para busca
 * de informacoes relacionadas a uma ExecucaoInstanciaAtividade.
 * 
 * <p>
 * A classe {@code ExecucaoInstanciaAtividadeFinder} prove metodos diversos
 * especializados em localizar uma ExecucaoInstanciaAtividade (ou varias)
 * segundo os mais diversos criterios, sendo uma classe especializada para este
 * fim.
 * </p>
 * 
 * @author hiagoalexandre
 * @since 2022-09-06
 *
 */
public class ExecucaoInstanciaAtividadeFinder {

	public static DynamicVO buscaExecucaoInstanciaAtividadeAtiva(BigDecimal idiAtv, BigDecimal codUsu,
			boolean considerarAcessoGerenteManufatura) throws SkalaModelException {

		DynamicVO vo = null;
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.EXECUCAO_INSTANCIA_ATIVIDADE);
		try {
			OperacaoProducaoHelper helper = OperacaoProducaoHelper.getInstance();

			StringBuilder sbWhere = new StringBuilder("this.IDIATV = ? AND this.DHFINAL IS NULL ");

			Collection<BigDecimal> params = new ArrayList<>();
			params.add(idiAtv);

			if (considerarAcessoGerenteManufatura) {
				if (!helper.isManagerManufatura(codUsu)) {
					sbWhere.append("AND this.CODEXEC = ? ");
					params.add(codUsu);
				}
			} else {
				sbWhere.append("AND this.CODEXEC = ? ");
				params.add(codUsu);
			}

			Collection<DynamicVO> execucoesAbertas = dao.find(sbWhere.toString(), params.toArray());

			if (!execucoesAbertas.isEmpty())
				vo = execucoesAbertas.iterator().next();

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar as Execuções de Atividades.", e);
		}
		return vo;
	}

	public static Collection<DynamicVO> buscaExecucoesAbertas(BigDecimal idiAtv) throws SkalaModelException {

		Collection<DynamicVO> execucoesAbertasVO = new ArrayList<>();
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.EXECUCAO_INSTANCIA_ATIVIDADE);
			execucoesAbertasVO = dao.find("this.IDIATV = ? AND this.DHFINAL IS NULL", idiAtv);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar as Execuções de Atividades Abertas.", e);
		}

		return execucoesAbertasVO;
	}
}

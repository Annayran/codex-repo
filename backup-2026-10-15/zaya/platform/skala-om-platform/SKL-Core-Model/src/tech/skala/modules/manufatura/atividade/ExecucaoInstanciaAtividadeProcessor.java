package tech.skala.modules.manufatura.atividade;

import java.math.BigDecimal;
import java.util.Collection;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.mgeprod.model.helper.OperacaoProducaoHelper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ExecucaoInstanciaAtividadeProcessor {

	public void pararInstanciaAtividade(InstanciaAtividade atividade, BigDecimal codMtp, String observacao)
			throws SkalaModelException {

		try {
			BigDecimal codUsu = AuthenticationInfo.getCurrent().getUserID();

			DynamicVO execucaoVO = buscaExecucaoInstanciaAtividadeAtiva(atividade.getIdiAtv(), codUsu);
			if (execucaoVO == null) {
				Collection<DynamicVO> execucoesAbertasVO = ExecucaoInstanciaAtividadeFinder
						.buscaExecucoesAbertas(atividade.getIdiAtv());

				if (!execucoesAbertasVO.isEmpty()) {
					BigDecimal codExec = execucoesAbertasVO.iterator().next().asBigDecimal("CODEXEC");
					execucaoVO = ExecucaoInstanciaAtividadeFinder
							.buscaExecucaoInstanciaAtividadeAtiva(atividade.getIdiAtv(), codExec, true);
				}
			}

			OperacaoProducaoHelper helper = OperacaoProducaoHelper.getInstance();
			if (execucaoVO != null)
				helper.finalizarExecucaoInsanciaAtividade(execucaoVO.asBigDecimal("IDIATV"), TimeUtils.getNow());

			helper.criarExecucaoInstanciaAtividade(TimeUtils.getNow(), null, codUsu, codUsu, atividade.getIdiAtv(),
					TipoParadaEnum.PARADA.getValue(), codMtp, observacao);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível iniciar parada.", e);
		}

	}

	private DynamicVO buscaExecucaoInstanciaAtividadeAtiva(BigDecimal idiAtv, BigDecimal codUsu) throws SkalaModelException {
		return ExecucaoInstanciaAtividadeFinder.buscaExecucaoInstanciaAtividadeAtiva(idiAtv, codUsu, false);
	}

	public void continuarInstanciaAtividade(InstanciaAtividade atividade) throws SkalaModelException {

		try {
			BigDecimal codUsu = AuthenticationInfo.getCurrent().getUserID();

			DynamicVO execucaoVO = ExecucaoInstanciaAtividadeFinder
					.buscaExecucaoInstanciaAtividadeAtiva(atividade.getIdiAtv(), codUsu, true);
			
			if (execucaoVO == null) {
				Collection<DynamicVO> execucoesAbertasVO = ExecucaoInstanciaAtividadeFinder
						.buscaExecucoesAbertas(atividade.getIdiAtv());
				execucaoVO = execucoesAbertasVO.iterator().next();
			}

			OperacaoProducaoHelper helper = OperacaoProducaoHelper.getInstance();
			if (execucaoVO != null)
				helper.finalizarExecucaoInsanciaAtividade(execucaoVO.asBigDecimal("IDIATV"), TimeUtils.getNow());

			helper.criarExecucaoInstanciaAtividade(TimeUtils.getNow(), null, codUsu, codUsu, atividade.getIdiAtv());
			helper.atribuirUsuarioExecutanteAtividade(atividade.getIdiAtv(), codUsu);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível continuar Instância Atividade.", e);
		}

	}

}

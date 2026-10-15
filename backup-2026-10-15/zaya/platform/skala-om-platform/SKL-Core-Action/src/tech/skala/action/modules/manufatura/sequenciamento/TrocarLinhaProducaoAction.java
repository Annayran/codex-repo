package tech.skala.action.modules.manufatura.sequenciamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.cbmsoft.modelcore.CbmModelException;
import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.ProgramacaoProducaoSimplificada;
import tech.skala.action.modules.manufatura.workcenter.WorkCenter;
import tech.skala.action.modules.manufatura.workcenter.WorkCenterExceptions.WorkCenterNotFoundException;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.extensions.Action;
import tech.skala.modules.manufatura.ordemproducao.OrdemProducaoFinder;

public class TrocarLinhaProducaoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		BigDecimal codWcpDestino = BigDecimalUtil.getBigDecimal(contexto.getParam("CODWCPDESTINO"));
		boolean arredondarLotes = showMensagemDeConfirmacao(contexto);
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			Registro linha = linhas[0];

			BigDecimal seqOP = BigDecimalUtil.getBigDecimal(linha.getCampo("SEQOP"));
			BigDecimal codWcpOrigem = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODWCP));
			
			WorkCenter workCenterOrigem = new WorkCenter();
			workCenterOrigem.loadByPk(codWcpOrigem);

			WorkCenter workCenterDestino = new WorkCenter();
			workCenterDestino.loadByPk(codWcpDestino);

			boolean capacidadePadIgual = workCenterOrigem.getQtdCapacidadePad().longValue() == workCenterDestino.getQtdCapacidadePad().longValue();

			SequenciamentoDeOPFinder seqFinder = new SequenciamentoDeOPFinder();
			Collection<BigDecimal> ordensProducao = seqFinder.findOrdensProducao(seqOP);

			TrocarLinhaProducaoProcessor processor = new TrocarLinhaProducaoProcessor();
			if (capacidadePadIgual) {
				for (BigDecimal idiProc : ordensProducao)
					processor.trocarLinhaProducaoComMesmasCapacidades(idiProc, workCenterOrigem.getCodWcp(), workCenterDestino.getCodWcp());

			} else {
				RoundingMode roundingMode = arredondarLotes ? RoundingMode.CEILING : RoundingMode.DOWN;
				BigDecimal qtdLotes = processor.getQtdTotalAProduzir(linha).divide(workCenterDestino.getQtdCapacidadePad(), roundingMode);

				ProgramacaoProducaoSimplificada programacao = new ProgramacaoProducaoSimplificada(BigDecimal.ONE.negate());
				programacao.loadByRecord(linha);
				programacao.setCodWcp(codWcpDestino);
				programacao.setQtdCapacidadePad(workCenterDestino.getQtdCapacidadePad());
				programacao.setCodProdPA(BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODPRODPA)));
				programacao.setQtdLotes(qtdLotes);

				BigDecimal menorOP = ordensProducao.stream().sorted().collect(Collectors.toList()).get(0);
				String nroLote = OrdemProducaoFinder.findNroLote(menorOP);

				String statusProc;
				processor.trocarControleNumeracaoLote(nroLote, programacao);
				processor.trocarLinhaProducaoComCapacidadesDiferentes(programacao, seqOP);
				for (BigDecimal idiProc : ordensProducao) {
					statusProc = OrdemProducaoFinder.findStatusProc(idiProc);
					processor.cancelarOPs(idiProc, statusProc);
				}
				
			}
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

	private boolean showMensagemDeConfirmacao(ContextoAcao contexto) throws WorkCenterNotFoundException, CbmModelException, RecordNotFoundException {

		Registro[] linhas = contexto.getLinhas();
		if(linhas.length > 1)
			throw new CbmModelException("Favor selecionar um único registro. Todas as OP's serão tratadas.");
		
		String tituloConfirmacao= "Trocar Linha de Produção";
		String mensagemConfirmacao = "%nClique <b>Sim</b> para Criar %s lotes contendo %s unidades, totalizando %s unidades a serem produzidas.%n"
				+ "%nClique <b>Não</b> para Criar %s lotes contendo %s unidades, totalizando %s unidades a serem produzidas.%n";

		BigDecimal codWcpDestino = BigDecimalUtil.getBigDecimal(contexto.getParam("CODWCPDESTINO"));
		TrocarLinhaProducaoProcessor processor = new TrocarLinhaProducaoProcessor();
		List<BigDecimal> informacoes = processor.getInformacoesLote(linhas[0], codWcpDestino);

		return contexto.confirmarSimNao(tituloConfirmacao, 
				String.format(mensagemConfirmacao, informacoes.toArray()), 1);
	}

}
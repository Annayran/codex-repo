package tech.skala.action.modules.wms.extrato;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class GeraExtratoWmsProcessor {
	private final Logger					logger;
	
	private BigDecimal						codProd;
	private BigDecimal						nuNotaEntrada;
	private List<TarefaWms>					tarefasWms;
	
	public GeraExtratoWmsProcessor(BigDecimal codProd, BigDecimal nuNotaEntrada) {
		this.logger = Logger.getLogger(this.getClass().getCanonicalName());
		
		this.codProd = codProd;
		this.nuNotaEntrada = nuNotaEntrada;
		tarefasWms = new LinkedList<>();
	}

	public void gerarExtratoWms() throws SkalaModelException {
		TarefaRecebimentoFinder finder = new TarefaRecebimentoFinder(codProd, nuNotaEntrada);
		List<TarefaWms> tarefasRecebimento = finder.getTarefasRecebimento();

		BigDecimal numNotaRec;
		BigDecimal sequenciaRec;
		BigDecimal nuTarefaInicial;
		BigDecimal codEndInicial;
		BigDecimal qtdVolPad;
		for (TarefaWms tarefaRecebimento:tarefasRecebimento) {
			tarefasWms.add(tarefaRecebimento);
			
			numNotaRec = tarefaRecebimento.getNumNotaRec();
			sequenciaRec = tarefaRecebimento.getSequenciaRec();
			nuTarefaInicial = tarefaRecebimento.getNuTarefa();
			codEndInicial = tarefaRecebimento.getCodEndDestino();
			qtdVolPad = tarefaRecebimento.getQtdVolPad();
			
			logger.info(
					String.format("find numNotaRec %s sequenciaRec %s nuTarefaInicial %s codEndInicial %s qtdVolPad %s",
							numNotaRec, sequenciaRec, nuTarefaInicial, codEndInicial, qtdVolPad));
			
			TarefasFinder tarefasFinder = new TarefasFinder(codProd, numNotaRec, sequenciaRec, nuTarefaInicial, codEndInicial, qtdVolPad);
			tarefasFinder.getProximasTarefas(tarefasWms);
			
			logger.info(String.format("tarefasWms %d", tarefasWms.size()));
		}

		if (!tarefasWms.isEmpty())
			persistirExtratoWms();
	}
	
	private void persistirExtratoWms() throws InsertNotPossibleException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					
					for (TarefaWms tarefaWms:tarefasWms) {
						logger.info(String.format("nuTarefa %s", tarefaWms.getNuTarefa()));
						tarefaWms.persists();
					}
					
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		} finally {
			JapeSession.close(hnd);
		}

	}

}

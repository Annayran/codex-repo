package tech.skala.action.modules.manufatura.sequenciamento;

import java.math.BigDecimal;
import java.util.Collection;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzirFinder;
import tech.skala.action.modules.manufatura.sequenciamento.SequenciamentoDeOPExceptions.SequenciamentoNaoAtualizadoException;
import tech.skala.action.modules.manufatura.sequenciamento.SequenciamentoDeOPExceptions.SequenciamentoNaoInicializadoException;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class SequenciamentoProcessor {
	SequenciamentoDeOP sequencia;

	public SequenciamentoProcessor(SequenciamentoDeOP sequencia) {
		this.sequencia = sequencia;
	}

	public void incluirOPsNoSequenciamento() throws InsertNotPossibleException, SequenciamentoNaoInicializadoException {
		
		if (sequencia == null)
			throw new SequenciamentoDeOPExceptions.SequenciamentoNaoInicializadoException();

		try {
			sequencia.persists();

			Collection<DynamicVO> produtosAcabadosVO;
			SequenciamentoDeOPInstancia instancia;
			BigDecimal prioridade;
			for (BigDecimal idiProc : sequencia.getOrdensDeProducaoLancadas()) {
				produtosAcabadosVO = ProdutoAcabadoAProduzirFinder.findProdutosAcabadosAProduzir(idiProc);

				for (DynamicVO produtoAcabadoVO : produtosAcabadosVO) {
					instancia = new SequenciamentoDeOPInstancia();
					instancia.setSeqOP(sequencia.getSeqOP());
					instancia.setWorkCenter(sequencia.getWorkCenter());
					instancia.setDtProg(sequencia.getDtProg());
					instancia.loadByProdutoAcabadoAProduzirVO(produtoAcabadoVO);
					
					prioridade = BigDecimalUtil.getValue(sequencia.getProgramacao().getPrioridade(), BigDecimal.ONE);
					
					instancia.setPrioridade(prioridade);
					instancia.persists();

					sequencia.getItens().add(instancia);
				}
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
	}

	public void atualizaObservacao(String observacao) throws SequenciamentoNaoAtualizadoException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { sequencia.getSeqOP() });
					EntityUpdate entityUpd = new EntityUpdate(SklEntityName.SEQUENCIAMENTO_OP, pk);
					entityUpd.setField(SklFieldName.OBSERVACAO, observacao);
					entityUpd.fluidUpdate();
				}

			});

		} catch (Exception e) {
			throw new SequenciamentoDeOPExceptions.SequenciamentoNaoAtualizadoException(e, sequencia.getSeqOP());
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
	
}

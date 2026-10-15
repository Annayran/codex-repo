package tech.skala.action.modules.manufatura.sequenciamento;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class SequenciamentoOPInstanciaProcessor {
	
	private Registro[] linhas;
	
	public SequenciamentoOPInstanciaProcessor(Registro[] linhas) {
		this.linhas = linhas;
	}
	
	public void definirConcluido(boolean isConcluido) throws Exception {
		
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					BigDecimal seqOp;
					BigDecimal idiProc;
					EntityPrimaryKey pk;
					for(Registro linha : linhas) {
						seqOp = BigDecimalUtil.getBigDecimal(linha.getCampo("SEQOP"));
						idiProc = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.IDIPROC));
						
						pk = new EntityPrimaryKey(new Object[] { seqOp, idiProc });
						setConcluido(pk, isConcluido);
					}
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possivel alterar campo Concluido.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
	private void setConcluido(EntityPrimaryKey pk, boolean isConcluido) throws SkalaModelException {
		try {
			EntityUpdate entityUpd = new EntityUpdate(SklEntityName.SEQUENCIAMENTO_OP_INSTANCIA, pk);
			entityUpd.setField(SklFieldName.CONCLUIDO, isConcluido ? "S" : "N");
			entityUpd.fluidUpdate();

		} catch (SkalaModelException e) {
			String msg = String.format("[IDIPROC: %s] Não foi possivel alterar campo Concluido.", pk);
			
			SkalaModelException.throwMe(msg, e);
		}
	}
}

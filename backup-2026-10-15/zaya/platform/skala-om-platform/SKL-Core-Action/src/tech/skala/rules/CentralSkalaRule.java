package tech.skala.rules;

import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.modelcore.comercial.AtributosRegras;
import br.com.sankhya.modelcore.comercial.ContextoRegra;
import br.com.sankhya.modelcore.comercial.Regra;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.events.BarramentoRegra;

public class CentralSkalaRule implements Regra {
	private static final long serialVersionUID = 1L;

	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private String msg;

	@Override
	public void afterDelete(ContextoRegra arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(ContextoRegra ctx) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterUpdate(ContextoRegra ctx) throws Exception {
		final boolean isConfirming = JapeSession.getPropertyAsBoolean(AtributosRegras.CONFIRMANDO, false)
				.booleanValue();

		PrePersistEntityState state = ctx.getPrePersistEntityState();
		DynamicVO newVo = state.getNewVO();
		
		final boolean isHeader = newVo.getValueObjectID().indexOf(DynamicEntityNames.CABECALHO_NOTA) > -1;

		if (isConfirming && isHeader) {
			final String newStatusNota = newVo.asString(SklFieldName.STATUSNOTA);

			if ("L".equalsIgnoreCase(newStatusNota)) {
				BarramentoRegra.incluiCicloVida(newVo);
				BarramentoRegra.criaAcompanhamentoDeEntrega(newVo);
			} 
		}
	}

	@Override
	public void beforeDelete(ContextoRegra arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInsert(ContextoRegra arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeUpdate(ContextoRegra ctx) throws Exception {
		// TODO Auto-generated method stub
	}
}

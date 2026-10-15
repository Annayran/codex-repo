package tech.skala.events;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;
import com.sankhya.util.TimeUtils.TipoPeriodo;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class PLTGFREGVENCREvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		Timestamp dtVigor = vo.asTimestamp("DTVIGOR");

		if (dtVigor != null)
			vo.setProperty("DTVIGOR", getReferencia(dtVigor));

		validarCentroResultadoEmRegionaisAtivas(vo);
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		if (event.getModifingFields().isModifing("DTVIGOR")) {
			Timestamp dtVigor = vo.asTimestamp("DTVIGOR");
			if (dtVigor != null)
				vo.setProperty("DTVIGOR", getReferencia(dtVigor));
		}

	}

	private Timestamp getReferencia(Timestamp data) throws Exception {
		final long DTREF_MAIOR_DTREF_ATUAL = 1;

		Timestamp dtRef = TimeUtils.getInicioPeriodo(data, TipoPeriodo.MES);
		Timestamp dtRefAtual = TimeUtils.getInicioPeriodo(TimeUtils.getNow(), TipoPeriodo.MES);

		if (dtRef.compareTo(dtRefAtual) != DTREF_MAIOR_DTREF_ATUAL) {
			String msg = String.format("DTVIGOR[%s] Data Vigor não pode ser menor ou igual a referência atual.",
					TimeUtils.formataDDMMYYYY(dtRef));
			SkalaModelException.throwMe(msg, null);
		}

		return dtRef;
	}

	private void validarCentroResultadoEmRegionaisAtivas(DynamicVO vo) throws Exception {
		String msg;
		StringBuilder where;
		BigDecimal codCenCus = vo.asBigDecimal("CODCENCUS");

		try {
			where = new StringBuilder();
			where.append("CODCENCUS = ? ");
			where.append(
					"AND EXISTS (SELECT 1 FROM AD_PLTGFREGVEN REGV WHERE REGV.CODIGO = CR.CODIGO AND REGV.ATIVO = 'S')");

			BigDecimal qtdRegionaisAtivasQuePossuemCR = NativeSql.getBigDecimal("COUNT(*)", "AD_PLTGFREGVENCR CR",
					where.toString(), new Object[] { codCenCus });

			if (qtdRegionaisAtivasQuePossuemCR.longValue() > 0L) {
				msg = String.format("[CODCENCUS %s] Centro de Resultado já existe em outra Regional ativa.", codCenCus);
				SkalaModelException.throwMe(msg, null);
			}

		} catch (Exception e) {
			msg = "Não foi possível realizar validação de Centro de Resultado.";
			SkalaModelException.throwMe(msg, e);
		}
	}

}

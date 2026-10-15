package tech.skala.events;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

import com.sankhya.util.JdbcUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;

public class DocaEvent implements EventoProgramavelJava {

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
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		NativeSql sql = null;
		ResultSet rs = null;
		try {

			if ("L".equals(vo.asString("SITUACAO")) && "N".equals(vo.asString("BALCAO"))) {

				String msg;
				BigDecimal codEnd;
				Timestamp dtConfirmacao = TimeUtils.getNow();

				codEnd = vo.asBigDecimal("CODEND");

				sql = new NativeSql(event.getJdbcWrapper());

				StringBuilder sbQuery = new StringBuilder();
				// O CAMPO AD_DTCONFIRM, NÃO EXISTE, ANALISAR UM CAMPO VÁLIDO
				// A MARCELA ORIENTOU A BUSCAR PELA DATA DE MOVIMENTO
				sbQuery.append("SELECT MAX (CAB.DHPROTOC) AS DTCONFIRM ");
				sbQuery.append("FROM TGFCAB CAB ");
				sbQuery.append("JOIN TGWSEP SEP ON (CAB.ORDEMCARGA = SEP.ORDEMCARGA) ");
				sbQuery.append("WHERE CAB.TIPMOV = 'V' ");
				sbQuery.append("AND CAB.STATUSNOTA = 'L' ");
				sbQuery.append("AND CAB.STATUSNFE = 'A' ");
				sbQuery.append("AND CAB.DTNEG >= TO_DATE('01/01/2019', 'DD/MM/YYYY')  ");
				sbQuery.append("AND SEP.CODENDDOCA = :CODEND ");

				sql.setNamedParameter("CODEND", codEnd);
				rs = sql.executeQuery(sbQuery.toString());

				while (rs.next())
					dtConfirmacao = rs.getTimestamp("DTCONFIRM");

				Timestamp dtConfirmacaoProxDia = TimeUtils.dataAdd(dtConfirmacao, 1, Calendar.DAY_OF_MONTH);

				if (TimeUtils.getNow().compareTo(dtConfirmacaoProxDia) < 0) {
					msg = String.format(
							"Não foi possível liberar a Doca."
									+ "%nAguarde <b>24 horas</b> após o faturamento da NFe para realizar a liberação.%n"
									+ "%nHora da confirmação da nota: %s" + "%nHora prevista para liberação da Doca: %s"
									+ "%nTempo Restante: <b>%s minuto(s)</b>.",
							TimeUtils.formataDDMMYYYYHHMMSS(dtConfirmacao),
							TimeUtils.formataDDMMYYYYHHMMSS(dtConfirmacaoProxDia),
							TimeUtils.getDifferenceInMinutes(TimeUtils.getNow(), dtConfirmacaoProxDia));

				}
			}

		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
		}

	}

}

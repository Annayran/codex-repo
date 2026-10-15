package br.com.zaya.like.Events;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.ConfirmacaoNotaHelper;

public class ConfimarNotaEvent implements EventoProgramavelJava {

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {

		
	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		
		BigDecimal nuConf = vo.asBigDecimal("NUCONF");
		BigDecimal nuNota = BigDecimalUtil.getValueOrZero(NativeSql.getBigDecimal("NUNOTA", "TGFCAB", "CODTIPOPER = 1259 AND STATUSNOTA <> 'L' AND NUCONFATUAL = ?", new Object[] {nuConf}));
		
		String statusConf = vo.asString("STATUS");
		
		if("F".equals(statusConf) && nuNota.compareTo(BigDecimal.ZERO) !=0) {
			System.out.println("ZAYA >> APTA PARA CONFIRMARÇÃO" + nuConf + " - " + nuNota + " - " +  statusConf);
			try {
				
				confirmarNota(nuNota);
				
			} catch (Exception e) {
			}
		} else {
			
			System.out.println("ZAYA >> Não está apta para confirmar çãoAPTA PARA CONFIRMARÇÃO" + nuConf + " - " + nuNota + " - " +  statusConf);
		}
		
	}

	private void confirmarNota(BigDecimal nuNota) throws Exception {
		br.com.sankhya.modelcore.comercial.BarramentoRegra bRegrasCab = br.com.sankhya.modelcore.comercial.BarramentoRegra.build(br.com.sankhya.modelcore.comercial.CentralFaturamento.class, "regrasConfirmacaoSilenciosa.xml", AuthenticationInfo.getCurrent());
        bRegrasCab.setValidarSilencioso(true);
        ConfirmacaoNotaHelper.confirmarNota(nuNota, bRegrasCab);
	}

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeCommit(TransactionContext tranCtx) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

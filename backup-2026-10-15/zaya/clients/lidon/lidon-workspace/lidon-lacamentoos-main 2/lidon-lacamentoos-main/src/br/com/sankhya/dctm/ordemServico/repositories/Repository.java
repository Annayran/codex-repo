package br.com.sankhya.dctm.ordemServico.repositories;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.sql.NativeSql;

public class Repository {
	
	  public static BigDecimal buscarCodCenCusPadUsuario(BigDecimal codUsu) {
		  
		  try {
			return NativeSql.getBigDecimal("CODCENCUSPAD", "TSIUSU", "CODUSU = ?", new Object[] { codUsu });
		} catch (Exception e) {
			e.getMessage();
		}
		return BigDecimal.ZERO;
	  }
	  
	  
		public static BigDecimal getTipoNegociacaoGarantia(BigDecimal codemp) throws Exception {
			return BigDecimalUtil.getValueOrZero(NativeSql.getBigDecimal("AD_CODTIPVENDA", "TGFEMP", "CODEMP = ? ", new Object[] { codemp }));
		}


		public static String getTipoOs(BigDecimal numOs) throws Exception {
			return NativeSql.getString("TIP.TIPOOS", "AD_TCSOSE OSE INNER JOIN AD_TIPOORDEMSERVICO TIP ON TIP.CODTIPODEOS = OSE.CODTIPODEOS ", "OSE.NUMOS  = ? ", new Object[] { numOs });
		}

}

package tech.skala.action.beans.radarproduto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.sankhya.util.BigDecimalUtil;

public class SklMath {
	
	private SklMath() {
		throw new IllegalStateException("Utility Class");
	}
	public static BigDecimal roundUpByMultiple(BigDecimal number, BigDecimal multiple) {
		if (number == null || multiple == null)
			throw new IllegalArgumentException("Numero ou multiplo não podem ser nulos");
		if (multiple.equals(BigDecimal.ZERO))
			throw new IllegalArgumentException("Multiplo não pode ser igual a zero");
		
		return number.divide(multiple, BigDecimal.ROUND_CEILING).setScale(0, RoundingMode.CEILING).multiply(multiple, BigDecimalUtil.MATH_CTX);
		
		
	}

}

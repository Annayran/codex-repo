package tech.skala.core.util.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SkalaBigDecimalUtils {

	private SkalaBigDecimalUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static BigDecimal truncate(BigDecimal value, int scale) {
		BigDecimal truncatedValue = value.setScale(scale, RoundingMode.DOWN);
		
		if (scale < 0)
			return new BigDecimal(truncatedValue.intValue());
		
		return truncatedValue;
	}

}

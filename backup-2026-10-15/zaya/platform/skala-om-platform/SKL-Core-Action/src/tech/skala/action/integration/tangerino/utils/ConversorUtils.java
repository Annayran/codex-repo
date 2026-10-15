package tech.skala.action.integration.tangerino.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.StringUtils;
import com.sankhya.util.TimeUtils;

public class ConversorUtils {
	private static final Logger					LOGGER = Logger.getLogger("ConversorUtils");

    public static String semAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static BigDecimal processarHorario(BigDecimal horasSankhya, BigDecimal horasAdicionais){
        BigDecimal totalHoras = horasSankhya.add(horasAdicionais);
        if (totalHoras.doubleValue() > 2359){
            totalHoras = totalHoras.subtract(new BigDecimal(2400));
        }
        return totalHoras;
    }
    
    public static BigDecimal horasParaMillis(BigDecimal hora) {
    	String msg = String.format("hora original %s", hora);
    	LOGGER.info(msg);
    	
    	long horaMinutoSegundo = hora.longValue() * 100L;
		BigDecimal shift = BigDecimal.valueOf(horaMinutoSegundo);
    	msg = String.format("hora x 100 %s", shift);
    	LOGGER.info(msg);

		Timestamp shiftTS = null;
		try {
			shiftTS = TimeUtils.bigDecimal2Timestamp(shift);
	    	msg = String.format("shiftTS %s %s", shiftTS, shiftTS.getTime());
	    	LOGGER.info(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(shiftTS.getTime());
    }

    public static BigDecimal converterHoraParaMillesegundos(BigDecimal horaSankhya){
        BigDecimal milliseconds = BigDecimal.ZERO;
        if (StringUtils.isNotEmpty(horaSankhya)){
            System.out.println(Integer.valueOf(horaSankhya.toString()));
            String horaComZeroAEsquerdaSePrecisar = String.format("%04d", Integer.valueOf(horaSankhya.toString()));
            String horasString = horaComZeroAEsquerdaSePrecisar.substring(0, 2);
            String minutosString = horaComZeroAEsquerdaSePrecisar.substring(2, 4);
            int hora = Integer.parseInt(horasString) * 3600000;
            int minuto = Integer.parseInt(minutosString) * 60000;
            milliseconds = BigDecimalUtil.valueOf(hora + minuto);
        }
        return milliseconds;
    }

    public static Long converterTimestampMillesegundos(Timestamp data) throws ParseException {
        return data.getTime();
    }
}

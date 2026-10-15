package br.zaya.telecontrol.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    
    private static final String FORMATO_DATA_BR = "dd/MM/yyyy";
    private static final String FORMATO_DATA_ISO = "yyyy-MM-dd";
    private static final String FORMATO_DATA_HORA = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * Converte string em formato ISO (yyyy-MM-dd) para Date
     */
    public static Date parseIsoDate(String dataStr) throws Exception {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA_ISO);
        return sdf.parse(dataStr);
    }
    
    /**
     * Converte string em formato DD/MM/YYYY para Date
     */
    public static Date parseBrDate(String dataStr) throws Exception {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA_BR);
        return sdf.parse(dataStr);
    }
    
    /**
     * Converte Date para string ISO (yyyy-MM-dd)
     */
    public static String formatIsoDate(Date data) {
        if (data == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA_ISO);
        return sdf.format(data);
    }
    
    /**
     * Converte Date para string BR (dd/MM/yyyy)
     */
    public static String formatBrDate(Date data) {
        if (data == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA_BR);
        return sdf.format(data);
    }
    
    /**
     * Obtém data de ontem
     */
    public static Date getYesterday() {
        Date hoje = new Date();
        return new Date(hoje.getTime() - 86400000); // 86400000 ms = 1 dia
    }
    
    /**
     * Converte Date para string ISO com hora
     */
    public static String formatIsoDateTime(Date data) {
        if (data == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA_HORA);
        return sdf.format(data);
    }
}


package tech.skala.action.integration.tangerino.utils;

import java.math.BigDecimal;

public class FuncaoUtils {
    public static String getIdFuncionarioFormatado(BigDecimal codEmpresa, BigDecimal codFuncionario){
        int codigoEmpresa = Integer.parseInt(String.valueOf(codEmpresa));
        int codigoFuncionario = Integer.parseInt(String.valueOf(codFuncionario));
        String formatarCodigoDaEmpresa = String.format("%04d", codigoEmpresa);
        String formatarCodigoDoFuncionario = String.format("%05d", codigoFuncionario);
        return formatarCodigoDaEmpresa + formatarCodigoDoFuncionario;
    }
}

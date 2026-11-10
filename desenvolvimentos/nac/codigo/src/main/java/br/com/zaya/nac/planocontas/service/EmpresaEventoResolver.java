package br.com.zaya.nac.planocontas.service;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.util.NumeroUtils;
import br.com.zaya.nac.planocontas.util.SysOutLog;
import java.math.BigDecimal;

/** Resolve a empresa contábil para o botão e para o evento automático. */
public final class EmpresaEventoResolver {

    public static final String PARAMETRO_EMPRESA = "ZYNACCODEMPCTA";
    private static final String ENTIDADE_PARAMETRO_SISTEMA = "ParametroSistema";

    public BigDecimal resolver(DynamicVO parceiroVO, String idExecucao) throws Exception {
        return resolver(
                propriedade(parceiroVO, "CODEMP"),
                "TGFPAR.CODEMP",
                idExecucao);
    }

    public BigDecimal resolver(
            Object empresaInformada,
            String origemEmpresa,
            String idExecucao) throws Exception {
        BigDecimal empresaOrigem = numeroPositivo(empresaInformada, origemEmpresa);
        if (empresaOrigem != null) {
            SysOutLog.info(
                    idExecucao,
                    "Empresa " + empresaOrigem + " obtida de " + origemEmpresa + ".");
            return empresaOrigem;
        }

        DynamicVO parametro = JapeFactory.dao(ENTIDADE_PARAMETRO_SISTEMA)
                .findOne(
                        "CHAVE = ? AND CODUSU = ?",
                        PARAMETRO_EMPRESA,
                        BigDecimal.ZERO);
        BigDecimal empresaParametro = numeroPositivo(
                valorParametro(parametro),
                "parâmetro " + PARAMETRO_EMPRESA);
        if (empresaParametro != null) {
            SysOutLog.info(
                    idExecucao,
                    "Empresa " + empresaParametro + " obtida do parâmetro "
                            + PARAMETRO_EMPRESA + ".");
            return empresaParametro;
        }

        throw new RegraNegocioException(
                "Não foi possível determinar a empresa contábil. Informe "
                        + origemEmpresa + " ou configure o parâmetro inteiro "
                        + PARAMETRO_EMPRESA + " como preferência global "
                        + "(CODUSU = 0) na Central de Parâmetros.");
    }

    private Object valorParametro(DynamicVO parametro) {
        if (parametro == null) {
            return null;
        }
        return propriedade(parametro, "INTEIRO");
    }

    private Object propriedade(DynamicVO vo, String campo) {
        return vo != null && vo.containsProperty(campo) ? vo.getProperty(campo) : null;
    }

    private BigDecimal numeroPositivo(Object valor, String campo) throws RegraNegocioException {
        BigDecimal numero = NumeroUtils.paraBigDecimal(valor, campo);
        if (numero == null) {
            return null;
        }
        if (numero.signum() <= 0) {
            throw new RegraNegocioException(
                    "O " + campo + " deve conter um código de empresa maior que zero.");
        }
        return numero;
    }
}

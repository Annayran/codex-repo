package br.com.zaya.nac.planocontas.repository.sankhya;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.model.Parceiro;
import br.com.zaya.nac.planocontas.repository.ParceiroRepository;
import java.math.BigDecimal;

public final class SankhyaParceiroRepository implements ParceiroRepository {

    private static final String ENTIDADE_PARCEIRO = "Parceiro";
    private static final String MOTIVO_LOCK = "PLANO_CONTAS_NAC_PROTECAO_PARCEIRO";
    private static final String CAMPO_PROTECAO_CLIENTE = "AD_ZYNACCTACLI";
    private static final String CAMPO_PROTECAO_FORNECEDOR = "AD_ZYNACCTAFOR";

    @Override
    public Parceiro buscar(BigDecimal codigoParceiro) throws Exception {
        JapeWrapper parceiroDao = dao();
        DynamicVO vo = parceiroDao.findByPK(codigoParceiro);
        if (vo == null) {
            throw new RegraNegocioException(
                    "O parceiro " + codigoParceiro + " não foi encontrado na TGFPAR.");
        }
        vo.lock(MOTIVO_LOCK);
        vo = parceiroDao.findByPK(codigoParceiro);
        return new Parceiro(
                vo.asBigDecimal("CODPARC"),
                vo.asString("NOMEPARC"),
                "S".equals(vo.asString("CLIENTE")),
                "S".equals(vo.asString("FORNECEDOR")),
                vo.asBigDecimal("CODCTACTB2"),
                vo.asBigDecimal("CODCTACTB"),
                checkbox(vo, CAMPO_PROTECAO_CLIENTE),
                checkbox(vo, CAMPO_PROTECAO_FORNECEDOR));
    }

    @Override
    public void vincularConta(
            BigDecimal codigoParceiro,
            String campo,
            BigDecimal codigoConta) throws Exception {
        dao().prepareToUpdateByPK(codigoParceiro)
                .set(campo, codigoConta)
                .set(campoProtecao(campo), "S")
                .update();
    }

    @Override
    public void vincularContaExclusiva(
            BigDecimal codigoParceiro,
            String campo,
            BigDecimal codigoConta) throws Exception {
        String campoIncompativel;
        String protecaoIncompativel;
        if ("CODCTACTB2".equals(campo)) {
            campoIncompativel = "CODCTACTB";
            protecaoIncompativel = CAMPO_PROTECAO_FORNECEDOR;
        } else if ("CODCTACTB".equals(campo)) {
            campoIncompativel = "CODCTACTB2";
            protecaoIncompativel = CAMPO_PROTECAO_CLIENTE;
        } else {
            throw new RegraNegocioException(
                    "Campo de vínculo contábil não suportado: " + campo + ".");
        }

        dao().prepareToUpdateByPK(codigoParceiro)
                .set(campoIncompativel, (Object) null)
                .set(protecaoIncompativel, "N")
                .set(campo, codigoConta)
                .set(campoProtecao(campo), "S")
                .update();
    }

    private String campoProtecao(String campoConta) throws RegraNegocioException {
        if ("CODCTACTB2".equals(campoConta)) {
            return CAMPO_PROTECAO_CLIENTE;
        }
        if ("CODCTACTB".equals(campoConta)) {
            return CAMPO_PROTECAO_FORNECEDOR;
        }
        throw new RegraNegocioException(
                "Campo de vínculo contábil não suportado: " + campoConta + ".");
    }

    private boolean checkbox(DynamicVO vo, String campo) throws RegraNegocioException {
        if (!vo.containsProperty(campo)) {
            throw new RegraNegocioException(
                    "O campo de proteção " + campo
                            + " não foi encontrado no cadastro de Parceiros. "
                            + "Revise a configuração dos campos adicionais antes de executar.");
        }
        return "S".equals(vo.asString(campo));
    }

    private JapeWrapper dao() {
        return JapeFactory.dao(ENTIDADE_PARCEIRO);
    }
}

package br.com.zaya.nac.planocontas.repository.sankhya;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.model.ContaCriada;
import br.com.zaya.nac.planocontas.model.Parceiro;
import br.com.zaya.nac.planocontas.model.RegraContaContabil;
import br.com.zaya.nac.planocontas.repository.PlanoContasRepository;
import br.com.zaya.nac.planocontas.service.DescricaoContaService;
import br.com.zaya.nac.planocontas.service.SequenciaContaService;
import br.com.zaya.nac.planocontas.util.NumeroUtils;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

public final class SankhyaPlanoContasRepository implements PlanoContasRepository {

    private static final String ENTIDADE_EMPRESA_CONTABILIDADE = "EmpresaContabilidade";
    private static final String ENTIDADE_PLANO_CONTA = "PlanoConta";
    private static final String ENTIDADE_CONTA_CONTABIL_REFERENCIAL =
            "ContaContabilReferencial";
    private static final String MOTIVO_LOCK = "PLANO_CONTAS_NAC_GERACAO_SEQUENCIAL";

    private final SequenciaContaService sequenciaService;
    private final DescricaoContaService descricaoContaService;

    public SankhyaPlanoContasRepository(SequenciaContaService sequenciaService) {
        this.sequenciaService = sequenciaService;
        this.descricaoContaService = new DescricaoContaService();
    }

    @Override
    public BigDecimal buscarEmpresaPlano(BigDecimal codigoEmpresa) throws Exception {
        DynamicVO empresa = JapeFactory.dao(ENTIDADE_EMPRESA_CONTABILIDADE)
                .findByPK(codigoEmpresa);
        if (empresa == null) {
            throw new RegraNegocioException(
                    "A empresa " + codigoEmpresa
                            + " não possui configuração na Contabilidade do Sankhya.");
        }
        BigDecimal empresaPlano = NumeroUtils.paraBigDecimal(
                empresa.getProperty("CODEMPPLACTA"),
                "TCBEMP.CODEMPPLACTA");
        if (empresaPlano == null) {
            throw new RegraNegocioException(
                    "A empresa " + codigoEmpresa
                            + " não possui CODEMPPLACTA configurado.");
        }
        return empresaPlano;
    }

    @Override
    public ContaCriada criarConta(
            BigDecimal empresaPlano,
            BigDecimal usuario,
            Parceiro parceiro,
            RegraContaContabil regra) throws Exception {
        JapeWrapper planoDao = JapeFactory.dao(ENTIDADE_PLANO_CONTA);
        DynamicVO contaPai = planoDao.findOne(
                "CODEMP = ? AND CTACTB = ?",
                empresaPlano,
                regra.getContaPai());

        validarContaPai(contaPai, regra);
        contaPai.lock(MOTIVO_LOCK);

        String prefixo = sequenciaService.extrairPrefixo(regra.getContaPai());
        String ultimaConta = buscarUltimaConta(planoDao, empresaPlano, prefixo);
        String novaConta = sequenciaService.proxima(regra.getContaPai(), ultimaConta);

        DynamicVO contaCriada = planoDao.create()
                .set("CODEMP", empresaPlano)
                .set("CTACTB", novaConta)
                .set("DESCRCTA", descricaoConta(parceiro))
                .set("CODCTACTBPAI", contaPai.asBigDecimal("CODCTACTB"))
                .set("GRAU", contaPai.asBigDecimal("GRAU").add(BigDecimal.ONE))
                .set("ANALITICA", "S")
                .set("ATIVA", "S")
                .set("CODGRUPOCTA", regra.getGrupoConta())
                .set("DTINCLUSAO", new Timestamp(System.currentTimeMillis()))
                .set("CODUSU", usuario)
                .set("CENCUSOBRIG", valorOuPadrao(contaPai, "CENCUSOBRIG", "N"))
                .set("PROJOBRIG", valorOuPadrao(contaPai, "PROJOBRIG", "N"))
                .save();

        BigDecimal codigoConta = contaCriada.asBigDecimal("CODCTACTB");
        vincularContaReferencial(empresaPlano, codigoConta, regra.getContaReferencial());
        return new ContaCriada(codigoConta, novaConta);
    }

    private void validarContaPai(
            DynamicVO contaPai,
            RegraContaContabil regra) throws RegraNegocioException {
        if (contaPai == null) {
            throw new RegraNegocioException(
                    "A conta sintética " + regra.getContaPai()
                            + " não foi encontrada no plano da empresa.");
        }
        if ("S".equals(contaPai.asString("ANALITICA"))) {
            throw new RegraNegocioException(
                    "A conta " + regra.getContaPai()
                            + " está marcada como analítica e não pode receber contas filhas.");
        }
        if (!"S".equals(contaPai.asString("ATIVA"))) {
            throw new RegraNegocioException(
                    "A conta sintética " + regra.getContaPai() + " está inativa.");
        }
    }

    private String buscarUltimaConta(
            JapeWrapper planoDao,
            BigDecimal empresaPlano,
            String prefixo) throws Exception {
        Collection<DynamicVO> contas = planoDao.findAndOrderBy(
                "CODEMP = ? AND CTACTB LIKE ? AND ANALITICA = 'S'",
                "CTACTB DESC",
                empresaPlano,
                prefixo + "%");
        Iterator<DynamicVO> iterator = contas.iterator();
        return iterator.hasNext() ? iterator.next().asString("CTACTB") : null;
    }

    private void vincularContaReferencial(
            BigDecimal empresaPlano,
            BigDecimal codigoConta,
            String contaReferencial) throws Exception {
        DynamicVO empresa = JapeFactory.dao(ENTIDADE_EMPRESA_CONTABILIDADE)
                .findByPK(empresaPlano);
        if (empresa == null) {
            throw new RegraNegocioException(
                    "Não foi possível identificar o Tipo/Instituição do plano referencial "
                            + "para a empresa do plano " + empresaPlano + ".");
        }
        BigDecimal codigoInstituicao = NumeroUtils.paraBigDecimal(
                empresa.getProperty("CODINSTRESP"),
                "TCBEMP.CODINSTRESP");
        if (codigoInstituicao == null) {
            throw new RegraNegocioException(
                    "A empresa do plano " + empresaPlano
                            + " não possui CODINSTRESP configurado.");
        }
        JapeFactory.dao(ENTIDADE_CONTA_CONTABIL_REFERENCIAL)
                .create()
                .set("CODCTACTB", codigoConta)
                .set("TIPO", codigoInstituicao)
                .set("CODCTAREF", contaReferencial)
                .save();
    }

    private Object valorOuPadrao(DynamicVO vo, String campo, Object padrao) {
        return vo.containsProperty(campo) && vo.getProperty(campo) != null
                ? vo.getProperty(campo)
                : padrao;
    }

    private String descricaoConta(Parceiro parceiro) {
        return descricaoContaService.gerar(parceiro);
    }
}

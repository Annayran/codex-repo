package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import tech.skala.core.commons.dd.DTO;

public class MutuarioAlteracaoDTO implements DTO {
	private BigDecimal							idMutuario;
	private String								identificadorExterno;
	private String								matricula;
	private String								numeroIdentificacaoSocial;
	private Timestamp							dataAdmissao;
	private PessoaDTO							pessoa;
	private CentroCustoIdDTO					centroCusto;
	private LotacaoIdDTO						lotacao;
	private CargoIdDTO							cargo;
	private RegimeContratacaoIdDTO				regimeContratacao;
	private String								registraPonto;
	private String								registraPontoOffline;
	private String								registraPontoPeloAplicativo;
	private String								registraPontoPeloNavegador;
	private String								registraPontoPeloNavegadorMobile;
	private String								registraPontoPeloToten;
	private String								validaReconhecimentoFacial;
	private String								ehPlantonista;
	private String								validarSpoofing;
	
	public MutuarioAlteracaoDTO() {
		this.registraPonto = "SIM";
		this.registraPontoOffline = "NAO";
		this.registraPontoPeloAplicativo = "NAO";
		this.registraPontoPeloNavegador = "NAO";
		this.registraPontoPeloNavegadorMobile = "NAO";
		this.registraPontoPeloToten = "NAO";
		this.validaReconhecimentoFacial = "NAO";
		this.ehPlantonista = "NAO";
		this.validarSpoofing = "NAO";
	}
	public BigDecimal getIdMutuario() {
		return idMutuario;
	}
	public void setIdMutuario(BigDecimal idMutuario) {
		this.idMutuario = idMutuario;
	}
	public String getIdentificadorExterno() {
		return identificadorExterno;
	}
	public void setIdentificadorExterno(String identificadorExterno) {
		this.identificadorExterno = identificadorExterno;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNumeroIdentificacaoSocial() {
		return numeroIdentificacaoSocial;
	}
	public void setNumeroIdentificacaoSocial(String numeroIdentificacaoSocial) {
		this.numeroIdentificacaoSocial = numeroIdentificacaoSocial;
	}
	public Timestamp getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(Timestamp dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	public PessoaDTO getPessoa() {
		return pessoa;
	}
	public void setPessoa(PessoaDTO pessoa) {
		this.pessoa = pessoa;
	}
	public CentroCustoIdDTO getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(CentroCustoIdDTO centroCusto) {
		this.centroCusto = centroCusto;
	}
	public LotacaoIdDTO getLotacao() {
		return lotacao;
	}
	public void setLotacao(LotacaoIdDTO lotacao) {
		this.lotacao = lotacao;
	}
	public CargoIdDTO getCargo() {
		return cargo;
	}
	public void setCargo(CargoIdDTO cargo) {
		this.cargo = cargo;
	}
	public RegimeContratacaoIdDTO getRegimeContratacao() {
		return regimeContratacao;
	}
	public void setRegimeContratacao(RegimeContratacaoIdDTO regimeContratacao) {
		this.regimeContratacao = regimeContratacao;
	}
	public String getRegistraPonto() {
		return registraPonto;
	}
	public void setRegistraPonto(String registraPonto) {
		this.registraPonto = registraPonto;
	}
	public String getRegistraPontoOffline() {
		return registraPontoOffline;
	}
	public void setRegistraPontoOffline(String registraPontoOffline) {
		this.registraPontoOffline = registraPontoOffline;
	}
	public String getRegistraPontoPeloAplicativo() {
		return registraPontoPeloAplicativo;
	}
	public void setRegistraPontoPeloAplicativo(String registraPontoPeloAplicativo) {
		this.registraPontoPeloAplicativo = registraPontoPeloAplicativo;
	}
	public String getRegistraPontoPeloNavegador() {
		return registraPontoPeloNavegador;
	}
	public void setRegistraPontoPeloNavegador(String registraPontoPeloNavegador) {
		this.registraPontoPeloNavegador = registraPontoPeloNavegador;
	}
	public String getRegistraPontoPeloNavegadorMobile() {
		return registraPontoPeloNavegadorMobile;
	}
	public void setRegistraPontoPeloNavegadorMobile(String registraPontoPeloNavegadorMobile) {
		this.registraPontoPeloNavegadorMobile = registraPontoPeloNavegadorMobile;
	}
	public String getRegistraPontoPeloToten() {
		return registraPontoPeloToten;
	}
	public void setRegistraPontoPeloToten(String registraPontoPeloToten) {
		this.registraPontoPeloToten = registraPontoPeloToten;
	}
	public String getValidaReconhecimentoFacial() {
		return validaReconhecimentoFacial;
	}
	public void setValidaReconhecimentoFacial(String validaReconhecimentoFacial) {
		this.validaReconhecimentoFacial = validaReconhecimentoFacial;
	}
	public String getEhPlantonista() {
		return ehPlantonista;
	}
	public void setEhPlantonista(String ehPlantonista) {
		this.ehPlantonista = ehPlantonista;
	}
	public String getValidarSpoofing() {
		return validarSpoofing;
	}
	public void setValidarSpoofing(String validarSpoofing) {
		this.validarSpoofing = validarSpoofing;
	}
	
	@Override
	public String toString() {
		return String.format(
				"MutuarioAlteracaoDTO [idMutuario=%s, identificadorExterno=%s, matricula=%s, numeroIdentificacaoSocial=%s, dataAdmissao=%s, pessoa=%s, centroCusto=%s, lotacao=%s, cargo=%s, regimeContratacao=%s, registraPonto=%s, registraPontoOffline=%s, registraPontoPeloAplicativo=%s, registraPontoPeloNavegador=%s, registraPontoPeloNavegadorMobile=%s, registraPontoPeloToten=%s, validaReconhecimentoFacial=%s, ehPlantonista=%s, validarSpoofing=%s]",
				idMutuario, identificadorExterno, matricula, numeroIdentificacaoSocial, dataAdmissao, pessoa,
				centroCusto, lotacao, cargo, regimeContratacao, registraPonto, registraPontoOffline,
				registraPontoPeloAplicativo, registraPontoPeloNavegador, registraPontoPeloNavegadorMobile,
				registraPontoPeloToten, validaReconhecimentoFacial, ehPlantonista, validarSpoofing);
	}
	
}

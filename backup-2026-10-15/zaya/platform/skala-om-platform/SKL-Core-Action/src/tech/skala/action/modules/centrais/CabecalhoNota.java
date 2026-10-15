package tech.skala.action.modules.centrais;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.comercial.vendas.ComercialHelper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * A classe {@code CabecalhoNota} representa a tabela {@code TGFCAB}. Devera ser
 * extendida em uma classe expecífica para ser utilizada.
 * <p>
 * 
 * @author hiagoalexandre
 * @since 2022-09-21
 */
public class CabecalhoNota implements EntityBean {
	protected static final String ENTITY_NAME = DynamicEntityNames.CABECALHO_NOTA;

	protected BigDecimal codEmp;
	protected BigDecimal nuNota;
	protected BigDecimal numNota;
	protected String serieNota;
	protected BigDecimal codParc;
	protected BigDecimal codParcTransp;
	protected BigDecimal codTipOper;
	protected Timestamp dhTipOper;
	protected BigDecimal codTipVenda;
	protected Timestamp dhTipVenda;
	protected String tipMov;
	protected Timestamp dtNeg;
	protected Timestamp dtFatur;
	protected BigDecimal codNat;
	protected BigDecimal codCencus;
	protected String statusNota;
	protected String pendente;
	protected String chaveNFe;
	protected BigDecimal ordemCarga;
	protected BigDecimal ordemCargaPai;
	private List<ItemNota> itensNota = new LinkedList<>();
	protected boolean isConfirmada;
	private Timestamp dtComercial;
	private Timestamp dhAgendamento;

	private BigDecimal codEmpNegoc;

	private BigDecimal codProj;

	private BigDecimal codVend;

	private BigDecimal codMoeda;

	private TipoCarteira tipoCarteira;

	private CabecalhoNotaSkl cabecalhoNotaSkl;

	private String comEx;

	private static final String QUERY_ITENS_CARTEIRA = "CarteiraPedidosProcessor_queItens.sql";
	private static final BigDecimal CODEMPCOMERCIAL = new BigDecimal(2);
	private static final BigDecimal CODLOCALCOMERCIAL = new BigDecimal(200100);
	private static final BigDecimal CODGRUPOPRODVENDAINI = new BigDecimal(10000);
	private static final BigDecimal CODGRUPOPRODVENDAFIN = new BigDecimal(19899);
	private static final List<String> SUBTIPO_EXPORTACAO_LIST = Arrays.asList("EE", "RE", "PE", "VE", "DE");

	public CabecalhoNotaSkl getCabecalhoNotaSkl() {
		return cabecalhoNotaSkl;
	}

	public void setCabecalhoNotaSkl(CabecalhoNotaSkl cabecalhoNotaSkl) {
		this.cabecalhoNotaSkl = cabecalhoNotaSkl;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getNumNota() {
		return numNota;
	}

	public void setNumNota(BigDecimal numNota) {
		this.numNota = numNota;
	}

	public String getSerieNota() {
		return serieNota;
	}

	public void setSerieNota(String serieNota) {
		this.serieNota = serieNota;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public BigDecimal getCodParcTransp() {
		return codParcTransp;
	}

	public void setCodParcTransp(BigDecimal codParcTransp) {
		this.codParcTransp = codParcTransp;
	}

	public BigDecimal getCodTipOper() {
		return codTipOper;
	}

	public void setCodTipOper(BigDecimal codTipOper) {
		this.codTipOper = codTipOper;
	}

	public Timestamp getDhTipOper() {
		return dhTipOper;
	}

	public void setDhTipOper(Timestamp dhTipOper) {
		this.dhTipOper = dhTipOper;
	}

	public BigDecimal getCodTipVenda() {
		return codTipVenda;
	}

	public void setCodTipVenda(BigDecimal codTipVenda) {
		this.codTipVenda = codTipVenda;
	}

	public Timestamp getDhTipVenda() {
		return dhTipVenda;
	}

	public void setDhTipVenda(Timestamp dhTipVenda) {
		this.dhTipVenda = dhTipVenda;
	}

	public String getTipMov() {
		return tipMov;
	}

	public void setTipMov(String tipMov) {
		this.tipMov = tipMov;
	}

	public Timestamp getDtNeg() {
		return dtNeg;
	}

	public void setDtNeg(Timestamp dtNeg) {
		this.dtNeg = dtNeg;
	}

	public Timestamp getDtFatur() {
		return dtFatur;
	}

	public void setDtFatur(Timestamp dtFatur) {
		this.dtFatur = dtFatur;
	}

	public BigDecimal getCodNat() {
		return codNat;
	}

	public void setCodNat(BigDecimal codNat) {
		this.codNat = codNat;
	}

	public BigDecimal getCodCencus() {
		return codCencus;
	}

	public void setCodCencus(BigDecimal codCencus) {
		this.codCencus = codCencus;
	}

	public String getStatusNota() {
		return statusNota;
	}

	public void setStatusNota(String statusNota) {
		this.statusNota = statusNota;
	}

	public String getPendente() {
		return pendente;
	}

	public void setPendente(String pendente) {
		this.pendente = pendente;
	}

	public String getChaveNFe() {
		return chaveNFe;
	}

	public void setChaveNFe(String chaveNFe) {
		this.chaveNFe = chaveNFe;
	}

	public BigDecimal getOrdemCarga() {
		return ordemCarga;
	}

	public void setOrdemCarga(BigDecimal ordemCarga) {
		this.ordemCarga = ordemCarga;
	}

	public BigDecimal getOrdemCargaPai() {
		return ordemCargaPai;
	}

	public void setOrdemCargaPai(BigDecimal ordemCargaPai) {
		this.ordemCargaPai = ordemCargaPai;
	}

	public List<ItemNota> getItensNota() {
		return itensNota;
	}

	public void setItensNota(List<ItemNota> itensNota) {
		this.itensNota = itensNota;
	}

	public boolean isConfirmada() {
		return isConfirmada;
	}

	public void setConfirmada(boolean isConfirmada) {
		this.isConfirmada = isConfirmada;
	}

	public TipoCarteira getTipoCarteira() {
		return tipoCarteira;
	}

	public void setTipoCarteira(TipoCarteira tipoCarteira) {
		this.tipoCarteira = tipoCarteira;
	}

	public String getComEx() {
		return comEx;
	}

	public Timestamp getDtComercial() {
		return dtComercial;
	}

	public void setDtComercial(Timestamp dtComercial) {
		this.dtComercial = dtComercial;
	}

	public Timestamp getDhAgendamento() {
		return dhAgendamento;
	}

	public void setDhAgendamento(Timestamp dhAgendamento) {
		this.dhAgendamento = dhAgendamento;
	}

	public BigDecimal getCodEmpNegoc() {
		return codEmpNegoc;
	}

	public void setCodEmpNegoc(BigDecimal codEmpNegoc) {
		this.codEmpNegoc = codEmpNegoc;
	}

	public BigDecimal getCodProj() {
		return codProj;
	}

	public void setCodProj(BigDecimal codProj) {
		this.codProj = codProj;
	}

	public BigDecimal getCodVend() {
		return codVend;
	}

	public void setCodVend(BigDecimal codVend) {
		this.codVend = codVend;
	}

	public BigDecimal getCodMoeda() {
		return codMoeda;
	}

	public void setCodMoeda(BigDecimal codMoeda) {
		this.codMoeda = codMoeda;
	}

	public static BigDecimal getCodlocalcomercial() {
		return CODLOCALCOMERCIAL;
	}

	public static BigDecimal getCodempcomercial() {
		return CODEMPCOMERCIAL;
	}

	public static BigDecimal getCodgrupoprodvendafin() {
		return CODGRUPOPRODVENDAFIN;
	}

	public static BigDecimal getCodgrupoprodvendaini() {
		return CODGRUPOPRODVENDAINI;
	}

	private void loadItens() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		NativeSql queItens = null;
		ResultSet rsItens = null;

		ItemNota item;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			queItens = new NativeSql(jdbc);

			queItens.loadSql(getClass(), QUERY_ITENS_CARTEIRA);
			queItens.setNamedParameter(SklFieldName.NUNOTA, nuNota);

			try {
				rsItens = queItens.executeQuery();
				while (rsItens.next()) {
					item = new ItemNota();
					item.setFromItemNotaRS(rsItens);
					itensNota.add(item);
				}

			} finally {
				JdbcUtils.closeResultSet(rsItens);
			}

		} catch (Exception e) {
			// SkalaModelException.throwMe, e);
		} finally {
			NativeSql.releaseResources(queItens);

			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public void setFromCabecalhoNotaRS(ResultSet rsCabecalho) throws SkalaModelException {
		try {
			this.codEmp = rsCabecalho.getBigDecimal(SklFieldName.CODEMP);
			this.setCodEmpNegoc(rsCabecalho.getBigDecimal("CODEMPNEGOC"));
			this.nuNota = rsCabecalho.getBigDecimal(SklFieldName.NUNOTA);
			this.numNota = rsCabecalho.getBigDecimal(SklFieldName.NUMNOTA);
			this.serieNota = rsCabecalho.getString("SERIENOTA");
			this.codParc = rsCabecalho.getBigDecimal(SklFieldName.CODPARC);
			this.codTipOper = rsCabecalho.getBigDecimal(SklFieldName.CODTIPOPER);
			this.dhTipOper = rsCabecalho.getTimestamp("DHTIPOPER");
			this.codTipVenda = rsCabecalho.getBigDecimal("CODTIPVENDA");
			this.dhTipVenda = rsCabecalho.getTimestamp("DHTIPVENDA");
			this.tipMov = rsCabecalho.getString("TIPMOV");
			this.dtNeg = rsCabecalho.getTimestamp("DTNEG");
			this.dtFatur = rsCabecalho.getTimestamp("DTFATUR");
			this.codNat = rsCabecalho.getBigDecimal("CODNAT");
			this.codCencus = rsCabecalho.getBigDecimal("CODCENCUS");
			this.setCodProj(rsCabecalho.getBigDecimal("CODPROJ"));
			this.setCodVend(rsCabecalho.getBigDecimal(SklFieldName.CODVEND));
			this.setCodMoeda(rsCabecalho.getBigDecimal("CODMOEDA"));
			this.statusNota = rsCabecalho.getString(SklFieldName.STATUSNOTA);
			this.pendente = rsCabecalho.getString(SklFieldName.PENDENTE);
			this.tipoCarteira = ComercialHelper.getTipoCarteiraFromTOP(codTipOper);
			this.cabecalhoNotaSkl = ComercialHelper.getCabecalhoNotaFromNota(nuNota);

			if (tipoCarteira == null) {
				this.comEx = "N";
			} else {
				this.comEx = (SUBTIPO_EXPORTACAO_LIST.contains(tipoCarteira.getTipo())) ? "S" : "N";
			}

			// loadTipoNegociacao();

			loadItens();

		} catch (Exception e) {
			String msg = "Não foi possível carregar os itens da Carteira.";
			SkalaModelException.throwMe(msg, e);
		}
	}

}

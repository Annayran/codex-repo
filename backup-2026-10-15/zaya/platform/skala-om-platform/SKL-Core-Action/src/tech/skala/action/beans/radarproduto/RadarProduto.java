package tech.skala.action.beans.radarproduto;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.beans.demanda.DemandaItem;
import tech.skala.action.beans.meta.MetaPorProduto;
import tech.skala.action.beans.produto.ProdutoComercial;
import tech.skala.action.beans.produto.ProdutoMeta;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class RadarProduto implements EntityBean, LoadableByValueObject, Persistable{
	
	private Timestamp						referencia;
	private BigDecimal						semana;
	private ProdutoMeta						produto;
	private BigDecimal						estoqueIni = BigDecimal.ZERO;
	private BigDecimal						estoque = BigDecimal.ZERO;
	private BigDecimal						reservado;
	private BigDecimal						wmsBloqueado;
	private BigDecimal						estoqueDisp = BigDecimal.ZERO;
	private BigDecimal						qtdPendenteFam = BigDecimal.ZERO;
	private BigDecimal						qtdPendente = BigDecimal.ZERO;
	private BigDecimal						qtdPendAtu = BigDecimal.ZERO;
	private BigDecimal						qtdPedidos = BigDecimal.ZERO;
	private BigDecimal						vlrUnit;
	private BigDecimal						vlrTot;
	private BigDecimal						vlrDesc;
	private BigDecimal						vlrLiq;
	private BigDecimal						vlrPendente;
	private BigDecimal						vlrPendAtu;
	private BigDecimal						qtdMedia = BigDecimal.ZERO;
	private BigDecimal						qtdPrev = BigDecimal.ZERO;
	private BigDecimal						qtdFat;
	private BigDecimal						percFat;
	private BigDecimal						qtdPrevProx = BigDecimal.ZERO;
	private BigDecimal						estoqueFinal = BigDecimal.ZERO;
	private BigDecimal						qtdDem;
	private BigDecimal						qtdDemPad;
	private BigDecimal						qtdExped;
	private BigDecimal						qtdDemSld;
	private BigDecimal						percExped;
	private List<RadarProdutoBOM>			listaMateriais;

	public RadarProduto(Timestamp referencia, BigDecimal semana, ProdutoMeta produto) {
		this.referencia = referencia;
		this.semana = semana;
		this.produto = produto;
		this.listaMateriais = new LinkedList<>();
	}

	public Timestamp getReferencia() {
		return referencia;
	}

	public void setReferencia(Timestamp referencia) {
		this.referencia = referencia;
	}

	public BigDecimal getSemana() {
		return semana;
	}

	public void setSemana(BigDecimal semana) {
		this.semana = semana;
	}

	public ProdutoMeta getProduto() {
		return produto;
	}

	public BigDecimal getCodFam() {
		return produto.getFamilia().getCodFam();
	}

	public void setProduto(ProdutoComercial produto) {
		this.produto = produto;
	}

	public BigDecimal getEstoqueIni() {
		return estoqueIni;
	}

	public void setEstoqueIni(BigDecimal estoqueIni) {
		this.estoqueIni = estoqueIni;
	}

	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getReservado() {
		return reservado;
	}

	public void setReservado(BigDecimal reservado) {
		this.reservado = reservado;
	}

	public BigDecimal getWmsBloqueado() {
		return wmsBloqueado;
	}

	public void setWmsBloqueado(BigDecimal wmsBloqueado) {
		this.wmsBloqueado = wmsBloqueado;
	}

	public BigDecimal getEstoqueDisp() {
		return estoqueDisp;
	}

	public void setEstoqueDisp(BigDecimal estoqueDisp) {
		this.estoqueDisp = estoqueDisp;
	}

	public BigDecimal getQtdPendenteFam() {
		return qtdPendenteFam;
	}

	public void setQtdPendenteFam(BigDecimal qtdPendenteFam) {
		this.qtdPendenteFam = qtdPendenteFam;
	}

	public BigDecimal getQtdPendente() {
		return qtdPendente;
	}

	public void setQtdPendente(BigDecimal qtdPendente) {
		this.qtdPendente = qtdPendente;
	}

	public BigDecimal getQtdPendAtu() {
		return qtdPendAtu;
	}

	public void setQtdPendAtu(BigDecimal qtdPendAtu) {
		this.qtdPendAtu = qtdPendAtu;
	}

	public BigDecimal getQtdPedidos() {
		return qtdPedidos;
	}

	public void setQtdPedidos(BigDecimal qtdPedidos) {
		this.qtdPedidos = qtdPedidos;
	}

	public BigDecimal getVlrUnit() {
		return vlrUnit;
	}

	public void setVlrUnit(BigDecimal vlrUnit) {
		this.vlrUnit = vlrUnit;
	}

	public BigDecimal getVlrTot() {
		return vlrTot;
	}

	public void setVlrTot(BigDecimal vlrTot) {
		this.vlrTot = vlrTot;
	}

	public BigDecimal getVlrDesc() {
		return vlrDesc;
	}

	public void setVlrDesc(BigDecimal vlrDesc) {
		this.vlrDesc = vlrDesc;
	}

	public BigDecimal getVlrLiq() {
		return vlrLiq;
	}

	public void setVlrLiq(BigDecimal vlrLiq) {
		this.vlrLiq = vlrLiq;
	}

	public BigDecimal getVlrPendente() {
		return vlrPendente;
	}

	public void setVlrPendente(BigDecimal vlrPendente) {
		this.vlrPendente = vlrPendente;
	}

	public BigDecimal getVlrPendAtu() {
		return vlrPendAtu;
	}

	public void setVlrPendAtu(BigDecimal vlrPendAtu) {
		this.vlrPendAtu = vlrPendAtu;
	}

	public BigDecimal getQtdMedia() {
		return qtdMedia;
	}

	public void setQtdMedia(BigDecimal qtdMedia) {
		this.qtdMedia = qtdMedia;
	}

	public BigDecimal getQtdPrev() {
		return qtdPrev;
	}

	public void setQtdPrev(BigDecimal qtdPrev) {
		this.qtdPrev = qtdPrev;
	}

	public BigDecimal getQtdFat() {
		return qtdFat;
	}

	public void setQtdFat(BigDecimal qtdFat) {
		this.qtdFat = qtdFat;
	}

	public BigDecimal getPercFat() {
		return percFat;
	}

	public void setPercFat(BigDecimal percFat) {
		this.percFat = percFat;
	}

	public BigDecimal getQtdPrevProx() {
		return qtdPrevProx;
	}

	public void setQtdPrevProx(BigDecimal qtdPrevProx) {
		this.qtdPrevProx = qtdPrevProx;
	}

	public BigDecimal getEstoqueFinal() {
		return estoqueFinal;
	}

	public void setEstoqueFinal(BigDecimal estoqueFinal) {
		this.estoqueFinal = estoqueFinal;
	}

	public BigDecimal getQtdDem() {
		return qtdDem;
	}

	public void setQtdDem(BigDecimal qtdDem) {
		this.qtdDem = qtdDem;
	}

	public BigDecimal getQtdDemPad() {
		return qtdDemPad;
	}

	public void setQtdDemPad(BigDecimal qtdDemPad) {
		this.qtdDemPad = qtdDemPad;
	}

	public BigDecimal getQtdExped() {
		return qtdExped;
	}

	public void setQtdExped(BigDecimal qtdExped) {
		this.qtdExped = qtdExped;
	}

	public BigDecimal getQtdDemSld() {
		return qtdDemSld;
	}

	public void setQtdDemSld(BigDecimal qtdDemSld) {
		this.qtdDemSld = qtdDemSld;
	}

	public BigDecimal getPercExped() {
		return percExped;
	}

	public void setPercExped(BigDecimal percExped) {
		this.percExped = percExped;
	}

	public BigDecimal calcularQtdDemPad() {
		BigDecimal tamLotePad = this.produto.getFamilia().getTamLotePad();
		return (tamLotePad.longValue() == 0L) ? this.qtdDem : SklMath.roundUpByMultiple(this.qtdDem, tamLotePad);
	}

	public List<RadarProdutoBOM> getListaMateriais() {
		return listaMateriais;
	}

	public void setListaMateriais(List<RadarProdutoBOM> listaMateriais) {
		this.listaMateriais = listaMateriais;
	}

	public void loadByMeta(MetaPorProduto metaPorProduto) {
		this.estoqueIni = metaPorProduto.getEstoqueIni();
		this.estoque = BigDecimal.ZERO;
		this.reservado = BigDecimal.ZERO;
		this.wmsBloqueado = BigDecimal.ZERO;
		this.estoqueDisp = BigDecimal.ZERO;
		this.qtdPendenteFam = BigDecimal.ZERO;
		this.qtdPendente = metaPorProduto.getQtdPendente();
		this.qtdPendAtu = metaPorProduto.getQtdPendAtu();
		this.qtdPedidos = BigDecimal.ZERO;
		this.vlrUnit = metaPorProduto.getVlrUnit();
		this.vlrTot = BigDecimal.ZERO;
		this.vlrDesc = BigDecimal.ZERO;
		this.vlrLiq = BigDecimal.ZERO;
		this.vlrPendente = metaPorProduto.getVlrPendente();
		this.vlrPendAtu = metaPorProduto.getVlrPendAtu();
		this.qtdMedia = metaPorProduto.getQtdMedia();
		this.qtdPrev = metaPorProduto.getQtdPrev();
		this.qtdFat = BigDecimal.ZERO;
		this.percFat = BigDecimal.ZERO;
		this.qtdPrevProx = BigDecimal.ZERO;
		this.estoqueFinal = metaPorProduto.getEstoqueFinal();
		this.qtdDem = BigDecimal.ZERO;
		this.qtdDemPad = BigDecimal.ZERO;
		this.qtdExped = BigDecimal.ZERO;
		this.qtdDemSld = BigDecimal.ZERO;
		this.percExped = BigDecimal.ZERO;
		

	}

//	public void loadByDemandaItemBean(DemandaItem itemDemanda, MetaPorProduto metaPorProduto) throws SkalaModelException {
//		this.estoqueIni = metaPorProduto.getEstoqueIni();
//		this.estoque = itemDemanda.getEstoque();
//		this.reservado = itemDemanda.getReservado();
//		this.wmsBloqueado = itemDemanda.getWmsBloqueado();
//		this.estoqueDisp = itemDemanda.getDisponivel();
//		this.qtdPendenteFam = BigDecimal.ZERO;
//		this.qtdPendente = itemDemanda.getQtdCarteiraInicial();
//		this.qtdPendAtu = metaPorProduto.getQtdPendAtu();
//		this.qtdPedidos = BigDecimal.ZERO;
//		this.vlrUnit = itemDemanda.getVlrUnit();
//		this.vlrTot = itemDemanda.getVlrTot();
//		this.vlrDesc = itemDemanda.getVlrDesc();
//		this.vlrLiq = itemDemanda.getVlrLiq();
//		this.vlrPendente = itemDemanda.getVlrPendente();
//		this.vlrPendAtu = metaPorProduto.getVlrPendAtu();
//		this.qtdMedia = itemDemanda.getQtdMediaProd();
//		this.qtdPrev = metaPorProduto.getQtdPrev();
//		this.qtdFat = BigDecimal.ZERO;
//		this.percFat = BigDecimal.ZERO;
//		this.qtdPrevProx = BigDecimal.ZERO;
//		this.estoqueFinal = metaPorProduto.getEstoqueFinal();
//		this.qtdDem = BigDecimal.ZERO;
//		this.qtdDemPad = BigDecimal.ZERO;
//		this.qtdExped = BigDecimal.ZERO;
//		this.qtdDemSld = BigDecimal.ZERO;
//		this.percExped = BigDecimal.ZERO;
//	}

	public void buildListaMateriais() throws SkalaModelException {
		JdbcWrapper jdbc = null;
		NativeSql queFormula = null;
		ResultSet rsFormula = null;
		
		BigDecimal codProdMP = BigDecimal.ZERO;
		BigDecimal qtdMistura = BigDecimal.ZERO;
		BigDecimal qtdDemanda = BigDecimal.ZERO;
		BigDecimal cusSemIcm = BigDecimal.ZERO;
		ProdutoMeta materiaPrima;
		RadarProdutoBOM radarProdutoBom;
		String codVolMP;
		
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			queFormula = new NativeSql(jdbc);
			queFormula.loadSql(getClass(), "RadarProduto_queFormula.sql");
			queFormula.setNamedParameter("CODPRODPA", this.produto.getCodProd());
			
			rsFormula = queFormula.executeQuery();
			while (rsFormula.next() ) {
				codProdMP = rsFormula.getBigDecimal("CODPRODMP");
				qtdMistura = rsFormula.getBigDecimal("QTDMISTURA");
				qtdDemanda = this.qtdDemSld.multiply(qtdMistura, BigDecimalUtil.MATH_CTX);
				codVolMP = rsFormula.getString("CODVOLMP");
				cusSemIcm = rsFormula.getBigDecimal("CUS_MP");
				
				materiaPrima = new ProdutoComercial(codProdMP);
				materiaPrima.loadByPk(codProdMP);
				
				radarProdutoBom = new RadarProdutoBOM(this.referencia, this.semana, this.produto, materiaPrima, codVolMP);
				radarProdutoBom.setQtdMistura(qtdMistura);
				radarProdutoBom.setQtdDemanda(qtdDemanda);
				radarProdutoBom.setCusSemIcm(cusSemIcm);
				
				this.listaMateriais.add(radarProdutoBom);
				}
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível carregar matérias primas.", e);
			
		} finally {
			JdbcUtils.closeResultSet(rsFormula);
			NativeSql.releaseResources(queFormula);
			JdbcWrapper.closeSession(jdbc);
		}
		
		

	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.estoqueIni = vo.asBigDecimal(SklFieldName.ESTOQUE_INI);
		this.estoque = vo.asBigDecimal(SklFieldName.ESTOQUE);
		this.reservado = vo.asBigDecimal(SklFieldName.RESERVADO);
		this.wmsBloqueado = vo.asBigDecimal(SklFieldName.WMSBLOQUEADO);
		this.estoqueDisp = vo.asBigDecimal(SklFieldName.ESTOQUEDISP);
		this.qtdPendenteFam = vo.asBigDecimal(SklFieldName.QTDPENDENTEFAM);
		this.qtdPendente = vo.asBigDecimal(SklFieldName.QTDPENDENTE);
		this.qtdPendAtu = vo.asBigDecimal(SklFieldName.QTDPENDATU);
		this.qtdPedidos = vo.asBigDecimal(SklFieldName.QTDPEDIDOS);
		this.vlrUnit = vo.asBigDecimal(SklFieldName.VLRUNIT);
		this.vlrTot = vo.asBigDecimal(SklFieldName.VLRTOT);
		this.vlrDesc = vo.asBigDecimal(SklFieldName.VLRDESC);
		this.vlrLiq = vo.asBigDecimal(SklFieldName.VLRLIQ);
		this.vlrPendente = vo.asBigDecimal(SklFieldName.VLRPENDENTE);
		this.vlrPendAtu = vo.asBigDecimal(SklFieldName.VLRPENDATU);
		this.qtdMedia = vo.asBigDecimal(SklFieldName.QTDMEDIA);
		this.qtdPrev = vo.asBigDecimal(SklFieldName.QTDPREV);
		this.qtdFat = vo.asBigDecimal(SklFieldName.QTDFAT);
		this.percFat = vo.asBigDecimal(SklFieldName.PERCFAT);
		this.qtdPrevProx = vo.asBigDecimal(SklFieldName.QTDPREVPROX);
		this.estoqueFinal = vo.asBigDecimal(SklFieldName.ESTOQUE_FINAL);
		this.qtdDem = vo.asBigDecimal(SklFieldName.QTDDEM);
		this.qtdDemPad = vo.asBigDecimal(SklFieldName.QTDDEMPAD);
		this.qtdExped = vo.asBigDecimal(SklFieldName.QTDEXPED);
		this.qtdDemSld = vo.asBigDecimal(SklFieldName.QTDDEMSLD);
		this.percExped = vo.asBigDecimal(SklFieldName.PERCEXPED);
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGRADAR");

		try {
			DynamicVO vo = dao.findByPK(referencia, semana, produto.getCodGProd());

			if (vo == null) {
				EntityInsert insert = new EntityInsert("AD_PLTGRADAR");

				insert.setField("REFERENCIA", referencia);
				insert.setField("SEMANA", semana);
				insert.setField("CODPROD", produto.getCodGProd());
				insert.setField("CODFAM", produto.getFamilia().getCodFam());
				insert.setField("ORDEM", produto.getFamilia().getOrdem());
				insert.setField("MARCA", produto.getMarca());
				insert.setField("ESTOQUE_INI", estoqueIni);
				insert.setField("ESTOQUE", estoque);
				insert.setField("RESERVADO", reservado);
				insert.setField("WMSBLOQUEADO", wmsBloqueado);
				insert.setField("ESTOQUEDISP", estoqueDisp);
				insert.setField("QTDPENDENTEFAM", qtdPendenteFam);
				insert.setField("QTDPENDENTE", qtdPendente);
				insert.setField("QTDPENDATU", qtdPendAtu);
				insert.setField("QTDPEDIDOS", qtdPedidos);
				insert.setField("VLRUNIT", vlrUnit);
				insert.setField("VLRTOT", vlrTot);
				insert.setField("VLRDESC", vlrDesc);
				insert.setField("VLRLIQ", vlrLiq);
				insert.setField("VLRPENDENTE", vlrPendente);
				insert.setField("VLRPENDATU", vlrPendAtu);
				insert.setField("QTDMEDIA", qtdMedia);
				insert.setField("QTDPREV", qtdPrev);
				insert.setField("QTDFAT", qtdFat);
				insert.setField("PERCFAT", percFat);
				insert.setField("QTDPREVPROX", qtdPrevProx);
				insert.setField("ESTOQUE_FINAL", estoqueFinal);
				insert.setField("QTDDDEM", qtdDem);
				insert.setField("QTDEMPAD", qtdDemPad);
				insert.setField("QTDEXPED", qtdExped);
				insert.setField("QTDDEMSLD", qtdDemSld);
				insert.setField("PERCEXPED", percExped);
				insert.setField("CODPROD", produto.getCodGProd());
				insert.setField("CODPRODD", produto.getCodGProdDepartamento());
				insert.setField("CODPRODS", produto.getCodGProdSecao());
				insert.fluidInsert();
			} else {
				EntityUpdate update = new EntityUpdate("AD_PLTGRADAR", vo);
				update.setField("ESTOQUE_INI", estoqueIni);
				update.setField("ESTOQUE", estoque);
				update.setField("RESERVADO", reservado);
				update.setField("WMSBLOQUEADO", wmsBloqueado);
				update.setField("ESTOQUEDISP", estoqueDisp);
				update.setField("QTDPENDENTEFAM", qtdPendenteFam);
				update.setField("QTDPENDENTE", qtdPendente);
				update.setField("QTDPENDATU", qtdPendAtu);
				update.setField("QTDPEDIDOS", qtdPedidos);
				update.setField("VLRUNIT", vlrUnit);
				update.setField("VLRTOT", vlrTot);
				update.setField("VLRDESC", vlrDesc);
				update.setField("VLRLIQ", vlrLiq);
				update.setField("VLRPENDENTE", vlrPendente);
				update.setField("VLRPENDATU", vlrPendAtu);
				update.setField("QTDMEDIA", qtdMedia);
				update.setField("QTDPREV", qtdPrev);
				update.setField("QTDFAT", qtdFat);
				update.setField("PERCFAT", percFat);
				update.setField("QTDPREVPROX", qtdPrevProx);
				update.setField("ESTOQUE_FINAL", estoqueFinal);
				update.setField("QTDDEM", qtdDem);
				update.setField("QTDDEMPAD", qtdDemPad);
				update.setField("QTDEXPED", qtdExped);
				update.setField("QTDDEMSLD", qtdDemSld);
				update.setField("PERCEXPED", percExped);
				update.fluidUpdate();
			}

			for (RadarProdutoBOM mp : listaMateriais) {
				mp.persists();
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"RadarProduto [referencia=%s, semana=%s, produto=%s, estoqueIni=%s, estoque=%s, reservado=%s, wmsBloqueado=%s, estoqueDisp=%s, qtdPendenteFam=%s, qtdPendente=%s, qtdPendAtu=%s, qtdPedidos=%s, vlrUnit=%s, vlrTot=%s, vlrDesc=%s, vlrLiq=%s, vlrPendente=%s, vlrPendAtu=%s, qtdMedia=%s, qtdPrev=%s, qtdFat=%s, percFat=%s, qtdPrevProx=%s, estoqueFinal=%s, qtdDem=%s, qtdDemPad=%s, qtdExped=%s, qtdDemSld=%s, percExped=%s, listaMateriais=%s]",
				referencia, semana, produto, estoqueIni, estoque, reservado, wmsBloqueado, estoqueDisp, qtdPendenteFam,
				qtdPendente, qtdPendAtu, qtdPedidos, vlrUnit, vlrTot, vlrDesc, vlrLiq, vlrPendente, vlrPendAtu,
				qtdMedia, qtdPrev, qtdFat, percFat, qtdPrevProx, estoqueFinal, qtdDem, qtdDemPad, qtdExped, qtdDemSld,
				percExped, listaMateriais);
	}

	public void setFromDemandaItemBean(DemandaItem itemDemanda, MetaPorProduto metaPorProduto) throws SkalaModelException {
		this.estoqueIni = metaPorProduto.getEstoqueIni();
		this.estoque = itemDemanda.getEstoque();
		this.reservado = itemDemanda.getReservado();
		this.wmsBloqueado = itemDemanda.getWmsBloqueado();
		this.estoqueDisp = itemDemanda.getDisponivel();
		this.qtdPendenteFam = BigDecimal.ZERO;
		this.qtdPendente = itemDemanda.getQtdCarteiraInicial();
		this.qtdPendAtu = metaPorProduto.getQtdPendAtu();
		this.qtdPedidos = BigDecimal.ZERO;
		this.vlrUnit = itemDemanda.getVlrUnit();
		this.vlrTot = itemDemanda.getVlrTot();
		this.vlrDesc = itemDemanda.getVlrDesc();
		this.vlrLiq = itemDemanda.getVlrLiq();
		this.vlrPendente = itemDemanda.getVlrPendente();
		this.vlrPendAtu = metaPorProduto.getVlrPendAtu();
		this.qtdMedia = itemDemanda.getQtdMediaProd();
		this.qtdPrev = metaPorProduto.getQtdPrev();
		this.qtdFat = BigDecimal.ZERO;
		this.percFat = BigDecimal.ZERO;
		this.qtdPrevProx = BigDecimal.ZERO;
		this.estoqueFinal = metaPorProduto.getEstoqueFinal();
		this.qtdDem = BigDecimal.ZERO;
		this.qtdDemPad = BigDecimal.ZERO;
		this.qtdExped = BigDecimal.ZERO;
		this.qtdDemSld = BigDecimal.ZERO;
		this.percExped = BigDecimal.ZERO;
	}

}

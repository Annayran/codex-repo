package tech.skala.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.produto.ProdutoExceptions.AlternativeVolumeNotFoundException;
import tech.skala.core.beans.produto.ProdutoVenda;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.modules.manufatura.atividade.InstanciaAtividade;
import tech.skala.modules.manufatura.workcenter.WorkCenter;

/**
 * A classe {@code OrdemProducao} representa a tabela {@code TPRIPROC}.
 *  
 * <p>
 * A classe {@code OrdemProducao} eh uma classe concreta que podera ser utilizada em 
 * diversas rotinas no sistema.
 * </p> 
 * 
 * @author marcos.damasceno
 * @author cassiomenezes
 * @author hiagoalexandre
 * @since 2022-07-13
 */
public class OrdemProducao implements EntityBean, LoadableByValueObject, LoadableByResultSet, LoadableByPrimaryKey {
	public static final String				ENTITY_NAME = DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO;
	
	private BigDecimal						idiProc;
	private BigDecimal 						idProc;
	private String 							nroLote;
	private ProdutoVenda					produtoVenda;
	private String 							controlePa;
	private Timestamp						dhInicio;
	private Timestamp						dhFinal;
	private String 							statusProc;
	private BigDecimal 						qtdProduzir;
	private WorkCenter						workCenter;
	private List<VolumeProducao>			etiquetas;
	private List<InstanciaAtividade> 		atividades;
	
	public OrdemProducao(BigDecimal idiProc) {
		this.idiProc = idiProc;
		this.etiquetas = new LinkedList<>();
		this.atividades = new LinkedList<>();
	}
	
	public BigDecimal getIdiProc() {
		return idiProc;
	}

	public BigDecimal getIdProc() {
		return idProc;
	}

	public void setIdProc(BigDecimal idProc) {
		this.idProc = idProc;
	}

	public String getNroLote() {
		return nroLote;
	}

	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}

	public ProdutoVenda getProdutoVenda() {
		return produtoVenda;
	}

	public void setProdutoVenda(ProdutoVenda produtoVenda) {
		this.produtoVenda = produtoVenda;
	}

	public BigDecimal getCodProdPa() {
		return produtoVenda.getCodProd();
	}

	public String getDescrProdPa() {
		return produtoVenda.getDescrProd();
	}

	public String getControlePa() {
		return controlePa;
	}

	public void setControlePa(String controlePa) {
		this.controlePa = controlePa;
	}

	public Timestamp getDhInicio() {
		return dhInicio;
	}

	public void setDhInicio(Timestamp dhInicio) {
		this.dhInicio = dhInicio;
	}

	public Timestamp getDhFinal() {
		return dhFinal;
	}

	public void setDhFinal(Timestamp dhFinal) {
		this.dhFinal = dhFinal;
	}

	public String getStatusProc() {
		return statusProc;
	}

	public void setStatusProc(String statusProc) {
		this.statusProc = statusProc;
	}

	public BigDecimal getQtdProduzir() {
		return qtdProduzir;
	}

	public void setQtdProduzir(BigDecimal qtdProduzir) {
		this.qtdProduzir = qtdProduzir;
	}
	
	public WorkCenter getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(WorkCenter workCenter) {
		this.workCenter = workCenter;
	}

	public List<VolumeProducao> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<VolumeProducao> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public List<InstanciaAtividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<InstanciaAtividade> atividades) {
		this.atividades = atividades;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		try {
			if (idiProc == null)
				this.idiProc = vo.asBigDecimal(SklFieldName.IDIPROC);
			
			this.idProc = vo.asBigDecimal(SklFieldName.IDPROC);
			this.nroLote = vo.asString(SklFieldName.NROLOTE);
			
			loadProdutoVenda(vo.asBigDecimal(SklFieldName.CODPRODPA));
			
			this.controlePa = vo.asString(SklFieldName.CONTROLEPA);
			this.dhInicio = vo.asTimestamp(SklFieldName.DHINI);
			this.dhFinal = vo.asTimestamp(SklFieldName.DHTERMINO);
			this.statusProc = vo.asString(SklFieldName.STATUSPROC);
			this.qtdProduzir = vo.asBigDecimal(SklFieldName.QTDPRODUZIR);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
	}

	private void loadProdutoVenda(BigDecimal codProdPa) throws AlternativeVolumeNotFoundException, RecordNotFoundException {
		this.produtoVenda = new ProdutoVenda(codProdPa);
		this.produtoVenda.loadByPk(codProdPa);
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		try {
			if (idiProc == null)
				this.idiProc = rs.getBigDecimal(SklFieldName.IDIPROC); 
				
			this.idProc = rs.getBigDecimal(SklFieldName.IDPROC);
			this.nroLote = rs.getString(SklFieldName.NROLOTE);
			
			loadProdutoVenda(rs.getBigDecimal(SklFieldName.CODPRODPA));
			
			this.controlePa = rs.getString(SklFieldName.CONTROLEPA);
			this.statusProc = rs.getString(SklFieldName.STATUSPROC);
			this.qtdProduzir = rs.getBigDecimal(SklFieldName.QTDPRODUZIR);
		} catch (SQLException | AlternativeVolumeNotFoundException e) {
			throw new RecordNotFoundException(e);
		}

	}
	
	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = (idiProc == null) ? dao.findByPK(keyValue) : dao.findByPK(idiProc);
			loadByVO(vo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RecordNotFoundException(e);
		}
	}
	
	public void loadEtiquetasCaixa() throws RecordNotFoundException {
		this.etiquetas = VolumeProducaoFinder.buscaEtiquetasCaixaPorIdiProc(idiProc);
	}

	@Override
	public String toString() {
		return String.format(
				"OrdemProducao [idiProc=%s, idProc=%s, nroLote=%s, produtoVenda=%s, controlePa=%s, dhInicio=%s, dhFinal=%s, statusProc=%s, qtdProduzir=%s, workCenter=%s, etiquetas=%s, atividades=%s]",
				idiProc, idProc, nroLote, produtoVenda, controlePa, dhInicio, dhFinal, statusProc, qtdProduzir,
				workCenter, etiquetas, atividades);
	}
	
	
}


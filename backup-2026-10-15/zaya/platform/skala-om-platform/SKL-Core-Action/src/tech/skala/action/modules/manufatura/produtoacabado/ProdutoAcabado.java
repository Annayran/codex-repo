package tech.skala.action.modules.manufatura.produtoacabado;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.produto.ProdutoExceptions.AlternativeVolumeNotFoundException;
import tech.skala.core.beans.produto.ProdutoVenda;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * A classe {@code ProdutoAcabado} representa a tabela {@code TPRLPA}.
 *  
 * <p>
 * A classe {@code ProdutoAcabado} representa a entidade <i>ProdutoAcabado</i> {@code TPRLPA} 
 * que sao os Produtos associados a um <b>Processo Produtivo</b>.
 * </p> 
 * 
 * @author cassiomenezes
 * @since 2022-07-28
 *
 */
public class ProdutoAcabado implements EntityBean, LoadableByValueObject {
	public static final String ENTITY_NAME = DynamicEntityNames.PRODUTO_ACABADO;
	
	private BigDecimal								idProc;
	private BigDecimal								codProdPA;
	private String									controlePA;
	private ProdutoVenda							produto;
	private String									referencia;
	private BigDecimal								tamLotePad;
	
	public ProdutoAcabado(BigDecimal idProc, BigDecimal codProdPA, String controlePA) throws RecordNotFoundException {
		this.idProc = idProc;
		this.codProdPA = codProdPA;
		this.controlePA = controlePA;
		
		try {
			this.produto = new ProdutoVenda(codProdPA);
		} catch (AlternativeVolumeNotFoundException e) {
			throw new RecordNotFoundException(e);
		}
		this.produto.loadByPk();
	}
	
	public ProdutoAcabado() {

	}

	public BigDecimal getIdProc() {
		return idProc;
	}

	public BigDecimal getCodProdPA() {
		return codProdPA;
	}

	public String getControlePA() {
		return controlePA;
	}

	public ProdutoVenda getProduto() {
		return produto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public BigDecimal getTamLotePad() {
		return tamLotePad;
	}

	public void setTamLotePad(BigDecimal tamLotePad) {
		this.tamLotePad = tamLotePad;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.idProc = vo.asBigDecimal(SklFieldName.IDPROC);
		this.codProdPA = vo.asBigDecimal(SklFieldName.CODPRODPA);
		this.controlePA = vo.asString(SklFieldName.CONTROLEPA);
		this.referencia = vo.asString("REFERENCIA");
		this.tamLotePad = vo.asBigDecimal(SklFieldName.TAMLOTEPAD);
		try {
			this.produto = new ProdutoVenda(codProdPA);
		} catch (AlternativeVolumeNotFoundException e) {
			throw new RecordNotFoundException(e);
		}
		this.produto.loadByPk();
	}
	
	@Override
	public String toString() {
		return String.format(
				"ProdutoAcabado [idProc=%s, codProdPA=%s, controlePA=%s, produto=%s, referencia=%s, tamLotePad=%s]",
				idProc, codProdPA, controlePA, produto, referencia, tamLotePad);
	}

	
}

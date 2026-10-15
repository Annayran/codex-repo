package tech.skala.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * Localiza Etiquetas da Ordem de Produção.
 * 
 * @author marcosdamasceno
 * @author cassiomenezes
 * @since 2022-08-12
 *
 */
public class VolumeProducaoFinder {
	
	public BigDecimal buscaCapacidadePorCaixa(BigDecimal codGProd) throws RecordNotFoundException{
		
		JapeWrapper dao = JapeFactory.dao(SklEntityName.CAPACIDADE_POR_CAIXA);
		DynamicVO vo = null;
		BigDecimal capacidadePorCaixa = BigDecimal.ZERO;
		try {
			vo = dao.findOne("this.CODGPROD = ? AND this.CODPARC = 527", codGProd);
			capacidadePorCaixa = vo.asBigDecimalOrZero(SklFieldName.CAPACIDADE);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
		
		return capacidadePorCaixa;
	}
	
	public static List<VolumeProducao> buscaEtiquetasCaixaPorIdiProc(BigDecimal idiProc) throws RecordNotFoundException {
		List<VolumeProducao> etiquetas = new LinkedList<>();

		JapeWrapper dao = JapeFactory.dao("AD_PLTPRIVOP");
		Collection<DynamicVO> vos = null;
		try {
			vos = dao.find("this.IDIPROC = ?", idiProc);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		VolumeProducao etiqueta;
		for(DynamicVO vo : vos) {
			etiqueta = new VolumeProducao();
			etiqueta.loadByVO(vo);
			etiquetas.add(etiqueta);
		}
		
		return etiquetas;

	}
	
	/**
	 * Método responsável por buscar todos os apontamentos que não foram processados
	 * para uma determinada Ordem de Produção.
	 * 
	 * <p>
	 * O método realiza uma busca na tabela {@code AD_PLTPRIVOP} filtrando pela
	 * Ordem de Produção e por etiquetas que possuem o campo {@code STATUS} igual a
	 * 'N' (Não Processado).
	 * </p>
	 * 
	 * @param idiProc
	 * @return etiquetas Retorna uma coleção contendo todos os apontamentos que não
	 *         foram processados
	 * @throws RecordNotFoundException
	 * 
	 * @see {@link VolumeProducaoStatusEnum}
	 */
	public static List<VolumeProducao> buscaApontamentosNaoProcessados(BigDecimal idiProc)
			throws RecordNotFoundException {
		List<VolumeProducao> etiquetas = new LinkedList<>();

		JapeWrapper dao = JapeFactory.dao("AD_PLTPRIVOP");
		Collection<DynamicVO> vos = null;
		try {
			vos = dao.find("this.IDIPROC = ? AND this.STATUS = 'N'", idiProc);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		VolumeProducao etiqueta;
		for (DynamicVO vo : vos) {
			etiqueta = new VolumeProducao();
			etiqueta.loadByVO(vo);
			etiquetas.add(etiqueta);
		}

		return etiquetas;
	}
	
	
	/**
	 * Método responsável por retornar o Número Único de Volume da Ordem de
	 * Produção.
	 * 
	 * <p>
	 * O método {@code buscaNuvOPPorCodBarra} retorna o identificador de Volume
	 * através do código de barras. O código de barras lido pela etiqueta é
	 * construído usando a seguinte estrutura. Construído utilizando duas
	 * informações, separadas por um <b>. (ponto final)</b>. <br>
	 * 
	 * <br>
	 * <b>[IDIPROC].[NUVOP]</b> <br>
	 * 
	 * <br>
	 * <b>IDIPROC</b> - é o identificador único de uma Ordem de Produção,
	 * representado pela classe {@code OrdemProducao}. <br>
	 * <b>NUVOP</b> - é o identificador único de um Volume de Produção, representado
	 * pela classe {@code VolumeProducao}.
	 * </p>
	 * 
	 * @param codBarra
	 * @return o Número Único de Volume da Ordem de Produção.
	 */
	public static BigDecimal buscaNuvOPPorCodBarra(String codBarra) {
		if (codBarra == null)
			return null;

		String[] tokens = codBarra.split("[.]");
		return BigDecimalUtil.getBigDecimal(tokens[1]);
	}

}

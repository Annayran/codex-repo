package tech.skala.action.modules.comercial.meta;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.sql.NativeSql;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ComercialUtilities {

	private ComercialUtilities() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Este método retorna o que está em Carteira (pendente) para o produto
	 * informado.
	 * 
	 * Busca as informações da <b>última</b> Demanda calculada para o produto.
	 * 
	 * @param codEmp
	 * @param codProd
	 * @param dtRef
	 * @return
	 * @throws SkalaModelException
	 */
	public static BigDecimal getCarteiraInicial(BigDecimal codEmp, BigDecimal codProd, Timestamp dtRef)
			throws SkalaModelException {

		BigDecimal cartInicial = BigDecimal.ZERO;
		try {
			cartInicial = NativeSql.getBigDecimal("PLF_GET_SKU_CART(:CODEMP, :CODPROD, :DTREF)", "DUAL", null,
					new Object[] { codEmp, codProd, dtRef });
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível calcular a carteira inicial do produto.", codProd);
			SkalaModelException.throwMe(msg, e);
		}

		return cartInicial;
	}

	public static BigDecimal getCarteiraAtual(BigDecimal codEmp, BigDecimal codProd) throws SkalaModelException {

		BigDecimal qtdPendAtu = BigDecimal.ZERO;
		try {
			qtdPendAtu = NativeSql.getBigDecimal("PLF_GET_SKU_CART_ATU(:CODEMP, :CODPROD)", "DUAL", null,
					new Object[] { codEmp, codProd });
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível obter a Carteira Atual do produto.", codProd);
			SkalaModelException.throwMe(msg, e);
		}

		return qtdPendAtu;
	}

	public static BigDecimal getMediaTrimestral(BigDecimal codEmp, BigDecimal codProd, Timestamp dtRef)
			throws SkalaModelException {

		BigDecimal qtdMediaProd = BigDecimal.ZERO;
		try {
			qtdMediaProd = NativeSql.getBigDecimal("PLF_GET_MEDIA_TRIMESTRE(:CODEMP, :CODPROD, :DTREF)", "DUAL", null,
					new Object[] { codEmp, codProd, dtRef });
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível calcular a média Trimestral do produto.", codProd);
			SkalaModelException.throwMe(msg, e);
		}

		return qtdMediaProd;
	}

	public static BigDecimal getEstoque(BigDecimal codEmp, BigDecimal codProd, BigDecimal codLocal, String controle,
			BigDecimal codParc, String tipo, String tipoEstoque, String mostrarEstoqueNegativo)
			throws SkalaModelException {

		BigDecimal estoque = BigDecimal.ZERO;
		try {
			estoque = NativeSql.getBigDecimal(
					"PLF_GETESTOQUEDISPONIVEL(:CODEMP, :CODPROD, :CODLOCAL, :CONTROLE, :CODPARC, :TIPO, :TIPOESTOQUE, :MOSTRARESTOQUENEGATIVO)",
					"DUAL", null, new Object[] { codEmp, codProd, codLocal, controle, codParc, tipo, tipoEstoque,
							mostrarEstoqueNegativo });
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível buscar o estoque deste produto.", codProd);
			SkalaModelException.throwMe(msg, e);
		}

		return estoque;
	}

	public static BigDecimal getEstoqueInicial(BigDecimal codEmp, BigDecimal codLocal, BigDecimal codProd,
			Timestamp dtRef) throws SkalaModelException {

		BigDecimal estoqueInicial = BigDecimal.ZERO;
		try {
			estoqueInicial = NativeSql.getBigDecimal("PLF_GET_EST_INI(:CODEMP, :CODLOCAL, :CODPROD, :DTREF)", "DUAL",
					null, new Object[] { codEmp, codLocal, codProd, dtRef });
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível calcular o estoque Inicial do produto.", codProd);
			SkalaModelException.throwMe(msg, e);
		}

		return estoqueInicial;
	}
	
	public static BigDecimal getCarteiraAFaturar(BigDecimal codEmp, BigDecimal codProd) throws SkalaModelException {

		BigDecimal qtdPedidos = BigDecimal.ZERO;
		try {
			qtdPedidos = NativeSql.getBigDecimal("PLF_GET_CARTEIRA_FATURAR(:CODEMP, :CODPROD)", "DUAL", null,
					new Object[] { codEmp, codProd });
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível calcular a Carteira a faturar do produto.",
					codProd);
			SkalaModelException.throwMe(msg, e);
		}

		return qtdPedidos;
	}

	public static BigDecimal getQtdFaturado(BigDecimal codEmp, BigDecimal codProd, Timestamp dtRef)
			throws SkalaModelException {

		BigDecimal qtdFaturado = BigDecimal.ZERO;
		try {
			qtdFaturado = NativeSql.getBigDecimal("PLF_GET_SKU_TOT(:CODEMP, :CODPROD, :DTREF)", "DUAL", null,
					new Object[] { codEmp, codProd, dtRef });
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível obter a Quantidade Faturada do produto.", codProd);
			SkalaModelException.throwMe(msg, e);
		}

		return qtdFaturado;
	}

	public static BigDecimal getQtdProduzida(BigDecimal codEmp, BigDecimal codProd, Timestamp dtRef)
			throws SkalaModelException {

		BigDecimal qtdProduzida = BigDecimal.ZERO;
		try {
			qtdProduzida = NativeSql.getBigDecimal("PLF_GET_QTDEXPED_TESTE(:CODEMP, :CODPROD, :DTREF)", "DUAL", null,
					new Object[] { codEmp, codProd, dtRef });
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível obter a Quantidade produzida do produto.",
					codProd);
			SkalaModelException.throwMe(msg, e);
		}

		return qtdProduzida;
	}
	public static BigDecimal getEstoqueDisponivel (BigDecimal codEmp, BigDecimal codProd,
			BigDecimal codLocal, String controle, BigDecimal codParc, String tipo, String tipoEstoque,
			String mostrarEstoqueNegativo) throws SkalaModelException {
		
		BigDecimal qtd = BigDecimal.ZERO;
		
		try {
			qtd = NativeSql.getBigDecimal("PLF_GETESTOQUEDISPONIVEL(:CODEMP, :CODPROD, :CODLOCAL, :CONTROLE, :CODPARC, :TIPO, :TIPOESTOQUE, :MOSTRARESTOQUENEGATIVO)", "DUAL", null,
					new Object[] { codEmp, codProd, codLocal, controle, codParc, tipo, tipoEstoque, mostrarEstoqueNegativo});
						
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível buscar o estoque deste produto.",
					codProd);
			SkalaModelException.throwMe(msg, e);
		}
		return qtd;		
}
	public static BigDecimal getQtdMediaProd (BigDecimal codEmp, BigDecimal codProd, Timestamp dtRef)
			throws SkalaModelException {
		
		BigDecimal qtdMediaProd = BigDecimal.ZERO;

		try {
			qtdMediaProd = NativeSql.getBigDecimal("PLF_GET_MEDIA_TRIMESTRE(:CODEMP, :CODPROD, :DTREF) QTDMEDIA", "DUAL", null,
					new Object[] { codEmp, codProd, dtRef});
						
		} catch (Exception e) {
			String msg = String.format("[CODPROD %s]Não foi possível calcular a média Trimestral do produto.",
					codProd);
			SkalaModelException.throwMe(msg, e);
		}
		return qtdMediaProd;		
	}
	

}

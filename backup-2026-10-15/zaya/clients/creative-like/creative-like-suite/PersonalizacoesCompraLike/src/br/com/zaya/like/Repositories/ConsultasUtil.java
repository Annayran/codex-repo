package br.com.zaya.like.Repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.MGECoreParameter;

public class ConsultasUtil {

	public static BigDecimal buscarTipoDeOperação(BigDecimal nunota) throws Exception {

		return NativeSql.getBigDecimal("CODTIPOPER", "TGFCAB", "NUNOTA = ?", new Object[] { nunota });
	}
	
	public static String buscarTipoMovimentoNota(BigDecimal nunota) throws Exception {
		return NativeSql.getString("TIPMOV", "TGFCAB", "NUNOTA = ?", new Object[] {nunota});
	}

	public static BigDecimal buscarCodUsuarioNota(BigDecimal nunota) throws Exception {
		return NativeSql.getBigDecimal("CODUSU", "TGFCAB", "NUNOTA = ?", new Object[] { nunota });
	}

	public static BigDecimal buscarCodUsuarioInclusaoNota(BigDecimal nunota) throws Exception {
		return NativeSql.getBigDecimal("CODUSU", "TGFCAB", "NUNOTA = ?", new Object[] { nunota });
	}

	public static boolean usuarioPodeAlterarPedido(BigDecimal codUsu) throws Exception {
		String paramListaUsuarios = MGECoreParameter.getParameterAsString("ZYUSUALTPEDCOM");
		List<BigDecimal> listaUsuarios = Arrays.stream(paramListaUsuarios.split(",")).map(u -> new BigDecimal(u))
				.collect(Collectors.toList());

		return listaUsuarios.contains(codUsu);
	}

	public static String buscarStatusNota(BigDecimal nuNota) throws Exception {
		return NativeSql.getString("STATUSNOTA", "TGFCAB", "NUNOTA = ?", new Object[] { nuNota });
	}

	public static boolean ehTopPedCompraRevenda(BigDecimal nuNota) throws Exception {
		BigDecimal codTipOper = ConsultasUtil.buscarTipoDeOperação(nuNota);
		String listTopPedidoCompraRevenda = MGECoreParameter.getParameterAsString("ZYTOPPEDREVEND");
		List<BigDecimal> listaTops = Arrays.stream(listTopPedidoCompraRevenda.split(",")).map(String::trim)
				.map(l -> new BigDecimal(l)).collect(Collectors.toList());
		if (listaTops.contains(codTipOper)) {
			return true;
		}
		return false;
	}

	public static boolean existeItemPendente(BigDecimal nunota, BigDecimal codProd) throws Exception {
		BigDecimal contador = NativeSql.getBigDecimal("COUNT(1)", "TGFITE",
				"PENDENTE = 'S' AND NUNOTA = ? AND CODPROD <> ?", new Object[] { nunota, codProd });
		return (contador.compareTo(BigDecimal.ZERO) == 0) ? false : true;
	}

	public static boolean topProibeExclusao(BigDecimal nunota) throws Exception {
		String proibeExclusao = NativeSql.getString("TPO.AD_PROIBIEXCLUIRITEM", "TGFCAB CAB, TGFTOP TPO",
				"CAB.CODTIPOPER = TPO.CODTIPOPER AND TPO.DHALTER = (SELECT MAX(T.DHALTER) FROM TGFTOP T WHERE T.CODTIPOPER = TPO.CODTIPOPER) AND CAB.NUNOTA = ? ",
				new Object[] { nunota });
		if ("S".equals(proibeExclusao)) {
			return true;
		}
		return false;
	}

	public static boolean topPermiteCancelarItem(BigDecimal nunota) throws Exception {
		String proibeExclusao = NativeSql.getString("TPO.AD_PERMITECANCELAR", "TGFCAB CAB, TGFTOP TPO",
				"CAB.CODTIPOPER = TPO.CODTIPOPER AND TPO.DHALTER = (SELECT MAX(T.DHALTER) FROM TGFTOP T WHERE T.CODTIPOPER = TPO.CODTIPOPER) AND CAB.NUNOTA = ? ",
				new Object[] { nunota });
		if ("S".equals(proibeExclusao)) {
			return true;
		}
		return false;
	}

}

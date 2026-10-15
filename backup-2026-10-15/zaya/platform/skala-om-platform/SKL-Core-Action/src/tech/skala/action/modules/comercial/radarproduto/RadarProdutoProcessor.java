package tech.skala.action.modules.comercial.radarproduto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;

import tech.skala.action.beans.radarproduto.RadarProduto;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.beans.radarproduto.RadarFamilia;
import tech.skala.action.modules.comercial.meta.ComercialUtilities;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class RadarProdutoProcessor {

	public void excluirListaMPporReferencia(Timestamp dtRef, BigDecimal codFam) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFRADARBOM");

		try {
			dao.deleteByCriteria(
					"this.REFERENCIA = ? AND this.CODPROD IN (SELECT CODPROD FROM PLVGFPRO WHERE CODFAM = ?)", dtRef,
					codFam);
		} catch (Exception e) {
			String msg = "Não foi possível deletar os produtos.";
			SkalaModelException.throwMe(msg, e);
		}
	}

	public void atualizarRadar(List<RadarFamilia> familias) throws SkalaModelException {
		BigDecimal codEmp;
		BigDecimal codProd;
		Timestamp dtRef;

		try {
			for (RadarFamilia radarFamilia : familias) {
				excluirListaMPporReferencia(radarFamilia.getDtRef(), radarFamilia.getCodFam());

				for (RadarProduto radarProduto : radarFamilia.getRadarProduto()) {
					codEmp = radarFamilia.getCodEmp();
					codProd = radarProduto.getProduto().getCodProd();

					dtRef = radarProduto.getReferencia();

					radarProduto.setQtdPendenteFam(radarFamilia.getQtdPendente());
					radarProduto.setQtdPendente(ComercialUtilities.getCarteiraInicial(codEmp, codProd, dtRef));
					radarProduto.setQtdPedidos(ComercialUtilities.getCarteiraAFaturar(codEmp, codProd));
					radarProduto.setQtdFat(ComercialUtilities.getQtdFaturado(codEmp, codProd, dtRef));
					radarProduto.setQtdExped(ComercialUtilities.getQtdProduzida(codEmp, codProd, dtRef));
					radarProduto.setQtdDem(radarProduto.getQtdPrev()
							.add(radarProduto.getEstoqueFinal().subtract(radarProduto.getEstoqueIni())));
					radarProduto.setQtdDemPad(radarProduto.calcularQtdDemPad());

					if (radarProduto.getQtdDem().longValue() < 0
							|| radarProduto.getQtdDem().longValue() < radarProduto.getQtdExped().longValue()) {
						radarProduto.setQtdDemSld(BigDecimal.ZERO);
						radarProduto.setQtdDem(BigDecimal.ZERO);
					} else {
						radarProduto.setQtdDemSld(radarProduto.getQtdDemPad().subtract(radarProduto.getQtdExped()));

					}

					radarProduto.setQtdPrevProx(BigDecimal.ZERO);
					if (radarProduto.getQtdPrev().longValue() != 0) {
						radarProduto.setPercFat(
								radarProduto.getQtdFat().divide(radarProduto.getQtdPrev(), BigDecimalUtil.MATH_CTX));
					} else {
						radarProduto.setPercFat(BigDecimal.ZERO);
					}

					if (radarProduto.getQtdDemPad().longValue() > 0)
						radarProduto.setPercExped(radarProduto.getQtdExped().divide(radarProduto.getQtdDemPad(),
								BigDecimalUtil.MATH_CTX));

					radarProduto.buildListaMateriais();
					radarProduto.persists();
				}
			}

		} catch (Exception e) {
			String msg = "Não foi possível atualizar o radar";
			msg = SkalaModelException.buildErrorMessage(msg, e);
			SkalaModelException.throwMe(msg, e);
		}

	}

}

package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.comercial.estoque.beans.CabecalhoNota;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class PedidoCompraBeneficioViaNova extends CabecalhoNota {
	private static final BigDecimal MODELO_PEDIDO_VIANOVA = BigDecimal.valueOf(2297746);
	private BigDecimal codCbe;

	public PedidoCompraBeneficioViaNova(String observacao, BigDecimal codCbe) throws Exception {
		super(MODELO_PEDIDO_VIANOVA, observacao);

		this.codCbe = codCbe;
	}

	public void buildItems() throws SkalaModelException {

		try {
			Collection<PrePersistEntityState> itensNota = new ArrayList<>();
			CACHelper cacHelper = new CACHelper();

			JapeWrapper centralBeneficioDAO = JapeFactory.dao("CentralBeneficio");
			DynamicVO vo = centralBeneficioDAO.findByPK(codCbe);

			BigDecimal vlrEmp = vo.asBigDecimal("VLREMP");

			DynamicVO itemVO = (DynamicVO) EntityFacadeFactory.getDWFFacade()
					.getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);

			itemVO.setProperty(SklFieldName.CODPROD, BigDecimal.valueOf(16237));
			itemVO.setProperty(SklFieldName.CODVOL, "UN");
			itemVO.setProperty(SklFieldName.CODLOCALORIG, BigDecimal.ZERO);

			itemVO.setProperty(SklFieldName.VLRUNIT, vlrEmp);

			itemVO.setProperty(SklFieldName.QTDNEG, BigDecimal.ONE);
			itemVO.setProperty("PERCDESC", BigDecimal.ZERO);
			itemVO.setProperty("VLRDESC", BigDecimal.ZERO);

			PrePersistEntityState itePreState = PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(),
					DynamicEntityNames.ITEM_NOTA, itemVO);
			itensNota.add(itePreState);

			cacHelper.incluirAlterarItem(super.getNuNota(), super.authInfo, itensNota, true);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir os itens.", e);
		}

	}

}

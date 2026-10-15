package tech.skala.action.modules.comercial.regional;

import java.math.BigDecimal;
import java.util.Collection;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AtivaRegionalModel {
	private Registro[]						linhas;

	public AtivaRegionalModel(Registro[] linhas) {
		this.linhas = linhas;
	}

	public void ativar() throws SkalaModelException {
		BigDecimal codigo;
		for (Registro linha : linhas) {
			codigo = BigDecimalUtil.getBigDecimal(linha.getCampo("CODIGO"));
			doUpdate(codigo);
		}
	}

	private void doUpdate(final BigDecimal codigo) throws SkalaModelException {
		SessionHandle hnd = null; // identifica o início de uma sessão.

		try {
			hnd = JapeSession.open(); // abertura da sessão

			hnd.execWithTX(new JapeSession.TXBlock() {
				public void doWithTx() throws Exception {
					ativarRegional(codigo);
					ativarCentroResultado(codigo);
				}
			});
		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		} finally {
			JapeSession.close(hnd); // fechamento da sessão
		}
	}

	/**
	 * @param codigo Codigo da Regional de Vendas
	 * @throws SkalaModelException
	 */
	private void ativarRegional(final BigDecimal codigo) throws SkalaModelException {
		EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codigo });
		EntityUpdate upd = new EntityUpdate(SklEntityName.REGIONAL_VENDA, pk);
		upd.setField(SklFieldName.ATIVO, "S");
		upd.fluidUpdate();
	}

	private void ativarCentroResultado(final BigDecimal codigo) throws SkalaModelException {
		try {
			JapeWrapper dao = JapeFactory.dao(SklEntityName.REGIONAL_VENDA_CR);

			EntityUpdate upd;
			Collection<DynamicVO> vos = dao.find("this.CODIGO = ?", codigo);
			for (DynamicVO vo : vos) {
				upd = new EntityUpdate(SklEntityName.REGIONAL_VENDA_CR, vo);
				upd.setField(SklFieldName.ATIVO, "S");
				upd.fluidUpdate();

				substituirCentroResultado(codigo, vo.asBigDecimal(SklFieldName.CODCENCUS));
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}
	}

	private void substituirCentroResultado(BigDecimal codigo, BigDecimal codCenCus) throws SkalaModelException {

		try {
			JapeWrapper dao = JapeFactory.dao(SklEntityName.REGIONAL_VENDA_CR_VENDEDOR);

			BigDecimal codVend;
			Collection<DynamicVO> vos = dao.find("this.CODIGO = ? AND this.CODCENCUS = ?", codigo, codCenCus);
			for (DynamicVO vo : vos) {
				codVend = vo.asBigDecimal(SklFieldName.CODVEND);

				substituirCentroResultadoVendedor(codVend, codCenCus);
				substituirCentroResultadoUsuario(codVend, codCenCus);
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}

	}

	private void substituirCentroResultadoVendedor(BigDecimal codVend, BigDecimal codCenCus)
			throws SkalaModelException {
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.VENDEDOR);
			DynamicVO vo = dao.findByPK(codVend);

			EntityUpdate upd = new EntityUpdate(DynamicEntityNames.VENDEDOR, vo);
			upd.setField(SklFieldName.CODCENCUSPAD, codCenCus);
			upd.fluidUpdate();

		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}
	}

	private void substituirCentroResultadoUsuario(BigDecimal codVend, BigDecimal codCenCus) throws SkalaModelException {
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.USUARIO);

			EntityUpdate upd;
			Collection<DynamicVO> vos = dao.find("this.CODVEND = ?", codVend);
			for (DynamicVO vo : vos) {
				upd = new EntityUpdate(DynamicEntityNames.USUARIO, vo);
				upd.setField(SklFieldName.CODCENCUSPAD, codCenCus);
				upd.fluidUpdate();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}
	}

}
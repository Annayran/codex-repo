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
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * 
 * @author cassiomenezes
 * @author hiagoalexandre
 * @since 2022-06-30
 *
 */
public final class InativaRegionalModel {
	private Registro[]						linhas;

	public InativaRegionalModel(Registro[] linhas) {
		this.linhas = linhas;
	}

	public void inativar() throws SkalaModelException {
		BigDecimal codigo;
		for(Registro linha:linhas) {
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

					inativarRegional(codigo);
					inativarCentroResultado(codigo);
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
	private void inativarRegional(final BigDecimal codigo) throws SkalaModelException {
		EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codigo });
		EntityUpdate upd = new EntityUpdate(SklEntityName.REGIONAL_VENDA, pk);
		upd.setField(SklFieldName.ATIVO, "N");
		upd.fluidUpdate();
	}
	
	private void inativarCentroResultado(final BigDecimal codigo) throws SkalaModelException {
		try {
			JapeWrapper dao = JapeFactory.dao(SklEntityName.REGIONAL_VENDA_CR);
			
			EntityUpdate upd;
			Collection<DynamicVO> crVO = dao.find("this.CODIGO = ?", codigo);
			for (DynamicVO vo : crVO) {
				upd = new EntityUpdate(SklEntityName.REGIONAL_VENDA_CR, vo);
				upd.setField(SklFieldName.ATIVO, "N");
				upd.fluidUpdate();
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}
	}
}

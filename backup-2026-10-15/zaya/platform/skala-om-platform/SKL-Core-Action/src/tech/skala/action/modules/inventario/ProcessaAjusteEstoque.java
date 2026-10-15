package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProcessaAjusteEstoque {
private final Logger								logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	
	private InventarioProcessor 			processor;
	private DynamicVO						modeloEntradaVO;
	private DynamicVO						modeloSaidaVO;
	
	public ProcessaAjusteEstoque(InventarioCabecalho inventario) throws SkalaModelException {
		this.processor = new InventarioProcessor(inventario);
	}

	public void processaAjusteEstoque(Params params, List<InventarioAjuste> itensAjuste) throws Exception {
		processor.processaAjusteEstoque(params, itensAjuste);

	}
	
	public void getModelos(BigDecimal codEmp) throws SkalaModelException {
		String msg;
		JapeWrapper empresaDAO = JapeFactory.dao(DynamicEntityNames.EMPRESA_FINANCEIRO);
		JapeWrapper cabecalhoDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

		DynamicVO empresaVO = null;
		try {
			empresaVO = empresaDAO.findByPK(codEmp);
			if (empresaVO == null) {
				msg = String.format("[Empresa %s] Não foi possível localizar a Empresa.", codEmp);
				SkalaModelException.throwMe(msg, null);				
			}
			
			msg = String.format("[Empresa %s] Localizada.", codEmp);
			logger.info(msg);
		} catch (Exception e) {
			msg = String.format("[Empresa %s] Não foi possível localizar a Empresa.", codEmp);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			SkalaModelException.throwMe(msg, e);
		}
		
		try {
			modeloEntradaVO = cabecalhoDAO.findByPK(empresaVO.asBigDecimal("NOTAENTAJUSTEST"));
			if (modeloEntradaVO == null) {
				msg = String.format("[Empresa %s] Não foi possível localizar o Modelo de Ajuste de Entradas.", codEmp);
				SkalaModelException.throwMe(msg, null);					
			}
			
			msg = String.format("[Empresa %s] Modelo de Entrada %s.", codEmp,
					empresaVO.asBigDecimal("NOTAENTAJUSTEST"));
			logger.info(msg);

		} catch (Exception e) {
			msg = String.format("[Empresa %s] Não foi possível localizar o Modelo de Ajuste de Entradas.", codEmp);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			SkalaModelException.throwMe(msg, e);
		}
		
		try {
			modeloSaidaVO = cabecalhoDAO.findByPK(empresaVO.asBigDecimal("NOTASAIAJUSTEST"));
			if (modeloSaidaVO == null) {
				msg = String.format("[Empresa %s] Não foi possível localizar o Modelo de Ajuste de Saídas.", codEmp);
				SkalaModelException.throwMe(msg, null);					
			}
			
			msg = String.format("[Empresa %s] Modelo de Saídas %s.", codEmp,
					empresaVO.asBigDecimal("NOTASAIAJUSTEST"));
			logger.info(msg);
		} catch (Exception e) {
			msg = String.format("[Empresa %s] Não foi possível localizar o Modelo de Ajuste de Saídas.", codEmp);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			SkalaModelException.throwMe(msg, e);
		}

	}
	
	public void setModelo(Params params, String tipo) {
		
		if (InventarioAjusteTipoAjusteEnum.ENTRADA.getValue().equals(tipo)) {
			params.setNuNotaOrig(modeloEntradaVO.asBigDecimal(SklFieldName.NUNOTA));
			params.setCodTipOperDestino(modeloEntradaVO.asBigDecimal(SklFieldName.CODTIPOPER));
		}
		
		if (InventarioAjusteTipoAjusteEnum.SAIDA.getValue().equals(tipo)) {
			params.setNuNotaOrig(modeloSaidaVO.asBigDecimal(SklFieldName.NUNOTA));
			params.setCodTipOperDestino(modeloSaidaVO.asBigDecimal(SklFieldName.CODTIPOPER));
		}
	}
}

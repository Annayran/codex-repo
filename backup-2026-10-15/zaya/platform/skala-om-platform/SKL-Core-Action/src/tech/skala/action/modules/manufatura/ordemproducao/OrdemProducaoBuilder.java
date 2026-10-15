package tech.skala.action.modules.manufatura.ordemproducao;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public final class OrdemProducaoBuilder {
	
	private OrdemProducaoBuilder() {
		throw new IllegalStateException("Utility class");
	}
	
	public static OrdemProducao buildBYPk(Object... keyValue) throws RecordNotFoundException {
		
		DynamicVO vo = findByPK(DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO, keyValue);
		
		return loadByVO(vo);
	}

	private static DynamicVO findByPK(String entityName, Object... keyValue) throws RecordNotFoundException {
		DynamicVO vo = null;
		try {
			JapeWrapper dao = JapeFactory.dao(entityName);
			vo = dao.findByPK(keyValue);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
		return vo;
	}

	public static OrdemProducao loadByVO(DynamicVO vo) throws RecordNotFoundException {
		if (vo == null)
			throw new RecordNotFoundException();
		
		OrdemProducao ordemProducao = new OrdemProducao();
		ordemProducao.setIdiProc(vo.asBigDecimal(SklFieldName.IDIPROC));
		ordemProducao.setNroLote(vo.asString(SklFieldName.NROLOTE));
		ordemProducao.setStatusProc(vo.asString(SklFieldName.STATUSPROC));

//		private BigDecimal						idiProc;
//		private ProcessoProdutivo				processoProdutivo;
//		private PlantaManufatura				planta;
//		private WorkCenter						workCenter;
//		private String							nroLote;
//		private String							statusProc;
//		private ProdutoAcabadoAProduzir			produtoAcabadoAProduzir;

		return ordemProducao;
	}
	
}

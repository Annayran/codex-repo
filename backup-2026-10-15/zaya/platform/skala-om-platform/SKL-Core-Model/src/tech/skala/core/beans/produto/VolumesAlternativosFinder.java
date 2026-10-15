package tech.skala.core.beans.produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.produto.ProdutoExceptions.AlternativeVolumeNotFoundException;

public class VolumesAlternativosFinder {
	
	private VolumesAlternativosFinder() {
		throw new IllegalStateException("Utility class");
	}

	public static List<VolumeAlternativo> findVolumesAlternativos(BigDecimal codProd) throws AlternativeVolumeNotFoundException {
		List<VolumeAlternativo> volumesAlternativos = null;
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.VOLUME_ALTERNATIVO);

			VolumeAlternativo volumeAlternativo;
			volumesAlternativos = new ArrayList<>();
			Collection<DynamicVO> volumesAlternativosVO = dao.find("this.CODPROD = ?", codProd);
			for (DynamicVO vo : volumesAlternativosVO) {
				volumeAlternativo = new VolumeAlternativo();
				volumeAlternativo.loadByVO(vo);
				
				volumesAlternativos.add(volumeAlternativo);
			}
		} catch (Exception e) {
			throw new ProdutoExceptions.AlternativeVolumeNotFoundException(e, codProd);
		}
		
		return volumesAlternativos;
	}

}

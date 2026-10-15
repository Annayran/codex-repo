package tech.skala.modules.suprimentos.abastecimento;

import java.math.BigDecimal;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sankhya.util.BigDecimalUtil;

import tech.skala.core.commons.dd.DTO;
import tech.skala.core.util.json.SkalaJsonUtils;

public class RequisicaoMapper {
	
	private static ModelMapper modelMapper;
	
	private RequisicaoMapper() {
		throw new IllegalStateException("Utility class");
	}
	
	public static DTO requisacao2RequisicaoStatusDTO(Requisicao requisicao) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(Requisicao.class, RequisicaoStatusDTO.class);

		return modelMapper.map(requisicao, RequisicaoStatusDTO.class);
	}
	
	public static DTO produtoRequisicao2ProdutoRequisicaoStatusDTO(ProdutoRequisicao produtoRequisicao) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(ProdutoRequisicao.class, ProdutoRequisicaoStatusDTO.class);
		
		return modelMapper.map(produtoRequisicao, ProdutoRequisicaoStatusDTO.class);
	}
	
	public static DTO jsonObject2RequisicaoSequenciaDTO(JsonObject jsonObject) {
		JsonElement nuReqJE = jsonObject.get("nuReq");
		JsonElement sequenciaJE = jsonObject.get("sequencia");
		
		if(SkalaJsonUtils.isJsonElementValid(nuReqJE) && SkalaJsonUtils.isJsonElementValid(sequenciaJE)) {
			modelMapper = new ModelMapper();
			modelMapper.typeMap(JsonElement.class, RequisicaoSequenciaDTO.class).addMappings(mapper -> {
				mapper.map(e -> e.getAsBigDecimal(), RequisicaoSequenciaDTO::setNuReq);
			});
		}
		
		return null;
	}
}

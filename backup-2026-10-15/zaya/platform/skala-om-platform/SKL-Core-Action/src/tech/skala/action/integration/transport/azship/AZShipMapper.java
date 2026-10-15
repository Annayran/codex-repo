package tech.skala.action.integration.transport.azship;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import tech.skala.core.commons.dd.DTO;

public class AZShipMapper {

	private static ModelMapper modelMapper;
	private static Converter<Object, String> toString = e -> e.getSource() == null ? null : e.getSource().toString();

	private AZShipMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static DTO dadosPedido2OrdersDTOMapper(DadosPedido dadosPedido) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(DadosPedido.class, OrderDTO.class).addMappings(mapper -> {
			mapper.map(DadosPedido::getNuNota, OrderDTO::setCode);
			mapper.map(DadosPedido::getCgc, OrderDTO::setCollectDocument);
			mapper.map(DadosPedido::getQtdNeg, OrderDTO::setCollectAmount);
			mapper.map(DadosPedido::getPesoBruto, OrderDTO::setCollectWeight);
			mapper.map(DadosPedido::getDtPrevSaida, OrderDTO::setCollectDate);
			mapper.map(DadosPedido::getCgcCpf, OrderDTO::setDeliveryDocument);
			mapper.map(DadosPedido::getRazaoSocial, OrderDTO::setDeliveryName);
			mapper.map(DadosPedido::getLogradouro, OrderDTO::setDeliveryStreet);
			mapper.map(DadosPedido::getCep, OrderDTO::setDeliveryCEP);
			mapper.map(DadosPedido::getNomeBai, OrderDTO::setDeliveryDistrict);
			mapper.map(DadosPedido::getNomeCid, OrderDTO::setDeliveryCity);
			mapper.map(DadosPedido::getUf, OrderDTO::setDeliveryUF);
			mapper.map(DadosPedido::getLatitude, OrderDTO::setDeliveryLatitude);
			mapper.map(DadosPedido::getLongitude, OrderDTO::setDeliveryLongitude);
			mapper.map(DadosPedido::getDtPrevEnt, OrderDTO::setDeliveryDate);
			mapper.map(DadosPedido::getVlrNota, OrderDTO::setOrderValue);
			mapper.map(DadosPedido::getObservacao, OrderDTO::setObservation);
		});

		return modelMapper.map(dadosPedido, OrderDTO.class);
	}

	public static DTO remessa2RemessaPedidoDtoMapper(AZShipRemessa remessa) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(AZShipRemessa.class, RemessaPedidoDTO.class).addMappings(mapper -> {
			mapper.using(toString).map(AZShipRemessa::getNuNota, RemessaPedidoDTO::setInternalCode);
			mapper.map(AZShipRemessa::getNuNota, RemessaPedidoDTO::setCode);
			mapper.map(AZShipRemessa::getCnpjEmp, RemessaPedidoDTO::setCollectDocument);
			mapper.map(AZShipRemessa::getQtdItens, RemessaPedidoDTO::setCollectAmount);
			mapper.map(AZShipRemessa::getPeso, RemessaPedidoDTO::setCollectWeight);
			mapper.map(AZShipRemessa::getDtColeta, RemessaPedidoDTO::setCollectDate);
			mapper.map(AZShipRemessa::getCgcCpf, RemessaPedidoDTO::setDeliveryDocument);
			mapper.map(AZShipRemessa::getRazaoSocial, RemessaPedidoDTO::setDeliveryName);
			mapper.map(AZShipRemessa::getLogradouro, RemessaPedidoDTO::setDeliveryStreet);
			mapper.map(AZShipRemessa::getCep, RemessaPedidoDTO::setDeliveryCEP);
			mapper.map(AZShipRemessa::getNomeCid, RemessaPedidoDTO::setDeliveryCity);
			mapper.map(AZShipRemessa::getNomeBai, RemessaPedidoDTO::setDeliveryDistrict);
			mapper.map(AZShipRemessa::getUf, RemessaPedidoDTO::setDeliveryUF);
			mapper.map(AZShipRemessa::getLatitude, RemessaPedidoDTO::setDeliveryLatitude);
			mapper.map(AZShipRemessa::getLongitude, RemessaPedidoDTO::setDeliveryLongitude);
			mapper.map(AZShipRemessa::getDtEntrega, RemessaPedidoDTO::setDeliveryDate);
			mapper.map(AZShipRemessa::getVlrTot, RemessaPedidoDTO::setOrderValue);
			mapper.map(AZShipRemessa::getObservacao, RemessaPedidoDTO::setObservation);
		});

		return modelMapper.map(remessa, RemessaPedidoDTO.class);
	}

}

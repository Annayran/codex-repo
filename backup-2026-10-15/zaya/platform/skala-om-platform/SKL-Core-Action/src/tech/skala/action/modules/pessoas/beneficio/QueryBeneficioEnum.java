package tech.skala.action.modules.pessoas.beneficio;

public enum QueryBeneficioEnum {

	BENEFICIO_ALELO("CalculoPreBeneficio_queValoresAlelo.sql"),
	BENEFICIO_ALELO_ECOMMERCE("CalculoPreBeneficio_queValoresAleloEcommerce.sql"),
	BENEFICIO_FLASH_VR_CSC("CalculoPreBeneficio_queValoresFlashVrCsc.sql"),
	BENEFICIO_TRANSUBE("CalculoPreBeneficio_queValoresTransube.sql"),
	BENEFICIO_TRANSUBE_SEMANAL("CalculoPreBeneficio_queValoresTransubeSemanal.sql"),
	BENEFICIO_GUARUPASS("CalculoPreBeneficio_queValoresGuarupass.sql"),
	BENEFICIO_VIA_NOVA("CalculoPreBeneficio_queValoresViaNova.sql"),
	BENEFICIO_VALECARD("CalculoPreBeneficio_queValoresValeCard.sql");
	
	private final String value;

	private QueryBeneficioEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}
}

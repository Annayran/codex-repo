package tech.skala.modules.manufatura.ordemproducao;

public enum VolumeProducaoStatusEnum {
	PROCESSADO("P"),
	EM_ANDAMENTO("A"),
	NAO_PROCESSADO("N");
	
	private final String value;

	private VolumeProducaoStatusEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}
}

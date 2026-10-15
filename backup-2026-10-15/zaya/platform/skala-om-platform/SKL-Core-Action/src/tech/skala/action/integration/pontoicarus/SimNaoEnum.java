package tech.skala.action.integration.pontoicarus;

public enum SimNaoEnum {
	SIM(true),
	NAO(false);

	private final boolean value;
	
	SimNaoEnum(boolean value) {
		this.value = value;
	}
	
	public static String toString(boolean value) {
		return value ? SIM.toString() : NAO.toString();
	}

	public boolean getValue() {
		return value;
	}

}

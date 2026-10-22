package br.com.zaya.mahospitalar.genesis.config;

public final class EnvironmentGenesisSecretProvider implements GenesisSecretProvider {
    public static final String SYSTEM_PROPERTY = "mah.genesis.token";
    public static final String ENVIRONMENT_VARIABLE = "MAH_GENESIS_TOKEN";

    @Override
    public String obterToken() {
        String token = System.getProperty(SYSTEM_PROPERTY);
        boolean ambienteTeste = Boolean.getBoolean("mah.genesis.allowEnvToken") || "S".equals(System.getenv("MAH_GENESIS_SMOKE"));
        if ((token == null || token.trim().isEmpty()) && ambienteTeste) token = System.getenv(ENVIRONMENT_VARIABLE);
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalStateException("Segredo Genesis indisponivel; execucao interrompida.");
        }
        return token.trim();
    }
}

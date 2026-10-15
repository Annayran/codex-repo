package br.zaya.telecontrol.shared.infrastructure.http;

import br.zaya.telecontrol.shared.config.ConfigTelecontrol;

public class Autenticacao {
	public static String dataExpiracaoAccessTokenUTC;

	public static String accessToken;

	public static String env;

	public static void Login() throws Exception {
		ConfigTelecontrol serv = new ConfigTelecontrol();
		accessToken = serv.token;
		env = serv.env;
	}
}


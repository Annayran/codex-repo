package br.zaya.telecontrol.services;

import br.zaya.telecontrol.utils.ConfigTelecontrol;

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

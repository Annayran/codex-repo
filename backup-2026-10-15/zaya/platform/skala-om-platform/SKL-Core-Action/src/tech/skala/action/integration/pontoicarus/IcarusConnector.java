package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sankhya.util.JsonUtils;
import com.sankhya.util.StringUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.core.http.ApiResponse;
import tech.skala.action.core.http.HttpConnection;
import tech.skala.action.core.http.HttpMethod;
import tech.skala.core.commons.dd.DTO;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * 
 * @author cassiomenezes
 * @author hiagoalexandre
 * @author taynacugler
 * @since 2023-04-12
 */
public class IcarusConnector {
	private static final Logger LOGGER = Logger.getLogger("tech.skala.action.integration.pontoicarus.Api");
	private static final Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			.create();
	
	private String										bearerAuth;
	
	public void buildBearerAuthentication(ConfiguracaoIcarus config) throws SkalaModelException {
		LOGGER.info("buildBearerAuthentication");
		
		this.bearerAuth = String.format("Bearer %s", config.getToken());
		validateToken(config);
	}

	private void validateToken(ConfiguracaoIcarus config) throws SkalaModelException {
		LOGGER.info("validateToken");
		
		if (!isTokenAlive(config)) {
			refreshToken(config);
		}
	}

	private boolean isTokenAlive(ConfiguracaoIcarus config) throws SkalaModelException {
		LOGGER.info("isTokenAlive");
		
		if (config.getToken() == null)
			return false;

		RelatorioUsuariosFilterDTO pesquisaDto = new RelatorioUsuariosFilterDTO();
		String json = gson.toJson(pesquisaDto, pesquisaDto.getClass());
		HttpConnection conn = buildConnection("/usuario/relatorioUsuarios/0", json, HttpMethod.POST);
		
		ApiResponse apiResponse = conn.doConnect();
		
		return apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK;
	}

	private void refreshToken(ConfiguracaoIcarus config) throws SkalaModelException {
		LOGGER.info("refreshToken");
		
		AccountCredentialsDTO credentialsDTO = (AccountCredentialsDTO) IcarusMapper.configuracao2AccountCredentialsDTOMapper(config);
		String payload = gson.toJson(credentialsDTO, credentialsDTO.getClass());
		HttpConnection conn = buildConnection("/usuario/logar", payload, HttpMethod.POST);
		
		ApiResponse apiResponse = conn.doConnect();
		if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JsonObject json = JsonUtils.convertStringToJsonObject(apiResponse.getResponse());
			String token = JsonUtils.getString(json, "token");
			
			config.setToken(token);
			config.setNewToken(true);
			
			this.bearerAuth = String.format("Bearer %s", token);
		}
	}

	public ApiResponse postCentroCusto(CentroCustoSaveDTO dto) throws SkalaModelException {
		LOGGER.info("postCentroCusto");

		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/centroCusto", payload, HttpMethod.POST);
				
		return conn.doConnect();
		
	}

	public BigDecimal getIdCentroCusto(CentroCustoPesquisaDTO pesquisaDto) throws SkalaModelException {
		LOGGER.info("getIdCentroCusto");
		
		String payload = gson.toJson(pesquisaDto, pesquisaDto.getClass());
		HttpConnection conn = buildConnection("/centroCusto/pesquisa/0", payload, HttpMethod.POST);
		
		String response = conn.doConnect().getResponse();
		
		JsonObject json = JsonUtils.convertStringToJsonObject(response);
		JsonArray content = JsonUtils.getJsonArray(json, "content");
		if (content == null || content.size() == 0)
			return null;
		
		return JsonUtils.getBigDecimal((JsonObject) content.iterator().next(), "idCentroCusto");
	}

	public void persistIdCentroCusto(final BigDecimal codEmp, final BigDecimal idCentroCusto) throws SkalaModelException {
		LOGGER.info("persistIdCentroCusto");
		
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					EmpresaIcarus empresa = new EmpresaIcarus();
					empresa.setCodEmp(codEmp);
					empresa.setIdCentroCusto(idCentroCusto);
					empresa.persists();
				}
			});
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Centro de Custo.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public ApiResponse postLotacao(LotacaoSaveDTO dto) throws SkalaModelException {
		LOGGER.info("postLotacao");

		//ApiConn.doConnect(bearerAuth, "/lotacao", dto, "POST");
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/lotacao", payload, HttpMethod.POST);

		
		return conn.doConnect();
		
	}

	public BigDecimal getIdLotacao(LotacaoPesquisaDTO pesquisaDto) throws SkalaModelException {
		String payload = gson.toJson(pesquisaDto, pesquisaDto.getClass());
		HttpConnection conn = buildConnection("/lotacao/pesquisa/0", payload, HttpMethod.POST);
		

		String response = conn.doConnect().getResponse();
		
		JsonObject json = JsonUtils.convertStringToJsonObject(response);
		JsonArray content = JsonUtils.getJsonArray(json, "content");
		if (content == null || content.size() == 0)
			return null;
		
		return JsonUtils.getBigDecimal((JsonObject) content.iterator().next(), "idLotacao");
	}

	public ApiResponse postCargo(CargoSaveDTO dto) throws SkalaModelException {
		LOGGER.info("postCargo");

		//ApiConn.doConnect(bearerAuth, "/cargo", dto, "POST");
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/cargo", payload, HttpMethod.POST);
		
		
		return conn.doConnect();
	}

	public BigDecimal getIdCargo(CargoPesquisaDTO pesquisaDto) throws SkalaModelException {
		String payload = gson.toJson(pesquisaDto, pesquisaDto.getClass());
		HttpConnection conn = buildConnection("/cargo/pesquisa/0", payload, HttpMethod.POST);

		String response = conn.doConnect().getResponse();
		
		JsonObject json = JsonUtils.convertStringToJsonObject(response);
		JsonArray content = JsonUtils.getJsonArray(json, "content");
		if (content == null || content.size() == 0)
			return null;
		
		return JsonUtils.getBigDecimal((JsonObject) content.iterator().next(), "idCargo");
	}

	public ApiResponse postRegimeContratacao(RegimeContratacaoSaveDTO dto) throws SkalaModelException {
		LOGGER.info("postRegimeContratacao");
		
		//return ApiConn.doConnect(bearerAuth, "/regimeContratacao", dto, "POST").getResponse();
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/regimeContratacao", payload, HttpMethod.POST);
		
		return conn.doConnect();
	}

	public BigDecimal getIdRegimeContratacao(RegimeContratacaoFilterDTO pesquisaDto) throws SkalaModelException {
		LOGGER.info("getIdRegimeContratacao");
		String payload = gson.toJson(pesquisaDto, pesquisaDto.getClass());
		HttpConnection conn = buildConnection("/regimeContratacao/pesquisa/0", payload, HttpMethod.POST);

		String response = conn.doConnect().getResponse();
		JsonObject json = JsonUtils.convertStringToJsonObject(response);
		JsonArray content = JsonUtils.getJsonArray(json, "content");
		if (content == null || content.size() == 0)
			return null;
		
		return JsonUtils.getBigDecimal((JsonObject) content.iterator().next(), "idRegimeContratacao");
	}

	public ApiResponse postMutuario(MutuarioAlteracaoDTO mutuarioDTO) throws SkalaModelException {
		LOGGER.info("postMutuario");
		
		//return ApiConn.doConnect(bearerAuth, "/mutuario", dto, "POST");
		String payload = gson.toJson(mutuarioDTO, mutuarioDTO.getClass());
		HttpConnection conn = buildConnection("/mutuario", payload, HttpMethod.POST);
		
		return conn.doConnect();
	}

	public ApiResponse postUsuario(UsuarioPasswdDTO dto) throws SkalaModelException {
		LOGGER.info("postUsuario");
		
		//return ApiConn.doConnect(bearerAuth, "/usuario", dto, "POST");
		String payload =  gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/usuario", payload, HttpMethod.POST);
				
		return conn.doConnect();
	}

	public void persistFuncionarioIcarus(final BigDecimal codEmp, final BigDecimal codFunc, final BigDecimal idMutuario, final BigDecimal idPessoa) throws SkalaModelException {
		LOGGER.info("persistFuncionarioIcarus");
		
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					FuncionarioIcarus bean = new FuncionarioIcarus();
					bean.setCodEmp(codEmp);
					bean.setCodFunc(codFunc);
					bean.setIdMutuario(idMutuario);
					bean.setIdPessoa(idPessoa);
					bean.persists();
				}
			});
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Mutuário.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
		
	}

	public BigDecimal getIdPerfil(PerfilPesquisaDTO perfilPesquisaDto) throws SkalaModelException {
		LOGGER.info("getIdPerfil");
		
		String payload = gson.toJson(perfilPesquisaDto, perfilPesquisaDto.getClass());
		HttpConnection conn = buildConnection("/perfil/pesquisa/0", payload, HttpMethod.POST);
	
		String response = conn.doConnect().getResponse();
		JsonObject json = JsonUtils.convertStringToJsonObject(response);
		JsonArray content = JsonUtils.getJsonArray(json, "content");
		if (content == null || content.size() == 0)
			return null;
		
		return JsonUtils.getBigDecimal((JsonObject) content.iterator().next(), "idPerfil");
	}

	public ApiResponse postTurno(TurnoDTO dto) throws SkalaModelException {
		LOGGER.info("postTurno");
		
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/turno", payload, HttpMethod.POST);
				
		return conn.doConnect();
	}

	public ApiResponse postMutuarioTurno(MutuarioTurnoSaveDTO mutuarioTurnoSaveDTO) throws SkalaModelException {
		LOGGER.info("postMutuarioTurno");
		String payload = gson.toJson(mutuarioTurnoSaveDTO, mutuarioTurnoSaveDTO.getClass());
		HttpConnection conn = buildConnection("/colaboradorTurno", payload, HttpMethod.POST);
			
		return conn.doConnect();

	}

	public ApiResponse  postMutuarioDemissao(MutuarioDemissaoDTO dto) throws SkalaModelException {
		LOGGER.info("postMutuarioDemissao");
		String payload = gson.toJson(dto, dto.getClass());
		
		LOGGER.info(payload);
		HttpConnection conn = buildConnection("/mutuario/demitir", payload, HttpMethod.POST);
		
		return conn.doConnect();
	}

	public void persistTurnoIcarus(BigDecimal codEmp, BigDecimal codCargaHor, BigDecimal idTurno) throws SkalaModelException {
		LOGGER.info("persistTurnoIcarus");
		
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					TurnoIcarus bean = new TurnoIcarus();
					bean.setCodEmp(codEmp);
					bean.setCodCargaHor(codCargaHor);
					bean.setIdTurno(idTurno);
					bean.persists();
				}
			});
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Turno.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}
	
	public BigDecimal getIdPessoa(PessoaDTO pesquisaDto) throws SkalaModelException {
		LOGGER.info("getIdPessoa");
		
		BigDecimal idPessoa = null;
		String path = String.format("/pessoa/buscarPorDocumento/%s", pesquisaDto.getDocumento());
		HttpConnection conn = buildConnection(path, null, HttpMethod.GET);
		String response = conn.doConnect().getResponse();
		
		String msg = String.format("response %s", response);
		LOGGER.info(msg);
		
		if (!StringUtils.isEmpty(response)) {
			JsonObject json = JsonUtils.convertStringToJsonObject(response);
			idPessoa = JsonUtils.getBigDecimal(json, "idPessoa");
		}
		
		return idPessoa;
	}

	public ApiResponse postAfastamentoFerias(AfastamentoPontoSaveDTO dto) throws SkalaModelException {
		LOGGER.info("postAfastamentoFerias");
		
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/afastamentoFerias", payload, HttpMethod.POST);

		LOGGER.info(conn.getPayload());

		return conn.doConnect();
	}
	
	public ApiResponse putAfastamentoFerias(DTO dto) throws SkalaModelException {
		LOGGER.info("putAfastamentoFerias");
		
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/afastamentoFerias", payload, HttpMethod.PUT);

		LOGGER.info(conn.getPayload());

		return conn.doConnect();
	}

	public ApiResponse postMotivoAbonoPonto(MotivoAbonoPontoSaveDTO dto) throws SkalaModelException {
		LOGGER.info("postMotivoAbonoPonto");
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/motivoAbonoPonto", payload, HttpMethod.POST);
				
		return conn.doConnect();
	}

	public BigDecimal getIdMotivoAbonoPonto(MotivoAbonoPontoSaveDTO dto) throws SkalaModelException {
		LOGGER.info("getIdMotivoAbonoPonto");
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/motivoAbonoPonto/pesquisa/0", payload, HttpMethod.POST);
		
		String response = conn.doConnect().getResponse();
		JsonObject json = JsonUtils.convertStringToJsonObject(response);
		JsonArray content = JsonUtils.getJsonArray(json, "content");
		if (content == null || content.size() == 0)
			return null;
		
		return JsonUtils.getBigDecimal((JsonObject) content.iterator().next(), "idMotivoAbonoPonto");
	}

	public ApiResponse postAbonoPonto(AbonoPontoSaveDTO dto) throws SkalaModelException {
		LOGGER.info("postAbonoPonto");
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/abonoPonto/abonarPonto", payload, HttpMethod.POST);
				
		return conn.doConnect();
		
	}

	public ApiResponse postAjustePonto(AjustePontoSaveDTO dto) throws SkalaModelException {
		LOGGER.info("postAjustePonto");
			
		String payload = gson.toJson(dto, dto.getClass());
		HttpConnection conn = buildConnection("/ajustePonto/ajustarPonto", payload, HttpMethod.POST);
		
		return conn.doConnect();
	}

	public ApiResponse buscarTodosPontos(BigDecimal idColaborador, Timestamp dataReferencia) throws SkalaModelException {
		LOGGER.info("getBuscarTodosPontos");
		
		SimpleDateFormat frmData = new SimpleDateFormat("yyyy-MM-dd");
		String path = String.format("/ponto/buscarTodosPontos/%s/%s", idColaborador, frmData.format(dataReferencia));
		
		
		HttpConnection conn = buildConnection(path, null, HttpMethod.GET);
		
		return conn.doConnect();
	
	}

	public ApiResponse relatorioPontosSkala(PontoPesquisaDTO pesquisaDto) throws SkalaModelException {
		LOGGER.info("getBuscarTodosPontos");
		
		String payload = gson.toJson(pesquisaDto, pesquisaDto.getClass());

		HttpConnection conn = buildConnection("/ponto/skala", payload, HttpMethod.POST);
		
		return conn.doConnect();
	}

	private HttpConnection buildConnection(String path, String payload, HttpMethod method) {
		Map<String, String> requestProperties = new HashMap<>();
		requestProperties.put("Content-Type", "application/json");
		requestProperties.put("Authorization", bearerAuth);

		return new HttpConnection.Builder("https://backendicarus.pontoicarus.com.br", method)
				.setPath(path).setPayload(payload).setRequestProperties(requestProperties)
				.build();
	}

}

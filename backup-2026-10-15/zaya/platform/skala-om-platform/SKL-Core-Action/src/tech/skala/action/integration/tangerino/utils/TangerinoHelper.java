package tech.skala.action.integration.tangerino.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.sankhya.util.StringUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import tech.skala.action.integration.tangerino.controller.TangerinoController;
import tech.skala.action.integration.tangerino.model.integracao.AdjustmentReasonRecordDTO;
import tech.skala.action.integration.tangerino.model.integracao.Autenticacao;
import tech.skala.action.integration.tangerino.model.integracao.Cargo;
import tech.skala.action.integration.tangerino.model.integracao.Colaborador;
import tech.skala.action.integration.tangerino.model.integracao.DismissDTO;
import tech.skala.action.integration.tangerino.model.integracao.EmployeeFunctionDTO;
import tech.skala.action.integration.tangerino.model.integracao.EmployeeFunctionSimpleDTO;
import tech.skala.action.integration.tangerino.model.integracao.EmployeeReturnDTO;
import tech.skala.action.integration.tangerino.model.integracao.EmployeeWorkScheduleAssignDTO;
import tech.skala.action.integration.tangerino.model.integracao.Escala;
import tech.skala.action.integration.tangerino.model.integracao.EscalaRelacao;
import tech.skala.action.integration.tangerino.model.integracao.LogItem;
import tech.skala.action.integration.tangerino.model.integracao.Setor;
import tech.skala.action.integration.tangerino.model.sistema.MensagemLog;
import tech.skala.action.integration.tangerino.model.sistema.RespostaRequisicao;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class TangerinoHelper {
	private static final Logger					LOGGER = Logger.getLogger("ConversorUtils");
	
	public TangerinoHelper() {
	}

	public static Autenticacao gerenciarAutenticacao() throws Exception {
		Autenticacao autenticacao = new Autenticacao();
		MGECoreParameter.getParameter("TANGERINO_URL");
		autenticacao.setUrl((String) MGECoreParameter.getParameter("TANGERINO_URL"));
		autenticacao.setToken((String) MGECoreParameter.getParameter("TANGERINO_TOKEN"));
		return autenticacao;
	}

	public static void incluirLog(LogItem item) throws Exception {
		if (item.isProducao())
			new LogItem(item.getDescricao(), item.getAtividade(), item.getCategoria(), item.getMensagem()).persists();
		else
			System.out.println(item.toString());
	}

	public static void incluirFuncao(EmployeeFunctionSimpleDTO dto, Autenticacao autenticacao, boolean isProducao) throws SkalaModelException {
		
		try {
			incluirLog(new LogItem("Funcao => CONTEUDO: ", dto.toString(), "C", MensagemLog.INFO, isProducao));

			Gson gson = new Gson();
			String jsonBody = gson.toJson(dto);
			String endpoint = TangerinoConstants.ROTA_FUNCAO;
			autenticacao.setEndpoint(endpoint);
			
			RespostaRequisicao resultado = HttpUtils.executeRequest(autenticacao, "POST", jsonBody);
			if (Boolean.TRUE.equals(resultado.getSucess())){
				incluirLog(new LogItem("Funcao => Resultado: ", resultado.toString(), "C", MensagemLog.OK, isProducao));
				
				EmployeeFunctionDTO funcao = new Gson().fromJson(resultado.getResponse(), EmployeeFunctionDTO.class);
				persistFunction(funcao);

			}else {
				incluirLog(new LogItem("Funcao => Resultado: ", resultado.toString(), "C", MensagemLog.ERRO, isProducao));
			}

		} catch (Exception e) {
			SkalaModelException.throwMe(null, e);
		}
		
	}

	private static void persistFunction(EmployeeFunctionDTO funcaoDTO) {
		JapeSession.SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { funcaoDTO.getExternalId() });
					EntityUpdate entityUpd = new EntityUpdate(DynamicEntityNames.FUNCAO, pk);
					entityUpd.setField("AD_ID_TANGERINO", funcaoDTO.getId());
					entityUpd.setField("AD_SINC_TANGERINO", "N");
					entityUpd.fluidUpdate();
				}
			});
		} catch (Exception e) {
			new LogItem("Erro ao salvar funcao: ", e.getMessage(), "C", MensagemLog.ERRO);
		} finally {
			JapeSession.close(hnd);
		}
		
	}

	public static void incluirCargo(Cargo cargo, Autenticacao autenticacao, boolean isProducao) throws Exception {
		Gson gson = new Gson();
		String jsonBody = gson.toJson(cargo);
		String endpoint = retornarEndpointComServico(TangerinoConstants.ROTA_CARGO, "register");
		autenticacao.setEndpoint(endpoint);
		RespostaRequisicao resultado = HttpUtils.executeRequest(autenticacao, "POST", jsonBody);
		if (resultado.getSucess()){
			incluirLog(new LogItem("Cargo => Resultado: ", resultado.toString(), "C", MensagemLog.OK, isProducao));
			Cargo cargoRetorno = new Gson().fromJson(resultado.getResponse(), Cargo.class);
			if (!StringUtils.isNotEmpty(cargo.getIdTangerino())){
				cargo.setIdTangerino(cargoRetorno.getIdTangerino());
			}
			try{
				cargo.persists();
			}catch (Exception e){
				incluirLog(new LogItem("Cargo => Erro ao Salvar: ", e.getMessage(), "C", MensagemLog.ERRO, isProducao));
			}
		} else {
			incluirLog(new LogItem("Cargo => Resultado: ", resultado.toString(), "C", MensagemLog.ERRO, isProducao));
		}
	}


	public static void incluirSetor(Setor setor, Autenticacao autenticacao, boolean isProducao) throws Exception {
		Gson gson = new Gson();
		String jsonBody = "";
		
		if (StringUtils.isNotEmpty(setor.getIdTangerino())){
			Setor setorBody = new Setor(null, setor.getNomeSetor(), setor.getIdTangerino());
			jsonBody = gson.toJson(setorBody);
		} else {
			jsonBody = gson.toJson(setor);
		}
		
		String endpoint = retornarEndpointComServico(TangerinoConstants.ROTA_LOCAL_SETOR, "register");
		autenticacao.setEndpoint(endpoint);
		RespostaRequisicao resultado = HttpUtils.executeRequest(autenticacao, "POST", jsonBody);
		if (resultado.getSucess()){
			incluirLog(new LogItem("Setor => Resultado: ", resultado.toString(), "C", MensagemLog.OK, isProducao));
			Setor setorRetorno = new Gson().fromJson(resultado.getResponse(), Setor.class);
			
			if (!StringUtils.isNotEmpty(setor.getIdTangerino()))
				setor.setIdTangerino(setorRetorno.getIdTangerino());
			
			try{
				setor.persists();
			} catch (Exception e){
				incluirLog(new LogItem("Setor => Erro ao Salvar: ", e.getMessage(), "C", MensagemLog.ERRO, isProducao));
			}
		} else {
			incluirLog(new LogItem("Setor => Resultado: ", resultado.toString(), "C", MensagemLog.ERRO, isProducao));
		}
	}

	public static void deleteWorkplace(Setor setor, Autenticacao autenticacao, boolean isProducao) throws Exception {
		String endpoint = retornarEndpointComServico(TangerinoConstants.ROTA_LOCAL_SETOR, "delete");
		
		String queryParameter = String.format("?externalId=%s", setor.getCodSetor());
		endpoint = retornarEndpointComServico(endpoint, queryParameter);

		autenticacao.setEndpoint(endpoint);
		RespostaRequisicao resultado = HttpUtils.executeRequest(autenticacao, "DELETE", null);

		if (Boolean.TRUE.equals(resultado.getSucess())){
			incluirLog(new LogItem("Excluir Setor => Resultado: ", resultado.toString(), "C", MensagemLog.OK, isProducao));
		} else {
			incluirLog(new LogItem("Excluir Setor => Resultado: ", resultado.toString(), "C", MensagemLog.ERRO, isProducao));
		}

	}

	public static void incluirEscala(Escala escala, Autenticacao autenticacao, boolean isProducao) throws Exception {
		Gson gson = new Gson();
		String jsonBody = gson.toJson(escala);
		
		String endpoint = retornarEndpointComServico(TangerinoConstants.ROTA_ESCALA, "register");
		autenticacao.setEndpoint(endpoint);
		RespostaRequisicao resultado = HttpUtils.executeRequest(autenticacao, "POST", jsonBody);
		if (Boolean.TRUE.equals(resultado.getSucess())){
			incluirLog(new LogItem("Escala => Resultado: ", resultado.toString(), "C", MensagemLog.OK, isProducao));
			Escala escalaRetorno = new Gson().fromJson(resultado.getResponse(), Escala.class);
			if (!StringUtils.isNotEmpty(escala.getIdTangerino())){
				escala.setIdTangerino(escalaRetorno.getIdTangerino());
			}
			if (StringUtils.isNotEmpty(escalaRetorno.getEscalas())){
				for (EscalaRelacao escalaItem: escalaRetorno.getEscalas() ) {
					List<BigDecimal> turnos = new ArrayList<>();
					if (StringUtils.isNotEmpty(escalaItem.getInicioTurno1())){
						turnos.add(new BigDecimal(1));
					}
					if (StringUtils.isNotEmpty(escalaItem.getInicioTurno2())){
						turnos.add(new BigDecimal(2));
					}
					escalaItem.setTurnos(turnos);
					if (!StringUtils.isNotEmpty(escalaItem.getIdTangerino())){
						escalaItem.setIdTangerino(escalaItem.getIdTangerino());
					}
					escalaItem.setCodCargaHoraria(escala.getCodCargaHoraria());
					try{
						escalaItem.persists();
					}catch (Exception e){
						incluirLog(new LogItem("Escala  => Erro ao Salvar Escala Detalhe: ", e.getMessage(), "C", MensagemLog.ERRO, isProducao));
					}
				}
			}
			try{
				escala.persists();
			}catch (Exception e){
				incluirLog(new LogItem("Escala => Erro ao Salvar Escala: ", e.getMessage(), "C", MensagemLog.ERRO, isProducao));
			}
		}else {
			incluirLog(new LogItem("Escala => Resultado: ", resultado.toString(), "C", MensagemLog.ERRO, isProducao));
		}
	}

	public static void incluirColaborador(Colaborador colaborador, Autenticacao autenticacao, boolean isProducao) throws Exception {
		Gson gson = new Gson();
		String jsonBody = gson.toJson(colaborador);
		incluirLog(new LogItem("Colaborador => JSON: ", jsonBody, "C", MensagemLog.INFO, isProducao));

		String endpoint = retornarEndpointComServico(TangerinoConstants.ROTA_COLABORADOR, "register");
		endpoint = retornarEndpointComServico(endpoint, "?allowUpdate=true");
		autenticacao.setEndpoint(endpoint);
		
		RespostaRequisicao resultado = HttpUtils.executeRequest(autenticacao, "POST", jsonBody);
		if (Boolean.TRUE.equals(resultado.getSucess())){
			incluirLog(new LogItem("Colaborador => Resultado: ", resultado.toString(), "C", MensagemLog.OK, isProducao));
			try{
				EmployeeReturnDTO employeeReturnDTO = new Gson().fromJson(resultado.getResponse(), EmployeeReturnDTO.class);
				
				if (StringUtils.isNotEmpty(employeeReturnDTO.getId())) {
					colaborador.setIdTangerino(employeeReturnDTO.getId());
					colaborador.persists();
				}
				
			}catch (Exception e){
				incluirLog(new LogItem("Colaborador => Erro ao Salvar: ", e.getMessage(), "C", MensagemLog.ERRO, isProducao));
			}
		}else {
			incluirLog(new LogItem("Colaborador => Resultado: ", resultado.toString(), "C", MensagemLog.ERRO, isProducao));
		}
	}

	public static void incluirDemissao(DismissDTO dismissDTO, Autenticacao autenticacao, boolean isProducao) throws Exception {
		Gson gson = new Gson();
		String jsonBody = gson.toJson(dismissDTO);
		incluirLog(new LogItem("Colaborador Demissao => JSON: ", jsonBody, "C", MensagemLog.OK, isProducao));

		String endpoint = retornarEndpointComServico(TangerinoConstants.ROTA_COLABORADOR, "dismiss");
		autenticacao.setEndpoint(endpoint);
		RespostaRequisicao resultado = HttpUtils.executeRequest(autenticacao, "POST", jsonBody);
		if (Boolean.TRUE.equals(resultado.getSucess())){
			incluirLog(new LogItem("Colaborador Demissao => Resultado: ", resultado.toString(), "C", MensagemLog.OK, isProducao));
			try{
				TangerinoController.setSyncComplete(dismissDTO.getCodEmp(), dismissDTO.getCodFunc());
			}catch (Exception e){
				incluirLog(new LogItem("Colaborador Demissao => Erro ao Salvar: ", e.getMessage(), "C", MensagemLog.ERRO, isProducao));
			}
		}else {
			incluirLog(new LogItem("Colaborador Demissao => Resultado: ", resultado.toString(), "C", MensagemLog.ERRO, isProducao));
		}
	}

	public static void incluirNovaCargaHoraria(EmployeeWorkScheduleAssignDTO workScheduleAssing, Autenticacao autenticacao, boolean isProducao) throws Exception {
		Gson gson = new Gson();
		String jsonBody = gson.toJson(workScheduleAssing);
		incluirLog(new LogItem("Colaborador Alteracao de Escala => JSON: ", jsonBody, "C", MensagemLog.INFO, isProducao));
		
		String endpoint = retornarEndpointComServico(TangerinoConstants.ROTA_ATRIBUIR_ESCALA, "register");
		
		autenticacao.setEndpoint(endpoint);
		RespostaRequisicao resultado = HttpUtils.executeRequest(autenticacao, "POST", jsonBody);
		if (Boolean.TRUE.equals(resultado.getSucess())){
			incluirLog(new LogItem("Colaborador Alteracao de Escala => Resultado: ", resultado.toString(), "C", MensagemLog.OK, isProducao));
		}else {
			incluirLog(new LogItem("Colaborador Alteracao Escala => Resultado: ", resultado.toString(), "C", MensagemLog.ERRO, isProducao));
		}
	}

	public static void enviarAjuste(AdjustmentReasonRecordDTO ajusteDTO, Autenticacao autenticacao, boolean isProducao) throws Exception {
		Gson gson = new Gson();
		String jsonBody = gson.toJson(ajusteDTO);
		incluirLog(new LogItem("Ferias => JSON: ", jsonBody, "C", MensagemLog.INFO, isProducao));

		String endpoint = retornarEndpointComServico(TangerinoConstants.ROTA_AJUSTE, "register");
		autenticacao.setEndpoint(endpoint);
		RespostaRequisicao resultado = HttpUtils.executeRequest(autenticacao, "POST", jsonBody);
		if (Boolean.TRUE.equals(resultado.getSucess())){
			incluirLog(new LogItem("Ferias => Resultado: ", resultado.toString(), "C", MensagemLog.OK, isProducao));

			try{
				TangerinoController.setSyncComplete(ajusteDTO.getEmployeeDTO().getCodEmp(), ajusteDTO.getEmployeeDTO().getCodFunc());
			}catch (Exception e){
				incluirLog(new LogItem("Colaborador Demissao => Erro ao Salvar: ", e.getMessage(), "C", MensagemLog.ERRO, isProducao));
			}
		}else {
			incluirLog(new LogItem("Ferias => Resultado: ", resultado.toString(), "C", MensagemLog.ERRO, isProducao));
		}

	}

	private static String retornarEndpointComServico(String endpoint, String servico){
		return endpoint + servico;
	}
	
}

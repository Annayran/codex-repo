package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;
import com.sankhya.util.JsonUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.beans.cargahoraria.IdentificacaoCargaHoraria;
import tech.skala.action.beans.cargo.Cargo;
import tech.skala.action.beans.departamento.Departamento;
import tech.skala.action.beans.funcionario.Funcionario;
import tech.skala.action.beans.funcionario.HistoricoOcorrencia;
import tech.skala.action.beans.registroocorrencia.RegistroOcorrencia;
import tech.skala.action.core.http.ApiResponse;
import tech.skala.action.modules.empresas.Empresa;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DTO;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.util.string.SkalaStringUtils;

public class Sync {
	private static final Logger LOGGER = Logger.getLogger("tech.skala.action.integration.pontoicarus.Api");

	private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
	private static IcarusConnector api;

	private Sync() {
		throw new IllegalStateException("Utility class");
	}

	public static void sendRequest() throws SkalaModelException {

		List<ConfiguracaoIcarus> configuracoes = IcarusFinder.getConfiguracoes();
		for (ConfiguracaoIcarus config : configuracoes) {
			getAuth(config);
			sendRequest(config);
		}

	}

	public static void sendRequestRegistrosPonto() throws SkalaModelException {

		List<ConfiguracaoIcarus> configuracoes = IcarusFinder.getConfiguracoes();
		for (ConfiguracaoIcarus config : configuracoes) {
			getAuth(config);
			sendRequestRegistrosPonto(config);
		}

	}

	private static void getAuth(ConfiguracaoIcarus config) throws SkalaModelException {
		if (api == null)
			api = new IcarusConnector();

		api.buildBearerAuthentication(config);
	}

	private static void sendRequest(ConfiguracaoIcarus config) throws SkalaModelException {

		BigDecimal codigo = config.getCodEmp();
////		incluirCentroCusto(codigo);
		incluirLotacao(codigo);
		incluirCargo(codigo);
		incluirRegimeContratacao(codigo);
//		incluiMutuarios(codigo);
		alteraMutuariosNew(codigo);
		incluiMutuariosNew(codigo);
		incluirMutuarioDemissao(codigo);
		incluirMutuarioDemissaoTransferencia(codigo);
		incluirFerias(codigo);
//		incluirMotivoAbonoPonto(codigo);
////		incluirAjustePonto(codEmp);
		incluirAfastamento(codigo);
		atualizarRetornoAfastamento(codigo);
//		incluirAbono(codigo);

		if (config.isNewToken())
			IcarusDAO.updateToken(config);
	}

	private static void sendRequestRegistrosPonto(ConfiguracaoIcarus config) throws SkalaModelException {

		BigDecimal codigo = config.getCodEmp();
		importarRegistrosDePonto(codigo);

		if (config.isNewToken())
			IcarusDAO.updateToken(config);

	}

	public static void incluirCentroCusto(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		CentroCustoSaveDTO dto;
		CentroCustoPesquisaDTO pesquisaDto;
		BigDecimal idCentroCusto;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			List<Empresa> beans = IcarusDAO.getEmpresas(codEmp);
			for (Empresa bean : beans) {

				dto = (CentroCustoSaveDTO) IcarusMapper.empresa2CentroCustoDtoMapper(bean);

				api.postCentroCusto(dto);

				pesquisaDto = (CentroCustoPesquisaDTO) IcarusMapper.empresa2CentroCustoPesquisaDtoMapper(bean);
				idCentroCusto = api.getIdCentroCusto(pesquisaDto);

				if (idCentroCusto != null)
					api.persistIdCentroCusto(((DynamicVO) beans).asBigDecimal(SklFieldName.CODEMP), idCentroCusto);

			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Centro de Custo.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public static void incluirLotacao(final BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		LotacaoSaveDTO dto;
		LotacaoPesquisaDTO pesquisaDto;
		BigDecimal idLotacao;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			List<Departamento> beans = IcarusDAO.getDepartamentos(codEmp);
			for (Departamento bean : beans) {

				dto = (LotacaoSaveDTO) IcarusMapper.departamento2LotacaoDtoMapper(bean);
				api.postLotacao(dto);

				pesquisaDto = (LotacaoPesquisaDTO) IcarusMapper.departamento2LotacaoPesquisaDtoMapper(bean);
				idLotacao = api.getIdLotacao(pesquisaDto);

				if (idLotacao != null)
					IcarusDAO.persistIdLotacao(codEmp, bean.getCodDep(), idLotacao);

			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Lotação.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public static void incluirCargo(final BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		CargoSaveDTO dto;
		CargoPesquisaDTO pesquisaDto;
		BigDecimal idCargo;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			List<Cargo> beans = IcarusDAO.getCargo(codEmp);

			for (Cargo bean : beans) {

				dto = (CargoSaveDTO) IcarusMapper.cargo2CargoDtoMapper(bean);
				api.postCargo(dto);

				pesquisaDto = (CargoPesquisaDTO) IcarusMapper.cargo2CargoPesquisaDtoMapper(bean);
				idCargo = api.getIdCargo(pesquisaDto);

				if (idCargo != null)
					IcarusDAO.persistIdCargo(codEmp, bean.getCodCargo(), idCargo);

			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Cargo.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public static void incluirRegimeContratacao(final BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		RegimeContratacaoSaveDTO dto;
		RegimeContratacaoFilterDTO pesquisaDto = null;
		BigDecimal idRegimeContratacao;
		// String regimeJSON = null;
		String response = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			List<RegimeContratacaoIcarus> beans = IcarusDAO.getContratacoes(codEmp);
			for (RegimeContratacaoIcarus bean : beans) {
				dto = IcarusMapper.regime2RegimeContratacaoSaveDtoMapper(bean);

				response = api.postRegimeContratacao(dto).getResponse();

				pesquisaDto = IcarusMapper.regime2RegimeContratacaoFilterDtoMapper(bean);

				idRegimeContratacao = api.getIdRegimeContratacao(pesquisaDto);

				if (idRegimeContratacao != null)
					IcarusDAO.persistIdRegimeContratacao(codEmp, bean.getCodigo(), idRegimeContratacao);

			}

		} catch (Exception e) {
			String msg = "Não foi possível incluir Regime de Contratação.";
			if (pesquisaDto != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, pesquisaDto.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private static void alteraMutuariosNew(BigDecimal codigo) throws SkalaModelException {
		SessionHandle hnd = null;

		MutuarioAlteracaoDTO mutuarioDTO = null;
		ApiResponse apiResponse = null;
		String msg;

		try {
			hnd = JapeSession.open();

			List<Funcionario> funcionarios = IcarusDAO.getFuncionariosAlteracao(codigo);
			for (Funcionario funcionario : funcionarios) {
				msg = String.format("Atualizacao Funcionario %s-%s", funcionario.getMatricula(),
						funcionario.getNomeFunc());
				LOGGER.info(msg);

				mutuarioDTO = (MutuarioAlteracaoDTO) buildMutuarioDTO(codigo, funcionario);

				apiResponse = api.postMutuario(mutuarioDTO);
				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK)
					IcarusDAO.persistsDataAtualizacaoMutuario(funcionario);
			}

		} catch (Exception e) {
			msg = "Não foi possível atualizar Funcionário.";

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public static void incluiMutuariosNew(final BigDecimal codigo) throws SkalaModelException {
		SessionHandle hnd = null;

		MutuarioAlteracaoDTO mutuarioDTO = null;
		BigDecimal idPessoa;
		String msg;
		ApiResponse apiResponse = null;
		String response = null;

		try {
			hnd = JapeSession.open();

			List<Funcionario> funcionarios = IcarusDAO.getFuncionarios(codigo);
			for (Funcionario funcionario : funcionarios) {
				msg = String.format("Inclusao Funcionario %s-%s %s-%s", funcionario.getCodEmp(),
						funcionario.getCodFunc(), funcionario.getMatricula(), funcionario.getNomeFunc());
				LOGGER.info(msg);

				mutuarioDTO = (MutuarioAlteracaoDTO) buildMutuarioDTO(codigo, funcionario);

				if (mutuarioDTO.getPessoa().getIdPessoa() == null) {
					idPessoa = api.getIdPessoa(mutuarioDTO.getPessoa());
					mutuarioDTO.getPessoa().setIdPessoa(idPessoa);

					msg = String.format("idPessoa %s", idPessoa);
					LOGGER.info(msg);
				}

				apiResponse = api.postMutuario(mutuarioDTO);
				response = apiResponse.getResponse();

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
					JsonObject json = JsonUtils.convertStringToJsonObject(response);
					BigDecimal idMutuario = JsonUtils.getBigDecimal(json, "idMutuario");

					JsonObject jsonPessoa = JsonUtils.getJsonObject(json, "pessoa");
					idPessoa = JsonUtils.getBigDecimal(jsonPessoa, "idPessoa");

					FuncionarioIcarus funcionarioIcarus = new FuncionarioIcarus();
					funcionarioIcarus.setCodEmp(funcionario.getCodEmp());
					funcionarioIcarus.setCodFunc(funcionario.getCodFunc());
					funcionarioIcarus.setIdMutuario(idMutuario);
					funcionarioIcarus.setIdPessoa(idPessoa);
					IcarusDAO.persistsFuncionarioIcarus(funcionarioIcarus);

					TurnoIcarus turnoIcarus = IcarusDAO.getTurnoIcarusByPk(funcionario.getCodCargaHor(), codigo);
					TurnoIdDTO turnoIdDTO = IcarusMapper.turnoIcarus2TurnoIdDtoMapper(turnoIcarus);

					Timestamp dtAdm = TimeUtils.clearTime(funcionario.getDtAdm());
					dtAdm = TimeUtils.dataAdd(dtAdm, 12, Calendar.HOUR_OF_DAY);

					MutuarioIdDTO mutuarioIdDTO = new MutuarioIdDTO();
					mutuarioIdDTO.setIdMutuario(idMutuario);

					MutuarioTurnoSaveDTO mutuarioTurnoSaveDTO = new MutuarioTurnoSaveDTO();
					mutuarioTurnoSaveDTO.setDataInicio(dtAdm);
					mutuarioTurnoSaveDTO.setTurnoDiaInicio(TipoTurnoDiaEnum.SEGUNDA.toString());
					mutuarioTurnoSaveDTO.setMutuario(mutuarioIdDTO);
					mutuarioTurnoSaveDTO.setTurno(turnoIdDTO);

					api.postMutuarioTurno(mutuarioTurnoSaveDTO);

					PerfilPesquisaDTO perfilPesquisaDto = new PerfilPesquisaDTO();
					BigDecimal idPerfil = api.getIdPerfil(perfilPesquisaDto);

					UsuarioPasswdDTO usuarioPasswdDTO = (UsuarioPasswdDTO) IcarusMapper
							.funcionarioIcarus2UsuarioPasswdDtoMapper(funcionario, funcionarioIcarus, idPerfil);

					apiResponse = api.postUsuario(usuarioPasswdDTO);

				} else {
					throw new SkalaModelException(response);
				}
			}

		} catch (Exception e) {
			msg = "Não foi possível incluir Funcionário.";

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private static DTO buildMutuarioDTO(final BigDecimal codigo, final Funcionario funcionario)
			throws RecordNotFoundException {
		MutuarioAlteracaoDTO mutuarioDTO = null;

		FuncionarioIcarus funcionarioIcarus = IcarusDAO.getFuncionarioIcarusByPk(funcionario.getCodEmp(),
				funcionario.getCodFunc());
		EmpresaIcarus empresaIcarus = IcarusDAO.getEmpresaIcarusByPk(funcionario.getCodEmp());

		CargoIcarus cargoIcarus = IcarusDAO.getCargoIcarusByPk(funcionario.getCodCargo(), codigo);
		DepartamentoIcarus departamentoIcarus = IcarusDAO.getDepartamentoIcarusByPk(funcionario.getCodDep(), codigo);

		String codigoRegimeTrabalho = IcarusDAO.getCodigoRegimeTrabalho(funcionario.getRegimeTrab());
		RegimeContratacaoIcarus regimeContratacaoIcarus = IcarusDAO.getRegimeContratacaoIcarusByPk(codigo,
				codigoRegimeTrabalho);

		mutuarioDTO = (MutuarioAlteracaoDTO) IcarusMapper.funcionario2MutuarioDtoMapper(funcionario, funcionarioIcarus,
				empresaIcarus, cargoIcarus, departamentoIcarus, regimeContratacaoIcarus);

		return mutuarioDTO;
	}

	private static DTO buildMutuarioDTO(final BigDecimal codEmp, final BigDecimal codFunc)
			throws RecordNotFoundException {
		MutuarioAlteracaoDTO mutuarioDTO = null;

		Funcionario funcionario = IcarusDAO.getFuncionarioByPk(codEmp, codFunc);
		FuncionarioIcarus funcionarioIcarus = IcarusDAO.getFuncionarioIcarusByPk(codEmp, codFunc);
		EmpresaIcarus empresaIcarus = IcarusDAO.getEmpresaIcarusByPk(codEmp);

		// CODIGO DA EMPRESA NO ICARUS
		// AGLUTINADOR DE CENTROS DE RESULTADO
		// REPRESENTA AS EMPRESAS PRINCIAIS DO GRUPO: 1 E 2
		BigDecimal codigo = empresaIcarus.getCodigo();

		CargoIcarus cargoIcarus = IcarusDAO.getCargoIcarusByPk(funcionario.getCodCargo(), codigo);
		DepartamentoIcarus departamentoIcarus = IcarusDAO.getDepartamentoIcarusByPk(funcionario.getCodDep(), codigo);

		String codigoRegimeTrabalho = IcarusDAO.getCodigoRegimeTrabalho(funcionario.getRegimeTrab());
		RegimeContratacaoIcarus regimeContratacaoIcarus = IcarusDAO.getRegimeContratacaoIcarusByPk(codigo,
				codigoRegimeTrabalho);

		mutuarioDTO = (MutuarioAlteracaoDTO) IcarusMapper.funcionario2MutuarioDtoMapper(funcionario, funcionarioIcarus,
				empresaIcarus, cargoIcarus, departamentoIcarus, regimeContratacaoIcarus);

		return mutuarioDTO;
	}

	public static void incluiMutuarios(final BigDecimal codigo) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		BigDecimal idPessoa;
		MutuarioAlteracaoDTO mutuarioDTO = null;
		ApiResponse apiResponse = null;
		String response = null;
		String msg;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append(
					"this.DTDEM IS NULL AND this.SITUACAO NOT IN (0,8,9) AND this.VINCULO NOT IN (2,30,80,90,95,99) ");
			sbWhere.append(
					"AND NOT EXISTS(SELECT 1 FROM AD_PLTPIFUN T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codigo);
			for (DynamicVO vo : vos) {
				msg = String.format("%s %s - %s", vo.asBigDecimal(SklFieldName.CODEMP),
						vo.asBigDecimal(SklFieldName.CODFUNC), vo.asString("NOMEFUNC"));
				LOGGER.info(msg);

				mutuarioDTO = (MutuarioAlteracaoDTO) buildMutuarioDTO(vo.asBigDecimal(SklFieldName.CODEMP),
						vo.asBigDecimal(SklFieldName.CODFUNC));

				if (mutuarioDTO.getPessoa().getIdPessoa() == null) {
					idPessoa = api.getIdPessoa(mutuarioDTO.getPessoa());
					mutuarioDTO.getPessoa().setIdPessoa(idPessoa);

					msg = String.format("idPessoa %s", idPessoa);
					LOGGER.info(msg);
				}

				msg = String.format("jsonPessoaDTO %s", mutuarioDTO);
				LOGGER.info(msg);

				apiResponse = api.postMutuario(mutuarioDTO);
				response = apiResponse.getResponse();

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
					LOGGER.info("Alteracao efetuada com sucesso");

					JsonObject json = JsonUtils.convertStringToJsonObject(response);
					BigDecimal idMutuario = JsonUtils.getBigDecimal(json, "idMutuario");

					JsonObject jsonPessoa = JsonUtils.getJsonObject(json, "pessoa");
					idPessoa = JsonUtils.getBigDecimal(jsonPessoa, "idPessoa");

					api.persistFuncionarioIcarus(vo.asBigDecimal(SklFieldName.CODEMP),
							vo.asBigDecimal(SklFieldName.CODFUNC), idMutuario, idPessoa);

				} else {
					throw new SkalaModelException(response);
				}

			}
		} catch (Exception e) {
			msg = "Não foi possível incluir Funcionário.";
			if (mutuarioDTO != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, mutuarioDTO.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private static void incluirMutuarioDemissao(final BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		MutuarioDemissaoDTO dto = null;

		String dtoJSON = null;
		ApiResponse apiResponse = null;
		String response = null;
		String msg;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			LinkedList<Funcionario> funcionarios = (LinkedList<Funcionario>) IcarusDAO.getFuncionariosDemitidos(codEmp);
			msg = String.format("Funcionarios demitidos %d", funcionarios.size());
			LOGGER.info(msg);
			for (Funcionario funcionario : funcionarios) {
				dto = (MutuarioDemissaoDTO) IcarusMapper.funcionario2MutuarioDemissaoDtoMapper(funcionario);
				dto.setIdMutuario(IcarusFinder.getIdMutuario(funcionario.getCodEmp(), funcionario.getCodFunc()));

				apiResponse = api.postMutuarioDemissao(dto);

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
					IcarusDAO.persistDemissaoIcarus(funcionario.getCodEmp(), funcionario.getCodFunc(),
							funcionario.getDtDem());

					atualizaRegistroPonto(funcionario.getCodEmp(), funcionario.getCodFunc());
				}
			}

		} catch (Exception e) {
			msg = "Não foi possível incluir Demissão.";

			if (dto != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, dto.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private static void incluirMutuarioDemissaoTransferencia(final BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;

		MutuarioDemissaoDTO dto = null;

		ApiResponse apiResponse = null;
		String msg;

		try {
			hnd = JapeSession.open();

			LinkedList<Funcionario> funcionarios = (LinkedList<Funcionario>) IcarusDAO
					.getFuncionariosTransferidos(codEmp);
			msg = String.format("Funcionarios transferidos %d", funcionarios.size());
			LOGGER.info(msg);
			for (Funcionario funcionario : funcionarios) {
				dto = (MutuarioDemissaoDTO) IcarusMapper.funcionario2MutuarioDemissaoDtoMapper(funcionario);
				dto.setIdMutuario(IcarusFinder.getIdMutuario(funcionario.getCodEmp(), funcionario.getCodFunc()));

				apiResponse = api.postMutuarioDemissao(dto);

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
					IcarusDAO.persistDemissaoIcarus(funcionario.getCodEmp(), funcionario.getCodFunc(),
							funcionario.getDtDem());

					atualizaRegistroPonto(funcionario.getCodEmp(), funcionario.getCodFunc());
				}
			}
		} catch (Exception e) {
			msg = "Não foi possível incluir Demissão por Transferência.";

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public static void alteraMutuario(final BigDecimal codEmp, final BigDecimal codFunc) throws SkalaModelException {
		SessionHandle hnd = null;

		MutuarioAlteracaoDTO mutuarioDTO = null;
//		String dtoJSON = null;
		ApiResponse apiResponse = null;
		String response = null;

		try {
			hnd = JapeSession.open();

			EmpresaIcarus empresaIcarus = IcarusDAO.getEmpresaIcarusByPk(codEmp);

			// CODIGO DA EMPRESA NO ICARUS
			// AGLUTINADOR DE CENTROS DE RESULTADO
			// REPRESENTA AS EMPRESAS PRINCIAIS DO GRUPO: 1 E 2
			BigDecimal codigo = empresaIcarus.getCodigo();

			mutuarioDTO = (MutuarioAlteracaoDTO) buildMutuarioDTO(codEmp, codFunc);

			ConfiguracaoIcarus config = IcarusDAO.getConfiguracaoEmpresa(codigo);
			getAuth(config);

			apiResponse = api.postMutuario(mutuarioDTO);
			response = apiResponse.getResponse();

			if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK)
				LOGGER.info("Alteracao efetuada com sucesso");

		} catch (Exception e) {
			String msg = String.format("Não foi possível alterar o Colaborador %s - %s", codEmp, codFunc);

			if (mutuarioDTO != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, mutuarioDTO.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public static void alteraUsuario(BigDecimal codEmp, BigDecimal codFunc) throws SkalaModelException {
		SessionHandle hnd = null;

		UsuarioPasswdDTO dto = null;
//		String dtoJSON = null;
		ApiResponse apiResponse = null;
		String response = null;

		try {
			hnd = JapeSession.open();

			Funcionario funcionario = IcarusDAO.getFuncionarioByPk(codEmp, codFunc);
			FuncionarioIcarus funcionarioIcarus = IcarusDAO.getFuncionarioIcarusByPk(codEmp, codFunc);
			getAuthFromCodEmp(codEmp);

			PerfilPesquisaDTO perfilPesquisaDto = new PerfilPesquisaDTO();
//			String JSON = gson.toJson(perfilPesquisaDto, perfilPesquisaDto.getClass());

			BigDecimal idPerfil = api.getIdPerfil(perfilPesquisaDto);

//			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

			dto = (UsuarioPasswdDTO) IcarusMapper.funcionarioIcarus2UsuarioPasswdDtoMapper(funcionario,
					funcionarioIcarus, idPerfil);

//			dtoJSON = gson.toJson(dto, dto.getClass());

			apiResponse = api.postUsuario(dto);
			response = apiResponse.getResponse();

			if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK)
				LOGGER.info("Alteracao efetuada com sucesso");

		} catch (Exception e) {
			String msg = String.format("Não foi possível alterar o Colaborador %s - %s", codEmp, codFunc);

			if (dto != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, dto.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public static void incluiTurno(BigDecimal codCargaHor) throws SkalaModelException {
		List<ConfiguracaoIcarus> configuracoes = IcarusFinder.getConfiguracoes();

		for (ConfiguracaoIcarus config : configuracoes) {
			getAuth(config);
			incluirTurno(codCargaHor, config);
		}

	}

	public static void incluirTurno(BigDecimal codCargaHor, ConfiguracaoIcarus config) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		String dtoJSON = null;
		String msg;

		if (codCargaHor == null)
			return;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			BigDecimal codEmp = config.getCodEmp();

			dtoJSON = (codCargaHor.intValue() == 196) ? incluirTurnoMarshall(codCargaHor, codEmp)
					: incluirTurnoNormal(codCargaHor, codEmp);

		} catch (Exception e) {
			msg = "Não foi possível incluir Turno.";

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private static String incluirTurnoNormal(BigDecimal codCargaHor, BigDecimal codEmp) throws Exception {
		IdentificacaoCargaHoraria bean;
		LinkedList<TurnoDiaIcarus> dias;
		LinkedList<TurnoPeriodoIcarus> periodos;
		TurnoDiaDTO turnoDiaDTO;
		LinkedList<TurnoDiaDTO> turnoDias;
		TurnoPeriodoDTO turnoPeriodoDTO;
		LinkedList<TurnoPeriodoDTO> turnoPeriodos;
		TurnoDTO dto = null;
		String dtoJSON = null;

		String msg;
		ApiResponse apiResponse = null;
		String response = null;

		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("this.ATIVO = 'S' AND this.CODCARGAHOR > 0 ");
		sbWhere.append(
				"AND NOT EXISTS(SELECT 1 FROM AD_PLTPITURNO T WHERE T.CODCARGAHOR = this.CODCARGAHOR AND T.CODEMP = ?) ");
		if (codCargaHor.compareTo(BigDecimal.ZERO) > 0)
			sbWhere.append("AND this.CODCARGAHOR = ? ");
		else
			sbWhere.append("AND this.CODCARGAHOR = -1 ");

		JapeWrapper dao = JapeFactory.dao(IdentificacaoCargaHoraria.ENTITY_NAME);
		Collection<DynamicVO> vos;

		vos = (codCargaHor != null) ? dao.find(sbWhere.toString(), codEmp, codCargaHor) : dao.find(sbWhere.toString());
		for (DynamicVO vo : vos) {
			bean = new IdentificacaoCargaHoraria();
			bean.loadByVO(vo);

			dto = (TurnoDTO) IcarusMapper.identificacaoCargaHoraria2TurnoDtoMapper(bean);

			dias = (LinkedList<TurnoDiaIcarus>) IcarusFinder.getDiasFromCargaHoraria(bean.getCodCargaHor(), codEmp);
			turnoDias = new LinkedList<>();

			BigDecimal horaEntSai = null;
			String horaFormatada = calcularViradaTurno(horaEntSai);
			for (TurnoDiaIcarus dia : dias) {
				turnoDiaDTO = IcarusMapper.turnoDia2TurnoDiaDtoMapper(dia);
				turnoDiaDTO.setJornadaFeriado(SimNaoEnum.toString(false));
				turnoDiaDTO.setLimiteHE50("02:00");

				periodos = (LinkedList<TurnoPeriodoIcarus>) IcarusFinder
						.getPeriodosFromCargaHoraria(bean.getCodCargaHor(), dia.getDiaSem(), codEmp);
				turnoPeriodos = new LinkedList<>();

				BigDecimal ordenacao = BigDecimal.ZERO;
				for (TurnoPeriodoIcarus periodo : periodos) {
					ordenacao = ordenacao.add(BigDecimal.ONE);

					turnoPeriodoDTO = IcarusMapper.turnoPeriodo2TurnoPeriodoDtoMapper(periodo);
					turnoPeriodoDTO.setOrdenacao(ordenacao);

					turnoPeriodos.add(turnoPeriodoDTO);

					horaEntSai = periodo.getHoraEntSai();
				}
				if (horaEntSai != null)
					horaFormatada = calcularViradaTurno(horaEntSai);

				msg = String.format("diaSem %s horaEntSai %s horaFormatada %s", dia.getDiaSem(), horaEntSai,
						horaFormatada);
				LOGGER.info(msg);

				turnoDiaDTO.setTurnoPeriodos(turnoPeriodos);
				turnoDias.add(turnoDiaDTO);
			}

			for (TurnoDiaDTO turnoDia : turnoDias)
				turnoDia.setViradaTurno(horaFormatada);

			dto.setTurnoDias(turnoDias);

			dtoJSON = gson.toJson(dto, dto.getClass());

			apiResponse = api.postTurno(dto);
			response = apiResponse.getResponse();

			if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
				JsonObject json = JsonUtils.convertStringToJsonObject(response);
				BigDecimal idTurno = JsonUtils.getBigDecimal(json, "idTurno");

				api.persistTurnoIcarus(codEmp, codCargaHor, idTurno);
			} else {
				msg = String.format("responseCode %s", apiResponse.getResponseCode());
				msg = SkalaStringUtils.appendWithSeparator(msg, apiResponse.getResponse());
				msg = SkalaStringUtils.appendWithSeparator(msg, dtoJSON);

				throw new SkalaModelException(msg);
			}

		}

		return dtoJSON;
	}

	private static String incluirTurnoMarshall(BigDecimal codCargaHor, BigDecimal codEmp) throws Exception {
		IdentificacaoCargaHoraria bean;
		LinkedList<TurnoDiaIcarus> dias;
		LinkedList<TurnoPeriodoIcarus> periodos;
		TurnoDiaDTO turnoDiaDTO;
		LinkedList<TurnoDiaDTO> turnoDias;
		TurnoPeriodoDTO turnoPeriodoDTO;
		LinkedList<TurnoPeriodoDTO> turnoPeriodos;
		TurnoDTO dto;
		String dtoJSON = null;

		ApiResponse apiResponse = null;
		String response = null;

		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("this.ATIVO = 'S' AND this.CODCARGAHOR > 0 ");
		sbWhere.append("AND NOT EXISTS(SELECT 1 FROM AD_PLTPITURNO T WHERE T.CODCARGAHOR = this.CODCARGAHOR) ");
		if (codCargaHor.compareTo(BigDecimal.ZERO) > 0)
			sbWhere.append("AND this.CODCARGAHOR = ? ");
		else
			sbWhere.append("AND this.CODCARGAHOR = -1 ");

		JapeWrapper dao = JapeFactory.dao(IdentificacaoCargaHoraria.ENTITY_NAME);
		Collection<DynamicVO> vos = (codCargaHor != null) ? dao.find(sbWhere.toString(), codCargaHor)
				: dao.find(sbWhere.toString());
		for (DynamicVO vo : vos) {
			bean = new IdentificacaoCargaHoraria();
			bean.loadByVO(vo);

			dto = (TurnoDTO) IcarusMapper.identificacaoCargaHoraria2TurnoDtoMapper(bean);

			dias = (LinkedList<TurnoDiaIcarus>) IcarusFinder.getDiasFromCargaHoraria(bean.getCodCargaHor(), codEmp);
			turnoDias = new LinkedList<>();
			String horaFormatada = null;
			for (TurnoDiaIcarus dia : dias) {
				turnoDiaDTO = IcarusMapper.turnoDia2TurnoDiaDtoMapper(dia);
				turnoDiaDTO.setJornadaFeriado(SimNaoEnum.toString(false));
				turnoDiaDTO.setLimiteHE50("02:00");

				periodos = (LinkedList<TurnoPeriodoIcarus>) IcarusFinder
						.getPeriodosFromCargaHoraria(bean.getCodCargaHor(), dia.getDiaSem(), codEmp);
				turnoPeriodos = new LinkedList<>();

				BigDecimal ordenacao = BigDecimal.ZERO;
				BigDecimal horaEntSai = null;
				for (TurnoPeriodoIcarus periodo : periodos) {
					ordenacao = ordenacao.add(BigDecimal.ONE);

					turnoPeriodoDTO = IcarusMapper.turnoPeriodo2TurnoPeriodoDtoMapper(periodo);
					turnoPeriodoDTO.setOrdenacao(ordenacao);

					turnoPeriodos.add(turnoPeriodoDTO);

					horaEntSai = periodo.getHoraEntSai();
				}
				horaFormatada = calcularViradaTurno(horaEntSai);

				turnoDiaDTO.setTurnoPeriodos(turnoPeriodos);
				turnoDias.add(turnoDiaDTO);
			}

			for (TurnoDiaDTO turnoDia : turnoDias)
				turnoDia.setViradaTurno(horaFormatada);

			dto.setTurnoDias(turnoDias);

//			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
			dtoJSON = gson.toJson(dto, dto.getClass());

			apiResponse = api.postTurno(dto);

			if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
				JsonObject json = JsonUtils.convertStringToJsonObject(response);
				BigDecimal idTurno = JsonUtils.getBigDecimal(json, "idTurno");

				api.persistTurnoIcarus(codEmp, codCargaHor, idTurno);
			}

		}

		return dtoJSON;
	}

	private static String calcularViradaTurno(BigDecimal horaEntSai) throws SkalaModelException {
		String horaFormatada = "23:59";

		if (horaEntSai == null)
			return horaFormatada;

		try {
			// Somar 6 horas na hora final do turno para definir a virada do turno
			// Desta forma, se funcionario trabalhar alem do horario sistema ira calcular
			// hora extra
			horaEntSai = horaEntSai.add(BigDecimal.valueOf(600L));
			if (horaEntSai.longValue() == 2400L)
				return "23:59";

			if (horaEntSai.longValue() > 2400L)
				horaEntSai = horaEntSai.subtract(BigDecimal.valueOf(2400L));

			horaEntSai = horaEntSai.multiply(BigDecimal.valueOf(100L), BigDecimalUtil.MATH_CTX);

			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			Timestamp time = TimeUtils.bigDecimal2Timestamp(horaEntSai);

			horaFormatada = timeFormat.format(time);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível determinar a virada de turno.", e);
		}

		return horaFormatada;
	}

	public static void incluiColaboradorTurno(BigDecimal codCargaHor) throws SkalaModelException {
		List<ConfiguracaoIcarus> configuracoes = IcarusFinder.getConfiguracoes();

		for (ConfiguracaoIcarus config : configuracoes) {
			getAuth(config);
			incluirColaboradorTurno(codCargaHor, config);
		}

	}

	private static void incluirColaboradorTurno(final Funcionario funcionario) {

		BigDecimal codCargaHor = funcionario.getCodCargaHor();

	}

	public static void incluirColaboradorTurno(BigDecimal codCargaHor, ConfiguracaoIcarus config)
			throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		BigDecimal codEmp;
		BigDecimal codFunc;
		Timestamp dtAdm;
		Timestamp turnoDataInicio;
		BigDecimal idMutuario;
		MutuarioIdDTO mutuarioIdDTO;
		TurnoIdDTO turnoIdDTO;
		MutuarioTurnoSaveDTO mutuarioTurnoSaveDTO = null;
//		String dtoJSON = null;
		String response = null;
		String msg;

		if (codCargaHor == null)
			return;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			JapeWrapper funcionarioDAO = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);

//			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

			Timestamp dataInicio = TimeUtils.buildData(19, 4, 2023);
			dataInicio = TimeUtils.dataAdd(dataInicio, 12, Calendar.HOUR_OF_DAY);

			List<Integer> turnosMarshall = Arrays.asList(193, 196, 197, 198);

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append(
					"this.DTDEM IS NULL AND this.SITUACAO NOT IN (0,8,9) AND this.VINCULO NOT IN (2,30,80,90,95,99) ");
			sbWhere.append("AND this.DISPENSAPONTO = 'N' ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIFUN T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC AND T.IDMUTUARIO > 0) ");
//			if (turnosMarshall.contains(codCargaHor.intValue()))
//				sbWhere.append("AND (this.AD_TURNOMARSHALL = ?) ");
//			else
//				sbWhere.append("AND (this.CODCARGAHOR > 0 AND this.CODCARGAHOR = ?) ");
			sbWhere.append("AND (this.CODCARGAHOR > 0 AND this.CODCARGAHOR = ?) ");
//			sbWhere.append("AND this.MATRICULA = 390161");

			JapeWrapper dao = JapeFactory.dao(TurnoIcarus.ENTITY_NAME);
			Collection<DynamicVO> vos = dao.find("this.CODCARGAHOR = ? AND this.CODEMP = ?", codCargaHor,
					config.getCodEmp());
			for (DynamicVO vo : vos) {
				turnoIdDTO = new TurnoIdDTO();
				turnoIdDTO.setIdTurno(vo.asBigDecimal("IDTURNO"));

//				Collection<DynamicVO> funcionariosVO = funcionarioDAO.find(sbWhere.toString(),
//						(turnosMarshall.contains(codCargaHor.intValue()))
//								? IcarusFinder.getTurnoEscalaMarshall(codCargaHor)
//								: codCargaHor);
				Collection<DynamicVO> funcionariosVO = funcionarioDAO.find(sbWhere.toString(), codCargaHor);

				for (DynamicVO funcionarioVO : funcionariosVO) {
					codEmp = funcionarioVO.asBigDecimal(SklFieldName.CODEMP);
					codFunc = funcionarioVO.asBigDecimal(SklFieldName.CODFUNC);

					dtAdm = TimeUtils.clearTime(funcionarioVO.asTimestamp("DTADM"));
					dtAdm = TimeUtils.dataAdd(dtAdm, 12, Calendar.HOUR_OF_DAY);

					if (dtAdm.compareTo(dataInicio) > 0)
						turnoDataInicio = dtAdm;
					else
						turnoDataInicio = dataInicio;

					idMutuario = IcarusFinder.getIdMutuario(codEmp, codFunc);

					mutuarioIdDTO = new MutuarioIdDTO();
					mutuarioIdDTO.setIdMutuario(idMutuario);

					mutuarioTurnoSaveDTO = new MutuarioTurnoSaveDTO();
					mutuarioTurnoSaveDTO.setDataInicio(turnoDataInicio);
					mutuarioTurnoSaveDTO.setTurnoDiaInicio(TipoTurnoDiaEnum.SEGUNDA.toString());
					mutuarioTurnoSaveDTO.setMutuario(mutuarioIdDTO);
					mutuarioTurnoSaveDTO.setTurno(turnoIdDTO);

//					dtoJSON = gson.toJson(mutuarioTurnoSaveDTO, mutuarioTurnoSaveDTO.getClass());

					response = api.postMutuarioTurno(mutuarioTurnoSaveDTO).getResponse();

					if (response.contains("responseCode"))
						throw new SkalaModelException(response);

				}

			}
		} catch (Exception e) {
			msg = "Não foi possível incluir vinculo Colaborador x Turno.";
			////
			if (mutuarioTurnoSaveDTO != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, mutuarioTurnoSaveDTO.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private static void incluirFerias(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		AfastamentoPontoSaveDTO dto = null;
		BigDecimal idMutuario = null;
		MutuarioIdDTO mutuarioIdDTO = null;
		ApiResponse apiResponse = null;
//		String dtoJSON = null;
		String response = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			List<RegistroOcorrencia> beans = IcarusDAO.getFerias(codEmp);
			for (RegistroOcorrencia bean : beans) {
				dto = (AfastamentoPontoSaveDTO) IcarusMapper.registroOcorrenciaFerias2AfastamentoPontoSaveDTO(bean);

				idMutuario = IcarusFinder.getIdMutuario(bean.getCodEmp(), bean.getCodFunc());
				mutuarioIdDTO = new MutuarioIdDTO();
				mutuarioIdDTO.setIdMutuario(idMutuario);

				dto.setMutuario(mutuarioIdDTO);
				///
//				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
//				dtoJSON = gson.toJson(dto, dto.getClass());
//				
//				LOGGER.info(dtoJSON);

				apiResponse = api.postAfastamentoFerias(dto);
				response = apiResponse.getResponse();

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK)
					IcarusDAO.persistFerias(bean);
			}

		} catch (Exception e) {
			String msg = "Não foi possível incluir as Férias.";

			if (dto != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, dto.toString());

			if (response != null)///
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private static void incluirMotivoAbonoPonto(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		MotivoAbonoPontoSaveDTO dto = null;
//		String dtoJSON = null;
		ApiResponse apiResponse = null;
		String response = null;
		BigDecimal idMotivoAbonoPonto;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			List<HistoricoOcorrencia> beans = IcarusDAO.getHistoricosOcorrencia(codEmp);
			for (HistoricoOcorrencia bean : beans) {
				dto = (MotivoAbonoPontoSaveDTO) IcarusMapper.historicoOcorrencia2MotivoAbonoPontoSaveDtoMapper(bean);
//
//				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
//				dtoJSON = gson.toJson(dto, dto.getClass());
//				
//				LOGGER.info(dtoJSON);

				apiResponse = api.postMotivoAbonoPonto(dto);
				response = apiResponse.getResponse();

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
					idMotivoAbonoPonto = api.getIdMotivoAbonoPonto(dto);

					IcarusDAO.persistIdHistoricoOcorrencia(codEmp, bean.getCodHistOcor(), idMotivoAbonoPonto);
				}
			}
		} catch (Exception e) {
			String msg = "Não foi possível integrar Motivo de Abono Ponto.";
			if (dto != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, dto.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	private static void incluirAjustePonto(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		AjustePontoSaveDTO dto = null;
		ApiResponse apiResponse = null;
		ApiResponse apiResponsePontos = null;

//		String dtoJSON = null;
		String response = null;
		PontoHorarioBatidaDTO batida = null;
		PontoHorarioBatidasDTO batidas = null;
		JsonArray batidasJson = null;

//		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			String msg;

			List<FuncionarioAjuste> beans = IcarusDAO.getFuncionariosComAjustes(codEmp);
			for (FuncionarioAjuste bean : beans) {

				apiResponsePontos = api.buscarTodosPontos(bean.getIdMutuario(), bean.getDataOcorrencia());

				if (apiResponsePontos.getResponseCode() == HttpURLConnection.HTTP_OK) {
					msg = String.format("%s", apiResponsePontos.getResponse());
					LOGGER.info(msg);

					batidas = new PontoHorarioBatidasDTO();
					batidasJson = JsonUtils.convertStringToJsonArray(apiResponsePontos.getResponse());
					for (JsonElement batidaJson : batidasJson) {
						batida = gson.fromJson(batidaJson, PontoHorarioBatidaDTO.class);
						batidas.getBatidas().add(batida);
					}

					msg = String.format("%s", batidas.toString());
					LOGGER.info(msg);

					for (RegistroOcorrencia ocorrencia : bean.getOcorrencias()) {
						batida = new PontoHorarioBatidaDTO();
						batida.setIdPontoHorarioBatida(BigDecimal.ZERO);
						batida.setHoraRegistroFormatado(TimeUtils.formataDDMMYYYYHHMM(ocorrencia.getDtInicOcor()));

						batidas.getBatidas().add(batida);
					}

					msg = String.format("%s", batidas.toString());
					LOGGER.info(msg);
				}

				Collections.sort(batidas.getBatidas(), new PontoHorarioBatidaComparator());
				bean.setBatidas(batidas);

				msg = String.format("%s", bean.toString());
				LOGGER.info(msg);

				dto = IcarusMapper.funcionarioAjuste2AjustePontoSaveDTO(bean);

				///
//				dtoJSON = gson.toJson(dto, dto.getClass());
//				LOGGER.info(dtoJSON);

				apiResponse = api.postAjustePonto(dto);

				response = apiResponse.getResponse();

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
					for (RegistroOcorrencia ocorrencia : bean.getOcorrencias())
						IcarusDAO.persistAfastamento(ocorrencia);
				}

			}

		} catch (Exception e) {
			String msg = "Não foi possível incluir Ajustes do Ponto.";

			if (dto != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, dto.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private static void incluirAfastamento(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		AfastamentoPontoSaveDTO dto = null;
		BigDecimal idMutuario = null;
		MutuarioIdDTO mutuarioIdDTO = null;
		BigDecimal idMotivoAbonoPonto = null;
		MotivoAbonoPontoIdDTO motivoAbonoPontoIdDTO = null;
		ApiResponse apiResponse = null;
		String response = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			String msg;
			List<RegistroOcorrencia> beans = IcarusDAO.getAfastamentos(codEmp);
			for (RegistroOcorrencia bean : beans) {

				msg = String.format("%s", bean.toString());
				LOGGER.info(msg);

				dto = (AfastamentoPontoSaveDTO) IcarusMapper.registroOcorrencia2AfastamentoPontoSaveDTO(bean);

				idMutuario = IcarusFinder.getIdMutuario(bean.getCodEmp(), bean.getCodFunc());
				mutuarioIdDTO = new MutuarioIdDTO();
				mutuarioIdDTO.setIdMutuario(idMutuario);

				dto.setMutuario(mutuarioIdDTO);

				idMotivoAbonoPonto = IcarusDAO.getIdMotivoAbonoPonto(codEmp, bean.getCodHistOcor());
				motivoAbonoPontoIdDTO = new MotivoAbonoPontoIdDTO();
				motivoAbonoPontoIdDTO.setIdMotivoAbonoPonto(idMotivoAbonoPonto);

				dto.setMotivoAfastamento(motivoAbonoPontoIdDTO);

				apiResponse = api.postAfastamentoFerias(dto);
				response = apiResponse.getResponse();

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
					IcarusDAO.persistAfastamento(bean);
				} else {
					msg = String.format("response %s %s", apiResponse.getResponseCode(), apiResponse.getResponse());
					LOGGER.info(msg);
				}
			}

		} catch (Exception e) {
			String msg = "Não foi possível incluir o Afastamento.";

			if (dto != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, dto.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private static void atualizarRetornoAfastamento(BigDecimal codigo) throws SkalaModelException {
		SessionHandle hnd = null;

		AfastamentoPontoUpdateDTO dto = null;
		ApiResponse apiResponse = null;
		String response = null;

		try {
			hnd = JapeSession.open();

			List<RegistroOcorrencia> beans = IcarusDAO.getRetornosAfastamentos(codigo);
			for (RegistroOcorrencia bean : beans) {

				dto = (AfastamentoPontoUpdateDTO) IcarusMapper.registroOcorrencia2AfastamentoPontoUpdateDTO(bean);
				dto.setIdAfastamentoPonto(IcarusDAO.getIdAfastamentoPonto(bean.getNuOcor()));

				apiResponse = api.putAfastamentoFerias(dto);

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
					IcarusDAO.setDataRetornoOcorrencia(bean.getNuOcor());
				} else {
					response = String.format("response %s", apiResponse.getResponse());
					LOGGER.info(response);
				}
			}

		} catch (Exception e) {
			String msg = "Não foi possível atualizar o Retorno de Afastamento.";
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private static void incluirAbono(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		AbonoPontoSaveDTO dto = null;
		BigDecimal idMutuario = null;
		MutuarioIdDTO mutuarioIdDTO = null;
		BigDecimal idMotivoAbonoPonto = null;
		MotivoAbonoPontoIdDTO motivoAbonoPontoIdDTO = null;
		ApiResponse apiResponse = null;
//		String dtoJSON = null;
		String response = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			String msg;
			List<RegistroOcorrencia> beans = IcarusDAO.getAbonos(codEmp);
			for (RegistroOcorrencia bean : beans) {
				msg = String.format("%s", bean.toString());
				LOGGER.info(msg);

				dto = (AbonoPontoSaveDTO) IcarusMapper.registroOcorrencia2AbonoPontoSaveDTO(bean);

				idMutuario = IcarusFinder.getIdMutuario(bean.getCodEmp(), bean.getCodFunc());
				mutuarioIdDTO = new MutuarioIdDTO();
				mutuarioIdDTO.setIdMutuario(idMutuario);

				dto.setMutuario(mutuarioIdDTO);

				idMotivoAbonoPonto = IcarusDAO.getIdMotivoAbonoPonto(codEmp, bean.getCodHistOcor());
				motivoAbonoPontoIdDTO = new MotivoAbonoPontoIdDTO();
				motivoAbonoPontoIdDTO.setIdMotivoAbonoPonto(idMotivoAbonoPonto);

				dto.setMotivoAbono(motivoAbonoPontoIdDTO);

				msg = String.format("%s", dto.toString());
				LOGGER.info(msg);
				///
//				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
//				dtoJSON = gson.toJson(dto, dto.getClass());
//				
//
//				LOGGER.info(dtoJSON);

				apiResponse = api.postAbonoPonto(dto);

				response = apiResponse.getResponse();

				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK)
					IcarusDAO.persistAfastamento(bean);
			}

		} catch (Exception e) {
			String msg = "Não foi possível incluir Abono.";

			if (dto != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, dto.toString());

			if (response != null)
				msg = SkalaStringUtils.appendWithSeparator(msg, response);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public static void atualizaRegistroPonto(final BigDecimal codEmp, final BigDecimal codFunc)
			throws SkalaModelException {
		SessionHandle hnd = null;

		ApiResponse apiResponse = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			hnd = JapeSession.open();

			Timestamp dtIniFech = IcarusDAO.getDataInicioFechamentoPonto();
			Timestamp dtFinalFech = IcarusDAO.getDataFinalFechamentoPonto();
			Timestamp dtFimAviso = IcarusDAO.getDtFimAviso(codEmp, codFunc);
			Timestamp dataFim = (dtFimAviso != null) ? dtFimAviso : dtFinalFech;

			FuncionarioIcarus funcionarioIcarus = IcarusDAO.getFuncionarioIcarusByPk(codEmp, codFunc);

			MutuarioIdDTO mutuarioIdDto = (MutuarioIdDTO) IcarusMapper
					.funcionarioIcarus2MutuarioIdDTOMapper(funcionarioIcarus);

			PontoPesquisaDTO pesquisaDto = new PontoPesquisaDTO();
			pesquisaDto.getMutuarios().add(mutuarioIdDto);
			pesquisaDto.setDataInicio(sdf.format(dtIniFech));
			pesquisaDto.setDataFim(sdf.format(dataFim));

			apiResponse = api.relatorioPontosSkala(pesquisaDto);

			PontosDTO pontos = new PontosDTO();
			PontoIcarus pontoIcarus;
			if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
				JsonArray array = JsonUtils.convertStringToJsonArray(apiResponse.getResponse());

				PontoDTO ponto = new PontoDTO();
				for (JsonElement json : array) {
					ponto = gson.fromJson(json, ponto.getClass());
					pontos.getPontos().add(ponto);
				}

				for (PontoDTO dto : pontos.getPontos()) {
					pontoIcarus = (PontoIcarus) IcarusMapper.pontoDto2PontoIcarusMapper(dto);
					pontoIcarus.setCodFuncionario(
							IcarusDAO.getCodFuncFromMatricula(pontoIcarus.getCodEmp(), dto.getCodfunc()));

					IcarusDAO.persistsPontoIcarus(pontoIcarus, dtIniFech);
				}
			}

		} catch (Exception e) {
			String msg = String.format("Não foi possível atualizar os registros de ponto do Colaborador %s - %s",
					codEmp, codFunc);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private static void setAtualizandoRpo(BigDecimal codEmp, BigDecimal codFunc, Timestamp dtIniFech)
			throws SkalaModelException {
		CallableStatement rsv = null;
		JdbcWrapper jdbc = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			rsv = jdbc.getConnection().prepareCall("{call STP_ATUALIZANDO_PONTORPO(?, ?, ?)}");
			rsv.setBigDecimal(1, codEmp);
			rsv.setBigDecimal(2, codFunc);
			rsv.setTimestamp(3, dtIniFech);

			rsv.execute();
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar ponto do Movimento RPO.", e);
		} finally {
			JdbcUtils.closeStatement(rsv);
			JdbcWrapper.closeSession(jdbc);
		}
	}

	public static void getAuthFromCodEmp(BigDecimal codEmp) throws RecordNotFoundException, SkalaModelException {
		EmpresaIcarus empresaIcarus = IcarusDAO.getEmpresaIcarusByPk(codEmp);

		// CODIGO DA EMPRESA NO ICARUS
		// AGLUTINADOR DE CENTROS DE RESULTADO
		// REPRESENTA AS EMPRESAS PRINCIAIS DO GRUPO: 1 E 2
		BigDecimal codigo = empresaIcarus.getCodigo();

		ConfiguracaoIcarus config = IcarusDAO.getConfiguracaoEmpresa(codigo);
		getAuth(config);
	}

	private static void importarRegistrosDePonto(BigDecimal codigo) throws SkalaModelException {
		SessionHandle hnd = null;

		ApiResponse apiResponse = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String msg = String.format("importarRegistrosDePonto empresa %s", codigo);
		LOGGER.info(msg);

		String matricula = null;
		String dtMov = null;
		PontoDTO pontoDTO = null;
		PontoIcarus pontoIcarus = null;

		try {
			hnd = JapeSession.open();

			List<CentroCustoIdDTO> centrosCusto = IcarusDAO.getCentrosCusto(codigo);

			List<Timestamp> datasPonto = IcarusDAO.getPeriodoPonto();
			for (Timestamp dataPonto : datasPonto) {
				PontoPesquisaDTO pesquisaDto = new PontoPesquisaDTO();
				pesquisaDto.setCentrosCusto(centrosCusto);
				pesquisaDto.setDataInicio(sdf.format(dataPonto));
				pesquisaDto.setDataFim(sdf.format(dataPonto));

				apiResponse = api.relatorioPontosSkala(pesquisaDto);

				PontosDTO pontos = new PontosDTO();
				if (apiResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
					JsonArray array = JsonUtils.convertStringToJsonArray(apiResponse.getResponse());

					PontoDTO ponto = new PontoDTO();
					for (JsonElement json : array) {
						ponto = gson.fromJson(json, ponto.getClass());

						if (ponto.getBatidas() != null)
							pontos.getPontos().add(ponto);
					}

					for (PontoDTO dto : pontos.getPontos()) {
						matricula = dto.getCodfunc();
						dtMov = dto.getDtmov();
						pontoDTO = dto;

						pontoIcarus = (PontoIcarus) IcarusMapper.pontoDto2PontoIcarusMapper(dto);
						if (pontoIcarus != null) {
							pontoIcarus.setCodFuncionario(
									IcarusDAO.getCodFuncFromMatricula(pontoIcarus.getCodEmp(),
											dto.getCodfunc()));
							
							IcarusDAO.persistsPontoIcarus(pontoIcarus, dataPonto);
						}
					}
				}
			}

		} catch (Exception e) {
			msg = String.format(
					"Não foi possível atualizar os registros de ponto - matricula %s dtMov %s pontoDTO %s pontoIcarus %S",
					matricula, dtMov, pontoDTO, pontoIcarus);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public static void sendRequestAfastamentoFerias() throws SkalaModelException {

		List<ConfiguracaoIcarus> configuracoes = IcarusFinder.getConfiguracoes();
		for (ConfiguracaoIcarus config : configuracoes) {
			getAuth(config);
			sendRequestAfastamentoFerias(config);
		}

	}

	private static void sendRequestAfastamentoFerias(ConfiguracaoIcarus config) throws SkalaModelException {

		BigDecimal codigo = config.getCodEmp();
		incluirAfastamento(codigo);

		if (config.isNewToken())
			IcarusDAO.updateToken(config);

	}

}

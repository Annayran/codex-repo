package tech.skala.action.integration.tangerino.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.StringUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import tech.skala.action.integration.tangerino.model.integracao.AdjustmentReasonDTO;
import tech.skala.action.integration.tangerino.model.integracao.AdjustmentReasonRecordDTO;
import tech.skala.action.integration.tangerino.model.integracao.Autenticacao;
import tech.skala.action.integration.tangerino.model.integracao.Cargo;
import tech.skala.action.integration.tangerino.model.integracao.Colaborador;
import tech.skala.action.integration.tangerino.model.integracao.DismissDTO;
import tech.skala.action.integration.tangerino.model.integracao.EmployeeFunctionSimpleDTO;
import tech.skala.action.integration.tangerino.model.integracao.EmployeeWorkScheduleAssignDTO;
import tech.skala.action.integration.tangerino.model.integracao.Escala;
import tech.skala.action.integration.tangerino.model.integracao.EscalaRelacao;
import tech.skala.action.integration.tangerino.model.integracao.LogItem;
import tech.skala.action.integration.tangerino.model.integracao.LogPrincipal;
import tech.skala.action.integration.tangerino.model.integracao.Setor;
import tech.skala.action.integration.tangerino.model.integracao.SimpleEmployeeDTO;
import tech.skala.action.integration.tangerino.model.sistema.MensagemLog;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.action.integration.tangerino.utils.ManagerUtils;
import tech.skala.action.integration.tangerino.utils.TangerinoHelper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class TangerinoController {
	private static final Logger LOGGER = Logger.getLogger("ConversorUtils");

	public String gerenciar() throws Exception {
		String mensagem = "";
		EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();

		new LogPrincipal().persists();

		Autenticacao autenticacao = TangerinoHelper.gerenciarAutenticacao();

		gerenciarFuncao(autenticacao);
		gerenciarCargos(dwfEntityFacade, autenticacao);
		gerenciarSetor(dwfEntityFacade, autenticacao);
//		deleteWorkplace(dwfEntityFacade, autenticacao);
		gerenciarEscalaTrabalho(dwfEntityFacade, autenticacao);
		gerenciarColaborador(dwfEntityFacade, autenticacao);
		gerenciarColocaboradorEstacalaTrabalho(dwfEntityFacade, autenticacao);
		enviarFerias(dwfEntityFacade, autenticacao);
		gerenciarColaboradorDemitido(dwfEntityFacade, autenticacao);
		finalizarExecucao(dwfEntityFacade, autenticacao);

		mensagem = "Rotina executada com sucesso! Verifique o log para acompanhamento. LOG: " + ManagerUtils.getInfo();
		return mensagem;
	}

	public static void gerenciarFuncao(Autenticacao autenticacao) throws SkalaModelException {
		LOGGER.info("gerenciarFuncao");

		new LogItem("Iniciando Rotina de Funcao", "", "C", MensagemLog.INFO).persists();

		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCAO);

			EmployeeFunctionSimpleDTO functionDTO;
			Collection<DynamicVO> vos = dao.find("COALESCE(this.AD_SINC_TANGERINO,'N') = 'S' AND this.CODFUNCAO > 0");
			for (DynamicVO vo : vos) {
				functionDTO = new EmployeeFunctionSimpleDTO();
				functionDTO.setDescription(vo.asString("DESCRFUNCAO"));
				functionDTO.setExternalId(vo.asBigDecimal("CODFUNCAO"));
				TangerinoHelper.incluirFuncao(functionDTO, autenticacao, true);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel acessar o cadastro de Funcao.", e);
		}

	}

	public static void gerenciarCargos(EntityFacade dwfEntityFacade, Autenticacao autenticacao) throws Exception {
		LOGGER.info("gerenciarCargos");

		new LogItem("Iniciando Rotina de Cargos", "", "C", MensagemLog.INFO).persists();
		FinderWrapper finder = new FinderWrapper("Cargo",
				"ATIVO = 'S' AND NVL(AD_SINC_TANGERINO,'N') = 'S' AND CODCARGO > 0");
		Collection<DynamicVO> cargosVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
		for (DynamicVO cargoVO : cargosVO) {
			Cargo cargo = new Cargo();
			if (StringUtils.isNotEmpty(cargoVO.asBigDecimal("AD_ID_TANGERINO"))) {
				cargo.setIdTangerino(cargoVO.asBigDecimal("AD_ID_TANGERINO"));
			}
			cargo.setNomeCargo(ConversorUtils.semAcento(cargoVO.asString("DESCRCARGO")));
			cargo.setCodCargo(cargoVO.asBigDecimal("CODCARGO"));
			TangerinoHelper.incluirCargo(cargo, autenticacao, true);
		}
	}

	private static void gerenciarSetor(EntityFacade dwfEntityFacade, Autenticacao autenticacao) throws Exception {
		LOGGER.info("gerenciarSetor");

		FinderWrapper finder = new FinderWrapper("Departamento",
				"COALESCE(this.AD_SINC_TANGERINO,'N') = 'S' AND this.ATIVO = 'S' AND this.ANALITICO = 'S' AND this.CODDEP > 0");
		Collection<DynamicVO> departamentosVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
		for (DynamicVO departamentoVO : departamentosVO) {
			Setor setor = new Setor();
			if (StringUtils.isNotEmpty(departamentoVO.asBigDecimal("AD_ID_TANGERINO"))) {
				setor.setIdTangerino(departamentoVO.asBigDecimal("AD_ID_TANGERINO"));
			}
			setor.setNomeSetor(ConversorUtils.semAcento(departamentoVO.asString("DESCRDEP")));
			setor.setCodSetor(departamentoVO.asBigDecimal("CODDEP"));
			
			TangerinoHelper.incluirSetor(setor, autenticacao, true);
		}
	}

	private static void deleteWorkplace(EntityFacade dwfEntityFacade, Autenticacao autenticacao) throws Exception {
		LOGGER.info("deleteWorkplace");

		FinderWrapper finder = new FinderWrapper("Departamento",
				"this.AD_ID_TANGERINO IS NOT NULL AND this.ATIVO = 'N' AND this.ANALITICO = 'S' AND this.CODDEP > 0");
		Collection<DynamicVO> departamentosVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
		for (DynamicVO departamentoVO : departamentosVO) {
			Setor setor = new Setor();
			setor.setIdTangerino(departamentoVO.asBigDecimal("AD_ID_TANGERINO"));
			setor.setNomeSetor(ConversorUtils.semAcento(departamentoVO.asString("DESCRDEP")));
			setor.setCodSetor(departamentoVO.asBigDecimal("CODDEP"));
			
			TangerinoHelper.deleteWorkplace(setor, autenticacao, true);
		}
	}

	private static void gerenciarEscalaTrabalho(EntityFacade dwfEntityFacade, Autenticacao autenticacao)
			throws Exception {
		LOGGER.info("gerenciarEscalaTrabalho");

		new LogItem("Iniciando Rotina de Escala", "", "C", MensagemLog.INFO).persists();

		BigDecimal qtdHorasAdicionais = BigDecimal.ZERO;
		try {
			qtdHorasAdicionais = BigDecimalUtil.valueOf(MGECoreParameter.getParameterAsInt("TANGERINO_HRS"));
		} catch (Exception e) {
			throw new Exception("Erro ao ler parametro: 'TANGERINO_HRS': " + ExceptionUtils.getMessage(e));
		}

		FinderWrapper finder = new FinderWrapper("IdentificacaoCargaHoraria",
				"COALESCE(this.AD_SINC_TANGERINO,'N') = 'S' AND this.CODCARGAHOR > 0 AND this.DESCRCARGAHOR IS NOT NULL");
		Collection<DynamicVO> cargaHorariasVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
		for (DynamicVO cargaHorarioVO : cargaHorariasVO) {
			new LogItem("Percorrendo Escala: " + cargaHorarioVO.asBigDecimal("CODCARGAHOR"), "", "C", MensagemLog.INFO)
					.persists();
			Escala escala = new Escala();
			if (StringUtils.isNotEmpty(cargaHorarioVO.asBigDecimal("AD_ID_TANGERINO"))) {
				escala.setIdTangerino(cargaHorarioVO.asBigDecimal("AD_ID_TANGERINO"));
			}
			escala.setNomeEscala(ConversorUtils.semAcento(cargaHorarioVO.asString("DESCRCARGAHOR")));
			escala.setCodCargaHoraria(cargaHorarioVO.asBigDecimal("CODCARGAHOR"));
			escala.setEscalas(gerenciarEscalasFilhas(cargaHorarioVO, qtdHorasAdicionais, dwfEntityFacade));
			TangerinoHelper.incluirEscala(escala, autenticacao, true);
		}
	}

	private static List<EscalaRelacao> gerenciarEscalasFilhas(DynamicVO cargaHorarioVO, BigDecimal qtdHorasAdicionais,
			EntityFacade dwfEntityFacade) throws Exception {
		LOGGER.info("gerenciarEscalasFilhas");

		new LogItem("Relacao de Escalas: " + cargaHorarioVO.asBigDecimal("CODCARGAHOR"), "", "C", MensagemLog.INFO)
				.persists();
		List<EscalaRelacao> escalaRelacaos = new ArrayList<>();
		FinderWrapper finder = new FinderWrapper("AD_VTFPHOR", "CODCARGAHOR = ?",
				cargaHorarioVO.asBigDecimal("CODCARGAHOR"));
		finder.setOrderBy("DIASEM");
		Collection<DynamicVO> itensVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
		for (DynamicVO itemVO : itensVO) {
			EscalaRelacao escalaRelacao = new EscalaRelacao();
			escalaRelacao.setCodCargaHoraria(itemVO.asBigDecimal("CODCARGAHOR"));
			if (StringUtils.isNotEmpty(itemVO.asBigDecimal("AD_ID_TANGERINO")))
				escalaRelacao.setIdTangerino(itemVO.asBigDecimal("AD_ID_TANGERINO"));

			escalaRelacao.setDia(itemVO.asBigDecimal("DIASEM"));
			if (StringUtils.isNotEmpty(itemVO.asBigDecimal("SAIDATURNO1"))) {
				escalaRelacao.setFimTurno1(ConversorUtils.horasParaMillis(itemVO.asBigDecimal("SAIDATURNO1")));
				escalaRelacao.setStartMainInterval(ConversorUtils.horasParaMillis(itemVO.asBigDecimal("SAIDATURNO1")));
			}
			if (StringUtils.isNotEmpty(itemVO.asBigDecimal("SAIDATURNO2")))
				escalaRelacao.setFimTurno2(ConversorUtils.horasParaMillis(itemVO.asBigDecimal("SAIDATURNO2")));
			if (StringUtils.isNotEmpty(itemVO.asBigDecimal("ENTRADATURNO1")))
				escalaRelacao.setInicioTurno1(ConversorUtils.horasParaMillis(itemVO.asBigDecimal("ENTRADATURNO1")));
			if (StringUtils.isNotEmpty(itemVO.asBigDecimal("ENTRADATURNO2"))) {
				escalaRelacao.setEndMainInterval(ConversorUtils.horasParaMillis(itemVO.asBigDecimal("ENTRADATURNO2")));
				escalaRelacao.setInicioTurno2(ConversorUtils.horasParaMillis(itemVO.asBigDecimal("ENTRADATURNO2")));
			}
			escalaRelacaos.add(escalaRelacao);
		}
		return escalaRelacaos;
	}

	private static void gerenciarColaborador(EntityFacade dwfEntityFacade, Autenticacao autenticacao) throws Exception {
		LOGGER.info("gerenciarColaborador");

		FinderWrapper finder = new FinderWrapper(DynamicEntityNames.FUNCIONARIO,
				"COALESCE(this.AD_SINC_TANGERINO,'N') = 'S' AND this.DTDEM IS NULL AND this.SITUACAO NOT IN (0,8,9) AND this.VINCULO NOT IN (02,30,80,90,95,99)");

//				"COALESCE(this.AD_SINC_TANGERINO,'N') = 'S' AND this.DTDEM IS NULL AND this.SITUACAO NOT IN (0,8,9) AND this.VINCULO NOT IN (02,60,30,80,90,95,99)");

		Colaborador colaborador;
		Cargo cargo;
		Collection<DynamicVO> funcionariosVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
		for (DynamicVO funcionarioVO : funcionariosVO) {
			colaborador = new Colaborador();
			if (StringUtils.isNotEmpty(funcionarioVO.asBigDecimal("AD_ID_TANGERINO"))) {
				colaborador.setIdTangerino(funcionarioVO.asBigDecimal("AD_ID_TANGERINO"));
			}
			colaborador.setNomeColaborador(ConversorUtils.semAcento(funcionarioVO.asString("NOMEFUNC")));
			colaborador.setCodFuncionario(funcionarioVO.asBigDecimal("CODFUNC"));
			colaborador.setCodEmpresa(funcionarioVO.asBigDecimal(SklFieldName.CODEMP));
//			colaborador.setMatricula(funcionarioVO.asBigDecimal("MATRICULA").toString());
			colaborador.setMatricula(funcionarioVO.asString("CPF"));
			colaborador.setCpf(funcionarioVO.asString("CPF"));
			colaborador.setCi(funcionarioVO.asString("IDENTIDADE"));
			colaborador.setCompany(funcionarioVO.asBigDecimal("Empresa.AD_ID_TANGERINO"));
			colaborador.setCompanyExternalId(funcionarioVO.asBigDecimal("Empresa.CODEMP"));
			colaborador.setEmail(StringUtils.trim(funcionarioVO.asString("EMAIL")));
			colaborador.setPis(funcionarioVO.asString("PIS"));
			colaborador.setTelefone(funcionarioVO.asString("TELEFONE"));
			if (StringUtils.isNotEmpty(funcionarioVO.asTimestamp("DTADM")))
				colaborador.setDataAdmissao(
						ConversorUtils.converterTimestampMillesegundos(funcionarioVO.asTimestamp("DTADM")));
			colaborador.setDataEfetivacao(ConversorUtils.converterTimestampMillesegundos(TimeUtils.buildData(1, 12, 2022)));
			if (StringUtils.isNotEmpty(funcionarioVO.asTimestamp("DTNASC")))
				colaborador.setDataNascimento(
						ConversorUtils.converterTimestampMillesegundos(funcionarioVO.asTimestamp("DTNASC")));
			if (StringUtils.isNotEmpty(funcionarioVO.asBigDecimal("CODDEP"))) {
				DynamicVO departamentoVO = (DynamicVO) dwfEntityFacade.findEntityByPrimaryKeyAsVO("Departamento",
						new Object[] { funcionarioVO.asBigDecimal("CODDEP") });
				if (StringUtils.isNotEmpty(departamentoVO.asBigDecimal("AD_ID_TANGERINO"))) {
					colaborador.setSetor(departamentoVO.asBigDecimal("AD_ID_TANGERINO"));
				}
			}
			if (StringUtils.isNotEmpty(funcionarioVO.asBigDecimal("CODCARGAHOR"))) {
				DynamicVO cargaHorariaVO = (DynamicVO) dwfEntityFacade.findEntityByPrimaryKeyAsVO(
						"IdentificacaoCargaHoraria", new Object[] { funcionarioVO.asBigDecimal("CODCARGAHOR") });
				if (StringUtils.isNotEmpty(cargaHorariaVO.asBigDecimal("AD_ID_TANGERINO"))) {
					colaborador.setEscala(cargaHorariaVO.asBigDecimal("AD_ID_TANGERINO"));
				}
			}
			if (StringUtils.isNotEmpty(funcionarioVO.asBigDecimal("CODEMP"))) {
				DynamicVO empresaVO = (DynamicVO) dwfEntityFacade.findEntityByPrimaryKeyAsVO("Empresa",
						new Object[] { funcionarioVO.asBigDecimal("CODEMP") });
				if (StringUtils.isNotEmpty(empresaVO.asString("AD_TIMEZONE"))) {
					colaborador.setTimeZone(empresaVO.asString("AD_TIMEZONE"));
				}
			}
			colaborador.setCtps(funcionarioVO.asBigDecimal("NUMCPS").toString());
			colaborador.setSeries(funcionarioVO.asString("SERIECPS"));

			colaborador.setJobRole(funcionarioVO.asBigDecimal("Cargo.AD_ID_TANGERINO"));
			colaborador.setJobRoleDescription(ConversorUtils.semAcento(funcionarioVO.asString("Cargo.DESCRCARGO")));
			colaborador.setJobRoleExternalId(funcionarioVO.asBigDecimal("Cargo.CODCARGO").toString());

			TangerinoHelper.incluirColaborador(colaborador, autenticacao, true);
		}
	}

	private void enviarFerias(EntityFacade dwfEntityFacade, Autenticacao autenticacao) throws SkalaModelException {
		LOGGER.info("enviarFerias");
		
		try {
			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.CODHISTOCOR IN (27) AND TRUNC(this.DTINICOCOR) >= TO_DATE('15/12/2022','DD/MM/YYYY')");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM TFPFUN T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC AND COALESCE(T.AD_SINC_TANGERINO,'N') = 'F' AND T.DTDEM IS NULL AND T.AD_ID_TANGERINO IS NOT NULL)");
			
			AdjustmentReasonDTO feriasDTO = new AdjustmentReasonDTO();
			feriasDTO.setId(BigDecimal.ONE);
			feriasDTO.setDescription("FERIAS");
			
			SimpleEmployeeDTO employeeDTO = new SimpleEmployeeDTO();
			AdjustmentReasonRecordDTO ajusteDTO = new AdjustmentReasonRecordDTO();
			
			FinderWrapper finder = new FinderWrapper(DynamicEntityNames.REGISTRO_OCORRENCIA, sbWhere.toString());
			Collection<DynamicVO> ocorrenciasVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
			for (DynamicVO vo:ocorrenciasVO) {
				employeeDTO = getSimpleEmployeeDTO(vo.asBigDecimal(SklFieldName.CODEMP), vo.asBigDecimal("CODFUNC"));
				
				ajusteDTO.setAdjustmentReasonDTO(feriasDTO);
				ajusteDTO.setEmployeeDTO(employeeDTO);
				ajusteDTO.setEndDate(BigDecimal.valueOf(TimeUtils.clearTime(vo.asTimestamp("DTFINALOCOR")).getTime()));
				ajusteDTO.setFullDay(true);
				ajusteDTO.setObservation(vo.asString("DESCROCOR"));
				ajusteDTO.setOrigem("Integracao SANKHYA");
				ajusteDTO.setStartDate(BigDecimal.valueOf(TimeUtils.clearTime(vo.asTimestamp("DTINICOCOR")).getTime()));
				ajusteDTO.setStatus("APROVADO");
				
				TangerinoHelper.enviarAjuste(ajusteDTO, autenticacao, true);
			}
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível consultar as férias.", e);
		}
	}
	
	private SimpleEmployeeDTO getSimpleEmployeeDTO(BigDecimal codEmp, BigDecimal codFunc) throws Exception {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
		DynamicVO vo = dao.findByPK(codEmp, codFunc);
		
		SimpleEmployeeDTO dto = new SimpleEmployeeDTO();
		dto.setCodEmp(codEmp);
		dto.setCodFunc(codFunc);
		dto.setId(vo.asBigDecimal("AD_ID_TANGERINO"));
		dto.setName(ConversorUtils.semAcento(vo.asString("NOMEFUNC")));
		dto.setExternalId(vo.asBigDecimal("MATRICULA").toString());
		dto.setTangerinoId(vo.asBigDecimal("AD_ID_TANGERINO"));
		
		return dto;
	}

	private static void gerenciarColaboradorDemitido(EntityFacade dwfEntityFacade, Autenticacao autenticacao)
			throws Exception {
		LOGGER.info("gerenciarColaboradorDemitido");

		FinderWrapper finder = new FinderWrapper(DynamicEntityNames.FUNCIONARIO,
				"COALESCE(this.AD_SINC_TANGERINO,'N') = 'D' AND this.DTDEM IS NOT NULL AND this.AD_ID_TANGERINO IS NOT NULL");
		Collection<DynamicVO> funcionariosVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
		for (DynamicVO funcionarioVO : funcionariosVO) {
			DismissDTO dismissDTO = new DismissDTO();
			dismissDTO.setCodEmp(funcionarioVO.asBigDecimal(SklFieldName.CODEMP));
			dismissDTO.setCodFunc(funcionarioVO.asBigDecimal("CODFUNC"));
			dismissDTO.setExternalId(funcionarioVO.asBigDecimal("MATRICULA").toString());
			dismissDTO.setResignationDate(
					BigDecimal.valueOf(TimeUtils.clearTime(funcionarioVO.asTimestamp("DTDEM")).getTime()));
			dismissDTO.setTangerinoId(funcionarioVO.asBigDecimal("AD_ID_TANGERINO"));

			TangerinoHelper.incluirDemissao(dismissDTO, autenticacao, true);
		}
	}

	private static void gerenciarColocaboradorEstacalaTrabalho(EntityFacade dwfEntityFacade, Autenticacao autenticacao)
			throws Exception {
		LOGGER.info("gerenciarColocaboradorEstacalaTrabalho");

		FinderWrapper finder = new FinderWrapper("Funcionario",
				"COALESCE(this.AD_SINC_TANGERINO,'N') = 'E' AND this.AD_ID_TANGERINO IS NOT NULL");

		EmployeeWorkScheduleAssignDTO workScheduleAssing;
		Collection<DynamicVO> funcionariosVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
		for (DynamicVO funcionarioVO : funcionariosVO) {
			workScheduleAssing = new EmployeeWorkScheduleAssignDTO();
			workScheduleAssing.setEmployeeExternalId(funcionarioVO.asBigDecimal("MATRICULA").toString());
			workScheduleAssing.setEmployeeId(funcionarioVO.asBigDecimal("AD_ID_TANGERINO"));
			workScheduleAssing.setStartDate(BigDecimal.valueOf(TimeUtils.clearTime(TimeUtils.getNow()).getTime()));

			DynamicVO cargaHorariaVO = (DynamicVO) dwfEntityFacade.findEntityByPrimaryKeyAsVO(
					"IdentificacaoCargaHoraria", new Object[] { funcionarioVO.asBigDecimal("CODCARGAHOR") });
			workScheduleAssing.setWorkScheduleId(cargaHorariaVO.asBigDecimal("AD_ID_TANGERINO"));

			TangerinoHelper.incluirNovaCargaHoraria(workScheduleAssing, autenticacao, true);
			try {
				Colaborador colaborador = new Colaborador();
				colaborador.setCodEmpresa(funcionarioVO.asBigDecimal(SklFieldName.CODEMP));
				colaborador.setCodFuncionario(funcionarioVO.asBigDecimal("CODFUNC"));
				colaborador.persists();
			} catch (Exception e) {
				TangerinoHelper.incluirLog(new LogItem("Colaborador  Alteracao Escala => Erro ao Salvar: ",
						e.getMessage(), "C", MensagemLog.ERRO, true));
			}

		}
	}

	public static void setSyncComplete(BigDecimal codEmp, BigDecimal codFunc) {
		final String ENTITY_NAME = DynamicEntityNames.FUNCIONARIO;

		JapeSession.SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codEmp, codFunc });
					EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, pk);
					entityUpd.setField("AD_SINC_TANGERINO", "N");
					entityUpd.fluidUpdate();
				}
			});
		} catch (Exception e) {
			new LogItem("Erro ao salvar colaborador: ", e.getMessage(), "C", MensagemLog.ERRO);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private static void finalizarExecucao(EntityFacade dwfEntityFacade, Autenticacao autenticacao) throws Exception {
		LOGGER.info("finalizarExecucao");

		FinderWrapper finder = new FinderWrapper("AD_ITGLOGITE", "CODLOG = ? AND MENSAGEM = 'ERRO'",
				ManagerUtils.getInfo());
		Collection<DynamicVO> listLogFilhoVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
		LogPrincipal logPrincipal = new LogPrincipal();
		logPrincipal.setCodLog(ManagerUtils.getInfo());
		if (listLogFilhoVO.size() > 0)
			logPrincipal.setStatusDosItens("E");
		else
			logPrincipal.setStatusDosItens("S");
		logPrincipal.persists();
	}
}
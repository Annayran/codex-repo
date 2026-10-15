package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;
import com.sankhya.util.StringUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.beans.cargahoraria.IdentificacaoCargaHoraria;
import tech.skala.action.beans.cargo.Cargo;
import tech.skala.action.beans.departamento.Departamento;
import tech.skala.action.beans.funcionario.Funcionario;
import tech.skala.action.beans.funcionario.HistoricoOcorrencia;
import tech.skala.action.beans.registroocorrencia.RegistroOcorrencia;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.action.modules.empresas.Empresa;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.commons.dd.DTO;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class IcarusMapper {
	private static final String					SIM = "SIM";
	private static final String					NAO = "NAO";

	private static ModelMapper modelMapper;
	private static Converter<Object, String> toString = e -> e.getSource() == null ? null : e.getSource().toString();
	private static Converter<Object, BigDecimal> string2BigDecimal = e -> e.getSource() == null ? null : BigDecimalUtil.getBigDecimal(e.getSource());
	private static Converter<String, String> getSimNaoFromString = e -> "S".equalsIgnoreCase(e.getSource()) ? SIM : NAO;
	private static Converter<Timestamp, Timestamp> ajustaDataClearDate = e -> e.getSource() == null ? null
			: TimeUtils.dataAdd(TimeUtils.clearTime(e.getSource()), 12, Calendar.HOUR_OF_DAY);

	private IcarusMapper() {
		throw new IllegalStateException("Utility class");
	}
	
	public static DTO configuracao2AccountCredentialsDTOMapper(ConfiguracaoIcarus bean) {
		modelMapper = new ModelMapper();
		return modelMapper.map(bean, AccountCredentialsDTO.class);		
	}

	public static DTO empresa2CentroCustoDtoMapper(Empresa empresa) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(Empresa.class, CentroCustoSaveDTO.class).addMappings(mapper -> {
			mapper.map(Empresa::getCgc, CentroCustoSaveDTO::setCnpj);
			mapper.using(toString).map(Empresa::getCodEmp, CentroCustoSaveDTO::setCodigo);
			mapper.map(Empresa::getNomeFantasia, CentroCustoSaveDTO::setDescricao);
			mapper.map(Empresa::getEmail, CentroCustoSaveDTO::setEmail);
			mapper.map(Empresa::getTelefone, CentroCustoSaveDTO::setTelefone);
		});

		return modelMapper.map(empresa, CentroCustoSaveDTO.class);
	}

	public static DTO empresa2CentroCustoPesquisaDtoMapper(Empresa empresa) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(Empresa.class, CentroCustoPesquisaDTO.class).addMappings(mapper -> {
			mapper.using(toString).map(Empresa::getCodEmp, CentroCustoPesquisaDTO::setCodigo);
			mapper.map(Empresa::getNomeFantasia, CentroCustoPesquisaDTO::setDescricao);
		});

		return modelMapper.map(empresa, CentroCustoPesquisaDTO.class);
	}

	public static DTO empresaIcarus2CentroCustoIdDtoMapper(EmpresaIcarus bean) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(EmpresaIcarus.class, CentroCustoIdDTO.class).addMappings(mapper -> {
			mapper.map(EmpresaIcarus::getIdCentroCusto, CentroCustoIdDTO::setIdCentroCusto);
		});

		return modelMapper.map(bean, CentroCustoIdDTO.class);
	}

	public static DTO departamento2LotacaoDtoMapper(Departamento departamento) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(Departamento.class, LotacaoSaveDTO.class).addMappings(mapper -> {
			mapper.using(toString).map(Departamento::getCodDep, LotacaoSaveDTO::setCodigo);
			mapper.map(Departamento::getDescrDep, LotacaoSaveDTO::setDescricao);
		});

		return modelMapper.map(departamento, LotacaoSaveDTO.class);
	}

	public static DTO departamento2LotacaoPesquisaDtoMapper(Departamento departamento) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(Departamento.class, LotacaoPesquisaDTO.class).addMappings(mapper -> {
			mapper.using(toString).map(Departamento::getCodDep, LotacaoPesquisaDTO::setCodigo);
			mapper.map(Departamento::getDescrDep, LotacaoPesquisaDTO::setDescricao);
		});

		return modelMapper.map(departamento, LotacaoPesquisaDTO.class);
	}

	public static DTO departamentoIcarus2LotacaoIdDtoMapper(DepartamentoIcarus bean) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(DepartamentoIcarus.class, LotacaoIdDTO.class).addMappings(mapper -> {
			mapper.map(DepartamentoIcarus::getIdLotacao, LotacaoIdDTO::setIdLotacao);
		});

		return modelMapper.map(bean, LotacaoIdDTO.class);
	}

	public static DTO cargo2CargoDtoMapper(Cargo cargo) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(Cargo.class, CargoSaveDTO.class).addMappings(mapper -> {
			mapper.using(toString).map(Cargo::getCodCargo, CargoSaveDTO::setCodigo);
			mapper.map(Cargo::getDescrCargo, CargoSaveDTO::setDescricao);
			mapper.using(toString).map(Cargo::getCodCbo, CargoSaveDTO::setSigla);
		});

		return modelMapper.map(cargo, CargoSaveDTO.class);
	}

	public static DTO cargo2CargoPesquisaDtoMapper(Cargo cargo) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(Cargo.class, CargoPesquisaDTO.class).addMappings(mapper -> {
			mapper.using(toString).map(Cargo::getCodCargo, CargoPesquisaDTO::setCodigo);
			mapper.map(Cargo::getDescrCargo, CargoPesquisaDTO::setDescricao);
			mapper.map(Cargo::getCodCbo, CargoPesquisaDTO::setSigla);
		});

		return modelMapper.map(cargo, CargoPesquisaDTO.class);
	}

	public static DTO cargoIcarus2CargoIdDtoMapper(CargoIcarus bean) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(CargoIcarus.class, CargoIdDTO.class).addMappings(mapper -> {
			mapper.map(CargoIcarus::getIdCargo, CargoIdDTO::setIdCargo);
		});

		return modelMapper.map(bean, CargoIdDTO.class);
	}

	public static RegimeContratacaoSaveDTO regime2RegimeContratacaoSaveDtoMapper(
			RegimeContratacaoIcarus regimeContratacao) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(RegimeContratacaoIcarus.class, RegimeContratacaoSaveDTO.class).addMappings(mapper -> {
			mapper.map(RegimeContratacaoIcarus::getCodigo, RegimeContratacaoSaveDTO::setCodigo);
			mapper.map(RegimeContratacaoIcarus::getDescricao, RegimeContratacaoSaveDTO::setDescricao);
		});

		return modelMapper.map(regimeContratacao, RegimeContratacaoSaveDTO.class);
	}

	public static RegimeContratacaoFilterDTO regime2RegimeContratacaoFilterDtoMapper(
			RegimeContratacaoIcarus regimeContratacao) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(RegimeContratacaoIcarus.class, RegimeContratacaoFilterDTO.class).addMappings(mapper -> {
			mapper.using(toString).map(RegimeContratacaoIcarus::getCodigo, RegimeContratacaoFilterDTO::setCodigo);
			mapper.map(RegimeContratacaoIcarus::getDescricao, RegimeContratacaoFilterDTO::setDescricao);
		});

		return modelMapper.map(regimeContratacao, RegimeContratacaoFilterDTO.class);
	}

	public static DTO regimeContratacaoIcarus2RegimeContratacaoIdDtoMapper(RegimeContratacaoIcarus bean) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(RegimeContratacaoIcarus.class, RegimeContratacaoIdDTO.class).addMappings(mapper -> {
			mapper.map(RegimeContratacaoIcarus::getIdRegimeContratacao, RegimeContratacaoIdDTO::setIdRegimeContratacao);
		});

		return modelMapper.map(bean, RegimeContratacaoIdDTO.class);
	}

	public static DTO funcionario2PessoaDtoMapper(Funcionario funcionario) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(Funcionario.class, PessoaDTO.class).addMappings(mapper -> {
			mapper.map(Funcionario::getCpf, PessoaDTO::setDocumento);
			mapper.map(Funcionario::getNomeFunc, PessoaDTO::setNomePessoa);
			mapper.map(Funcionario::getDtNasc, PessoaDTO::setDataNascimento);
			mapper.map(Funcionario::getNomePai, PessoaDTO::setNomePai);
			mapper.map(Funcionario::getNomeMae, PessoaDTO::setNomeMae);
			mapper.map(Funcionario::getNomeCidadeNascimento, PessoaDTO::setCidadeNatal);
			mapper.map(Funcionario::getSiglaUfNascimento, PessoaDTO::setUfNatal);
		});

		PessoaDTO dto = modelMapper.map(funcionario, PessoaDTO.class);
		dto.setEmail(IcarusFinder.getEmailPadrao(funcionario));
		dto.setTipoPessoa("FISICA");

		switch (funcionario.getSexo()) {
		case "F":
			dto.setSexo("FEMININO");
			break;
		case "M":
			dto.setSexo("MASCULINO");
			break;
		default:
			dto.setSexo("NAO BINARIO");
			break;
		}

		if (funcionario.getEstadoCivil() == null) {
			dto.setEstadoCivil("SOLTEIRO");
		} else {
			switch (funcionario.getEstadoCivil().toString()) {
			case "1":
				dto.setEstadoCivil("SOLTEIRO");
				break;
			case "2":
				dto.setEstadoCivil("CASADO");
				break;
			case "3":
				dto.setEstadoCivil("VIUVO");
				break;
			case "4":
				dto.setEstadoCivil("SEPARADO");
				break;
			case "5":
				dto.setEstadoCivil("SEPARADO");
				break;
			case "6":
				dto.setEstadoCivil("DIVORCIADO");
				break;
			case "7":
				dto.setEstadoCivil("CASADO");
				break;
			default:
				dto.setEstadoCivil("SOLTEIRO");
				break;
			}
		}

		String estrangeiro = (funcionario.getNacionalidade().intValue() == 10) ? "NAO" : "SIM";
		dto.setExtrangeiro(estrangeiro);

		dto.setNacionalidade(getDescricaoNacionalidade(funcionario.getNacionalidade()));
		dto.setRaca(getDescricaoRaca(funcionario.getRacaFuncionario()));

		return dto;
	}

	private static String getDescricaoNacionalidade(BigDecimal nacionalidade) {
		String descricaoNacionalidade = "BRASILEIRO";

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(IcarusMapper.class, "IcarusMapper_queDescricaoNacionalidade.sql");
			sql.setNamedParameter("NOMETAB", "TFPFUN");
			sql.setNamedParameter("NOMECAMPO", "NACIONALIDADE");
			sql.setNamedParameter("VALOR", nacionalidade.toString());

			rs = sql.executeQuery();

			while (rs.next()) {
				descricaoNacionalidade = ConversorUtils.semAcento(rs.getString("OPCAO"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return descricaoNacionalidade;
	}

	private static String getDescricaoRaca(BigDecimal raca) {
		String descricaoRaca = "NAO INFORMADO";

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(IcarusMapper.class, "IcarusMapper_queDescricaoRaca.sql");
			sql.setNamedParameter("NOMETAB", "TFPFUN");
			sql.setNamedParameter("NOMECAMPO", "RACAFUNCIONARIO");
			sql.setNamedParameter("VALOR", raca.toString());

			rs = sql.executeQuery();

			while (rs.next()) {
				descricaoRaca = ConversorUtils.semAcento(rs.getString("OPCAO")).toUpperCase();
				descricaoRaca = StringUtils.removerCaracteresEspeciais(descricaoRaca);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return descricaoRaca;
	}

	public static MutuarioAlteracaoDTO funcionario2MutuarioDtoMapper(Funcionario bean) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(Funcionario.class, MutuarioAlteracaoDTO.class).addMappings(mapper -> {
			mapper.map(Funcionario::getMatricula, MutuarioAlteracaoDTO::setMatricula);
			mapper.map(Funcionario::getPis, MutuarioAlteracaoDTO::setNumeroIdentificacaoSocial);
			mapper.map(Funcionario::getDtAdm, MutuarioAlteracaoDTO::setDataAdmissao);
			mapper.using(toString).map(Funcionario::getCodFunc, MutuarioAlteracaoDTO::setIdentificadorExterno);
		});

		MutuarioAlteracaoDTO dto = modelMapper.map(bean, MutuarioAlteracaoDTO.class);
		if (bean.isDispensaPonto()) {
			dto.setRegistraPonto("NAO");
			dto.setRegistraPontoPeloToten("NAO");
		}
		
		dto.setPessoa((PessoaDTO) funcionario2PessoaDtoMapper(bean));

		return dto;
	}

	public static DTO funcionario2MutuarioDtoMapper(Funcionario funcionario, FuncionarioIcarus funcionarioIcarus,
			EmpresaIcarus empresaIcarus, CargoIcarus cargoIcarus, DepartamentoIcarus departamentoIcarus,
			RegimeContratacaoIcarus regimeContratacao) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(Funcionario.class, MutuarioAlteracaoDTO.class).addMappings(mapper -> {
			mapper.map(Funcionario::getMatricula, MutuarioAlteracaoDTO::setMatricula);
			mapper.map(Funcionario::getPis, MutuarioAlteracaoDTO::setNumeroIdentificacaoSocial);
			mapper.map(Funcionario::getDtAdm, MutuarioAlteracaoDTO::setDataAdmissao);
			mapper.using(toString).map(Funcionario::getCodFunc, MutuarioAlteracaoDTO::setIdentificadorExterno);
		});

		MutuarioAlteracaoDTO dto = modelMapper.map(funcionario, MutuarioAlteracaoDTO.class);
		if (funcionario.isDispensaPonto()) {
			dto.setRegistraPonto("NAO");
			dto.setRegistraPontoPeloToten("NAO");
		}
		
		dto.setCentroCusto((CentroCustoIdDTO) empresaIcarus2CentroCustoIdDtoMapper(empresaIcarus));
		dto.setCargo((CargoIdDTO) cargoIcarus2CargoIdDtoMapper(cargoIcarus));
		dto.setLotacao((LotacaoIdDTO) departamentoIcarus2LotacaoIdDtoMapper(departamentoIcarus));
		dto.setRegimeContratacao((RegimeContratacaoIdDTO) regimeContratacaoIcarus2RegimeContratacaoIdDtoMapper(regimeContratacao));
		
		PessoaDTO pessoaDTO = (PessoaDTO) funcionario2PessoaDtoMapper(funcionario);

		if (funcionarioIcarus != null) {
			dto.setIdMutuario(funcionarioIcarus.getIdMutuario());
			pessoaDTO.setIdPessoa(funcionarioIcarus.getIdPessoa());
		}
		
		dto.setPessoa(pessoaDTO);

		return dto;
	}

	public static DTO funcionarioIcarus2UsuarioPasswdDtoMapper(Funcionario funcionario,
			FuncionarioIcarus funcionarioIcarus, BigDecimal idPerfil) {

		PerfilIdDTO perfilIdDTO = new PerfilIdDTO();
		perfilIdDTO.setIdPerfil(idPerfil);
		
		modelMapper = new ModelMapper();
		modelMapper.typeMap(Funcionario.class, UsuarioPasswdDTO.class).addMappings(mapper -> {
			mapper.map(Funcionario::getCpf, UsuarioPasswdDTO::setPasswordUsuario);
		});
		
		UsuarioPasswdDTO usuarioPasswdDTO = modelMapper.map(funcionario, UsuarioPasswdDTO.class);
		usuarioPasswdDTO.setPessoa(funcionarioIcarus2PessoaIdDTOMapper(funcionarioIcarus));
		usuarioPasswdDTO.setPerfil(perfilIdDTO);

		return usuarioPasswdDTO;
	}

	private static PessoaIdDTO funcionarioIcarus2PessoaIdDTOMapper(FuncionarioIcarus bean) {
		
		modelMapper = new ModelMapper();
		modelMapper.typeMap(FuncionarioIcarus.class, PessoaIdDTO.class).addMappings(mapper -> {
			mapper.map(FuncionarioIcarus::getIdPessoa, PessoaIdDTO::setIdPessoa);
		});
		
		return modelMapper.map(bean, PessoaIdDTO.class);
	}

	public static DTO funcionario2MutuarioDemissaoDtoMapper(Funcionario funcionario) {
		Converter<BigDecimal, String> getTipoTurnover = new Converter<BigDecimal, String>() {
			public String convert(MappingContext<BigDecimal, String> context) {
				
				if (context.getSource() == null)
					return null;
				
				List<Integer> turnoverInvoluntario = Arrays.asList(1, 3, 4, 5, 7, 8, 9, 10, 11);
				
				return turnoverInvoluntario.contains(context.getSource().intValue()) ? "INVOLUNTARIO" : "VOLUNTARIO";
			}
		};

		modelMapper = new ModelMapper();
		modelMapper.typeMap(Funcionario.class, MutuarioDemissaoDTO.class).addMappings(mapper -> {
			mapper.map(Funcionario::getDtDem, MutuarioDemissaoDTO::setDtDesligamento);
			mapper.map(Funcionario::getDescrTipResc, MutuarioDemissaoDTO::setMotivoDesligamento);
			mapper.using(getTipoTurnover).map(Funcionario::getCodTpr, MutuarioDemissaoDTO::setTipoTurnover);
		});

		MutuarioDemissaoDTO dto = modelMapper.map(funcionario, MutuarioDemissaoDTO.class);
		dto.setSuspenderAcessos(SimNaoEnum.NAO.toString());
		
		return dto;
	}

	/**
	 * 
	 * @param regimeTrab
	 * @return
	 * @deprecated 2023-08-10
	 */
	public static String getCodigoRegimeTrabalho(BigDecimal regimeTrab) {
		String codigoRegimeTrabalho = "CLT";

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(IcarusMapper.class, "IcarusMapper_queCodigoRegimeTrabalho.sql");
			sql.setNamedParameter("NOMETAB", "TFPFUN");
			sql.setNamedParameter("NOMECAMPO", "REGIMETRAB");
			sql.setNamedParameter("VALOR", regimeTrab.toString());

			rs = sql.executeQuery();

			while (rs.next()) {
				codigoRegimeTrabalho = rs.getString("OPCAO");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return codigoRegimeTrabalho;
	}

	public static DTO identificacaoCargaHoraria2TurnoDtoMapper(IdentificacaoCargaHoraria bean) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(IdentificacaoCargaHoraria.class, TurnoDTO.class).addMappings(mapper -> {
			mapper.using(toString).map(IdentificacaoCargaHoraria::getCodCargaHor, TurnoDTO::setCodigo);
			mapper.map(IdentificacaoCargaHoraria::getDescrCargaHor, TurnoDTO::setDescricao);
		});
		
		TurnoDTO dto = modelMapper.map(bean, TurnoDTO.class);
		dto.setAplicarToleranciaIntervalos(SIM);
		dto.setFusoHorario("UTC_MENOS_TRES");
		dto.setIgnoraFeriados(NAO);
		dto.setIntervaloFlexivel(SIM);
		dto.setIntervaloPreAssinalado(NAO);
		dto.setTipoTurno("SEMANAL");
		dto.setInicioAdicionalNoturno("22:00:00");
		dto.setHoraExtraDSR150(BigDecimal.ZERO);
		dto.setHoraExtraNormal(BigDecimal.ZERO);
		dto.setHoraExtraDSR(BigDecimal.ZERO);
		dto.setTolerancia(BigDecimal.TEN);
		dto.setToleranciaPorBatida(SIM);
		dto.setJornadaFlexivel(SIM);

		return dto;
	}

	public static TurnoDiaDTO turnoDia2TurnoDiaDtoMapper(TurnoDiaIcarus bean) {
		Converter<BigDecimal, String> getTipoTurnoDia = new Converter<BigDecimal, String>() {
			public String convert(MappingContext<BigDecimal, String> context) {
				return context.getSource() == null ? null : TipoTurnoDiaEnum.toString(context.getSource());
			}
		};
		
		modelMapper = new ModelMapper();
		modelMapper.typeMap(TurnoDiaIcarus.class, TurnoDiaDTO.class).addMappings(mapper -> {
			mapper.using(getTipoTurnoDia).map(TurnoDiaIcarus::getDiaSem, TurnoDiaDTO::setTipoTurnoDia);
			mapper.using(getSimNaoFromString).map(TurnoDiaIcarus::getDescansoSem, TurnoDiaDTO::setFolga);
			mapper.map(TurnoDiaIcarus::getIdTurnoDia, TurnoDiaDTO::setIdTurnoDia);
		});
		
		return modelMapper.map(bean, TurnoDiaDTO.class);
	}

	public static TurnoPeriodoDTO turnoPeriodo2TurnoPeriodoDtoMapper(TurnoPeriodoIcarus bean) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(TurnoPeriodoIcarus.class, TurnoPeriodoDTO.class).addMappings(mapper -> {
			mapper.map(TurnoPeriodoIcarus::getHoraTurno, TurnoPeriodoDTO::setHora);
			mapper.map(TurnoPeriodoIcarus::getTipo, TurnoPeriodoDTO::setTipo);
			mapper.map(TurnoPeriodoIcarus::getIdturnoPeriodo, TurnoPeriodoDTO::setIdTurnoPeriodo);
		});

		return modelMapper.map(bean, TurnoPeriodoDTO.class);
	}

	public static DTO registroOcorrenciaFerias2AfastamentoPontoSaveDTO(RegistroOcorrencia bean) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(RegistroOcorrencia.class, AfastamentoPontoSaveDTO.class).addMappings(mapper -> {
			mapper.map(RegistroOcorrencia::getDescrOcor, AfastamentoPontoSaveDTO::setJustificativa);
			mapper.using(ajustaDataClearDate).map(RegistroOcorrencia::getDtInicOcor, AfastamentoPontoSaveDTO::setDiaInicio);
			mapper.using(ajustaDataClearDate).map(RegistroOcorrencia::getDtFinalOcor, AfastamentoPontoSaveDTO::setDiaFim);
		});
		
		AfastamentoPontoSaveDTO dto = modelMapper.map(bean, AfastamentoPontoSaveDTO.class);
		dto.setTipoAfastamento("FERIAS");
		dto.setAntecipar13(NAO);
		dto.setDiasAbono(BigDecimal.ZERO);
		
		return dto;
	}

	public static DTO historicoOcorrencia2MotivoAbonoPontoSaveDtoMapper(HistoricoOcorrencia bean) {

		modelMapper = new ModelMapper();
		modelMapper.typeMap(HistoricoOcorrencia.class, MotivoAbonoPontoSaveDTO.class).addMappings(mapper -> {
			mapper.map(HistoricoOcorrencia::getDescrHistOcor, MotivoAbonoPontoSaveDTO::setDescricao);
		});
		
		MotivoAbonoPontoSaveDTO dto = modelMapper.map(bean, MotivoAbonoPontoSaveDTO.class);
		dto.setTipoAbono("ABONO");
		
		return dto;
	}

	public static DTO registroOcorrencia2AfastamentoPontoSaveDTO(RegistroOcorrencia bean) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(RegistroOcorrencia.class, AfastamentoPontoSaveDTO.class).addMappings(mapper -> {
			mapper.map(RegistroOcorrencia::getDescrOcor, AfastamentoPontoSaveDTO::setJustificativa);
			mapper.using(ajustaDataClearDate).map(RegistroOcorrencia::getDtInicOcor, AfastamentoPontoSaveDTO::setDiaInicio);
			mapper.using(ajustaDataClearDate).map(RegistroOcorrencia::getDtFinalOcor, AfastamentoPontoSaveDTO::setDiaFim);
		});
		
		AfastamentoPontoSaveDTO dto = modelMapper.map(bean, AfastamentoPontoSaveDTO.class);
		dto.setTipoAfastamento("AFASTAMENTO");
		dto.setAntecipar13(NAO);
		dto.setDiasAbono(BigDecimal.ZERO);
		
		return dto;
	}

	public static DTO registroOcorrencia2AfastamentoPontoUpdateDTO(RegistroOcorrencia bean) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(RegistroOcorrencia.class, AfastamentoPontoUpdateDTO.class).addMappings(mapper -> {
			mapper.map(RegistroOcorrencia::getDescrOcor, AfastamentoPontoUpdateDTO::setJustificativa);
			mapper.using(ajustaDataClearDate).map(RegistroOcorrencia::getDtFinalOcor, AfastamentoPontoUpdateDTO::setDiaFim);
		});
		
		return modelMapper.map(bean, AfastamentoPontoUpdateDTO.class);
	}

	public static DTO registroOcorrencia2AbonoPontoSaveDTO(RegistroOcorrencia bean) throws RecordNotFoundException {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(RegistroOcorrencia.class, AbonoPontoSaveDTO.class).addMappings(mapper -> {
			mapper.map(RegistroOcorrencia::getDescrOcor, AbonoPontoSaveDTO::setJustificativa);
			mapper.using(ajustaDataClearDate).map(RegistroOcorrencia::getDtInicOcor, AbonoPontoSaveDTO::setDataSolicitacao);
		});

		AbonoPontoSaveDTO dto = modelMapper.map(bean, AbonoPontoSaveDTO.class);
		
		ConfiguracaoHistoricoOcorrenciaIcarus cfgHistorico = new ConfiguracaoHistoricoOcorrenciaIcarus();
		cfgHistorico.loadByPk(bean.getCodHistOcor());
		
		if ("H".equals(cfgHistorico.getUnidade())) {
			long differenceInMinutes = TimeUtils.getDifferenceInMinutes(bean.getDtInicOcor(), bean.getDtFinalOcor());
			BigDecimal timeInMinutes = TimeUtils.minutes2Time(BigDecimal.valueOf(differenceInMinutes));
			String tempoAbono = TimeUtils.getDisplayableTime(timeInMinutes);
			
			dto.setTempoAbono(tempoAbono);
		} else {
			dto.setHoraFim(bean.getDtFinalOcor());
			dto.setHoraInicioDateTime(bean.getDtFinalOcor());
			dto.setHoraInicio(bean.getDtInicOcor());
			dto.setHoraFimDateTime(bean.getDtInicOcor());
			
			dto.setTipoPeriodoAbono("PERIODO_ESPECIFICO");
		}
		
		return dto;
	}

	public static AjustePontoSaveDTO funcionarioAjuste2AjustePontoSaveDTO(FuncionarioAjuste bean) throws Exception {
		AjustePontoSaveDTO dto = new AjustePontoSaveDTO();
		
		MutuarioIdDTO mutuarioDTO = new MutuarioIdDTO();
		mutuarioDTO.setIdMutuario(bean.getFuncionario().getIdMutuario());
		
		dto.setMutuario(mutuarioDTO);
		
		Gson gson = new GsonBuilder().create();
		
		Timestamp dtInicOcor;
		AjustePontoHorarioDTO ajusteDTO;
		LocalDateTimeDTO datetimeDTO;
		List<AjustePontoHorarioDTO> ajustes = new LinkedList<>();
		for (RegistroOcorrencia ocorrencia:bean.getOcorrencias()) {
			if (dto.getJustificativa() == null)
				dto.setJustificativa(ocorrencia.getDescrOcor());
			
			if (dto.getDataSolicitacao() == null)
				dto.setDataSolicitacao(
						TimeUtils.dataAdd(TimeUtils.clearTime(ocorrencia.getDtInicOcor()), 12, Calendar.HOUR_OF_DAY));
			
		}
		
		Timestamp horaRegistro;
		for (PontoHorarioBatidaDTO ponto:bean.getBatidas().getBatidas()) {
			horaRegistro = TimeUtils.toTimestamp(ponto.getHoraRegistroFormatado(), "dd/MM/yyyy HH:mm");
			
			datetimeDTO = new LocalDateTimeDTO();
			datetimeDTO.setYear(BigDecimal.valueOf(TimeUtils.getYear(horaRegistro)));
			datetimeDTO.setMonthValue(BigDecimal.valueOf(TimeUtils.getMonth(horaRegistro)));
			datetimeDTO.setDayOfMonth(BigDecimal.valueOf(TimeUtils.getDay(horaRegistro)));
			
			Calendar c = new GregorianCalendar();
			c.setTimeInMillis(horaRegistro.getTime());
			
			datetimeDTO.setHour(BigDecimal.valueOf(c.get(Calendar.HOUR_OF_DAY)));
			datetimeDTO.setMinute(BigDecimal.valueOf(c.get(Calendar.MINUTE)));
			datetimeDTO.setSecond(BigDecimal.ZERO);
			
			ajusteDTO = new AjustePontoHorarioDTO();
			ajusteDTO.setHorarioDateTime(gson.toJson(datetimeDTO, datetimeDTO.getClass()));
			
			ajustes.add(ajusteDTO);
		}
		
		dto.setAjustePontoHorario(ajustes);
		
		return dto;
	}

	public static DTO funcionarioIcarus2MutuarioIdDTOMapper(FuncionarioIcarus bean) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(FuncionarioIcarus.class, MutuarioIdDTO.class).addMappings(mapper -> {
			mapper.map(FuncionarioIcarus::getIdMutuario, MutuarioIdDTO::setIdMutuario);
		});
		
		return modelMapper.map(bean, MutuarioIdDTO.class);
	}

	public static EntityBean pontoDto2PontoIcarusMapper(PontoDTO dto) {
		
		modelMapper = new ModelMapper();
		modelMapper.typeMap(PontoDTO.class, PontoIcarus.class).addMappings(mapper -> {
			mapper.using(string2BigDecimal).map(PontoDTO::getCodemp, PontoIcarus::setCodEmp);
			//mapper.map(PontoDTO::getCodfunc, PontoIcarus::setMatricula);
			mapper.map(PontoDTO::getBatidas, PontoIcarus::setBatidas);
			mapper.using(string2BigDecimal).map(PontoDTO::getNormal, PontoIcarus::setNormal);
			mapper.using(string2BigDecimal).map(PontoDTO::getFaltas, PontoIcarus::setFaltas);
			mapper.using(string2BigDecimal).map(PontoDTO::getNoturna, PontoIcarus::setNoturna);
			mapper.using(string2BigDecimal).map(PontoDTO::getAtestado, PontoIcarus::setAtestado);
			mapper.using(string2BigDecimal).map(PontoDTO::getTrabalhado, PontoIcarus::setTrabalhado);
			mapper.using(string2BigDecimal).map(PontoDTO::getTotal, PontoIcarus::setTotal);
		});
		
		PontoIcarus bean = modelMapper.map(dto, PontoIcarus.class);
		bean.setMatricula(dto.getCodfunc());
		try {
			Timestamp dtMov = TimeUtils.toTimestamp(dto.getDtmov(), "yyyy-MM-dd");
			bean.setDtMovimento(dtMov);
		} catch (Exception e) {
			return null;
		}
				
		return bean;
	}

	public static TurnoIdDTO turnoIcarus2TurnoIdDtoMapper(TurnoIcarus bean) {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(TurnoIcarus.class, TurnoIdDTO.class).addMappings(mapper -> 
			mapper.map(TurnoIcarus::getIdTurno, TurnoIdDTO::setIdTurno)
		);
		
		return modelMapper.map(bean, TurnoIdDTO.class);
	}

}

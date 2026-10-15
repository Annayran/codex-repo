package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.sankhya.util.JdbcUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import tech.skala.action.beans.cargo.Cargo;
import tech.skala.action.beans.departamento.Departamento;
import tech.skala.action.beans.funcionario.Funcionario;
import tech.skala.action.beans.funcionario.HistoricoOcorrencia;
import tech.skala.action.beans.registroocorrencia.RegistroOcorrencia;
import tech.skala.action.modules.empresas.Empresa;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * Classe do tipo {@code DAO - Data Access Object}
 * 
 * @author cassiomenezes
 * @since 2023-06-01
 *
 */
public class IcarusDAO {
	private static final Logger LOGGER = Logger.getLogger("tech.skala.action.integration.pontoicarus.IcarusDAO");

	private IcarusDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static ConfiguracaoIcarus getConfiguracaoEmpresa(final BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		
		ConfiguracaoIcarus configuracao = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			JapeWrapper dao = JapeFactory.dao(ConfiguracaoIcarus.ENTITY_NAME);
			DynamicVO vo = dao.findByPK(codEmp);
			
			configuracao = new ConfiguracaoIcarus();
			configuracao.loadByVO(vo);
			
		} catch (Exception e) {
			String msg = String.format("Não foi possível encontrar as configurações da empresa %s.", codEmp);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return configuracao;
	}

	public static void updateToken(ConfiguracaoIcarus config) throws SkalaModelException {
		LOGGER.info("updateToken");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					config.persists();
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar o Token.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public static void persistIdLotacao(BigDecimal codEmp, BigDecimal codDep, BigDecimal idLotacao)
			throws SkalaModelException {
		LOGGER.info("persistIdLotacao");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					DepartamentoIcarus departamento = new DepartamentoIcarus();
					departamento.setCodDep(codDep);
					departamento.setCodEmp(codEmp);
					departamento.setIdLotacao(idLotacao);
					departamento.persists();
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir a Lotação.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public static void persistIdCargo(BigDecimal codEmp, BigDecimal codCargo, BigDecimal idCargo)
			throws SkalaModelException {
		LOGGER.info("persistIdCargo");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					CargoIcarus cargo = new CargoIcarus();
					cargo.setCodCargo(codCargo);
					cargo.setCodEmp(codEmp);
					cargo.setIdCargo(idCargo);
					cargo.persists();
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Cargo.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	public static void persistIdRegimeContratacao(BigDecimal codEmp, String codigo, BigDecimal idRegimeContratacao)
			throws SkalaModelException {
		LOGGER.info("persistIdRegimeContratacao");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					RegimeContratacaoIcarus bean = new RegimeContratacaoIcarus();
					bean.setCodEmp(codEmp);
					bean.setCodigo(codigo);
					bean.setIdRegimeContratacao(idRegimeContratacao);
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

	public static EmpresaIcarus getEmpresaIcarusByPk(final Object... keyValue) throws RecordNotFoundException {
		EmpresaIcarus empresaIcarus = new EmpresaIcarus();
		empresaIcarus.loadByPk(keyValue);
		
		return empresaIcarus;
	}

	public static CargoIcarus getCargoIcarusByPk(final Object... keyValue) throws RecordNotFoundException {
		CargoIcarus bean = new CargoIcarus();
		bean.loadByPk(keyValue);

		return bean;
	}

	public static DepartamentoIcarus getDepartamentoIcarusByPk(final Object... keyValue) throws RecordNotFoundException {
		DepartamentoIcarus bean = new DepartamentoIcarus();
		bean.loadByPk(keyValue);
		
		return bean;
	}

	public static String getCodigoRegimeTrabalho(BigDecimal regimeTrab) {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;

		String codigoRegimeTrabalho = "CLT";

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(IcarusDAO.class, "IcarusMapper_queCodigoRegimeTrabalho.sql");
			sql.setNamedParameter("NOMETAB", "TFPFUN");
			sql.setNamedParameter("NOMECAMPO", "REGIMETRAB");
			sql.setNamedParameter("VALOR", regimeTrab.toString());
			
			ResultSet rs = null;
			try {
				rs = sql.executeQuery();

				while (rs.next()) {
					codigoRegimeTrabalho = rs.getString("OPCAO");
				}				
			} finally {
				JdbcUtils.closeResultSet(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return codigoRegimeTrabalho;
	}

	public static RegimeContratacaoIcarus getRegimeContratacaoIcarusByPk(final Object... keyValue) throws RecordNotFoundException {
		RegimeContratacaoIcarus regimeContratacao = new RegimeContratacaoIcarus();
		regimeContratacao.loadByPk(keyValue);
		
		return regimeContratacao;
	}

	public static Funcionario getFuncionarioByPk(final Object... keyValue) throws RecordNotFoundException {
		Funcionario bean = new Funcionario();
		bean.loadByPk(keyValue);
		
		return bean;
	}
	
	public static FuncionarioIcarus getFuncionarioIcarusByPk(final Object... keyValue) throws RecordNotFoundException {
		FuncionarioIcarus bean = null;
		try {
			bean = new FuncionarioIcarus();
			bean.loadByPk(keyValue);
		} catch (Exception e) {
			LOGGER.info("Funcionario não integrado.");
		}
		
		return bean;
	}

	public static List<RegistroOcorrencia> getFerias(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		List<RegistroOcorrencia> ocorrencias = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.CODHISTOCOR IN (27) ");
			sbWhere.append(
					"AND this.DTINICOCOR > TO_DATE('19/05/2023','DD/MM/YYYY') ");
			sbWhere.append("AND NOT EXISTS(SELECT 1 FROM AD_PLTPIOCO T WHERE T.NUOCOR = this.NUOCOR) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");

			RegistroOcorrencia ocorrencia;
			ocorrencias = new LinkedList<>();

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.REGISTRO_OCORRENCIA);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp);
			for (DynamicVO vo : vos) {
				ocorrencia = new RegistroOcorrencia();
				ocorrencia.loadByVO(vo);

				ocorrencias.add(ocorrencia);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Férias.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return ocorrencias;
	}

	public static void persistFerias(RegistroOcorrencia bean) throws SkalaModelException {
		LOGGER.info("persistFerias");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					RegistroOcorrenciaIcarus ocorrencia = new RegistroOcorrenciaIcarus();
					ocorrencia.setNuOcor(bean.getNuOcor());
					ocorrencia.setTipoAfastamento("F");
					ocorrencia.persists();
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Mutuário.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public static List<Funcionario> getFuncionarios(BigDecimal codigo) throws RecordNotFoundException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
	
		LinkedList<Funcionario> funcionarios = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			hnd.setFindersMaxRows(-1);
			
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			Funcionario funcionario;
			funcionarios = new LinkedList<>();
			
			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append(
					"this.DTDEM IS NULL AND this.SITUACAO NOT IN (0,8,9) AND this.VINCULO NOT IN (2,30,80,90,95,99) ");
			sbWhere.append(
					"AND NOT EXISTS(SELECT 1 FROM AD_PLTPIFUN T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");
			sbWhere.append(
					"AND this.DTADM <= TRUNC(SYSDATE)" );
//			sbWhere.append("AND this.CODEMP = 1 AND this.CODFUNC = 3722 ");

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codigo);
			for (DynamicVO vo : vos) {
				funcionario = new Funcionario();
				funcionario.loadByVO(vo);
				
				funcionarios.add(funcionario);
			}

		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return funcionarios;
	}

	public static List<Funcionario> getFuncionariosAlteracao(BigDecimal codigo) throws RecordNotFoundException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
	
		LinkedList<Funcionario> funcionarios = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			hnd.setFindersMaxRows(-1);
			
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			Funcionario funcionario;
			funcionarios = new LinkedList<>();
			
			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append(
					"EXISTS(SELECT 1 FROM AD_PLTPIFUN T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIFUNUPD T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC AND T.DHINTEG IS NULL) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codigo);
			for (DynamicVO vo : vos) {
				funcionario = new Funcionario();
				funcionario.loadByVO(vo);
				
				funcionarios.add(funcionario);
			}

		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return funcionarios;
	}

	public static List<Funcionario> getFuncionariosDemitidos(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		LinkedList<Funcionario> funcionariosDemitidos = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			Funcionario funcionario;
			funcionariosDemitidos = new LinkedList<>();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append(
					"EXISTS(SELECT 1 FROM TFPAVI T WHERE T.DTCANCEL IS NULL AND T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIFUN T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");
			sbWhere.append(
					"AND NOT EXISTS(SELECT 1 FROM AD_PLTPIDEM T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC) ");
//			sbWhere.append("AND (this.CODEMP = 1 AND this.CODFUNC = 1441) ");

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp);
			for (DynamicVO vo : vos) {
				funcionario = new Funcionario();
				funcionario.loadByVO(vo);
				if (funcionario.getDtDem() == null)
					funcionario.setDtDem(getDtFimAviso(funcionario.getCodEmp(), funcionario.getCodFunc()));

				funcionariosDemitidos.add(funcionario);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Funcionários Demitidos.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return funcionariosDemitidos;
	}

	public static List<Funcionario> getFuncionariosTransferidos(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		LinkedList<Funcionario> funcionariosDemitidos = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			Funcionario funcionario;
			funcionariosDemitidos = new LinkedList<>();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append(
					"this.DTDEM IS NOT NULL AND this.SITUACAO IN (8) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIFUN T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");
			sbWhere.append(
					"AND NOT EXISTS(SELECT 1 FROM AD_PLTPIDEM T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC) ");
//			sbWhere.append("AND (this.CODEMP = 1 AND this.CODFUNC = 1441) ");

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp);
			for (DynamicVO vo : vos) {
				funcionario = new Funcionario();
				funcionario.loadByVO(vo);
//				funcionario.setDtDem(getDtFimAviso(funcionario.getCodEmp(), funcionario.getCodFunc()));
				funcionario.setDtDem(TimeUtils.clearTime(TimeUtils.getNow()));

				funcionariosDemitidos.add(funcionario);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Funcionários Demitidos.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return funcionariosDemitidos;
	}

	public static Timestamp getDtFimAviso(final BigDecimal codEmp, final BigDecimal codFunc) throws RecordNotFoundException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
	
		Timestamp dtFimAviso = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			JapeWrapper dao = JapeFactory.dao("AvisoPrevio");
			DynamicVO vo = dao.findOne("this.CODEMP = ? AND this.CODFUNC = ? " , codEmp, codFunc);
			
			if (vo != null)
				dtFimAviso = vo.asTimestamp("DTFIMAVISO");
			
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
		
		return dtFimAviso;
	}

	public static List<HistoricoOcorrencia> getHistoricosOcorrencia(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		List<HistoricoOcorrencia> historicos = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			HistoricoOcorrencia historico;
			historicos = new LinkedList<>();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("EXISTS(SELECT 1 FROM AD_PLTPICFGHIS T WHERE T.CODHISTOCOR = this.CODHISTOCOR) ");
			sbWhere.append(
					"AND NOT EXISTS(SELECT 1 FROM AD_PLTPIHIS T WHERE T.CODEMP = ? AND T.CODHISTOCOR = this.CODHISTOCOR) ");

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.HISTORICO_OCORRENCIA);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp);
			for (DynamicVO vo : vos) {
				historico = new HistoricoOcorrencia();
				historico.loadByVO(vo);

				historicos.add(historico);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar os Históricos de Ocorrência.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return historicos;
	}

	public static void persistIdHistoricoOcorrencia(BigDecimal codEmp, BigDecimal codHistOcor,
			BigDecimal idMotivoAbonoPonto) throws SkalaModelException {
		LOGGER.info("persistIdHistoricoOcorrencia");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					HistoricoOcorrenciaIcarus bean = new HistoricoOcorrenciaIcarus();
					bean.setCodEmp(codEmp);
					bean.setCodHistOcor(codHistOcor);
					bean.setIdMotivoAbonoPonto(idMotivoAbonoPonto);
					bean.persists();
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Motivo Abono Ponto.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	public static List<RegistroOcorrencia> getAfastamentos(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		List<RegistroOcorrencia> ocorrencias = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("(this.DTFINALOCOR IS NULL OR this.DTFINALOCOR > GET_TSIPAR_DATA('AD_DTINIFECH')) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIHIS T WHERE T.CODEMP = ? AND T.CODHISTOCOR = this.CODHISTOCOR) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGHIS T WHERE T.CODHISTOCOR =  this.CODHISTOCOR AND T.TIPO IN ('A')) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");
			sbWhere.append("AND NOT EXISTS(SELECT 1 FROM AD_PLTPIOCO T WHERE T.NUOCOR = this.NUOCOR) ");
//			sbWhere.append("AND this.CODEMP = 2 AND this.CODFUNC = 2088 ");

			RegistroOcorrencia ocorrencia;
			ocorrencias = new LinkedList<>();

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.REGISTRO_OCORRENCIA);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp, codEmp);
			for (DynamicVO vo : vos) {
				ocorrencia = new RegistroOcorrencia();
				ocorrencia.loadByVO(vo);

				ocorrencias.add(ocorrencia);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Afastamentos.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return ocorrencias;
	}

	public static List<RegistroOcorrencia> getRetornosAfastamentos(BigDecimal codigo) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		List<RegistroOcorrencia> ocorrencias = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.DTFINALOCOR > GET_TSIPAR_DATA('AD_DTINIFECH') ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIHIS T WHERE T.CODEMP = ? AND T.CODHISTOCOR = this.CODHISTOCOR) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIOCO T WHERE T.NUOCOR = this.NUOCOR AND T.IDAFASTAMENTOPONTO IS NOT NULL AND T.DHATURETORNO IS NULL) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");

			RegistroOcorrencia ocorrencia;
			ocorrencias = new LinkedList<>();

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.REGISTRO_OCORRENCIA);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codigo, codigo);
			for (DynamicVO vo : vos) {
				ocorrencia = new RegistroOcorrencia();
				ocorrencia.loadByVO(vo);

				ocorrencias.add(ocorrencia);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Retornos de Afastamentos.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return ocorrencias;
	}

	public static BigDecimal getIdMotivoAbonoPonto(BigDecimal codEmp, BigDecimal codHistOcor)
			throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		BigDecimal id = null;

		try {
			JapeWrapper dao = JapeFactory.dao("AD_PLTPIHIS");
			DynamicVO vo = dao.findByPK(codEmp, codHistOcor);

			if (vo != null)
				id = vo.asBigDecimal("IDMOTIVOABONOPONTO");

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar o Histórico.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return id;
	}

	public static void persistAfastamento(RegistroOcorrencia bean) throws SkalaModelException {
		LOGGER.info("persistAfastamento");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					RegistroOcorrenciaIcarus ocorrencia = new RegistroOcorrenciaIcarus();
					ocorrencia.setNuOcor(bean.getNuOcor());
					ocorrencia.setTipoAfastamento("A");
					ocorrencia.persists();
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Ocorrência.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public static List<RegistroOcorrencia> getAbonos(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		List<RegistroOcorrencia> ocorrencias = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append(
					"(this.DTINICOCOR > TO_DATE('19/06/2023','DD/MM/YYYY') OR (this.DTINICOCOR < TO_DATE('20/06/2023','DD/MM/YYYY') AND (this.DTFINALOCOR IS NULL OR this.DTFINALOCOR  > TO_DATE('19/06/2023','DD/MM/YYYY')))) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIHIS T WHERE T.CODEMP = ? AND T.CODHISTOCOR = this.CODHISTOCOR) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGHIS T WHERE T.CODHISTOCOR =  this.CODHISTOCOR AND T.TIPO IN ('O')) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");
			sbWhere.append("AND NOT EXISTS(SELECT 1 FROM AD_PLTPIOCO T WHERE T.NUOCOR = this.NUOCOR) ");

			RegistroOcorrencia ocorrencia;
			ocorrencias = new LinkedList<>();

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.REGISTRO_OCORRENCIA);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp, codEmp);
			for (DynamicVO vo : vos) {
				ocorrencia = new RegistroOcorrencia();
				ocorrencia.loadByVO(vo);

				ocorrencias.add(ocorrencia);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Abonos.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return ocorrencias;
	}

	public static List<FuncionarioAjuste> getFuncionariosComAjustes(BigDecimal codEmpRef) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		BigDecimal codEmp = null;
		BigDecimal codFunc = null;
		Timestamp dtInicOcor = null;

		List<FuncionarioAjuste> beans = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			FuncionarioAjuste funcionarioAjuste;
			FuncionarioIcarus funcionario;
			List<RegistroOcorrencia> ocorrenciasAjuste;
			beans = new LinkedList<>();

			sql = new NativeSql(jdbc);

			sql.loadSql(IcarusFinder.class, "IcarusDAO_queAjustePonto.sql");
			sql.setNamedParameter("CODEMPREF", codEmpRef);

			rs = sql.executeQuery();
			while (rs.next()) {
				codEmp = rs.getBigDecimal(SklFieldName.CODEMP);
				codFunc = rs.getBigDecimal(SklFieldName.CODFUNC);
				dtInicOcor = rs.getTimestamp("DTINICOCOR");
				
				funcionario = new FuncionarioIcarus();
				funcionario.setCodEmp(codEmp);
				funcionario.setCodFunc(codFunc);
				funcionario.setIdMutuario(rs.getBigDecimal("IDMUTUARIO"));
				
				ocorrenciasAjuste = getOcorrenciasAjuste(codEmpRef, codEmp, codFunc, dtInicOcor);
				
				funcionarioAjuste = new FuncionarioAjuste();
				funcionarioAjuste.setFuncionario(funcionario);
				funcionarioAjuste.setDataOcorrencia(dtInicOcor);
				funcionarioAjuste.setOcorrencias(ocorrenciasAjuste);
				
				beans.add(funcionarioAjuste);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Funcionarios com Ajustes.", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return beans;
	}

	private static List<RegistroOcorrencia> getOcorrenciasAjuste(BigDecimal codEmpRef, BigDecimal codEmp, BigDecimal codFunc, Timestamp dtInicOcor) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		List<RegistroOcorrencia> ocorrencias = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append(
					"(this.DTINICOCOR > TO_DATE('19/06/2023','DD/MM/YYYY') OR (this.DTINICOCOR < TO_DATE('20/06/2023','DD/MM/YYYY') AND (this.DTFINALOCOR IS NULL OR this.DTFINALOCOR  > TO_DATE('19/06/2023','DD/MM/YYYY')))) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIHIS T WHERE T.CODEMP = ? AND T.CODHISTOCOR = this.CODHISTOCOR) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGHIS T WHERE T.CODHISTOCOR =  this.CODHISTOCOR AND T.TIPO IN ('O')) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");
			sbWhere.append("AND NOT EXISTS(SELECT 1 FROM AD_PLTPIOCO T WHERE T.NUOCOR = this.NUOCOR) ");
			sbWhere.append("AND this.CODEMP = ? AND this.CODFUNC = ? ");
			sbWhere.append("AND TRUNC(this.DTINICOCOR) = ? ");
			
			RegistroOcorrencia ocorrencia;
			ocorrencias = new LinkedList<>();

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.REGISTRO_OCORRENCIA);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmpRef, codEmpRef, codEmp, codFunc,
					TimeUtils.clearTime(dtInicOcor));
			for (DynamicVO vo : vos) {
				ocorrencia = new RegistroOcorrencia();
				ocorrencia.loadByVO(vo);

				ocorrencias.add(ocorrencia);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Ajustes.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
		
		return ocorrencias;

	}

	
	public static List<Departamento> getDepartamentos(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		
		List <Departamento> departamentos = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();	
			
			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.ANALITICO = 'S' ");
			sbWhere.append("AND this.CODDEP > 0 ");
			sbWhere.append("AND NOT EXISTS(SELECT 1 FROM AD_PLTPILOTACAO T WHERE T.CODEMP = ? AND T.CODDEP = this.CODDEP) ");
			sbWhere.append("AND EXISTS(SELECT 1 FROM TFPFUN T WHERE T.CODDEP = this.CODDEP) ");

			Departamento departamento;
			departamentos = new LinkedList<>();
			
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.DEPARTAMENTO);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp);
			for (DynamicVO vo : vos) {
				departamento = new Departamento();
				departamento.loadByVO(vo);

				departamentos.add(departamento);
			}
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar departamento.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return departamentos;
	}

	public static List<Cargo> getCargo(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		
		List <Cargo> cargos = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.ATIVO = 'S' ");
			sbWhere.append("AND this.CODCARGO > 0 ");
			sbWhere.append("AND NOT EXISTS(SELECT 1 FROM AD_PLTPICARGO T WHERE T.CODEMP = ? AND T.CODCARGO = this.CODCARGO) ");
			
			Cargo cargo;
			cargos = new LinkedList<>();

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CARGO);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp);
			
			for (DynamicVO vo : vos) {
				cargo = new Cargo();
				cargo.loadByVO(vo);

				cargos.add(cargo);
			}
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar cargo.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return cargos;
	}

	public static List<RegimeContratacaoIcarus> getContratacoes(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		
		List <RegimeContratacaoIcarus> contratacoes = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();		
			
			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.IDREGIMECONTRATACAO IS NULL ");
			sbWhere.append("AND this.CODEMP = ? ");
			RegimeContratacaoIcarus contratacao;
			contratacoes = new LinkedList<>();
	
			JapeWrapper dao = JapeFactory.dao("AD_PLTPIREG");
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp);
			for (DynamicVO vo : vos) {
				contratacao = new RegimeContratacaoIcarus();
				contratacao.loadByVO(vo);
				
				contratacoes.add(contratacao);
			}
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar contratação.", e);
			
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
		
		return contratacoes;
	}

	public static List<Empresa> getEmpresas(BigDecimal codEmp) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		List<Empresa> centroCustos = null;
		
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.CODEMP BETWEEN 1 AND 899 AND NOT EXISTS(SELECT 1 FROM AD_PLTPICENCUS T WHERE T.CODEMP = this.CODEMP) ");
			sbWhere.append("AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");
			
			Empresa centroCusto;
			centroCustos = new LinkedList<>();
			
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.EMPRESA);
			Collection<DynamicVO> empresasVO = dao.find(sbWhere.toString(), codEmp);
			for (DynamicVO vo:empresasVO) {
				centroCusto = new Empresa();
				centroCusto.loadByVO(vo);
				
				centroCustos.add(centroCusto);
				
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Empresas.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
		
		return centroCustos;
			
	}

	public static BigDecimal getCodFuncFromMatricula(BigDecimal codEmp, String matricula) throws SkalaModelException {
		SessionHandle hnd = null;
		
		BigDecimal codFunc = null;
		try {
			hnd = JapeSession.open();

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.CODEMP = ? AND this.MATRICULA = ? ");

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
			DynamicVO vo = dao.findOne(sbWhere.toString(), codEmp, matricula);
			
			codFunc = vo.asBigDecimal(SklFieldName.CODFUNC);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Funcionario a partir da matricula.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
		return codFunc;
	}

	public static void persistsPontoIcarus(final PontoIcarus pontoIcarus, final Timestamp dtIniFech) throws SkalaModelException {
		LOGGER.info("persistsPontoIcarus");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			hnd.setFindersMaxRows(-1);
			
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					pontoIcarus.persists();
					
					persistsTfpRpo(pontoIcarus.getCodEmp(), pontoIcarus.getCodFuncionario(), dtIniFech);
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível persistir o Registro de Ponto.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}
	
	private static void persistsTfpRpo(BigDecimal codEmp, BigDecimal codFunc, Timestamp dtIniFech) throws SkalaModelException {
		LOGGER.info("persistsTfpRpo");
		
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

	public static Timestamp getDataInicioFechamentoPonto() {
		Timestamp dtIniFech = null;
		
		try {
			dtIniFech = TimeUtils.clearTime((Timestamp) MGECoreParameter.getParameter("AD_DTINIFECH"));
		} catch (Exception e) {
			dtIniFech = TimeUtils.clearTime(TimeUtils.getNow());
		}
		
		return dtIniFech;
	}

	public static Timestamp getDataFinalFechamentoPonto() {
		Timestamp dtIniFech = null;
		
		try {
			dtIniFech = TimeUtils.clearTime((Timestamp) MGECoreParameter.getParameter("AD_DTFINFECH"));
		} catch (Exception e) {
			dtIniFech = TimeUtils.clearTime(TimeUtils.getNow());
		}
		
		return dtIniFech;
	}

	public static void persistDemissaoIcarus(final BigDecimal codEmp, final BigDecimal codFunc, final Timestamp dtDem)
			throws SkalaModelException {
		LOGGER.info("persistDemissaoIcarus");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					DemissaoIcarus demissao = new DemissaoIcarus();
					demissao.setCodEmp(codEmp);
					demissao.setCodFunc(codFunc);
					demissao.setDtDem(dtDem);
					demissao.persists();	
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível persistir o Registro de Ponto.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	public static BigDecimal getIdAfastamentoPonto(final BigDecimal nuOcor) throws RecordNotFoundException {
		BigDecimal idAfastamentoPonto = null;
		
		try {
			JapeWrapper dao = JapeFactory.dao(IcarusConstants.PLTPIOCO);
			
			DynamicVO vo = dao.findByPK(nuOcor);
			if (vo != null)
				idAfastamentoPonto = vo.asBigDecimal("IDAFASTAMENTOPONTO");
						
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
		
		return idAfastamentoPonto;
	}

	public static void setDataRetornoOcorrencia(final BigDecimal nuOcor) throws SkalaModelException {
		LOGGER.info("persistDemissaoIcarus");

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					RegistroOcorrenciaIcarus ocorrencia = new RegistroOcorrenciaIcarus();
					ocorrencia.loadByPk(nuOcor);
					ocorrencia.setDhAtuRetorno(TimeUtils.getNow());
					ocorrencia.persists();
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar Retorno de Afastamento.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
		
	}

	public static List<CentroCustoIdDTO> getCentrosCusto(BigDecimal codigo) throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		EmpresaIcarus empresaIcarus = null;
		CentroCustoIdDTO centroCustoIdDTO = null;
		List<CentroCustoIdDTO> centrosCusto = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.CODIGO = ? ");

			JapeWrapper dao = JapeFactory.dao(EmpresaIcarus.ENTITY_NAME);
			centrosCusto = new LinkedList<>();
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codigo);
			for (DynamicVO vo : vos) {
				empresaIcarus = new EmpresaIcarus();
				empresaIcarus.loadByVO(vo);
				
				centroCustoIdDTO = (CentroCustoIdDTO) IcarusMapper.empresaIcarus2CentroCustoIdDtoMapper(empresaIcarus);
				centrosCusto.add(centroCustoIdDTO);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Centros de Custo Icarus.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
		return centrosCusto;
	}

	public static List<Timestamp> getPeriodoPonto() throws RecordNotFoundException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;

		List<Timestamp> datasPonto = new LinkedList<>();
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(IcarusDAO.class, "IcarusDAO_quePeriodoPonto.sql");
			ResultSet rs = null;
			try {
				rs = sql.executeQuery();

				while (rs.next()) {
					datasPonto.add(TimeUtils.clearTime(rs.getTimestamp("DT")));
				}				
			} finally {
				JdbcUtils.closeResultSet(rs);
			}

		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return datasPonto;
	}

	public static void persistsFuncionarioIcarus(final BigDecimal codEmp, final BigDecimal codFunc,
			final BigDecimal idMutuario, final BigDecimal idPessoa) throws SkalaModelException {
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
			InsertNotPossibleException.throwMe("Não foi possível incluir Mutuário.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	public static void persistsFuncionarioIcarus(final FuncionarioIcarus bean) throws SkalaModelException {
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
	
	public static TurnoIcarus getTurnoIcarusByPk(final Object... keyValue) throws RecordNotFoundException {
		TurnoIcarus turnoIcarus = new TurnoIcarus();
		turnoIcarus.loadByPk(keyValue);
		
		return turnoIcarus;
	}

	public static void persistsDataAtualizacaoMutuario(final Funcionario funcionario) throws SkalaModelException {
		LOGGER.info("persistsDataAtualizacaoMutuario");
		
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					JapeWrapper dao = JapeFactory.dao(FuncionarioUpdateIcarus.ENTITY_NAME);
					DynamicVO vo = dao.findOne("this.CODEMP = ? AND this.CODFUNC = ? ", funcionario.getCodEmp(),
							funcionario.getCodFunc());
					
					FuncionarioUpdateIcarus funcionarioUpd = new FuncionarioUpdateIcarus();
					funcionarioUpd.loadByVO(vo);
					funcionarioUpd.setDhInteg(TimeUtils.getNow());
					funcionarioUpd.persists();
				}
			});
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Mutuário.", e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

}


	
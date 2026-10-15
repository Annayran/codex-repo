package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.beans.funcionario.Funcionario;
import tech.skala.core.commons.constants.SklFieldName;

public class IcarusFinder {
	private static final Logger LOGGER = Logger.getLogger("tech.skala.action.integration.pontoicarus.IcarusFinder");

	public static final String PLTPICENCUS = "AD_PLTPICENCUS";
	public static final String PLTPILOTACAO = "AD_PLTPILOTACAO";
	public static final String PLTPICARGO = "AD_PLTPICARGO";
	public static final String PLTPIREG = "AD_PLTPIREG";
	public static final String PLTPIFUN = "AD_PLTPIFUN";

	private IcarusFinder() {
		throw new IllegalStateException("Utility class");
	}

	public static List<ConfiguracaoIcarus> getConfiguracoes() {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		List<ConfiguracaoIcarus> configuracoes = null;
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			ConfiguracaoIcarus configuracao;
			configuracoes = new LinkedList<>();
			
			JapeWrapper dao = JapeFactory.dao(ConfiguracaoIcarus.ENTITY_NAME);
			Collection<DynamicVO> vos = dao.find("this.ATIVO = 'S'");
			for (DynamicVO vo:vos) {
				configuracao = new ConfiguracaoIcarus();
				configuracao.loadByVO(vo);
				
				configuracoes.add(configuracao);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return configuracoes;
	}
	
	public static AccountCredentialsDTO getCredentials() {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		AccountCredentialsDTO credentialsDTO = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			JapeWrapper dao = JapeFactory.dao("AD_PLTPICFG");
			DynamicVO vo = dao.findOne("this.ATIVO = 'S'");

			credentialsDTO = new AccountCredentialsDTO();
			credentialsDTO.loadByVO(vo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return credentialsDTO;
	}

	public static EmpresaIcarus findEmpresaIcarusByCodEmp(BigDecimal codEmp) {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		EmpresaIcarus entidadeIcarus = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			JapeWrapper dao = JapeFactory.dao(PLTPICENCUS);
			DynamicVO vo = dao.findOne("this.CODEMP = ?", codEmp);

			entidadeIcarus = new EmpresaIcarus();
			entidadeIcarus.loadByVO(vo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return entidadeIcarus;
	}

	public static DepartamentoIcarus findDepartamentoIcarusByCodDep(final BigDecimal codEmp, final BigDecimal codDep) {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		DepartamentoIcarus entidadeIcarus = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			JapeWrapper dao = JapeFactory.dao(PLTPILOTACAO);
			DynamicVO vo = dao.findByPK(codDep, codEmp);

			entidadeIcarus = new DepartamentoIcarus();
			entidadeIcarus.loadByVO(vo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return entidadeIcarus;
	}

	public static CargoIcarus findCargoIcarusByCodCar(final BigDecimal codEmp, final BigDecimal codCargo) {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;

		CargoIcarus entidadeIcarus = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			JapeWrapper dao = JapeFactory.dao(PLTPICARGO);
			DynamicVO vo = dao.findByPK(codCargo, codEmp);
			
			entidadeIcarus = new CargoIcarus();
			entidadeIcarus.loadByVO(vo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return entidadeIcarus;
	}

	public static List<TurnoDiaIcarus> getDiasFromCargaHoraria(BigDecimal codCargaHor, BigDecimal codEmp) {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		
		NativeSql sql = null;
		ResultSet rs = null;
		LinkedList<TurnoDiaIcarus> dias = null;
		BigDecimal diaSem;
		String descansoSem = null;
		TurnoDiaIcarus turnoDia = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(IcarusFinder.class, "IcarusFinder_queTurnoDiaCargaHoraria.sql");
			sql.setNamedParameter("CODCARGAHOR", codCargaHor);
			sql.setNamedParameter(SklFieldName.CODEMP, codEmp);

			rs = sql.executeQuery();

			String msg;
			dias = new LinkedList<>();
			while (rs.next()) {
				diaSem = rs.getBigDecimal("DIASEM");
				descansoSem = rs.getString("DESCANSOSEM");

				msg = String.format("codEmp %s diaSem %s descanso %s tipoTurnoDia %s", codEmp, diaSem, descansoSem,
						TipoTurnoDiaEnum.toString(diaSem));
				LOGGER.info(msg);

				turnoDia = new TurnoDiaIcarus(codEmp, diaSem, descansoSem, TipoTurnoDiaEnum.toString(diaSem));
				dias.add(turnoDia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return dias;
	}

	public static List<TurnoPeriodoIcarus> getPeriodosFromCargaHoraria(BigDecimal codCargaHor, BigDecimal diaSem, BigDecimal codEmp) {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;
		BigDecimal horaEntSai = null;
		String horaturno = null;
		String tipo = null;

		LinkedList<TurnoPeriodoIcarus> periodos = null;
		TurnoPeriodoIcarus turnoPeriodo = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(IcarusFinder.class, "IcarusFinder_queTurnoPeriodoCargaHoraria.sql");
			sql.setNamedParameter("CODCARGAHOR", codCargaHor);
			sql.setNamedParameter("DIASEM", diaSem);
			sql.setNamedParameter(SklFieldName.CODEMP, codEmp);

			rs = sql.executeQuery();

			String msg;
			periodos = new LinkedList<>();
			while (rs.next()) {
				horaEntSai = rs.getBigDecimal("HORAENTSAI");
				horaturno = rs.getString("HORATURNO");
				tipo = rs.getString("TIPO");

				msg = String.format("diaSem %s horaEntSai %s horaturno %s tipo %s", diaSem, horaEntSai, horaturno,
						tipo);
				LOGGER.info(msg);

				turnoPeriodo = new TurnoPeriodoIcarus(codEmp, horaEntSai, horaturno, tipo);
				periodos.add(turnoPeriodo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return periodos;
	}

	/**
	 * 
	 * @param codEmp
	 * @return
	 * 
	 * @deprecated 2023-07-18
	 */
	public static List<Funcionario> getFuncionariosDemitidos(BigDecimal codEmp) {
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
					"this.DTDEM IS NOT NULL ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPIFUN T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC) ");
			sbWhere.append(
					"AND EXISTS(SELECT 1 FROM AD_PLTPICFGEMP T WHERE T.CODEMP = ? AND T.CODEMPGRP = this.CODEMP) ");
			sbWhere.append(
					"AND this.CODFUNC = 65 ");

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
			Collection<DynamicVO> vos = dao.find(sbWhere.toString(), codEmp);
			for (DynamicVO vo:vos) {
				funcionario = new Funcionario();
				funcionario.loadByVO(vo);
				
				funcionariosDemitidos.add(funcionario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return funcionariosDemitidos;
	}

	public static BigDecimal getIdMutuario(BigDecimal codEmp, BigDecimal codFunc) throws Exception {
		BigDecimal idMutuario = null;
		
		JapeWrapper dao = JapeFactory.dao(PLTPIFUN);
		DynamicVO vo = dao.findOne("this.CODEMP = ? AND this.CODFUNC = ?", codEmp, codFunc);
		
		if (vo != null)
			idMutuario = vo.asBigDecimal("IDMUTUARIO");
		
		return idMutuario;
	}
	
	public static String getEmailPadrao(Funcionario funcionario) {
		List<Integer> empresasMaster = Arrays.asList(1, 104, 105, 106);
		
		String dominio = (empresasMaster.contains(funcionario.getCodEmp().intValue())) ? "masterline.ind.br" : "platinacsc.com.br";
		
		return String.format("%s@%s", funcionario.getCpf(), dominio);
	}
	
	public static String getTurnoEscalaMarshall(BigDecimal codCargaHor) {
		String turno = null;
		
		switch (codCargaHor.intValue()) {
		case 193:
			turno = "A";
			break;
		case 198:
			turno = "B";
			break;
		case 196:
			turno = "C";
			break;
		case 197:
			turno = "D";
			break;
		default:
			break;
		}
		
		return turno;
	}

}

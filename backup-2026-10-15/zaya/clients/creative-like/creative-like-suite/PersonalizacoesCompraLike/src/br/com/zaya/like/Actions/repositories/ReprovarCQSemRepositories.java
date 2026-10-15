package br.com.zaya.like.Actions.repositories;

import java.math.BigDecimal;
import java.sql.ResultSet;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class ReprovarCQSemRepositories {
	
	private static final int REPROVADO_SEM_REPOSCAO = 4;
	
	public static void updateTGLCDQ(BigDecimal nuApo, String observacao, BigDecimal codUsu) throws Exception {
		JdbcWrapper jdbc = null;
		NativeSql nativeUpdate = null;
		try {
		    jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			nativeUpdate = new NativeSql(jdbc);
			nativeUpdate.appendSql("UPDATE AD_TGLCDQ "
					+ " SET STATUSLIB = :STATUSLIB, "
					+ " OBSLIB = :OBSLIB, "
					+ " VALIDADOR = :VALIDADOR, "
					+ " USULIB = :USULIB "
					+ "WHERE NUAPO = :NUAPO ");
			nativeUpdate.setNamedParameter("STATUSLIB", REPROVADO_SEM_REPOSCAO);
			nativeUpdate.setNamedParameter("OBSLIB", observacao);
			nativeUpdate.setNamedParameter("VALIDADOR", "S");
			nativeUpdate.setNamedParameter("USULIB", codUsu);
			nativeUpdate.setNamedParameter("NUAPO", nuApo);
			nativeUpdate.executeUpdate();

		} catch (Exception e) {
			throw new Exception("Erro ao atualizar 'Controle de Qualidade': " + e.getMessage());
		} finally {
			NativeSql.releaseResources(nativeUpdate);
			JdbcWrapper.closeSession(jdbc);
		}
	}
	
	
	public static void updateTPRIPA(BigDecimal nuNegociacao, BigDecimal qtdReproada) throws Exception {
		JdbcWrapper jdbc = null;
		NativeSql nativeUpdate = null;
		try {
		    jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			nativeUpdate = new NativeSql(jdbc);
			nativeUpdate.appendSql("UPDATE TPRIPA "
					+ " SET QTDPRODUZIR = (QTDPRODUZIR + :QTDREP)"
					+ " WHERE IDIPROC IN (SELECT DISTINCT A.IDIPROC "
					+ "     FROM TPRAPO O "
					+ "     INNER JOIN TPRIATV A ON A.IDIATV = O.IDIATV "
					+ "     INNER JOIN TPRIPROC P ON P.IDIPROC = A.IDIPROC "
					+ "     WHERE P.NROLOTE = TO_CHAR(:NUNEG))");
			
			nativeUpdate.setNamedParameter("QTDREP", qtdReproada);
			nativeUpdate.setNamedParameter("NUNEG", nuNegociacao);
			nativeUpdate.executeUpdate();

		} catch (Exception e) {
			throw new Exception("Erro ao atualizar 'Apontamento': " + e.getMessage());
		} finally {
			NativeSql.releaseResources(nativeUpdate);
			JdbcWrapper.closeSession(jdbc);
		}
	}
	
	public static void updateTPRAPA(BigDecimal nuApo, BigDecimal qtdReproada) throws Exception {
		JdbcWrapper jdbc = null;
		NativeSql updateTPRAPA = null;
		NativeSql updateTPRAPO = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			updateTPRAPA = new NativeSql(jdbc);
			updateTPRAPA.appendSql("UPDATE TPRAPA " 
			        + " SET QTDPERDA = :QTDREP," 
					+ "     CODMPE = 4,"
					+ "     QTDMPE = :QTDREP, " 
					+ "     QTDAPONTADA = 0 " 
					+ " WHERE NUAPO = :NUAPO ");

			updateTPRAPA.setNamedParameter("QTDREP", qtdReproada);
			updateTPRAPA.setNamedParameter("NUAPO", nuApo);
			updateTPRAPA.executeUpdate();
			

			updateTPRAPO = new NativeSql(jdbc);
			updateTPRAPO.appendSql("UPDATE TPRAPO " 
			        + " SET SITUACAO = 'C'"
					+ " WHERE NUAPO = :NUAPO ");

			updateTPRAPO.setNamedParameter("QTDREP", qtdReproada);
			updateTPRAPO.setNamedParameter("NUAPO", nuApo);
			updateTPRAPO.executeUpdate();

		} catch (Exception e) {
			throw new Exception("Erro ao atualizar 'Apontamento': " + e.getMessage());
		} finally {
			NativeSql.releaseResources(updateTPRAPA);
			NativeSql.releaseResources(updateTPRAPO);
			JdbcWrapper.closeSession(jdbc);
		}
	}
	
	public static void updateAD_LIBERACAOQUALIDADE(Registro linha, BigDecimal nuNotaTransf,BigDecimal codUsuLogado,  BigDecimal nuNotaDev) throws Exception {
		JdbcWrapper jdbc = null;
		NativeSql updateQUALIDADE = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			updateQUALIDADE = new NativeSql(jdbc);
			updateQUALIDADE.appendSql("UPDATE AD_LIBERACAOQUALIDADE " 
			        + " SET NUNOTA = :NUNOTA, " 
					+ "     STATUS = 'RR', "
					+ "     NUNOTATRANSF = :NUNOTATRANSF , " 
					+ "     DHAPROVACAO = SYSDATE " 
					+ " WHERE ID = :ID " 
					+ " AND SEQ = :SEQ "
					+ " AND CODLIB = :CODLIB ");

			updateQUALIDADE.setNamedParameter("NUNOTA", nuNotaDev);
			updateQUALIDADE.setNamedParameter("NUNOTATRANSF", nuNotaTransf);
			updateQUALIDADE.setNamedParameter("ID", linha.getCampo("ID"));
			updateQUALIDADE.setNamedParameter("SEQ", linha.getCampo("SEQ"));
			updateQUALIDADE.setNamedParameter("CODLIB", linha.getCampo("CODLIB"));
			updateQUALIDADE.executeUpdate();

		} catch (Exception e) {
			throw new Exception("Erro ao atualizar 'Qualidade': " + e.getMessage());
		} finally {
			NativeSql.releaseResources(updateQUALIDADE);
			JdbcWrapper.closeSession(jdbc);
		}
	}
	
	private static BigDecimal buscarIdiProc(BigDecimal nuNegociacao) throws Exception {
		BigDecimal result = NativeSql.getBigDecimal("DISTINCT A.IDIPROC","TPRAPO O "
				+ " JOIN TPRIATV A ON A.IDIATV = O.IDIATV "
				+ " JOIN TPRIPROC P ON P.IDIPROC = A.IDIPROC"
				, "P.NROLOTE = TO_CHAR(?)", new Object[] {nuNegociacao});
		
		return BigDecimalUtil.getValueOrZero(result);
		
	}

	public static void buscarDadosParceiro(BigDecimal codParc, BigDecimal codTipVenda, BigDecimal codParcTranspPreferencial, String cifFob) {
		NativeSql dadosParcSql = null;
		ResultSet rs = null;
		JdbcWrapper jdbc = null;
		
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			dadosParcSql = new NativeSql(jdbc);
			
			dadosParcSql.appendSql("SELECT PAR.CODPARC, ");
			dadosParcSql.appendSql("  PAR.AD_CIF_FOB, ");
			dadosParcSql.appendSql("  CPL.SUGTIPNEGENTR, ");
			dadosParcSql.appendSql("  CPL.CODPARCTRANSP ");
			dadosParcSql.appendSql(" FROM TGFPAR PAR ");
			dadosParcSql.appendSql(" INNER JOIN TGFCPL CPL ON PAR.CODPARC = CPL.CODPARC ");
			dadosParcSql.appendSql(" WHERE PAR.CODPARC = :CODPARC ");
			
			dadosParcSql.setNamedParameter("CODPARC", codParc);
			rs = dadosParcSql.executeQuery();
			if (rs.next()) {
				codTipVenda = rs.getBigDecimal("SUGTIPNEGENTR");
				codParcTranspPreferencial = rs.getBigDecimal("CODPARCTRANSP");
				cifFob = rs.getString("AD_CIF_FOB");
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar dados do parceiro: " + e.getMessage());
		} finally {
			
			JdbcWrapper.closeSession(jdbc);
			NativeSql.releaseResources(dadosParcSql);
			JdbcUtils.closeResultSet(rs);
		}
	}

	public static BigDecimal buscarSequenciaNota(Registro linha, BigDecimal nuNotaCompra) throws Exception {
		BigDecimal sequencia = NativeSql.getBigDecimal("SEQUENCIA", "AD_VZYNFCOMPRACQ", "NUNOTA = ? AND ID = ? AND SEQ = ?", new Object[] {nuNotaCompra,linha.getCampo("ID"), linha.getCampo("SEQ")});
		return sequencia;
	}

	public static BigDecimal buscarQtdNegocCompra(BigDecimal nuNotaCompra, BigDecimal sequencia) throws Exception {
		BigDecimal qtdnegociada = NativeSql.getBigDecimal("QTDNEG - QTDENTREGUE", "TGFITE", "NUNOTA = ? AND SEQUENCIA = ? ", new Object[] {nuNotaCompra,sequencia});
		return qtdnegociada;
	}
	
	public static BigDecimal buscarTOPDestino(BigDecimal nuNota) throws Exception {
		BigDecimal top = BigDecimal.ZERO;

		NativeSql sql = null;
		ResultSet rs = null;
		
		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			sql = new NativeSql(jdbc);

			sql.appendSql(" SELECT CODTIPOPERDESTINO ");
			sql.appendSql("   FROM TGFCAB CAB ");
			sql.appendSql(" INNER JOIN TGFTOP TPO ON CAB.CODTIPOPER = TPO.CODTIPOPER AND CAB.DHTIPOPER = TPO.DHALTER ");
			sql.appendSql(" WHERE DHALTER = (SELECT MAX(T.DHALTER) FROM TGFTOP T WHERE T.CODTIPOPER = TPO.CODTIPOPER) ");
			sql.appendSql(" AND NUNOTA = :NUNOTA ");

			sql.setNamedParameter("NUNOTA", nuNota);

			rs = sql.executeQuery();
			if (rs.next()) {
				top = rs.getBigDecimal("CODTIPOPERDESTINO");
			}
		} finally {
			NativeSql.releaseResources(sql);
			JdbcUtils.closeResultSet(rs);
			JdbcWrapper.closeSession(jdbc);
		}
		return top;
	}
}

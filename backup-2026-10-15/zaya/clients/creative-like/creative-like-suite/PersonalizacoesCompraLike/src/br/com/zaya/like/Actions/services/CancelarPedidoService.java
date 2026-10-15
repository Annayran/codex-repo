package br.com.zaya.like.Actions.services;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.like.Repositories.ConsultasUtil;
import br.com.zaya.like.helper.UtilitariosGenericos;

public class CancelarPedidoService {
	
	private static String motivoNota = null;

	public static void cancelarItemPedido(BigDecimal nuNota, BigDecimal codProd, String motivoCancelamento) throws Exception {

		JapeSession.putProperty("br.com.sankhya.mgecom.centralnotas.NotaSendoCancelada", "S");

		try {
			inserirObervacao("Pedido possui itens cancelados! Motivo: " + motivoCancelamento , nuNota);
			confirmaNota(nuNota, codProd);
			marcarItemPedidoComoNaoPendentePorProduto(nuNota, codProd);
		} catch (MGEModelException e) {
				MGEModelException.throwMe(e);
		} finally {
			JapeSession.putProperty("br.com.sankhya.mgecom.centralnotas.NotaSendoCancelada", "N");
		} 

	}

	private static void inserirObervacao(String motivo, BigDecimal nuNota ) throws Exception {
		String obsNota = NativeSql.getString("OBSERVACAO", "TGFCAB", "NUNOTA = ? ", new Object[] {nuNota}) ;
		
		motivoNota = motivo; 
		
		if(motivo.equals(obsNota)) {
			return;
		}
		
		BigDecimal numNota = NativeSql.getBigDecimal("NUMNOTA", "TGFCAB", "NUNOTA = ? ", new Object[] {nuNota}) ;
		
		if(numNota.compareTo(BigDecimal.ZERO) == 0 ) {
			numNota = nuNota;
		}
			
		JapeFactory.dao("CabecalhoNota")
		.prepareToUpdateByPK(new Object[] { nuNota })
		.set("OBSERVACAO", motivo)
		.set("NUMNOTA", numNota)
		.update();
	}

	public static void cancelarArtePorSequencia(BigDecimal nuNegociacao, BigDecimal seguenciaNegociacao) throws MGEModelException {
		try {
			
			BigDecimal idArte = NativeSql.getBigDecimal("MAX(IDARTE)", "AD_TGLITEART", "NUNEGOCIACAO = ? AND SEQUENCIA = ?", new Object[] {nuNegociacao,seguenciaNegociacao});

			JapeFactory.dao("AD_TGLITEART")
			.prepareToUpdateByPK(new Object[] { nuNegociacao ,seguenciaNegociacao, idArte})
			.set("STATUS", "CP")
			.update();

		} catch (Exception e) {
			MGEModelException.throwMe(e);
		} finally {
		//	JapeSession.close(hnd);
		}

	}
	
	public static void cancelarArtePorProduto(BigDecimal nuNegociacao, BigDecimal codProd) throws MGEModelException {
		NativeSql sqlSequencias = null;
		ResultSet rs = null;
		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			
			sqlSequencias = new NativeSql(jdbc);
			sqlSequencias.appendSql(" SELECT MAX(IDARTE) AS IDARTE, SEQUENCIA, CODPROD, NUNEGOCIACAO  ");
			sqlSequencias.appendSql(" FROM AD_TGLITEART ");
			sqlSequencias.appendSql(" WHERE NUNEGOCIACAO = :NUNEGOCIACAO AND CODPROD = :CODPROD ");
			sqlSequencias.appendSql(" GROUP BY  SEQUENCIA, CODPROD, NUNEGOCIACAO ");
			
			sqlSequencias.setNamedParameter("NUNEGOCIACAO", nuNegociacao);
			sqlSequencias.setNamedParameter("CODPROD", codProd);
			
			rs = sqlSequencias.executeQuery();
			
			while (rs.next()) {
				JapeFactory.dao("AD_TGLITEART")
				.prepareToUpdateByPK(new Object[] { nuNegociacao ,rs.getBigDecimal("SEQUENCIA"), rs.getBigDecimal("IDARTE")})
				.set("STATUS", "CP")
				.update();
			}


		} catch (Exception e) {
			MGEModelException.throwMe(e);
		} finally {
			NativeSql.releaseResources(sqlSequencias);
			JdbcWrapper.closeSession(jdbc);
		}

	}
	
	public static void marcarItemPedidoComoNaoPendentePorSequencia(BigDecimal nuNota, BigDecimal sequenciaPNE) throws MGEModelException {
		try {
			BigDecimal sequencia = NativeSql.getBigDecimal("SEQUENCIA", "TGFITE", "NUNOTA = ? AND AD_SEQUENCIAPNE = ?", new Object[] {nuNota, sequenciaPNE}) ;
			

			JapeFactory.dao("ItemNota")
			.prepareToUpdateByPK(new Object[] { nuNota, sequencia })
			.set("OBSERVACAO","Pedido possui itens cancelados! Motivo: " + motivoNota)
			.set("PENDENTE", "N")
			.update();
			

		} catch (Exception e) {
			MGEModelException.throwMe(e);
		} finally {
		//	JapeSession.close(hnd);
		}

	}
	
	public static void marcarItemPedidoComoNaoPendentePorProduto(BigDecimal nuNota, BigDecimal codProd) throws MGEModelException {
		NativeSql sqlSequencias = null;
		ResultSet rs = null;
		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			
			sqlSequencias = new NativeSql(jdbc);
			sqlSequencias.appendSql(" SELECT SEQUENCIA ");
			sqlSequencias.appendSql(" FROM TGFITE ");
			sqlSequencias.appendSql(" WHERE NUNOTA = :NUNOTA AND CODPROD = :CODPROD ");
			
			sqlSequencias.setNamedParameter("NUNOTA", nuNota);
			sqlSequencias.setNamedParameter("CODPROD", codProd);
			
			rs = sqlSequencias.executeQuery();
			
			while (rs.next()) {
				marcarItemPedidoVendaNaoPendente(nuNota, rs.getBigDecimal("SEQUENCIA"));
			}
			

		} catch (Exception e) {
			MGEModelException.throwMe(e);
		} finally {
			NativeSql.releaseResources(sqlSequencias);
			JdbcWrapper.closeSession(jdbc);
		}

	}
	
	public static void marcarItemPedidoComoNaoPendentePorProduto(BigDecimal nuNota, BigDecimal codProd, BigDecimal sequencia ) throws SQLException, Exception {
		marcarItemPedidoVendaNaoPendente(nuNota, sequencia);
			
	}

	private static void marcarItemPedidoVendaNaoPendente(BigDecimal nuNota, BigDecimal sequencia) throws Exception, SQLException {
		JapeFactory.dao("ItemNota")
		.prepareToUpdateByPK(new Object[] { nuNota, sequencia })
		.set("OBSERVACAO", "Pedido possui itens cancelados! Motivo: " + motivoNota)
		.set("PENDENTE", "N")
		.update();
		dadosAcompanhamentoNota(nuNota, motivoNota, sequencia);
	}
	
    private static void confirmaNota(BigDecimal nuNota, BigDecimal codProd ) throws Exception {
        JdbcWrapper jdbcWrapper = null;

        String statusNota = ConsultasUtil.buscarStatusNota(nuNota);
        if("L".equals(statusNota)) {
        	return;
        }
        
        if(ConsultasUtil.existeItemPendente(nuNota, codProd)) {
        	return;
        }
        try {
            jdbcWrapper = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbcWrapper.openSession();

            Connection        conn = jdbcWrapper.getConnection();

            CallableStatement cstmt = conn.prepareCall("{call STP_CONFIRMANOTA2(?,?,?)}");

            cstmt.setBigDecimal(1, nuNota);
            cstmt.setString(2, "S");
            cstmt.setBigDecimal(3, BigDecimal.valueOf(-1));
            cstmt.execute();
            

        } finally {
            if(jdbcWrapper != null) {
                jdbcWrapper.closeSession();
            }
        }
    }
    
	public static void dadosAcompanhamentoNota(BigDecimal nuNota, String ocorrencia, BigDecimal sequencia) throws Exception {
		
		BigDecimal codusu = (BigDecimal) JapeSessionContext.getRequiredProperty("usuario_logado");
		IncluirAcompanhamentoService.incluir(nuNota, sequencia, ocorrencia, codusu);
		
	}

	public static List<BigDecimal> buscarUsuariosParaNotificacao(BigDecimal nuNegociacao, BigDecimal nuNota) throws MGEModelException {
		List<BigDecimal> usuarios = new ArrayList<>();
		
		NativeSql sqlBuscarUsuario = null;
		ResultSet rs = null;
		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			
			sqlBuscarUsuario = new NativeSql(jdbc);
			
			sqlBuscarUsuario.appendSql(" SELECT   ");
			sqlBuscarUsuario.appendSql("     CODUSU  ");
			sqlBuscarUsuario.appendSql(" FROM   ");
			sqlBuscarUsuario.appendSql("     (SELECT USU.CODUSU  ");
			sqlBuscarUsuario.appendSql("     FROM TGFCAB CAB  ");
			sqlBuscarUsuario.appendSql("      INNER JOIN TSIUSU USU ON CAB.CODVEND = USU.CODVEND  ");
			sqlBuscarUsuario.appendSql("     WHERE NUNOTA = :NUNOTA and CAB.CODVEND <> 0 ");
			    
			sqlBuscarUsuario.appendSql("     UNION  ");
			    
			sqlBuscarUsuario.appendSql("     SELECT USU.CODUSU  ");
			sqlBuscarUsuario.appendSql("     FROM AD_TGLCAB CAB  ");
			sqlBuscarUsuario.appendSql("      INNER JOIN TSIUSU USU ON CAB.CODVEND = USU.CODVEND  ");
			sqlBuscarUsuario.appendSql("     WHERE NUNEGOCIACAO = :NUNEGOCIACAO and  CAB.CODVEND <> 0)  ");
			
			sqlBuscarUsuario.setNamedParameter("NUNOTA", nuNota);
			sqlBuscarUsuario.setNamedParameter("NUNEGOCIACAO", nuNegociacao);
			
			rs = sqlBuscarUsuario.executeQuery();
			
			while (rs.next()) {
				usuarios.add(rs.getBigDecimal("CODUSU"));
			}
						
			return usuarios;
			

		} catch (Exception e) {
			MGEModelException.throwMe(e);
		} finally {
			NativeSql.releaseResources(sqlBuscarUsuario);
			JdbcWrapper.closeSession(jdbc);
		}

 return usuarios;	
		
	}

	public static void incluirNotificacaoCancelamento(Collection<BigDecimal> usuarios, String motivoCancelamento) {
		
		 for(BigDecimal codUsu : usuarios) {
			 try {
				UtilitariosGenericos.incluirNotificacao("Pedido Cancelado", motivoCancelamento, codUsu , null, BigDecimal.ZERO);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		 
		
	}
}

package br.com.zaya.like.helper;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class UtilitariosGenericos {
	

	public static void incluirNotificacao(String titulo, String descricao, BigDecimal codUsu, BigDecimal codGrupoUsu, BigDecimal codRemetente) throws SQLException {
		
		JdbcWrapper jdbcWrapper = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
        jdbcWrapper.openSession();

        Connection conn = jdbcWrapper.getConnection();

        CallableStatement cstmt;
		try {
			cstmt = conn.prepareCall("{call STP_NOTIFICA_SISTEMA(?,?,?,?,?)}");
			
	        cstmt.setString(1, titulo);
	        cstmt.setString(2, descricao);
	        cstmt.setBigDecimal(3, codUsu);
	        cstmt.setBigDecimal(4, codGrupoUsu);
	        cstmt.setBigDecimal(5, codRemetente);
	        cstmt.execute();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}


		
		
	}

}

package tech.skala.events;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AD_PLTGFRRTOCOEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {

	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		// // cria vo
		DynamicVO vo = (DynamicVO) event.getVo();
		
		// campo QTDPROGRAMADO
		BigDecimal qtdProgramado = vo.asBigDecimalOrZero("QTDPROGRAMADO");
		
		

		// if se o campo qtdProgramado ta sendo modificado, se tiver = throw e para
		if (qtdProgramado.longValue() != 0) {
			validaUsuarioCorrente();
		}

		preencheCampos(vo);

	}

	private void preencheCampos(DynamicVO vo) throws SkalaModelException {
		// instanciar campos/parâmetros
		BigDecimal codProd = vo.asBigDecimal(SklFieldName.CODPROD);
		Timestamp dtProg = vo.asTimestamp("DTPROG");

		// instancia conexão jdbc, hnd, rs, sql
		JdbcWrapper jdbc = null;
		SessionHandle hnd = null;
		ResultSet rs = null;
		NativeSql sql = null;

		try {
			// abre conexão jdbc, hnd
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(getClass(), "sql/queGetContagemGetProduto.sql");

			sql.setNamedParameter("P_DTPROG", dtProg);
			sql.setNamedParameter("P_CODPROD", codProd);

			// executa query
			rs = sql.executeQuery();

			// instancia campo que vou receber da query, no caso (QTDContagem)
			BigDecimal qtdContagem = null;
			Timestamp dtContagem = null;

			while (rs.next()) {
				qtdContagem = rs.getBigDecimal("QTDCONTAGEM");
				dtContagem = rs.getTimestamp("DTCONTAGEM");

				vo.setProperty("QTDESTOQUE", qtdContagem);
				vo.setProperty("DTCONTAGEM", dtContagem);
			}

		} catch (Exception e) {
			String msg = String.format(
					"Não foi possível inserir data da contagem ou a qtdestoque. CodProd: %s e DtProg: %S", codProd,
					dtProg);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	/**
	 * @param codUsu
	 * @throws Exception
	 * @throws SkalaModelException
	 */
	private void validaUsuarioCorrente() throws SkalaModelException {
		DynamicVO gruposPorUsuarioVO = null;

		try {
			BigDecimal codUsu = AuthenticationInfo.getCurrent().getUserID();
			JapeWrapper gruposPorUsuarioDAO = JapeFactory.dao("GruposPorUsuario");

			gruposPorUsuarioVO = gruposPorUsuarioDAO.findOne("this.CODUSU = ? AND this.CODGRUPO = 68", codUsu);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível validar usuário", e);
		}

		// verifica se usuario esta na tabela de grupo
		if (gruposPorUsuarioVO == null) {
			throw new SkalaModelException("Campo Programado só pode ser alterado por usuários do grupo PCP.");
		}
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		// cria vo
		DynamicVO vo = (DynamicVO) event.getVo();

		// lista de campos que estao sendo modificados
		ModifingFields modifingFields = event.getModifingFields();

		// verificar se dos campos modificados, o campo qtdprogramado está sendo
		// modificado, se sim = true
		if (modifingFields.isModifing("QTDPROGRAMADO")) {
			// pegar cod usu corrente

			// if se o campo qtdProgramado ta sendo modificado, se tiver = throw e para
			validaUsuarioCorrente();
		}

	}

}

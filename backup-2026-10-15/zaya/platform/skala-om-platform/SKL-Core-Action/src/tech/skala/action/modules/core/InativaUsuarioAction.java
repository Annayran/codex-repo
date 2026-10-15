package tech.skala.action.modules.core;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class InativaUsuarioAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			BigDecimal codEmp;
			BigDecimal codFunc; 
			
			for (Registro linha : linhas) {
				codEmp = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODEMP));
				codFunc = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODFUNC));
				
				JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
				
				DynamicVO vo = dao.findByPK(codEmp, codFunc);
				
				inativaUsuario(vo);
				
			}
			

			/* adiciona uma mensagem na mensagem de retorno ao usuario */
			msg = "";
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}
	
	private void inativaUsuario(DynamicVO vo) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					inativarUsuario(vo);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar usuário", e);
		} finally {
			JapeSession.close(hnd);
		}
	}
	
	
	private void inativarUsuario(DynamicVO vo) throws SkalaModelException {

		try {
			JapeWrapper usuXFunDAO = JapeFactory.dao("AD_PLTSIUSUXFUN");
			Collection<DynamicVO> usuXFunVOs = usuXFunDAO.find("this.CODEMP = ? AND this.CODFUNC = ?",
					vo.asBigDecimal(SklFieldName.CODEMP), vo.asBigDecimal(SklFieldName.CODFUNC));

			JapeWrapper usuarioDAO = JapeFactory.dao("Usuario");
			Timestamp dtDem = vo.asTimestamp("DTDEM");

			for (DynamicVO usuXFunVO : usuXFunVOs) {
				BigDecimal codUsu = usuXFunVO.asBigDecimal(SklFieldName.CODUSU);

				DynamicVO usuarioVO = usuarioDAO.findByPK(codUsu);
				Timestamp dtLimAcesso = usuarioVO.asTimestamp("DTLIMACESSO");

				if (dtLimAcesso == null) {
					String nomeUsu = usuarioVO.asString("NOMEUSU");
					EntityUpdate update = new EntityUpdate("Usuario", usuarioVO);
					update.setField("NOMEUSU", "Z" + nomeUsu);
					update.setField("DTLIMACESSO", TimeUtils.dataAdd(dtDem, -1, Calendar.DAY_OF_MONTH));
					update.fluidUpdate();
				}
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível inativar o usuário.", e);

		}

	}


}
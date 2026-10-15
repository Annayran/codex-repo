package tech.skala.action.modules.core;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ModelHelper {

	public void inativaAcoesAgendadas(final BigDecimal codModulo) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {

					JapeWrapper acaoAgendadaDAO = JapeFactory.dao(DynamicEntityNames.ACAO_AGENDADA);
					Collection<DynamicVO> acoes = acaoAgendadaDAO.find("this.CODMODULO = ?", codModulo);

					ModelProcessor processor = new ModelProcessor();
					processor.inativaAcoesAgendadas(acoes);
				}

			});

		} catch (Exception e) {
			String msg = String.format("Não foi possível desativar as ações que usam o Módulo %s", codModulo);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public void incluiUsuarioNoGrupo(BigDecimal codGrupo, BigDecimal codUsu) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					ModelProcessor processor = new ModelProcessor();
					processor.incluiUsuarioNoGrupo(codGrupo, codUsu);
				}

			});

		} catch (Exception e) {
			String msg = String.format("Não foi possível incluir o Usuário %s no Grupo %s", codUsu, codGrupo);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public void excluiUsuarioDoGrupo(BigDecimal codGrupo, BigDecimal codUsu) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					ModelProcessor processor = new ModelProcessor();
					processor.excluirUsuarioDoGrupo(codGrupo, codUsu);

				}

			});

		} catch (Exception e) {
			String msg = String.format("Não foi possível excluir o Usuário %s do Grupo %s", codUsu, codGrupo);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	public void copiaGrupoDoUsuario(BigDecimal codUsuOrig, BigDecimal codUsuDest) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					ModelProcessor processor = new ModelProcessor();
					processor.copiarGrupoDoUsuario(codUsuOrig, codUsuDest);

				}

			});

		} catch (Exception e) {
			String msg = String.format("Não foi possível copiar grupos do usuário %s para o usuário %s", codUsuOrig,
					codUsuDest);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	public void copiaUsuariosDoGrupo(BigDecimal grupoOrig, BigDecimal grupoDest) throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					ModelProcessor processor = new ModelProcessor();
					processor.copiarUsuariosDoGrupo(grupoOrig, grupoDest);
				}

			});

		} catch (Exception e) {
			String msg = String.format("Não foi possível copiar usuarios do grupo % para o grupo %s", grupoOrig,
					grupoDest);

			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}

}

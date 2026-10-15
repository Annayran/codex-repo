package tech.skala.action.modules.core;

import java.math.BigDecimal;
import java.util.Collection;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ModelProcessor {

	private static final String INATIVO = "N";

	public void inativaAcoesAgendadas(Collection<DynamicVO> acoes) throws SkalaModelException {

		if (acoes.isEmpty()) {
			String msg = "Não existem Ações Agendadas que usam este Módulo";
			throw new IllegalArgumentException(msg);
		}

		for (DynamicVO acao : acoes)
			inativarAcoesAgendadas(acao);

	}

	private void inativarAcoesAgendadas(DynamicVO vo) throws SkalaModelException {

		try {
			EntityUpdate update = new EntityUpdate(DynamicEntityNames.ACAO_AGENDADA, vo);
			update.setField("ATIVO", INATIVO);

			update.fluidUpdate();
		} catch (Exception e) {
			String msg = String.format("Não foi possível desativar a Ação Agendada %s-%s", vo.asBigDecimal("NUAAG"),
					vo.asString("DESCRICAO"));
			SkalaModelException.throwMe(msg, e);
		}
	}

	public void incluiUsuarioNoGrupo(BigDecimal codGrupo, BigDecimal codUsu) throws SkalaModelException {

		try {
			JapeWrapper grupoUsuarioDAO = JapeFactory.dao(DynamicEntityNames.GRUPO_USUARIO);
			DynamicVO vo = grupoUsuarioDAO.findOne("this.CODGRUPO = ?", codGrupo);

			if (vo == null)
				return;

		} catch (Exception e) {
			String msg = String.format("Não foi possível encontrar o Usuário %s.", codUsu);
			SkalaModelException.throwMe(msg, e);

		}

		incluirUsuarioNoGrupo(codGrupo, codUsu);

	}

	private void incluirUsuarioNoGrupo(BigDecimal codGrupo, BigDecimal codUsu) throws SkalaModelException {

		String msg;

		try {
			JapeWrapper dao = JapeFactory.dao("GruposPorUsuario");
			Collection<DynamicVO> vo = dao.find("this.CODGRUPO = ? AND this.CODUSU = ? AND this.DATAFIM Is NULL",
					codGrupo, codUsu);

			if (!vo.isEmpty()) {
				msg = "O Usuário já está ativo neste grupo.";
				throw new SkalaModelException(msg);
			}

			EntityInsert insert = new EntityInsert("GruposPorUsuario");
			insert.setField("CODGRUPO", codGrupo);
			insert.setField(SklFieldName.CODUSU, codUsu);
			insert.setField("DATAINICIO", TimeUtils.clearTime(TimeUtils.getNow()));
			insert.setField("REPASSAR", "N");

			insert.fluidInsert();

		} catch (Exception e) {
			msg = String.format("Não foi possível incluir o Usuário %s no grupo %s.", codUsu, codGrupo);
			SkalaModelException.throwMe(msg, e);

		}
	}

	public void excluirUsuarioDoGrupo(BigDecimal codGrupo, BigDecimal codUsu) throws SkalaModelException {

		try {
			JapeWrapper dao = JapeFactory.dao("GruposPorUsuario");
			DynamicVO vo = dao.findOne("this.CODGRUPO = ? AND this.CODSU = ? AND this.DATAFIM IS NULL", codGrupo,
					codUsu);

			if (vo == null)
				return;

			EntityUpdate update = new EntityUpdate("GruposPorUsuario", vo);
			update.setField("DATAFIM", TimeUtils.clearTime(TimeUtils.getNow()));

			update.fluidUpdate();

		} catch (Exception e) {
			String msg = String.format("Não foi possível excluir o Usuário %s do grupo %s.", codUsu, codGrupo);
			SkalaModelException.throwMe(msg, e);

		}
	}

	public void copiarGrupoDoUsuario(BigDecimal codUsuOrig, BigDecimal codUsuDest) throws SkalaModelException {

		// buscar grupos que o usuorig está
		// para cada grupo que vc encontrou incluir o usudest
		// chamar metodo incluir usuario no grupo

		try {
			JapeWrapper dao = JapeFactory.dao("GruposPorUsuario");
			Collection<DynamicVO> gruposVO = dao.find("this.CODUSU = ? AND this.DATAFIM IS NULL "
					+ "AND NOT EXISTS (SELECT 1 FROM TSIGPU T WHERE T.CODUSU = ? AND T.DATAFIM IS NULL AND T.CODGRUPO = this.CODGRUPO)",
					codUsuOrig, codUsuDest);
			BigDecimal codGrupo;

			for (DynamicVO grupoVO : gruposVO) {
				codGrupo = grupoVO.asBigDecimal("CODGRUPO");

				incluirUsuarioNoGrupo(codGrupo, codUsuDest);
			}

		} catch (Exception e) {
			String msg = String.format("Não foi possível incluir o Usuário %s nos grupos do usuário de origem",
					codUsuDest);
			SkalaModelException.throwMe(msg, e);
		}

	}

	public void copiarUsuariosDoGrupo(BigDecimal grupoOrig, BigDecimal grupoDest) throws SkalaModelException {
		
		
		//Copiando usuarios do grupo adicional
		try {
			JapeWrapper dao = JapeFactory.dao("GruposPorUsuario");
			Collection<DynamicVO> grupoUsuarioVOs = dao.find("this.CODGRUPO = ? AND this.DATAFIM IS NULL "
					+ "AND NOT EXISTS(SELECT 1 FROM TSIGPU T WHERE T.CODGRUPO = ? AND T.DATAFIM IS NULL AND T.CODUSU = this.CODUSU)",
					grupoOrig, grupoDest);

			// Remover AND T.CODGRUPO = this.CODGRUPO
			// AND NOT EXISTS(SELECT 1 FROM TSIGPU T WHERE T.CODGRUPO = :CODGRUPODEST AND
			// T.DATAFIM IS NULL AND T.CODUSU = this.CODUSU)

			BigDecimal codUsuario;

			for (DynamicVO grupoUsuarioVO : grupoUsuarioVOs) {
				codUsuario = grupoUsuarioVO.asBigDecimal(SklFieldName.CODUSU);

				incluirUsuarioNoGrupo(grupoDest, codUsuario);
			}

		} catch (Exception e) {
			String msg = String.format("Não foi possível incluir os usuários do grupo %s no grupo de destino",
					grupoOrig);
			SkalaModelException.throwMe(msg, e);
		}
		
//Copiando Usuario do grupo principal
		
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.USUARIO);
			Collection<DynamicVO> usuariosVO = dao.find("this.CODGRUPO = ? AND this.DTLIMACESSO IS NULL "
					+ "AND NOT EXISTS(SELECT 1 FROM TSIGPU T WHERE T.CODGRUPO = ? AND T.DATAFIM IS NULL AND T.CODUSU = this.CODUSU)",
					grupoOrig, grupoDest);

//			SELECT this.CODUSU FROM TSIUSU this
//			WHERE
//			this.CODGRUPO = :CODGRUPOORIG 
//			AND NOT EXISTS(SELECT 1 FROM TSIGPU T WHERE T.CODGRUPO = :CODGRUPODEST AND T.DATAFIM IS NULL AND T.CODUSU = this.CODUSU)
			// Entidade Usuario

			BigDecimal codUsuario;

			for (DynamicVO usuarioVO : usuariosVO) {
				codUsuario = usuarioVO.asBigDecimal("CODUSU");

				incluirUsuarioNoGrupo(grupoDest, codUsuario);
			}

		} catch (Exception e) {
			String msg = String.format("Não foi possível incluir os usuários do grupo %s no grupo de destino",
					grupoOrig);
			SkalaModelException.throwMe(msg, e);
		}
	}

}

package tech.skala.action.modules.manufatura.pcp.programacaosimplificada;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Collection;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.ProgramacaoProducaoSimplificadaExceptions.OrdensProducaoNaoIncluidasNaProgramacaoException;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.ProgramacaoProducaoSimplificadaExceptions.ProgramacaoNaoLocalizadaException;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzirFinder;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProgramacaoSimplificadaProcessor {

	private ProgramacaoProducaoSimplificada programacao;

	public ProgramacaoSimplificadaProcessor(ProgramacaoProducaoSimplificada programacao) {
		this.programacao = programacao;
	}

	public void atualizarStatusProgramacao() throws ProgramacaoNaoLocalizadaException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ProgramacaoProducaoSimplificada.ENTITY_NAME);

		DynamicVO vo;
		try {
			vo = dao.findByPK(programacao.getNuProgP());
		} catch (Exception e) {
			throw new ProgramacaoProducaoSimplificadaExceptions.ProgramacaoNaoLocalizadaException(e,
					programacao.getNuProgP().longValue());
		}

		EntityUpdate entityUpd = new EntityUpdate(ProgramacaoProducaoSimplificada.ENTITY_NAME, vo);
		entityUpd.setField("STATUS", ProgramacaoProducaoSimplificadaStatusEnum.PROGRAMADA.getValue());
		entityUpd.fluidUpdate();
	}

	public void incluirOrdensDeProducao(BigDecimal nuPrgp) throws OrdensProducaoNaoIncluidasNaProgramacaoException {
		try {
			Collection<DynamicVO> produtosAcabadosVO;
			ProgramacaoProducaoSimplificadaOrdemProducao ordemProducao;

			for (BigDecimal idiProc : programacao.getOrdensDeProducaoLancadas()) {
				produtosAcabadosVO = ProdutoAcabadoAProduzirFinder.findProdutosAcabadosAProduzir(idiProc);
				for (DynamicVO produtoAcabadoVO : produtosAcabadosVO) {
					ordemProducao = new ProgramacaoProducaoSimplificadaOrdemProducao();
					ordemProducao.setFromProdutoAcabadoAProduzirVO(produtoAcabadoVO);
					ordemProducao.setNuProgP(programacao.getNuProgP().longValue() == 0L? nuPrgp : programacao.getNuProgP());
					ordemProducao.persist();

					programacao.getOrdensDeProducao().add(ordemProducao);
				}
			}
		} catch (Exception e) {
			throw new ProgramacaoProducaoSimplificadaExceptions.OrdensProducaoNaoIncluidasNaProgramacaoException(e);
		}

	}

	public void importaProgramacaoSimplificada() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					importarProgramacaoSimplificada();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir as Programações Simplificadas", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void importarProgramacaoSimplificada() throws RecordNotFoundException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		ProgramacaoProducaoSimplificada programacao;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(ProgramacaoProducaoSimplificada.class, "queProgramacaoSimplificada.sql");

			rs = sql.executeQuery();
			while (rs.next()) {
				programacao = new ProgramacaoProducaoSimplificada(null);
				programacao.loadByResultSet(rs);
				programacao.persists();
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}

	}
}

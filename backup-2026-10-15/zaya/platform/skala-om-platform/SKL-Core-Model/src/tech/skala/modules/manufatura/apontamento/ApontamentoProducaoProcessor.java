package tech.skala.modules.manufatura.apontamento;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.jdom.Element;

import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.mgeprod.model.helper.ApontamentoHelper;
import br.com.sankhya.mgeprod.model.helper.MomentoOperacaoEstoque;
import br.com.sankhya.mgeprod.model.helper.OperacoesEstoqueHelper;
import br.com.sankhya.mgeprod.model.utils.ProdutoControle;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.manufatura.atividade.InstanciaAtividade;
import tech.skala.modules.manufatura.atividade.InstanciaAtividadeProcessor;
import tech.skala.modules.manufatura.atividade.InstanciaAtividadeSituacaoEnum;
import tech.skala.modules.manufatura.ordemproducao.OrdemProducao;
import tech.skala.modules.manufatura.ordemproducao.VolumeProducao;
import tech.skala.modules.manufatura.ordemproducao.VolumeProducaoFinder;
import tech.skala.modules.manufatura.ordemproducao.VolumeProducaoStatusEnum;

public class ApontamentoProducaoProcessor {

	private final Logger					logger = Logger.getLogger(this.getClass().getCanonicalName());

	/**
	 * O método {@code criaApontamentoProducao} é responsável por criar um
	 * apontamento para uma determinada OP.
	 * 
	 * <p>
	 * Este método realiza uma verificação de apontamentos pendentes, é disparado
	 * uma exceção, assim impedidindo o seguimento do método.
	 * </p>
	 * 
	 * @param ordemProducao
	 * @param nuvOP
	 * @throws SkalaModelException
	 */
	public void criaApontamentoProducao(OrdemProducao ordemProducao, BigDecimal nuvOP) throws SkalaModelException {
		ApontamentoProducao apontamento;
		for (InstanciaAtividade atividade : ordemProducao.getAtividades()) {

			verificaApontamentoPendente(atividade);

			apontamento = criarApontamentos(ordemProducao, atividade, nuvOP);
			
			if(apontamento != null) {
				verificaApontamentosAdicionais(ordemProducao, apontamento);
				alteraQtdApontada(ordemProducao, apontamento);				
			}
			
		}

	}
	
	private void verificaApontamentosAdicionais(OrdemProducao ordemProducao, ApontamentoProducao apontamento) throws SkalaModelException {
		long qtdApontadaTotal = 0;
		for (VolumeProducao etiqueta : ordemProducao.getEtiquetas())
			if (VolumeProducaoStatusEnum.PROCESSADO.getValue().equals(etiqueta.getStatus()))
				qtdApontadaTotal += etiqueta.getCapacidade().longValue();

		if (qtdApontadaTotal >= ordemProducao.getQtdProduzir().longValue()) 
			relacionarApontamentoPA(apontamento);
	}

	private void relacionarApontamentoPA(ApontamentoProducao apontamento) throws SkalaModelException {
		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			ApontamentoHelper apontamentoHelper = new ApontamentoHelper(jdbc);
			ArrayList<ProdutoControle> produtosAcabadosAtividade = apontamentoHelper
					.getProdutosAcabadosAtividade(apontamento.getIdiAtv());

			long seqPA = 1;
			for (ProdutoControle produtoAcabadoAtv : produtosAcabadosAtividade) {

				EntityInsert entityNew = new EntityInsert(DynamicEntityNames.APONTAMENTO_PA);
				entityNew.setField(SklFieldName.NUAPO, apontamento.getNuApo());
				entityNew.setField(SklFieldName.SEQAPA, BigDecimal.valueOf(seqPA));
				entityNew.setField(SklFieldName.CODPRODPA, produtoAcabadoAtv.getCodProd());
				entityNew.setField(SklFieldName.CONTROLEPA, produtoAcabadoAtv.getControle());
				entityNew.setField(SklFieldName.QTDAPONTADA, BigDecimal.ZERO);
				entityNew.setField(SklFieldName.QTDPERDA, BigDecimal.ZERO);
				entityNew.fluidInsert();

				seqPA++;
			}

		} catch (Exception e) {
			String msg = String.format("[NUAPO %s] Não foi possível relacionar produto ao apontamento.", apontamento.getNuApo());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
	}

	private void verificaApontamentoPendente(InstanciaAtividade atividade) throws SkalaModelException {
		
		List<ApontamentoProducao> apontamentosPendentes = ApontamentoProducaoFinder.buscaApontamentosPendentesPorAtividade(atividade);
	
		if(!apontamentosPendentes.isEmpty()) {
			String msg = "Já existe um Apontamento aguardando confirmação.\nFinalize-o antes de iniciar um novo.";
			throw new SkalaModelException(msg);
		}
		
	}

	private ApontamentoProducao criarApontamentos(OrdemProducao ordemProducao, InstanciaAtividade atividade, BigDecimal nuvOP) throws SkalaModelException {
		JdbcWrapper jdbc = null;

		Element element = null;
		ApontamentoProducao apontamento = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			ApontamentoHelper apt = new ApontamentoHelper(jdbc);
			element = apt.criarApontamentosAtividade(AuthenticationInfo.getCurrent().getUserID(), atividade.getIdiAtv());
			
			apontamento = new ApontamentoProducao();
			apontamento.loadByPk(element.getAttribute("NUAPO").getLongValue());
			
			relacionaApontamentoACaixa(ordemProducao, apontamento, nuvOP);
			
			atividade.getApontamentos().add(apontamento);
		} catch (Exception e) {
			String msg = String.format(
					"[OP %s] Não foi possível criar Apontamentos para Atividade %s da Ordem de Produção.", ordemProducao.getIdiProc(),
					atividade.getIdiAtv());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
		return apontamento;
	}
	
	private void relacionaApontamentoACaixa(OrdemProducao ordemProducao, ApontamentoProducao apontamento,
			BigDecimal nuvOP) throws SkalaModelException {
		JapeWrapper volumeProducaoDAO = JapeFactory.dao("AD_PLTPRIVOP");
		try {
			DynamicVO vo = volumeProducaoDAO.findByPK(ordemProducao.getIdiProc(), nuvOP);

			VolumeProducao volume = new VolumeProducao();
			volume.loadByVO(vo);
			volume.setNuApo(apontamento.getNuApo());
			volume.setStatus(VolumeProducaoStatusEnum.EM_ANDAMENTO.getValue());
			volume.persists();
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível relacionar o apontamento à uma caixa.", e);
		}
	}
	
	private void alteraQtdApontada(OrdemProducao ordemProducao, ApontamentoProducao apontamento) throws SkalaModelException {
		
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.APONTAMENTO_PA);
		VolumeProducaoFinder finder = new VolumeProducaoFinder();
		BigDecimal capacidadePorCaixa = null;
		Collection<DynamicVO> apontamentosVO = null;
		try {
			apontamentosVO = dao.find("this.NUAPO = ?", apontamento.getNuApo());
			capacidadePorCaixa = finder.buscaCapacidadePorCaixa(ordemProducao.getProdutoVenda().getCodGProd());

			for (DynamicVO apontamentoVO : apontamentosVO) {
				EntityUpdate entityUpd = new EntityUpdate(DynamicEntityNames.APONTAMENTO_PA, apontamentoVO);
				entityUpd.setField("QTDAPONTADA", capacidadePorCaixa);
				entityUpd.fluidUpdate();
			}

		} catch (Exception e) {
			String msg = String.format("[NUAPO: %s] Não foi possível alterar quantidade Apontada.",
					apontamento.getNuApo());
			SkalaModelException.throwMe(msg, e);
		}

	}
	
	/**
	 * Método responsável por realizar a confirmação de um apontamento.
	 * 
	 * <p>
	 * O método tem a responsabilidade de realizar a confirmação de um apontamento
	 * <p>
	 * 
	 * @param ordemProducao
	 * @param nuvOP 
	 * @param apontamento 
	 * @throws SkalaModelException
	 */
	public void confirmaApontamento(OrdemProducao ordemProducao, BigDecimal nuvOP, ApontamentoProducao apontamento) throws SkalaModelException {
		if (apontamento != null) {
			verificaInstanciaAtividadeParada(apontamento);

			confirmaApontamento(apontamento);
			confirmaEtiqueta(ordemProducao.getIdiProc(), nuvOP);
		}
	}

	public void executaOperacoesEstoqueAtividade(ApontamentoProducao apontamento, OrdemProducao ordemProducao, final boolean isUltimoApontamento) throws SkalaModelException {
		JdbcWrapper jdbc = null;
		
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			ApontamentoHelper apt = new ApontamentoHelper(jdbc);
			OperacoesEstoqueHelper operacoesEstoqueHelper = new OperacoesEstoqueHelper(jdbc);
			operacoesEstoqueHelper.executarOperacoesEstoqueAtividade(apontamento.getIdiAtv(), apontamento.getNuApo(), MomentoOperacaoEstoque.APONTAMENTO_PA);
			
			if(isUltimoApontamento) {
				//processarPerdaUltimoApontamento(apontamento.getIdiAtv(), ordemProducao.getIdiProc(), apontamento.getNuApo());
				//apt.finalizarProducaoProdutoAcabadosApontamento(ordemProducao.getIdiProc(), apontamento.getNuApo()); 
				
				InstanciaAtividadeProcessor atividadeProcessor = new InstanciaAtividadeProcessor();
				atividadeProcessor.finalizarInstanciaAtividade(apontamento.getIdiAtv());
			}
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível executar operações de estoque.", e);
			excluirApontamento(apontamento);
			
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}
		
	}

	private void excluirApontamento(ApontamentoProducao apontamento) throws SkalaModelException {
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_APONTAMENTO);
			dao.delete(apontamento.getNuApo());
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir Apontamento.", e);
		}
	}

	private void verificaInstanciaAtividadeParada(ApontamentoProducao apontamento) throws SkalaModelException {

		InstanciaAtividade atividade = new InstanciaAtividade();
		atividade.loadByPk(apontamento.getIdiAtv());

		if (InstanciaAtividadeSituacaoEnum.PARADA.getValue().equals(atividade.getSituacao())) {
			String msg = String.format(
					"[NUAPO %s IDIATV %s] Não foi possível concluir apontamento. Atividade está parada.",
					apontamento.getNuApo(), apontamento.getIdiAtv());
			throw new SkalaModelException(msg);
		}
	}
	
	private void confirmaEtiqueta(BigDecimal idiProc, BigDecimal nuvOP) throws SkalaModelException {
		JapeWrapper volumeProducaoDAO = JapeFactory.dao("AD_PLTPRIVOP");
		DynamicVO vo = null;
		
		try {
			vo = volumeProducaoDAO.findByPK(idiProc, nuvOP);
			
			VolumeProducao volume = new VolumeProducao();
			volume.loadByVO(vo);
			volume.setStatus(VolumeProducaoStatusEnum.PROCESSADO.getValue());
			volume.persists();
		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}
	}


	private void confirmaApontamento(ApontamentoProducao apontamento) throws SkalaModelException {
		JdbcWrapper jdbc = null;

		JapeWrapper apontamentoDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_APONTAMENTO);
		DynamicVO apontamentoVO = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			ApontamentoHelper apt = new ApontamentoHelper(jdbc);

			if (ApontamentoProducaoSituacaoEnum.PENDENTE.getValue().equalsIgnoreCase(apontamento.getSituacao())) {
				
				apontamentoVO = apontamentoDAO.findByPK(apontamento.getNuApo());
				apt.validarConfirmacaoApontamento(apontamentoVO, true);
				apt.confirmarApontamento(apontamento.getNuApo(), true, true);
			}

		} catch (Exception e) {
			String msg = String.format("[NUAPO %s] Não foi possível confirmar o apontamento.", apontamento.getNuApo());
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcWrapper.closeSession(jdbc);
		}

	}
	
	public void processarPerdaUltimoApontamento(BigDecimal idiatv, BigDecimal idiproc, BigDecimal nuapo) throws SkalaModelException {
		JdbcWrapper jdbc = null;

		
		NativeSql sqlBuscaApontamentoQtdApontadaMenorQtdProduzir = null;
		JapeSession.putProperty("br.com.sankhya.mgeprod.ignorar.reproporcionalizacao.qtd.apontada.mp", Boolean.TRUE);
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			sqlBuscaApontamentoQtdApontadaMenorQtdProduzir = new NativeSql(jdbc, getClass(),
					"BuscaApontamentoQtdApontada_queBuscaApontamentoQtdApontada.sql");
			sqlBuscaApontamentoQtdApontadaMenorQtdProduzir.setNamedParameter("IDIATV", idiatv);
			sqlBuscaApontamentoQtdApontadaMenorQtdProduzir.setNamedParameter("IDIPROC", idiproc);
			sqlBuscaApontamentoQtdApontadaMenorQtdProduzir.setNamedParameter("NUAPO", nuapo);
			ResultSet result = sqlBuscaApontamentoQtdApontadaMenorQtdProduzir.executeQuery();
			while (result.next()) {
				if (result.getDouble("TOTAL_APONTADO") < 0.0D) {
					PersistentLocalEntity apontamentoPAEntity = EntityFacadeFactory.getDWFFacade()
							.findEntityByPrimaryKey("ApontamentoPA",
									new Object[] { nuapo, result.getBigDecimal("SEQAPA") });
					DynamicVO apontamentoPAVO = (DynamicVO) apontamentoPAEntity.getValueObject();
					apontamentoPAVO.setProperty("QTDPERDA", result.getBigDecimal("TOTAL_APONTADO").abs());
					apontamentoPAEntity.setValueObject((EntityVO) apontamentoPAVO);
				}
			}
			result.close();
		
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar perda para o ultimo apontamento.", e);
		} finally {
			JapeSession.putProperty("br.com.sankhya.mgeprod.ignorar.reproporcionalizacao.qtd.apontada.mp",
					Boolean.FALSE);
			NativeSql.releaseResources(sqlBuscaApontamentoQtdApontadaMenorQtdProduzir);
				JdbcWrapper.closeSession(jdbc);
		}
	}

	
}

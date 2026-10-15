package tech.skala.core.services;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.SessionBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JsonUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.BaseSPBean;
import br.com.sankhya.ws.ServiceContext;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.SessionWrapper;
import tech.skala.core.util.json.SkalaJsonUtils;
import tech.skala.core.util.string.SkalaStringUtils;
import tech.skala.modules.suprimentos.abastecimento.ProdutoAtendimentoRequisicao;
import tech.skala.modules.suprimentos.abastecimento.ProdutoAtendimentoRequisicaoDTO;
import tech.skala.modules.suprimentos.abastecimento.ProdutoRequisicao;
import tech.skala.modules.suprimentos.abastecimento.ProdutoRequisicaoStatusEnum;
import tech.skala.modules.suprimentos.abastecimento.Requisicao;
import tech.skala.modules.suprimentos.abastecimento.RequisicaoFinder;
import tech.skala.modules.suprimentos.abastecimento.RequisicaoSequenciaDTO;
import tech.skala.modules.suprimentos.abastecimento.RequisicaoStatusEnum;

/**
 * 
 * @author Leandro Bizzinotto
 * @since 2023-03-13
 * 
 * @ejb.bean name="SkalaAbastecimentoSP"
 *           jndi-name="tech/skala/core/services/SkalaAbastecimentoSP"
 *           type="Stateless" transaction-type="Container" view-type="remote"
 * @ejb.transaction type="Supports"
 * @ejb.util generate="false"
 */
public class SkalaAbastecimentoSPBean extends BaseSPBean implements SessionBean {
	
	private static final long serialVersionUID = 2051492662516052027L;
	
	private static Gson gson = new GsonBuilder()
			.serializeNulls()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
			.create();
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void buscaRequisicoesLiberadas(ServiceContext ctx) {
		SessionWrapper session = null;
		String errorMessagem = null;

		List<Requisicao> requisicoes = null;
		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			RequisicaoFinder requisicaoFinder = new RequisicaoFinder();
			requisicoes = requisicaoFinder.buscaRequisicoesLiberadas();
		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, requisicoes, errorMessagem);
			
			if (session != null)
				session.close();
		}
	}
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void gerarEtiquetas(ServiceContext ctx) {
		String mensagem = "OK.";
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.addProperty("message", mensagem);

		ctx.setJsonResponse(jsonResponse);
	}
	
	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void salvarLote(ServiceContext ctx) {
		SessionWrapper session = null;
		String errorMessagem = null;

		ProdutoAtendimentoRequisicao atendimentoRequisicao = null;
		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			ProdutoAtendimentoRequisicaoDTO dto = gson.fromJson(paramsJO, ProdutoAtendimentoRequisicaoDTO.class);

			if (dto != null) {
				ProdutoRequisicao produtoRequisicao = RequisicaoFinder.buscaProdutoRequisicaoByPK(dto.getNuReq(),
						dto.getSequencia());

				atendimentoRequisicao = new ProdutoAtendimentoRequisicao();

				atendimentoRequisicao.setCodProd(produtoRequisicao.getCodProd());
				atendimentoRequisicao.setCodVol(produtoRequisicao.getCodVol());
				atendimentoRequisicao.setControle(dto.getControle());
				atendimentoRequisicao.setNuReq(dto.getNuReq());
				atendimentoRequisicao.setQtdAtendida(dto.getQtdAtendida());

				BigDecimal qtdUnit = (dto.getQtdVol().longValue() == 0) ? BigDecimal.ZERO
						: dto.getQtdAtendida().divide(dto.getQtdVol(), BigDecimalUtil.MATH_CTX);

				atendimentoRequisicao.setQtdUnit(qtdUnit);
				atendimentoRequisicao.setQtdVol(dto.getQtdVol());
				atendimentoRequisicao.setSequencia(dto.getSequencia());

				persistirLote(atendimentoRequisicao);
			}
		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponse(ctx, atendimentoRequisicao, errorMessagem);

			if (session != null)
				session.close();
		}
	}

	/**
	 * @ejb.interface-method tview-type="remote"
	 * @ejb.transaction type="Supports"
	 */
	public void setAtendimentoItem(ServiceContext ctx) {
		SessionWrapper session = null;
		String errorMessagem = null;
		
		Requisicao requisicao = null;
		try {
			session = SessionWrapper.getInstance();
			setupContext(ctx);

			JsonObject paramsJO = JsonUtils.getJsonObject(ctx.getJsonRequestBody(), "params");

			RequisicaoSequenciaDTO dto = gson.fromJson(paramsJO, RequisicaoSequenciaDTO.class);

			if (dto != null) {
				requisicao = RequisicaoFinder.buscaRequisicaoByPK(dto.getNuReq());
				ProdutoRequisicao produtoRequisicao = RequisicaoFinder.buscaProdutoRequisicaoByPK(dto.getNuReq(), dto.getSequencia());

				persistirAtendimentoProduto(requisicao, produtoRequisicao);
			}
		} catch (Exception e) {
			errorMessagem = SkalaModelException.buildErrorMessage(null, e);
			e.printStackTrace();
		} finally {
			setContextResponseAtendimento(ctx, requisicao, errorMessagem);

			if (session != null)
				session.close();
		}
	}
	
	private void persistirLote(ProdutoAtendimentoRequisicao atendimentoRequisicao) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					atendimentoRequisicao.persists();
				}
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível salvar Lote do Produto", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void persistirAtendimentoProduto(Requisicao requisicao, ProdutoRequisicao produtoRequisicao)	
			throws SkalaModelException {
		
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					if (RequisicaoStatusEnum.LIBERADO.getValue().equals(requisicao.getStatus())) {
						requisicao.setStatus(RequisicaoStatusEnum.EM_ATENDIMENTO.getValue());
						
						requisicao.persists();
					}

					if (ProdutoRequisicaoStatusEnum.PENDENTE.getValue().equals(produtoRequisicao.getStatus())) {
						produtoRequisicao.setStatus(ProdutoRequisicaoStatusEnum.EM_ATENDIMENTO.getValue());
						produtoRequisicao.setCodUsuAtd(AuthenticationInfo.getCurrent().getUserID());
						
						produtoRequisicao.persists();
					}
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar Atendimento Item", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
	private void setContextResponse(ServiceContext ctx, ProdutoAtendimentoRequisicao produtoAtendimentoRequisicao,
			String errorMessagem) {
		JsonObject jsonResponse = new JsonObject();
		String status;
		String mensagem;
		StringBuilder sbErrorMessagem = new StringBuilder();

		if (produtoAtendimentoRequisicao == null) {
			status = "0";
			mensagem = "Atendimento não realizado";
		} else {
			status = "1";
			mensagem = "Atendimento realizado";
		}
		
		sbErrorMessagem.append(mensagem);
		if (errorMessagem != null) {
			sbErrorMessagem.append(SkalaStringUtils.lineSeparator());
			sbErrorMessagem.append(errorMessagem);
		}

		jsonResponse.addProperty("status", status);
		jsonResponse.addProperty("message", sbErrorMessagem.toString());

		ctx.setJsonResponse(jsonResponse);
		
	}
	
	private void setContextResponse(ServiceContext ctx, List<Requisicao> requisicoes, String errorMessagem) {
		JsonObject jsonResponse = new JsonObject();
		String status;
		String mensagem;
		StringBuilder sbErrorMessagem = new StringBuilder();

		if (requisicoes.isEmpty()) {
			status = "0";
			mensagem = "Não há requisições criadas";
		} else {
			status = "1";
			mensagem = "Requisições localizadas";
		}
		
		sbErrorMessagem.append(mensagem);
		if (errorMessagem != null) {
			sbErrorMessagem.append(SkalaStringUtils.lineSeparator());
			sbErrorMessagem.append(errorMessagem);
		}

		jsonResponse.addProperty("status", status);
		jsonResponse.addProperty("message", sbErrorMessagem.toString());

		String json = gson.toJson(requisicoes);
		jsonResponse.add("requisicoes", JsonUtils.parserString(json));

		ctx.setJsonResponse(jsonResponse);
	}
	
	private void setContextResponseAtendimento(ServiceContext ctx, Requisicao requisicao, String errorMessagem) {
		JsonObject jsonResponse = new JsonObject();
		String status;
		String mensagem;
		StringBuilder sbErrorMessagem = new StringBuilder();

		if (requisicao == null) {
			status = "0";
			mensagem = "Atendimento não realizado";
		} else {
			status = "1";
			mensagem = "Atendimento realizado";
		}
		
		sbErrorMessagem.append(mensagem);
		if (errorMessagem != null) {
			sbErrorMessagem.append(SkalaStringUtils.lineSeparator());
			sbErrorMessagem.append(errorMessagem);
		}

		jsonResponse.addProperty("status", status);
		jsonResponse.addProperty("message", sbErrorMessagem.toString());

		ctx.setJsonResponse(jsonResponse);
	}
}

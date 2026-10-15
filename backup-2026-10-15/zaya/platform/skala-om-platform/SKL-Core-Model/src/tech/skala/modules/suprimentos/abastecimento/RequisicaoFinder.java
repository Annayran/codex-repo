package tech.skala.modules.suprimentos.abastecimento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * A classe {@code RequisicaoFinder} prove recursos para busca de informacoes relacionadas a 
 * uma {@link Requisicao}.
 * 
 * <p>
 * A classe {@code RequisicaoFinder} prove metodos diversos especializados em localizar uma {@link Requisicao} 
 * (ou varias) segundo os mais diversos criterios, sendo uma classe especializada para este fim.
 * </p>
 * 
 * @author leandrobizzinotto
 * @since 2023-03-13
 *
 */
public class RequisicaoFinder {
	
	public static Requisicao buscaRequisicaoByPK(BigDecimal nuReq) throws SkalaModelException {
		Requisicao requisicao = new Requisicao();
		
		requisicao.loadByPk(nuReq);
		
		return requisicao;
		
	} 
	
	public static ProdutoRequisicao buscaProdutoRequisicaoByPK(BigDecimal nuReq, BigDecimal sequencia) throws SkalaModelException {
		ProdutoRequisicao produtoRequisicao = new ProdutoRequisicao();
		
		produtoRequisicao.loadByPk(nuReq, sequencia);
		
		return produtoRequisicao;
		
	}
	
	public static ProdutoAtendimentoRequisicao buscaProdutoAtendimentoRequisicaoByPK(BigDecimal nuReq, BigDecimal sequencia, BigDecimal sequenciaAtd) throws SkalaModelException {
		ProdutoAtendimentoRequisicao buscaProdutoAtendimentoRequisicao = new ProdutoAtendimentoRequisicao();
		
		buscaProdutoAtendimentoRequisicao.loadByPk(nuReq, sequencia, sequenciaAtd);
		
		return buscaProdutoAtendimentoRequisicao;
	}
	
	
	public List<Requisicao> buscaRequisicoesLiberadas() throws SkalaModelException {
		
		JapeWrapper dao = JapeFactory.dao(SklEntityName.REQUISICOES); 
		
		Collection<DynamicVO> requisicoesVO = null;
		try {
			requisicoesVO = dao.find("this.STATUS = 'L'");
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar Requisição", e);
		}
		
		
		Requisicao requisicao = null;
		List<Requisicao> requisicoes = new ArrayList<>();
		for (DynamicVO vo : requisicoesVO) {
			requisicao = new Requisicao();
			requisicao.loadByVO(vo);
			
			List<ProdutoRequisicao> itensRequisicao = buscaItensRequisicao(requisicao.getNuReq());
			requisicao.setProdutos(itensRequisicao);
			
			requisicoes.add(requisicao);
		}
		
		return requisicoes;
	}

	private List<ProdutoRequisicao> buscaItensRequisicao(BigDecimal nuReq) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(SklEntityName.PRODUTO_REQUISICOES);
		
		Collection<DynamicVO> itensRequisicaoVO = null;
		
		try {
			itensRequisicaoVO = dao.find("this.NUREQ = ?", nuReq);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar os Itens da Requisição", e);
		}
		
		ProdutoRequisicao produtoRequisicao = null;
		List<ProdutoRequisicao> produtos = new ArrayList<>();
		for (DynamicVO vo : itensRequisicaoVO) {
			produtoRequisicao = new ProdutoRequisicao();
			produtoRequisicao.loadByVO(vo);
			produtoRequisicao.setProdutoAtendimentos(buscaItensAtendimento(nuReq, produtoRequisicao.getSequencia()));
			
			produtos.add(produtoRequisicao);
		}

		return produtos;
	}
	
	private List<ProdutoAtendimentoRequisicao> buscaItensAtendimento(BigDecimal nuReq, BigDecimal sequencia)
			throws SkalaModelException {

		JapeWrapper dao = JapeFactory.dao("AD_PLTGFREQITEATD");

		Collection<DynamicVO> itensAtendimentoVO = null;

		try {
			itensAtendimentoVO = dao.find("this.NUREQ = ? AND this.SEQUENCIA = ?", nuReq, sequencia);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar os Itens da Requisição", e);
		}

		ProdutoAtendimentoRequisicao produtoAtendimento = null;
		List<ProdutoAtendimentoRequisicao> produtosAtendimento = new ArrayList<>();
		for (DynamicVO vo : itensAtendimentoVO) {
			produtoAtendimento = new ProdutoAtendimentoRequisicao();
			produtoAtendimento.loadByVO(vo);

			produtosAtendimento.add(produtoAtendimento);
		}

		return produtosAtendimento;

	}
	
}


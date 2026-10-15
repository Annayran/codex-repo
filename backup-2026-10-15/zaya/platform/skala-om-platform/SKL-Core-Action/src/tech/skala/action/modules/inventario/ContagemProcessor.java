package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.inventario.ContagemInventario;
import tech.skala.modules.inventario.ContagemInventarioStatusEnum;
import tech.skala.modules.inventario.ProdutoContagem;

public class ContagemProcessor {

	//private final Logger								logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	
	public void processaContagem(ContagemInventario contagemInventario) throws SkalaModelException {
		
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					processarContagem(contagemInventario);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar Contagem de Inventário.",e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void processarContagem(ContagemInventario contagemInventario) throws SkalaModelException {
		ContagemEstoque contagemEstoque;
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ESTOQUE);
		try {
			contagemEstoque = new ContagemEstoque();
			contagemEstoque.setCodEmp(contagemInventario.getCodEmp());
			contagemEstoque.setCodLocal(contagemInventario.getCodLocal());
			contagemEstoque.setCodParc(contagemInventario.getCodParc());
			contagemEstoque.setTipo(contagemInventario.getTipo());
			contagemEstoque.setDtContagem(getDataInventario(contagemInventario.getIdIvt()));
			contagemEstoque.setSequencia(contagemInventario.getSequencia().add(BigDecimal.ONE));

			//validar controle com espaço
			contagemInventario.getProdutos().forEach(p -> p.setControle(p.getControle().trim()));
			
			List<ProdutoContagem> produtos = getProdutosAgrupados(contagemInventario.getProdutos());

			DynamicVO vo;
			String controle;
			for (ProdutoContagem produto : produtos) {
				controle = ("SEMLOTE".equalsIgnoreCase(produto.getControle()) ? " " : produto.getControle());

				vo = dao.findByPK(contagemInventario.getCodEmp(), produto.getCodProd(),
						contagemInventario.getCodLocal(), controle, contagemInventario.getCodParc(),
						contagemInventario.getTipo());

				contagemEstoque.setCodProd(produto.getCodProd());
				contagemEstoque.setControle(controle);
				contagemEstoque.setQtdEstUnCad(produto.getQtdContagem());
				contagemEstoque.setQtdEst(produto.getQtdContagem());

				if (vo != null) {
					contagemEstoque.setCodVol(vo.asString("Produto.CODVOL"));
					contagemEstoque.setDtVal(vo.asTimestamp(SklFieldName.DTVAL));
					contagemEstoque.setDtFabricacao(vo.asTimestamp(SklFieldName.DTFABRICACAO));
				}

				contagemEstoque.persists();
			}
			
			contagemInventario.setStatus(ContagemInventarioStatusEnum.CONTAGEM_PROCESSADA.getValue());
			contagemInventario.persists();

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar Contagem de Inventário.", e);
		}

	}
	
	private List<ProdutoContagem> getProdutosAgrupados(List<ProdutoContagem> produtosContagem) {
		List<ProdutoContagem> produtos = new ArrayList<>();

		for (ProdutoContagem produto : produtosContagem) {
			if (!produtos.contains(produto)) {
				produtos.add(produto);
			} else {
				ProdutoContagem p = produtos.get(produtos.indexOf(produto));
				p.setQtdContagem(p.getQtdContagem().add(produto.getQtdContagem()));
			}
		}

		return produtos;
	}
	
	
	private Timestamp getDataInventario(BigDecimal idIvt) throws RecordNotFoundException {

		JapeWrapper dao = JapeFactory.dao(SklEntityName.INVENTARIO);
		DynamicVO vo;
		Timestamp dtIvt = null;
		try {
			vo = dao.findByPK(idIvt);
			dtIvt = vo.asTimestamp(SklFieldName.DTIVT);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		return dtIvt;
	}

	public void validarContagem(ContagemInventario contagemInventario) throws SkalaModelException {
		
		if(contagemInventario.getStatus().equals(ContagemInventarioStatusEnum.CONTAGEM_PROCESSADA.getValue()))
			throw new SkalaModelException("Contagem já Processada.");

		if(!contagemInventario.getStatus().equals(ContagemInventarioStatusEnum.FINALIZADA.getValue()))
			throw new SkalaModelException("Só é possível processar contagens com o Status <b>Finalizada</b>.");
	}

}

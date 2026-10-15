package br.com.zaya.like.Actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.like.Actions.repositories.CancelarPedidoCompraRepository;
import br.com.zaya.like.Actions.services.CancelarPedidoService;
import br.com.zaya.like.Repositories.ConsultasUtil;

public class CancelarPedidoCompraAction implements AcaoRotinaJava {
  private static final String PEDIDO_COMPRAS = "O";
	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
		
		Registro[] linhas = contexto.getLinhas();
		int linhasSelecionadas = linhas.length;
		boolean executar = false;
		
		String motivoCancelamento = (String) contexto.getParam("P_MOTIVO");
		
		BigDecimal nunota = (BigDecimal) linhas[0].getCampo("NUNOTA");
		
		boolean topPermiteCancelarItem = ConsultasUtil.topPermiteCancelarItem(nunota);
		
		String tipMovimento = ConsultasUtil.buscarTipoMovimentoNota(nunota);
		
		if(!topPermiteCancelarItem){
			throw new Exception("Ação permitida apenas para pedidos/notas que tenha a marcação: 'Permite Cancelar Itens do lançamento?'");
		}

		if (linhasSelecionadas == 1) {
			BigDecimal nunegociacao = (BigDecimal) linhas[0].getCampo("AD_NUNEGOCIACAO");
			
			if (nunegociacao == null) {
				nunegociacao = nunota; 
			}

			String titulo = "Cancelar Item Pedido";
			String texto = "Deseja realizar o cancelamento deste pedido, Negociação/Nota [" + nunegociacao + "] ?";
			int indice = 1;
			executar = contexto.confirmarSimNao(titulo, texto, indice);
		}

		if (linhasSelecionadas > 1) {
			String titulo = "Cancelar Item Pedido";
			String texto = "Deseja realizar o cancelamento dos pedidos Selecionados ?";
			int indice = 1;
			executar = contexto.confirmarSimNao(titulo, texto, indice);
		}
		
		List<BigDecimal> usuarios = new ArrayList<>();

		if (executar) {
			for (Registro linha : contexto.getLinhas()) {
				BigDecimal nunegociacao = (BigDecimal) linha.getCampo("AD_NUNEGOCIACAO");
				BigDecimal nuNota = (BigDecimal) linha.getCampo("NUNOTA");
				BigDecimal codProd = (BigDecimal) linha.getCampo("CODPROD");
				BigDecimal sequencia = (BigDecimal) linha.getCampo("SEQUENCIA");
				
				String pendente =  (String) linha.getCampo("PENDENTE");
				
				if("N".equals(pendente)) {
					contexto.setMensagemRetorno("Este item não tem quantidade pendente, não pode ser cancelados. O item do pedido " + nuNota + " - " + codProd + " não está pendente.");
					return;
				}
				
				usuarios = CancelarPedidoService.buscarUsuariosParaNotificacao(nunegociacao, nuNota);

				CancelarPedidoService.cancelarItemPedido(nuNota, codProd, motivoCancelamento);

				if (PEDIDO_COMPRAS.equals(tipMovimento)) {
					Collection<DynamicVO> itensPedidoVenda = new CancelarPedidoCompraRepository().buscarItensPedidoVendaDaNegociacao(nunegociacao, codProd);
					for (DynamicVO itemPedido : itensPedidoVenda) {
						CancelarPedidoService.dadosAcompanhamentoNota(itemPedido.asBigDecimal("NUNOTA"), motivoCancelamento, itemPedido.asBigDecimal("SEQUENCIA"));
					}
				}
				
				if((BigDecimal.valueOf(1638)).compareTo(ConsultasUtil.buscarTipoDeOperação(nunota)) == 0) {
					Collection<DynamicVO> itensPedidoAmostra = new CancelarPedidoCompraRepository().buscarItensPedidoRequisicaoDaNegociacao(nunegociacao, codProd);

					for (DynamicVO itemAmostra : itensPedidoAmostra) {
						CancelarPedidoService.dadosAcompanhamentoNota(itemAmostra.asBigDecimal("NUNOTA"), motivoCancelamento, itemAmostra.asBigDecimal("SEQUENCIA"));
						
						//CancelarPedidoCompraService.marcarItemPedidoComoNaoPendentePorProduto(itemAmostra.asBigDecimal("NUNOTA"), codProd, itemAmostra.asBigDecimal("SEQUENCIA"));
					}
					
				}
				
				CancelarPedidoService.incluirNotificacaoCancelamento(usuarios,motivoCancelamento);
		
			}

		} else {
			contexto.setMensagemRetorno("Operação Finalizada Pelo Usuario");
			return;

		}
		contexto.setMensagemRetorno("Pedido cancelado com sucesso!");

	}

}

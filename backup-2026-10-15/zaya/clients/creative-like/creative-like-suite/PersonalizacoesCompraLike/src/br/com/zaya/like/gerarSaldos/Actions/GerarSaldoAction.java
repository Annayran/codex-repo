package br.com.zaya.like.gerarSaldos.Actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.CollectionUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.EntityDAO;
import br.com.sankhya.jape.dao.EntityPropertyDescriptor;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.CentralFaturamento;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.ConfirmacaoNotaHelper;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import br.com.sankhya.ws.ServiceContext;
import br.com.zaya.like.gerarSaldos.Services.GerarSaldosService;
import br.com.zaya.like.helper.DocumentoHelper;

public class GerarSaldoAction implements AcaoRotinaJava{
  EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();

	public void doAction(ContextoAcao contexto) throws Exception {
		StringBuilder stringRetorno = new StringBuilder();

		Registro[] linhas = contexto.getLinhas();
		int linhasSelecionadas = linhas.length;
		boolean executar = false;

		if (linhasSelecionadas == 1) {
			BigDecimal nunegociacao = (BigDecimal) linhas[0].getCampo("NUNEGOCIACAO");

			String titulo = "Geração de Documento";
			String texto = "Deseja realizar a Criação de Documento a partir da Proposta / Negociação [" + nunegociacao + "] ?";
			int indice = 1;
			executar = contexto.confirmarSimNao(titulo, texto, indice);
		}

		if (linhasSelecionadas > 1) {
			BigDecimal nunegociacao = (BigDecimal) linhas[0].getCampo("NUNEGOCIACAO");

			String titulo = "Geração de Documento";
			String texto = "Deseja realizar a Criação dos Documentos a partir das Propostas / Negociações Selecionadas ?";
			int indice = 1;
			executar = contexto.confirmarSimNao(titulo, texto, indice);
		}

		if (executar) {

			BigDecimal nunotaModelo = GerarSaldosService.buscarNunotaModelo(dwfFacade);

			DynamicVO modeloNotaVO = GerarSaldosService.buscarModeloNotaVO(nunotaModelo, dwfFacade);

			EntityDAO cabecalhoNotaDAO = this.dwfFacade.getDAOInstance("CabecalhoNota");
			EntityDAO cabecalhoNegociacaoDAO = this.dwfFacade.getDAOInstance("AD_TGLCAB");

			Map<String, EntityPropertyDescriptor> fieldsCabecalho = cabecalhoNegociacaoDAO.getSQLProvider().getAllFieldsByName();

			for (Registro linha : contexto.getLinhas()) {
				contexto.setMensagemRetorno(GerarSaldosService.executarAcao(stringRetorno, nunotaModelo, modeloNotaVO, cabecalhoNotaDAO,fieldsCabecalho, linha, dwfFacade));
			}

		} else {

			contexto.setMensagemRetorno("Ação Cancelada com Sucesso");

			return;
		}

		contexto.setMensagemRetorno(stringRetorno.toString() + "\nAção Finalizada com Sucesso");
	}




}

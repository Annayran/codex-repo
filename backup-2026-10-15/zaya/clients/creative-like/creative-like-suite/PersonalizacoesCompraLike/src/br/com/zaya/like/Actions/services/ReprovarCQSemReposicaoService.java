package br.com.zaya.like.Actions.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.PlatformService;
import br.com.sankhya.modelcore.PlatformServiceFactory;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.ConfirmacaoNotaHelper;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import br.com.zaya.like.Actions.repositories.ReprovarCQSemRepositories;

public class ReprovarCQSemReposicaoService {
	
	public static BigDecimal reprovarCQSemReposicao(Registro linha, BigDecimal codUsuLogado, BigDecimal param_P_CODLOCAL) throws Exception {
		try {
			String status = (String) linha.getCampo("STATUSNOTA");
			
			BigDecimal nuApo = (BigDecimal) linha.getCampo("NUAPO");
			String observacao = (String) linha.getCampo("OBSLIB");
			BigDecimal nuNegociacao = (BigDecimal) linha.getCampo("NUNEGOCIACAO");
			BigDecimal qtdReprovada = (BigDecimal) linha.getCampo("QTDREP");
		
			verficarLiberacaoAtendida(status);
			
			ReprovarCQSemRepositories.updateTGLCDQ(nuApo, observacao, codUsuLogado);
			ReprovarCQSemRepositories.updateTPRIPA(nuNegociacao, qtdReprovada);
			
		    BigDecimal nunotaTransf = inserirNotasDeTransferencia(linha,param_P_CODLOCAL,codUsuLogado);
			
			ReprovarCQSemRepositories.updateTPRAPA(nuApo, qtdReprovada);
			ReprovarCQSemRepositories.updateAD_LIBERACAOQUALIDADE(linha, nunotaTransf, codUsuLogado, BigDecimal.ZERO);
			
			confirmarNota(nunotaTransf);
			
			return nunotaTransf;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} 
		
	}

	public static void verficarLiberacaoAtendida(String status) {
		List<String> statusAtendidos = Arrays.asList("F", "R", "RR");
		
		if(statusAtendidos.contains(status)){	
			throw new RuntimeException("Liberação de qualidade já foi atendida, para alterar o status gentileza utilizar o botão de ação: \"Limpar Status Liberação de Qualidade\", depois realizar ação desejada.");
		}
	}

	private static BigDecimal inserirNotasDeTransferencia(Registro linha, BigDecimal param_P_CODLOCAL,BigDecimal codUsuLogado) throws Exception {
		BigDecimal nuNotaModelo;
		BigDecimal nunotaDevolucao = BigDecimal.ZERO;
		try {
			
			BigDecimal codLocalOrigem =  BigDecimal.valueOf(6020000);
			
			EntityFacade entityFacade = EntityFacadeFactory.getDWFFacade();
			
			BigDecimal nuNegociacao = (BigDecimal) linha.getCampo("NUNEGOCIACAO");
			
			BigDecimal codParc = (BigDecimal) linha.getCampo("CODPARC");
			
			nuNotaModelo = buscarNotaModeloParam("ZYMODTRANSFRMA");
			DynamicVO notaTransfVO =  getModeloNotaVO(nuNotaModelo);
			
			buscarDadosParceiro(codParc, notaTransfVO);
			notaTransfVO.setProperty("AD_NUNEGOCIACAO", nuNegociacao);
			
			AuthenticationInfo auth = AuthenticationInfo.getCurrent();
			
			CACHelper cacHelper = new CACHelper();
			
			PrePersistEntityState cabPreState = PrePersistEntityState.build(entityFacade, DynamicEntityNames.CABECALHO_NOTA, notaTransfVO);
			
			BarramentoRegra bRegrasCab = cacHelper.incluirAlterarCabecalho(auth, cabPreState);
			DynamicVO newCabVO = bRegrasCab.getState().getNewVO();
			
			BigDecimal nuNota = newCabVO.asBigDecimal("NUNOTA");	
			
			DynamicVO itemVO = (DynamicVO) entityFacade.getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);

			itemVO.setProperty("CODPROD", linha.getCampo("CODPROD"));
			itemVO.setProperty("QTDNEG", linha.getCampo("QTDNEG"));
			itemVO.setProperty("CODLOCALORIG", codLocalOrigem);
			itemVO.setProperty("CODLOCALDEST", param_P_CODLOCAL);
			itemVO.setProperty("CONTROLE", nuNegociacao.toString());
			itemVO.setProperty("CODVOL", linha.getCampo("CODVOL"));

			PrePersistEntityState itePreState = PrePersistEntityState.build(entityFacade, DynamicEntityNames.ITEM_NOTA, itemVO);

			Collection<PrePersistEntityState> itensNota = new ArrayList<PrePersistEntityState>();

			itensNota.add(itePreState);

			cacHelper.incluirAlterarItem(nuNota, auth, itensNota, true);

			nunotaDevolucao = nuNota;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao inserir nota de transferência: " + e.getMessage());
		}
		
		return nunotaDevolucao;
	}

	private static void buscarDadosParceiro(BigDecimal codParc, DynamicVO notaTransfVO) {
		BigDecimal codTipVenda = BigDecimal.ZERO;
		BigDecimal codParcTranspPreferencial = BigDecimal.ZERO;
		String cifFob = null;
		
		ReprovarCQSemRepositories.buscarDadosParceiro(codParc,codTipVenda,codParcTranspPreferencial,cifFob);
						
		notaTransfVO.setProperty("CODPARC", codParc);
		
		if(!"T".equals(notaTransfVO.getProperty("TIPMOV"))){
			notaTransfVO.setProperty("CIF_FOB",cifFob);
			notaTransfVO.setProperty("CODPARCTRANSP",codParcTranspPreferencial);
			notaTransfVO.setProperty("CODTIPVENDA", BigDecimal.ZERO);
		} else {
			if(codTipVenda.compareTo(BigDecimal.ZERO) != 0 ) {
				notaTransfVO.setProperty("CODTIPVENDA",codTipVenda);
			}
		}
	}

	private static BigDecimal buscarNotaModeloParam(String parametro) throws Exception {
		int nuNotaModelo = MGECoreParameter.getParameterAsInt(parametro);
		
		if(nuNotaModelo == 0) {
			throw new Exception("Parâmetro " + parametro + " não está configurado. Favor configurar para prosseguir.");
		}
		
		return BigDecimalUtil.valueOf(nuNotaModelo);
	}

	private static DynamicVO getModeloNotaVO(BigDecimal nuNotaModelo) throws Exception {
		DynamicVO modeloCabecalhoVO = null;

			try {
				EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
				modeloCabecalhoVO = (DynamicVO) dwfEntityFacade.findEntityByPrimaryKeyAsVO(DynamicEntityNames.CABECALHO_NOTA, new Object[]{nuNotaModelo});
			} catch(Exception e) {
				e.printStackTrace();
				throw new Exception("Falha no cálculo de preço dinâmico: O modelo de nota/pedido informado no parâmetro MODCALCPRECDIN não existe.\n" + e.getMessage());
			}
			
			modeloCabecalhoVO.setProperty("NUNOTA", null);
			modeloCabecalhoVO.setProperty("NUMNOTA", null);
			modeloCabecalhoVO.setProperty("TIPMOV", "T");

		return modeloCabecalhoVO;
	}
	
	private static void confirmarNota(BigDecimal nuNota) throws Exception {
		br.com.sankhya.modelcore.comercial.BarramentoRegra bRegrasCab = br.com.sankhya.modelcore.comercial.BarramentoRegra.build(br.com.sankhya.modelcore.comercial.CentralFaturamento.class, "regrasConfirmacaoSilenciosa.xml", AuthenticationInfo.getCurrent());
        bRegrasCab.setValidarSilencioso(true);
        ConfirmacaoNotaHelper.confirmarNota(nuNota, bRegrasCab);
	}

	public static BigDecimal gerarDevolucaoCompra(Registro linha, BigDecimal nuNotaCompra,  BigDecimal codUsoLogado) throws Exception {
		BigDecimal nuNotaDevolucao = BigDecimal.ZERO;
		
		devolucaoparcial(linha, nuNotaCompra);
		
		BigDecimal nuNegociacao = (BigDecimal) linha.getCampo("NUNEGOCIACAO");
		
		faturar(nuNotaCompra, nuNegociacao, codUsoLogado);
		
		return nuNotaDevolucao;
		
	}

	private static void faturar(BigDecimal nuNotaCompra,BigDecimal nuNegociacao, BigDecimal codUsoLogado) throws Exception {
		PlatformService faturaNotaService = PlatformServiceFactory.getInstance().lookupService("@core:faturamento.service");
		
		BigDecimal topDestino = ReprovarCQSemRepositories.buscarTOPDestino(nuNotaCompra);

		Collection<BigDecimal> notasParaFaturamento = new ArrayList<>(Arrays.asList( nuNotaCompra ));

		faturaNotaService.set("NUNOTA"          ,null);
		faturaNotaService.set("NUNOTACOLLECTION", notasParaFaturamento);
		faturaNotaService.set("CODTIPOPER"      , topDestino);
		faturaNotaService.set("DTENTRADASAIDA"  , new Timestamp(System.currentTimeMillis()));
		faturaNotaService.set("HRENTRADASAIDA"  , new Timestamp(System.currentTimeMillis()));
		faturaNotaService.set("DTFATURAMENTO"   , new Timestamp(System.currentTimeMillis()));
		faturaNotaService.set("CODUSUAUTHINFO"  , codUsoLogado);

		faturaNotaService.execute();
		
		
		
	}

	private static void devolucaoparcial(Registro linha, BigDecimal nuNotaCompra) throws Exception {
		
		BigDecimal sequencia =  ReprovarCQSemRepositories.buscarSequenciaNota(linha, nuNotaCompra);
		BigDecimal qtdNegNotaCompra = ReprovarCQSemRepositories.buscarQtdNegocCompra(nuNotaCompra, sequencia);
		BigDecimal qtdReprovada = (BigDecimal) linha.getCampo("QTDREP");
		
		qtdReprovada = qtdReprovada == null ? BigDecimal.ZERO : qtdReprovada;
		
		BigDecimal qtdCorte = qtdNegNotaCompra.subtract(qtdReprovada);
		
		if(BigDecimal.ZERO.compareTo(qtdCorte) >= 0) {
			return;
			//throw new Exception("Quantidade pendente da Nota de Compra não é suficiente para atender a demanda!<br> Faça a Devolução de compra Manual.");
		}
		try {
			JapeFactory.dao(DynamicEntityNames.ITEM_NOTA).
			prepareToUpdateByPK(nuNotaCompra,sequencia )
			.set("QTDCONFERIDA", qtdCorte )
			.update();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}  
	}
	
}

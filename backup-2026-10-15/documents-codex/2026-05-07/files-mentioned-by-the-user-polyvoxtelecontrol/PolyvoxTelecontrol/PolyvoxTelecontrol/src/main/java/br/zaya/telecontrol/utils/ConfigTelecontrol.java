package br.zaya.telecontrol.utils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ParameterUtils;
import java.math.BigDecimal;

public class ConfigTelecontrol {
	public String url;

	public String token;

	public String env;

	public String epProdutos;

	public String epPostos;

	public String epClientes;

	public String epPecas;

	public String epPrecos;

	public String epFamilia;

	public String epLinha;

	public String epListaBasica;

	public String epPedidos;

	public String epPedidosNaoIntegrados;

	public String epFaturamento;

    public String epOrdemServico;

	public BigDecimal codTabelaPreco;

	public boolean debugMode = false;

	public BigDecimal modPedido;

	public boolean confirmaPedido = false;

	public BigDecimal modPedidoGarrantia;

    public BigDecimal modPedGarPlx;

	public BigDecimal codigoTabelaPreco;

	public BigDecimal codigoTabelaPrecoGarantia;

	public BigDecimal codLocal;

	public ConfigTelecontrol() throws Exception {
		BigDecimal idCfg = (BigDecimal) ParameterUtils.getParameter("IDCONFIGTLC");
		DynamicVO configVO = JapeFactory.dao("AD_TCLCFG").findByPK(idCfg);
		if (configVO == null) {
			throw new Exception("Configuração Telecontrol não encontrada (AD_TCLCFG) para o ID: " + idCfg);
		}
		this.url = configVO.asString("URL");
		this.token = configVO.asString("TOKEN");
		this.env = configVO.asString("ENV");
		this.epFamilia = configVO.asString("EP_FAMILIA");
		this.epLinha = configVO.asString("EP_LINHA");
		this.epProdutos = configVO.asString("EP_PRODUTOS");
		this.epPecas = configVO.asString("EP_PECAS");
		this.epPostos = configVO.asString("EP_POSTOS");
		this.epClientes = configVO.asString("EP_CLIENTES");
		this.epListaBasica = configVO.asString("EP_LISTABASICA");
		this.epPedidosNaoIntegrados = configVO.asString("EP_PEDIDOSNAOINTEG");
		this.epPedidos = configVO.asString("EP_PEDIDOS");
		this.epFaturamento = configVO.asString("EP_FATURAMENTO");
        this.epOrdemServico = configVO.asString("EP_OS");
		this.codTabelaPreco = configVO.asBigDecimal("CODTAB");
		this.modPedido = configVO.asBigDecimal("MODPEDIDO");
		this.modPedidoGarrantia = configVO.asBigDecimal("MODPEDIDOGAR");
        this.modPedGarPlx = configVO.asBigDecimal("MODPEDGARPLX");
		this.codigoTabelaPreco = configVO.asBigDecimal("CODTAB");
		this.codigoTabelaPrecoGarantia = configVO.asBigDecimal("CODTABGAR");
		this.confirmaPedido = configVO.asBoolean("CONFIRMAPEDIDO");
		this.epPrecos = configVO.asString("EP_PRECOS");
		this.debugMode = configVO.asBoolean("DEBUGMODE");
		this.codLocal = configVO.asBigDecimal("CODLOCAL");
	}
}

package tech.skala.action.integration.traycorp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.cbmsoft.modelcore.beans.Endereco;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.StringUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.comercial.CentralFinanceiro;
import br.com.sankhya.modelcore.comercial.CentralItemNota;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.impostos.ImpostosHelpper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ListenerParameters;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import tech.skala.action.integration.traycorp.TrayCorpException.ClienteEcommNaoSalvoException;
import tech.skala.action.integration.traycorp.TrayCorpException.ParceiroEcommNaoLocalizadoException;
import tech.skala.action.integration.traycorp.TrayCorpException.PedidoCabecalhoNaoSalvoException;
import tech.skala.action.integration.traycorp.TrayCorpException.PedidoEcommNaoSalvoException;
import tech.skala.action.integration.traycorp.TrayCorpException.PedidoItemNaoSalvoException;
import tech.skala.action.modules.centrais.StatusNotaEnum;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class TrayCorpImportProcessor {

	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private TrayPedido pedido;
	private BigDecimal codEmp;
	private EcommLigacaoClienteParceiro ligacaoCliente;

	public TrayCorpImportProcessor(TrayPedido pedido) {
		this.pedido = pedido;
	}

	public void processarPedido() throws SkalaModelException {
		getEmpresa();
		incluirCliente();
		incluirPedido();
	}

	private void getEmpresa() throws SkalaModelException {
		try {
			this.codEmp = BigDecimalUtil.getValueOrZero(
					(BigDecimal) MGECoreParameter.getParameter("pltcom", "br.com.platinacsc.comercial.plcodempecomm"));
		} catch (Exception e) {
			SkalaModelException.throwMe("Parâmetro <b>PLCODEMPECOMM</b> não localizado.", e);
		}

		if (codEmp.intValue() == 0)
			throw new SkalaModelException("Empresa não localizada. Favor verificar o parâmetro <b>PLCODEMPECOMM</b>");
	}

	private void incluirCliente() throws SkalaModelException {
		TrayUsuario usuario = pedido.getUsuario();
		TrayEndereco trayEndereco = pedido.getPedidoEndereco().iterator().next();

		BigDecimal idCliente = cadastraParceiroEcomm(usuario, trayEndereco);
		BigDecimal codParc = cadastraParceiro(usuario, trayEndereco, pedido.getPedidoId());
		
		validaCliente(codParc);

		ligarClienteParceiro(idCliente, codParc);

		String msg = String.format("[PEDIDO %d] Cliente incluído: %s", pedido.getPedidoId(), ligacaoCliente);
		logger.info(msg);

	}

	private void validaCliente(BigDecimal codParc) throws SkalaModelException {
		try {
			DynamicVO parceiroVO = JapeFactory.dao(DynamicEntityNames.PARCEIRO).findByPK(codParc);

			EntityUpdate update = new EntityUpdate(DynamicEntityNames.PARCEIRO, parceiroVO);
			if (!"S".equals(parceiroVO.asString(SklFieldName.CLIENTE))) {
				parceiroVO.setProperty(SklFieldName.CLIENTE, "S");
				
				update.setField(SklFieldName.CLIENTE, "S");
				update.fluidUpdate();
			}
			
			/*if(StringUtils.isEmpty(parceiroVO.asString(SklFieldName.GRUPOAUTOR))) {
				parceiroVO.setProperty(SklFieldName.GRUPOAUTOR, "=");
				
				update.setField(SklFieldName.GRUPOAUTOR, "=");
				update.fluidUpdate();
			}*/
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível alterar o parceiro para Cliente", e);
		}

	}

	private void ligarClienteParceiro(BigDecimal idCliente, BigDecimal codParc) throws SkalaModelException {
		ligacaoCliente = new EcommLigacaoClienteParceiro();
		ligacaoCliente.setIdCliente(idCliente);
		ligacaoCliente.setCodParc(codParc);
		ligacaoCliente.persists();
	}

	private BigDecimal cadastraParceiroEcomm(TrayUsuario usuario, TrayEndereco trayEndereco)
			throws ParceiroEcommNaoLocalizadoException, ClienteEcommNaoSalvoException {

		BigDecimal idCliente;
		try {
			idCliente = BigDecimalUtil.getBigDecimal(usuario.getUsuarioId());

			DynamicVO ecommParceiroVO = JapeFactory.dao(TrayCorpUtils.ECOMM_PARCEIRO).findByPK(idCliente);
			if (ecommParceiroVO != null)
				return idCliente;

		} catch (Exception e) {
			throw new TrayCorpException.ParceiroEcommNaoLocalizadoException(e);
		}

		cadastrarParceiroEcomm(usuario, trayEndereco, idCliente);

		return idCliente;
	}

	private void cadastrarParceiroEcomm(TrayUsuario usuario, TrayEndereco trayEndereco, BigDecimal idCliente)
			throws ClienteEcommNaoSalvoException {

		BigDecimal numero;
		int tamanhoComplemento = 0;
		String numeroStr = trayEndereco.getNumero();
		String complemento = trayEndereco.getComplemento();
		if (numeroStr != null) {
			numeroStr = numeroStr.trim();
			try {
				numero = BigDecimalUtil.getValueOrZero(new BigDecimal(numeroStr));
				numeroStr = (BigDecimal.ZERO.compareTo(numero) == 0) ? "SN" : numero.toString();
			} catch (Exception e) {
				complemento = String.format("%s %s", numeroStr, complemento);

				tamanhoComplemento = complemento.length();
				if (tamanhoComplemento > 100)
					complemento = complemento.substring(0, 100);

				numeroStr = "SN";
			}
		}
		
		String endereco = trayEndereco.getEndereco();
		if(endereco.length() > 60)
			endereco = endereco.substring(0, 60);

		trayEndereco.setNumero(numeroStr);
		trayEndereco.setComplemento(complemento);

		try {
			EntityInsert insert = new EntityInsert(TrayCorpUtils.ECOMM_PARCEIRO);
			insert.setField("ID_CLIENTE", idCliente);
			insert.setField("CLIENTE", usuario.getNome());
			insert.setField("CPF", usuario.getCpf());
			insert.setField("EMAIL", usuario.getEmail());
			insert.setField("TELEFONE", usuario.getTelefoneResidencial());
			insert.setField("TELEFONE2", usuario.getTelefoneCelular());
			insert.setField("RAZAO", usuario.getRazaoSocial());
			insert.setField("ENDERECO", endereco);
			insert.setField("NUM", trayEndereco.getNumero());
			insert.setField("CIDADE", trayEndereco.getCidade());
			insert.setField("ESTADO", trayEndereco.getEstado());
			insert.setField("CEP", trayEndereco.getCep());
			insert.setField("COMPLEMENTO", trayEndereco.getComplemento());
			insert.setField("BAIRRO", trayEndereco.getBairro());
			insert.setField("PAIS", trayEndereco.getPais());
			insert.setField("VALOR_PAGTO", BigDecimal.ZERO);

			insert.fluidInsert();
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new TrayCorpException.ClienteEcommNaoSalvoException(e);
		}
	}

	private BigDecimal cadastraParceiro(TrayUsuario usuario, TrayEndereco trayEndereco, long pedidoId)
			throws SkalaModelException {

		boolean isPessoaFisica = "Fisica".equalsIgnoreCase(usuario.getTipoPessoa());
		boolean isPessoaJuridica = "Juridica".equalsIgnoreCase(usuario.getTipoPessoa());

		if (isPessoaFisica && isPessoaJuridica)
			throw new SkalaModelException(
					"Não é permitido que o cliente seja <b>Pessoa Física</b> e <b>Pessoa Jurídica</b> simultaneamente.");

		String cpf = StringUtils.getEmptyAsNull(usuario.getCpf());
		cpf = validaCpf(isPessoaFisica, cpf);

		String cnpj = StringUtils.getEmptyAsNull(usuario.getCnpj());
		cnpj = validaCnpj(isPessoaJuridica, cnpj);

		String cpfCnpj = (isPessoaFisica) ? cpf : cnpj;
		BigDecimal codParc = TrayCorpUtils.buscarParceiroAtivo(cpfCnpj);

		return (codParc != null) ? codParc
				: cadastrarParceiro(usuario, trayEndereco, isPessoaFisica, cpfCnpj, pedidoId);
	}

	private BigDecimal cadastrarParceiro(TrayUsuario usuario, TrayEndereco trayEndereco, boolean isPessoaFisica,
			String cpfCnpj, long pedidoId) throws SkalaModelException {

		Timestamp dataNascimento;
		try {
			dataNascimento = TimeUtils
					.clearTime(TimeUtils.toTimestamp(usuario.getDataNascimento(), "yyyy-MM-dd'T'HH:mm:ss"));
		} catch (Exception e) {
			throw new IllegalArgumentException("Data de Nascimento inválida.", e);
		}

		String tipPessoa = (isPessoaFisica) ? "F" : "J";
		String cliente = null;
		BigDecimal codParc;
		String nomeParc = null;
		String razaoCliente = null;
		String razaoSocial = null;
		BigDecimal codReg = null;
		Endereco endereco = null;
		String nomeCidade = null;
		String cep = null;
		try {
			cliente = StringUtils.getEmptyAsNull(usuario.getNome());
			if (cliente != null) {
				cliente = StringUtils.removePontuacao(cliente);
				cliente = StringUtils.removerCaracteresEspeciais(cliente);
				cliente = cliente.toUpperCase();
			}

			razaoCliente = StringUtils.getEmptyAsNull(usuario.getRazaoSocial());
			if (razaoCliente != null) {
				razaoCliente = StringUtils.removePontuacao(razaoCliente);
				razaoCliente = StringUtils.removerCaracteresEspeciais(razaoCliente);
				razaoCliente = razaoCliente.toUpperCase();
			}

			nomeParc = cliente;
			razaoSocial = (razaoCliente == null) ? cliente : razaoCliente;

			String estado = StringUtils.getEmptyAsNull(trayEndereco.getEstado());
			String cidade = StringUtils.getEmptyAsNull(trayEndereco.getCidade());
			nomeCidade = ("DF".equalsIgnoreCase(estado)) ? "BRASILIA" : cidade;
			cep = StringUtils.getEmptyAsNull(trayEndereco.getCep());

			logger.info(String.format("Nome cidade antes de normalizar: %s", nomeCidade));

			endereco = new Endereco();
			endereco.setNomeEndereco(StringUtils.getEmptyAsNull(trayEndereco.getEndereco()));
			endereco.setNumero(StringUtils.getEmptyAsNull(trayEndereco.getNumero()));
			endereco.setNomeCidade(nomeCidade);
			endereco.setUnidadeFederativa(estado);
			endereco.setCep(cep);
			endereco.setComplemento(StringUtils.getEmptyAsNull(trayEndereco.getComplemento()));
			endereco.setNomeBairro(StringUtils.getEmptyAsNull(trayEndereco.getBairro()));
			endereco.setNomePais(StringUtils.getEmptyAsNull(trayEndereco.getPais()));
			endereco.normalize();
			logger.info(String.format("Nome cidade apos normalizar: %s", endereco.getNomeCidade()));

			endereco.setNomeCidade(replaceAccentuatedChars(endereco.getNomeCidade()));
			logger.info(String.format("Nome cidade depois de chamar o metodo novo: %s", endereco.getNomeCidade()));

			endereco.buildEndereco();

			if (endereco.getCodigoCidade() != null) {
				JapeWrapper cidadeDAO = JapeFactory.dao(DynamicEntityNames.CIDADE);
				DynamicVO cidadeVO = cidadeDAO.findByPK(endereco.getCodigoCidade());

				codReg = cidadeVO.asBigDecimal("CODREG");
			}

		} catch (Exception e) {
			throw new TrayCorpException.EnderecoNaoDefinidoException(e);
		}

		try {
			EntityInsert insert = new EntityInsert(DynamicEntityNames.PARCEIRO);
			insert.setField(SklFieldName.CODVEND, TrayCorpUtils.CODIGO_VENDEDOR_ECOMM);
			insert.setField(SklFieldName.NOMEPARC, nomeParc);
			insert.setField(SklFieldName.RAZAOSOCIAL, razaoSocial);
			insert.setField("TIPPESSOA", tipPessoa);
			insert.setField(SklFieldName.CGC_CPF, cpfCnpj);
			insert.setField(SklFieldName.CLIENTE, "S");
			insert.setField("CODEND", endereco.getCodigoEndereco());
			insert.setField("NUMEND", endereco.getNumero());
			insert.setField("COMPLEMENTO", endereco.getComplemento());
			insert.setField("CODBAI", endereco.getCodigoBairro());

			BigDecimal codCidOld = endereco.getCodigoCidade();
			BigDecimal codCid = codCidOld;
			
			if(codCidOld.longValue() == 6026L)
				codCid = BigDecimal.valueOf(4208);
			 			
			insert.setField("CODCID", codCid);
			insert.setField("CEP", endereco.getCep());
			insert.setField("TELEFONE", StringUtils.getEmptyAsNull(usuario.getTelefoneComercial()));
			insert.setField("FAX", StringUtils.getEmptyAsNull(usuario.getTelefoneCelular()));
			insert.setField("EMAIL", StringUtils.getEmptyAsNull(usuario.getEmail()));
			insert.setField("EMAILNFE", StringUtils.getEmptyAsNull(usuario.getEmail()));
			insert.setField("DTNASC", dataNascimento);
			insert.setField("CLASSIFICMS", "C");
			insert.setField("GRUPOAUTOR", "=");
			insert.setField("CODEMPPREF", codEmp);
			insert.setField("CODTAB", TrayCorpUtils.CODIGO_TABELA_PRECO);
			insert.setField("CODCTACTB", BigDecimal.valueOf(672));
			insert.setField("GRUPOAUTOR", "="); //chamado 66088
			insert.setField("CODTIPPARC", TrayCorpUtils.CODIGO_PERFIL_PARCEIRO);
			if (codReg != null)
				insert.setField(SklFieldName.CODREG, codReg);

			DynamicVO parceiroVO = insert.fluidInsert();
			parceiroVO.setProperty("GRUPOAUTOR", "=");
			
			codParc = parceiroVO.asBigDecimal(SklFieldName.CODPARC);
			
			DynamicVO complementoParceiroVO = JapeFactory.dao(DynamicEntityNames.COMPLEMENTO_PARCEIRO).findByPK(codParc);
			if(complementoParceiroVO == null) {
				insert = new EntityInsert(DynamicEntityNames.COMPLEMENTO_PARCEIRO);
				insert.setField(SklFieldName.CODPARC, codParc);
				insert.setField("SUGTIPNEGSAID", BigDecimal.valueOf(258)); //chamado 66088
				insert.fluidInsert();				
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new TrayCorpException.ParceiroNaoSalvoException(e);
		}

		return codParc;
	}

	private String validaCpf(boolean isPessoaFisica, String cpf) {
		if (isPessoaFisica && cpf != null) {
			String strCpf;
			try {
				strCpf = StringUtils.removerCaracteresEspeciais(StringUtils.removePontuacao(cpf.trim()));
				strCpf = StringUtils.replaceString(
						StringUtils.removePontuacao(StringUtils.removerCaracteresEspeciais(strCpf)), "-", "");
				strCpf = new BigDecimal(strCpf).toPlainString();

				if (strCpf.length() < 11) {
					strCpf = StringUtils.padl(strCpf, 11, '0');
				}
				cpf = StringUtils.getEmptyAsNull(strCpf);
				if (cpf == null)
					throw new IllegalArgumentException("CPF não informado.");
			} catch (Exception e) {
				throw new IllegalArgumentException("CPF inválido.", e);
			}

		}
		return cpf;
	}

	private String validaCnpj(boolean isPessoaJuridica, String cnpj) {
		if (isPessoaJuridica && cnpj != null) {
			cnpj = StringUtils.removePontuacao(cnpj.trim());
			String strCnpj;
			try {
				strCnpj = StringUtils.removerCaracteresEspeciais(StringUtils.removePontuacao(cnpj.trim()));
				strCnpj = StringUtils.replaceString(
						StringUtils.removePontuacao(StringUtils.removerCaracteresEspeciais(strCnpj)), "-", "");
				strCnpj = new BigDecimal(strCnpj).toPlainString();

				if (strCnpj.length() < 14) {
					strCnpj = StringUtils.padl(strCnpj, 14, '0');
				}
				cnpj = StringUtils.getEmptyAsNull(strCnpj);
				if (cnpj == null)
					throw new IllegalArgumentException("CNPJ não informado.");
			} catch (Exception e) {
				throw new IllegalArgumentException("CNPJ inválido.", e);
			}

		}
		return cnpj;
	}

	private void incluirPedido() throws SkalaModelException {
		incluirPedidoEcomm();
		incluirPedidoDeVenda();
	}

	private void incluirPedidoEcomm() throws PedidoEcommNaoSalvoException {

		TrayUsuario usuario = pedido.getUsuario();
		Collection<TrayItem> itens = pedido.getItens();
		Collection<TrayPagamento> pagamentos = pedido.getPagamento();
		Collection<TrayObservacao> observacoes = pedido.getObservacao();
		TrayFrete frete = pedido.getFrete();

		String formaPagamento = "Boleto";
		if (!pagamentos.isEmpty()) {
			TrayPagamento primeiroPagamento = pagamentos.iterator().next();
			long pagamentoId = primeiroPagamento.getFormaPagamentoId();
			formaPagamento = (pagamentoId == 3403 ? "Boleto" : "Cartão de Crédito");
		}

		StringBuilder sbObservacoes = new StringBuilder();
		for (TrayObservacao observacao : observacoes)
			sbObservacoes.append(observacao.getObservacao());

		String observacaoPedido = sbObservacoes.toString();

		BigDecimal pedidoId = new BigDecimal(pedido.getPedidoId());

		String msg = String.format("[PEDIDO %s] Incluindo pedido e-commerce...", pedidoId);
		logger.info(msg);

		Timestamp dataPedido;
		try {
			dataPedido = TimeUtils.clearTime(TimeUtils.toTimestamp(pedido.getData(), "yyyy-MM-dd'T'HH:mm:ss"));
		} catch (Exception e) {
			throw new IllegalArgumentException("Formato inválido para Data do Pedido.", e);
		}
		String horaPedido = pedido.getData().substring(11, 16);

		try {

			persistsCabecalhoEcomm(usuario, frete, formaPagamento, observacaoPedido, pedidoId, dataPedido, horaPedido);
			persistsItensEcomm(itens, pedidoId);

		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new TrayCorpException.PedidoEcommNaoSalvoException(e);
		}

	}

	private void persistsCabecalhoEcomm(TrayUsuario usuario, TrayFrete frete, String formaPagamento,
			String observacaoPedido, BigDecimal pedidoId, Timestamp dataPedido, String horaPedido)
			throws SkalaModelException {

		EntityInsert insert = new EntityInsert(TrayCorpUtils.ECOMM_CABECALHO);
		insert.setField("ID_CLIENTE", new BigDecimal(usuario.getUsuarioId()));
		insert.setField("PEDIDO", pedidoId);
		insert.setField("DATA_PEDIDOS", dataPedido);
		insert.setField("HORA", horaPedido);
		insert.setField("SUBTOTAL_DE_PRODUTOS", BigDecimal.valueOf(pedido.getValorSubTotalSemDescontos()));
		insert.setField("IMPOSTOS", BigDecimal.ZERO);
		insert.setField("LOCAL_VENDA", "Tray Corp");
		insert.setField("FRETE", frete.getFreteContrato());
		insert.setField("VALOR_FRETE", new BigDecimal(Double.toString(frete.getValorFreteCliente())));
		insert.setField("PAGAMENTO1", BigDecimal.valueOf(pedido.getValorTotalPedido()));
		insert.setField("FORMA_PAGAMENTO_ESCOLHIDA", formaPagamento);
		insert.setField("OBS_CLIENTE", observacaoPedido);
		insert.setField(SklFieldName.STATUS, "A Enviar");
		insert.setField("DESCONTO", BigDecimal.ZERO);
		insert.setField("ACRESCIMO_FORMA", BigDecimal.ZERO);
		insert.setField(SklFieldName.CLIENTE, StringUtils.getEmptyAsNull(usuario.getNome()));

		insert.fluidInsert();
	}

	private void persistsItensEcomm(Collection<TrayItem> itens, BigDecimal pedidoId) throws InsertNotPossibleException {

		for (TrayItem trayItem : itens) {
			EntityInsert insertNew = new EntityInsert(TrayCorpUtils.ECOMM_ITEM);
			insertNew.setField("PEDIDO", pedidoId);
			insertNew.setField("ID_PRODUTO", BigDecimalUtil.getBigDecimal(trayItem.getProdutoVarianteId()));
			insertNew.setField("PRODUTO", trayItem.getNome());
			insertNew.setField("CUSTO", BigDecimalUtil.getBigDecimal(trayItem.getPrecoCusto()));
			insertNew.setField("PRECO", BigDecimalUtil.getBigDecimal(trayItem.getPrecoVenda()));
			insertNew.setField("QUANTIDADE", BigDecimalUtil.getBigDecimal(trayItem.getQuantidade()));
			insertNew.setField(SklFieldName.CODPROD, BigDecimalUtil.getBigDecimal(trayItem.getSku()));
			insertNew.setField("REFERENCIA", trayItem.getSku());
			insertNew.fluidInsert();
		}

	}

	private void incluirPedidoDeVenda() throws SkalaModelException {

		try {
			BigDecimal nuNota = persistsCabecalhoVenda();
			persistsItensVenda(nuNota);

			ImpostosHelpper impHelpper = new ImpostosHelpper();
			impHelpper.totalizarNota(nuNota);
			impHelpper.salvarNota();

			CentralFinanceiro centralFinanceiro = new CentralFinanceiro();
			centralFinanceiro.inicializaNota(nuNota);
			centralFinanceiro.refazerFinanceiro();
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new TrayCorpException.PedidoCabecalhoNaoSalvoException(e);
		}

	}

	private BigDecimal persistsCabecalhoVenda() throws PedidoCabecalhoNaoSalvoException {
		JapeSessionContext.putProperty(ListenerParameters.CENTRAIS, Boolean.TRUE);

		BigDecimal vlrDesc = calculaDesconto();

		EntityInsert insert = new EntityInsert(DynamicEntityNames.CABECALHO_NOTA);
		insert.setField(SklFieldName.CODEMP, codEmp);
		insert.setField(SklFieldName.NUMNOTA, BigDecimal.ZERO);
		insert.setField(SklFieldName.CODCENCUS, TrayCorpUtils.CODCENCUS_ECOMM);
		insert.setField("CODNAT", new BigDecimal(1010101)); // TODO criar parametro
		insert.setField(SklFieldName.CODTIPOPER, new BigDecimal(3122)); // TODO criar parametro
		insert.setField("CODTIPVENDA", new BigDecimal(258)); // TODO criar parametro
		insert.setField(SklFieldName.CODVEND, TrayCorpUtils.CODIGO_VENDEDOR_ECOMM);

		insert.setField("TIPFRETE", "S");
		insert.setField("CIF_FOB", "C");
		insert.setField(SklFieldName.CODPARCTRANSP, BigDecimal.ZERO); // TODO criar parametro
		insert.setField("VLRFRETE", new BigDecimal(Double.toString(pedido.getFrete().getValorFreteCliente())));

		// Calculamos qual a data de vencimento do frete...
		Timestamp dtAtual = TimeUtils.clearTime(TimeUtils.getNow());
		Timestamp dtVencFrete = new Timestamp(TimeUtils.add(dtAtual.getTime(), 30, Calendar.DAY_OF_MONTH));
		insert.setField("VENCFRETE", dtVencFrete);
		insert.setField("AD_TIPFRETEEC", pedido.getFrete().getFreteContrato());

		if (vlrDesc != null && vlrDesc.doubleValue() > 0D)
			insert.setField("VLRDESCTOT", vlrDesc);

		insert.setField(SklFieldName.CODPARC, ligacaoCliente.getCodParc());

		BigDecimal pedidoId = new BigDecimal(pedido.getPedidoId());

		String msg = String.format("[PEDIDO %s] Incluindo pedido de venda...", pedidoId);
		logger.info(msg);

		insert.setField("NUMPEDIDO2", pedidoId.toString());
		insert.setField(SklFieldName.STATUSNOTA, StatusNotaEnum.PENDENTE.getValue());

		StringBuilder sbObservacoes = new StringBuilder();
		for (TrayObservacao observacao : pedido.getObservacao())
			sbObservacoes.append(observacao.getObservacao());

		String observacaoPedido = sbObservacoes.toString();
		insert.setField(SklFieldName.OBSERVACAO, observacaoPedido);

		BigDecimal nuNota = null;
		try {
			Timestamp dataPedido = TimeUtils
					.clearTime(TimeUtils.toTimestamp(pedido.getData(), "yyyy-MM-dd'T'HH:mm:ss"));
			insert.setField(SklFieldName.DTNEG, dataPedido);

			DynamicVO cabecalhoVO = insert.fluidInsert();
			nuNota = cabecalhoVO.asBigDecimal(SklFieldName.NUNOTA);
		} catch (Exception e) {
			throw new TrayCorpException.PedidoCabecalhoNaoSalvoException(e);
		}

		return nuNota;
	}

	private BigDecimal calculaDesconto() {
		String cupomDesconto = StringUtils.getEmptyAsNull(pedido.getCupomDesconto());
		BigDecimal vlrCupomDesc = null;

		if (cupomDesconto != null && cupomDesconto.contains("/")) {
			String[] tokens = cupomDesconto.split("/");

			for (String token : tokens) {
				try {
					if (vlrCupomDesc == null) {
						vlrCupomDesc = BigDecimalUtil.getBigDecimal(token);
					} else {
						vlrCupomDesc = vlrCupomDesc.add(BigDecimalUtil.getBigDecimal(token), BigDecimalUtil.MATH_CTX);
					}
				} catch (Exception e) {
					vlrCupomDesc = BigDecimal.ZERO;
				}
			}
		}

		BigDecimal vlrDescCalc = vlrCupomDesc;
		BigDecimal desconto = BigDecimalUtil.getValueOrZero(BigDecimalUtil.getBigDecimal(pedido.getValorDesconto()));
		if (desconto != null && desconto.doubleValue() > 0D)
			vlrDescCalc = (vlrDescCalc == null) ? desconto : vlrDescCalc.add(desconto);

		return vlrDescCalc;
	}

	protected void persistsItensVenda(BigDecimal nuNota) throws PedidoItemNaoSalvoException {

		try {
			CentralItemNota centralItem = new CentralItemNota();
			String topAtualEst = "R";

			ItemPedido item = null;
			for (TrayItem trayItem : pedido.getItens()) {

				item = new ItemPedido();
				item.setCodProd(BigDecimalUtil.getBigDecimal(trayItem.getSku()));
				item.setQtdNeg(BigDecimalUtil.getBigDecimal(trayItem.getQuantidade()));

				CentralItemNota.ParamsInicializacaoProduto p = new CentralItemNota.ParamsInicializacaoProduto();
				p.codProd = item.getCodProd();
				p.nuNota = nuNota;
				p.forcarCalcPreco = false;

				ComercialUtils.PrecoUnitarioInfo pui = centralItem.inicializaProduto(p);
				pui.setVlrUnit(BigDecimalUtil.getBigDecimal(trayItem.getPrecoVenda()));

				DynamicVO itemVO = (DynamicVO) EntityFacadeFactory.getDWFFacade()
						.getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);
				itemVO.setProperty(SklFieldName.NUNOTA, nuNota);
				itemVO.setProperty(SklFieldName.CODLOCALORIG, new BigDecimal(200600));
				itemVO.setProperty(SklFieldName.CODPROD, item.getCodProd());
				itemVO.setProperty(SklFieldName.QTDNEG, BigDecimalUtil.getRounded(item.getQtdNeg(),
						BigDecimalUtil.getValueOrZero(pui.getDecQtd()).intValue()));
				itemVO.setProperty(SklFieldName.OBSERVACAO, item.getObs());
				itemVO.setProperty(SklFieldName.CODVOL, pui.getCodVol());
				itemVO.setProperty(SklFieldName.VLRUNIT, pui.getVlrUnit());
				itemVO.setProperty("CUSTO", pui.getCusto());
				itemVO.setProperty("NUTAB", pui.getNuTab());
				itemVO.setProperty("PRECOBASE", pui.getPrecoBase());
				itemVO.setProperty("VLRCUS", pui.getVlrCus());
				itemVO.setProperty("PERCDESC", pui.getPercDesc());
				itemVO.setProperty(SklFieldName.USOPROD, pui.getUsoProd());
				itemVO.setProperty(SklFieldName.VLRTOT, BigDecimalUtil
						.getRounded(pui.getVlrUnit().multiply(itemVO.asBigDecimal(SklFieldName.QTDNEG)), 2));

				if ("B".equals(topAtualEst)) {
					itemVO.setProperty("ATUALESTOQUE", BigDecimal.valueOf(-1L));
				} else {
					itemVO.setProperty("ATUALESTOQUE",
							("R-E".indexOf(topAtualEst) > -1) ? BigDecimal.ONE : BigDecimal.ZERO);
				}

				itemVO.setProperty("RESERVA", "R".equals(topAtualEst) ? "S" : "N");

				centralItem.recalcularValores(SklFieldName.QTDNEG,
						itemVO.asBigDecimalOrZero(SklFieldName.QTDNEG).toString(), itemVO, nuNota);
				EntityFacadeFactory.getDWFFacade().createEntity(DynamicEntityNames.ITEM_NOTA, (EntityVO) itemVO);
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new TrayCorpException.PedidoItemNaoSalvoException(e);
		}
	}

	public void atualizarSituacaoPedido() throws SkalaModelException {

		BigDecimal pedidoId = BigDecimal.valueOf(pedido.getPedidoId());
		TrayCorpConnector.atualizaPedidoParaEmPreparacao(pedidoId);

		String msg = String.format("[PEDIDO %s] Status atualizado.", pedidoId);
		logger.info(msg);

	}

	private String replaceAccentuatedChars(String string) {
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		return string.replaceAll("[^\\p{ASCII}]", "");
	}
}

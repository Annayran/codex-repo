package tech.skala.action.integration.traycorp;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class TrayCorpUtils {
	
	public static final String TOKEN = "Basic plati-e1302ce6-f91e-434b-b28f-0630a32596ce";
	
	public static final String ENDPOINT = "https://api.fbits.net";
	public static final String GET_PEDIDOS = "/pedidos";
	public static final String GET_PEDIDO_ID = "/pedidos/{pedidoId}";
	public static final String PUT_STATUS = "/pedidos/{pedidoId}/status";
	public static final String POST_COMPLETE = "/pedidos/complete";
	public static final String GET_PRODUTOS = "/produtos";
	public static final String PUT_PRODUTOS_ESTOQUES = "/produtos/estoques";
	public static final String GET_USUARIOS = "/usuarios";
	public static final String GET_USUARIO_CPF = "/usuarios/cpf/{cpf}";

	public static final String ECOMM_PARCEIRO = "PLTGFPAREC";
	public static final String ECOMM_CABECALHO = "PLTGFCABEC";
	public static final String ECOMM_LIGACAO_PARCEIRO = "PLTGFLPPEC";
	public static final String ECOMM_ITEM = "PLTGFITEEC";

	public static final BigDecimal SITUACAO_EM_PREPARACAO = BigDecimal.valueOf(11);
	public static final BigDecimal CODIGO_VENDEDOR_ECOMM = BigDecimal.valueOf(94);
	public static final BigDecimal CODIGO_TABELA_PRECO = BigDecimal.valueOf(72);
	public static final BigDecimal CODCENCUS_ECOMM = BigDecimal.valueOf(300403);
	public static final BigDecimal CODIGO_PERFIL_PARCEIRO = BigDecimal.valueOf(4010000);


	private TrayCorpUtils() {

	}

	public static BigDecimal buscarParceiroAtivo(String cpfCnpj) {
		try {
			EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();

			FinderWrapper finder = new FinderWrapper(DynamicEntityNames.PARCEIRO,
					"this.CGC_CPF = ? AND this.ATIVO = 'S'", new Object[] { cpfCnpj });
			finder.setOrderBy("this.CODPARC ASC");

			Collection<DynamicVO> parceirosVO = dwfEntityFacade.findByDynamicFinderAsVO(finder);
			if (parceirosVO.isEmpty()) {
				String msg = String.format("CPF/CNPJ: %s. Parceiro n�o localizado.", cpfCnpj);
				throw new IllegalArgumentException(msg);
			}

			return (parceirosVO.isEmpty()) ? null : parceirosVO.iterator().next().asBigDecimal("CODPARC");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}

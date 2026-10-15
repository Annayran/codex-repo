package tech.skala.action.modules.wms.endereco;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class EnderecoArmazenagemDAO {

	private EnderecoArmazenagemDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static EnderecoArmazenagem getEnderecoPelaSeparacao(BigDecimal nuSeparacao) throws SkalaModelException {
		SessionHandle hnd = null;

		EnderecoArmazenagem enderecoArmazenagem = null;
		try {
			hnd = JapeSession.open();

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.SEPARACAO);
			DynamicVO vo = dao.findByPK(nuSeparacao);

			BigDecimal codEndDoca = vo.asBigDecimal("CODENDDOCA");

			enderecoArmazenagem = new EnderecoArmazenagem();
			enderecoArmazenagem.setCodEnd(codEndDoca);
		} catch (Exception e) {
			String msg = String.format("[NUSEPARACAO %s]Não foi possível localizar o Endereço da Separação",
					nuSeparacao);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

		return enderecoArmazenagem;
	}

	public static List<BigDecimal> getEnderecosItensTarefa(BigDecimal nuTarefa) throws SkalaModelException {
		SessionHandle hnd = null;

		List<BigDecimal> enderecos = new ArrayList<>();
		try {
			hnd = JapeSession.open();

			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ITEM_TAREFA);
			Collection<DynamicVO> vos = dao.find("this.NUTAREFA = ?", nuTarefa);

			for (DynamicVO vo : vos)
				enderecos.add(vo.asBigDecimalOrZero("CODENDORIGEM"));

		} catch (Exception e) {
			String msg = String.format("[NUTAREFA %s]Não foi possível localizar o Endereço dos itens da Tarefa", nuTarefa);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

		return enderecos;
	}

}

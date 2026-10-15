package tech.skala.action.integration.tangerino;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.modules.pessoas.funcionario.Funcao;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * Classe utilitaria que busca registros no banco de dados.
 * 
 * @author cassiomenezes
 * @since 2023-01-23
 * 
 */
public class TangerinoFinder {

	private TangerinoFinder() {
		throw new IllegalStateException("Utility class");
	}

	public static List<Funcao> carregaFuncoesEnviar() throws RecordNotFoundException {
		SessionHandle hnd = null;
		List<Funcao> funcoes = new LinkedList<>();

		try {
			hnd = JapeSession.open();
			
			Funcao funcao;
			JapeWrapper dao = JapeFactory.dao("AD_PLTFPFCOITG");
			Collection<DynamicVO> vos = dao.find("this.DHSINC IS NULL");
			for (DynamicVO vo:vos) {
				funcao = new Funcao();
				funcao.loadByPk(vo.asBigDecimal("CODFUNCAO"));
				
				funcoes.add(funcao);
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		} finally {
			JapeSession.close(hnd);
		}
		
		return funcoes;
	}
}

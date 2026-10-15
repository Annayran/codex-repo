package tech.skala.modules.suprimentos.recebimento;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Collection;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.PlatformService;
import br.com.sankhya.modelcore.PlatformServiceFactory;
import br.com.sankhya.modelcore.comercial.AtributosRegras;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ParceiroHellper;
import tech.skala.core.beans.suprimento.recebimento.Conferencia;
import tech.skala.core.beans.suprimento.recebimento.ConferenciaItem;
import tech.skala.core.beans.suprimento.recebimento.ConferenciaSituacaoEnum;
import tech.skala.core.beans.suprimento.recebimento.Recebimento;
import tech.skala.core.beans.suprimento.recebimento.RecebimentoConferencia;
import tech.skala.core.beans.suprimento.recebimento.RecebimentoSituacaoEnum;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InvalidFieldException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.util.string.SkalaStringUtils;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaExceptions.ConferenciaNaoProcessadaException;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaExceptions.DataEntradaSaidaNaoAtualizadaException;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaExceptions.LancamentoSemItensException;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaExceptions.RecebimentoNaoFinalizadoException;

/**
 * A classe {@code RecebimentoMercadoriaFinder} prove recursos para busca de informacoes relacionadas a 
 * uma {@link RecebimentoMercadoria}.
 * 
 * <p>
 * A classe {@code RecebimentoMercadoriaFinder} prove metodos diversos especializados em localizar uma {@link RecebimentoMercadoria} 
 * (ou varias) segundo os mais diversos criterios, sendo uma classe especializada para este fim.
 * </p>
 * 
 * @author marcosdamasceno
 * @since 2022-09-02
 *
 */
public class RecebimentoMercadoriaFinder {

	/**
	 * O método {@code buscaRecebimento} é responsável por buscar um recebimento
	 * através do numero da nota e o cnpj do parceiro.
	 * 
	 * @param cnpj Paramêtro que guarda a informação do parceiro.
	 * @param numNota Paramêtro que guarda a informação da nota fiscal.
	 * @return recebimento Retorno que quarda as informações do recebimento.
	 * @throws SkalaModelException 
	 */
	public Recebimento buscaRecebimento(String cnpj, BigDecimal numNota) throws SkalaModelException {
		JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		Collection<DynamicVO> notasVO = null;

		try {
			ParceiroHellper parceiro = new ParceiroHellper();
			BigDecimal codParc = parceiro.getParceiroFromCGC(cnpj);

			notasVO = cabecalhoNotaDAO.find("this.TIPMOV IN ('C') AND this.CODPARC = ? AND this.NUMNOTA = ?", codParc, numNota);
			
			if (notasVO.isEmpty()) 
				throw new RecebimentoMercadoriaExceptions.NotaNaoEncontradaException(numNota, cnpj);
			

			if (notasVO.size() > 1) 
				throw new RecebimentoMercadoriaExceptions.NumNotaCnpjDuplicidadeException(numNota, cnpj);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RecebimentoMercadoriaExceptions.NotaNaoEncontradaException(numNota, cnpj);
		}

		return getRecebimento(notasVO);
	}
	
	/**
	 * O método {@code buscaRecebimento} é responsável por buscar um recebimento
	 * através do numero da nota e o cnpj do parceiro.
	 * 
	 * @param chaveNfe Paramêtro que guarda a informação da nota fiscal
	 * @return recebimento Retorno que quarda as informações do recebimento.
	 * @throws SkalaModelException 
	 */
	public Recebimento buscaRecebimento(String chaveNfe) throws SkalaModelException {
		JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		Collection<DynamicVO> notasVO = null;
		try {
			notasVO = cabecalhoNotaDAO.find("this.CHAVENFE = ?", chaveNfe);
		} catch (Exception e) {
			throw new RecebimentoMercadoriaExceptions.ChaveNfeNaoEncontradaException(e, SkalaStringUtils.getNumeroNotaFromChaveNfe(chaveNfe));
		}

		if (notasVO == null || notasVO.isEmpty())
			throw new RecebimentoMercadoriaExceptions.ChaveNfeNaoEncontradaException(SkalaStringUtils.getNumeroNotaFromChaveNfe(chaveNfe));
		
		if (notasVO.size() > 1)
			throw new RecebimentoMercadoriaExceptions.ChaveNfeDuplicidadeException(SkalaStringUtils.getNumeroNotaFromChaveNfe(chaveNfe));
		
		return getRecebimento(notasVO);
	}

	private Recebimento getRecebimento(Collection<DynamicVO> notasVO) throws SkalaModelException {

		if (notasVO == null || notasVO.isEmpty())
			return null;

		if (notasVO.size() > 1) 
			throw new RecebimentoMercadoriaExceptions.ChaveNfeDuplicidadeException("");
	
		DynamicVO notaVO = notasVO.iterator().next();
		BigDecimal nuNota = notaVO.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal codParc = notaVO.asBigDecimal(SklFieldName.CODPARC);
		BigDecimal numNota = notaVO.asBigDecimal(SklFieldName.NUMNOTA);
		String cnpj = notaVO.asString("Parceiro.CGC_CPF");
		Recebimento recebimento = buscaRecebimentoPorNuNota(nuNota);
		
		if (recebimento == null) {
			RecebimentoMercadoriaProcessor processor = new RecebimentoMercadoriaProcessor();
			recebimento = processor.buildRecebimento(nuNota, codParc, numNota, cnpj);
		}

		return recebimento;
	}
	
	/**
	 * O método {@code buscaRecebimentoPorNuNota} é responsável por buscar um recebimento
	 * através do numero único da nota.
	 * 
	 * @param nuNota Paramêtro que guarda a informação da número único da nota fiscal.
	 * @return recebimento Retorno que quarda as informações do recebimento.
	 * @throws SkalaModelException 
	 */
	private Recebimento buscaRecebimentoPorNuNota(final BigDecimal nuNota) throws SkalaModelException {
		Recebimento recebimento = null;
		JapeWrapper recebimentoDAO = JapeFactory.dao(SklEntityName.MOBILE_RECEBIMENTO);

		Collection<DynamicVO> recebimentosVO = null;
		try {
			recebimentosVO = recebimentoDAO.find("this.NUNOTA = ?", nuNota);
			if (recebimentosVO.size() > 1)
				throw new RecebimentoMercadoriaExceptions.RecebimentoDuplicadoException(nuNota);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		for (DynamicVO recebimentoVO : recebimentosVO) {
			try {
				recebimento = new Recebimento();
				recebimento.loadByVO(recebimentoVO);
				break;
			} catch (Exception e) {
				throw new RecebimentoMercadoriaExceptions.RecebimentoNaoEncontradoException();
			}
		}

		return recebimento;
	}
}

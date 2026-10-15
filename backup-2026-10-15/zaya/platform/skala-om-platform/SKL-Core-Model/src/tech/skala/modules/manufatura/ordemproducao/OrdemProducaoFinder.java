package tech.skala.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.sql.ResultSet;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.NullPrimaryKeyException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.manufatura.atividade.InstanciaAtividadeFinder;
import tech.skala.modules.manufatura.workcenter.WorkCenter;

/**
 * A classe {@code OrdemProducaoFinder} prove recursos para busca de informacoes relacionadas a 
 * uma {@link OrdemProducao}.
 * 
 * <p>
 * A classe {@code OrdemProducaoFinder} prove metodos diversos especializados em localizar uma {@link OrdemProducao} 
 * (ou varias) segundo os mais diversos criterios, sendo uma classe especializada para este fim.
 * </p>
 * 
 * @author marcosdamasceno
 * @author cassiomenezes
 * @author hiagoalexandre
 * @author odilon
 * @since 2022-07-15
 *
 */
public class OrdemProducaoFinder {
	private static final String ENTITY_NAME = DynamicEntityNames.CABECALHO_INSTANCIA_PROCESSO;

	private BigDecimal						codWcp;

	public OrdemProducaoFinder(BigDecimal codWcp) {
		this.codWcp = codWcp;
	}

	/**
	 * O metodo {@code buscaOrdemProducaoPorLinha} retorna uma Ordem de Producao para uma Linha de Producao.
	 * 
	 * <p>
	 * Este metodo deve retornar <b>uma unica</b> Ordem de Producao nao finalizada de uma Linha de Producao (que 
	 * equivale a um Centro de Trabalho).<br>
	 * Esta OP pode estar com situacao <b>"Criada"</b> ou <b>"Em andamento"</b>, sem nenhum apontamento ou com 
	 * apontamentos parciais.
	 * </p>
	 * 
	 * @param codWcp Codigo valido de uma Linha de Producao (Centro de Trabalho)
	 * @return Uma Ordem de Producao nao finalizada.
	 * @throws SkalaModelException quando ocorrer algum problema na inicializacao da Ordem de Producao.
	 * 
	 */
	public OrdemProducao buscaOrdemProducaoPorLinha() throws SkalaModelException {
		
		validarWorkCenter();
		return getOrdemProducaoPorLinha();
		
	}
	
	private void validarWorkCenter() throws NullPrimaryKeyException, RecordNotFoundException {

		if (codWcp == null)
			throw new DataDictionaryExceptions.NullPrimaryKeyException();
		
		WorkCenter workCenter = new WorkCenter();
		workCenter.loadByPk(codWcp);
		
	}

	private OrdemProducao getOrdemProducaoPorLinha() throws RecordNotFoundException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;
		
		OrdemProducao ordemProducao = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "queOrdemProducaoPorLinha.sql");
			sql.setNamedParameter("CODWCP", codWcp);
			
			rs = sql.executeQuery();

			while (rs.next()) {
				ordemProducao = new OrdemProducao(rs.getBigDecimal(SklFieldName.IDIPROC));
				ordemProducao.loadByResultSet(rs);
				ordemProducao.loadEtiquetasCaixa();
				
				WorkCenter workCenter = new WorkCenter();
				workCenter.loadByPk(codWcp);
				ordemProducao.setWorkCenter(workCenter);

				ordemProducao.setAtividades(InstanciaAtividadeFinder.buscarAtividadesPorIdiProc(ordemProducao.getIdiProc()));
			}
				
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
		
		return ordemProducao;
	}
	
	/**
	 * O metodo {@code buscaOrdemProducaoPorCodBarra} retorna uma Ordem de Producao
	 * para um Código de Barras.
	 * 
	 * <p>
	 * Este metodo deve retornar <b>uma unica</b> Ordem de Producao informada no
	 * Código de Barras da etiqueta.<br>
	 * Esta OP pode estar com situacao <b>"Criada"</b> ou <b>"Em andamento"</b>, sem
	 * nenhum apontamento ou com apontamentos parciais.
	 * </p>
	 * 
	 * @param codBarra Codigo de Barras presente na Etiqueta
	 * @return Uma Ordem de Producao nao finalizada.
	 * @throws RecordNotFoundException quando ocorrer algum problema na
	 * inicializacao da Ordem de Producao.
	 * 
	 */
	public static OrdemProducao buscaOrdemProducaoPorCodBarra(String codBarra) throws RecordNotFoundException {

		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		OrdemProducao ordemProducao = null;
		BigDecimal idiProc;
		try {
			idiProc = getIdiProcFromCodBarra(codBarra);

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(OrdemProducaoFinder.class, "queOrdemProducao.sql");
			sql.setNamedParameter("IDIPROC", idiProc);

			rs = sql.executeQuery();

			while (rs.next()) {
				ordemProducao = new OrdemProducao(rs.getBigDecimal(SklFieldName.IDIPROC));
				ordemProducao.loadByResultSet(rs);
				ordemProducao.loadEtiquetasCaixa();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}

		return ordemProducao;
	}
	
	/**
	 * Metódo responsável por retirar a informação de idiProc (que é o identificador
	 * único da tabela {@code TPRIPROC}) código de Barras da etiqueta.
	 * 
	 * @param codBarra Código de barras da etiqueta
	 * @return idiProc
	 */
	private static final BigDecimal getIdiProcFromCodBarra(String codBarra) {
		if (codBarra == null)
			return null;
		
		String[] tokens = codBarra.split("[.]");
		return BigDecimalUtil.getBigDecimal(tokens[0]);
	}

	
	public static String findNroLote(BigDecimal idiProc) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo;
		try {
			vo = dao.findByPK(idiProc);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		return vo.asString(SklFieldName.NROLOTE);
	}
	
	public static String findStatusProc(BigDecimal idiProc) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo;
		try {
			vo = dao.findByPK(idiProc);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		return vo.asString(SklFieldName.STATUSPROC);
	}
}

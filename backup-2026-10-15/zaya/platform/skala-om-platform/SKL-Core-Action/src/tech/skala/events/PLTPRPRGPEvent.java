package tech.skala.events;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.manufatura.processoprodutivo.ProcessoProdutivoFinder;
import tech.skala.action.modules.manufatura.processoprodutivo.ProcessoProdutivoResumo;
import tech.skala.action.modules.manufatura.produtoacabado.ProdutoAcabadoFinder;
import tech.skala.action.modules.manufatura.workcenter.WorkCenter;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class PLTPRPRGPEvent implements EventoProgramavelJava {
	final private Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private String msg;

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		final DynamicVO vo = (DynamicVO) event.getVo();

		if (BigDecimalUtil.getValueOrZero(vo.asBigDecimal("CODPROD")).compareTo(BigDecimal.ZERO) > 0
				&& BigDecimalUtil.getValueOrZero(vo.asBigDecimal("CODPRC")).compareTo(BigDecimal.ZERO) > 0) {
			this.loadMateriasPrimas(vo);
			
			setInformacoesProcessoProdutivo(vo);
		}
	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		final DynamicVO vo = (DynamicVO) event.getVo();
		final ModifingFields modifingFields = event.getModifingFields();

		if (modifingFields.isModifingAny("CODPRC,CODPROD,QTDLOTES")) {
			this.clearMateriaPrima(BigDecimalUtil.getValueOrZero(vo.asBigDecimal("NUPRGP")));
			this.loadMateriasPrimas(vo);
		}
	}

	@Override
	public void beforeCommit(TransactionContext event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		final DynamicVO vo = (DynamicVO) event.getVo();

		this.clearMateriaPrima(BigDecimalUtil.getValueOrZero(vo.asBigDecimal("NUPRGP")));
	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		setInformacoesProcessoProdutivo(vo);

		if (vo.asBigDecimal(SklFieldName.QTDCAPACIDADEPAD) == null)
			setInformacoesCapacidadePadrao(vo);
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		ModifingFields modifingFields = event.getModifingFields();

		if (modifingFields.isModifingAny("CODWCP,CODPROD,CODPRC")) {
			setInformacoesProcessoProdutivo(vo);
			setInformacoesCapacidadePadrao(vo);
		}

	}

	private void setInformacoesCapacidadePadrao(DynamicVO vo) throws RecordNotFoundException {
		BigDecimal codWcp = vo.asBigDecimal(SklFieldName.CODWCP);

		WorkCenter workCenter = new WorkCenter();
		workCenter.loadByPk(codWcp);

		BigDecimal qtdCapacidadePad = workCenter.getQtdCapacidadePad();
		vo.setProperty(SklFieldName.QTDCAPACIDADEPAD, qtdCapacidadePad);
	}

	private void setInformacoesProcessoProdutivo(DynamicVO vo) throws SkalaModelException {

		BigDecimal codWcp = vo.asBigDecimal(SklFieldName.CODWCP);
		BigDecimal codPrc = vo.asBigDecimal("CODPRC");
		BigDecimal codProd = vo.asBigDecimal(SklFieldName.CODPROD);
		BigDecimal codPlp = vo.asBigDecimal(SklFieldName.CODPLP);

		if (codPrc == null) {
			
			ProdutoAcabadoFinder finder = new ProdutoAcabadoFinder(codProd, codPlp);
	
			ProcessoProdutivoResumo processoProdutivoResumo = ProcessoProdutivoFinder
					.buscaProcessoProdutivoPorCodWcp(codWcp);
	
			BigDecimal idProc = (processoProdutivoResumo == null ? finder.findProcessoProdutivoPadrao()
					: processoProdutivoResumo.getIdProc());
	
			codPrc = (processoProdutivoResumo == null ? ProcessoProdutivoFinder.buscaCodPrcPorIdProc(idProc)
					: processoProdutivoResumo.getCodPrc());
	
			vo.setProperty(SklFieldName.IDPROC, idProc);
			vo.setProperty("CODPRC", codPrc);
		
		} else {
			ProcessoProdutivoResumo processoProdutivo = ProcessoProdutivoFinder.buscaProcessoProdutivoPorCodPrc(codPrc);
			
			if (processoProdutivo != null) {
				vo.setProperty(SklFieldName.IDPROC, processoProdutivo.getIdProc());
			}
		}
	}

	private void loadMateriasPrimas(final DynamicVO vo) throws SkalaModelException {
		JdbcWrapper jdbc = null;
		JapeSession.SessionHandle hnd = null;
		ResultSet rs = null;
		NativeSql sql = null;
		final BigDecimal numeroProgramacao = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("NUPRGP"));
		BigDecimal codProdMateriaPrima = BigDecimal.ZERO;
		String codVolMateriaPrima = null;
		BigDecimal qtdDemanda = BigDecimal.ZERO;
		BigDecimal codEmp = BigDecimal.ZERO;
		BigDecimal codLocal = BigDecimal.ZERO;
		BigDecimal estoque = BigDecimal.ZERO;
		BigDecimal qtdPendente = BigDecimal.ZERO;
		BigDecimal leadtime = BigDecimal.ZERO;

		BigDecimal qtdCapacidadePad = vo.asBigDecimal(SklFieldName.QTDCAPACIDADEPAD);
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			final BigDecimal codProd = vo.asBigDecimal("CODPROD");
			final BigDecimal processoProdutivo = vo.asBigDecimal("CODPRC");
			final BigDecimal qtdeLotes = vo.asBigDecimal("QTDLOTES");

			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "sql/ProgramacaoProducaoSimplifica_queExplosaoMP.sql");
			sql.setNamedParameter("CODPRC", processoProdutivo);
			sql.setNamedParameter("CODPRODPA", codProd);
			rs = sql.executeQuery();

			
			while (rs.next()) {
				codProdMateriaPrima = rs.getBigDecimal("F2_MP");
				codVolMateriaPrima = rs.getString("CODVOLMP");
				qtdDemanda = rs.getBigDecimal("QTDMISTURA").multiply(qtdeLotes).multiply(qtdCapacidadePad);
				codEmp = rs.getBigDecimal("CODEMP");
				codLocal = rs.getBigDecimal("CODLOCAL");
				estoque = rs.getBigDecimal("ESTOQUE");
				qtdPendente = rs.getBigDecimal("QTDPENDENTE");
				leadtime = rs.getBigDecimal("LEADTIME");
				this.setMateriaPrima(numeroProgramacao, codProdMateriaPrima, codEmp, codLocal, codVolMateriaPrima,
						estoque, qtdDemanda, qtdPendente, leadtime);
			}
		} catch (Exception e) {
			SkalaModelException.throwMe(
					String.format("Não foi possível explodir as Matérias Primas da Programação [%s], verifique.",
							numeroProgramacao),
					e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	private void setMateriaPrima(final BigDecimal numeroProgramacao, final BigDecimal codProdMateriaPrima,
			final BigDecimal codEmp, final BigDecimal codLocal, final String codVolMateriaPrima,
			final BigDecimal estoque, final BigDecimal qtdDemanda, final BigDecimal qtdePendente,
			final BigDecimal leadtime) throws Exception {
		final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
		final DynamicVO vo = (DynamicVO) dwFacade.getDefaultValueObjectInstance("AD_PLTPRPRGPEXPMP");
		try {
			vo.setProperty("NUPRGP", numeroProgramacao);
			vo.setProperty("CODPROD", codProdMateriaPrima);
			vo.setProperty("CODEMP", codEmp);
			vo.setProperty("CODLOCAL", codLocal);
			vo.setProperty("CODVOL", codVolMateriaPrima);
			vo.setProperty("ESTOQUE", estoque);
			vo.setProperty("QTDDEMANDA", qtdDemanda);
			vo.setProperty("QTDCOMPRAR", estoque.add(qtdePendente).subtract(qtdDemanda));
			vo.setProperty("QTDPENDENTE", qtdePendente);
			vo.setProperty("LEADTIME", leadtime);
			dwFacade.createEntity("AD_PLTPRPRGPEXPMP", (EntityVO) vo);
		} catch (Exception e) {
			SkalaModelException.throwMe("Ocorreu erro ao salvar informa\u00e7\u00f5es das Mat\u00e9rias Primas.", e);
		}
	}

	private void clearMateriaPrima(final BigDecimal numeroProgramacao) throws Exception {
		final JapeWrapper dao = JapeFactory.dao("AD_PLTPRPRGPEXPMP");

		try {
			dao.deleteByCriteria("this.NUPRGP >= ?", new Object[] { numeroProgramacao });
		} catch (Exception e) {
			SkalaModelException.throwMe("Ocorreu erro ao limpar as Mat\u00e9rias Primas.", e);
		}
	}
}

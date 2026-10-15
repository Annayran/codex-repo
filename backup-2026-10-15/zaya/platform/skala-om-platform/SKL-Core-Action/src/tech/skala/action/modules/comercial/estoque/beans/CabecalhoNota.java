package tech.skala.action.modules.comercial.estoque.beans;

import java.math.BigDecimal;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.AtributosRegras;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ListenerParameters;
import tech.skala.core.commons.exceptions.SkalaModelException;

public abstract class CabecalhoNota {
	private BigDecimal codParc;
	private String observacao;
	private DynamicVO cloneModelVO;
	private DynamicVO cabecalhoVO;
	private BigDecimal nuNota;

	protected AuthenticationInfo authInfo = AuthenticationInfo.getCurrent();

	protected CabecalhoNota(BigDecimal nuNotaTemplate) throws SkalaModelException {
		cloneNotaByTemplate(nuNotaTemplate);
	}

	protected CabecalhoNota(BigDecimal nuNotaTemplate, BigDecimal codParc, String observacao) throws SkalaModelException {
		this.codParc = codParc;
		this.observacao = observacao;
		
		cloneNotaByTemplate(nuNotaTemplate);
	}
	
	protected CabecalhoNota(BigDecimal nuNotaTemplate, String observacao) throws SkalaModelException {
		this.observacao = observacao;
		
		cloneNotaByTemplate(nuNotaTemplate);
	}

	public DynamicVO getCabecalhoVO() {
		return cabecalhoVO;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	private void cloneNotaByTemplate(BigDecimal nuNotaTemplate) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

		try {
			DynamicVO vo = dao.findByPK(nuNotaTemplate);

			this.cloneModelVO = vo.buildClone();
			this.cloneModelVO.setPrimaryKey(null);
			this.cloneModelVO.clearReferences();

			clearFields();
			initFields();

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível clonar o modelo de nota informado.", e);
		}

	}

	private void clearFields() {
		String[] camposParaAnular = { "NUNOTA", "DHTIPOPER", "STATUSNOTA", "DTNEG", "HRMOV", "DTMOV", "VOLUME",
				"QTDVOL", "CHAVENFE", "CODMAQ", "DANFE", "DTENTSAI", "DTFATUR", "DHPROTOC", "DHREGDPEC", "NULOTENFE",
				"NULOTENFSE", "NUMALEATORIO", "NUMCOTACAO", "NUMNFSE", "NUMPROTOC", "NUMREGDPEC", "NUNOTA",
				"NUNOTAPEDFRET", "TPEMISNFE", "INDPRESNFCE", "TPEMISNFSE", "STATUSNFE", "STATUSNFSE", "COMGER", "NUREM",
				"VLRDESCTOTITEM", "VLRNOTA", "PESO" };

		for (String nomeCampo : camposParaAnular)
			cloneModelVO.setProperty(nomeCampo, null);
	}

	private void initFields() throws SkalaModelException {		
		cloneModelVO.setProperty("DTNEG", TimeUtils.clearTime(TimeUtils.getNow()));
		cloneModelVO.setProperty("CODPARC", this.codParc);

		try {
			DynamicVO topVO = ComercialUtils.getTipoOperacao(cloneModelVO.asBigDecimal("CODTIPOPER"), TimeUtils.getNow());
			
			cloneModelVO.setProperty("TIPMOV", topVO.asString("TIPMOV"));
		} catch (Exception e) {
			e.printStackTrace();
//			SkalaModelException.throwMe("Não foi possivel buscar o Tipo de Movimento da TOP.", e);
			cloneModelVO.setProperty("TIPMOV", "C");
		}

		String observacaoModelo = cloneModelVO.asString("OBSERVACAO");
		String observacaoLancamento = null;
		if (observacaoModelo != null && observacao != null) {
			observacaoLancamento = String.format("%s | %s", observacaoModelo, observacao);
		} else if (observacaoModelo != null) {
			observacaoLancamento = String.format("%s", observacaoModelo);
		} else if (observacao != null) {
			observacaoLancamento = String.format("%s", observacao);
		}

		if (observacaoLancamento != null)
			cloneModelVO.setProperty("OBSERVACAO", observacaoLancamento);
	}

	public void buildCabecalhoNotaByTemplate() throws SkalaModelException {
		JapeSessionContext.putProperty(ListenerParameters.CENTRAIS, Boolean.TRUE);
		JapeSessionContext.putProperty(AtributosRegras.INC_UPD_ITEM_CENTRAL, Boolean.TRUE);

		try {
			PrePersistEntityState cabPreState = PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(),
					DynamicEntityNames.CABECALHO_NOTA, cloneModelVO);

			CACHelper cacHelper = new CACHelper();
			BarramentoRegra bRegrasCab = cacHelper.incluirAlterarCabecalho(AuthenticationInfo.getCurrent(),
					cabPreState);
			this.cabecalhoVO = bRegrasCab.getState().getNewVO();

			this.nuNota = cabecalhoVO.asBigDecimalOrZero("NUNOTA");

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir o Cabeçalho do Lançamento.", e);
		}

	}

	public abstract void buildItems() throws SkalaModelException;
}
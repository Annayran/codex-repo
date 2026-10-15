package tech.skala.core.beans.suprimento.recebimento;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.sankhya.util.JdbcUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class Conferencia implements EntityBean, LoadableByValueObject, Persistable {
	public static final String				ENTITY_NAME = SklEntityName.MOBILE_CONFERENCIA;

	private BigDecimal						nuConferencia;
	private BigDecimal						nuNota;
	private BigDecimal						codParc;
	private String							situacao;
	private Timestamp						dhInicioConf;
	private Timestamp						dhFinalConf;
	private String							tipConf;
	private BigDecimal						codUsu;
	private Timestamp						dhAlter;
	private Collection<ConferenciaItem>		itens = new ArrayList<>();
	
	public BigDecimal getNuConferencia() {
		return nuConferencia;
	}

	public void setNuConferencia(BigDecimal nuConferencia) {
		this.nuConferencia = nuConferencia;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Timestamp getDhInicioConf() {
		return dhInicioConf;
	}

	public void setDhInicioConf(Timestamp dhInicioConf) {
		this.dhInicioConf = dhInicioConf;
	}

	public Timestamp getDhFinalConf() {
		return dhFinalConf;
	}

	public void setDhFinalConf(Timestamp dhFinalConf) {
		this.dhFinalConf = dhFinalConf;
	}

	public String getTipConf() {
		return tipConf;
	}

	public void setTipConf(String tipConf) {
		this.tipConf = tipConf;
	}

	public BigDecimal getCodUsu() {
		return codUsu;
	}

	public void setCodUsu(BigDecimal codUsu) {
		this.codUsu = codUsu;
	}

	public Timestamp getDhAlter() {
		return dhAlter;
	}

	public void setDhAlter(Timestamp dhAlter) {
		this.dhAlter = dhAlter;
	}

	public Collection<ConferenciaItem> getItens() {
		return itens;
	}

	public void setItens(Collection<ConferenciaItem> itens) {
		this.itens = itens;
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		
		if (nuConferencia == null) {

			EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
			entityNew.setField(SklFieldName.SITUACAO, situacao);
			entityNew.setField(SklFieldName.TIPCONF, tipConf);
			entityNew.setField(SklFieldName.DHINICIOCONF, dhInicioConf);
			DynamicVO vo = entityNew.fluidInsert();
			
			this.nuConferencia = vo.asBigDecimal(SklFieldName.NUCONFERENCIA);
			this.codUsu = vo.asBigDecimal(SklFieldName.CODUSU);
			this.dhAlter = vo.asTimestamp(SklFieldName.DHALTER);
			persistItens();
		} else {
			EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { nuConferencia });
			EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, pk);
			entityUpd.setField(SklFieldName.SITUACAO, situacao);
			entityUpd.setField(SklFieldName.DHFINALCONF, TimeUtils.getNow());

			entityUpd.fluidUpdate();
		}
		return null;
	}
	
	private void persistItens() throws InsertNotPossibleException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		if (nuNota != null) {
			try {
				jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
				jdbc.openSession();

				sql = new NativeSql(jdbc);
				sql.setReuseStatements(true);

				sql.cleanParameters();
				sql.loadSql(this.getClass(), "queItensConferencia.sql");
				sql.setNamedParameter(SklFieldName.NUNOTA, nuNota);

				rs = sql.executeQuery();

				ConferenciaItem item;
				while (rs.next()) {
					item = new ConferenciaItem(nuConferencia, codParc);
					item.loadByResultSet(rs);
					item.persists();

					this.itens.add(item);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new DataDictionaryExceptions.InsertNotPossibleException(e);
			} finally {
				JdbcUtils.closeResultSet(rs);
				NativeSql.releaseResources(sql);
				JdbcWrapper.closeSession(jdbc);
			}
		}
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
			this.nuConferencia = vo.asBigDecimal(SklFieldName.NUCONFERENCIA);
			this.situacao = vo.asString(SklFieldName.SITUACAO);
			this.dhInicioConf = vo.asTimestamp(SklFieldName.DHINICIOCONF);
			this.dhFinalConf = vo.asTimestamp(SklFieldName.DHFINALCONF);
			this.tipConf = vo.asString(SklFieldName.TIPCONF);
			this.codUsu = vo.asBigDecimal(SklFieldName.CODUSU);
			this.dhAlter = vo.asTimestamp(SklFieldName.DHALTER);

			loadItensConferencia();
	}

	private void loadItensConferencia() throws RecordNotFoundException {
		try {
			ConferenciaItem itemConferencia = null;
			JapeWrapper itemConferenciaDAO = JapeFactory.dao(SklEntityName.MOBILE_CONFERENCIA_ITEM);
			Collection<DynamicVO> itensConferenciaVO = itemConferenciaDAO.find("this.NUCONFERENCIA = ?", nuConferencia);
			for (DynamicVO itemConferenciaVO : itensConferenciaVO) {
				itemConferencia = new ConferenciaItem(nuConferencia, codParc);
				itemConferencia.loadByVO(itemConferenciaVO);

				this.itens.add(itemConferencia);
			}
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
	}

}

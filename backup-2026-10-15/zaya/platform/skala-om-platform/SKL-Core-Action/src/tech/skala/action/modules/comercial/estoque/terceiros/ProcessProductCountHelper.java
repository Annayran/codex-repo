package tech.skala.action.modules.comercial.estoque.terceiros;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedList;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProcessProductCountHelper {

	private static final BigDecimal MAX_ALLOWED_SEQUENCE = new BigDecimal("5");

	private Registro[] rows;

	private BigDecimal id;
	private Timestamp countDate;
	private Collection<ProductCount> collection = new LinkedList<>();
	private final Map<ProductCountKey, BigDecimal> lastSequenceByKey = new HashMap<>();

	public ProcessProductCountHelper(Registro[] rows) {
		this.rows = rows;
		this.collection.clear();
	}

	public void processProductCount() throws SkalaModelException {
		this.id = BigDecimalUtil.getBigDecimal(this.rows[0].getCampo("NUNICO"));
		this.countDate = (Timestamp) this.rows[0].getCampo("DTCONTAGEM");

		try {
			this.loadData();
			this.persistProductCount(collection);
			this.persistProcessed(this.id, this.countDate);
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel processar a acao.", e);
		}

	}

	private void loadData() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(getClass(), "ProcessProductCount_queData.sql");
			sql.setNamedParameter("NUNICO", this.id);
			sql.setNamedParameter("DTCONTAGEM", this.countDate);

			rs = sql.executeQuery();

			ProductCount productCount = null;

			while (rs.next()) {
				productCount = new ProductCount();

				productCount.setCountDate(this.countDate);
				productCount.setCompanyId(rs.getBigDecimal(SklFieldName.CODEMP));
				productCount.setLocationId(rs.getBigDecimal(SklFieldName.CODLOCAL));
				productCount.setProductId(rs.getBigDecimal(SklFieldName.CODPROD));
				productCount.setControl(rs.getString(SklFieldName.CONTROLE));
				productCount.setQuantity(rs.getBigDecimal("QTDEST"));
				productCount.setVolumeId(rs.getString(SklFieldName.CODVOL));
				productCount.setPartnerId(rs.getBigDecimal(SklFieldName.CODPARC));
				productCount.setType(Constants.TIPO);
				productCount.setSequence(this.resolveNextSequence(productCount));
				productCount.setId(this.id);
				productCount.setStandardUnitQuantity(rs.getBigDecimal("QTDESTUNCAD"));
				productCount.setManufacturingDate(rs.getTimestamp(SklFieldName.DTFABRICACAO));
				productCount.setExpirationDate(rs.getTimestamp(SklFieldName.DTVAL));

				collection.add(productCount);
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel carregar as informacoes da Contagem de Produtos.", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	private BigDecimal resolveNextSequence(ProductCount productCount) throws SkalaModelException {
		ProductCountKey key = ProductCountKey.from(productCount);
		BigDecimal maxSequence = this.lastSequenceByKey.get(key);

		if (maxSequence == null) {
			maxSequence = this.loadMaxSequenceFromDatabase(productCount);
		}

		BigDecimal nextSequence;
		if (maxSequence == null || maxSequence.compareTo(BigDecimal.ZERO) <= 0) {
			nextSequence = new BigDecimal(Constants.SEQUENCIA);
		} else {
			nextSequence = maxSequence.add(BigDecimal.ONE);
		}

		if (nextSequence.compareTo(MAX_ALLOWED_SEQUENCE) > 0)
			SkalaModelException.throwMe(
					String.format("Ja existe 4a contagem para o produto %s na data informada.", productCount.getProductId()),
					new Exception());

		this.lastSequenceByKey.put(key, nextSequence);

		return nextSequence;
	}

	private BigDecimal loadMaxSequenceFromDatabase(ProductCount productCount) throws SkalaModelException {
		BigDecimal maxSequence = BigDecimal.ZERO;
		String control = normalizeText(productCount.getControl());
		String volume = normalizeText(productCount.getVolumeId());

		try {
			maxSequence = NativeSql.getBigDecimal("MAX(SEQUENCIA)", "TGFCTE",
					"DTCONTAGEM = ? AND CODEMP = ? AND CODLOCAL = ? AND CODPROD = ? AND NVL(CONTROLE, ' ') = ? AND NVL(CODVOL, ' ') = ? AND CODPARC = ? AND TIPO = ?",
					new Object[] { productCount.getCountDate(), productCount.getCompanyId(), productCount.getLocationId(),
							productCount.getProductId(), control, volume, productCount.getPartnerId(),
							productCount.getType() });
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel resolver a sequencia da Contagem de Produtos.", e);
		}

		return maxSequence;
	}

	private static final class ProductCountKey {
		private final Timestamp countDate;
		private final BigDecimal companyId;
		private final BigDecimal locationId;
		private final BigDecimal productId;
		private final String control;
		private final String volumeId;
		private final BigDecimal partnerId;
		private final String type;

		private ProductCountKey(Timestamp countDate, BigDecimal companyId, BigDecimal locationId, BigDecimal productId,
				String control, String volumeId, BigDecimal partnerId, String type) {
			this.countDate = countDate;
			this.companyId = companyId;
			this.locationId = locationId;
			this.productId = productId;
			this.control = control;
			this.volumeId = volumeId;
			this.partnerId = partnerId;
			this.type = type;
		}

		private static ProductCountKey from(ProductCount productCount) {
			return new ProductCountKey(productCount.getCountDate(), productCount.getCompanyId(),
					productCount.getLocationId(), productCount.getProductId(),
					normalizeText(productCount.getControl()), normalizeText(productCount.getVolumeId()),
					productCount.getPartnerId(), productCount.getType());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof ProductCountKey))
				return false;
			ProductCountKey other = (ProductCountKey) obj;
			return java.util.Objects.equals(countDate, other.countDate) && java.util.Objects.equals(companyId, other.companyId)
					&& java.util.Objects.equals(locationId, other.locationId) && java.util.Objects.equals(productId, other.productId)
					&& java.util.Objects.equals(control, other.control) && java.util.Objects.equals(volumeId, other.volumeId)
					&& java.util.Objects.equals(partnerId, other.partnerId) && java.util.Objects.equals(type, other.type);
		}

		@Override
		public int hashCode() {
			return java.util.Objects.hash(countDate, companyId, locationId, productId, control, volumeId, partnerId, type);
		}
	}

	private static String normalizeText(String value) {
		if (value == null || value.trim().length() == 0)
			return " ";

		return value;
	}

	private void persistProductCount(Collection<ProductCount> collection) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					for (ProductCount productCount : collection) {
						productCount.persists();
					}
				}
			});
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel persistir as informacoes da Contagem de Produtos.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void persistProcessed(BigDecimal id, Timestamp countDate) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					updateProcessed(id, countDate);
				}
			});
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel persistir as informacoes da Contagem de Produtos.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void updateProcessed(BigDecimal id, Timestamp countDate) {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(getClass(), "ProcessProductCount_queUpdateProcessed.sql");
			sql.setNamedParameter("NUNICO", id);
			sql.setNamedParameter("DTCONTAGEM", countDate);
			sql.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}

	}

}

package tech.skala.action.integration.prodwin;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class ProdwinRequestResponseLog implements EntityBean, Persistable {

	public static final String ENTITY_NAME = "AD_PLTPRPRGPOPPRODWIN";

	private BigDecimal productionOrderId;
	private BigDecimal releaseNumber;
	private BigDecimal scheduleNumber;
	private BigDecimal sequence;
	private BigDecimal statusCode;
	private String url;
	private String request;
	private String response;

	public BigDecimal getProductionOrderId() {
		return productionOrderId;
	}

	public void setProductionOrderId(BigDecimal productionOrderId) {
		this.productionOrderId = productionOrderId;
	}

	public BigDecimal getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(BigDecimal releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public BigDecimal getScheduleNumber() {
		return scheduleNumber;
	}

	public void setScheduleNumber(BigDecimal scheduleNumber) {
		this.scheduleNumber = scheduleNumber;
	}

	public BigDecimal getSequence() {
		return sequence;
	}

	public void setSequence(BigDecimal sequence) {
		this.sequence = sequence;
	}

	public BigDecimal getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(BigDecimal statusCode) {
		this.statusCode = statusCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		try {
			EntityInsert entity = new EntityInsert(ENTITY_NAME);

			entity.setField(SklFieldName.IDIPROC, this.productionOrderId);
			entity.setField("NULOP", this.releaseNumber);
			entity.setField("NUPRGP", this.scheduleNumber);
			entity.setField("STATUSCODE", this.statusCode);
			entity.setField("URL", this.url);
			entity.setField("REQUEST", this.request.toCharArray());
			entity.setField("RESPONSE", this.response.toCharArray());

			entity.fluidInsert();
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return null;
	}

	@Override
	public String toString() {
		return "ProdwinRequestResponseLog [productionOrderId=" + productionOrderId + ", releaseNumber=" + releaseNumber
				+ ", scheduleNumber=" + scheduleNumber + ", sequence=" + sequence + ", statusCode=" + statusCode
				+ ", url=" + url + ", request=" + request + ", response=" + response + "]";
	}

}

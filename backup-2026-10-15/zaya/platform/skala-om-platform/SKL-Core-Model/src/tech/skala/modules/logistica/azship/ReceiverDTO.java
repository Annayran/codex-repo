package tech.skala.modules.logistica.azship;

import java.util.ArrayList;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class ReceiverDTO implements DTO {

	private String  								orderCode;
	private String  								nfCode;
	private List<String>  							receiptImages = new ArrayList<>();
	private String  								observation;
	private String  								arrivalDate;
	private String  								receiverDate;
	private String  								status;
	private String  								approveDate;
	private String  								refuseDate;
	private String  								refuseReason;
	private AddressDTO  							address;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getNfCode() {
		return nfCode;
	}

	public void setNfCode(String nfCode) {
		this.nfCode = nfCode;
	}

	public List<String> getReceiptImages() {
		return receiptImages;
	}

	public void setReceiptImages(List<String> receiptImages) {
		this.receiptImages = receiptImages;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getReceiverDate() {
		return receiverDate;
	}

	public void setReceiverDate(String receiverDate) {
		this.receiverDate = receiverDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getRefuseDate() {
		return refuseDate;
	}

	public void setRefuseDate(String refuseDate) {
		this.refuseDate = refuseDate;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO addressDTO) {
		this.address = addressDTO;
	}

	@Override
	public String toString() {
		return String.format(
				"ReceiverDTO [orderCode=%s, nfCode=%s, receiptImages=%s, observation=%s, arrivalDate=%s, receiverDate=%s, status=%s, approveDate=%s, refuseDate=%s, refuseReason=%s, address=%s]",
				orderCode, nfCode, receiptImages, observation, arrivalDate, receiverDate, status, approveDate,
				refuseDate, refuseReason, address);
	}

}

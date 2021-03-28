package com.dpm.dto;

import org.springframework.stereotype.Component;

import com.dpm.utility.Status;
@Component
public class CheckListFilterDTO {

	private int productType;
	private int lot;
	private String date;
	private String shift;
	private Status status;
	private  int size;
	public CheckListFilterDTO(int productType, int lot, String date, String shift, Status status, int size) {
		super();
		this.productType = productType;
		this.lot = lot;
		this.date = date;
		this.shift = shift;
		this.status = status;
		this.size = size;
	}
	public CheckListFilterDTO() {
		super();
	}
	public int getProductType() {
		return productType;
	}
	public void setProductType(int productType) {
		this.productType = productType;
	}
	public int getLot() {
		return lot;
	}
	public void setLot(int lot) {
		this.lot = lot;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "CheckListFilterDTO [productType=" + productType + ", lot=" + lot + ", date=" + date + ", shift=" + shift
				+ ", status=" + status + ", size=" + size + "]";
	}
	
}

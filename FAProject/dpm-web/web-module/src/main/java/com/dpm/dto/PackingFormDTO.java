package com.dpm.dto;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import com.dpm.utility.Status;

/**
 * Author:  HoiNX1        Modified date: 26/01/2021 11:26 AM     
 */

public class PackingFormDTO {
	
	//ID
	private Integer id;
	
	//Loai san pham
	private Integer typeProductID;
	
	//MaySP
	private Integer machineID;
	
	//Nguoi nhap
	private Integer createManID;
		
	//Ngay dong goi
	private String packingDate;
	
	//Ma lo ban thanh pham ( save lotCode of ProductLot)
	private String semi_Product_Lot;
	
	//Thoi gian
	private String timePacking;
	
	//Ma lo thanh pham (save lotCode of ProductLot)
	private String finished_Product_Lot;
	
	//So luong
	private Integer quanity;
	
	//Ca san xuat
	private Integer shift;
	
	//Quy cach
	private Integer packingID;
	
	//Quan doc
	private Integer foreManID;
	
	//Loai bao bi
	private Integer typeOfPackingID;
	
	//Nguoi dong goi
	private Integer inChargeManID;
	
	//So luong bao bi
	private Integer packingQuanity;
	
	//STT
	private Integer sttNo;
		
	//status
	private Status status;

	
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getTypeProductID() {
		return typeProductID;
	}



	public void setTypeProductID(Integer typeProductID) {
		this.typeProductID = typeProductID;
	}



	public Integer getMachineID() {
		return machineID;
	}



	public void setMachineID(Integer machineID) {
		this.machineID = machineID;
	}



	public Integer getCreateManID() {
		return createManID;
	}



	public void setCreateManID(Integer createManID) {
		this.createManID = createManID;
	}



	public String getPackingDate() {
		return packingDate;
	}



	public void setPackingDate(String packingDate) {
		this.packingDate = packingDate;
	}



	public String getSemi_Product_Lot() {
		return semi_Product_Lot;
	}



	public void setSemi_Product_Lot(String semi_Product_Lot) {
		this.semi_Product_Lot = semi_Product_Lot;
	}



	public String getTimePacking() {
		return timePacking;
	}



	public void setTimePacking(String timePacking) {
		this.timePacking = timePacking;
	}



	public String getFinished_Product_Lot() {
		return finished_Product_Lot;
	}



	public void setFinished_Product_Lot(String finished_Product_Lot) {
		this.finished_Product_Lot = finished_Product_Lot;
	}



	public Integer getQuanity() {
		return quanity;
	}



	public void setQuanity(Integer quanity) {
		this.quanity = quanity;
	}



	public Integer getShift() {
		return shift;
	}



	public void setShift(Integer shift) {
		this.shift = shift;
	}



	public Integer getPackingID() {
		return packingID;
	}



	public void setPackingID(Integer packingID) {
		this.packingID = packingID;
	}



	public Integer getForeManID() {
		return foreManID;
	}



	public void setForeManID(Integer foreManID) {
		this.foreManID = foreManID;
	}



	public Integer getTypeOfPackingID() {
		return typeOfPackingID;
	}



	public void setTypeOfPackingID(Integer typeOfPackingID) {
		this.typeOfPackingID = typeOfPackingID;
	}



	public Integer getInChargeManID() {
		return inChargeManID;
	}



	public void setInChargeManID(Integer inChargeManID) {
		this.inChargeManID = inChargeManID;
	}



	public Integer getPackingQuanity() {
		return packingQuanity;
	}



	public void setPackingQuanity(Integer packingQuanity) {
		this.packingQuanity = packingQuanity;
	}



	public Integer getSttNo() {
		return sttNo;
	}



	public void setSttNo(Integer sttNo) {
		this.sttNo = sttNo;
	}



	public Status getStatus() {
		return status;
	}



	public void setStatus(Status status) {
		this.status = status;
	}



	public PackingFormDTO() {
		// TODO Auto-generated constructor stub
	}



	@Override
	public String toString() {
		return "PackingFormDTO [id=" + id + ", typeProductID=" + typeProductID + ", machineID=" + machineID
				+ ", createManID=" + createManID + ", packingDate=" + packingDate + ", semi_Product_Lot="
				+ semi_Product_Lot + ", timePacking=" + timePacking + ", finished_Product_Lot=" + finished_Product_Lot
				+ ", quanity=" + quanity + ", shift=" + shift + ", packingID=" + packingID + ", foreManID=" + foreManID
				+ ", typeOfPackingID=" + typeOfPackingID + ", inChargeManID=" + inChargeManID + ", packingQuanity="
				+ packingQuanity + ", sttNo=" + sttNo + ", status=" + status + "]";
	}
	
	
}

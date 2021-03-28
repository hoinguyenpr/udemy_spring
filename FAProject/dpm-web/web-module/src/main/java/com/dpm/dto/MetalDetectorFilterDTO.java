/**
 * 
 */
package com.dpm.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author DinhDN
 *
 */
public class MetalDetectorFilterDTO {

	@NotEmpty
	private String departmentCode;

	@NotEmpty
	private String typeProductCode;

	@NotEmpty
	private String lotNo;

	@NotEmpty
	private String checkingSample;

	@NotEmpty
	private String status;

	@NotEmpty
	private String userName;

	@NotEmpty
	private String startDate;

	
	@NotEmpty
	private String endDate;

	public MetalDetectorFilterDTO() {
		super();
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getTypeProductCode() {
		return typeProductCode;
	}

	public void setTypeProductCode(String typeProductCode) {
		this.typeProductCode = typeProductCode;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getCheckingSample() {
		return checkingSample;
	}

	public void setCheckingSample(String checkingSample) {
		this.checkingSample = checkingSample;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "MetalDetectorFilterDTO [departmentCode=" + departmentCode
				+ ", typeProductCode=" + typeProductCode + ", lotNo=" + lotNo
				+ ", checkingSample=" + checkingSample + ", status=" + status
				+ ", userName=" + userName + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}

}
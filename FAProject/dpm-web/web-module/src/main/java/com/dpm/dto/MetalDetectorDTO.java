/**
 * 
 */
package com.dpm.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.dpm.constant.Constants;

/**
 * @author DinhDN 15-01-2021 11:28:00 Update by DinhDN 20/01/2021 09:55:00 AM
 *
 */
public class MetalDetectorDTO {

	private Integer id;

	@NotEmpty
	private String departmentCode;

	@NotEmpty
	private String typeProductCode;

	@NotEmpty
	private String lotNo;

	@DateTimeFormat(pattern = Constants.FORMAT_DATE)
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date dateCheck;

	@DateTimeFormat(pattern = Constants.FORMAT_TIME)
	@Temporal(TemporalType.TIME)
	@NotNull
	private Date timeCheck;

	@NotEmpty
	private String frequency;

	@NotNull
	private Boolean optionsRadiosFe;

	@NotNull
	private Boolean optionsRadiosNonfe;

	@NotNull
	private Boolean optionsRadiosSus;

	private String correctiveAction;

	@NotEmpty
	private String userName;

	@NotNull
	@Size(max = Constants.STRING_SIZE_MAX, min = Constants.STRING_SIZE_MIN)
	private String status;

	/**
	 * Function create new Object with noParametter.
	 */
	public MetalDetectorDTO() {
		this.status = Constants.STATUS_PENDING;
		this.optionsRadiosFe = true;
		this.optionsRadiosNonfe = true;
		this.optionsRadiosSus = true;
	}

	/*
	 * Getter setter for all attribute
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getDateCheck() {
		return dateCheck;
	}

	public void setDateCheck(Date dateCheck) {
		this.dateCheck = dateCheck;
	}

	public Date getTimeCheck() {
		return timeCheck;
	}

	public void setTimeCheck(Date timeCheck) {
		this.timeCheck = timeCheck;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Boolean getOptionsRadiosFe() {
		return optionsRadiosFe;
	}

	public void setOptionsRadiosFe(Boolean optionsRadiosFe) {
		this.optionsRadiosFe = optionsRadiosFe;
	}

	public Boolean getOptionsRadiosNonfe() {
		return optionsRadiosNonfe;
	}

	public void setOptionsRadiosNonfe(Boolean optionsRadiosNonfe) {
		this.optionsRadiosNonfe = optionsRadiosNonfe;
	}

	public Boolean getOptionsRadiosSus() {
		return optionsRadiosSus;
	}

	public void setOptionsRadiosSus(Boolean optionsRadiosSus) {
		this.optionsRadiosSus = optionsRadiosSus;
	}

	public String getCorrectiveAction() {
		return correctiveAction;
	}

	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Function override method toString() of object
	 */
	@Override
	public String toString() {
		return "MetalDetectorDTO [id=" + id + ", departmentCode=" + departmentCode
				+ ", typeProductCode=" + typeProductCode + ", lotNo=" + lotNo
				+ ", dateCheck=" + dateCheck + ", timeCheck=" + timeCheck + ", frequency="
				+ frequency + ", optionsRadiosFe=" + optionsRadiosFe
				+ ", optionsRadiosNonfe=" + optionsRadiosNonfe + ", optionsRadiosSus="
				+ optionsRadiosSus + ", correctiveAction=" + correctiveAction
				+ ", userName=" + userName + ", status=" + status + "]";
	}

}

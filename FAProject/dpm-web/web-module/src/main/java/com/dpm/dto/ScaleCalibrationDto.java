package com.dpm.dto;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 
 * @author ThuanLV6
 * added scale calibration dto 
 */
public class ScaleCalibrationDto {


	private Integer id;

	private LocalDateTime createdDate;

	
	private String calibratingMethod;


	private float firstResult;

	private float secondResult;


	private float thirdResult;


	private float avarageResult;


	private String comment;


	private Integer standardDeviceId;
	private String standardDeviceName;


	private Integer calibratedDeviceId;
	private String calibratedDeviceName;


	private String departmentCode;
	private String departmentName;


	private Integer calibratorId;
	private String calibratorName;


	private Integer inspectorId;
	private String inspectorName;
	
	private Integer scaleSymbolId;
	private int status;
	
	public ScaleCalibrationDto() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCalibratingMethod() {
		return calibratingMethod;
	}

	public void setCalibratingMethod(String calibratingMethod) {
		this.calibratingMethod = calibratingMethod;
	}

	public float getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(float firstResult) {
		this.firstResult = firstResult;
	}

	public float getSecondResult() {
		return secondResult;
	}

	public void setSecondResult(float secondResult) {
		this.secondResult = secondResult;
	}

	public float getThirdResult() {
		return thirdResult;
	}

	public void setThirdResult(float thirdResult) {
		this.thirdResult = thirdResult;
	}

	public float getAvarageResult() {
		return avarageResult;
	}

	public void setAvarageResult(float avarageResult) {
		this.avarageResult = avarageResult;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStandardDeviceId() {
		return standardDeviceId;
	}

	public void setStandardDeviceId(Integer standardDeviceId) {
		this.standardDeviceId = standardDeviceId;
	}

	public String getStandardDeviceName() {
		return standardDeviceName;
	}

	public void setStandardDeviceName(String standardDeviceName) {
		this.standardDeviceName = standardDeviceName;
	}

	public Integer getCalibratedDeviceId() {
		return calibratedDeviceId;
	}

	public void setCalibratedDeviceId(Integer calibratedDeviceId) {
		this.calibratedDeviceId = calibratedDeviceId;
	}

	public String getCalibratedDeviceName() {
		return calibratedDeviceName;
	}

	public void setCalibratedDeviceName(String calibratedDeviceName) {
		this.calibratedDeviceName = calibratedDeviceName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getCalibratorId() {
		return calibratorId;
	}

	public void setCalibratorId(Integer calibratorId) {
		this.calibratorId = calibratorId;
	}

	public String getCalibratorName() {
		return calibratorName;
	}

	public void setCalibratorName(String calibratorName) {
		this.calibratorName = calibratorName;
	}

	public Integer getInspectorId() {
		return inspectorId;
	}

	public void setInspectorId(Integer inspectorId) {
		this.inspectorId = inspectorId;
	}

	public String getInspectorName() {
		return inspectorName;
	}

	public void setInspectorName(String inspectorName) {
		this.inspectorName = inspectorName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	public Integer getScaleSymbolId() {
		return scaleSymbolId;
	}

	public void setScaleSymbolId(Integer scaleSymbolId) {
		this.scaleSymbolId = scaleSymbolId;
	}

	@Override
	public String toString() {
		return "ScaleCalibrationDto [id=" + id + ", createdDate=" + createdDate
				+ ", calibratingMethod=" + calibratingMethod + ", firstResult="
				+ firstResult + ", secondResult=" + secondResult + ", thirdResult="
				+ thirdResult + ", avarageResult=" + avarageResult + ", comment="
				+ comment + ", standardDeviceId=" + standardDeviceId
				+ ", standardDeviceName=" + standardDeviceName + ", calibratedDeviceId="
				+ calibratedDeviceId + ", calibratedDeviceName=" + calibratedDeviceName
				+ ", departmentCode=" + departmentCode + ", departmentName="
				+ departmentName + ", calibratorId=" + calibratorId + ", calibratorName="
				+ calibratorName + ", inspectorId=" + inspectorId + ", inspectorName="
				+ inspectorName + ", scaleSymbolId=" + scaleSymbolId + ", status="
				+ status + "]";
	}

	
	

	
	
}

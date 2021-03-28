package com.dpm.dto;

import java.time.LocalDateTime;

import com.dpm.entity.Employee;

/**
 * 
 * @author ThuanLV6- 2/2/2021
 *
 */
public class ScaleCalibrationFilterDto {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Integer calibratorId;
	private Integer inspectorId;
	private boolean isApproved;
	private boolean isPending;
	private boolean isRejected;
	private String keyword;
	public ScaleCalibrationFilterDto() {
		super();
	}
	public ScaleCalibrationFilterDto(LocalDateTime startDate, LocalDateTime endDate, Integer calibratorId,
			Integer inspectorId, boolean isApproved, boolean isPending, boolean isRejected) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.calibratorId = calibratorId;
		this.inspectorId = inspectorId;
		this.isApproved = isApproved;
		this.isPending = isPending;
		this.isRejected = isRejected;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public Integer getCalibratorId() {
		return calibratorId;
	}
	public void setCalibratorId(Integer calibratorId) {
		this.calibratorId = calibratorId;
	}
	public Integer getInspectorId() {
		return inspectorId;
	}
	public void setInspectorId(Integer inspectorId) {
		this.inspectorId = inspectorId;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public boolean isPending() {
		return isPending;
	}
	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}
	public boolean isRejected() {
		return isRejected;
	}
	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "ScaleCalibrationFilterDto [startDate=" + startDate + ", endDate=" + endDate + ", calibratorId="
				+ calibratorId + ", inspectorId=" + inspectorId + ", isApproved=" + isApproved + ", isPending="
				+ isPending + ", isRejected=" + isRejected + ", keyword=" + keyword + "]";
	}
	
	
	
	
}

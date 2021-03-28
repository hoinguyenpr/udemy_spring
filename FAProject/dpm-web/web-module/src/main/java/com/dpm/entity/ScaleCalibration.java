package com.dpm.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author ThuanLV6 scale calibration record
 */
@Entity
@Table(name = "scale_calibration")
public class ScaleCalibration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@Column(name = "calibrating_method")
	private String calibratingMethod;

	@Column(name = "first_result")
	private float firstResult;

	@Column(name = "second_result")
	private float secondResult;

	@Column(name = "third_result")
	private float thirdResult;

	@Column(name = "avarage_result")
	private float avarageResult;

	@Column(name = "comment")
	private String comment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "standard_device_id", nullable = true)
	private Machine standardDevice;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "calibrated_device_id", nullable = true)
	private Machine calibratedDevice;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", nullable = true)
	private Department department;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "calibrator_id", nullable = true)
	private Employee calibrator;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "inspector_id", nullable = true)
	private Employee inspector;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "symbol_id", nullable = true)
	private ScaleSymbol scaleSymbol;
	
	@Column(columnDefinition = "boolean default false", name = "is_deleted")
	private boolean isDeleted;
		

	

	public ScaleCalibration() {
	
	}

	/**
	 * 
	 * @param createdDate
	 * @param calibratingMethod
	 * @param firstResult
	 * @param secondResult
	 * @param thirdResult
	 * @param avarageResult
	 * @param comment
	 * @param standardDevice
	 * @param calibratedDevice
	 * @param department
	 * @param calibrator
	 * @param inspector
	 * @param status
	 */
	public ScaleCalibration(LocalDateTime createdDate, String calibratingMethod, float firstResult,
			float secondResult, float thirdResult, float avarageResult, String comment,
			Machine standardDevice, Machine calibratedDevice, Department department,
			Employee calibrator, Employee inspector, String status) {
		super();
		this.createdDate = createdDate;
		this.calibratingMethod = calibratingMethod;
		this.firstResult = firstResult;
		this.secondResult = secondResult;
		this.thirdResult = thirdResult;
		this.avarageResult = avarageResult;
		this.comment = comment;
		this.standardDevice = standardDevice;
		this.calibratedDevice = calibratedDevice;
		this.department = department;
		this.calibrator = calibrator;
		this.inspector = inspector;
		this.status = status;
		
		
		
	}
	
	

	public ScaleCalibration(LocalDateTime createdDate, String calibratingMethod,
			float firstResult, float secondResult, float thirdResult, float avarageResult,
			Machine standardDevice, Machine calibratedDevice, Department department,
			Employee calibrator, ScaleSymbol scaleSymbol, String status) {
		super();
		this.createdDate = createdDate;
		this.calibratingMethod = calibratingMethod;
		this.firstResult = firstResult;
		this.secondResult = secondResult;
		this.thirdResult = thirdResult;
		this.avarageResult = avarageResult;
		this.standardDevice = standardDevice;
		this.calibratedDevice = calibratedDevice;
		this.department = department;
		this.calibrator = calibrator;
		this.scaleSymbol = scaleSymbol;
		this.status = status;
		
	
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

	public Machine getStandardDevice() {
		return standardDevice;
	}

	public void setStandardDevice(Machine standardDevice) {
		this.standardDevice = standardDevice;
	}

	public Machine getCalibratedDevice() {
		return calibratedDevice;
	}

	public void setCalibratedDevice(Machine calibratedDevice) {
		this.calibratedDevice = calibratedDevice;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Employee getCalibrator() {
		return calibrator;
	}

	public void setCalibrator(Employee calibrator) {
		this.calibrator = calibrator;
	}

	public Employee getInspector() {
		return inspector;
	}

	public void setInspector(Employee inspector) {
		this.inspector = inspector;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public ScaleSymbol getScaleSymbol() {
		return scaleSymbol;
	}

	public void setScaleSymbol(ScaleSymbol scaleSymbol) {
		this.scaleSymbol = scaleSymbol;
	}

	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public ScaleCalibration(Integer id, LocalDateTime createdDate,
			LocalDateTime modifiedDate, String calibratingMethod, float firstResult,
			float secondResult, float thirdResult, float avarageResult, String comment,
			Machine standardDevice, Machine calibratedDevice, Department department,
			Employee calibrator, Employee inspector, String status,
			ScaleSymbol scaleSymbol, boolean isDeleted) {
		super();
		this.id = id;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.calibratingMethod = calibratingMethod;
		this.firstResult = firstResult;
		this.secondResult = secondResult;
		this.thirdResult = thirdResult;
		this.avarageResult = avarageResult;
		this.comment = comment;
		this.standardDevice = standardDevice;
		this.calibratedDevice = calibratedDevice;
		this.department = department;
		this.calibrator = calibrator;
		this.inspector = inspector;
		this.status = status;
		this.scaleSymbol = scaleSymbol;
		this.isDeleted = isDeleted;
	}

	

}

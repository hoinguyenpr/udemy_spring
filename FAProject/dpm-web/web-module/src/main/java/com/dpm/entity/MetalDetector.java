/**
 * 
 */
package com.dpm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.dpm.constant.Constants;

/**
 * @author DinhDN Update: 12/01/2021, Modify: 03/02/2021 05:24 PM
 */

@Entity
@Table(name = "metal_detector")
public class MetalDetector implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "product_lot_no")
	@NotNull
	private ProductLot productLot;

	@ManyToOne
	@JoinColumn(name = "department_code")
	@NotNull
	private Department department;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	@NotNull
	private Employee inspector;

	@Column(name = "checking_date")
	@DateTimeFormat(pattern = Constants.FORMAT_DATE)
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date checkingDate;

	@Column(name = "checking_time")
	@DateTimeFormat(pattern = Constants.FORMAT_TIME)
	@Temporal(TemporalType.TIME)
	@NotNull
	private Date checkingTime;

	@Column(name = "created_date")
	@CreationTimestamp
	private Date createdDate;

	@Column(name = "modify_date")
	@UpdateTimestamp
	private Date modifyDate;

	@Column(name = "frequency")
	@NotEmpty
	@Size(max = Constants.STRING_SIZE_MAX, min = Constants.STRING_SIZE_MIN)
	private String frequency;

	@Column(name = "checking_sample_fe")
	@NotNull
	private Boolean checkingSampleFe;

	@Column(name = "checking_sample_non_fe")
	@NotNull
	private Boolean checkingSampleNonFe;

	@Column(name = "checking_sample_sus")
	@NotNull
	private Boolean checkingSampleSus;

	@Column(name = "corrective_action")
	private String correctiveAction;

	@Column(name = "status")
	@NotEmpty
	@Size(max = Constants.STRING_SIZE_MAX, min = Constants.STRING_SIZE_MIN)
	private String status;

	@Column(name = "is_delete")
	@NotNull
	private Boolean isDelete;

	public MetalDetector() {
		this.isDelete = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProductLot getProductLot() {
		return productLot;
	}

	public void setProductLot(ProductLot productLot) {
		this.productLot = productLot;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Employee getInspector() {
		return inspector;
	}

	public void setInspector(Employee inspector) {
		this.inspector = inspector;
	}

	public Date getCheckingDate() {
		return checkingDate;
	}

	public void setCheckingDate(Date checkingDate) {
		this.checkingDate = checkingDate;
	}

	public Date getCheckingTime() {
		return checkingTime;
	}

	public void setCheckingTime(Date checkingTime) {
		this.checkingTime = checkingTime;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Boolean getCheckingSampleFe() {
		return checkingSampleFe;
	}

	public void setCheckingSampleFe(Boolean checkingSampleFe) {
		this.checkingSampleFe = checkingSampleFe;
	}

	public Boolean getCheckingSampleNonFe() {
		return checkingSampleNonFe;
	}

	public void setCheckingSampleNonFe(Boolean checkingSampleNonFe) {
		this.checkingSampleNonFe = checkingSampleNonFe;
	}

	public Boolean getCheckingSampleSus() {
		return checkingSampleSus;
	}

	public void setCheckingSampleSus(Boolean checkingSampleSus) {
		this.checkingSampleSus = checkingSampleSus;
	}

	public String getCorrectiveAction() {
		return correctiveAction;
	}

	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "MetalDetector [id=" + id + ", productLot=" + productLot + ", department=" + department + ", inspector="
				+ inspector + ", checkingDate=" + checkingDate + ", checkingTime=" + checkingTime + ", frequency="
				+ frequency + ", checkingSampleFe=" + checkingSampleFe + ", checkingSampleNonFe=" + checkingSampleNonFe
				+ ", checkingSampleSus=" + checkingSampleSus + ", correctiveAction=" + correctiveAction + ", status="
				+ status + ", isDelete=" + isDelete + "]\n";
	}

}

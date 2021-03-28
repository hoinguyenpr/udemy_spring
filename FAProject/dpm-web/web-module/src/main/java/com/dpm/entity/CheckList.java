package com.dpm.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.dpm.dto.CheckListDTO;
import com.dpm.dto.CheckListSettingDTO;
import com.dpm.utility.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

/**
 * 
 * @author LamPQT
 * @Date 1/12/21
 */
@Entity
@Table(name = "checklists")
public class CheckList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	@Column(name = "date_create")
	private LocalDate date;

	@JsonFormat(pattern = "hh:mm a")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonSerialize(using = LocalTimeSerializer.class)
	@Column(name = "time_create")
	private LocalTime time;

	private Float moisture;
	private Short ph;
	private Float brix;
	private boolean color;
	private boolean taste;
	private Float impurity;

	@Column(name = "quantity_satisfied")
	private Integer quantitySatisfied;
	@Column(name = "quantity_unsatisfied")
	private Integer quantityUnsatisfied;
	@Column(name = "quantity_mix")
	private Integer quantityMix;

	@Nationalized
	private String remark;

	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "lot_id")
	private ProductLot lot;

	@ManyToOne
	@JoinColumn(name = "qc_id")
	private Employee qc;

	@ManyToOne
	@JoinColumn(name = "own_id")
	private Employee own;

	@ManyToOne
	@JoinColumn(name = "verifier_id")
	private Employee verifier;
	@Column(name = "created_date")
	@CreationTimestamp
	private Date createdDate;

	@Column(name = "modify_date")
	@UpdateTimestamp
	private Date modifyDate;
	
	
	
	
	public CheckList() {
		super();
	}


	public CheckList( LocalDate date, LocalTime time, Float moisture, Short ph,
			Float brix, boolean color, boolean taste, Float impurity,
			Integer quantitySatisfied, Integer quantityUnsatisfied, Integer quantityMix,
			String remark, Status status, ProductLot lot, Employee qc, Employee own,
			Employee verifier) {
		super();
		
		this.date = date;
		this.time = time;
		this.moisture = moisture;
		this.ph = ph;
		this.brix = brix;
		this.color = color;
		this.taste = taste;
		this.impurity = impurity;
		this.quantitySatisfied = quantitySatisfied;
		this.quantityUnsatisfied = quantityUnsatisfied;
		this.quantityMix = quantityMix;
		this.remark = remark;
		this.status = status;
		this.lot = lot;
		this.qc = qc;
		this.own = own;
		this.verifier = verifier;
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public LocalTime getTime() {
		return time;
	}


	public void setTime(LocalTime time) {
		this.time = time;
	}


	public Float getMoisture() {
		return moisture;
	}


	public void setMoisture(Float moisture) {
		this.moisture = moisture;
	}


	public Short getPh() {
		return ph;
	}


	public void setPh(Short ph) {
		this.ph = ph;
	}


	public Float getBrix() {
		return brix;
	}


	public void setBrix(Float brix) {
		this.brix = brix;
	}


	public boolean isColor() {
		return color;
	}


	public void setColor(boolean color) {
		this.color = color;
	}


	public boolean isTaste() {
		return taste;
	}


	public void setTaste(boolean taste) {
		this.taste = taste;
	}


	public Float getImpurity() {
		return impurity;
	}


	public void setImpurity(Float impurity) {
		this.impurity = impurity;
	}


	public Integer getQuantitySatisfied() {
		return quantitySatisfied;
	}


	public void setQuantitySatisfied(Integer quantitySatisfied) {
		this.quantitySatisfied = quantitySatisfied;
	}


	public Integer getQuantityUnsatisfied() {
		return quantityUnsatisfied;
	}


	public void setQuantityUnsatisfied(Integer quantityUnsatisfied) {
		this.quantityUnsatisfied = quantityUnsatisfied;
	}


	public Integer getQuantityMix() {
		return quantityMix;
	}


	public void setQuantityMix(Integer quantityMix) {
		this.quantityMix = quantityMix;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public ProductLot getLot() {
		return lot;
	}


	public void setLot(ProductLot lot) {
		this.lot = lot;
	}


	public Employee getQc() {
		return qc;
	}


	public void setQc(Employee qc) {
		this.qc = qc;
	}


	public Employee getOwn() {
		return own;
	}


	public void setOwn(Employee own) {
		this.own = own;
	}


	public Employee getVerifier() {
		return verifier;
	}


	public void setVerifier(Employee verifier) {
		this.verifier = verifier;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}


	@Override
	public String toString() {
		return "CheckList [id=" + id + ", date=" + date + ", time=" + time + ", moisture="
				+ moisture + ", ph=" + ph + ", brix=" + brix + ", color=" + color
				+ ", taste=" + taste + ", impurity=" + impurity + ", quantitySatisfied="
				+ quantitySatisfied + ", quantityUnsatisfied=" + quantityUnsatisfied
				+ ", quantityMix=" + quantityMix + ", remark=" + remark + ", status="
				+ status + ", lot=" + lot + ", qc=" + qc + ", own=" + own + ", verifier="
				+ verifier + ", createdDate=" + createdDate + ", modifyDate=" + modifyDate
				+ "]";
	}


	public static CheckList mapping(CheckListDTO dto) {
		CheckList checkList = new CheckList();
		checkList.setId(dto.getId());
		checkList.setBrix(dto.getBrix());
		checkList.setImpurity(dto.getImpurity());
		checkList.setPh(dto.getPh());
		checkList.setColor(dto.isColor());
		checkList.setTaste(dto.isTaste());
		checkList.setMoisture(dto.getMoisture());
		checkList.setQuantityMix(dto.getQuantityMix());
		checkList.setQuantitySatisfied(dto.getQuantitySatisfied());
		checkList.setQuantityUnsatisfied(dto.getQuantityUnsatisfied());
		checkList.setRemark(dto.getRemark());

		checkList.setDate(LocalDate.parse(dto.getDate()));
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
		DateTimeFormatter timeFormatter2 = DateTimeFormatter.ofPattern("HH:mm");

		try {
			checkList.setTime(LocalTime.parse(dto.getTime(),timeFormatter));

		} catch (Exception e) {
			checkList.setTime(LocalTime.parse(dto.getTime(),timeFormatter2));

		}
		return checkList;
	}

	
	public CheckListDTO toDTO() {
		return new CheckListDTO(id, date.toString(), time.toString(), moisture, ph, brix, color, taste, impurity,
				quantitySatisfied, quantityUnsatisfied, quantityMix, remark, lot.getLotCode(), qc.getUsername(), qc.getUsername());
	}
	
	public CheckListSettingDTO toSettingDTO() {
		return new CheckListSettingDTO (
				String.valueOf(moisture), 
				String.valueOf(ph) ,String.valueOf(brix) , 
				color, taste, 
				String.valueOf(impurity) ,
				String.valueOf(quantitySatisfied), 
				String.valueOf(quantityUnsatisfied), 
				String.valueOf(quantityMix), remark, 
				String.valueOf(lot.getLotCode()), 
				qc.getUsername());
	}
	
}

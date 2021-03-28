package com.dpm.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import net.bytebuddy.implementation.bind.annotation.Default;

public class CheckListDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotNull(message = "Bạn phải chọn ngày")
	@NotBlank(message = "Bạn phải chọn ngày")
	private String date;

	@NotNull(message = "Bạn phải chọn giờ")
	@NotBlank(message = "Bạn phải chọn giờ")
	private String time;
	
	@Positive(message = "{validation.typeMismatch.float}")
	@DecimalMax(value = "1.00" ,message = "{validation.typeMismatch.float}")
	private Float moisture;
	
	
	@Min(value = 0, message = "{validation.checklist.ph")
	@Max(value = 14, message = "{validation.checklist.ph}")
	private Short ph;
	

	@DecimalMin(value = "0.00", message = "{validation.typeMismatch.float2}")
	@DecimalMax(value = ""+Float.MAX_VALUE ,message = "{validation.typeMismatch.float2}")
	private Float brix;
	
	
	private boolean color;
	private boolean taste;
	
	
	@DecimalMin(value = "0.00", message = "{validation.typeMismatch.float}")
	@DecimalMax(value = "1.00" ,message = "{validation.typeMismatch.float}")
	private Float impurity;

	@Min(value = 0, message = "{validation.typeMismatch.int}")
	@Max(value = Integer.MAX_VALUE ,message = "{validation.typeMismatch.int}")
	
	private Integer quantitySatisfied;

	@Min(value = 0, message = "{validation.typeMismatch.int}")
	@Max(value = Integer.MAX_VALUE ,message = "{validation.typeMismatch.int}")
	private Integer quantityUnsatisfied;

	@Min(value = 0, message = "{validation.typeMismatch.int}")
	@Max(value = Integer.MAX_VALUE ,message = "{validation.typeMismatch.int}")
	private Integer quantityMix;
	
	private String remark;
	
	private String lotCode;
	private String qc;
	private String own;

	public CheckListDTO() {
		super();
	}


	public CheckListDTO(Integer id,
			@NotNull(message = "Bạn phải chọn ngày") @NotBlank(message = "Bạn phải chọn ngày") String date,
			@NotNull(message = "Bạn phải chọn giờ") @NotBlank(message = "Bạn phải chọn giờ") String time,
			@Positive(message = "{validation.typeMismatch.float}") @DecimalMax(value = "1.00", message = "{validation.typeMismatch.float}") Float moisture,
			@Min(value = 0, message = "{validation.checklist.ph") @Max(value = 14, message = "{validation.checklist.ph}") Short ph,
			@DecimalMin(value = "0.00", message = "{validation.typeMismatch.float2}") @DecimalMax(value = "3.4028235E38", message = "{validation.typeMismatch.float2}") Float brix,
			boolean color, boolean taste,
			@DecimalMin(value = "0.00", message = "{validation.typeMismatch.float}") @DecimalMax(value = "1.00", message = "{validation.typeMismatch.float}") Float impurity,
			@Min(value = 0, message = "{validation.typeMismatch.int}") @Max(value = 2147483647, message = "{validation.typeMismatch.int}") Integer quantitySatisfied,
			@Min(value = 0, message = "{validation.typeMismatch.int}") @Max(value = 2147483647, message = "{validation.typeMismatch.int}") Integer quantityUnsatisfied,
			@Min(value = 0, message = "{validation.typeMismatch.int}") @Max(value = 2147483647, message = "{validation.typeMismatch.int}") Integer quantityMix,
			String remark, String lotCode, String qc, String own) {
		super();
		this.id = id;
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
		this.lotCode = lotCode;
		this.qc = qc;
		this.own = own;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
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

	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

	public String getQc() {
		return qc;
	}

	public void setQc(String qc) {
		this.qc = qc;
	}

	public String getOwn() {
		return own;
	}

	public void setOwn(String own) {
		this.own = own;
	}

	@Override
	public String toString() {
		return "CheckListDTO [id=" + id + ", date=" + date + ", time=" + time + ", moisture=" + moisture + ", ph=" + ph
				+ ", brix=" + brix + ", color=" + color + ", taste=" + taste + ", impurity=" + impurity
				+ ", quantitySatisfied=" + quantitySatisfied + ", quantityUnsatisfied=" + quantityUnsatisfied
				+ ", quantityMix=" + quantityMix + ", remark=" + remark + ", lotCode=" + lotCode + ", qc=" + qc
				+ ", own=" + own + "]";
	}

	
	
}

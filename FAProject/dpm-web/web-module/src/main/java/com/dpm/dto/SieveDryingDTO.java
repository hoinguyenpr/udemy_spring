package com.dpm.dto;

import java.io.Serializable;

import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table(name = "sieve_drying")
public class SieveDryingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer typeProductId;

	private String inputDate;

	private String inputTime;

	private Integer machineId;

	private Integer ingredientBatchCode;

	private Float moisture;

	private Float inputTemp;

	private Float pressure;

	private Float outputTemp;

	private Float pH;

	private boolean impurities;

	private boolean size;

	private boolean color;

	private boolean odor;

	private boolean taste;

	private boolean netStat;

	private Integer lotId;
	
	private String status;
	
	private Integer QCId;
	
	private Integer workerId;
	
	private Integer verifierId;
	
	public SieveDryingDTO() {
		super();
	}

	public SieveDryingDTO(Integer id, Integer typeProductId, String inputDate, String inputTime, Integer machineId,
			Integer ingredientBatchCode, Float moisture, Float inputTemp, Float pressure, Float outputTemp, Float pH,
			boolean impurities, boolean size, boolean color, boolean odor, boolean taste, boolean netStat,
			Integer lotId, String status, Integer qCId, Integer workerId, Integer verifierId) {
		super();
		this.id = id;
		this.typeProductId = typeProductId;
		this.inputDate = inputDate;
		this.inputTime = inputTime;
		this.machineId = machineId;
		this.ingredientBatchCode = ingredientBatchCode;
		this.moisture = moisture;
		this.inputTemp = inputTemp;
		this.pressure = pressure;
		this.outputTemp = outputTemp;
		this.pH = pH;
		this.impurities = impurities;
		this.size = size;
		this.color = color;
		this.odor = odor;
		this.taste = taste;
		this.netStat = netStat;
		this.lotId = lotId;
		this.status = status;
		QCId = qCId;
		this.workerId = workerId;
		this.verifierId = verifierId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTypeProductId() {
		return typeProductId;
	}

	public void setTypeProductId(Integer typeProductId) {
		this.typeProductId = typeProductId;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputTime() {
		return inputTime;
	}

	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public Integer getIngredientBatchCode() {
		return ingredientBatchCode;
	}

	public void setIngredientBatchCode(Integer ingredientBatchCode) {
		this.ingredientBatchCode = ingredientBatchCode;
	}

	public Float getMoisture() {
		return moisture;
	}

	public void setMoisture(Float moisture) {
		this.moisture = moisture;
	}

	public Float getInputTemp() {
		return inputTemp;
	}

	public void setInputTemp(Float inputTemp) {
		this.inputTemp = inputTemp;
	}

	public Float getPressure() {
		return pressure;
	}

	public void setPressure(Float pressure) {
		this.pressure = pressure;
	}

	public Float getOutputTemp() {
		return outputTemp;
	}

	public void setOutputTemp(Float outputTemp) {
		this.outputTemp = outputTemp;
	}

	public Float getpH() {
		return pH;
	}

	public void setpH(Float pH) {
		this.pH = pH;
	}

	public boolean isImpurities() {
		return impurities;
	}

	public void setImpurities(boolean impurities) {
		this.impurities = impurities;
	}

	public boolean isSize() {
		return size;
	}

	public void setSize(boolean size) {
		this.size = size;
	}

	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public boolean isOdor() {
		return odor;
	}

	public void setOdor(boolean odor) {
		this.odor = odor;
	}

	public boolean isTaste() {
		return taste;
	}

	public void setTaste(boolean taste) {
		this.taste = taste;
	}

	public boolean isNetStat() {
		return netStat;
	}

	public void setNetStat(boolean netStat) {
		this.netStat = netStat;
	}

	public Integer getLotId() {
		return lotId;
	}

	public void setLotId(Integer lotId) {
		this.lotId = lotId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getQCId() {
		return QCId;
	}

	public void setQCId(Integer qCId) {
		QCId = qCId;
	}

	public Integer getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}

	public Integer getVerifierId() {
		return verifierId;
	}

	public void setVerifierId(Integer verifierId) {
		this.verifierId = verifierId;
	}

	@Override
	public String toString() {
		return "SieveDryingDTO [id=" + id + ", typeProductId=" + typeProductId + ", inputDate=" + inputDate
				+ ", inputTime=" + inputTime + ", machineId=" + machineId + ", ingredientBatchCode="
				+ ingredientBatchCode + ", moisture=" + moisture + ", inputTemp=" + inputTemp + ", pressure=" + pressure
				+ ", outputTemp=" + outputTemp + ", pH=" + pH + ", impurities=" + impurities + ", size=" + size
				+ ", color=" + color + ", odor=" + odor + ", taste=" + taste + ", netStat=" + netStat + ", lotId="
				+ lotId + ", status=" + status + ", QCId=" + QCId + ", workerId=" + workerId + ", verifierId="
				+ verifierId + "]";
	}
	
}

package com.dpm.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.PressingMornitor;
import com.dpm.entity.TypeProduct;

public class PressingMonitorDTO {

	private Integer id;
	
	@NotNull(message = "Type product must not null")
	private Integer typeProductId;
	
	@NotNull(message = "Input time must not null")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate inputDate;
	
	@NotNull(message = "Input time must not null")
	@DateTimeFormat(iso = ISO.TIME)
	private LocalTime inputTime;
	
	@NotNull(message = "Machine not null")
	private Integer machineId;
	
	@NotNull(message = "Ingredient bactch must not null")
	private Integer ingredientBatchId;
	
	@NotNull(message = "Weight must not null")
	@Min(value = 0, message = "Weight must greater than or equal 0")
	private Integer weight;
	
	@NotNull(message = "Net status must not null")
	private boolean netStatusBeforePress;
	
	@NotNull(message = "Pressing time must be not null")
	@Min(value = 0, message = "Pressing time must equal or greater than 0")
	private Integer pressingTime;
	
	private boolean pressureCondition;
	
	@NotNull(message = "Weight coconut milk must be not null")
	@Min(value = 0, message = "Pressing time must equal or greater than 0")
	private Integer weightCoconutMilk;
	
	@NotNull(message = "Weight residue must be not null")
	@Min(value = 0, message = "Pressing time must equal or greater than 0")
	private Integer weightResidue;
	
	private String note;
	
	@NotNull(message = "Status must be not null")
	private Integer status;
	
	@NotNull(message = "Create employee must be not null")
	private Integer createIdEmployee;

	
	public PressingMonitorDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public PressingMonitorDTO(Integer id, Integer typeProductId, LocalDate inputDate, LocalTime inputTime,
			Integer machineId, Integer ingredientBatchId, Integer weight, boolean netStatusBeforePress,
			Integer pressingTime, boolean pressureCondition, Integer weightCoconutMilk, Integer weightResidue,
			String note) {
		this.id = id;
		this.typeProductId = typeProductId;
		this.inputDate = inputDate;
		this.inputTime = inputTime;
		this.machineId = machineId;
		this.ingredientBatchId = ingredientBatchId;
		this.weight = weight;
		this.netStatusBeforePress = netStatusBeforePress;
		this.pressingTime = pressingTime;
		this.pressureCondition = pressureCondition;
		this.weightCoconutMilk = weightCoconutMilk;
		this.weightResidue = weightResidue;
		this.note = note;
	}

	public PressingMonitorDTO(PressingMornitor pm) {
		this.id = pm.getId();
		this.typeProductId = pm.getTypeProduct().getId();
		this.inputDate = pm.getInputTime() != null ? pm.getInputTime().toLocalDate() : LocalDateTime.now().toLocalDate();
		this.inputTime = pm.getInputTime() != null ? pm.getInputTime().toLocalTime() : LocalDateTime.now().toLocalTime();
		this.machineId = pm.getMachine().getId();
		this.ingredientBatchId = pm.getIngredientBatch().getId();
		this.weight = pm.getWeight();
		this.netStatusBeforePress = pm.getNetStatusBeforePress();
		this.pressingTime = pm.getPressingTime();
		this.pressureCondition = pm.isPressureCondition();
		this.weightCoconutMilk = pm.getWeightCoconutMilk();
		this.weightResidue = pm.getWeightResidue();
		this.note = pm.getNote();
		this.createIdEmployee = pm.getCreateEmployee().getId();
		this.status = pm.getStatus();
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

	public LocalDate getInputDate() {
		return inputDate;
	}

	public void setInputDate(LocalDate inputDate) {
		this.inputDate = inputDate;
	}

	public LocalTime getInputTime() {
		return inputTime;
	}

	public void setInputTime(LocalTime inputTime) {
		this.inputTime = inputTime;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public Integer getIngredientBatchId() {
		return ingredientBatchId;
	}

	public void setIngredientBatchId(Integer ingredientBatchId) {
		this.ingredientBatchId = ingredientBatchId;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public boolean isNetStatusBeforePress() {
		return netStatusBeforePress;
	}

	public void setNetStatusBeforePress(boolean netStatusBeforePress) {
		this.netStatusBeforePress = netStatusBeforePress;
	}

	public Integer getPressingTime() {
		return pressingTime;
	}

	public void setPressingTime(Integer pressingTime) {
		this.pressingTime = pressingTime;
	}

	public boolean isPressureCondition() {
		return pressureCondition;
	}

	public void setPressureCondition(boolean pressureCondition) {
		this.pressureCondition = pressureCondition;
	}

	public Integer getWeightCoconutMilk() {
		return weightCoconutMilk;
	}

	public void setWeightCoconutMilk(Integer weightCoconutMilk) {
		this.weightCoconutMilk = weightCoconutMilk;
	}

	public Integer getWeightResidue() {
		return weightResidue;
	}

	public void setWeightResidue(Integer weightResidue) {
		this.weightResidue = weightResidue;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreateIdEmployee() {
		return createIdEmployee;
	}

	public void setCreateIdEmployee(Integer createIdEmployee) {
		this.createIdEmployee = createIdEmployee;
	}

	public PressingMornitor toEntity() {

		TypeProduct tp = new TypeProduct();
		tp.setId(typeProductId);
		
		Machine mc = new Machine();
		mc.setId(machineId);
		
		IngredientBatch ib = new IngredientBatch();
		ib.setId(ingredientBatchId);
		
		return new PressingMornitor(
				id,
				tp,
				inputDate.atTime(inputTime),
				mc,
				ib,
				weight,
				netStatusBeforePress,
				pressingTime,
				pressureCondition,
				weightCoconutMilk,
				weightResidue,
				note,
				status
			);
				
	}

	@Override
	public String toString() {
		return "PressingMonitorDTO [id=" + id + ", typeProduct=" + typeProductId + ", inputDate=" + inputDate
				+ ", inputTime=" + inputTime + ", machine=" + machineId + ", ingredientBatch=" + ingredientBatchId
				+ ", weight=" + weight + ", netStatusBeforePress=" + netStatusBeforePress + ", pressingTime="
				+ pressingTime + ", pressureCondition=" + pressureCondition + ", weightCoconutMilk=" + weightCoconutMilk
				+ ", weightResidue=" + weightResidue + ", note=" + note + ", status=" + status
				+ ", createIdEmployee=" + createIdEmployee + "]";
	}

	
	
}

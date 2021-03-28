package com.dpm.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.dpm.constant.Constants;

@Entity
@Table(name = "pressing_mornitor")
public class PressingMornitor  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_product")
	private TypeProduct typeProduct;

	@Column(name = "input_time")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime inputTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machine")
	private Machine machine;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingredient_batch")
	private IngredientBatch ingredientBatch;

	private Integer weight;

	@Column(name = "net_status_before_press")
	private Boolean netStatusBeforePress;

	@Column(name = "pressing_time")
	private Integer pressingTime;

	@Column(name = "pressure_condition")
	private boolean pressureCondition;

	@Column(name = "weight_coconut_milk")
	private Integer weightCoconutMilk;

	@Column(name = "weight_residue")
	private Integer weightResidue;

	private String note;
	
	@Column(name = "status")
	private Integer status = Constants.STATUS.PENDING;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "create_employee")
	private Employee createEmployee;

	public PressingMornitor() {
		// TODO Auto-generated constructor stub
	}

	public PressingMornitor(Integer id, TypeProduct typeProduct, LocalDateTime inputTime,
			Machine machine, IngredientBatch ingredientBatch, Integer weight,
			Boolean netStatusBeforePress, Integer pressingTime, boolean pressureCondition,
			Integer weightCoconutMilk, Integer weightResidue, String note, Integer status) {
		super();
		this.id = id;
		this.typeProduct = typeProduct;
		this.inputTime = inputTime;
		this.machine = machine;
		this.ingredientBatch = ingredientBatch;
		this.weight = weight;
		this.netStatusBeforePress = netStatusBeforePress;
		this.pressingTime = pressingTime;
		this.pressureCondition = pressureCondition;
		this.weightCoconutMilk = weightCoconutMilk;
		this.weightResidue = weightResidue;
		this.note = note;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypeProduct getTypeProduct() {
		return typeProduct;
	}

	public void setTypeProduct(TypeProduct typeProduct) {
		this.typeProduct = typeProduct;
	}

	public LocalDateTime getInputTime() {
		return inputTime;
	}

	public void setInputTime(LocalDateTime inputTime) {
		this.inputTime = inputTime;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public IngredientBatch getIngredientBatch() {
		return ingredientBatch;
	}

	public void setIngredientBatch(IngredientBatch ingredientBatch) {
		this.ingredientBatch = ingredientBatch;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Boolean getNetStatusBeforePress() {
		return netStatusBeforePress;
	}

	public void setNetStatusBeforePress(Boolean netStatusBeforePress) {
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
	
	public Employee getCreateEmployee() {
		return createEmployee;
	}

	public void setCreateEmployee(Employee createEmployee) {
		this.createEmployee = createEmployee;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, ingredientBatch, inputTime, machine, netStatusBeforePress,
				note, pressingTime, pressureCondition, typeProduct, weight,
				weightCoconutMilk, weightResidue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PressingMornitor))
			return false;
		PressingMornitor other = (PressingMornitor) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(ingredientBatch, other.ingredientBatch)
				&& Objects.equals(inputTime, other.inputTime)
				&& Objects.equals(machine, other.machine)
				&& Objects.equals(netStatusBeforePress, other.netStatusBeforePress)
				&& Objects.equals(note, other.note)
				&& Objects.equals(pressingTime, other.pressingTime)
				&& pressureCondition == other.pressureCondition
				&& Objects.equals(typeProduct, other.typeProduct)
				&& Objects.equals(weight, other.weight)
				&& Objects.equals(weightCoconutMilk, other.weightCoconutMilk)
				&& Objects.equals(weightResidue, other.weightResidue);
	}

	@Override
	public String toString() {
		return "PressingMornitor [id=" + id + ", typeProduct=" + typeProduct + ", inputTime=" + inputTime + ", machine="
				+ machine + ", ingredientBatch=" + ingredientBatch + ", weight=" + weight + ", netStatusBeforePress="
				+ netStatusBeforePress + ", pressingTime=" + pressingTime + ", pressureCondition=" + pressureCondition
				+ ", weightCoconutMilk=" + weightCoconutMilk + ", weightResidue=" + weightResidue + ", note=" + note
				+ ", status=" + status + ", createEmployee=" + createEmployee + "]";
	}
	
	

}

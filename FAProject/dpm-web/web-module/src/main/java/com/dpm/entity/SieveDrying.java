package com.dpm.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dpm.dto.SieveDryingDTO;

@SuppressWarnings("serial")
@Entity
@Table(name = "sieve_drying")
public class SieveDrying implements Serializable {
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "type_product_id")
	private TypeProduct typeProduct;

	@Column(name = "input_date")
	private LocalDate inputDate;

	@Column(name = "input_time")
	private LocalTime inputTime;

	@ManyToOne
	@JoinColumn(name = "machine")
	private Machine machine;

	@ManyToOne
	@JoinColumn(name = "ingredient_batch")
	private IngredientBatch ingredientBatch;

	private float moisture;

	private float inputTemp;

	private float pressure;

	private float outputTemp;

	private float pH;

	private boolean impurities;

	private boolean size;

	private boolean color;

	private boolean odor;

	private boolean taste;

	private boolean netStat;

	private String status;

	private String correctiveAction;

	@ManyToOne
	@JoinColumn(name = "qc_id")
	private Employee qc;

	@ManyToOne
	@JoinColumn(name = "worker_id")
	private Employee worker;

	@ManyToOne
	@JoinColumn(name = "verifier_id")
	private Employee verifier;

	@ManyToOne
	@JoinColumn(name = "lot_id")
	private ProductLot lotCode;

	public SieveDrying() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TypeProduct getTypeProduct() {
		return typeProduct;
	}

	public void setTypeProduct(TypeProduct typeProduct) {
		this.typeProduct = typeProduct;
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

	public float getMoisture() {
		return moisture;
	}

	public void setMoisture(float moisture) {
		this.moisture = moisture;
	}

	public float getInputTemp() {
		return inputTemp;
	}

	public void setInputTemp(float f) {
		this.inputTemp = f;
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	public float getOutputTemp() {
		return outputTemp;
	}

	public void setOutputTemp(float f) {
		this.outputTemp = f;
	}

	public float getpH() {
		return pH;
	}

	public void setpH(float pH) {
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

	

	public ProductLot getLotCode() {
		return lotCode;
	}

	public void setLotCode(ProductLot lotCode) {
		this.lotCode = lotCode;
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

	public ProductLot getLotId() {
		return lotCode;
	}

	public void setLotId(ProductLot lotId) {
		this.lotCode = lotId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCorrectiveAction() {
		return correctiveAction;
	}

	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}

	public Employee getQc() {
		return qc;
	}

	public void setQc(Employee qc) {
		this.qc = qc;
	}

	public Employee getWorker() {
		return worker;
	}

	public void setWorker(Employee worker) {
		this.worker = worker;
	}

	public Employee getVerifier() {
		return verifier;
	}

	public void setVerifier(Employee verifier) {
		this.verifier = verifier;
	}

	@Override
	public String toString() {
		return "SieveDrying [id=" + id + ", typeProduct=" + typeProduct + ", inputDate=" + inputDate + ", inputTime="
				+ inputTime + ", machine=" + machine + ", ingredientBatch=" + ingredientBatch + ", moisture=" + moisture
				+ ", inputTemp=" + inputTemp + ", pressure=" + pressure + ", outputTemp=" + outputTemp + ", pH=" + pH
				+ ", impurities=" + impurities + ", size=" + size + ", color=" + color + ", odor=" + odor + ", taste="
				+ taste + ", netStat=" + netStat + ", status=" + status + ", correctiveAction=" + correctiveAction
				+ ", qc=" + qc + ", worker=" + worker + ", verifier=" + verifier + ", lotCode=" + lotCode + "]";
	}

	public static SieveDrying mapping(SieveDryingDTO dto) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		SieveDrying sieveDrying = new SieveDrying();
		if (dto.getId()!=null) {
			sieveDrying.setId(dto.getId().intValue());
		}
		sieveDrying.setInputDate(LocalDate.parse(dto.getInputDate(), dateFormatter));
		sieveDrying.setInputTime(LocalTime.parse(dto.getInputTime(), timeFormatter));
		sieveDrying.setMoisture(dto.getMoisture().floatValue());
		sieveDrying.setInputTemp(dto.getInputTemp().floatValue());
		sieveDrying.setPressure(dto.getPressure().floatValue());
		sieveDrying.setOutputTemp(dto.getOutputTemp().floatValue());
		sieveDrying.setpH(dto.getpH().floatValue());
		sieveDrying.setImpurities(dto.isImpurities());
		sieveDrying.setSize(dto.isSize());
		sieveDrying.setColor(dto.isColor());
		sieveDrying.setOdor(dto.isOdor());
		sieveDrying.setTaste(dto.isTaste());
		sieveDrying.setNetStat(dto.isNetStat());
		return sieveDrying;
	}
	
	public static SieveDrying mappingDefault(SieveDryingDTO dto) {

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		SieveDrying sieveDrying = new SieveDrying();
		if (dto.getId()!=null) {
			sieveDrying.setId(dto.getId().intValue());
		}
		sieveDrying.setInputDate(LocalDate.parse(dto.getInputDate(), dateFormatter));
		
		if (dto.getMoisture()!=null) {
			sieveDrying.setMoisture(dto.getMoisture().floatValue());
		}else {
			sieveDrying.setMoisture(-1);
		}
		
		if (dto.getInputTemp()!=null) {
			sieveDrying.setInputTemp(dto.getInputTemp().floatValue());
		}else {
			sieveDrying.setInputTemp(-1);
		}
		
		if (dto.getPressure()!=null) {
			sieveDrying.setPressure(dto.getPressure().floatValue());
		}else {
			sieveDrying.setPressure(-1);
		}
		
		if (dto.getOutputTemp()!=null) {
			sieveDrying.setOutputTemp(dto.getOutputTemp().floatValue());
		}else {
			sieveDrying.setOutputTemp(-1);
		}
		
		if (dto.getpH()!=null) {
			sieveDrying.setpH(dto.getpH().floatValue());
		}else {
			sieveDrying.setpH(-1);
		}

		sieveDrying.setImpurities(dto.isImpurities());
		sieveDrying.setSize(dto.isSize());
		sieveDrying.setColor(dto.isColor());
		sieveDrying.setOdor(dto.isOdor());
		sieveDrying.setTaste(dto.isTaste());
		sieveDrying.setNetStat(dto.isNetStat());
		return sieveDrying;
	}

}

package com.dpm.entity;

import java.io.Serializable;
/**
 * author: VuDH11
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.dpm.constant.Constants;
import com.dpm.dto.BrixpHTrackingDTO;

@Entity
@Table(name = "Brix_pH_monitoring_form")
public class TrackingPHBx implements Serializable {

	private static final long serialVersionUID = 1L;
	private int batchNo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	// Ingredient batch
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingredient_batch")
	private IngredientBatch ingredient;

	// Pumping time
	@Column(name = "pumping_time", nullable = false)
	private LocalTime pumpingTime;

	// pressure
	@NotNull
	@PositiveOrZero
	@Column(name = "pressure", nullable = false)
	private int pressure;

	// Mixing

	@NotNull
	@PositiveOrZero
	@Column(name = "pH_pre_mix")
	private int pHPreMix;

	@NotNull
	@PositiveOrZero
	@Column(name = "brix_pre_mix")
	private float BrixPreMix;

	@NotNull
	@PositiveOrZero
	@Column(name = "pH_mix")
	private int pHMix;

	@NotNull
	@PositiveOrZero
	@Column(name = "brix_mix")
	private float BrixMix;

	@NotNull
	@PositiveOrZero
	@Column(name = "pH_homogenizing")
	private int pHHomo;

	@NotNull
	@PositiveOrZero
	@Column(name = "brix_homogenizing") // , nullable = false
	private float BrixHomo;

	// Finished Product

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productLot")
	private ProductLot productLot;

	// Person in charge

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_in_charge")
	private Employee personInCharge;

	// Verifier
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "verifier")
	private Employee verifier;

	// Packing (type of product (F1.5 or ...))
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "packing")
	private Packing packing;

	// Machine
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machine")
	private Machine machine;

	// Date
	@Column(name = "date", nullable = false)
	private LocalDate date;

	// Status
	@Column(name = "status", nullable = false)
	private Integer status = Constants.STATUS.PENDING;
	
	public TrackingPHBx(BrixpHTrackingDTO dto) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.date = LocalDate.parse(dto.getDateCreate(), dateFormatter);
		this.pumpingTime = LocalTime.parse(dto.getPumpingTime());
		this.id = dto.getId();
		IngredientBatch ingredient = new IngredientBatch();
		ingredient.setId(Integer.parseInt(dto.getIngredientId()));
		this.ingredient = ingredient;
		this.pressure = dto.getPressure();
		this.pHPreMix = dto.getpHPreMix();
		BrixPreMix = dto.getBrixPreMix();
		this.pHMix = dto.getpHMix();
		BrixMix = dto.getBrixMix();
		this.pHHomo = dto.getpHHomo();
		this.BrixHomo = dto.getBrixHomo();
		ProductLot product = new ProductLot();
		product.setId(Integer.parseInt(dto.getProductLotId()));
		this.productLot = product;
		Employee personInCharge = new Employee();
		personInCharge.setId(Integer.parseInt(dto.getPersonInChargeId()));
		this.personInCharge = personInCharge;
		Employee verifier = new Employee();
		verifier.setId(Integer.parseInt(dto.getVerifierId()));
		this.verifier = verifier;
		Packing packing = new Packing();
		packing.setId(Integer.parseInt(dto.getPackingId()));
		this.packing = packing;
		Machine machine = new Machine();
		machine.setId(Integer.parseInt(dto.getMachineId()));
		this.machine = machine;
	}

	public TrackingPHBx() {
		super();
	}

	public TrackingPHBx(int batchNo, Integer id, @NotNull IngredientBatch ingredient,
			LocalTime pumpingTime, @NotNull @PositiveOrZero int pressure, @NotNull @PositiveOrZero int pHPreMix,
			@NotNull @PositiveOrZero float brixPreMix, @NotNull @PositiveOrZero int pHMix,
			@NotNull @PositiveOrZero float brixMix, @NotNull @PositiveOrZero int pHHomo,
			@NotNull @PositiveOrZero float brixHomo, @NotNull ProductLot productLot,
			@NotNull Employee personInCharge,
			@NotNull Employee verifier, @NotNull Packing packing,
			@NotNull Machine machine, LocalDate date, int status) {
		super();
		this.batchNo = batchNo;
		this.id = id;
		this.ingredient = ingredient;
		this.pumpingTime = pumpingTime;
		this.pressure = pressure;
		this.pHPreMix = pHPreMix;
		BrixPreMix = brixPreMix;
		this.pHMix = pHMix;
		BrixMix = brixMix;
		this.pHHomo = pHHomo;
		BrixHomo = brixHomo;
		this.productLot = productLot;
		this.personInCharge = personInCharge;
		this.verifier = verifier;
		this.packing = packing;
		this.machine = machine;
		this.date = date;
		this.status = status;
	}

	public int getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public IngredientBatch getIngredient() {
		return ingredient;
	}

	public void setIngredient(IngredientBatch ingredient) {
		this.ingredient = ingredient;
	}

	public LocalTime getPumpingTime() {
		return pumpingTime;
	}

	public void setPumpingTime(LocalTime pumpingTime) {
		this.pumpingTime = pumpingTime;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getpHPreMix() {
		return pHPreMix;
	}

	public void setpHPreMix(int pHPreMix) {
		this.pHPreMix = pHPreMix;
	}

	public float getBrixPreMix() {
		return BrixPreMix;
	}

	public void setBrixPreMix(float brixPreMix) {
		BrixPreMix = brixPreMix;
	}

	public int getpHMix() {
		return pHMix;
	}

	public void setpHMix(int pHMix) {
		this.pHMix = pHMix;
	}

	public float getBrixMix() {
		return BrixMix;
	}

	public void setBrixMix(float brixMix) {
		BrixMix = brixMix;
	}

	public int getpHHomo() {
		return pHHomo;
	}

	public void setpHHomo(int pHHomo) {
		this.pHHomo = pHHomo;
	}

	public float getBrixHomo() {
		return BrixHomo;
	}

	public void setBrixHomo(float brixHomo) {
		BrixHomo = brixHomo;
	}

	public ProductLot getProductLot() {
		return productLot;
	}

	public void setProductLot(ProductLot productLot) {
		this.productLot = productLot;
	}

	public Employee getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(Employee personInCharge) {
		this.personInCharge = personInCharge;
	}

	public Employee getVerifier() {
		return verifier;
	}

	public void setVerifier(Employee verifier) {
		this.verifier = verifier;
	}

	public Packing getPacking() {
		return packing;
	}

	public void setPacking(Packing packing) {
		this.packing = packing;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TrackingPHBx [batchNo=" + batchNo + ", id=" + id + ", ingredient=" + ingredient + ", pumpingTime="
				+ pumpingTime + ", pressure=" + pressure + ", pHPreMix=" + pHPreMix + ", BrixPreMix=" + BrixPreMix
				+ ", pHMix=" + pHMix + ", BrixMix=" + BrixMix + ", pHHomo=" + pHHomo + ", BrixHomo=" + BrixHomo
				+ ", productLot=" + productLot + ", personInCharge=" + personInCharge + ", verifier=" + verifier
				+ ", packing=" + packing + ", machine=" + machine + ", date=" + date + ", status=" + status + "]";
	}


	

}

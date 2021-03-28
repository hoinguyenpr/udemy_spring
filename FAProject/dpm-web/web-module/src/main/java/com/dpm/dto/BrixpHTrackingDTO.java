package com.dpm.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;

import com.dpm.constant.Constants;
import com.dpm.entity.TrackingPHBx;
import com.dpm.service.ProductLotSevice;;

public class BrixpHTrackingDTO {

	Integer id;
	int batchNo;
	@NotBlank(message = "Vui lòng điền mã nguyên liệu")
	String ingredientId;
	@NotBlank(message = "Vui lòng điền người giám sát")
	String verifierId;
	@NotBlank(message = "Vui lòng nhập đúng giá trị ngày")
	String dateCreate;
	@NotBlank(message = "Vui lòng điền người thực hiện")
	String personInChargeId;
	@NotBlank(message = "Vui lòng điền máy SP")
	String machineId;
	@NotBlank(message = "Vui lòng nhập loại sản phẩm")
	String typeProductId;
	@NotBlank(message = "Vui lòng nhập mã lô thành phẩm")
	String productLotId;
	@NotBlank(message = "Vui lòng nhập bao bì lọa sản phẩm")
	String packingId;
	@NotBlank(message = "Vui lòng điền giờ bơm")
	String pumpingTime;
	int pressure;
	@PositiveOrZero(message = "Chỉ nhận giá trị >=0")
	int pHPreMix;
	@PositiveOrZero(message = "Chỉ nhận giá trị >=0")
	float brixPreMix;
	@PositiveOrZero(message = "Chỉ nhận giá trị >=0")
	int pHMix;
	@PositiveOrZero(message = "Chỉ nhận giá trị >=0")
	float brixMix;
	@PositiveOrZero(message = "Chỉ nhận giá trị >=0")
	int pHHomo;
	@PositiveOrZero(message = "Chỉ nhận giá trị >=0")
	float brixHomo;
	

	@Autowired
	ProductLotSevice productLot;

	public BrixpHTrackingDTO(TrackingPHBx form) {
		this.id = form.getId();
		this.ingredientId = form.getIngredient().getId().toString();
		this.verifierId = String.valueOf(form.getVerifier().getId());
		this.dateCreate = String.valueOf(form.getDate());
		this.personInChargeId = String.valueOf(form.getPersonInCharge().getId());
		this.machineId = String.valueOf(form.getMachine().getId());
		this.productLotId = String.valueOf(form.getProductLot().getId());
		this.typeProductId = String.valueOf(form.getProductLot().getTypeProduct().getId());
		this.packingId = String.valueOf(form.getPacking().getId());
		this.pressure = form.getPressure();
		this.pHPreMix = form.getpHPreMix();
		this.brixPreMix = form.getBrixPreMix();
		this.pHMix = form.getpHMix();
		this.brixMix = form.getBrixMix();
		this.pHHomo = form.getpHHomo();
		this.brixHomo = form.getBrixHomo();
	}

	public BrixpHTrackingDTO() {
		super();
	}

	public BrixpHTrackingDTO(Integer id, int batchNo,
			@NotBlank(message = "Vui lòng điền mã nguyên liệu") String ingredientId,
			@NotBlank(message = "Vui lòng điền người giám sát") String verifierId,
			@NotBlank(message = "Vui lòng nhập đúng giá trị ngày") String dateCreate,
			@NotBlank(message = "Vui lòng điền người thực hiện") String personInChargeId,
			@NotBlank(message = "Vui lòng điền máy SP") String machineId,
			@NotBlank(message = "Vui lòng nhập loại sản phẩm") String typeProductId,
			@NotBlank(message = "Vui lòng nhập mã lô thành phẩm") String productLotId,
			@NotBlank(message = "Vui lòng nhập bao bì lọa sản phẩm") String packingId,
			@NotBlank(message = "Vui lòng điền giờ bơm") String pumpingTime, int pressure,
			@PositiveOrZero(message = "Chỉ nhận giá trị >=0") int pHPreMix,
			@PositiveOrZero(message = "Chỉ nhận giá trị >=0") float brixPreMix,
			@PositiveOrZero(message = "Chỉ nhận giá trị >=0") int pHMix,
			@PositiveOrZero(message = "Chỉ nhận giá trị >=0") float brixMix,
			@PositiveOrZero(message = "Chỉ nhận giá trị >=0") int pHHomo,
			@PositiveOrZero(message = "Chỉ nhận giá trị >=0") float brixHomo,
			ProductLotSevice productLot) {
		super();
		this.id = id;
		this.batchNo = batchNo;
		this.ingredientId = ingredientId;
		this.verifierId = verifierId;
		this.dateCreate = dateCreate;
		this.personInChargeId = personInChargeId;
		this.machineId = machineId;
		this.typeProductId = typeProductId;
		this.productLotId = productLotId;
		this.packingId = packingId;
		this.pumpingTime = pumpingTime;
		this.pressure = pressure;
		this.pHPreMix = pHPreMix;
		this.brixPreMix = brixPreMix;
		this.pHMix = pHMix;
		this.brixMix = brixMix;
		this.pHHomo = pHHomo;
		this.brixHomo = brixHomo;
		this.productLot = productLot;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}

	public String getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(String ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getVerifierId() {
		return verifierId;
	}

	public void setVerifierId(String verifierId) {
		this.verifierId = verifierId;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getPersonInChargeId() {
		return personInChargeId;
	}

	public void setPersonInChargeId(String personInChargeId) {
		this.personInChargeId = personInChargeId;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getTypeProductId() {
		return typeProductId;
	}

	public void setTypeProductId(String typeProductId) {
		this.typeProductId = typeProductId;
	}

	public String getProductLotId() {
		return productLotId;
	}

	public void setProductLotId(String productLotId) {
		this.productLotId = productLotId;
	}

	public String getPackingId() {
		return packingId;
	}

	public void setPackingId(String packingId) {
		this.packingId = packingId;
	}

	public String getPumpingTime() {
		return pumpingTime;
	}

	public void setPumpingTime(String pumpingTime) {
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
		return brixPreMix;
	}

	public void setBrixPreMix(float brixPreMix) {
		this.brixPreMix = brixPreMix;
	}

	public int getpHMix() {
		return pHMix;
	}

	public void setpHMix(int pHMix) {
		this.pHMix = pHMix;
	}

	public float getBrixMix() {
		return brixMix;
	}

	public void setBrixMix(float brixMix) {
		this.brixMix = brixMix;
	}

	public int getpHHomo() {
		return pHHomo;
	}

	public void setpHHomo(int pHHomo) {
		this.pHHomo = pHHomo;
	}

	public float getBrixHomo() {
		return brixHomo;
	}

	public void setBrixHomo(float brixHomo) {
		this.brixHomo = brixHomo;
	}

	public ProductLotSevice getProductLot() {
		return productLot;
	}

	public void setProductLot(ProductLotSevice productLot) {
		this.productLot = productLot;
	}

	@Override
	public String toString() {
		return "BrixpHTrackingDTO [id=" + id + ", batchNo=" + batchNo + ", ingredientId="
				+ ingredientId + ", verifierId=" + verifierId + ", dateCreate="
				+ dateCreate + ", personInChargeId=" + personInChargeId + ", machineId="
				+ machineId + ", typeProductId=" + typeProductId + ", productLotId="
				+ productLotId + ", packingId=" + packingId + ", pumpingTime="
				+ pumpingTime + ", pressure=" + pressure + ", pHPreMix=" + pHPreMix
				+ ", brixPreMix=" + brixPreMix + ", pHMix=" + pHMix + ", brixMix="
				+ brixMix + ", pHHomo=" + pHHomo + ", brixHomo=" + brixHomo
				+ ", productLot=" + productLot + "]";
	}

}

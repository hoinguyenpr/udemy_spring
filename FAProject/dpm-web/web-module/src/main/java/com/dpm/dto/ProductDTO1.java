package com.dpm.dto;

import java.time.LocalDate;

import com.dpm.entity.Machine;
import com.dpm.entity.TypeProduct;


public class ProductDTO1 {

	private Integer productId;

	private String productCode;

	
	private String productName;
	
	private Integer residue;
	
	private Integer coconutMilk;
	
	private Double totalWeightInput;

	private String productEntryDate;
	
	private TypeProduct typeProduct;
	
	private Machine machine;
	
	private String status;
	
	private Integer finishedProductDetail;

	private Integer unsatisfiedProduct;

	private Integer waterExtract;

	private Integer coconutSilk;
	
	private Integer lowPh;
	
	private Integer clotted;
	
	private Integer xylon;
	
	private Integer scrap;

	private Integer crusted;

	private Integer residueDetail;
	
	private Integer total;
	
	private String lotNo;

	private String lotName;
	
	private String note;

	public ProductDTO1() {
		super();
	}
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductEntryDate() {
		return productEntryDate;
	}

	public void setProductEntryDate(String productEntryDate) {
		this.productEntryDate = productEntryDate;
	}

	
	public Integer getResidue() {
		return residue;
	}


	public void setResidue(Integer residue) {
		this.residue = residue;
	}


	public Integer getCoconutMilk() {
		return coconutMilk;
	}


	public void setCoconutMilk(Integer coconutMilk) {
		this.coconutMilk = coconutMilk;
	}


	public Double getTotalWeightInput() {
		return totalWeightInput;
	}


	public void setTotalWeightInput(Double totalWeightInput) {
		this.totalWeightInput = totalWeightInput;
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
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getFinishedProductDetail() {
		return finishedProductDetail;
	}

	public void setFinishedProductDetail(Integer finishedProductDetail) {
		this.finishedProductDetail = finishedProductDetail;
	}



	public Integer getUnsatisfiedProduct() {
		return unsatisfiedProduct;
	}



	public void setUnsatisfiedProduct(Integer unsatisfiedProduct) {
		this.unsatisfiedProduct = unsatisfiedProduct;
	}



	public Integer getWaterExtract() {
		return waterExtract;
	}



	public void setWaterExtract(Integer waterExtract) {
		this.waterExtract = waterExtract;
	}



	public Integer getCoconutSilk() {
		return coconutSilk;
	}



	public void setCoconutSilk(Integer coconutSilk) {
		this.coconutSilk = coconutSilk;
	}



	public Integer getLowPh() {
		return lowPh;
	}



	public void setLowPh(Integer lowPh) {
		this.lowPh = lowPh;
	}



	public Integer getClotted() {
		return clotted;
	}



	public void setClotted(Integer clotted) {
		this.clotted = clotted;
	}



	public Integer getXylon() {
		return xylon;
	}



	public void setXylon(Integer xylon) {
		this.xylon = xylon;
	}



	public Integer getScrap() {
		return scrap;
	}



	public void setScrap(Integer scrap) {
		this.scrap = scrap;
	}



	public Integer getCrusted() {
		return crusted;
	}



	public void setCrusted(Integer crusted) {
		this.crusted = crusted;
	}



	public Integer getResidueDetail() {
		return residueDetail;
	}



	public void setResidueDetail(Integer residueDetail) {
		this.residueDetail = residueDetail;
	}



	public Integer getTotal() {
		return total;
	}



	public void setTotal(Integer total) {
		this.total = total;
	}



	public String getLotNo() {
		return lotNo;
	}



	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}



	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}
	
	

	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}
	
	



	public ProductDTO1(Integer productId, String productCode, String productName,
			Integer residue, Integer coconutMilk, Double totalWeightInput,
			String productEntryDate, TypeProduct typeProduct, Machine machine,
			String status, Integer finishedProductDetail, Integer unsatisfiedProduct,
			Integer waterExtract, Integer coconutSilk, Integer lowPh, Integer clotted,
			Integer xylon, Integer scrap, Integer crusted, Integer residueDetail,
			Integer total, String lotNo, String lotName, String note) {
		super();
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.residue = residue;
		this.coconutMilk = coconutMilk;
		this.totalWeightInput = totalWeightInput;
		this.productEntryDate = productEntryDate;
		this.typeProduct = typeProduct;
		this.machine = machine;
		this.status = status;
		this.finishedProductDetail = finishedProductDetail;
		this.unsatisfiedProduct = unsatisfiedProduct;
		this.waterExtract = waterExtract;
		this.coconutSilk = coconutSilk;
		this.lowPh = lowPh;
		this.clotted = clotted;
		this.xylon = xylon;
		this.scrap = scrap;
		this.crusted = crusted;
		this.residueDetail = residueDetail;
		this.total = total;
		this.lotNo = lotNo;
		this.lotName = lotName;
		this.note = note;
	}


	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productCode=" + productCode + ", productName=" + productName
				+ ", residue=" + residue + ", coconutMilk=" + coconutMilk + ", totalWeightInput=" + totalWeightInput
				+ ", productEntryDate=" + productEntryDate + ", status=" + status + "]";
	}
	
}

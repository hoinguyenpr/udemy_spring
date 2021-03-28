package com.dpm.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.dpm.dto.ProductDTO1;

@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer productId;

	@Column(name = "product_code", nullable = false, unique = true)
	private String productCode;

	@Column(name = "product_name", nullable = false)
	private String productName;
	
	/**
	 * ThienNTN1 modify 3h30 12/01/2021
	 */
	@Column(name = "residue")
	private Integer residue;
	
	@Column(name = "coconut_milk")
	private Integer coconutMilk;
	
	@Column(name = "total_weight_input")
	private Double totalWeightInput;

	
	

	@Column(name = "product_entry_date", nullable = false)
	private LocalDate productEntryDate;
	
	
	
	/**
	 * ThienNTN1 modify 3h30 12/01/2021
	 */
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
	private FinishedProduct finishedProduct;

	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private FinishedProductReport finishedProductReport;
	
	@ManyToOne
	private TypeProduct typeProduct;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machine")
	private Machine machine;
	
	private String status;
	
	/**
	 * Modify by ThienNTN1 22/2/2021 1:30 PM
	 */
	@Column(name = "finished_product_detail")
	private Integer finishedProductDetail;

	@Column(name = "unsatisfied_product")
	private Integer unsatisfiedProduct;

	@Column(name = "water_extract")
	private Integer waterExtract;

	@Column(name = "coconut_silk")
	private Integer coconutSilk;
	
	@Column(name = "low_ph")
	private Integer lowPh;
	@Column(name = "clotted")
	private Integer clotted;
	@Column(name = "xylon")
	private Integer xylon;
	
	@Column(name = "scrap")
	private Integer scrap;

	@Column(name = "crusted")
	private Integer crusted;

	@Column(name = "residue_detail")
	private Integer residueDetail;
	
	@Column(name = "total")
	private Integer total;
	
	@Column(name = "lot_no")
	private String lotNo;

	@Column(name = "lot_name")
	private String lotName;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "created_date")
	@CreationTimestamp
	private Date createdDate;

	@Column(name = "modify_date")
	@UpdateTimestamp
	private Date modifyDate;

	public Product() {
		super();
	}
	
	

	public Product(Integer productId, String productCode, String productName, Integer residue, Integer coconutMilk,
			Double totalWeightInput, LocalDate productEntryDate, FinishedProduct finishedProduct,
			FinishedProductReport finishedProductReport, TypeProduct typeProduct, Machine machine, String status) {
		super();
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.residue = residue;
		this.coconutMilk = coconutMilk;
		this.totalWeightInput = totalWeightInput;
		this.productEntryDate = productEntryDate;
		this.finishedProduct = finishedProduct;
		this.finishedProductReport = finishedProductReport;
		this.typeProduct = typeProduct;
		this.machine = machine;
		this.status = status;
	}
	
	public Product(Integer productId, String productCode, String productName,
			Integer residue, Integer coconutMilk, Double totalWeightInput,
			LocalDate productEntryDate, FinishedProduct finishedProduct,
			FinishedProductReport finishedProductReport, TypeProduct typeProduct,
			Machine machine, String status, Integer finishedProductDetail,
			Integer unsatisfiedProduct, Integer waterExtract, Integer coconutSilk,
			Integer lowPh, Integer clotted, Integer xylon, Integer scrap, Integer crusted,
			Integer residueDetail, Integer total, String lotNo, String lotName, String note) {
		super();
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.residue = residue;
		this.coconutMilk = coconutMilk;
		this.totalWeightInput = totalWeightInput;
		this.productEntryDate = productEntryDate;
		this.finishedProduct = finishedProduct;
		this.finishedProductReport = finishedProductReport;
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

	public LocalDate getProductEntryDate() {
		return productEntryDate;
	}

	public void setProductEntryDate(LocalDate productEntryDate) {
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


	public FinishedProduct getFinishedProduct() {
		return finishedProduct;
	}


	public void setFinishedProduct(FinishedProduct finishedProduct) {
		this.finishedProduct = finishedProduct;
	}


	public FinishedProductReport getFinishedProductReport() {
		return finishedProductReport;
	}


	public void setFinishedProductReport(FinishedProductReport finishedProductReport) {
		this.finishedProductReport = finishedProductReport;
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



	public static Product toEntity(ProductDTO1 productDTO1) {
		Product product = new Product();
		
		product.setProductId(productDTO1.getProductId());
		
		product.setProductCode(productDTO1.getProductCode());
		
		product.setProductName(productDTO1.getProductName());

		product.setResidue(productDTO1.getResidue());
		
		
		product.setCoconutMilk(productDTO1.getCoconutMilk());

		product.setTotalWeightInput(productDTO1.getTotalWeightInput());
		
		product.setProductEntryDate(LocalDate.parse(productDTO1.getProductEntryDate()));
		
		product.setTypeProduct(productDTO1.getTypeProduct());
		
		product.setMachine(productDTO1.getMachine());
		
		product.setStatus(productDTO1.getStatus());
		
		product.setFinishedProductDetail(productDTO1.getFinishedProductDetail());
		
		product.setUnsatisfiedProduct(productDTO1.getUnsatisfiedProduct());
		product.setWaterExtract(productDTO1.getWaterExtract());
		product.setCoconutSilk(productDTO1.getCoconutSilk());
		product.setLowPh(productDTO1.getLowPh());
		product.setClotted(productDTO1.getClotted());
		product.setXylon(productDTO1.getXylon());
		product.setScrap(productDTO1.getScrap());
		product.setCrusted(productDTO1.getCrusted());
		product.setResidueDetail(productDTO1.getResidueDetail());
		product.setTotal(productDTO1.getTotal());
		product.setLotNo(productDTO1.getLotNo());
		product.setLotName(productDTO1.getLotName());
		
		product.setNote(productDTO1.getNote());
		return product;
	}
	
	public ProductDTO1 toDTO(Product product) {
		ProductDTO1 productDTO1 = new ProductDTO1();
		
		productDTO1.setProductId(product.getProductId());
		
		productDTO1.setProductCode(product.getProductCode());
		
		productDTO1.setProductName(product.getProductName());

		productDTO1.setResidue(product.getResidue());
		
		
		productDTO1.setCoconutMilk(product.getCoconutMilk());

		productDTO1.setTotalWeightInput(product.getTotalWeightInput());
		
		productDTO1.setProductEntryDate(product.getProductEntryDate().toString());
		
		productDTO1.setTypeProduct(product.getTypeProduct());
		
		productDTO1.setMachine(product.getMachine());
		
		productDTO1.setStatus(product.getStatus());
		
		productDTO1.setFinishedProductDetail(product.getFinishedProductDetail());
		
		productDTO1.setUnsatisfiedProduct(product.getUnsatisfiedProduct());
		productDTO1.setWaterExtract(product.getWaterExtract());
		productDTO1.setCoconutSilk(product.getCoconutSilk());
		productDTO1.setLowPh(product.getLowPh());
		productDTO1.setClotted(product.getClotted());
		productDTO1.setXylon(product.getXylon());
		productDTO1.setScrap(product.getScrap());
		productDTO1.setCrusted(product.getCrusted());
		productDTO1.setResidueDetail(product.getResidueDetail());
		productDTO1.setTotal(product.getTotal());
		productDTO1.setLotNo(product.getLotNo());
		productDTO1.setLotName(product.getLotName());
		
		productDTO1.setNote(product.getNote());
		return productDTO1;
	}

	public int calculateTotal() {
		return this.getFinishedProductDetail() +
				this.getUnsatisfiedProduct() +
				this.getWaterExtract() +
				this.getCoconutSilk() +
				this.getLowPh() +
				this.getClotted() +
				this.getXylon() +
				this.getScrap() + 
				this.getCrusted() +
				this.getResidueDetail();
	}


	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productCode=" + productCode + ", productName=" + productName
				+ ", residue=" + residue + ", coconutMilk=" + coconutMilk + ", totalWeightInput=" + totalWeightInput
				+ ", productEntryDate=" + productEntryDate + ", status=" + status + "]";
	}
	
}

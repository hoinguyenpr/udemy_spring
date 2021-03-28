package com.dpm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * ThienNTN1 modify 3h30 12/01/2021
 */
@Entity
@Table(name = "finished_product_report")
public class FinishedProductReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	

	@Column(name = "finished_product")
	private Integer finishedProduct;

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

	@Column(name = "residue")
	private Integer residue;
	
	@Column(name = "total")
	private Integer total;
	
	@Column(name = "lot_no")
	private String lotNo;

	@Column(name = "lot_name")
	private String lotName;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public FinishedProductReport() {
		super();
	}

	public FinishedProductReport(Integer id, Integer finishedProduct,
			Integer unsatisfiedProduct, Integer waterExtract, Integer coconutSilk,
			Integer lowPh, Integer clotted, Integer xylon, Integer scrap, Integer crusted,
			Integer residue, Integer total, String lotNo, String lotName,
			Product product) {
		super();
		this.id = id;
		this.finishedProduct = finishedProduct;
		this.unsatisfiedProduct = unsatisfiedProduct;
		this.waterExtract = waterExtract;
		this.coconutSilk = coconutSilk;
		this.lowPh = lowPh;
		this.clotted = clotted;
		this.xylon = xylon;
		this.scrap = scrap;
		this.crusted = crusted;
		this.residue = residue;
		this.total = total;
		this.lotNo = lotNo;
		this.lotName = lotName;
		this.product = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFinishedProduct() {
		return finishedProduct;
	}

	public void setFinishedProduct(Integer finishedProduct) {
		this.finishedProduct = finishedProduct;
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

	public Integer getResidue() {
		return residue;
	}

	public void setResidue(Integer residue) {
		this.residue = residue;
	}

	@Override
	public String toString() {
		return "FinishedProductReport [id=" + id + ", finishedProduct=" + finishedProduct
				+ ", unsatisfiedProduct=" + unsatisfiedProduct + ", waterExtract="
				+ waterExtract + ", coconutSilk=" + coconutSilk + ", lowPh=" + lowPh
				+ ", clotted=" + clotted + ", xylon=" + xylon + ", scrap=" + scrap
				+ ", crusted=" + crusted + ", residue=" + residue + ", total=" + total
				+ ", lotNo=" + lotNo + ", lotName=" + lotName + ", product=" + product
				+ "]";
	}


	

	
	

}

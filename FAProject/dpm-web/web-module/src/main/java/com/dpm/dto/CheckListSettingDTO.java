package com.dpm.dto;

import java.io.Serializable;
/**
 * @author LamPQT
 * DTO for Setting Default CheckList
 */
public class CheckListSettingDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
		
	private String moisture;
	
	private String ph;

	private String brix;
	
	private boolean color;
	private boolean taste;
	
	private String impurity;

		
	private String quantitySatisfied;

	
	private String quantityUnsatisfied;

	
	private String quantityMix;
	
	private String remark;
	
	private String lotCode;
	private String qc;
	public CheckListSettingDTO( String moisture, String ph, String brix,
			boolean color, boolean taste, String impurity, String quantitySatisfied,
			String quantityUnsatisfied, String quantityMix, String remark, String lotCode,
			String qc) {
		super();
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
	}
	public CheckListSettingDTO() {
		super();
	}
	public String getMoisture() {
		return moisture;
	}
	public void setMoisture(String moisture) {
		this.moisture = moisture;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getBrix() {
		return brix;
	}
	public void setBrix(String brix) {
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
	public String getImpurity() {
		return impurity;
	}
	public void setImpurity(String impurity) {
		this.impurity = impurity;
	}
	public String getQuantitySatisfied() {
		return quantitySatisfied;
	}
	public void setQuantitySatisfied(String quantitySatisfied) {
		this.quantitySatisfied = quantitySatisfied;
	}
	public String getQuantityUnsatisfied() {
		return quantityUnsatisfied;
	}
	public void setQuantityUnsatisfied(String quantityUnsatisfied) {
		this.quantityUnsatisfied = quantityUnsatisfied;
	}
	public String getQuantityMix() {
		return quantityMix;
	}
	public void setQuantityMix(String quantityMix) {
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
	@Override
	public String toString() {
		return "CheckListSettingDTO [moisture=" + moisture + ", ph="
				+ ph + ", brix=" + brix + ", color=" + color + ", taste=" + taste
				+ ", impurity=" + impurity + ", quantitySatisfied=" + quantitySatisfied
				+ ", quantityUnsatisfied=" + quantityUnsatisfied + ", quantityMix="
				+ quantityMix + ", remark=" + remark + ", lotCode=" + lotCode + ", qc="
				+ qc + "]";
	}
	
	
}

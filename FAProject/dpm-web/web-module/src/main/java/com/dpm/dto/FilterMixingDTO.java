package com.dpm.dto;

public class FilterMixingDTO {
	private String type;
	private String date;
	private String machine;
	
	
	public FilterMixingDTO() {
		super();
	}
	public FilterMixingDTO(String type, String date, String machine) {
		super();
		this.type = type;
		this.date = date;
		this.machine = machine;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}

}

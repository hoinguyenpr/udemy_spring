package com.dpm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author ThuanLV6
 *	this entity is for symbols on scale calibrating form
 */
@Entity
@Table(name = "scale_symbol")
public class ScaleSymbol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "symbol_string", unique =true)
	private String symbolString;

	public ScaleSymbol() {
	}

	public ScaleSymbol(String symbolString) {
		this.symbolString = symbolString;
	}
	
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSymbolString() {
		return symbolString;
	}

	public void setSymbolString(String symbolString) {
		this.symbolString = symbolString;
	}

	@Override
	public String toString() {
		return "ScaleSymbol [id=" + id + ", symbolString=" + symbolString + "]";
	}
	
	
	
}

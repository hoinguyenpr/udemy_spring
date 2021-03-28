package com.dpm.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "machine")
public class Machine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "machine_name")
	private String machineName;

	@Column(name = "machine_code", unique = true)
	private String machineCode;

	@Column(name = "machine_type")
	private String machineType;

	@Column(name = "machine_origin")
	private String machineOrigin;

	@Column(name = "machine_status")
	private String machineStatus;

	@Column(name = "machine_import_date")
	private String machineImportDate;

	@Column(name = "machine_reciever")
	private String machineReciever;

	@Column(name = "amount_of_machine")
	private Integer amountOfMachine;

	// ThienNTN1 modify 3h30 12/01/2021
	@OneToMany(mappedBy = "machine",fetch = FetchType.EAGER)
	private Set<Product> products;

	public Machine() {
		super();
	}
	
	public Machine(String machineCode) {
		super();
		this.machineCode = machineCode;
	}

	public Machine(Integer id, String machineName, String machineCode, String machineType,
			String machineOrigin, String machineStatus, String machineImportDate,
			String machineReciever, Integer amountOfMachine) {
		super();
		this.id = id;
		this.machineName = machineName;
		this.machineCode = machineCode;
		this.machineType = machineType;
		this.machineOrigin = machineOrigin;
		this.machineStatus = machineStatus;
		this.machineImportDate = machineImportDate;
		this.machineReciever = machineReciever;
		this.amountOfMachine = amountOfMachine;
	}

	
	/**
	 * thuanlv6 added this constructor 21/01
	 * @param machineName
	 * @param machineCode
	 * @param machineType
	 * @param machineOrigin
	 * @param machineStatus
	 * @param machineImportDate
	 * @param machineReciever
	 * @param amountOfMachine
	 */
	public Machine(String machineName, String machineCode, String machineType,
			String machineOrigin, String machineStatus, String machineImportDate,
			String machineReciever, Integer amountOfMachine) {
		super();
		this.machineName = machineName;
		this.machineCode = machineCode;
		this.machineType = machineType;
		this.machineOrigin = machineOrigin;
		this.machineStatus = machineStatus;
		this.machineImportDate = machineImportDate;
		this.machineReciever = machineReciever;
		this.amountOfMachine = amountOfMachine;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	public String getMachineOrigin() {
		return machineOrigin;
	}

	public void setMachineOrigin(String machineOrigin) {
		this.machineOrigin = machineOrigin;
	}

	public String getMachineStatus() {
		return machineStatus;
	}

	public void setMachineStatus(String machineStatus) {
		this.machineStatus = machineStatus;
	}

	public String getMachineImportDate() {
		return machineImportDate;
	}

	public void setMachineImportDate(String machineImportDate) {
		this.machineImportDate = machineImportDate;
	}

	public String getMachineReciever() {
		return machineReciever;
	}

	public void setMachineReciever(String machineReciever) {
		this.machineReciever = machineReciever;
	}

	public Integer getAmountOfMachine() {
		return amountOfMachine;
	}

	public void setAmountOfMachine(Integer amountOfMachine) {
		this.amountOfMachine = amountOfMachine;
	}

	@Override
	public String toString() {
		return "Machine [id=" + id + ", machineName=" + machineName + ", machineCode="
				+ machineCode + ", machineType=" + machineType + ", machineOrigin="
				+ machineOrigin + ", machineStatus=" + machineStatus
				+ ", machineImportDate=" + machineImportDate + ", machineReciever="
				+ machineReciever + ", amountOfMachine=" + amountOfMachine + ", products="
				+ products + "]";
	}
	

}
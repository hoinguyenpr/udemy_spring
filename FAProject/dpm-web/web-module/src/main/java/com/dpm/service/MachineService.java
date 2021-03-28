package com.dpm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.entity.Machine;

public interface MachineService {

	public List<Machine> findAllMachine();

	Page<Machine> findAllMachine(Pageable pageable);

	public void deleteMachineById(Integer deleteId);

	public String editOrCreateMachine(Machine machine);

	public Page<Machine> listAllForSearch(Pageable pageable, String search);
	
	//TruongDD: added at 10:00PM 01-14-2021
	public Optional<Machine> get(int machineId);

	public Page<Machine> getListSupplierCodePaging(Pageable pageable);

	// Modify by NguyenND6 14/02/2021 11:00 AM 
	
	public List<Machine> getAll();
	
	/**
	 * ThuanLV6 added findAllByMachineType
	 * @param machineType
	 * @return
	 */
	public List<Machine> findAllByMachineType(String machineType);
	
	// ThuanLV6 added findMachineById 15/01 
	public Machine findMachineById(int id);
	
	// ThuanLV6 added originalSave
	public Machine save(Machine machine);
}
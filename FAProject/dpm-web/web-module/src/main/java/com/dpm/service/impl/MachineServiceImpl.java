package com.dpm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.constant.Constants;
import com.dpm.dao.MachineDAO;
import com.dpm.entity.Machine;
import com.dpm.service.MachineService;

@Service
public class MachineServiceImpl implements MachineService {

	@Autowired
	public MachineDAO machineDao;

	@Override
	public List<Machine> findAllMachine() {
		return machineDao.findAll();
	}

	@Override
	public Page<Machine> findAllMachine(Pageable pageable) {
		return machineDao.findAll(pageable);
	}

	@Override
	public void deleteMachineById(Integer id) {
		machineDao.deleteById(id);

	}

	@Override
	public String editOrCreateMachine(Machine machine) {

		// Create new machine
		if (machine.getId() == 0) {
			try {
				machineDao.save(machine);
				return Constants.SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.FAILED;
			}
		}

		// Edit machine
		else {
			Machine machineDb = machineDao.findById(machine.getId()).get();
			machineDb.setMachineCode(machine.getMachineCode());
			machineDb.setMachineName(machine.getMachineName());
			machineDb.setAmountOfMachine(machine.getAmountOfMachine());
			machineDb.setMachineImportDate(machine.getMachineImportDate());
			machineDb.setMachineOrigin(machine.getMachineOrigin());
			machineDb.setMachineReciever(machine.getMachineReciever());
			machineDb.setMachineStatus(machine.getMachineStatus());
			machineDb.setMachineType(machine.getMachineType());
			try {
				machineDao.save(machineDb);
				return Constants.SUCCESS;

			} catch (Exception e) {
				e.printStackTrace();
				return Constants.FAILED;
			}
		}

	}

	
	@Override
	public Page<Machine> listAllForSearch(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Machine> getListSupplierCodePaging(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	

	// Modify by NguyenND6 14/02/2021 11:00 AM 

	@Override
	public List<Machine> getAll() {
		// TODO Auto-generated method stub
		return machineDao.findAll();
	}
	
	//TruongDD: added at 10:00PM 01-14-2021
	@Override
	public Optional<Machine> get(int machineId) {
		return machineDao.findById(machineId);
	}


//	@Override
//	public Page<Machine> listAllForSearch(Pageable pageable, String search) {
//		// TODO Auto-generated method stub
//		Page<Machine> listSupplier = machineDao.search(pageable, search);
//		return listSupplier;
//
//	}
//
//	@Override
//	public Page<Machine> getListSupplierCodePaging(Pageable pageable) {
//		// TODO Auto-generated method stub
//		return machineDao.findAll(pageable);
//	}

	/**
	 * Thuanlv6 added findAllByMachineType
	 */
	@Override
	public List<Machine> findAllByMachineType(String machineType) {
		
		return machineDao.findAllByMachineType(machineType);
	}

	// ThuanLV6 added find machine by id
	@Override
	public Machine findMachineById(int id) {
		
		return machineDao.findById(id).get();
	}

	@Override
	public Machine save(Machine machine) {
	
		return machineDao.save(machine);
	}

}

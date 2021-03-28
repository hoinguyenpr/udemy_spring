package com.dpm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.Machine;

@Repository
public interface MachineDAO extends JpaRepository<Machine, Integer> {

//	@Query("SELECT e FROM MACHINE e")
//	public Page<Machine> findAllSupplier(Pageable pageable);
//
//	@Query("SELECT a FROM MACHINE a WHERE " + "CONCAT(a.amount_of_machine,' ', a.machine_code,' ', a.machine_import_date,' ', a.machine_name,' ', a.machine_origin,' ', a.machine_reciever,' ', a.machine_status,' ', a.machine_type)" + "LIKE %?1%")
//	public Page<Machine> search(Pageable pageable, String keyword);

	// Modify by NguyenND6 14/02/2021 14:25
	
		public Optional<Machine> findByMachineCode(String code);
		
		/**
		 * ThuanLV6 added findAllByMachineType
		 * @param machineType
		 * @return
		 */
		@Query("SELECT machine FROM Machine machine WHERE machine.machineType=?1")
		public List<Machine> findAllByMachineType(String machineType);
		
		
		//HoiNX1
		Machine getByMachineCode(String machineCode);
}

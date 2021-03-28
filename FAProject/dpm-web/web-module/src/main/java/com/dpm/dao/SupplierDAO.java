package com.dpm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.Supplier;

@Repository
public interface SupplierDAO extends JpaRepository<Supplier, Integer> {
	@Query("SELECT e FROM Supplier e")
	public Page<Supplier> findAllSupplier(Pageable pageable);

	@Query("SELECT a FROM Supplier a WHERE "
			+ "CONCAT(a.supplierCode,' ', a.supplierName,' ', a.phoneNumber)"
			+ "LIKE %?1%")
	public Page<Supplier> search(Pageable pageable, String keyword);

}

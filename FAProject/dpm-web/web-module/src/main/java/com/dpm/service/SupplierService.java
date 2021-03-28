package com.dpm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.entity.Supplier;

public interface SupplierService {
	public List<Supplier> listSupplier();

	public void deleteSupplierById(Integer id);

	public void saveOrUpdateSupplier(Supplier supplier);

	public Page<Supplier> listAllForSearch(Pageable pageable, String search);

	public Page<Supplier> getListSupplierCodePaging(Pageable pageable);
}

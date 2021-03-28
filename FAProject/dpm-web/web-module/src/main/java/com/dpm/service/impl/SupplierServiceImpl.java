package com.dpm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.dao.SupplierDAO;
import com.dpm.entity.Supplier;
import com.dpm.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	public SupplierDAO supplierDao;

	@Override
	public void deleteSupplierById(Integer id) {
		// TODO Auto-generated method stub
		supplierDao.deleteById(id);
	}

	@Override
	public List<Supplier> listSupplier() {
		// TODO Auto-generated method stub
		return supplierDao.findAll();
	}

	@Override
	public void saveOrUpdateSupplier(Supplier supplier) {
		// TODO Auto-generated method stub

		if (supplier.getId() == 0) {
			try {
				supplierDao.save(supplier);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Supplier supDb = supplierDao.findById(supplier.getId()).get();
			supDb.setSupplierCode(supplier.getSupplierCode());
			supDb.setSupplierName(supplier.getSupplierName());
			supDb.setPhoneNumber(supplier.getPhoneNumber());
			supDb.setAddress(supplier.getAddress());
			try {
				supplierDao.save(supDb);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	@Override
	public Page<Supplier> listAllForSearch(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		Page<Supplier> listSupplier = supplierDao.search(pageable, search);
		return listSupplier;

	}

	@Override
	public Page<Supplier> getListSupplierCodePaging(Pageable pageable) {
		// TODO Auto-generated method stub
		return supplierDao.findAll(pageable);
	}

}

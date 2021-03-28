package com.dpm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.dao.FinishedProductDAO;
import com.dpm.entity.FinishedProduct;
import com.dpm.entity.ProductLot;
import com.dpm.service.FinishedProductService;

@Service
public class FinishedProductServiceImpl implements FinishedProductService {
	@Autowired
	private FinishedProductDAO finishedProductDao;

	
	// delete
	@Override
	public void deleteProduct(int id) {
		finishedProductDao.deleteById(id);
	}

	// list
	@Override
	public List<FinishedProduct> getAllFinishProduct() {
		return finishedProductDao.findAll();
	}

	// create and update
	@Override
	public void saveOrUpdateProduct(FinishedProduct finishedProduct) {
		if (finishedProduct.getId() == null) {
			try {
				finishedProductDao.save(finishedProduct);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			FinishedProduct finishedProductDb = finishedProductDao
					.findById(finishedProduct.getId()).get();
		
			finishedProductDb.setNote(finishedProduct.getNote());
			try {
				finishedProductDao.save(finishedProductDb);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Page<FinishedProduct> listAllForSearch(Pageable pageable, String search) {
		Page<FinishedProduct> listFinishedProduct = finishedProductDao.search(pageable,
				search);
		return listFinishedProduct;

	}

	@Override
	public Page<FinishedProduct> getListProductCodePaging(Pageable pageable) {
		return finishedProductDao.findAll(pageable);
	}

	@Override
	public FinishedProduct getProductByProductCode(String productCode) throws Exception {
		return null;
	}
	
	/*
	 * HoiNX1 20/01/2021
	 * */
	@Override
	public List<FinishedProduct> getAllLoThanhPham() {
		// TODO Auto-generated method stub
		List<FinishedProduct> finishedProducts = new ArrayList<FinishedProduct>();
		finishedProducts = finishedProductDao.findAll();
		return finishedProducts;
	}


	/*
	 * HoiNX1 25/01/2021
	 * */
	public List<FinishedProduct> findAllFinishedProductByType(String type) {
		// TODO Auto-generated method stub
		return finishedProductDao.findAllByType(type);
	}

	@Override
	public FinishedProduct getProductByProductCodeAndType(ProductLot lotCode, String type) {
		return finishedProductDao.findByLotCodeAndType(lotCode, type);
	}


	
}

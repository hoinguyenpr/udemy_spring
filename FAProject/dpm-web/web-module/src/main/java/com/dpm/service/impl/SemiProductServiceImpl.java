package com.dpm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.dao.SemiProductDAO;
import com.dpm.entity.FinishedProduct;
import com.dpm.entity.SemiProduct;
import com.dpm.service.SemiProductService;

/**
 * Author: HoiNX1 Modified date: 20/01/2020 11:37 AM Modifier:
 */

@Service
public class SemiProductServiceImpl implements SemiProductService {

	@Autowired
	private SemiProductDAO semiProductDAO;
	
	
	//list
	@Override
	public List<SemiProduct> listSemiProducts() {
		// TODO Auto-generated method stub
		return semiProductDAO.findAll();
	}

	@Override
	public void deleteProduct(int id) {
		// TODO Auto-generated method stub
		semiProductDAO.deleteById(id);
	}
	
	
	//save or update function
	@Override
	public void saveOrUpdateProduct(SemiProduct semiProduct) {
		if (semiProduct.getId() == null) {
			try {
				semiProductDAO.save(semiProduct);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			SemiProduct finishedProductDb = semiProductDAO
					.findById(semiProduct.getId()).get();
		
			finishedProductDb.setNote(semiProduct.getNote());
			try {
				semiProductDAO.save(finishedProductDb);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	@Override
	public SemiProduct getProductByProductCode(String productCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SemiProduct> listAllForSearch(Pageable pageable, String search) {
		Page<SemiProduct> liPageSemiProduct = semiProductDAO.search(pageable, search);
		return liPageSemiProduct;
	}

	@Override
	public Page<SemiProduct> getListProductCodePaging(Pageable pageable) {
		// TODO Auto-generated method stub
		return semiProductDAO.findAll(pageable);
	}
	
	
	/*
	 * HoiNX1 20/01/2020
	 * */
	
	@Override
	public List<SemiProduct> getAllLoThanhPham() {
		// TODO Auto-generated method stub
		List<SemiProduct> semiProducts = new ArrayList<SemiProduct>();
		semiProducts = semiProductDAO.findAll();
		return semiProducts;
	}

}

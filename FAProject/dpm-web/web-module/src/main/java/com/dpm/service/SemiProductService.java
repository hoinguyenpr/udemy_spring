package com.dpm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.entity.SemiProduct;

/**
 * Author:  HoiNX1        Modified date: 14/01/2020 11:37 AM     
 * Modifier: 
 */


public interface SemiProductService {
	// list
	public List<SemiProduct> listSemiProducts();

	// delete
	public void deleteProduct(int id);

	// create update
	public void saveOrUpdateProduct(SemiProduct semiProduct);

	public SemiProduct getProductByProductCode(String productCode) throws Exception;

	public Page<SemiProduct> listAllForSearch(Pageable pageable, String search);

	public Page<SemiProduct> getListProductCodePaging(Pageable pageable);

	public List<SemiProduct> getAllLoThanhPham();
}

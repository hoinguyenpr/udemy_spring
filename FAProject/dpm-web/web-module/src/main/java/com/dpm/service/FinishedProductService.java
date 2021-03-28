package com.dpm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.entity.FinishedProduct;
import com.dpm.entity.ProductLot;

public interface FinishedProductService {
	
	// list
	public List<FinishedProduct> getAllFinishProduct();

	// delete
	public void deleteProduct(int id);

	// create update
	public void saveOrUpdateProduct(FinishedProduct finishedProduct);

	public FinishedProduct getProductByProductCode(String productCode) throws Exception;

	public Page<FinishedProduct> listAllForSearch(Pageable pageable, String search);

	public Page<FinishedProduct> getListProductCodePaging(Pageable pageable);

	public List<FinishedProduct> getAllLoThanhPham();
	
	//HoiNX1 add findAllFinishedProductByType
	public List<FinishedProduct> findAllFinishedProductByType(String type);
	
	public FinishedProduct getProductByProductCodeAndType(ProductLot lotCode, String type);

}

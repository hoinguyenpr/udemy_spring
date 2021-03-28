package com.dpm.service;

import java.util.List;
import java.util.Optional;

import com.dpm.entity.ProductLot;

public interface ProductLotSevice {

	//Add by VuDH11
	ProductLot findById(int id);
	
	Optional<ProductLot> get(int id);

	Optional<ProductLot> findByLotCode(String lotcode);

	// update by DinhDN 15-01-2021 01:34:00
	/**
	 * Function create or update new ProductLot
	 * 
	 * @param productLot
	 * @return True if create or update success, otherwise return false.
	 */
	public Boolean saveOrUpdate(ProductLot productLot);

	//Update by DinhDN 15-01-2021 01:55:00
	/**
	 * Function get list all ProductLot from database
	 * 
	 * @return list object ProductLot.
	 */
	public List<ProductLot> getAllProductLot();

	
	//Update by LamPQT 15-01-2021 08:45:00
		/**
		 * Function get list all ProductLot from database
		 * By TypeProductId
		 * @return list object ProductLot.
		 */
	List<ProductLot> getByType(int id);
}

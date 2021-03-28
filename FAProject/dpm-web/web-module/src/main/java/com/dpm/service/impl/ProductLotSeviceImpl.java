package com.dpm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpm.dao.ProductLotDAO;
import com.dpm.entity.ProductLot;
import com.dpm.service.ProductLotSevice;

@Service
public class ProductLotSeviceImpl implements ProductLotSevice {
	
	//update by VuDH11
	public ProductLot findById(int id) {
		return lotDAO.findById(id).get();
	}
	

	// update by DinhDN 15-01-2021 01:41:00
	private static Logger logger = LogManager.getLogger(TypeProductServiceImpl.class);

	@Autowired
	private ProductLotDAO lotDAO;

	@Override
	public Optional<ProductLot> get(int id) {
		try {
			return lotDAO.findById(id);
		} catch (Exception e) {
			return Optional.empty();
		}

	}

	// update b DinhDN 15-01-2021 01:53:00
	/**
	 * Function get list all ProductLot from database
	 * 
	 * @return list object ProductLot.
	 */
	@Override
	public List<ProductLot> getAllProductLot() {
		// Create new list ProductLot
		List<ProductLot> productLots = new ArrayList<ProductLot>();

		// Try get list productLot in database and write to log file if error.
		try {
			productLots = lotDAO.findAll();
			return productLots;
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage());
			System.out.println("ERROR: " + e.getMessage());
		}
		return productLots;
	}

	// TruongDD: added on 01-14-2021
	public Optional<ProductLot> findByLotCode(String lotCode) {
		try {
			return lotDAO.findByLotCode(lotCode);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	// update by DinhDN 15-01-2021 01:34:00
	/**
	 * Function create or update new ProductLot
	 * 
	 * @param productLot
	 * @return True if create or update success, otherwise return false.
	 */
	@Override
	public Boolean saveOrUpdate(ProductLot productLot) {

		Optional<ProductLot> optional = null;

		try {
			// Try get ProductLot in database with parameter is ProductLotCode
			optional = lotDAO.findByProductLotCode(productLot.getLotCode());

			// Update productLot If ProductLotCode is exists in database.
			if (optional.isPresent()) {
				ProductLot productLotUpdate = optional.get();

				productLotUpdate.setLotCode(productLot.getLotCode());
				productLotUpdate.setDateCreate(productLot.getDateCreate());
				productLotUpdate.setQuantity(productLot.getQuantity());
				productLotUpdate.setTypeProduct(productLot.getTypeProduct());
				productLotUpdate.setCreate(productLot.getCreate());

				lotDAO.save(productLotUpdate);
			} else {
				// If productLotId is not exists in database then create new productLot.
				lotDAO.save(productLot);
			}
			return true;

		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage());
			System.out.println("ERROR: " + e.getMessage());
		}
		return false;
	}

	// Update by LamPQT 15-01-2021 08:45:00
	/**
	 * Function get list all ProductLot from database By TypeProductId
	 * 
	 * @return list object ProductLot.
	 */
	@Override
	public List<ProductLot> getByType(int id) {
		List<ProductLot> productLots = new ArrayList<ProductLot>();

		try {
			productLots =  lotDAO.getAllProductLotByTypeId(id);
			return productLots;
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage());
			System.out.println("ERROR: " + e.getMessage());
		}
		return productLots;
	}

}

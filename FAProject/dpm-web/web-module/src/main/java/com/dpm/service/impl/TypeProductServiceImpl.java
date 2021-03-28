package com.dpm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.dao.TypeProductDAO;
import com.dpm.entity.TypeProduct;
import com.dpm.service.TypeProductService;

@Service
public class TypeProductServiceImpl implements TypeProductService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TypeProductServiceImpl.class);

	@Autowired
	TypeProductDAO typeProductDao;

	// Modify by DinhDN 14-01-2021
	/**
	 * Function get list TypeProduct from database.
	 * 
	 * @return List object TypeProduct
	 */
	@Override
	public List<TypeProduct> getAllTypeProduct() {

		// Create new list typeProduct
		List<TypeProduct> result = new ArrayList<TypeProduct>();

		// Get list typeProduct from database. If can't connect to database write to log
		// file.
		try {
			result = typeProductDao.findAll();
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}

		return result;

	}

	// Modified by Truong 01-13-2021
	// Update By DinhDN 15-01-2021 01:23:00
	/**
	 * Try get TypeProduct by typeProductCode. If typeProductCode is exists in
	 * database return object typeProduct. otherwise if error write error to log
	 * file.
	 * 
	 * @param typeProductCode
	 * @return Object typeProduct;
	 */
	@Override
	public TypeProduct getTypeProductByTypeProductCode(String typeProductCode) {
		TypeProduct typeProduct = new TypeProduct();

		// Try get TypeProduct by typeProductCode. If typeProductCode is exists in
		// database return object typeProduct. otherwise if error write error to log
		// file and return null.
		try {
			typeProduct = typeProductDao.findByTypeProductCode(typeProductCode);
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}
		return typeProduct;
	}

	@Override
	public TypeProduct getTypeProductByTypeProductName(String typeProductName)
			throws Exception {
		TypeProduct typeName = typeProductDao.findByTypeProductName(typeProductName);

		if (typeName != null) {
			return typeName;
		} else {
			throw new Exception("Khong co ten loai san pham da luu");
		}
	}

	// Create by DinhDN 14-01-2021
	/**
	 * Function create or update new TypeProduct in database.
	 * 
	 * @param typeProduct
	 * @return True if create or update success, otherwise false.
	 */
	@Override
	public Boolean saveOrUpdate(TypeProduct typeProduct) {

		TypeProduct typeProductUpdate = null;

		try {
			// Try get typeProduct in database with parameter is typeProductId
			typeProductUpdate = typeProductDao
					.findByTypeProductCode(typeProduct.getTypeProductCode());

			// Update typeProduct If typeProductId is exists in database.
			if (typeProductUpdate != null) {

				typeProductUpdate.setTypeProductCode(typeProduct.getTypeProductCode());
				typeProductUpdate.setTypeProductName(typeProduct.getTypeProductName());
				typeProductUpdate.setNote(typeProduct.getNote());

				typeProductDao.save(typeProductUpdate);
			} else {
				// If typeProductId is not exists in database then create new typeProduct.
				typeProductDao.save(typeProduct);
			}
			return true;

		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}
		return false;
	}

	@Override
	public void deleteByTypeProductCode(Integer id) {
		typeProductDao.deleteById(id);
//		typeProductDao.deleteByMaLoaiSP(maLoaiSanPham);

	}

	@Override
	public boolean saveTypeProductCode(TypeProduct typeProduct) {
		try {
			typeProductDao.save(typeProduct);
		} catch (Exception e) {
			LOGGER.error("Error: " + e.getMessage());
			return false;
		}
		return true;
	}

	// modify by DinhDN 15-01-2021 02:37:00
	@Override
	public Page<TypeProduct> getListTypeProductCodePaging(Pageable pageable) {

		return typeProductDao.getAllTypeProduct(pageable);
	}

	// Modify by DinhDN 14-01-2021
	/**
	 * Function get all typeProduct by keyword
	 * 
	 * @param pageable
	 * @param search
	 * @return List object TypeProduct.
	 */
	@Override
	public Page<TypeProduct> listAllForSearch(Pageable pageable, String search) {
		// Create new list object typeProduct.
		List<TypeProduct> products = new ArrayList<TypeProduct>();
		Page<TypeProduct> listProduct = new PageImpl<TypeProduct>(products);

		// Get typeProduct list from database. If error when read data then write log
		// error to file.
		try {
			listProduct = typeProductDao.searchTypeProduct(search, pageable);
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}

		return listProduct;
	}

	// TruongDD: added on 01-13-2021
	@Override
	public Optional<TypeProduct> get(int id) {
		return typeProductDao.findById(id);
	}

}

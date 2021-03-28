package com.dpm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.entity.TypeProduct;

public interface TypeProductService {

	// Create by DinhDN 14-01-2021
	/**
	 * Function create or update new TypeProduct in database.
	 * 
	 * @param typeProduct
	 * @return True if create or update success, otherwise false.
	 */
	public Boolean saveOrUpdate(TypeProduct typeProduct);

	// Modify by DinhDN 14-01-2021
	/**
	 * Function get list TypeProduct from database.
	 * 
	 * @return List object TypeProduct
	 */
	public List<TypeProduct> getAllTypeProduct();

	/**
	 * Try get TypeProduct by typeProductCode. If typeProductCode is exists in
	 * database return object typeProduct. otherwise if error write error to log
	 * file.
	 * 
	 * @param typeProductCode
	 * @return Object typeProduct;
	 */
	public TypeProduct getTypeProductByTypeProductCode(String typeProductCode);

	public TypeProduct getTypeProductByTypeProductName(String typeProductName)
			throws Exception;

	public void deleteByTypeProductCode(Integer id);

	public boolean saveTypeProductCode(TypeProduct typeProduct);

	public Page<TypeProduct> getListTypeProductCodePaging(Pageable pageable);

	// Modify by DinhDN 14-01-2021
	/**
	 * Function get all typeProduct by keyword
	 * 
	 * @param pageable
	 * @param search
	 * @return List object TypeProduct.
	 */
	public Page<TypeProduct> listAllForSearch(Pageable pageable, String search);

	// TruongDD: added 01-13-2021
	public Optional<TypeProduct> get(int typeProductId);

}

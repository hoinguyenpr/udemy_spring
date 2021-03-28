package com.dpm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE " + "CONCAT(p.productName,' ',p.productCode)"
			+ "LIKE %?1%")
	public Page<Product> findByProductCodeOrProductName(String keyword,
			Pageable pageable);

	@Query("SELECT COUNT(p.productId) FROM Product p WHERE "
			+ "CONCAT(p.productName,' ',p.productCode)" + "LIKE %?1%")
	public int countByProductCodeOrProductName(String keyword);
	
	@Query("select p" + 
			" from  Product	p" + 
			" where p.typeProduct.id = ?1")
	public Page<Product> filterByTypeProductId(int typeProductId, Pageable pageable);
	
	public Product findByProductCode(String productCode);
	
	Boolean existsByProductCode(String productCode);
	
	Boolean existsByProductId(Integer id);
	
	@Query(value = "SELECT * FROM `qams`.`product` WHERE `product`.`status`= 'Pending'", nativeQuery = true)
	public Page<Product> getPending(Pageable pageable);
}

package com.dpm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dpm.dto.ProductDTO1;
import com.dpm.dto.ProductReportDTO;
import com.dpm.dto.SieveDryingReportDTO;
import com.dpm.entity.Employee;
import com.dpm.entity.FinishedProductReport;
import com.dpm.entity.PressingMornitor;
import com.dpm.entity.Product;

public interface ProductService {
	
	Page<Product> listAll(Pageable pageable);

	public void save(Product product);

	public List<Product> getListAllProducts();

	public String editOrCreateProduct(Product product);

	public boolean deleteProductById(Integer productId);

	public Page<Product> getListProductAndPaginationWithSort(Pageable pageable);

	public int getTotalPage(int pageSize);

	public int getTotalPage(int pageSize, String keyword);

	public Sort setSort(String sortBy);

	public Page<Product> searchByCodeOrNameAndPagination(Pageable pageable, String keyword);
	
	
	
	
	//ThienNTN1
	
	public List<Product> listAll();
	
	Product findById(int id);
	
	List<Product> listAllProduct();
	
	Page<Product> listAllPage(Pageable pageable);
	
	public Product get(int id);
	
	//findAllByProductCode
	Page<Product> searchAllProductByProductId(int id,  Pageable pageable);

	Page<Product> getReport(ProductReportDTO rdto, Pageable pageable);

	boolean insert(ProductDTO1 productDTO1);
	boolean update(ProductDTO1 productDTO1);
	
	public String approve(List<Integer> idList);
	public String reject(List<Integer> idList);
	
	public String approve(int id);
	
	public String reject(int id);

	public String deleteSelected(List<Integer> idList);
}

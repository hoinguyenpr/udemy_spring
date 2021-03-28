package com.dpm.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dpm.constant.Constants;
import com.dpm.dao.ProductDAO;
import com.dpm.dao.ReportProductDAO;
import com.dpm.dto.ProductDTO1;
import com.dpm.dto.ProductReportDTO;
import com.dpm.entity.FinishedProductReport;
import com.dpm.entity.Product;
import com.dpm.entity.SieveDrying;
import com.dpm.service.FinishedProductReportService;
import com.dpm.service.ProductService;
import com.dpm.utility.Status;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDao;

	@Override
	public List<Product> getListAllProducts() {
		return productDao.findAll();
	}

	@Override
	public String editOrCreateProduct(Product product) {
		if (product.getProductId() == null) {
			try {
				productDao.save(product);
				return Constants.SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.FAILED;
			}
		}

		// Edit product
		else {
			Product productDb = productDao.findById(product.getProductId()).get();
			productDb.setProductCode(product.getProductCode());
			productDb.setProductName(product.getProductName());
			productDb.setProductEntryDate(product.getProductEntryDate());
			try {
				productDao.save(productDb);
				return Constants.SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.FAILED;
			}
		}
	}

	@Override
	public boolean deleteProductById(Integer productId) {
		try {
			productDao.deleteById(productId);
			return true;
		} catch (Exception e) {
			System.out.println("Class: FinishedProductReportServiceImpl [Func] update: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Page<Product> getListProductAndPaginationWithSort(Pageable pageable) {

		Page<Product> listProducts;

		listProducts = productDao.findAll(pageable);

		if (!listProducts.isEmpty()) {
			return listProducts;
		} else {
			return null;
		}
	}

	@Override
	public int getTotalPage(int pageSize) {

		int total = productDao.findAll().size();

		if (total % pageSize == 0) {
			return (total / pageSize);
		} else {
			return (total / pageSize) + 1;
		}
	}

	@Override
	public int getTotalPage(int pageSize, String keyword) {

		int total = productDao.countByProductCodeOrProductName(keyword);

		if (total % pageSize == 0) {
			return (total / pageSize);
		} else {
			return (total / pageSize) + 1;
		}
	}

	@Override
	public Sort setSort(String sortBy) {

		Sort sort = null;
		if (sortBy.equals("DESC")) {
			sort = Sort.by("productName").descending();
		} else {
			sort = Sort.by("productName").ascending();
		}
		return sort;
	}

	@Override
	public Page<Product> searchByCodeOrNameAndPagination(Pageable pageable, String keyword) {
		return productDao.findByProductCodeOrProductName(keyword, pageable);
	}
	
	

	@Override
	public void save(Product product) {
		productDao.save(product);
    }
     
	
	
	
	@Override
	public List<Product> listAll() {
        return productDao.findAll();
    }

	@Override
	public Product findById(int id) {
		return productDao.getOne(id);
	}

	@Override
	public List<Product> listAllProduct() {
		return productDao.findAll();
	}

	@Override
	public Product get(int id) {
        return productDao.findById(id).get();
    }
     
	@Override
	public Page<Product> listAll(Pageable pageable) {
		return productDao.findAll(pageable);
	}
	
	
	@Override
	public Page<Product> listAllPage(Pageable pageable) {
		return productDao.findAll(pageable);
	}

	@Override
	public Page<Product> searchAllProductByProductId(int id, Pageable pageable) {
		return productDao.filterByTypeProductId(id, pageable);
	
	}
	
	@Autowired
	ReportProductDAO reportProductDAO;
	
	@Override
	public Page<Product> getReport(ProductReportDTO rdto, Pageable pageable) {
		Page<Product> list = null;
		try {
		list = reportProductDAO.getReport(rdto, pageable);
			return list;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return list;
		}
	}

	@Autowired
	FinishedProductReportService finishedProductReportService;
	
	@Override
	public boolean insert(ProductDTO1 productDTO1) {
			try {
				Product product = new Product();
				
				productDTO1.setProductCode("P" + productDTO1.getProductCode());
				productDTO1.setStatus(Status.Pending.toString());
				
				int totalOfProduct = 0;
				
				product = Product.toEntity(productDTO1);
				
				totalOfProduct = product.calculateTotal();
				product.setTotal(totalOfProduct);
				
				productDao.save(product);
				
				return true;
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		return false;
	}
	
	@Override
	public boolean update(ProductDTO1 productDTO1) {
			try {
				System.out.println("======update");
				Product oldProduct = new Product();
				oldProduct = productDao.getOne(productDTO1.getProductId());
				
				productDTO1.setStatus(oldProduct.getStatus());
				Product product = new Product();
				
				int totalOfProduct = 0;
				
				product = Product.toEntity(productDTO1);
				
				totalOfProduct = product.calculateTotal();
				product.setTotal(totalOfProduct);
				
				productDao.save(product);
				
				return true;
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		return false;
	}
	
	@Override
	public String approve(List<Integer> idList) {
		try {
			for (Integer id : idList) {
				Product drying = productDao.findById(id).get();
				drying.setStatus(Status.Approved.toString());
				productDao.save(drying);
			}
			return Constants.SUCCESS;
		} catch (EntityNotFoundException e) {
			LOGGER.info(e.toString());
			return Constants.FAILED;
		}
	}
	
	@Override
	public String reject(List<Integer> idList) {
		try {
			for (Integer id : idList) {
				Product drying = productDao.findById(id).get();
				drying.setStatus(Status.Rejected.toString());
				productDao.save(drying);
			}
			return Constants.SUCCESS;
		} catch (EntityNotFoundException e) {
			LOGGER.info(e.toString());
			return Constants.FAILED;
		}
	}

	@Override
	public String approve(int id) {
		try {
			Product drying = productDao.findById(id).get();
			drying.setStatus(Status.Approved.toString());
			productDao.save(drying);
			return Constants.SUCCESS;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return Constants.FAILED;
		}
	}

	@Override
	public String reject(int id) {
		try {
			Product drying = productDao.findById(id).get();
			drying.setStatus(Status.Rejected.toString());
			productDao.save(drying);
			return Constants.SUCCESS;
		} catch (EntityNotFoundException e) {
			LOGGER.info(e.toString());
			return Constants.FAILED;
		}
	}

	@Override
	public String deleteSelected(List<Integer> idList) {
		try {
			for (Integer id : idList) {
				productDao.deleteById(id);
			}
			return Constants.SUCCESS;
		} catch (EntityNotFoundException e) {
			LOGGER.info(e.toString());
			return Constants.FAILED;
		}
	}
}

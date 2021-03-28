package com.dpm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.dpm.entity.Additive;

public interface AdditiveService {

	// Get list all additives
	public List<Additive> getListAllAdditives();

	// Edit or create new additive
	public String editOrCreateAdditive(Additive additive);

	// Delete additive
	public String deleteAdditiveById(int id);

	// Get list additives and pagination with sort
	public List<Additive> getListAdditiveAndPaginationWithSort(int pageSize,
			int pageNumber, Sort sort);

	// Get total page
	public int getTotalPage(int pageSize);

	public int getTotalPage(int pageSize, String keyword);

	// Set sort
	public Sort setSort(String sortBy);

	// Search by additive code or additive name
	public List<Additive> searchByCodeOrNameAndPagination(int pageSize, int pageNumber,
			Sort sort, String keyword);
	
	// Modify By NguyenND6 14/01/2021 11:30AM
	public List<Additive> getAll();
	public Optional<Additive> getById(int id);
}

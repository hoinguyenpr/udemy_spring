package com.dpm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dpm.constant.Constants;
import com.dpm.dao.AdditiveDAO;
import com.dpm.entity.Additive;
import com.dpm.service.AdditiveService;

@Service("additiveService")
public class AdditiveServiceImpl implements AdditiveService {

	@Autowired
	private AdditiveDAO additiveDao;

	// Get list all additives
	@Override
	public List<Additive> getListAllAdditives() {

		return additiveDao.findAll();
	}

	// Edit or create new additive
	@Override
	public String editOrCreateAdditive(Additive additive) {
		// Create new additive
		if (additive.getId() == 0) {
			try {
				additiveDao.save(additive);
				return Constants.SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.FAILED;
			}
		}

		// Edit additive
		else {
			Additive additiveDb = additiveDao.findById(additive.getId()).get();
			additiveDb.setAdditiveCode(additive.getAdditiveCode());
			additiveDb.setAdditiveName(additive.getAdditiveName());
			try {
				additiveDao.save(additiveDb);
				return Constants.SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.FAILED;
			}
		}
	}

	// Delete by id
	@Override
	public String deleteAdditiveById(int id) {
		try {
			additiveDao.deleteById(id);
			return Constants.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.FAILED;
		}
	}

	// Get list additives and pagination with sort
	@Override
	public List<Additive> getListAdditiveAndPaginationWithSort(int pageSize,
			int pageNumber, Sort sort) {
		Page<Additive> listAdditives;

		// Get data from database
		listAdditives = additiveDao.findAll(PageRequest.of(pageNumber, pageSize, sort));

		// Return list additives
		if (!listAdditives.isEmpty()) {
			return listAdditives.getContent();
		} else {
			return new ArrayList<Additive>();
		}
	}

	@Override
	public int getTotalPage(int pageSize) {
		// Get count all additves
		int total = additiveDao.findAll().size();

		// Return total pages
		if (total % pageSize == 0) {
			return (total / pageSize);
		} else {
			return (total / pageSize) + 1;
		}
	}

	@Override
	public int getTotalPage(int pageSize, String keyword) {
		// Get count all additves
		int total = additiveDao.countByAdditiveCodeOrAdditiveName(keyword);

		// Return total pages
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
			sort = Sort.by("additiveName").descending();
		} else {
			sort = Sort.by("additiveName").ascending();
		}

		return sort;
	}

	// Search by additive code or additive name
	@Override
	public List<Additive> searchByCodeOrNameAndPagination(int pageSize, int pageNumber,
			Sort sort, String keyword) {

		Page<Additive> pageAdditives = additiveDao.findByAdditiveCodeOrAdditiveName(
				keyword, PageRequest.of(pageNumber, pageSize, sort));
		List<Additive> listAdditive = pageAdditives.getContent();

		return listAdditive;
	}
	//Modify By NguyenND6
	@Override
	public List<Additive> getAll(){
		// TODO Auto-generated method stub
		return additiveDao.findAll();
	}

	@Override
	public Optional<Additive> getById(int id) {
		// TODO Auto-generated method stub
		Optional<Additive> opAdditive = additiveDao.findById(id);
		return opAdditive;
	}
}

package com.dpm.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.dao.ScaleCalibrationCustomDao;
import com.dpm.dao.ScaleCalibrationDAO;
import com.dpm.dto.ScaleCalibrationFilterDto;
import com.dpm.entity.ScaleCalibration;
import com.dpm.service.ScaleCalibrationService;
import com.dpm.utility.Status;

@Service
public class ScaleCalibrationServiceImpl implements ScaleCalibrationService {

	@Autowired
	private ScaleCalibrationDAO scaleCalibrationDAO;
	
	@Autowired
	private ScaleCalibrationCustomDao scaleCalibrationCustomDao;
	
	@Override
	public ScaleCalibration saveScaleCalibration(ScaleCalibration scaleCalibration) {
		if(scaleCalibration.getStatus()==null) {
			scaleCalibration.setStatus(Status.Pending.toString());
		}
		scaleCalibration.setModifiedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return scaleCalibrationDAO.save(scaleCalibration);
	}

	@Override
	public void updateScaleCalibration(ScaleCalibration scaleCalibration) {
		scaleCalibration.setModifiedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		scaleCalibrationDAO.save(scaleCalibration);
		
	}

	@Override
	public void deleteScaleCalibration(int id) {
		scaleCalibrationDAO.deleteById(id);
		
	}

	@Override
	public ScaleCalibration findScaleCalibrationById(int id) {
		return scaleCalibrationDAO.findById(id).get();
		
	}

	@Override
	public List<ScaleCalibration> findAll() {
		List<ScaleCalibration> result = scaleCalibrationDAO.findAll();		
		
		// remove element with isDeleted field equals true
		result.removeIf(x -> x.isDeleted());
		return result;
	}

	@Override
	public ScaleCalibration add(ScaleCalibration scaleCalibration) {
		return scaleCalibrationDAO.save(scaleCalibration);
	}

	@Override
	public ScaleCalibration deleteByStatus(int id) {
		ScaleCalibration objectToDelete = scaleCalibrationDAO.findById(id).get();
		objectToDelete.setDeleted(true);
		return scaleCalibrationDAO.save(objectToDelete);
	}

	@Override
	public void saveAll(List<ScaleCalibration> scaleCalibrationList) {
		 Iterable<ScaleCalibration> iterable = scaleCalibrationList;
		 scaleCalibrationDAO.saveAll(iterable);
	
	}

	@Override
	public Page<ScaleCalibration> getPage(Pageable pagable) {
		Page<ScaleCalibration> result = scaleCalibrationDAO.findAllByIsDeletedFalse(pagable);
	
		return result;
	}

	@Override
	public Page<ScaleCalibration> getPageWithSearchAndFilter(ScaleCalibrationFilterDto filterDto, Pageable pageable) {
		Page<ScaleCalibration> result;
		result = scaleCalibrationCustomDao.filter(filterDto, pageable);
		return result;
	}

	@Override
	public List<ScaleCalibration> getReportWithSearchAndFilter(ScaleCalibrationFilterDto filterDto) {
		
		return scaleCalibrationCustomDao.export(filterDto);
	}


}

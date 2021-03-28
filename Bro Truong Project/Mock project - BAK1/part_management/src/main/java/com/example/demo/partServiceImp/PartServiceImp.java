package com.example.demo.partServiceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Utils.Constants;
import com.example.demo.entity.PartDetail;
import com.example.demo.partService.PartService;
import com.example.demo.partServiceDAO.PartServiceDAO;

@Service
public class PartServiceImp implements PartService {
	
	@Autowired
	private PartServiceDAO partServiceDao;
	
	//Save new part definition to database
	@Override
	public String savePart(PartDetail part) {
		String saveStatus="";
		try {
			part.setStatus(Constants.part_status_ACTIVE);
			partServiceDao.save(part);
			saveStatus = "Save success: " + part.toString();
		} catch (Exception e) {
			e.printStackTrace();
			saveStatus = "Save failed: " + part.toString();
		}
		return saveStatus;
	}

	@Override
	public List<PartDetail> getAllPart() {
		List<PartDetail> allPartList = new ArrayList<PartDetail>();
		
		try {
			allPartList = partServiceDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allPartList;
	}

	@Override
	public PartDetail getPartByNumber(String partNumber) {
		PartDetail part = new PartDetail();
		try {
			part = partServiceDao.findByPartNumber(partNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return part;
	}

	@Override
	public String updatePart(PartDetail newPart) {
		String updateStatus="";
		PartDetail oldPart = new PartDetail();
		try {
			oldPart = partServiceDao.findByPartNumber(newPart.getPartNumber());
			oldPart.setPartDesc(newPart.getPartDesc());
			oldPart.setStatus(Constants.part_status_ACTIVE);
			partServiceDao.save(oldPart);
			updateStatus = "Updated success: " + oldPart.toString();
		} catch (Exception e) {
			e.printStackTrace();
			updateStatus = "Update failed: " + newPart.toString();
		}
		return updateStatus;
	}

	@Override
	public String obsoletePart(String partNumber) {
		String updateStatus="";
		PartDetail oldPart = new PartDetail();
		try {
			oldPart = partServiceDao.findByPartNumber(partNumber);
			oldPart.setStatus(Constants.part_status_OBSOLETED);
			partServiceDao.save(oldPart);
			updateStatus = "Obsoleted success: " + oldPart.toString();
		} catch (Exception e) {
			e.printStackTrace();
			updateStatus = "Obsolete failed: " + partNumber;
		}
		return updateStatus;
	}

	@Override
	public List<PartDetail> getAllActive() {
		List<PartDetail> activePartList = new ArrayList<PartDetail>();
		try {
			activePartList = partServiceDao.getAllActive();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activePartList;
	}
	
	
	
}

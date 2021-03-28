package com.dpm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpm.dao.PackingDAO;
import com.dpm.entity.Packing;
import com.dpm.service.PackingService;

@Service
public class PackingServiceImpl implements PackingService{
	@Autowired
	PackingDAO packingDAO;

	@Override
	public void addPacking(Packing packing) {
		// TODO Auto-generated method stub
		packingDAO.save(packing);
	}

	@Override
	public void deletePacking(int id) {
		// TODO Auto-generated method stub
		packingDAO.deleteById(id);
	}

	@Override
	public List<Packing> listAll() {
		// TODO Auto-generated method stub
		List<Packing> list = packingDAO.findAll();
		return list;
	}

	@Override
	public Packing findById(int id) {
		Packing pack = packingDAO.findById(id).get();
		return pack;
	}
	
	
}

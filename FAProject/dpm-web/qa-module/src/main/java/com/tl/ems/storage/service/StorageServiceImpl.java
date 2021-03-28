package com.tl.ems.storage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tl.ems.storage.entity.Storage;
import com.tl.ems.storage.repository.StorageRepository;

/**
 * This class use to implement all abstract method from StorageService interface 
 * @author DangVTH
 *
 */
@Service
public class StorageServiceImpl implements StorageService {

	@Autowired
	private StorageRepository storageRepository;
	
	@Override
	public List<Storage> findAll() {
		
		return storageRepository.findAll();
	}

	@Override
	public List<Storage> search(String q) {
		
		return storageRepository.findByNameContaining(q);
	}

	@Override
	public Storage findOne(Integer id) {
		
		return storageRepository.findById(id).get();
	}

	@Override
	public Storage save(Storage sto) {
		
		return storageRepository.save(sto);
	}

	@Override
	public void delete(Storage sto) {
		
		storageRepository.delete(sto);
	}

	@Override
	public Storage findOne(String name) {
		
		return storageRepository.findByName(name);
	}
	
}

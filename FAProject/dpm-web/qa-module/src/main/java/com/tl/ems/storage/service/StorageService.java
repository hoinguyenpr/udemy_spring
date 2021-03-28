package com.tl.ems.storage.service;

import java.util.List;

import com.tl.ems.storage.entity.Storage;

/**
 * This interface use to abstract method handle data
 * @author DangVTH
 *
 */
public interface StorageService {
	
	List<Storage> findAll();

    List<Storage> search(String q);

    Storage findOne(Integer id);
    
    Storage findOne(String name);

    Storage save(Storage sto);

    void delete(Storage sto);
    
    
}

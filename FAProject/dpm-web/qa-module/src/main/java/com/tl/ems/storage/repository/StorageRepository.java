package com.tl.ems.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tl.ems.storage.entity.Storage;

/**
 * This interface extends from JPA Repository to use available method
 * @author DangVTH
 *
 */
public interface StorageRepository extends JpaRepository<Storage, Integer>{
	
	List<Storage> findByNameContaining(String name);
	Storage findByName(String name);
	
}

package com.tl.ems.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tl.ems.storage.entity.Storage;
import com.tl.ems.storage.repository.StorageRepository;
import com.tl.ems.storage.service.StorageService;

/**
 * StorageModuleApplication class is a class that have a main method to run this module
 * @author DangVTH
 *
 */
@SpringBootApplication
public class StorageModuleApplication implements CommandLineRunner {

	/**
	 * Declare a variable storage service to init dummy data 
	 */
	@Autowired
	private StorageService stoService;
	
	/**
	 * Main method to run this module
	 * @param args
	 */
	public static void main(String[] args) {
		
		SpringApplication.run(StorageModuleApplication.class, args);
		
	}

	/**
	 * Run method to prepare dummy data
	 */
	@Override
	public void run(String... args) throws Exception {
		
		stoService.save(new Storage("Kho 0", 1));
		stoService.save(new Storage("Kho thanh lý", 1));
		
	}
	
}

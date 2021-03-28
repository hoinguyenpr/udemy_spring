package com.tl.ems.storage.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tl.ems.storage.entity.Storage;
import com.tl.ems.storage.repository.StorageRepository;
import com.tl.ems.storage.service.StorageService;

/**
 * The storage controller class is a rest controller. Use to get url from frontend and handle data.
 * @author DangVTH
 *
 */
@RestController
@RequestMapping("api")
public class StorageController {
	
	/**
	 * Declare a variable storage service to handle data 
	 */ 
	@Autowired
	private StorageService stoService;
	
	/**
	 * Get all storage from database
	 * @return List
	 */
	@GetMapping("/list")
	public List<Storage> getAllStorages() {
		
		return stoService.findAll();
		
	}
	

	
	/**
	 * Search storage from database by name
	 * @return List
	 */
	@GetMapping("/{key}/list")
	public List<Storage> searchStorageByName(@PathVariable(value = "key") String key) {
		
		return stoService.search(key);
		
	}
	
	/**
	 * Create a new storage into database
	 * @param sto
	 * @return Storage
	 */
	@PostMapping("/save")
	public Storage createStorage(@Valid @RequestBody Storage sto) {
		
		return stoService.save(sto);
		
	}
	
	/**
	 * Find one storage by storage's id
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/{id}/get")
	public ResponseEntity<Storage> getStorageById(@PathVariable(value = "id") Integer id) {
		
		//Find storage by id
		Storage sto = stoService.findOne(id);
		
		if (sto == null) {
			
			return ResponseEntity.notFound().build();
			
		}
		
		return ResponseEntity.ok().body(sto);

	}
	
	@GetMapping("/get/{name}")
	public ResponseEntity<Storage> getStorageByName(@PathVariable(value = "name") String name) {
		
		//Find storage by name
		Storage sto = stoService.findOne(name);
		
		if (sto == null) {
			
			return null;
			
		}
		
		return ResponseEntity.ok().body(sto);
		
	}
	
	/**
	 * Update a storage in database
	 * @param id
	 * @param stoDetail
	 * @return ResponseEntity
	 */
	@PutMapping("/{id}/update")
	public ResponseEntity<Storage> updateStorage(@PathVariable(value = "id") Integer id, @Valid @RequestBody Storage stoDetail) {
		
		//Find storage by id
		Storage sto = stoService.findOne(id);
		
		if (sto == null) {
			
			return ResponseEntity.notFound().build();
			
		}
		
		//Set new value for storage's name and update
		sto.setName(stoDetail.getName());
		Storage updateStorage = stoService.save(sto);
		
		return ResponseEntity.ok().body(updateStorage);

	}
	
	/**
	 * Delete a storage in database
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<Storage> deleteStorage(@PathVariable(value = "id") Integer id) {
		
		//Find storage by id
		Storage sto = stoService.findOne(id);
		
		if (sto == null) {
			
			return ResponseEntity.notFound().build();
		}
	
		//Delete storage by storage object
		stoService.delete(sto);
		return ResponseEntity.ok().build();

	}
}

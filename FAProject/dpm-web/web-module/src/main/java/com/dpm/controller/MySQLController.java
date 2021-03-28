package com.dpm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dpm.service.MySQLService;


@Controller
@RequestMapping("/database")
public class MySQLController {
	
	@Autowired
	private MySQLService mySQLService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MySQLController.class);

	@RequestMapping(value= {"/back-up/", "/back-up"})
	@ResponseBody
	public ResponseEntity<?> backUpDatabase(HttpServletResponse response){
		
		LOGGER.info("backUpDatabase");
		HttpHeaders headers = new HttpHeaders();
		
		try {
        	File backupFile = mySQLService.backupDatabase();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.set("Content-disposition", "attachment; filename=" + backupFile.getName());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(backupFile));
            
            return ResponseEntity.ok().headers(headers).contentLength(backupFile.length())
            		.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

		} catch (ClassNotFoundException | SQLException | IOException e) {
			LOGGER.error("Backup Failure: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Backup Failure: " + e.getMessage());
		}
		
		return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping("/restore/")
	public RedirectView RestoreDatabase(@RequestParam("file") MultipartFile file, RedirectAttributes model) {
		
		if(mySQLService.restoreDatabase(file)) {
			model.addFlashAttribute("message", "RESTORE_SUCCESS");
		} else {
			model.addFlashAttribute("message", "RESTORE_FAILED");
		}
		
		return new RedirectView("/setting");

	}
	
}

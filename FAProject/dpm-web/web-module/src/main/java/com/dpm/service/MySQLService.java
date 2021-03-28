package com.dpm.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

public interface MySQLService {

	File backupDatabase() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException;
	
	Boolean restoreDatabase(MultipartFile file);
	
}

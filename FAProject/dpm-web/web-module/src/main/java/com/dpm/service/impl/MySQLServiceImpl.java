package com.dpm.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dpm.constant.Constants;
import com.dpm.service.MySQLService;
import com.dpm.smattme.MysqlExportService;
import com.dpm.smattme.MysqlImportService;

@Service
public class MySQLServiceImpl implements MySQLService{

	@Value("${spring.datasource.url}")
	private String mysqlUrl;

	@Value("${spring.datasource.username}")
	private String usernameMysql;

	@Value("${spring.datasource.password}")
	private String passwordMysql;

	@Value("${spring.datasource.driver-class-name}")
	private String driver;
	
	@Value("${backup-folder-path}")
	private String pathOfBackupFolder;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MySQLServiceImpl.class);
	
	@Override
	public File backupDatabase() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		String backupFolder = repareBackupFolder();
		File dbToTempFolder = backupDBToTempFolder(backupFolder);
		return dbToTempFolder;
	}
	
	public Boolean restoreDatabase(MultipartFile file) {
		try {
//			
//			String pathFileRestore = pathOfBackupFolder + File.separator + file.getOriginalFilename();
//			File newfile = new File(pathFileRestore);
//			file.transferTo(newfile);

			byte[] bytes = file.getBytes();

			String sql = new String(bytes);
			
			MysqlImportService  mysqlImportService = MysqlImportService.builder().setSqlString(sql)
					.setJdbcConnString(mysqlUrl).setUsername(usernameMysql).setPassword(passwordMysql)
					.setDeleteExisting(true);
			
			mysqlImportService.importDatabase();
			return true;

		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("restoreDatabase, error:" + e.getCause() + " " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("restoreDatabase, error:" + e.getCause() + " " + e.getMessage());
		}
		return false;
	}

	private File backupDBToTempFolder(String savePath)
			throws IOException, SQLException, ClassNotFoundException, FileNotFoundException {

		// Required properties for exporting of db

		Properties properties = new Properties();

		// properties.setProperty(MysqlExportService.DB_NAME, "fams");

		properties.setProperty(MysqlExportService.DB_USERNAME, usernameMysql);

		properties.setProperty(MysqlExportService.DB_PASSWORD, passwordMysql);

		properties.setProperty(MysqlExportService.JDBC_DRIVER_NAME, driver);

		properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, mysqlUrl);
		
		// set the outputs temp dir

		properties.setProperty(MysqlExportService.TEMP_DIR, savePath);

		properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");

		MysqlExportService mysqlExportService = new MysqlExportService(properties);

		mysqlExportService.export();

		File serverFile = mysqlExportService.getGeneratedZipFile();

		return serverFile;

	}

	private String repareBackupFolder() {

		File backupRootDir = new File(pathOfBackupFolder);

		if (!backupRootDir.exists()) {
			backupRootDir.mkdirs();
		}
		// Set the outputs temp dir

		Date backupDate = new Date();

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String backupDateStr = format.format(backupDate);

		String fileName = Constants.FILE_NAME_DB; // default file name

		String saveFileName = fileName + "_" + backupDateStr + ".sql";

		String savePath = backupRootDir + File.separator + saveFileName;
		return savePath;
	}

}

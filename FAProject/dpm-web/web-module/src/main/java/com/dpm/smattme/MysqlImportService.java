package com.dpm.smattme;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by seun_ on 01-Mar-18.
 *
 */
public class MysqlImportService {

	private String database;
	private String username;
	private String password;
	private String sqlString;
	private String jdbcConnString;
	private String jdbcDriver;
	private boolean deleteExisting;
	private boolean dropExisting;
	private List<String> tables;
	private Logger logger = LoggerFactory.getLogger(MysqlImportService.class);

	private MysqlImportService() {
		this.deleteExisting = false;
		this.dropExisting = false;
		this.tables = new ArrayList<>();
	}

	public boolean reCreateDB() throws SQLException, ClassNotFoundException {

		// Recreate database
		// connect to the database
		Connection connection;
		connection = getConnection();

		Statement statement = connection.createStatement();
		statement.addBatch("DROP DATABASE " + database);
		statement.addBatch("CREATE DATABASE " + database);
		statement.executeLargeBatch();
		statement.close();
		connection.close();
		return true;
	}

	/**
	 *
	 * @return bool
	 * @throws SQLException           exception
	 * @throws ClassNotFoundException exception
	 */
	public boolean importDatabase() throws SQLException, ClassNotFoundException {

		if (!this.assertValidParams()) {
			logger.error("Required Parameters not set or empty \n"
					+ "Ensure database, username, password, sqlString params are configured \n"
					+ "using their respective setters");
			return false;
		}

		// connect to the database
		Connection connection;
		connection = getConnection();

		Statement stmt = connection.createStatement();

		if (deleteExisting || dropExisting) {

			// get all the tables, so as to eliminate delete errors due to non-existent
			// tables
			tables = MysqlBaseService.getAllTables(database, stmt);
			logger.debug("tables found for deleting/dropping: \n" + tables.toString());

			// execute delete query
			for (String table : tables) {

				// if deleteExisting and dropExisting is true
				// skip the deleteExisting query
				// dropExisting will take care of both
				if (deleteExisting && !dropExisting) {
					String delQ = "TRUNCATE FROM " + "`" + table + "`;";
					logger.debug("adding " + delQ + " to batch");
					stmt.addBatch(delQ);
				}

				if (dropExisting) {
					String dropQ = "DROP TABLE IF EXISTS " + "`" + table + "`";
					logger.debug("adding " + dropQ + " to batch");
					stmt.addBatch(dropQ);
				}

			}
		}

		// disable foreign key check
		stmt.addBatch("SET FOREIGN_KEY_CHECKS = 0");

		// now process the sql string supplied
		while (sqlString.contains(MysqlBaseService.SQL_START_PATTERN)) {

			// get the chunk of the first statement to execute
			int startIndex = sqlString.indexOf(MysqlBaseService.SQL_START_PATTERN);
			int endIndex = sqlString.indexOf(MysqlBaseService.SQL_END_PATTERN);

			String executable = sqlString.substring(startIndex, endIndex);
			logger.debug("adding extracted executable SQL chunk to batch : \n" + executable);
			stmt.addBatch(executable);

			// remove the chunk from the whole to reduce it
			sqlString = sqlString.substring(endIndex + 1);

			// repeat
		}

		// add enable foreign key check
		stmt.addBatch("SET FOREIGN_KEY_CHECKS = 1");

		// now execute the batch
		long[] result = stmt.executeLargeBatch();

		String resultString = Arrays.stream(result).mapToObj(String::valueOf).reduce("",
				(s1, s2) -> s1 + ", " + s2 + ", ");
		logger.debug(result.length
				+ " queries were executed in batches for provided SQL String with the following result : \n"
				+ resultString);

		stmt.close();
		connection.close();

		return true;
	}

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection connection;
		if (jdbcConnString == null || jdbcConnString.isEmpty()) {
			connection = MysqlBaseService.connect(username, password, database, jdbcDriver);
		} else {

			if (jdbcConnString.contains("?")) {
				database = jdbcConnString.substring(jdbcConnString.lastIndexOf("/") + 1, jdbcConnString.indexOf("?"));
			} else {
				database = jdbcConnString.substring(jdbcConnString.lastIndexOf("/") + 1);
			}

			logger.debug("database name extracted from connection string: " + database);
			connection = MysqlBaseService.connectWithURL(username, password, jdbcConnString, jdbcDriver);
		}
		return connection;
	}

	/**
	 * This function will check that required parameters are set
	 * 
	 * @return bool
	 */
	private boolean assertValidParams() {
		return username != null && !this.username.isEmpty() && password != null && sqlString != null
				&& !this.sqlString.isEmpty() && ((database != null && !this.database.isEmpty())
						|| (jdbcConnString != null && !jdbcConnString.isEmpty()));
	}

	/**
	 * This function will create a new MysqlImportService instance thereby
	 * facilitating a builder pattern
	 * 
	 * @return MysqlImportService
	 */
	public static MysqlImportService builder() {
		return new MysqlImportService();
	}

	public MysqlImportService setDatabase(String database) {
		this.database = database;
		return this;
	}

	public MysqlImportService setUsername(String username) {
		this.username = username;
		return this;
	}

	public MysqlImportService setPassword(String password) {
		this.password = password;
		return this;
	}

	public MysqlImportService setSqlString(String sqlString) {
		this.sqlString = sqlString;
		return this;
	}

	public MysqlImportService setDeleteExisting(boolean deleteExisting) {
		this.deleteExisting = deleteExisting;
		return this;
	}

	public MysqlImportService setDropExisting(boolean dropExistingTable) {
		this.dropExisting = dropExistingTable;
		return this;
	}

	public MysqlImportService setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
		return this;
	}

	public MysqlImportService setJdbcConnString(String jdbcConnString) {
		this.jdbcConnString = jdbcConnString;
		return this;
	}
}

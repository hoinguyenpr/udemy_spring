package com.dpm.configs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dpm.constant.Constants;
import com.dpm.constant.SecurityConstants;
import com.dpm.dao.AdditiveDAO;
import com.dpm.dao.CheckListDAO;
import com.dpm.dao.DefaultSettingDAO;
import com.dpm.dao.DepartmentDAO;
import com.dpm.dao.EmployeeDAO;
import com.dpm.dao.FinishedProductReportDAO;
import com.dpm.dao.IngredientBatchDAO;
import com.dpm.dao.MachineDAO;
import com.dpm.dao.MixingMaterialDAO;
import com.dpm.dao.PackingDAO;
import com.dpm.dao.PressingMornitorDAO;
import com.dpm.dao.ProductDAO;
import com.dpm.dao.ProductLotDAO;
import com.dpm.dao.RoleDAO;
import com.dpm.dao.SieveDryingDAO;
import com.dpm.dao.SupplierDAO;
import com.dpm.dao.TypeOfPackingDAO;
import com.dpm.dao.TypeProductDAO;
import com.dpm.entity.Additive;
import com.dpm.entity.CheckList;
import com.dpm.entity.DefaultSetting;
import com.dpm.entity.Department;
import com.dpm.entity.Employee;
import com.dpm.entity.FinishedProduct;
import com.dpm.entity.FinishedProductReport;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.MixingMaterial;
import com.dpm.entity.Packing;
import com.dpm.entity.PackingForm;
import com.dpm.entity.PressingMornitor;
import com.dpm.entity.Product;
import com.dpm.entity.ProductLot;
import com.dpm.entity.Role;
import com.dpm.entity.ScaleCalibration;
import com.dpm.entity.ScaleSymbol;
import com.dpm.entity.SieveDrying;
import com.dpm.entity.Supplier;
import com.dpm.entity.TrackingPHBx;
import com.dpm.entity.TypeOfPacking;
import com.dpm.entity.TypeProduct;
import com.dpm.service.DefaultSettingService;
import com.dpm.service.DepartmentService;
import com.dpm.service.EmployeeService;
import com.dpm.service.FinishedProductService;
import com.dpm.service.IngredientBatchService;
import com.dpm.service.MachineService;
import com.dpm.service.PackingFormService;
import com.dpm.service.PackingService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.ScaleCalibrationService;
import com.dpm.service.ScaleSymbolService;
import com.dpm.service.TrackingPHBxFormService;
import com.dpm.service.TypeOfPackingService;
import com.dpm.service.TypeProductService;
import com.dpm.utility.EmployeePosition;
import com.dpm.utility.Status;

@Component
public class DataGeneration {

	// Modify DinhDN Change name.
	private static final Logger LOGGER = LoggerFactory.getLogger(DataGeneration.class);

	@Autowired
	TrackingPHBxFormService trackingForm;

	@Autowired
	IngredientBatchService ingrService;

	@Autowired
	FinishedProductService finishedProductService;

	@Autowired
	private RoleDAO roleDao;

	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private PasswordEncoder passEnd;

	@Autowired
	private SupplierDAO supplierDao;

	@Autowired
	private MachineDAO machineDao;

	@Autowired
	private CheckListDAO checkListDAO;

	@Autowired
	private ProductLotDAO lotDAO;

	@Autowired
	private TypeProductDAO typeProductDAO;

	@Autowired
	private DefaultSettingDAO settingDAO;

	@Autowired
	private IngredientBatchDAO ingredientBatchDao;

	@Autowired
	private TypeProductDAO typeProductDao;
	// thuanlv6 injects
	@Autowired
	private EmployeeService employeeService;

	// thuanlv6
	@Autowired
	private ScaleCalibrationService scaleCalibrationService;

	// thuanlv6
	@Autowired
	private MachineService machineService;

	@Autowired
	private ProductLotDAO productLotDao;

	@Autowired
	private SieveDryingDAO sieveDryingDao;

	@Autowired
	private PackingDAO packingDao;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private FinishedProductReportDAO finishedProductReportDAO;

	@Autowired
	private ScaleSymbolService scaleSymbolService;

	@Autowired
	private DepartmentDAO departmentDAO;

	@Autowired
	private TypeOfPackingDAO typeOfPackingDAO;

	// add by DinhDN
	@Autowired
	private DepartmentService departmentService;

	// add by DinhDN
	@Autowired
	private TypeProductService typeProductService;

	// add by DinhDN
	@Autowired
	private ProductLotSevice productLotSevice;

	// add by HoiNX1
	@Autowired
	private PackingService packingService;

	@Autowired
	private TypeOfPackingService typeOfPackingService;

	@Autowired
	private PackingFormService packingFormService;

	private Random random = new Random();

	@Autowired
	private AdditiveDAO additiveDAO; // Nguyennd6

	@Autowired
	private MixingMaterialDAO mixingMaterialDAO;

	@Autowired
	private PressingMornitorDAO pressingMonitorDao;

	@EventListener(classes = ApplicationReadyEvent.class)
	public void generateDefaultData() {

		createEmployee();
		createSupplier();
		createMachine();
		createIngredientBatch();
		createTypeProduct();
		createProductLot();
		createCheckList();
		createPacking();
		createTypeOfPacking();
		createSieveDrying();
		createDepartment();
		createAdditive(); // NguyenND6

		createDefaultSettingCheckList();
		createFinishedProducts();
		scaleCalibrationDataInit();// ThuanLV6 modified.
		createMixingMaterial();
		createDefaultDataFunctionMeatalDetector();// DinhDN // Create By ThienNTN1
		createProduct();
		createFinishedProductReport();

		createDefaultSettingFinishedProductReport();

		// Add method LongVT7
		createDefaultSettingValueForPressingMonitor();

		createDummyScaleCalibrationRecords(100); // ThuanLV6

		createDefaultSettingSieveDrying();// TruongDD added on 01/27/2021

		// Create sample packingForm by HoiNX1
		createPackingForms();
		// Create default tracking pH Brix form by VuDH11
		createDefaultSettingTrackingForm();

		// Create default Mixing Material
		createDefaultSettingMixingMaterial();
		// Add sample tracking form by VuDH11
		createTrackingForm();

		// Add pressing monitor by LongVT7
		createPressingMonitor();
		LOGGER.info("\nGenerate data success\n");

	}

	/**
	 * @author VuDH11
	 * 
	 */
	@Autowired
	DefaultSettingService defaultSetting;

	private void createTrackingForm() {

		for (int i = 0; i < 7; i++) {
			TrackingPHBx sample = defaultSetting.getDefaultTrackingForm();
			sample.setPumpingTime(LocalTime.now());
			sample.setDate(LocalDate.now());
			trackingForm.addForm(sample);
		}
	}

	private void createPressingMonitor() {
		Employee e = employeeDAO.findEmployeeByUsername("admin");
		TypeProduct typeProduct = typeProductDao.findAll().get(0);
		Machine machine = machineDao.findAll().get(0);
		IngredientBatch ib = ingredientBatchDao.findAll().get(0);

		PressingMornitor pressingMornitor = new PressingMornitor(0, typeProduct, LocalDateTime.now(), machine, ib, 300,
				true, 200, true, 150, 150, "", Constants.STATUS.PENDING);
		pressingMornitor.setCreateEmployee(e);
		for (int i = 0; i < 10; i++) {
			pressingMonitorDao.save(pressingMornitor);
		}
		pressingMornitor.setStatus(Constants.STATUS.VERIFIED);
		pressingMonitorDao.save(pressingMornitor);
		pressingMonitorDao.save(pressingMornitor);

		pressingMornitor.setStatus(Constants.STATUS.REJECTED);
		pressingMonitorDao.save(pressingMornitor);
		pressingMonitorDao.save(pressingMornitor);

		pressingMornitor.setStatus(Constants.STATUS.DELETE);
		pressingMonitorDao.save(pressingMornitor);
		pressingMonitorDao.save(pressingMornitor);
	}

	private void createDefaultSettingTrackingForm() {
		LOGGER.info("Generate setting value for prssing monitor");

//		TypeProduct typeProduct = typeProductDAO.findAll().get(0);
		Machine machine = machineDao.findAllByMachineType(Constants.MACHINE_TYPE.PRESSING).get(0);
		IngredientBatch ingredientBatch = ingredientBatchDao.findAll().get(0);
		Employee personInCharge = employeeDAO.findAll().get(0);
		Employee verifier = employeeDAO.findAll().get(1);
		ProductLot productLot = productLotDao.findAll().get(1);
		Packing packing = packingDao.findAll().get(0);

		DefaultSetting df_productLot = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM,
				"PRODUCT_LOT", productLot.getId().toString());
		DefaultSetting df_machine = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM, "MACHINE",
				machine.getId().toString());
		DefaultSetting df_ingredientBatch = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM,
				"INGREDIENT_BATCH", ingredientBatch.getId().toString());
		DefaultSetting df_personInCharge = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM,
				"PERSON_IN_CHARGE", personInCharge.getId().toString());
		DefaultSetting df_verifier = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM,
				"VERIFIER", verifier.getId().toString());
		DefaultSetting df_packing = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM, "PACKING",
				packing.getId().toString());

		DefaultSetting df_pressure = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM,
				"PRESSURE", "2");
		DefaultSetting df_pHPreMix = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM,
				"PH_PREMIX", "2");
		DefaultSetting df_pHMix = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM, "PH_MIX",
				"2");
		DefaultSetting df_pHHomo = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM, "PH_HOMO",
				"2");
		DefaultSetting df_brixPreMix = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM,
				"BRIX_PREMIX", "1.3");
		DefaultSetting df_brixMix = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM,
				"BRIX_MIX", "1.3");
		DefaultSetting df_brixHomo = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM,
				"BRIX_HOMO", "1.3");

		settingDAO.saveAll(Arrays.asList(df_productLot, df_machine, df_ingredientBatch, df_personInCharge, df_verifier,
				df_packing, df_pressure, df_pHPreMix, df_pHMix, df_pHHomo, df_brixPreMix, df_brixMix, df_brixHomo));

		LOGGER.info("Generated setting value for prssing monitor");
	}

	private void createPacking() {
		LOGGER.info("Create Packing form");
		packingDao.saveAll(Arrays.asList(new Packing("F1.5", "1.5 kg"), new Packing("F2.9", "2.0 kg")));

		if (packingDao.findAll().size() < 5) {
			for (int i = 0; i < 5; i++) {
				packingDao.save(new Packing(i + " kg", "F" + i));
			}
		}
		LOGGER.info("Create packing form finished");
	}

	/**
	 * @author HoiNX1 defaultDataPackingForm()
	 * @author LongVT7: change method name
	 */

	private void createFinishedProducts() {

		LOGGER.info("Create data for finished product");

		if (productLotDao.count() == 0) {
			createProductLot();
		}

		List<ProductLot> listProductLots = productLotDao.findAll();

		if (finishedProductService.findAllFinishedProductByType(Constants.SEMI_PRODUCT).size() < 3) {
			finishedProductService.saveOrUpdateProduct(
					new FinishedProduct(listProductLots.get(1), Constants.SEMI_PRODUCT, null, "Note 1"));
			finishedProductService.saveOrUpdateProduct(
					new FinishedProduct(listProductLots.get(2), Constants.SEMI_PRODUCT, null, "Note 2"));
			finishedProductService.saveOrUpdateProduct(
					new FinishedProduct(listProductLots.get(3), Constants.SEMI_PRODUCT, null, "Note 3"));
			finishedProductService.saveOrUpdateProduct(
					new FinishedProduct(listProductLots.get(0), Constants.SEMI_PRODUCT, null, "Note 4"));
		}

		if (finishedProductService.findAllFinishedProductByType(Constants.FINISHED_PRODUCT).size() < 3) {
			finishedProductService.saveOrUpdateProduct(
					new FinishedProduct(listProductLots.get(1), Constants.FINISHED_PRODUCT, null, "Note 1"));
			finishedProductService.saveOrUpdateProduct(
					new FinishedProduct(listProductLots.get(2), Constants.FINISHED_PRODUCT, null, "Note 2"));
			finishedProductService.saveOrUpdateProduct(
					new FinishedProduct(listProductLots.get(3), Constants.FINISHED_PRODUCT, null, "Note 3"));
			finishedProductService.saveOrUpdateProduct(
					new FinishedProduct(listProductLots.get(0), Constants.FINISHED_PRODUCT, null, "Note 4"));
		}
	}

	private void createPackingForms() {

		PackingForm packingForm1 = new PackingForm();

		packingForm1.setPackingDate(LocalDate.now());
		packingForm1.setTime(LocalTime.now());
		packingForm1.setPackingQuanity(10);
		packingForm1.setQuantity(100);
		packingForm1.setShift(1);
		packingForm1.setSttNo(12);
		packingForm1.setStatus(Status.Pending);
		packingForm1.setCreateman(employeeService.getAllEmployee().get(0));
		packingForm1.setFinishedProduct(
				finishedProductService.findAllFinishedProductByType(Constants.FINISHED_PRODUCT).get(0));
		packingForm1.setForeman(employeeService.getAllEmployee().get(1));
		packingForm1.setMachine(machineService.getAll().get(1));
		packingForm1.setPacking(packingService.listAll().get(1));
		packingForm1.setPersonInCharge(employeeService.getAllEmployee().get(1));
		packingForm1.setSemiProduct(finishedProductService.findAllFinishedProductByType(Constants.SEMI_PRODUCT).get(1));
		packingForm1.setTypeProduct(typeProductService.getAllTypeProduct().get(1));
		packingForm1.setTypeofpacking(typeOfPackingService.getAllTypePacking().get(1));

		packingFormService.save(packingForm1);

		PackingForm packingForm2 = new PackingForm();

		packingForm2.setPackingDate(LocalDate.now());
		packingForm2.setTime(LocalTime.now());
		packingForm2.setPackingQuanity(15);
		packingForm2.setQuantity(100);
		packingForm2.setShift(1);
		packingForm2.setSttNo(12);
		packingForm2.setStatus(Status.Pending);
		packingForm2.setCreateman(employeeService.getAllEmployee().get(1));
		packingForm2.setFinishedProduct(
				finishedProductService.findAllFinishedProductByType(Constants.FINISHED_PRODUCT).get(2));
		packingForm2.setForeman(employeeService.getAllEmployee().get(1));
		packingForm2.setMachine(machineService.getAll().get(2));
		packingForm2.setPacking(packingService.listAll().get(0));
		packingForm2.setPersonInCharge(employeeService.getAllEmployee().get(1));
		packingForm2.setSemiProduct(finishedProductService.findAllFinishedProductByType(Constants.SEMI_PRODUCT).get(2));
		packingForm2.setTypeProduct(typeProductService.getAllTypeProduct().get(2));
		packingForm2.setTypeofpacking(typeOfPackingService.getAllTypePacking().get(0));

		packingFormService.save(packingForm2);

	}

	private void createTypeOfPacking() {
		LOGGER.info("Create data for type of packing");
		if (typeOfPackingDAO.findAll().isEmpty()) {
			TypeOfPacking typeOfPacking1HoiNX1 = new TypeOfPacking("PL", "Plastic", "Note 1");
			TypeOfPacking typeOfPacking2HoiNX1 = new TypeOfPacking("PVC", "Poliester", "Note 2");
			TypeOfPacking typeOfPacking3HoiNX1 = new TypeOfPacking("CT", "Carton", "Note 3");
			typeOfPackingDAO.saveAll(Arrays.asList(typeOfPacking1HoiNX1, typeOfPacking2HoiNX1, typeOfPacking3HoiNX1));
		}
		LOGGER.info("Created data for type of packing");
	}

	/**
	 * @author LamPQT createCheckList()
	 * @author LongVT7: update logger
	 */
	private void createCheckList() {

		LOGGER.info("Create Checklist data");
		ProductLot lot = lotDAO.findByLotCode("lot001").get();
		Employee e = employeeService.findByPosition("Qc").get(0);
		Employee e1 = employeeService.findByPosition("Qc").get(1);

		List<CheckList> checkLists = new ArrayList<CheckList>();
		for (int i = 0; i < 20; i++) {
			CheckList c = new CheckList(LocalDate.now(), LocalTime.now().plusHours((long) i), (float) 0.01, (short) 1,
					(float) 0.01, true, true, (float) 0.01, 1000, 0, 1000, "", Status.Pending, lot, e, e, null);
			checkLists.add(c);
		}

		for (int i = 0; i < 20; i++) {
			CheckList c = new CheckList(LocalDate.now(), LocalTime.now().plusHours((long) i), (float) 0.01, (short) 1,
					(float) 0.01, false, false, (float) 0.01, 1000, 0, 1000, "", Status.Approved, lot, e1, e1, null);
			checkLists.add(c);
		}
		checkListDAO.saveAll(checkLists);
		LOGGER.info("Generated Checklist data");
	}

	/**
	 * @author TruongDD
	 * @author LongVT7: update code
	 */
	private void createSieveDrying() {

		LOGGER.info("Generate sieve drying data");

		if (productLotDao.count() == 0) {
			createProductLot();
		}

		if (typeProductDao.count() == 0) {
			createTypeProduct();
		}

		List<TypeProduct> listTypeProducts = typeProductDao.findAll();
		List<ProductLot> listProductLots = productLotDao.findAll();

		long minDay = LocalDate.of(2021, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2021, 12, 31).toEpochDay();

		for (int i = 1; i <= 50; i++) {
			SieveDrying sieveDrying = new SieveDrying();
			long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
			LocalDate inputDate = LocalDate.ofEpochDay(randomDay);
			LocalTime inputTime = LocalTime.of(random.nextInt(23), random.nextInt(59));
			sieveDrying.setWorker(employeeService.getEmployeeById(1));
			sieveDrying.setQc(employeeService.getEmployeeById(2));
			sieveDrying.setVerifier(employeeService.getEmployeeById(3));
			sieveDrying.setTypeProduct(listTypeProducts.get((random.nextInt(listTypeProducts.size() - 1))));
			sieveDrying.setInputDate(inputDate);
			sieveDrying.setInputTime(inputTime);
			sieveDrying.setMachine(machineDao.findById(1 + random.nextInt(2)).get());
			sieveDrying.setIngredientBatch(ingredientBatchDao.findById(1 + random.nextInt(2)).get());
			sieveDrying.setLotId(listProductLots.get(random.nextInt(listProductLots.size() - 1)));
			sieveDrying.setMoisture(5 * Precision.round(random.nextFloat(), 2));
			sieveDrying.setInputTemp(140 + 10 * Precision.round(random.nextFloat(), 2));
			sieveDrying.setPressure(100 * Precision.round(random.nextFloat(), 2));
			sieveDrying.setOutputTemp(80 + 10 * Precision.round(random.nextFloat(), 2));
			sieveDrying.setpH(6 + Precision.round(random.nextFloat(), 2));
			sieveDrying.setImpurities(true);
			sieveDrying.setSize(true);
			sieveDrying.setColor(true);
			sieveDrying.setOdor(true);
			sieveDrying.setTaste(true);
			sieveDrying.setNetStat(true);
			sieveDrying.setStatus(Status.Pending.toString());
			sieveDryingDao.save(sieveDrying);
		}

		LOGGER.info("Generated sieve drying data");

	}

	/**
	 * @author LongVT7
	 */
	private void createRoles() {
		LOGGER.info("Generate roles data");

		if (roleDao.findRoleByRole(SecurityConstants.ROLEs.ADMIN) == null) {
			roleDao.save(new Role(0, SecurityConstants.ROLEs.ADMIN));
		}

		if (roleDao.findRoleByRole(SecurityConstants.ROLEs.EMPLOYEE) == null) {
			roleDao.save(new Role(0, SecurityConstants.ROLEs.EMPLOYEE));
		}

		LOGGER.info("Generated roles data");
	}

	/**
	 * @author LongVT7
	 */
	private void createEmployee() {

		LOGGER.info("Generate employee data");

		String defPass = "123";

		if (roleDao.count() < 2) {
			createRoles();
		}

		Employee em = new Employee(1, "employee", EmployeePosition.Worker.toString(), null, null, "");
		Employee ad = new Employee(2, "admin", EmployeePosition.Qc.toString(), null, null, "");
		Employee mn = new Employee(3, "manager", EmployeePosition.Verifier.toString(), null, null, "");

		em.setUsername("user");
		em.setPassword(passEnd.encode(defPass));
		em.setRole(new HashSet<Role>(Arrays.asList(roleDao.findRoleByRole(SecurityConstants.ROLEs.EMPLOYEE))));

		ad.setUsername("admin");
		ad.setPassword(passEnd.encode(defPass));
		ad.setRole(new HashSet<Role>(Arrays.asList(roleDao.findRoleByRole(SecurityConstants.ROLEs.ADMIN))));

		mn.setUsername("manager");
		mn.setPassword(passEnd.encode(defPass));
		mn.setRole(new HashSet<Role>(Arrays.asList(roleDao.findRoleByRole(SecurityConstants.ROLEs.ADMIN),
				roleDao.findRoleByRole(SecurityConstants.ROLEs.EMPLOYEE))));

		if (employeeDAO.findEmployeeByUsername(em.getUsername()) == null) {
			employeeDAO.save(em);
		}

		if (employeeDAO.findEmployeeByUsername(ad.getUsername()) == null) {
			employeeDAO.save(ad);
		}

		if (employeeDAO.findEmployeeByUsername(mn.getUsername()) == null) {
			employeeDAO.save(mn);
		}

		// ThuanLV6 add scale-calibrating employee
		Employee myEmp = new Employee();
		myEmp.setDepartment(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION);
		myEmp.setUsername("tom123");
		myEmp.setPosition(EmployeePosition.Worker.toString());
		myEmp.setEmployeeName("Tommy");
		employeeService.saveOrUpdateEmployee(myEmp);

		myEmp = new Employee();
		myEmp.setUsername("john456");
		myEmp.setEmployeeName("John");
		myEmp.setPosition(EmployeePosition.Worker.toString());
		myEmp.setDepartment(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION);
		employeeService.saveOrUpdateEmployee(myEmp);

		myEmp = new Employee();
		myEmp.setUsername("jim789");
		myEmp.setEmployeeName("Jim");
		myEmp.setPosition(EmployeePosition.Worker.toString());
		myEmp.setDepartment(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION);
		employeeService.saveOrUpdateEmployee(myEmp);

		// LamPQT add for Employee has QC position
		myEmp = new Employee();
		myEmp.setUsername("lampqt");
		myEmp.setEmployeeName("Lam");
		myEmp.setPosition(EmployeePosition.Qc.toString());
		myEmp.setDepartment(Constants.DEPARTMENT_TYPE.SCALE);
		employeeService.saveOrUpdateEmployee(myEmp);

		myEmp = new Employee();
		myEmp.setUsername("quanpm");
		myEmp.setEmployeeName("Quan");
		myEmp.setPosition(EmployeePosition.Qc.toString());
		myEmp.setDepartment(Constants.DEPARTMENT_TYPE.SCALE);
		employeeService.saveOrUpdateEmployee(myEmp);

		myEmp = new Employee();
		myEmp.setUsername("hoanghh");
		myEmp.setEmployeeName("Hoang");
		myEmp.setPosition(EmployeePosition.Qc.toString());
		myEmp.setDepartment(Constants.DEPARTMENT_TYPE.SCALE);
		employeeService.saveOrUpdateEmployee(myEmp);

		LOGGER.info("Generated employee data");
	}

	private void createSupplier() {

		LOGGER.info("Generate supplier data");

		Supplier supplier = new Supplier(1, "SL001", "Tony Stack", 23456789, "L.A (Long An");
		Supplier supplier2 = new Supplier(2, "SL002", "Tony Stack 2", 23456789, "L.A (Long An");
		Supplier supplier3 = new Supplier(3, "SL003", "Tony Stack 3", 23456789, "L.A (Long An");

		supplierDao.saveAll(Arrays.asList(supplier, supplier2, supplier3));

		LOGGER.info("Generated supplier data");

	}

	private void createIngredientBatch() {

		LOGGER.info("Generate ingredient batch data");

		if (supplierDao.count() == 0) {
			createSupplier();
		}

		List<Supplier> listSuppliers = supplierDao.findAll();

		IngredientBatch ingredientBatch1 = new IngredientBatch(1, "Lô 1", "LOT001", "2021-01-25", "1", "admin", "admin",
				listSuppliers.get(random.nextInt(listSuppliers.size() - 1)));
		IngredientBatch ingredientBatch2 = new IngredientBatch(2, "Lô 2", "LOT002", "2021-01-24", "2", "admin", "admin",
				listSuppliers.get(random.nextInt(listSuppliers.size() - 1)));
		IngredientBatch ingredientBatch3 = new IngredientBatch(3, "Lô 3", "LOT003", "2021-01-23", "1", "admin", "admin",
				listSuppliers.get(random.nextInt(listSuppliers.size() - 1)));
		IngredientBatch ingredientBatch4 = new IngredientBatch(4, "Lô 3", "LOT004", "2021-01-22", "3", "admin", "admin",
				listSuppliers.get(random.nextInt(listSuppliers.size() - 1)));

		ingredientBatchDao
				.saveAll(Arrays.asList(ingredientBatch1, ingredientBatch2, ingredientBatch3, ingredientBatch4));

		LOGGER.info("Generated ingredient batch data");
	}

	private void createTypeProduct() {

		LOGGER.info("Generate type products data");

		TypeProduct type1 = new TypeProduct("BSD", "Bột sữa dừa", "");
		TypeProduct type2 = new TypeProduct("BND", "Bột nước dừa", "");
		TypeProduct type3 = new TypeProduct("BĐ", "Bột điều", "");
		type1.setId(1);
		type2.setId(2);
		type3.setId(3);

		typeProductDAO.saveAll(Arrays.asList(type1, type2, type3));

		LOGGER.info("Generated type products data");

	}

	private void createDepartment() {

		LOGGER.info("Generate departments data");

		// initialize scale-calibrating departments
		if (departmentDAO.findByDepartmentCodeStartingWith(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION).isEmpty()) {
			departmentDAO.saveAll(Arrays.asList(
					new Department(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION + "001", "Sản xuất bột sữa dừa",
							"someone1"),
					new Department(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION + "002", "Sản xuất thạch dừa",
							"someone2"),
					new Department(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION + "003", "Sản xuất rau cau dừa",
							"someone3")));
		}

		LOGGER.info("Generated departments data");

	}

	private void createMachine() {

		LOGGER.info("Generate machines data");

		if (machineDao.findAllByMachineType(Constants.MACHINE_TYPE.SCALE).size() < 4) {
			machineDao.saveAll(Arrays.asList(
					new Machine(0, "Can dien tu 5kg", "es5", Constants.MACHINE_TYPE.SCALE, "Tau khua", "Available",
							"none", "none", 10),
					new Machine(0, "Can dien tu 100kg", "es100", Constants.MACHINE_TYPE.SCALE, "Tau khua", "Available",
							"none", "none", 15),
					new Machine(0, "Can dien tu 500kg", "es500", Constants.MACHINE_TYPE.SCALE, "Tau khua", "Available",
							"none", "none", 20),
					new Machine(0, "Can dien tu 1000kg", "es1000", Constants.MACHINE_TYPE.SCALE, "Tau khua",
							"Available", "none", "none", 25)));
		}

		if (machineDao.findAllByMachineType(Constants.MACHINE_TYPE.PRESSING).isEmpty()) {
			machineDao.saveAll(Arrays.asList(
					new Machine(0, "EP COT 1", "PS001", Constants.MACHINE_TYPE.PRESSING, "ALIBABA", "N/A", "2020-10-03",
							"2020-10-03", 3),
					new Machine(0, "EP COT 2", "PS002", Constants.MACHINE_TYPE.PRESSING, "ALIBABA", "N/A", "2020-10-03",
							"2020-10-03", 3),
					new Machine(0, "EP COT 3", "PS003", Constants.MACHINE_TYPE.PRESSING, "ALIBABA", "N/A", "2020-10-03",
							"2020-10-03", 3)));
		}

		if (machineDao.findAllByMachineType(Constants.MACHINE_TYPE.WEIGHT).isEmpty()) {
			machineDao.saveAll(Arrays.asList(
					new Machine(0, "QUA TA 5KG", "W0005", Constants.MACHINE_TYPE.WEIGHT, "ALIBABA", "N/A", "2020-10-03",
							"2020-10-03", 3),
					new Machine(0, "QUA TA 10KG", "W0010", Constants.MACHINE_TYPE.WEIGHT, "ALIBABA", "N/A",
							"2020-10-03", "2020-10-03", 3),
					new Machine(0, "QUA TA 100KG", "W0100", Constants.MACHINE_TYPE.WEIGHT, "ALIBABA", "N/A",
							"2020-10-03", "2020-10-03", 3),
					new Machine(0, "QUA TA 1000KG", "W1000", Constants.MACHINE_TYPE.WEIGHT, "ALIBABA", "N/A",
							"2020-10-03", "2020-10-03", 3)));
		}

		LOGGER.info("Generated machines data");

	}

	private void createProduct() {

		LOGGER.info("Generate products data");

		if (machineDao.count() == 0) {
			createMachine();
		}

		if (typeProductDao.count() == 0) {
			createTypeProduct();
		}
		List<Product> listProducts = new ArrayList<Product>();
		List<Machine> listMachines = machineDao.findAll();
		List<TypeProduct> listTypeProducts = typeProductDao.findAll();

		Product product1 = new Product(1, "P001", "Bột sữa dừa hạng nhất", 1, 2, 10.0, LocalDate.now(), null, null,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listMachines.get(random.nextInt(listMachines.size() - 1)), Status.Pending.toString(),

				2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 20, null, null, "San pham loai tot");

		Product product2 = new Product(2, "P002", "Bột nước dừa hạng nhất", 1, 2, 10.0, LocalDate.now(), null, null,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listMachines.get(random.nextInt(listMachines.size() - 1)), Status.Pending.toString(),

				2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 20, null, null, "San pham loai tot");

		Product product3 = new Product(3, "P003", "Bột nước dừa hạng bét", 1, 2, 10.0, LocalDate.now(), null, null,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listMachines.get(random.nextInt(listMachines.size() - 1)), Status.Approved.toString(),

				2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 20, null, null, "San pham loai tot");

		Product product4 = new Product(4, "P004", "Bột điều hạng nhất", 1, 2, 10.0, LocalDate.now(), null, null,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listMachines.get(random.nextInt(listMachines.size() - 1)), Status.Rejected.toString(),

				2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 20, null, null, "San pham loai tot");
		Collections.addAll(listProducts, product1, product2, product3, product4);

		for (int i = 5; i <= 30; i++) {
			String code = "";
			if (i > 9) {
				code = "P0" + i;
			} else {
				code = "P00" + i;
			}
			LocalDate date = LocalDate.parse("2020-01-08");
			listProducts.add(new Product(i, code, "Bột sữa dừa hạng " + i, 1, 2, 10.0, date, null, null,
					listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
					listMachines.get(random.nextInt(listMachines.size() - 1)), Status.Pending.toString(),

					2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 20, null, null, "San pham loai tot"));
		}

		productDAO.saveAll(listProducts);

		LOGGER.info("Generated products data");

	}

	private void createFinishedProductReport() {

		LOGGER.info("Generate finished product reports data");

		if (productDAO.count() == 0) {
			createProduct();
		}
		List<FinishedProductReport> listFinishedProductReports = new ArrayList<FinishedProductReport>();

		List<Product> listProducts = productDAO.findAll();

		for (int i = 1; i <= 30; i++) {
			listFinishedProductReports.add(new FinishedProductReport(i, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, "lotNo" + i,
					"lotName" + i, listProducts.get(i - 1)));
		}
		finishedProductReportDAO.saveAll(listFinishedProductReports);

		LOGGER.info("Generated finished product reports data");

	}

	private void createProductLot() {

		LOGGER.info("Generate product lots data");

		if (typeProductDao.count() == 0) {
			createTypeProduct();
		}

		List<TypeProduct> listTypeProducts = typeProductDao.findAll();

		List<Employee> listEmployees = employeeDAO.findAll();

		ProductLot lot1 = new ProductLot("lot001", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));
		ProductLot lot2 = new ProductLot("lot002", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));
		ProductLot lot3 = new ProductLot("lot003", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));
		ProductLot lot4 = new ProductLot("lot004", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));

		ProductLot lot5 = new ProductLot("lot005", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));
		ProductLot lot6 = new ProductLot("lot006", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));
		ProductLot lot7 = new ProductLot("lot007", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));
		ProductLot lot8 = new ProductLot("lot008", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));

		ProductLot lot9 = new ProductLot("lot009", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));
		ProductLot lot10 = new ProductLot("lot0010", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));
		ProductLot lot11 = new ProductLot("lot0011", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));
		ProductLot lot12 = new ProductLot("lot0012", LocalDateTime.now(), 1000,
				listTypeProducts.get(random.nextInt(listTypeProducts.size() - 1)),
				listEmployees.get(random.nextInt(listEmployees.size() - 1)));

		productLotDao.saveAll(Arrays.asList(lot1, lot2, lot3, lot4, lot5, lot6, lot7, lot8, lot9, lot10, lot11, lot12));

		LOGGER.info("Generated product lots data");

	}

	/**
	 * @author LamPQT
	 */
	private void createDefaultSettingCheckList() {

		List<Employee> es = employeeDAO.findByPosition("Qc");
		ProductLot lot = lotDAO.findAll().get(0);

		DefaultSetting check_qc = new DefaultSetting("qa_check", "qc", es.get(0).getUsername());
		DefaultSetting check_time = new DefaultSetting("qa_check", "time", LocalTime.now().toString());
		DefaultSetting check_date = new DefaultSetting("qa_check", "date", LocalDate.now().toString());
		DefaultSetting check_lotCode = new DefaultSetting("qa_check", "lotCode", lot.getLotCode());
		DefaultSetting check_moisture = new DefaultSetting("qa_check", "moisture", "0.06");
		DefaultSetting check_ph = new DefaultSetting("qa_check", "ph", "1");
		DefaultSetting check_brix = new DefaultSetting("qa_check", "brix", "0.01");
		DefaultSetting check_color = new DefaultSetting("qa_check", "color", "0");
		DefaultSetting check_taste = new DefaultSetting("qa_check", "taste", "0");
		DefaultSetting check_impurity = new DefaultSetting("qa_check", "impurity", "0.01");
		DefaultSetting check_quantity_satisfied = new DefaultSetting("qa_check", "quantity_satisfied", "1000");
		DefaultSetting check_quantity_unsatisfied = new DefaultSetting("qa_check", "quantity_unsatisfied", "0");
		DefaultSetting check_quantity_mix = new DefaultSetting("qa_check", "quantity_mix", "1000");

		DefaultSetting check_remark = new DefaultSetting("qa_check", "remark", "");

		settingDAO.saveAll(Arrays.asList(check_qc, check_time, check_date, check_lotCode, check_moisture, check_ph,
				check_brix, check_color, check_taste, check_impurity, check_quantity_satisfied,
				check_quantity_unsatisfied, check_quantity_mix, check_remark));

		LOGGER.info("Generated setting check list data");

	}

	/**
	 * @author ThienNTN1
	 */
	private void createDefaultSettingFinishedProductReport() {

		LOGGER.info("Generate setting FinishedProductReport data");

		DefaultSetting fnPdProductCode = new DefaultSetting("fn_pd_report", "fn_pd_product_code", "001");
		DefaultSetting fnPdProductName = new DefaultSetting("fn_pd_report", "fn_pd_product_name",
				"Bột sữa dừa hạng nhất");
		DefaultSetting fnPdTypeProduct = new DefaultSetting("fn_pd_report", "fn_pd_type_product", "1");

		DefaultSetting fnPdInputDate = new DefaultSetting("fn_pd_report", "fn_pd_input_date", "2021-01-01");
		DefaultSetting fnPdMachineNo = new DefaultSetting("fn_pd_report", "fn_pd_machine_no", "1");
		DefaultSetting fnPdTotalWeightInput = new DefaultSetting("fn_pd_report", "fn_pd_total_weight_input", "12");

		DefaultSetting fnPdResidueInput = new DefaultSetting("fn_pd_report", "fn_pd_residue_input", "12");
		DefaultSetting fnPdCoconutMilkInput = new DefaultSetting("fn_pd_report", "fn_pd_coconut_milk_input", "12");
		DefaultSetting fnPdFinishedProduct = new DefaultSetting("fn_pd_report", "fn_pd_finished_product", "12");

		DefaultSetting fnPdUnsatisfiedProduct = new DefaultSetting("fn_pd_report", "fn_pd_unsatisfied_product", "0");
		DefaultSetting fnPdWaterExtract = new DefaultSetting("fn_pd_report", "fn_pd_water_extract", "1");
		DefaultSetting fnPdBlackPoint = new DefaultSetting("fn_pd_report", "fn_pd_black_point", "2");

		DefaultSetting fnPdLowPH = new DefaultSetting("fn_pd_report", "fn_pd_low_ph", "12");
		DefaultSetting fnPdClotted = new DefaultSetting("fn_pd_report", "fn_pd_clotted", "12");
		DefaultSetting fnPdXylon = new DefaultSetting("fn_pd_report", "fn_pd_xylon", "12");

		DefaultSetting fnPdScrap = new DefaultSetting("fn_pd_report", "fn_pd_scrap", "12");
		DefaultSetting fnPdCrusted = new DefaultSetting("fn_pd_report", "fn_pd_crusted", "12");
		DefaultSetting fnPdResidue = new DefaultSetting("fn_pd_report", "fn_pd_residue", "12");

		settingDAO.saveAll(Arrays.asList(fnPdProductCode, fnPdProductName, fnPdTypeProduct, fnPdInputDate,
				fnPdMachineNo, fnPdTotalWeightInput, fnPdResidueInput, fnPdCoconutMilkInput, fnPdFinishedProduct,
				fnPdUnsatisfiedProduct, fnPdWaterExtract, fnPdBlackPoint, fnPdLowPH, fnPdClotted, fnPdXylon, fnPdScrap,
				fnPdCrusted, fnPdResidue));

		LOGGER.info("Generated setting FinishedProductReport data");

	}

	/**
	 * @author ThuanLV6
	 * 
	 * @author LongVT7: Modified data
	 */
	private void scaleCalibrationDataInit() {

		LOGGER.info("Generate scale calibration data");

		if (scaleSymbolService.findAll().size() < 1) {
			scaleSymbolService.save(new ScaleSymbol("C-01"));
			scaleSymbolService.save(new ScaleSymbol("C-02"));
			scaleSymbolService.save(new ScaleSymbol("C-03"));
		}

		LOGGER.info("Generated scale calibration data");
		// ThuanLV 6 finishes initializing data
	}

//	NguyenND6 Generate Additive Data

	private void createAdditive() {

		LOGGER.info("Generate additive data");

		if (additiveDAO.count() == 0) {

			Additive add1 = new Additive("su01", "sugar01");
			Additive add2 = new Additive("su02", "sugar02");
			Additive add3 = new Additive("su03", "sugar03");
			additiveDAO.saveAll(Arrays.asList(add1, add2, add3));

		}

		LOGGER.info("Generated additive data");

	}

	private void createMixingMaterial() {

		LOGGER.info("Generate mixingMaterial data");

		if (mixingMaterialDAO.count() == 0) {

			IngredientBatch ingredientBatch = ingredientBatchDao.getOne(1);
			Machine machine = machineDao.getOne(1);
			Additive additive = additiveDAO.getOne(1);
			ProductLot productLot = productLotDao.getOne(1);
			Employee employee = employeeDAO.getOne(1);
			TypeProduct typeProduct = typeProductDao.getOne(1);
			MixingMaterial mix1 = new MixingMaterial(1, 1f, 2f, LocalDate.now(), 2f, 1, typeProduct, machine,
					ingredientBatch, employee, productLot, additive);
			MixingMaterial mix2 = new MixingMaterial(1, 2f, 2f, LocalDate.now(), 2f, 1, typeProduct, machine,
					ingredientBatch, employee, productLot, additive);
			MixingMaterial mix3 = new MixingMaterial(1, 3f, 3f, LocalDate.now(), 2f, 1, typeProduct, machine,
					ingredientBatch, employee, productLot, additive);
			mixingMaterialDAO.saveAll(Arrays.asList(mix1, mix2, mix3));

		}

		LOGGER.info("Generated mixingMaterial data");

	}

	/**
	 * @author DinhDN Function create data for test Function Meatal Detector.
	 */
	private void createDefaultDataFunctionMeatalDetector() {
		for (int i = 0; i < 5; i++) {
			Department department = new Department("departmentCode" + i, "departmentName" + i, "manager" + i);

			LOGGER.debug("Create new department " + (i + 1) + " :" + departmentService.saveOrUpdate(department));
		}

		for (int i = 0; i < 5; i++) {
			TypeProduct typeProduct = new TypeProduct("typeProductCode" + i, "typeProductName" + i, "note" + i);

			LOGGER.debug("Create new typeProduct " + (i + 1) + " :" + typeProductService.saveOrUpdate(typeProduct));
		}

		// Create new list productLot in database.
		TypeProduct typeProduct = typeProductService.getTypeProductByTypeProductCode("typeProductCode0");
		Employee employee = new Employee("employeeName", "dinhnhatdong", "position", "department", "managerment",
				"note");
		employeeService.saveOrUpdateEmployee(employee);
		employee = employeeService.getEmployeeById(1);

		for (int i = 0; i < 5; i++) {
			ProductLot productLot = new ProductLot("lotCode" + i, LocalDateTime.now(), 10, typeProduct, employee);

			LOGGER.debug("Create new productLot " + (i + 1) + " :" + productLotSevice.saveOrUpdate(productLot));
		}
	}

	// ThuanLV6: function to initialize dummy scale-calibrating data
	private void createDummyScaleCalibrationRecords(int amount) {
		LocalDate start = LocalDate.of(1989, Month.OCTOBER, 14);
		LocalDate end = LocalDate.now();
		Random rand = new Random();
		// list of standard devices
		List<Machine> standardDevices = machineService.findAllByMachineType(Constants.MACHINE_TYPE.WEIGHT);

		// list of calibrated devices
		List<Machine> calibratedDevices = machineService.findAllByMachineType(Constants.MACHINE_TYPE.SCALE);

		// list of calibrators
		List<Employee> calibrators = employeeService.getAllByDepartment(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION);

		// list of scale symbols
		List<ScaleSymbol> scaleSymbols = scaleSymbolService.findAll();

		// list of scaling departments
		List<Department> scaleDepartments = departmentService.findAllByDepartmentCode(Constants.DEPARTMENT_TYPE.SCALE);

		for (int i = 1; i <= amount; i++) {
			scaleCalibrationService.saveScaleCalibration(new ScaleCalibration(between(start, end).atStartOfDay(), // created
																													// date
					"Thủ công", // calibrating method
					1, // first result
					1, // second result
					1, // third result
					1, // average result
					standardDevices.get(rand.nextInt(standardDevices.size())),
					calibratedDevices.get(rand.nextInt(calibratedDevices.size())),
					scaleDepartments.get(rand.nextInt(scaleDepartments.size())),
					calibrators.get(rand.nextInt(calibrators.size())),
					scaleSymbols.get(rand.nextInt(scaleSymbols.size())), (i % 3 == 0 ? Status.Approved.toString()
							: (i % 2 == 0 ? Status.Pending.toString() : Status.Rejected.toString()))));

		}
	}

	public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
		long startEpochDay = startInclusive.toEpochDay();
		long endEpochDay = endExclusive.toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

		return LocalDate.ofEpochDay(randomDay);
	}

	/**
	 * @author LongVT7
	 */
	public void createDefaultSettingValueForPressingMonitor() {

		LOGGER.info("Generate setting value for prssing monitor");

		TypeProduct typeProduct = typeProductDAO.findAll().get(0);
		Machine machine = machineDao.findAllByMachineType(Constants.MACHINE_TYPE.PRESSING).get(0);
		IngredientBatch ingredientBatch = ingredientBatchDao.findAll().get(0);

		DefaultSetting df_typeProduct = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR,
				"TYPE_PRODUCT", typeProduct.getId().toString());
		DefaultSetting df_machine = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR, "MACHINE",
				machine.getId().toString());
		DefaultSetting df_ingredientBatch = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR,
				"INGREDIENT_BATCH", ingredientBatch.getId().toString());

		DefaultSetting df_residue = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR, "RESIDUE",
				"50");
		DefaultSetting df_weight = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR, "WEIGHT",
				"300");
		DefaultSetting df_netStatus = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR,
				"NET_STATUS", "true");

		DefaultSetting df_pressTime = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR,
				"PRESS_TIME", "150");
		DefaultSetting df_pressureStatus = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR,
				"PRESSURE_STATUS", "true");
		DefaultSetting df_weightMilk = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR,
				"WEIGHT_MILK", "250");

		settingDAO.saveAll(Arrays.asList(df_typeProduct, df_machine, df_ingredientBatch, df_residue, df_weight,
				df_netStatus, df_pressTime, df_pressureStatus, df_weightMilk));

		LOGGER.info("Generated setting value for prssing monitor");

	}

	/**
	 * @author LongVT7
	 */
	private void createDefaultSettingSieveDrying() {

		LOGGER.info("Generate setting value for sieve drying");

		TypeProduct typeProduct = typeProductDAO.findAll().get(0);
		Machine machine = machineDao.findAllByMachineType(Constants.MACHINE_TYPE.PRESSING).get(0);
		IngredientBatch ingredientBatch = ingredientBatchDao.findAll().get(0);
		Employee worker = employeeDAO.findByPosition(EmployeePosition.Worker.toString()).get(0);
		Employee qc = employeeDAO.findByPosition(EmployeePosition.Qc.toString()).get(0);
		Employee verifier = employeeDAO.findByPosition(EmployeePosition.Verifier.toString()).get(0);
		ProductLot lot = productLotDao.findAll().get(0);

		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		System.out.println(date.format(localDate));

		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime localTime = LocalTime.parse("00:00");
		System.out.println(time.format(localTime));

		DefaultSetting df_worker = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "WORKER",
				worker.getId().toString());
		DefaultSetting df_qc = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "QC", qc.getId().toString());
		DefaultSetting df_verifier = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "VERIFIER",
				verifier.getId().toString());
		DefaultSetting df_typeProduct = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "TYPE_PRODUCT",
				typeProduct.getId().toString());
		DefaultSetting df_inputDate = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "INPUT_DATE",
				localDate.toString());
		DefaultSetting df_productLot = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "PRODUCT_LOT",
				lot.getId().toString());
		DefaultSetting df_ingredLot = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "INGREDIENT_BATCH",
				lot.getId().toString());
		DefaultSetting df_machine = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "MACHINE",
				machine.getId().toString());
		DefaultSetting df_moisture = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "MOISTURE", "1.5");
		DefaultSetting df_pressure = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "PRESSURE", "150.0");
		DefaultSetting df_inputTemp = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "INPUT_TEMP", "180.0");
		DefaultSetting df_pH = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "pH", "7.0");
		DefaultSetting df_outputTemp = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "OUTPUT_TEMP", "93.5");
		DefaultSetting df_impurities = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "IMPURITIES", "true");
		DefaultSetting df_size = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "SIZE", "true");
		DefaultSetting df_color = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "COLOR", "true");
		DefaultSetting df_odor = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "ODOR", "true");
		DefaultSetting df_taste = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "TASTE", "true");
		DefaultSetting df_netStat = new DefaultSetting(Constants.PREFIX.SIEVE_DRYING, "NET_STAT", "true");

		settingDAO.saveAll(Arrays.asList(df_worker, df_qc, df_verifier, df_typeProduct, df_inputDate, df_productLot,
				df_ingredLot, df_machine, df_moisture, df_pressure, df_inputTemp, df_pH, df_outputTemp, df_impurities,
				df_size, df_color, df_odor, df_taste, df_netStat));

		LOGGER.info("Generated setting value for prssing monitor");

	}

	/**
	 * @author NguyenND6
	 */
	private void createDefaultSettingMixingMaterial() {

		LOGGER.info("Generate setting value for Mixing Material");

		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();

		IngredientBatch ingredientBatch = ingredientBatchDao.getOne(1);
		Machine machine = machineDao.getOne(1);
		Additive additive = additiveDAO.getOne(1);
		ProductLot productLot = productLotDao.getOne(1);
		Employee employee = employeeDAO.getOne(1);
		TypeProduct typeProduct = typeProductDao.getOne(1);

		DefaultSetting df_typeProduct = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL,
				"TYPE_PRODUCT", typeProduct.getId().toString());
		DefaultSetting df_machine = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL, "MACHINE",
				machine.getId().toString());
		DefaultSetting df_ingredientBatch = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL,
				"INGREDIENT_BATCH", ingredientBatch.getId().toString());
		DefaultSetting df_productLot = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL,
				"PRODUCT_LOT", productLot.getId().toString());
		DefaultSetting df_employee = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL, "EMPLOYEE",
				employee.getId().toString());
		DefaultSetting df_additive = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL, "ADDITIVE",
				additive.getId().toString());

		DefaultSetting df_date = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL, "DATE",
				localDate.toString());
		DefaultSetting df_batch = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL, "BATCH", "1");
		DefaultSetting df_amountMaterial = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL,
				"AMOUNT_MATERIAL", "300");

		DefaultSetting df_time = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL, "TIME", "2");
		DefaultSetting df_amountAdditive = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL,
				"AMOUNT_ADDITIVE", "7.5");
		DefaultSetting df_no = new DefaultSetting(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL, "NO", "2");

		settingDAO.saveAll(Arrays.asList(df_typeProduct, df_machine, df_ingredientBatch, df_batch, df_productLot,
				df_employee, df_additive, df_date, df_amountMaterial, df_time, df_amountAdditive, df_no));

		LOGGER.info("Generated setting value for Mixing Material");

	}

}

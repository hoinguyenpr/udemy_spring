package com.dpm.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.constant.Constants;
import com.dpm.dao.SieveDryingCustomDAO;
import com.dpm.dao.SieveDryingDAO;
import com.dpm.dto.SieveDryingDTO;
import com.dpm.dto.SieveDryingReportDTO;
import com.dpm.entity.Employee;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.ProductLot;
import com.dpm.entity.SieveDrying;
import com.dpm.entity.TypeProduct;
import com.dpm.service.EmployeeService;
import com.dpm.service.IngredientBatchService;
import com.dpm.service.MachineService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.SieveDryingService;
import com.dpm.service.TypeProductService;
import com.dpm.utility.Status;

@Service
public class SieveDryingServiceImpl implements SieveDryingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SieveDryingServiceImpl.class);

	@Autowired
	SieveDryingDAO sieveDryingDAO;
	
	@Autowired
	SieveDryingCustomDAO customDao;

	// Add new record to database
	
	@Autowired
	DefaultSettingServiceImpl defSettingService;
	
	@Autowired
	IngredientBatchService ingredientBatchService;

	@Autowired
	SieveDryingService sieveDryingService;

	@Autowired
	TypeProductService typeProductService;

	@Autowired
	IngredientBatchService ingrService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ProductLotSevice lotSevice;

	@Autowired
	MachineService machineService;

	@Override
	public String updateSieveDrying(SieveDryingDTO dto) {
		try {
			SieveDrying sieveDrying = sieveDryingDAO.findById(dto.getId()).get();
			Optional<Employee> qc = employeeService.get(dto.getQCId());
			Optional<Employee> verifier = employeeService.get(dto.getVerifierId());
			Optional<Employee> worker = employeeService.get(dto.getWorkerId());
			Optional<ProductLot> lotCode = lotSevice.get(dto.getLotId());
			Optional<TypeProduct> typeProduct = typeProductService.get(dto.getTypeProductId());
			Optional<IngredientBatch> igrBatch = ingrService.get(dto.getIngredientBatchCode());
			Optional<Machine> machine = machineService.get(dto.getMachineId());
			sieveDrying = SieveDrying.mapping(dto);

			sieveDrying.setQc(qc.get());
			sieveDrying.setWorker(worker.get());
			sieveDrying.setVerifier(verifier.get());
			sieveDrying.setLotId(lotCode.get());
			sieveDrying.setTypeProduct(typeProduct.get());
			sieveDrying.setIngredientBatch(igrBatch.get());
			sieveDrying.setMachine(machine.get());
			sieveDrying.setStatus(Status.Pending.toString());
			sieveDryingDAO.save(sieveDrying);
			
			return "UPDATE_SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "UPDATE_FAILED";
		}
	}

	@Override
	public String deleteSieveDrying(int id) {
		try {
			SieveDrying drying = sieveDryingDAO.findById(id).get();
			drying.setStatus(Status.Removed.toString());
			sieveDryingDAO.save(drying);
			return "DELETE_SUCCESS";
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return "DELETE_FAIL";
		}

	}

	@Override
	public SieveDrying getSieveDryingById(Integer id) {
		try {
			return sieveDryingDAO.getOne(id);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Page<SieveDrying> ListAllPaging(Pageable pageable) {
		try {
			return sieveDryingDAO.findAll(pageable);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Page<SieveDrying> listAllForSearch(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SieveDrying> getAll() {
		List<SieveDrying> list = new ArrayList<SieveDrying>();
		try {
			list = sieveDryingDAO.findAll();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}

	@Override
	public String createStr(SieveDrying sieveDrying) {
		try {
			sieveDryingDAO.save(sieveDrying);
			return "CREATE_SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "CREATE_FAILED";
		}
	}

	@Override
	public List<SieveDrying> getAllAvailable() {
		List<SieveDrying> list = new ArrayList<SieveDrying>();
		try {
			list = sieveDryingDAO.getAllAvailable();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	@Override
	public Page<SieveDrying> getAllAvailable(Pageable pageable) {
		Page<SieveDrying> list = null;
		try {
			list = sieveDryingDAO.getAllAvailable(pageable);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	
	@Override
	public List<SieveDrying> getAllButNotDefault() {
		List<SieveDrying> list = new ArrayList<SieveDrying>();
		try {
			list = sieveDryingDAO.getAllButNotDefault();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	@Override
	public SieveDrying getDefault() {
		SieveDrying list = new SieveDrying();
		try {
			list = sieveDryingDAO.getDefault();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}

	@Override
	public String setDefault(SieveDryingDTO dto) {
		
		LOGGER.info("Entered setDefault procedure with DTO = " + dto.toString());
		try {
			SieveDrying sieveDrying = new SieveDrying();
			LOGGER.info("Mapping...");
			sieveDrying = SieveDrying.mappingDefault(dto);
			LOGGER.info("Map Success!");
			sieveDrying.setStatus(Status.Default.toString());
			
			Optional<Employee> qc = employeeService.get(dto.getQCId());
			Optional<Employee> verifier = employeeService.get(dto.getVerifierId());
			Optional<Employee> worker = employeeService.get(dto.getWorkerId());
			Optional<ProductLot> lotCode = lotSevice.get(dto.getLotId());
			Optional<TypeProduct> typeProduct = typeProductService.get(dto.getTypeProductId());
			Optional<IngredientBatch> igrBatch = ingrService.get(dto.getIngredientBatchCode());
			Optional<Machine> machine = machineService.get(dto.getMachineId());

			

			sieveDrying.setQc(qc.get());
			sieveDrying.setWorker(worker.get());
			sieveDrying.setVerifier(verifier.get());
			sieveDrying.setLotId(lotCode.get());
			sieveDrying.setTypeProduct(typeProduct.get());
			sieveDrying.setIngredientBatch(igrBatch.get());
			sieveDrying.setMachine(machine.get());	
			
			System.out.println("SieveDryingServiceImpl/setDefault: ");
			System.out.println(sieveDrying.toString());
			
			defSettingService.setDefaultSieveDrying(sieveDrying);
			return "UPDATE_DEFAULT_VALUE_SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "UPDATE_DEFAULT_VALUE_FAILED";
		}
	}

	@Override
	public Page<SieveDrying> getReport(SieveDryingReportDTO rdto, Pageable pageable) {
		Page<SieveDrying> list = null;
		try {
		list = customDao.getReport(rdto, pageable);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}

	@Override
	public List<SieveDrying> getPending() {
		List<SieveDrying> list = new ArrayList<SieveDrying>();
		try {
		list = sieveDryingDAO.getPending();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}

	@Override
	public String approveSieveDrying(int id) {
		try {
			SieveDrying drying = sieveDryingDAO.findById(id).get();
			drying.setStatus(Status.Approved.toString());
			sieveDryingDAO.save(drying);
			return Constants.SUCCESS;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return Constants.FAILED;
		}
	}
	
	@Override
	public String approveSieveDrying(List<Integer> idList) {
		try {
			for (Integer id : idList) {
				SieveDrying drying = sieveDryingDAO.findById(id).get();
				drying.setStatus(Status.Approved.toString());
				sieveDryingDAO.save(drying);
			}
			return Constants.SUCCESS;
		} catch (EntityNotFoundException e) {
			LOGGER.info(e.toString());
			return Constants.FAILED;
		}
	}

	@Override
	public String rejectSieveDrying(int id) {
		try {
			SieveDrying drying = sieveDryingDAO.findById(id).get();
			drying.setStatus(Status.Rejected.toString());
			sieveDryingDAO.save(drying);
			return Constants.SUCCESS;
		} catch (EntityNotFoundException e) {
			LOGGER.info(e.toString());
			return Constants.FAILED;
		}
	}
	
	@Override
	public String rejectSieveDrying(List<Integer> idList) {
		try {
			for (Integer id : idList) {
				SieveDrying drying = sieveDryingDAO.findById(id).get();
				drying.setStatus(Status.Rejected.toString());
				sieveDryingDAO.save(drying);
			}
			return Constants.SUCCESS;
		} catch (EntityNotFoundException e) {
			LOGGER.info(e.toString());
			return Constants.FAILED;
		}
	}

	@Override
	public Page<SieveDrying> getAllButNotDefault(Pageable pageable) {
		Page<SieveDrying> list = null;
		try {
			list = sieveDryingDAO.getAllButNotDefault(pageable);
			return list;
		} catch (Exception e) {
			LOGGER.info(e.toString());
			return list;
		}
	}
	
	private static XSSFWorkbook workbook;

	@Override
	public void exportReport(HttpServletResponse response, SieveDryingReportDTO rdto) {
		workbook = new XSSFWorkbook();
		List<SieveDrying> records = customDao.getReport(rdto);
        final String excelFilePath = "SieveDrying_report_template.xlsx";
        try {
        	
			writeExcel(records, excelFilePath);
			ServletOutputStream stream = response.getOutputStream();
			workbook.write(stream);
			workbook.close();
			stream.close();
		} catch (IOException e) {
			LOGGER.info(e.toString());
		}
	}
	
	public static void writeExcel(List<SieveDrying> records, String excelFilePath) throws IOException {
		
        // Create sheet
        Sheet sheet = workbook.createSheet("report"); // Create sheet with sheet name report
        writeHeader(sheet);
        
        int rowIndex = 4;
 
        // Write data
        for (SieveDrying sd : records) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeBook(sd, row);
            rowIndex++;
        }
         
        // Write footer
        writeFooter(sheet, rowIndex);
    }
	
	private static void writeHeader(Sheet sheet) {
		CellStyle styleTitle = workbook.createCellStyle();
		CellStyle styleHeader1 = workbook.createCellStyle();
		CellStyle styleHeader2 = workbook.createCellStyle();
		XSSFFont fontTitle = workbook.createFont();
		XSSFFont fontHeader = workbook.createFont();
		XSSFFont fontHeader2 = workbook.createFont();
		
		fontTitle.setBold(true);
		fontTitle.setFontHeight(16);
		fontHeader.setBold(false);
		fontHeader.setFontHeight(12);
		fontHeader2.setBold(true);
		fontHeader2.setFontHeight(10);
		
		styleTitle.setFont(fontTitle);
		styleTitle.setWrapText(true);
		styleTitle.setAlignment(HorizontalAlignment.CENTER);
		styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
		styleHeader1.setAlignment(HorizontalAlignment.LEFT);
		styleHeader2.setFont(fontHeader2);
		styleHeader2.setAlignment(HorizontalAlignment.CENTER);
		styleHeader2.setVerticalAlignment(VerticalAlignment.CENTER);
		styleHeader2.setWrapText(true);
		styleHeader2.setBorderBottom(BorderStyle.THIN);
		styleHeader2.setBorderLeft(BorderStyle.THIN);
		styleHeader2.setBorderRight(BorderStyle.THIN);
		styleHeader2.setBorderTop(BorderStyle.THIN);
		
		
		sheet = workbook.getSheet("report");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 15));
		Row rowTitle = sheet.createRow(0);
		rowTitle.setHeightInPoints(44.5f);
		
		createCell(rowTitle, 0, "BIỂU MẪU GIÁM SÁT SẤY SÀNG\r\nSPRAYING DRYING MONITORING FORM", styleTitle);
		
		Row rowHeader1 = sheet.createRow(1);
		Row rowHeader2 = sheet.createRow(2);
		Row rowHeader3 = sheet.createRow(3);
		
		createCell(rowHeader1,0,"Loại sản phẩm:", styleHeader1);
		createCell(rowHeader1,5,"Ngày thực hiện:", styleHeader1);
		createCell(rowHeader1,9,"Máy SP:", styleHeader1);
		createCell(rowHeader1,12,"Tần suất: 30 phút/lần", styleHeader1);
		
		createCell(rowHeader2,0,"Type of product:", styleHeader1);
		createCell(rowHeader2,5,"Date:", styleHeader1);
		createCell(rowHeader2,9,"Machine:", styleHeader1);
		createCell(rowHeader2,12,"Frequency: every 30 minutes", styleHeader1);
		
		createCell(rowHeader3,0,"Giờ kiểm tra/\r\nTime", styleHeader2);
		createCell(rowHeader3,1,"Mã lô NL/\r\nMaterial batch code", styleHeader2);
		createCell(rowHeader3,2,"Độ ẩm/\r\nMoisture (≤ 2 %)", styleHeader2);
		createCell(rowHeader3,3,"Nhiệt độ vào / Input temperature 160 - 200oC", styleHeader2);
		createCell(rowHeader3,4,"Áp suất / Pressure\r\n(100 - 200 psi)", styleHeader2);
		createCell(rowHeader3,5,"Nhiệt độ ra / Output temperature 90 - 95 0C", styleHeader2);
		createCell(rowHeader3,6,"pH", styleHeader2);
		createCell(rowHeader3,7,"Tạp chất / Impurities\n(Đ/K)", styleHeader2);
		createCell(rowHeader3,8,"Kích thước hạt / Size (Đ/K)", styleHeader2);
		createCell(rowHeader3,9,"Màu / Color (Đ/K)", styleHeader2);
		createCell(rowHeader3,10,"Mùi / Odor (Đ/K)", styleHeader2);
		createCell(rowHeader3,11,"Vị / Taste (Đ/K)", styleHeader2);
		createCell(rowHeader3,12,"Tình trạng lưới / Net status (Đ/K)", styleHeader2);
		createCell(rowHeader3,13,"Mã lô TP\r\nFinished product lot code ", styleHeader2);
		createCell(rowHeader3,14,"HĐKP / Corrective action", styleHeader2);
	}
	
	// Write data
    private static void writeBook(SieveDrying sd, Row row) {
    	CellStyle styleContent1 = workbook.createCellStyle();
		XSSFFont fontContent1 = workbook.createFont();
		fontContent1.setFontHeight(10);
		styleContent1.setFont(fontContent1);
		styleContent1.setBorderBottom(BorderStyle.THIN);
		styleContent1.setBorderTop(BorderStyle.THIN);
		styleContent1.setBorderLeft(BorderStyle.THIN);
		styleContent1.setBorderRight(BorderStyle.THIN);
         
        //Fill input time
        createCell(row,0,sd.getInputTime().toString(),styleContent1);
 
        
        //Fill ingredient batch code
        createCell(row,1,sd.getIngredientBatch().getIngredientBatchCode(),styleContent1);
        
        //Fill moisture
        createCell(row,2,sd.getMoisture(),styleContent1);
        
        //Fill input temp
        createCell(row,3,sd.getInputTemp(),styleContent1);
        
        //Fill pressure
        createCell(row,4,sd.getPressure(),styleContent1);
        
        //Fill output temp
        createCell(row,5,sd.getOutputTemp(),styleContent1);
        
        //Fill pH
        createCell(row,6,sd.getpH(),styleContent1);
        
        //Fill impurities
        String isImpurities = "K";
        if (sd.isImpurities()) {
        	isImpurities = "Đ";
        }
        createCell(row,7,isImpurities,styleContent1);
        
        //Fill size
        String size = "K";
        if (sd.isSize()) {
        	size = "Đ";
        }
        createCell(row,8,size,styleContent1);
                
        //Fill color
        String color = "K";
        if (sd.isColor()) {
        	color = "Đ";
        }
        createCell(row,9,color,styleContent1);
        
        //Fill odor
        String odor = "K";
        if (sd.isOdor()) {
        	odor = "Đ";
        }
        createCell(row,10,odor,styleContent1);
        
        //Fill taste
        String taste = "K";
        if (sd.isTaste()) {
        	taste = "Đ";
        }
        createCell(row,11,taste,styleContent1);
        
        //Fill net status
        String netStat = "K";
        if (sd.isNetStat()) {
        	netStat = "Đ";
        }
        createCell(row,12,netStat,styleContent1);
        
        //Fill product lot code
        createCell(row,13,sd.getLotCode().getLotCode().toString(),styleContent1);
        
        //Fill corrective action
        createCell(row,14,sd.getCorrectiveAction(),styleContent1);
 
    }
    
    private static void writeFooter(Sheet sheet, int rowIndex) {
        // Create row
    	CellStyle styleFooter1 = workbook.createCellStyle();
		XSSFFont fontFooter1 = workbook.createFont();
		fontFooter1.setBold(true);
		fontFooter1.setFontHeight(12);
		
		
		styleFooter1.setFont(fontFooter1);
        Row row = sheet.createRow(rowIndex);
        
        createCell(row,1,"QC", styleFooter1);
        createCell(row,4,"Quản đốc / Foreman", styleFooter1);
        createCell(row,9,"Người thẩm tra / Verifier", styleFooter1);
    }
    
    private static void createCell(Row row, int columnCount, Object value, CellStyle style) {
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Float) {
			cell.setCellValue((Float) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	@Override
	public boolean isValid(SieveDryingDTO dto) {
		if (employeeService.getEmployeeById(dto.getWorkerId())==null) {
			LOGGER.info("Invalid Worker ID" + dto.getWorkerId());
			return false;
		}
		if (employeeService.getEmployeeById(dto.getQCId())==null) {
			LOGGER.info("Invalid QC ID" + dto.getQCId());
			return false;
		}
		if (employeeService.getEmployeeById(dto.getVerifierId())==null) {
			LOGGER.info("Invalid Verifier ID" + dto.getVerifierId());
			return false;
		}
		if (typeProductService.get(dto.getTypeProductId())==null) {
			LOGGER.info("Invalid Product type ID" + dto.getTypeProductId());
			return false;
		}
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			LocalDate.parse(dto.getInputDate(), dateFormatter);
        } catch (DateTimeParseException e) {
        	LOGGER.info(e.getMessage());
            return false;
        }
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		
		try {
			LocalTime.parse(dto.getInputTime(), timeFormatter);
        } catch (DateTimeParseException e) {
        	LOGGER.info(e.getMessage());
            return false;
        }
		
		if (ingredientBatchService.get(dto.getIngredientBatchCode())==null) {
			LOGGER.info("Invalid ingredient batch ID: " + dto.getIngredientBatchCode());
			return false;
		}
		
		if (lotSevice.findById(dto.getLotId())==null) {
			System.out.println("Invalid Lot ID: " + dto.getLotId());
			return false;
		}
		
		if (machineService.get(dto.getMachineId())==null) {
			LOGGER.info("Invalid Machine ID: " + dto.getMachineId());
			return false;
		}
		
		if ((dto.getMoisture()==null)||(dto.getMoisture()<0.0)||(dto.getMoisture()>2.0)) {
			LOGGER.info("Invalid Moisture: " + dto.getMoisture());
			return false;
		}
		
		if ((dto.getPressure()<100.0)||(dto.getPressure()>200.0)) {
			LOGGER.info("Invalid Pressure: " + dto.getPressure());
			return false;
		}
		
		if ((dto.getInputTemp()<160.0)||(dto.getInputTemp()>180.0)) {
			LOGGER.info("Invalid InputTemp: " + dto.getInputTemp());
			return false;
		}
		
		if ((dto.getpH()<6.0)||(dto.getpH()>8.0)) {
			LOGGER.info("Invalid pH: " + dto.getpH());
			return false;
		}

		return true;
	}

	@Override
	public boolean isValid(SieveDrying sd) {
		
		if (sd.getWorker()==null) {
			return false;
		}
		if (sd.getQc()==null) {
			return false;
		}
		if (sd.getVerifier()==null) {
			return false;
		}
		if (sd.getTypeProduct()==null) {
			return false;
		}
				
		if (sd.getIngredientBatch()==null) {
			return false;
		}
		
		if (sd.getLotId()==null) {
			return false;
		}
		
		if (sd.getMachine()==null) {
			return false;
		}
		
		if ((sd.getMoisture()<0f)||(sd.getMoisture()>2f)) {
			return false;
		}
		
		if ((sd.getPressure()<100f)||(sd.getPressure()>200f)) {
			return false;
		}
		
		if ((sd.getInputTemp()<160f)||(sd.getInputTemp()>180f)) {
			return false;
		}
		
		if ((sd.getpH()<90f)||(sd.getInputTemp()>95f)) {
			return false;
		}

		return true;
	}
}

package com.dpm.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.constant.Constants;
import com.dpm.dao.PressingMornitorDAO;
import com.dpm.dto.PressingMonitorDTO;
import com.dpm.entity.Employee;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.PressingMornitor;
import com.dpm.service.DefaultSettingService;
import com.dpm.service.PressingMornitorService;
@Service
public class PressingMornitorServiceImpl implements PressingMornitorService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PressingMornitorServiceImpl.class);

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	@Value("${excel-temp-path}")
	private String excelTmpPath;

	@Autowired
	private PressingMornitorDAO pressingMornitorDao;
	
	@Autowired
	private DefaultSettingService defaultSettingService;

	public PressingMornitorServiceImpl(PressingMornitorDAO pressingMornitorDao) {
		this.pressingMornitorDao = pressingMornitorDao;
	}

	// Get all pressing monitor without condition
	@Override
	public Page<PressingMornitor> listAll(Pageable pageable) {
		return pressingMornitorDao.findAll(pageable);
	}
	
	// List all pressing monitor with status
	@Override
	public Page<PressingMornitor> listAllByStatus(Integer status, Pageable pageable) {
		return pressingMornitorDao.findAllByStatus(status, pageable);
	}

	// Get data by id
	@Override
	public Optional<PressingMornitor> getById(Integer id) {
		return pressingMornitorDao.findById(id);
	}

	// Get data between two point date
	@Override
	public Page<PressingMornitor> findByInputTimeBetween(LocalDate start, LocalDate end, Pageable pageable) {

		Page<PressingMornitor> list = null;
		list = pressingMornitorDao.findAllByStatusNotAndInputTimeBetweenOrderByInputTimeDesc(Constants.STATUS.DELETE, start.atStartOfDay(), end.atTime(23, 59, 59), pageable);

		return list;

	}
	
	@Override
	public Page<PressingMornitor> findAllByStatusAndInputTimeBetween(Integer status, LocalDate start, LocalDate end,
			Pageable pageable) {
		
		Page<PressingMornitor> list = null;
		list = pressingMornitorDao.findAllByStatusNotAndStatusAndInputTimeBetweenOrderByInputTimeDesc(Constants.STATUS.DELETE, status, start.atStartOfDay(), end.atTime(23, 59, 59), pageable);
		
		return list;
	}

	// Insert data to database by entity
	@Override
	public boolean insert(PressingMonitorDTO pm) {
		if (!pressingMornitorDao.existsById(pm.getId())) {
			try {
				Employee emp = new Employee();
				emp.setId(pm.getCreateIdEmployee());
				PressingMornitor tmp = pm.toEntity();
				tmp.setCreateEmployee(emp);
				
				pressingMornitorDao.save(tmp);
				return true;
			} catch (Exception e) {
				LOGGER.error("Class: PressingMornitorServiceImpl [Func] insert: " + e.getMessage());
			}
		}
		return false;
	}

	// Update data to database by entity
	@Override
	public boolean update(PressingMonitorDTO pm) {
		if (pressingMornitorDao.existsById(pm.getId())) {
			try {
				Employee emp = new Employee();
				emp.setId(pm.getCreateIdEmployee());
				PressingMornitor tmp = pm.toEntity();
				tmp.setCreateEmployee(emp);
				
				pressingMornitorDao.save(tmp);
				return true;
			} catch (Exception e) {
				LOGGER.error("Class: PressingMornitorServiceImpl [Func] update: " + e.getMessage());
			}
		}
		return false;
	}

	// Delete data to database by entity
	@Override
	public boolean delete(Integer id) {
		try {
			Integer rows = pressingMornitorDao.updateStatusById(Constants.STATUS.DELETE, id);
			if(rows > 0) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Class: PressingMornitorServiceImpl [Func] delete: " + e.getMessage());
		}
		return false;
	}

	// Get all data have input_time same parameter date
	@Override
	public Page<PressingMornitor> findAllByDay(LocalDate date, Pageable pageable) {
		return pressingMornitorDao.findAllByStatusNotAndInputTimeBetweenOrderByInputTimeDesc(Constants.STATUS.DELETE, date.atStartOfDay(), date.atTime(23, 59, 59),
				pageable);
	}

	// Export report excel and use output stream make downloading
	@Override
	public File exportExcelByDate(LocalDate startDate, LocalDate endDate, Integer status) throws IOException {

		workbook = new XSSFWorkbook();
		FileOutputStream outFile = null;
		
		try {
			
			File file = new File(excelTmpPath);
			if(file.isFile()) {
				file.delete();
			} else {
				file.getParentFile().mkdirs();
			}

			// Get list pressing monitor
			List<PressingMornitor> list = pressingMornitorDao
					.findAllByInputTimeBetweenOrderByInputTimeDesc(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

			//Set style
			CellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			font.setFontHeight(16);
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.CENTER);

			//Create sheet pressing monitor
			sheet = workbook.createSheet("Pressing Monitor");

			// Create header
			createCell(sheet.createRow(0), 0, "CÔNG TY TNHH ABC", style);
			createCell(sheet.createRow(1), 0, "ABC CO., LTD", style);

			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 3));
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 4, 9));

			createCell(sheet.createRow(3), 0, "BIỂU MẪU GIÁM SÁT ÉP CỐT", style);
			createCell(sheet.createRow(4), 0, "PRESSING MONITORING FORM", style);
			
			style = workbook.createCellStyle();
			font = workbook.createFont();
			font.setBold(true);
			font.setFontHeight(16);
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.LEFT);

			Row row2 = sheet.createRow(6);
			createCell(row2, 0, "Từ ngày: " + startDate.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE), style);
			createCell(row2, 4, "Đến ngày: " + endDate.atTime(23, 59, 59).format(DateTimeFormatter.ISO_LOCAL_DATE), style);

			//Write header table
			writeHeaderLine();
			
			//Write data in table body
			writeDataLines(list);
			
			outFile = new FileOutputStream(file);
			workbook.write(outFile);
			
			return file;

		} catch (IOException ex) {
			LOGGER.error(ex.getMessage());
			throw ex;
		} finally {
			workbook.close();
			outFile.close();
		}
	
	}

	private void writeHeaderLine() {
		Row row = sheet.createRow(8);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);

		createCell(row, 0, "Tên NCC/ Mã lô NL", style);
		createCell(row, 1, "Máy", style);
		createCell(row, 2, "Loại SP", style);
		createCell(row, 3, "Ngày nhập NL", style);
		createCell(row, 4, "Giờ nhập NL", style);
		createCell(row, 5, "Khối lượng (Kg)", style);
		createCell(row, 6, "Tình trạng lưới lọc cốt \n trước khi ép (Đ/K)", style);
		createCell(row, 7, "Thời gian ép cốt", style);
		createCell(row, 8, "Áp suất< 50 (Đ/K)", style);
		createCell(row, 9, "Khối lượng cốt", style);
		createCell(row, 10, "Xác (Kg)", style);
		createCell(row, 11, "Ghi chú", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	
	private void writeFooter(int row) {
		
		sheet.addMergedRegion(new CellRangeAddress(row + 1, row + 1, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(row + 1, row + 1, 7, 9));
		sheet.addMergedRegion(new CellRangeAddress(row + 2, row + 2, 0, 1));
		
		Row r = sheet.createRow(row + 1);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		
		createCell(r, 0, "QC", style);
		createCell(r, 7, "Người thẩm tra / Verifier", style);
		
		r = sheet.createRow(row + 2);
		createCell(r, 0, " GMP0203BTC-BM03,PB04", style);
		
	}

	private void writeDataLines(List<PressingMornitor> list) {

		int rowCount = 9;

		CellStyle evenRowStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		evenRowStyle.setFont(font);
		evenRowStyle.setBorderBottom(BorderStyle.THIN);
		evenRowStyle.setBorderTop(BorderStyle.THIN);
		evenRowStyle.setBorderRight(BorderStyle.THIN);
		evenRowStyle.setBorderLeft(BorderStyle.THIN);
		
		CellStyle oddRowStyle = workbook.createCellStyle();
		oddRowStyle.setFont(font);
		oddRowStyle.setBorderBottom(BorderStyle.THIN);
		oddRowStyle.setBorderTop(BorderStyle.THIN);
		oddRowStyle.setBorderRight(BorderStyle.THIN);
		oddRowStyle.setBorderLeft(BorderStyle.THIN);
		oddRowStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		oddRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		for (PressingMornitor p : list) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			IngredientBatch batch = p.getIngredientBatch();

			CellStyle style = (rowCount % 2) == 0 ? evenRowStyle : oddRowStyle;
			
			createCell(row, columnCount++,
					batch.getSupplier().getSupplierName() + " / " + batch.getIngredientBatchCode(), style);
			createCell(row, columnCount++, p.getMachine().getMachineCode() + "/" + p.getMachine().getMachineName(), style);
			createCell(row, columnCount++, p.getTypeProduct().getTypeProductCode() + "/" + p.getTypeProduct().getTypeProductName(), style);
			createCell(row, columnCount++, p.getInputTime().toLocalDate().toString(), style);
			createCell(row, columnCount++, p.getInputTime().toLocalTime().toString(), style);
			createCell(row, columnCount++, p.getWeight(), style);
			createCell(row, columnCount++, p.getNetStatusBeforePress() ? "Đạt" : "Không đạt", style);
			createCell(row, columnCount++, p.getPressingTime(), style);
			createCell(row, columnCount++, p.isPressureCondition() ? "Đạt" : "Không đạt", style);
			createCell(row, columnCount++, p.getWeightCoconutMilk(), style);
			createCell(row, columnCount++, p.getWeightResidue(), style);
			createCell(row, columnCount++, p.getNote(), style);

		}
		
		writeFooter(rowCount);
		
	}
	
	// Get default value from database
	@Override
	public PressingMornitor getDefaultValue() {
		return defaultSettingService.getDefaultValuePressingMonitor();
	}

	// Set default value by entity
	@Override
	public boolean setDefaultValueByEntity(PressingMornitor pm) {
		return defaultSettingService.updateDefaultValuePresingMonitor(pm);
	}

	//Get value form id and set default value with this data
	@Override
	public boolean setDefaultValueById(Integer id) {
		
		Optional<PressingMornitor> optional = this.getById(id);
		if(optional.isPresent()) {
			return defaultSettingService.updateDefaultValuePresingMonitor(optional.get());
		}
		return false;
		
	}
	
	// Change status to refuse of all ids
	@Override
	public boolean refuse(Integer[] ids) {
		try {
			Integer rows = pressingMornitorDao.updateStatusByIds(Constants.STATUS.REJECTED, ids);
			if(rows > 0) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Class: PressingMornitorServiceImpl [Func] refuse: " + e.getMessage());
		}
		return false;
	}
	
	// Change status to approve of all ids
	@Override
	public boolean approve(Integer[] ids) {
		try {
			Integer rows = pressingMornitorDao.updateStatusByIds(Constants.STATUS.VERIFIED, ids);
			if(rows > 0) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Class: PressingMornitorServiceImpl [Func] approve: " + e.getMessage());
		}
		return false;
	}

}

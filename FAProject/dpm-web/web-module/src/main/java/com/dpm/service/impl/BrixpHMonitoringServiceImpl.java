package com.dpm.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.dao.TrackingPHBxDAO;
import com.dpm.entity.TrackingPHBx;
import com.dpm.service.TrackingPHBxFormService;

@Service
public class BrixpHMonitoringServiceImpl implements TrackingPHBxFormService {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	@Autowired
	TrackingPHBxDAO trackingPHBxDAO;

	@Override
	public List<TrackingPHBx> listForms() {
		List<TrackingPHBx> listAll = new ArrayList<TrackingPHBx>();
		listAll = trackingPHBxDAO.findAll();
		for (TrackingPHBx form : listAll){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime time = LocalTime.parse(form.getPumpingTime().format(formatter));
			form.setPumpingTime(time);
		}
		return listAll;
	}

	@Override
	public void addForm(TrackingPHBx form) {
		trackingPHBxDAO.save(form);
	}

	@Override
	public TrackingPHBx findFormById(int id) {
		
		return trackingPHBxDAO.findById(id).get();
	}

	@Override
	public boolean deleteById(int id) {
		trackingPHBxDAO.deleteById(id);
		return true;
	}

	@Override
	public Page<TrackingPHBx> listFormsByDate(LocalDate date, int page) {
		page--;
		Pageable paging = PageRequest.of(page, 5);
		Page<TrackingPHBx> list = trackingPHBxDAO.findByDate(date, paging);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		for (TrackingPHBx form : list){
			LocalTime time = LocalTime.parse(form.getPumpingTime().format(formatter));
			form.setPumpingTime(time);
		}
		return list;
	}
	
	@Override
	public List<TrackingPHBx> listFormsByDate(LocalDate date) {
		List<TrackingPHBx> list = trackingPHBxDAO.findByDate(date);
		int batch = 0;
		for (int i = 0; i<list.size();i++) {
			LocalDate sameDate = list.get(0).getDate();
			if(list.get(i).getDate().equals(sameDate))	
				batch+=1;
			else
				batch = 1;
			list.get(i).setBatchNo(batch);
			}
		for (TrackingPHBx form : list){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime time = LocalTime.parse(form.getPumpingTime().format(formatter));
			form.setPumpingTime(time);
		}
		return list;
	}
	
	/**
	 * @param LocalDate, int, int
	 * @return Page<TrackingPHBx>
	 * 
	 */
	@Override
	public Page<TrackingPHBx> listFormsByStatus(LocalDate date, int status, int page) {
		page--;
		Pageable paging = PageRequest.of(page, 5);
		Page<TrackingPHBx> formList = trackingPHBxDAO.findByDateAndStatus(date, status, paging);
		return formList;
	}
	
	@Override
	public void approvalSelected(Integer[] list, int status) {
		
		for (Integer i : list) {
			TrackingPHBx approvalForm = this.findFormById(i);
			approvalForm.setStatus(status);
			this.addForm(approvalForm);
		}
	}
	//TODO export file excel
	@Override
	public void exportExcel(HttpServletResponse response, List<TrackingPHBx> exportList) {
		String date= "no data";
		if(!exportList.isEmpty()) {
			TrackingPHBx trackingDemo = exportList.get(0);
			date = trackingDemo.getDate().toString();
			
		}

		workbook = new XSSFWorkbook();
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		
		
		sheet = workbook.createSheet("pH Brix Monitoring Form");
		
		
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 12));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 12));
		

		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 3));
		
		//table
		sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1));
		
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 5));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 7));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 8, 9));
		
		sheet.addMergedRegion(new CellRangeAddress(5, 6, 10, 10));
		sheet.addMergedRegion(new CellRangeAddress(5, 6, 11, 11));
		sheet.addMergedRegion(new CellRangeAddress(5, 6, 12, 12));
		sheet.addMergedRegion(new CellRangeAddress(5, 6, 13, 13));
		sheet.addMergedRegion(new CellRangeAddress(5, 6, 14, 14));
		sheet.addMergedRegion(new CellRangeAddress(5, 6, 15, 15));

		createCell(sheet.createRow(1), 0, "BIỂU MẪU THEO DÕI ĐỘ PH-BRIX", style);
		createCell(sheet.createRow(2), 0, "PH-BRIX MONITORING FORM", style);
		
		style = workbook.createCellStyle();
		font = workbook.createFont();
		font.setFontHeight(16);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.LEFT);
		
		writeHeaderLine(date, style);
		writeTableHeader(style);
		writeDataLines(exportList);
		
		ServletOutputStream stream;
		try {
			stream = response.getOutputStream();
			workbook.write(stream);
			workbook.close();
			stream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
				
	}
	
	private void writeHeaderLine(String date, CellStyle style) {
		style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);	
		font.setBold(false);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
	}
	
	private void writeTableHeader(CellStyle style) {
		
		Row tableHeader = sheet.createRow(5);
		Row tableSubHeader = sheet.createRow(6);

		XSSFFont font = workbook.createFont();	
		font.setBold(true);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setShrinkToFit(true);
		style.setWrapText(true);
		
		
		createCell(tableHeader, 0, "Số\n mẻ", style);
		createCell(tableSubHeader,0, "", style);
		createCell(tableHeader,1, "Mã lô NL", style);
		createCell(tableSubHeader,1, "", style);
		
		createCell(tableHeader,2, "Trước trộn", style);
		createCell(tableHeader,3, "", style);
		createCell(tableHeader,4, "", style);
		createCell(tableHeader,5, "", style);
		createCell(tableSubHeader, 2, "Giờ bơm", style);
		createCell(tableSubHeader, 3, "Áp suất\n(2kg/cm2)", style);
		createCell(tableSubHeader, 4, "pH", style);
		createCell(tableSubHeader, 5, "Brix", style);	
		
		createCell(tableHeader,6, "Trộn", style);
		createCell(tableHeader,7, "", style);
		createCell(tableSubHeader, 6, "pH", style);
		createCell(tableSubHeader, 7, "Brix", style);
		
		createCell(tableHeader,8, "Đồng hóa", style);
		createCell(tableHeader,9, "", style);
		createCell(tableSubHeader, 8, "pH", style);
		createCell(tableSubHeader, 9, "Brix", style);
		
		createCell(tableHeader,10, "Loại sản phẩm", style);
		createCell(tableSubHeader, 10, "", style);
		createCell(tableHeader,11, "Mã lô thành phẩm", style);
		createCell(tableSubHeader, 11, "", style);
		createCell(tableHeader,12, "Người thực hiện", style);
		createCell(tableSubHeader, 12, "", style);
		createCell(tableHeader,13,"Loại sản phẩm",style);
		createCell(tableSubHeader, 13, "", style);
		createCell(tableHeader,14,"Máy SP",style);
		createCell(tableSubHeader, 14, "", style);
		createCell(tableHeader,15,"Ngày thực hiện",style);
		createCell(tableSubHeader, 15, "", style);
		
		
		

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
	
	private void writeDataLines(List<TrackingPHBx> exportList) {

		int rowCount = 7;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(12);
	
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		int batchNo = 1;
		for (TrackingPHBx trackingForm : exportList) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
	
			createCell(row, columnCount++,batchNo++, style);
			createCell(row, columnCount++, trackingForm.getIngredient().getIngredientBatchName(), style);
			createCell(row, columnCount++, trackingForm.getPumpingTime().toString(), style);
			createCell(row, columnCount++, trackingForm.getPressure(), style);
			createCell(row, columnCount++, trackingForm.getpHPreMix(), style);
			createCell(row, columnCount++, String.valueOf(trackingForm.getBrixPreMix()), style);
			createCell(row, columnCount++, trackingForm.getpHMix(), style);
			createCell(row, columnCount++, String.valueOf(trackingForm.getBrixMix()), style);
			createCell(row, columnCount++, trackingForm.getpHHomo(), style);
			createCell(row, columnCount++, String.valueOf(trackingForm.getBrixHomo()), style);
			createCell(row, columnCount++, trackingForm.getPacking().getPackingName(), style);
			createCell(row, columnCount++, trackingForm.getProductLot().getLotCode(), style);
			createCell(row, columnCount++, trackingForm.getPersonInCharge().getEmployeeName(), style);
			createCell(row, columnCount++, trackingForm.getProductLot().getTypeProduct().getTypeProductName(), style);
			createCell(row, columnCount++, trackingForm.getMachine().getMachineName(), style);
			createCell(row, columnCount++, trackingForm.getDate().toString(), style);
		}
		writeFooter(rowCount);
	}
	
private void writeFooter(int row) {
		
		
		Row r = sheet.createRow(row + 1);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(12);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		
		createCell(r, 3, "QC", style);
		createCell(r, 13, "Người thẩm tra / Verifier", style);
		
	}




}

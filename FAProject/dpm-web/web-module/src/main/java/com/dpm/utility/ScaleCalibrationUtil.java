package com.dpm.utility;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.GroupLayout.Alignment;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dpm.entity.ScaleCalibration;

/**
 * 
 * @author ThuanLV6
 * utilities for scale calibration
 * created at 21/01 4:22 PM
 *
 */
public class ScaleCalibrationUtil {
	   private XSSFWorkbook workbook;
	    private XSSFSheet sheet;
	    private List<ScaleCalibration> listScaleCalibrations;
	     
	    public ScaleCalibrationUtil(List<ScaleCalibration> listScaleCalibrations) {
	        this.listScaleCalibrations = listScaleCalibrations;
	        workbook = new XSSFWorkbook();
	    }
	
	 
	    private void writeHeaderLine() {
	        sheet = workbook.createSheet("Scale calibrations");
	        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:L2")); // merge title cells
	        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:A4")); 
	        sheet.addMergedRegion(CellRangeAddress.valueOf("B3:B4")); 
	        sheet.addMergedRegion(CellRangeAddress.valueOf("C3:C4")); 
	        sheet.addMergedRegion(CellRangeAddress.valueOf("D3:D4")); 
	        sheet.addMergedRegion(CellRangeAddress.valueOf("E3:E4")); 
	        sheet.addMergedRegion(CellRangeAddress.valueOf("F3:I3")); 
	        sheet.addMergedRegion(CellRangeAddress.valueOf("J3:J4")); 
	        sheet.addMergedRegion(CellRangeAddress.valueOf("K3:K4")); 
	        sheet.addMergedRegion(CellRangeAddress.valueOf("L3:L4")); 
	        
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setBold(true);
	        font.setFontHeight(16);
	        style.setFont(font);
	        style.setAlignment(HorizontalAlignment.CENTER);
	        style.setVerticalAlignment(VerticalAlignment.CENTER);
	        style.setWrapText(true);
	        
	    	createCell(sheet.createRow(1), 0, "SỔ THEO DÕI HIỆU CHUẨN CÂN HẰNG NGÀY", style);
	        
	    	Row row = sheet.createRow(2);
	        int colNum = 0;
	        createCell(row, colNum++, "STT", style);      
	        createCell(row, colNum++, "Ký hiệu", style);      
	        createCell(row, colNum++, "Giờ hiệu chuẩn", style);    
	        createCell(row, colNum++, "Tên thiết bị", style);    
	        createCell(row, colNum++, "Thiết bị chuẩn", style);  
	        createCell(row, colNum, "Kết quả hiệu chuẩn", style);  
	      
	        Row row3 = sheet.createRow(3);
	        createCell(row3, colNum++, "1", style);    
	        createCell(row3, colNum++, "2", style);
	        createCell(row3, colNum++, "3", style);
	        createCell(row3, colNum++, "TB", style);
	        createCell(row, colNum++, "Người hiệu chuẩn", style);
	        createCell(row, colNum++, "Người kiểm tra", style);
	        createCell(row, colNum++, "Ghi chú", style);
	         
//	        for (int i=0; i< colNum; i++) {
//	        	sheet.autoSizeColumn(i);
//	        }
	    }
	     
	    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	        sheet.autoSizeColumn(columnCount);
	        Cell cell = row.createCell(columnCount);
	        if (value instanceof Integer) {
	            cell.setCellValue((Integer) value);
	        } else if(value instanceof Float) {
	        	cell.setCellValue((Float) value);
	        } else if (value instanceof Boolean) {
	            cell.setCellValue((Boolean) value);
	        }else {
	            cell.setCellValue((String) value);
	        }
	        cell.setCellStyle(style);
	    }
	 
	    private void writeDataLines() {
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setFontHeight(14);
	        style.setFont(font);
//	        style.setWrapText(true);
	        int orderNumber = 1;
	        int rowCount = 4;
	        for (ScaleCalibration scaleCalibration : listScaleCalibrations) {
	            Row row = sheet.createRow(rowCount++);
	            int columnCount = 0;
	            createCell(row, columnCount++, orderNumber++, style); // numerical order
	            createCell(row, columnCount++, scaleCalibration.getScaleSymbol().getSymbolString(), style); // scale symbol
	            createCell(row, columnCount++, scaleCalibration.getCreatedDate().toString(), style); // calibrated date
	            createCell(row, columnCount++, scaleCalibration.getCalibratedDevice().getMachineName(), style); // calibrated device
	            createCell(row, columnCount++, scaleCalibration.getStandardDevice().getMachineName(), style); // standard device
	            createCell(row, columnCount++, scaleCalibration.getFirstResult(), style); // result 1
	            createCell(row, columnCount++, scaleCalibration.getSecondResult(), style); // result 2
	            createCell(row, columnCount++, scaleCalibration.getThirdResult(), style); // result 3
	            createCell(row, columnCount++, scaleCalibration.getAvarageResult(), style); // result average
	            createCell(row, columnCount++, (scaleCalibration.getCalibrator()!=null ? 
	            		scaleCalibration.getCalibrator().getEmployeeName() :
	            		"") , style); // calibrator name
	            createCell(row, columnCount++, (scaleCalibration.getInspector()!=null ? 
	            		scaleCalibration.getInspector().getEmployeeName() :
	            		"") , style); // inspector name
	            createCell(row, columnCount++, scaleCalibration.getComment(), style);
	             
	        }
	    }
	     
	    public void export(HttpServletResponse response) throws IOException {
	        writeHeaderLine();
	        writeDataLines();
	         
	        ServletOutputStream outputStream = response.getOutputStream();
	        workbook.write(outputStream);
	        workbook.close();
	         
	        outputStream.close();
	         
	    }
	
}

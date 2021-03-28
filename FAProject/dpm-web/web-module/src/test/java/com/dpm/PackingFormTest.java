package com.dpm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dpm.entity.PackingForm;
import com.dpm.service.PackingFormService;

/**
 * 
 * @author HoiNX1
 *
 */

@SpringBootTest
public class PackingFormTest {

	@Autowired
	private PackingFormService packingFormService;
	
	@Test
	public void testSaveAll() {
		PackingForm temp = new PackingForm();
		temp.setShift(1);
//		temp.setTime(LocalTime.now());
		
		assertEquals(1,packingFormService.save(temp).getShift());
	}
	
	@Test
	public void testFindAll() {
		assertEquals(1, packingFormService.getAllPackingForm().size());
	}
	
	
}

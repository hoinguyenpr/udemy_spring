package com.dpm.automation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration
@TestPropertySource("/test.properties") 
public class CheckListModulTest {

	private  WebDriver driver ;
	@Value("${server.host}")
	private  String host;
	
	@Value("${server.port}")
	private  String port;
	
	@Before
	public  void setUp() throws UnknownHostException {
		WebDriverManager.chromedriver().setup();
		
   
		driver  = new ChromeDriver();
		driver.get(host+":"+port);
		
		String username = "//*[@id=\"username\"]";
		String passwork = "//*[@id=\"password\"]";
		String login = "/html/body/div[1]/div/div/div/div/div/form/div[3]/button";
		
		driver.findElement(By.xpath(username)).sendKeys("user");
		driver.findElement(By.xpath(passwork)).sendKeys("123");
		 
		driver.findElement(By.xpath(login)).click();
	}
	@Test
	public void testManagementCheckList() {
		
		
		 String navItem = "/html/body/div/nav/div[2]/div/ul/li[2]/a";
		 String managerUrl = "/html/body/div/nav/div[2]/div/ul/li[2]/div/div[2]/div[5]/ul/li[1]/a";
		 driver.findElement(By.xpath(navItem)).click();
		 driver.findElement(By.xpath(managerUrl)).click();
		 assertEquals(host+":"+port+"/checklist/create-check-list", driver.getCurrentUrl());
		 
	}
	
	@Test
	public void testfilterCheckList() {
		
		
		 String navItem = "/html/body/div/nav/div[2]/div/ul/li[2]/a";
		 String filterUrl = "/html/body/div/nav/div[2]/div/ul/li[2]/div/div[2]/div[5]/ul/li[2]/a";
		 driver.findElement(By.xpath(navItem)).click();
		 driver.findElement(By.xpath(filterUrl)).click();
		 assertEquals(host+":"+port+"/checklist/show-check-list", driver.getCurrentUrl());
		 
	}
	
}	

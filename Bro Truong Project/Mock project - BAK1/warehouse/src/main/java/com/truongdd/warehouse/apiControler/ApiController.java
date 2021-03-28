package com.truongdd.warehouse.apiControler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truongdd.warehouse.entity.ItemOrder;
import com.truongdd.warehouse.entity.ItemStock;
import com.truongdd.warehouse.itemStockService.ItemStockService;
import com.truongdd.warehouse.itemStockService.OrderService;

@RestController
@RequestMapping("/stock")
public class ApiController {
	@Autowired
	private ItemStockService itemStockService;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/all")
	public List<ItemStock> getAllStockInfo() {
		return itemStockService.getAllStockInfo();
	}
	
	@GetMapping("/{partNumber}")
	public ItemStock getStockInfoByPartNumber(@PathVariable String partNumber) {
		return itemStockService.getStockInfoByPartNumber(partNumber);
	}
	
	@PostMapping("/intake")
	public List<ItemStock> inventoryIntake(@RequestBody List<ItemStock> itemList){
		System.out.println("Commencing inventory intake: " + itemList.toString());
		List<ItemStock> invalidItem = new ArrayList<ItemStock>();
		boolean intakeResult;
		for (ItemStock item : itemList) {
			intakeResult = itemStockService.stockIntake(item);
			if (!intakeResult) {
				invalidItem.add(item);
			}
		}
		return invalidItem;
	}
	
	@PostMapping("/withdraw")
	public List<ItemStock> inventoryWithdraw(@RequestBody List<ItemStock> itemList){
		System.out.println("Commencing inventory withdraw: " + itemList.toString());
		List<ItemStock> invalidItem = new ArrayList<ItemStock>();
		boolean withdrawResult;
		for (ItemStock item : itemList) {
			withdrawResult = itemStockService.stockWithdraw(item);
			if (!withdrawResult) {
				invalidItem.add(item);
			}
		}
		return invalidItem;
	}
	
	@GetMapping("/getOrder/{orderId}")
	public Optional<ItemOrder> getOrder(@PathVariable Integer orderId) {
		Optional<ItemOrder> itemOrder = null;
		itemOrder = orderService.getOrderById(orderId);
		return itemOrder;
	}
	
}

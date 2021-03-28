package com.truongdd.warehouse.itemStockService;

import java.util.List;

import com.truongdd.warehouse.entity.ItemOrder;
import com.truongdd.warehouse.entity.ItemStock;
import com.truongdd.warehouse.entity.OrderDetail;

public interface ItemStockService {
	
	public boolean stockIntake(ItemStock itemList);
	
	public boolean stockWithdraw(ItemStock itemList);
	
	public ItemStock getStockInfoByPartNumber(String partNumber);
	
	public List<ItemStock> getAllStockInfo();

	public OrderDetail stockWithdraw(OrderDetail orderDetail);
	

}

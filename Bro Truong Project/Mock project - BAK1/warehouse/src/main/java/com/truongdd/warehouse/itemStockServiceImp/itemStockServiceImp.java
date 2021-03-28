package com.truongdd.warehouse.itemStockServiceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truongdd.warehouse.entity.ItemStock;
import com.truongdd.warehouse.entity.OrderDetail;
import com.truongdd.warehouse.itemStockDao.ItemStockDAO;
import com.truongdd.warehouse.itemStockDao.OrderDetailDAO;
import com.truongdd.warehouse.itemStockDao.OrderServiceDAO;
import com.truongdd.warehouse.itemStockService.ItemStockService;

@Component
public class itemStockServiceImp implements ItemStockService {
	@Autowired
	private ItemStockDAO itemStockDao;
	
	@Autowired
	private OrderServiceDAO orderServiceDao;
	
	@Autowired
	private OrderDetailDAO orderDetailDao;

	@Override
	public boolean stockIntake(ItemStock itemStock) {
		ItemStock tempItem = new ItemStock();
		int temp_stock_sum = 0;
		System.out.println("stockIntake - isValidPart: " + itemStock.getPartNumber() + " " + itemStockDao.isValidPart(itemStock.getPartNumber()));
		if (itemStockDao.isValidPart(itemStock.getPartNumber())) {
			System.out.println("stockIntake - itemStockDao.existsByPartNumber: " + itemStock.getPartNumber() + " - " + itemStockDao.existsByPartNumber(itemStock.getPartNumber()));
			try {
				if (itemStockDao.existsByPartNumber(itemStock.getPartNumber())) {
					tempItem = itemStockDao.getByPartNumber(itemStock.getPartNumber());
					System.out.println("stockIntake - current stock " + tempItem.getPartNumber() + " - " + tempItem.getStockQty());
					temp_stock_sum = tempItem.getStockQty() + itemStock.getStockQty();
					System.out.println("stockIntake - new stock value: " + tempItem.getPartNumber() + " - " + temp_stock_sum);
					tempItem.setStockQty(temp_stock_sum);
					itemStockDao.save(tempItem);
					System.out.println("stockIntake - Intake success: " + tempItem.getPartNumber() + " - " + tempItem.getStockQty() + " unit");
					System.out.println("--------------------------------------");
					return true;
				}else {
					System.out.println("stockIntake - add new item to stock: " + itemStock.getPartNumber() + " - " + itemStock.getStockQty() + " unit");
					itemStockDao.save(itemStock);
					System.out.println("itemStockServiceImp - Intake success: " + itemStock.getPartNumber() + " - " + itemStock.getStockQty() + " unit");
					System.out.println("--------------------------------------");
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("itemStockServiceImp - Invalid part: " + itemStock.getPartNumber());
		System.out.println("--------------------------------------");
		return false;
	}

	@Override
	public boolean stockWithdraw(ItemStock itemStock) {
		ItemStock tempItem = new ItemStock();
		int temp_stock_sum = 0;
		System.out.println("stockWithdraw - isValidPart: " + itemStock.getPartNumber() + " " + itemStockDao.isValidPart(itemStock.getPartNumber()));
		
		if ((itemStockDao.isValidPart(itemStock.getPartNumber()) && (itemStockDao.existsByPartNumber(itemStock.getPartNumber())))) {
			try {
				tempItem = itemStockDao.getByPartNumber(itemStock.getPartNumber());
				System.out.println("stockWithdraw - current stock " + tempItem.getPartNumber() + " - " + tempItem.getStockQty());
				temp_stock_sum = tempItem.getStockQty() - itemStock.getStockQty();
				System.out.println("stockWithdraw - new stock value: " + tempItem.getPartNumber() + " - " + temp_stock_sum);
				tempItem.setStockQty(temp_stock_sum);
				itemStockDao.save(tempItem);
				System.out.println("stockWithdraw - Withdrawal success: " + itemStock.getPartNumber() + " - " + itemStock.getStockQty() + " unit");
				System.out.println("--------------------------------------");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public ItemStock getStockInfoByPartNumber(String partNumber) {
		return itemStockDao.getByPartNumber(partNumber);
	}

	@Override
	public List<ItemStock> getAllStockInfo() {
		return itemStockDao.findAll();
	}

	@Override
	public OrderDetail stockWithdraw(OrderDetail orderDetail) {
		ItemStock tempItem;
		int temp_stock_sum = 0;
		
		if ((itemStockDao.isValidPart(orderDetail.getPartNumber()) && (itemStockDao.existsByPartNumber(orderDetail.getPartNumber())))) {
			try {
				tempItem = itemStockDao.getByPartNumber(orderDetail.getPartNumber());
				System.out.println("stockWithdraw - current stock " + tempItem.getPartNumber() + " - " + tempItem.getStockQty());
				if (tempItem.getStockQty() >= orderDetail.getOrderedQty()) {
					temp_stock_sum = tempItem.getStockQty() - orderDetail.getOrderedQty();
					System.out.println("stockWithdraw - new stock value: " + tempItem.getPartNumber() + " - " + temp_stock_sum);
					tempItem.setStockQty(temp_stock_sum);
					orderDetail.setDeliveredQty(orderDetail.getOrderedQty());
//					itemStockDao.save(tempItem);
					itemStockDao.saveAndFlush(tempItem);
					orderDetailDao.saveAndFlush(orderDetail);
				}
				else if (tempItem.getStockQty()==0) {
					orderDetail.setNote("Out of Stock");
				}
				else {
					orderDetail.setDeliveredQty(tempItem.getStockQty());
					orderDetail.setNote("Partial delivered");
					tempItem.setStockQty(0);
					itemStockDao.saveAndFlush(tempItem);
					orderDetailDao.saveAndFlush(orderDetail);
				}
				itemStockDao.flush();
				System.out.println("stockWithdraw - Withdrawal success: " + orderDetail.getPartNumber() + " - " + orderDetail.getDeliveredQty() + " unit");
				System.out.println("--------------------------------------");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orderDetail;
	}



}

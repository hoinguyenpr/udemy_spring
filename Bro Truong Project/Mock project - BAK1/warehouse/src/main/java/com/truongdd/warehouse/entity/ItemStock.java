package com.truongdd.warehouse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_stock", uniqueConstraints = { @UniqueConstraint(columnNames = { "part_number" }) })
public class ItemStock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int itemId;

	@Column(name = "part_number")
	@NotNull
	private String partNumber;

	@Column(name = "stock_qty")
	@NotNull
	private int stockQty;

	public void setStockQty(int stockQty) {
		if (stockQty >= 0) {
			this.stockQty = stockQty;
		} else
			this.stockQty = 0;
	}

}

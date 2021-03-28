package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_order")

public class ItemOrder {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int orderId;
	
	@NotNull
	@Column(name="request_owner")
	private String requestOwner;
	
	@NotNull
	@Column(name="order_time")
	private LocalDateTime time;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "parentOrder", cascade = {CascadeType.ALL})
	private List<OrderDetail> contents;
	
	@NotNull
	@Column(name = "order_status")
	private String status;
	
	public void add(OrderDetail orderDetail) {
		if (contents == null) {
			contents = new ArrayList<>();
		}
		contents.add(orderDetail);
	}

}

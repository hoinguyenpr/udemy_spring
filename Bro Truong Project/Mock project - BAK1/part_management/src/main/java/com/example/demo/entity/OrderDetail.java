package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="order_detail")
public class OrderDetail {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int orderItemId;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonIgnore
	private ItemOrder parentOrder;
	
	@NotNull
	@Column(name="part_number")
	private String partNumber;
	
	@NotNull
	@Column(name="part_desc")
	private String partDesc;
	
	@NotNull
	@Column(name = "order_qty")
	private int orderedQty;
	
	@NotNull
	@Column(name = "delivered_qty")
	private int deliveredQty;
	
	@NotNull
	@Column(name = "note")
	private String note;

}

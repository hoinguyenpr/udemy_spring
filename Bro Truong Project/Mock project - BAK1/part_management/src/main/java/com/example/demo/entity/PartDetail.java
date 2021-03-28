package com.example.demo.entity;

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

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "part_detail",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "part_number" }),
                @UniqueConstraint(columnNames = { "part_desc" })
        })
public class PartDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int partId;
	
	@NotNull
	@Column(name="part_number", unique = true)
	private String partNumber;
	
	@NotNull
	@Column(name = "part_desc", unique = true)
	private String partDesc;
	
	@NotNull
	@Column(name = "status")
	private String status;
	
}

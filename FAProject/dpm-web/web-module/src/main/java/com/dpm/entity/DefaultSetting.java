package com.dpm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Nationalized;

/**
 * 
 * @author LamPQT
 * @Date 25/12/21
 * @modified: TruongDD - 01/26/2020 restructured
 */
@Entity
@Table(name = "default_setting")
public class DefaultSetting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "prefix")
	@Nationalized
	@NotEmpty
	private String prefix;

	@Column(name = "key_default")
	@Nationalized
	@NotEmpty
	private String key;

	@Column(name = "value_default")
	@Nationalized
	private String value;

	public DefaultSetting(String prefix, String key, String value) {
		super();

		this.prefix = prefix;
		this.key = key;
		this.value = value;
	}

	public DefaultSetting() {
		super();
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DefaultSetting [id=" + id + ", prefix=" + prefix + ", key=" + key
				+ ", value=" + value + "]";
	}

}

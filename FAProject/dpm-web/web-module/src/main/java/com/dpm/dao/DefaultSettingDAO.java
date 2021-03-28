package com.dpm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.dpm.entity.DefaultSetting;

@Repository
public interface DefaultSettingDAO extends JpaRepository<DefaultSetting, Integer> {
	

	List<DefaultSetting> findByPrefix(String prefix);
	
	DefaultSetting findByPrefixAndKey(String prefix,String key);
	
}

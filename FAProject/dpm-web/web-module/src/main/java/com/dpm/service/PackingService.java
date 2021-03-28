package com.dpm.service;

import java.util.List;

import com.dpm.entity.Packing;

public interface PackingService {

	public void addPacking(Packing packing);

	public void deletePacking(int id);

	public List<Packing> listAll();

	public Packing findById(int id);
}

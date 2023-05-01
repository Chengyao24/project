package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Member;
import com.example.demo.model.Porder;

public interface PorderService {
	List<Porder> getAllPorder();

	void savePorder(Porder porder);

	void deletePorder(long id);

	List<Porder> getPorderByMemberId(long id);
	
}

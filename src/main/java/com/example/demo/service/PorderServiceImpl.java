package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Porder;

@Service
public class PorderServiceImpl implements PorderService {

	@Autowired
	private PorderRepository porderRepository;

	@Override
	public List<Porder> getAllPorder() {

		return porderRepository.findAll();
	}

	@Override
	public void savePorder(Porder porder) {

		this.porderRepository.save(porder);
	}

	@Override
	public void deletePorder(long id) {

		this.porderRepository.deleteById(id);
	}

	@Override
	public List<Porder> getPorderByMemberId(long id) {
		return this.porderRepository.findPorderByMemberId(id);	
	}

}

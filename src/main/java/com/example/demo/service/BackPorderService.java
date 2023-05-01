package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Porder;

public interface BackPorderService {
	
	List <Porder> getAllPorders();
    void savePorder(Porder porder);
    Porder getPorderById(long id);
    void deletePorderById(long id);
    public List<Porder> getByKeyword(String keyword);

}

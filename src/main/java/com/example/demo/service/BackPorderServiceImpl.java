package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Porder;
import com.example.demo.repository.BackPorderRepository;

@Service
public class BackPorderServiceImpl implements BackPorderService {
	
    @Autowired
    private BackPorderRepository porderRepository;
	
	@Override
    public List <Porder> getAllPorders() {
        return porderRepository.findAll();
    }

    @Override
    public void savePorder(Porder porder) {
        this.porderRepository.save(porder);
    }

    @Override
    public Porder getPorderById(long id) {
    	//Optional是Lumda語法
        Optional <Porder> optional = porderRepository.findById(id);
        Porder order = null;
        if (optional.isPresent()) { //如果optional的指定id存在
        	order = optional.get(); //optional取得資料
        } else {
            throw new RuntimeException(" Porder not found for id :: " + id); //錯誤顯示:找不到指定id
        }
        return order;
    }

    @Override
    public void deletePorderById(long id) {
        this.porderRepository.deleteById(id);
    }

	@Override
	public List<Porder> getByKeyword(String keyword) {
		return porderRepository.findByKeyword(keyword);
	}

}

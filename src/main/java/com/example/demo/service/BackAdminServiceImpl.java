package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.BackAdminRepository;

@Service
public class BackAdminServiceImpl implements BackAdminService {

    @Autowired
    private BackAdminRepository adminRepository;

    @Override
    public List <Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public void saveAdmin(Admin admin) {
        this.adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(long id) {
    	//Optional是Lumda語法
        Optional <Admin> optional = adminRepository.findById(id);
        Admin admin = null;
        if (optional.isPresent()) { //如果optional的指定id存在
        	admin = optional.get(); //optional取得資料
        } else {
            throw new RuntimeException(" Admin not found for id :: " + id); //錯誤顯示:找不到指定id
        }
        return admin;
    }

    @Override
    public void deleteAdminById(long id) {
        this.adminRepository.deleteById(id);
    }

    @Override
	public boolean checkCodeAndPassword(String code, String password) {
        Optional<Admin> optional = adminRepository.findByCode(code);
        if (optional.isPresent() && optional.get().getPassword().equals(password)) {
            return true;
        } 
        else {
            return false;
        }
	}

	@Override
	public Admin getAdminCode(String code) {
		Optional<Admin> optional=adminRepository.findByCode(code);
		Admin admin = null;
        if (optional.isPresent()) { //如果optional的指定id存在
        	admin = optional.get(); //optional取得資料
        } else {
            throw new RuntimeException(" Admin not found for code :: " + code); //錯誤顯示:找不到指定id
        }
        return admin;
	}

	@Override
	public List<Admin> getByKeyword(String keyword) {
		return adminRepository.findByKeyword(keyword);
	}
	
}


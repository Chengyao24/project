package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Admin;
import com.example.demo.model.Member;

public interface BackAdminService {

	List <Admin> getAllAdmins();
    void saveAdmin(Admin admin);
    Admin getAdminById(long id);
    void deleteAdminById(long id);
    Admin getAdminCode(String code);
	boolean checkCodeAndPassword(String code, String password);
	public List<Admin> getByKeyword(String keyword);
}


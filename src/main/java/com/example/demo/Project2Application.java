package com.example.demo;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.service.FilesStorageService;

@SpringBootApplication
public class Project2Application implements CommandLineRunner {

	@Resource
	 FilesStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(Project2Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		storageService.init();//初始化檔案儲存的環境或設定	
	}

}

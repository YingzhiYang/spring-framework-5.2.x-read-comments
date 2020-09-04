package com.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Repository
public class IndexDao {
	public IndexDao() {
		System.out.println("Constructor");
	}

	@PostConstruct
	public void init(){
		System.out.println("init");
	}

	public void query(){
		System.out.println("query");
	}
}

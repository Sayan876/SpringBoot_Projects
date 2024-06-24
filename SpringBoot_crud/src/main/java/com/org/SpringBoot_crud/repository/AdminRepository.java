package com.org.SpringBoot_crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.SpringBoot_crud.dto.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
	
	public List<Admin> findByName(String name);
	
	Optional<Admin> findByPhoneAndPassword(long phone, String password);
}

package com.org.SpringBoot_crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.org.SpringBoot_crud.dto.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

	
	public List<Hospital> findByName(String name);
	
	@Query("select a.hospitals from Admin a where a.id=?1")
	public List<Hospital> findbyAdminId(int id);
	
	@Query("select a.hospitals from Admin a where a.phone=?1 and a.password=?2")
	public List<Hospital> verifyByphoneandpass(long phone, String pass);
}

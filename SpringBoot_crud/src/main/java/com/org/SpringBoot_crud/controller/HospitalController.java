package com.org.SpringBoot_crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.SpringBoot_crud.dto.Hospital;
import com.org.SpringBoot_crud.dto.ResponseStructure;
import com.org.SpringBoot_crud.server.HospitalService;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {
    
	@Autowired
	private HospitalService hospitalService;
	
	@PostMapping("/{id}")
	public ResponseEntity<ResponseStructure<Hospital>> saveHospital(@RequestBody Hospital hospital,@PathVariable int id) {
		return hospitalService.saveHospital(hospital, id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Hospital>> findbyhospitalid(@PathVariable(name="id") int id) {
		return hospitalService.findbyid(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<Hospital>> updateHosp(@RequestBody Hospital hospital,@PathVariable(name="id")  int id){
		return hospitalService.updateHospital(hospital,id);
	}
	
	
	@GetMapping("/find-by-name/{hos_name}")
	public ResponseEntity<ResponseStructure<List<Hospital>>> findbyname(@PathVariable(name="hos_name") String name){
		return hospitalService.findByName(name);
	}
	
	@GetMapping("/find-by-adminid/{admin_id}")
	public ResponseEntity<ResponseStructure<List<Hospital>>> findbyadminid(@PathVariable(name="admin_id") int id){
		return hospitalService.findbyadmin(id);
	}
	
	@PostMapping("/verify-by-ppass")
	public ResponseEntity<ResponseStructure<List<Hospital>>> verifyby(@RequestParam(name="phone") long phone, @RequestParam(name="password") String password){
		return hospitalService.verify(phone, password);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteHospital(@PathVariable(name="id") int id) {
		return hospitalService.Deletehos(id);
	}
}

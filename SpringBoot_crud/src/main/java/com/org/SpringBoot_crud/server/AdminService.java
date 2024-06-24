package com.org.SpringBoot_crud.server;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.org.SpringBoot_crud.dto.Admin;
import com.org.SpringBoot_crud.dto.ResponseStructure;
import com.org.SpringBoot_crud.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
  private AdminRepository adminRepository;
	
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(Admin admin) {
		ResponseStructure<Admin> structure = new ResponseStructure<Admin>();
		structure.setMessage("admin saved successfully");
		structure.setData(adminRepository.save(admin));
		structure.setStatuscode(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED.value()).body(structure);
	}
	
	public ResponseEntity<ResponseStructure<Admin>> findById(int id) {
		ResponseStructure<Admin> structure = new ResponseStructure<Admin>();
		Optional<Admin> recAdmin = adminRepository.findById(id);
		if(recAdmin.isPresent()) {
			structure.setMessage("admin Found");
			structure.setData(recAdmin.get());
			structure.setStatuscode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
			
		}else {
			structure.setMessage("admin not found");
			structure.setData(null);
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Admin>>> findByName(String name){
		ResponseStructure<List<Admin>> structure = new ResponseStructure<>();
		List<Admin> admins = adminRepository.findByName(name);
		    if(admins.isEmpty()) {
		    	structure.setMessage("Name not found");
				structure.setData(null);
				structure.setStatuscode(HttpStatus.NOT_FOUND.value());
				return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(structure);
		    }
			structure.setMessage("Name matched");
			structure.setData(admins);
			structure.setStatuscode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK.value()).body(structure);
			
		
	}
	
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(Admin admin) {
		ResponseStructure<Admin> structure = new ResponseStructure<Admin>();
		Optional<Admin> recAdmin = adminRepository.findById(admin.getId());
		if(recAdmin.isPresent()) {
			Admin dbAdmin = recAdmin.get();
			dbAdmin.setEmail(admin.getEmail());
			dbAdmin.setName(admin.getName());
			dbAdmin.setPassword(admin.getPassword());
			dbAdmin.setPhone(admin.getPhone());
			structure.setMessage("Admin updated");
			structure.setData(adminRepository.save(admin));
			structure.setStatuscode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(structure);
		}else {
			structure.setData(null);
			structure.setMessage("cannot updated the Admin");
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
		}
	}
	
	
	public ResponseEntity<ResponseStructure<Admin>> verify(long phone, String password) {
		ResponseStructure<Admin> structure = new ResponseStructure<Admin>();
		Optional<Admin> recAdmin = adminRepository.findByPhoneAndPassword(phone, password);
		if(recAdmin.isPresent()) {
			structure.setMessage("verification successful");
			structure.setData(recAdmin.get());
			structure.setStatuscode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
			
		}else {
			structure.setMessage("admin not found");
			structure.setData(null);
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
		}
	}
	
	public ResponseEntity<ResponseStructure<String>> delete(int id) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		Optional<Admin> recAdmin = adminRepository.findById(id);
		if(recAdmin.isPresent()) {
			adminRepository.delete(recAdmin.get());
			structure.setMessage("Admin Found and proceed");
			structure.setData("Admin Deleted");
			structure.setStatuscode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK.value()).body(structure);
		}else {
			structure.setMessage("Cannot Fetch the Admin");
			structure.setData("Cannot delete sorry");
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(structure);
		}
	}
}

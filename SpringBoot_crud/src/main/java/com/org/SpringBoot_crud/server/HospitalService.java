package com.org.SpringBoot_crud.server;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.org.SpringBoot_crud.dto.Admin;
import com.org.SpringBoot_crud.dto.Hospital;
import com.org.SpringBoot_crud.dto.ResponseStructure;
import com.org.SpringBoot_crud.repository.AdminRepository;
import com.org.SpringBoot_crud.repository.HospitalRepository;

@Service
public class HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private AdminRepository adminRepository;

	public ResponseEntity<ResponseStructure<Hospital>> saveHospital(Hospital hospital, int id) {
		ResponseStructure<Hospital> structure = new ResponseStructure<>();
		Optional<Admin> recAdmin = adminRepository.findById(id);
		if (recAdmin.isPresent()) {
			Admin dbAdmin = recAdmin.get();
			dbAdmin.getHospitals().add(hospital);
			hospital.setAdmin(dbAdmin);
			adminRepository.save(dbAdmin);
			structure.setData(hospitalRepository.save(hospital));
			structure.setMessage("Hospital added succesfully");
			structure.setStatuscode(HttpStatus.CREATED.value());
			return ResponseEntity.status(HttpStatus.CREATED.value()).body(structure);
		} else {
			structure.setMessage("cannot add hospital as Admin id is Invalid");
			structure.setData(null);
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(structure);
		}
	}

	public ResponseEntity<ResponseStructure<Hospital>> findbyid(int id) {
		ResponseStructure<Hospital> structure = new ResponseStructure<>();
		Optional<Hospital> rechos = hospitalRepository.findById(id);
		if (rechos.isPresent()) {
			structure.setMessage("Hospital Found");
			structure.setData(rechos.get());
			structure.setStatuscode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK.value()).body(structure);
		} else {
			structure.setMessage("Not found");
			structure.setData(null);
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(structure);
		}
	}

	public ResponseEntity<ResponseStructure<Hospital>> updateHospital(Hospital hospital, int id) {
		ResponseStructure<Hospital> structure = new ResponseStructure<>();
		Optional<Admin> recAdmin = adminRepository.findById(id);
		Optional<Hospital> rechos = hospitalRepository.findById(hospital.getId());
		if (rechos.isPresent()) {
			Admin dbAdmin = recAdmin.get();
			Hospital dbh = rechos.get();
			dbAdmin.getHospitals().add(hospital);
			hospital.setAdmin(dbAdmin);
			dbh.setFounder(hospital.getFounder());
			dbh.setGstNumber(hospital.getGstNumber());
			dbh.setName(hospital.getName());
			dbh.setYearOfEstablishment(hospital.getYearOfEstablishment());
			structure.setMessage("Hospital details updated");
			structure.setData(hospitalRepository.save(hospital));
			structure.setStatuscode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(structure);
		} else {
			structure.setMessage("Cannot Update the hospital details");
			structure.setData(null);
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(structure);
		}
	}

	public ResponseEntity<ResponseStructure<List<Hospital>>> findByName(String name) {
		ResponseStructure<List<Hospital>> structure = new ResponseStructure<>();
		List<Hospital> hospitals = hospitalRepository.findByName(name);
		if (hospitals.isEmpty()) {
			structure.setMessage("No hospitals found Under this name");
			structure.setData(null);
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(structure);
		} else {
			structure.setMessage("Matched");
			structure.setData(hospitals);
			structure.setStatuscode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK.value()).body(structure);
		}
	}

	public ResponseEntity<ResponseStructure<List<Hospital>>> findbyadmin(int aid) {
		ResponseStructure<List<Hospital>> structure = new ResponseStructure<>();
		List<Hospital> hospitals = hospitalRepository.findbyAdminId(aid);
		if (hospitals.isEmpty()) {
			structure.setMessage("Admin id not found");
			structure.setData(null);
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(structure);
		} else {
			structure.setMessage("Admin Found");
			structure.setData(hospitals);
			structure.setStatuscode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK.value()).body(structure);
		}
	}
	
	
	public ResponseEntity<ResponseStructure<List<Hospital>>> verify(long phone, String password){
		ResponseStructure<List<Hospital>> structure = new ResponseStructure<>();
		List<Hospital> hospitals = hospitalRepository.verifyByphoneandpass(phone, password);
		if(hospitals.isEmpty()) {
			structure.setMessage("Invalid Phone or password");
			structure.setData(null);
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(structure);
		}else {
			structure.setMessage("Phone and Password matched");
			structure.setData(hospitals);
			structure.setStatuscode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK.value()).body(structure);
		}
	}
	
	public ResponseEntity<ResponseStructure<String>> Deletehos(int id) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		Optional<Hospital> rechos = hospitalRepository.findById(id);
		if(rechos.isPresent()) {
			hospitalRepository.delete(rechos.get());
			structure.setMessage("The hospital is deleted successfully");
			structure.setData("Yes Deleted");
			structure.setStatuscode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK.value()).body(structure);
		}else {
			structure.setMessage("Ah! Jesus Christ..Something's wrong");
			structure.setData("Cannot Delete");
			structure.setStatuscode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(structure);
		}
	}
}

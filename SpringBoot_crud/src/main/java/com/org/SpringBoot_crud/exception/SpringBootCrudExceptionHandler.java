package com.org.SpringBoot_crud.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.org.SpringBoot_crud.dto.ResponseStructure;
@ControllerAdvice
public class SpringBootCrudExceptionHandler extends ResponseEntityExceptionHandler{
    
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseStructure<String>> handle(ConstraintViolationException exception){
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setMessage(exception.getErrorMessage());
		structure.setData("cannot save the data");
		structure.setStatuscode(HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structure);
	}
}

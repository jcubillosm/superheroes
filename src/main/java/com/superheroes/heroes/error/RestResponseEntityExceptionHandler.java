package com.superheroes.heroes.error;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.superheroes.heroes.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler  {

	 @ExceptionHandler({EmptyResultDataAccessException.class,
	      InvalidDataAccessResourceUsageException.class})
	  protected ResponseEntity<Object> handleEmptyResultDataAccess(
	      final RuntimeException ex, final WebRequest request) {
	    final String bodyOfResponse = "Superheroe not found";
	    log.error(bodyOfResponse, ex);
	    return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND,
	        request);
	  }

	  @ExceptionHandler({ResourceNotFoundException.class})
	  protected ResponseEntity<Object> handleResourceNotFound(
	      final RuntimeException ex, final WebRequest request) {
	    log.error(ex.getMessage(), ex);
	    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND,
	        request);
	  }
}

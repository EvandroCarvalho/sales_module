package br.com.salesModule.handler;

import java.util.Calendar;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.salesModule.error.ErrorDetails;
import br.com.salesModule.error.ItemsNotFound;
import br.com.salesModule.error.ItemsNotFoundDetails;
import br.com.salesModule.error.ValidationErrorDetails;
import javassist.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ErrorDetails {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException manfException) {
		String filedErros = manfException.getBindingResult().getFieldErrors()
				.stream()
				.map(FieldError::getField)
				.collect(Collectors.joining(","));
		String fieldMessages = manfException.getBindingResult().getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.joining(",")); 
		
		ValidationErrorDetails vEDetails = ValidationErrorDetails.builder()
				.timestamp(Calendar.getInstance().getTime().getTime())
				.field(filedErros)
				.fieldMessage(fieldMessages)
				.title("Field Validation Error")
				.detail("Field Validation Error")
				.developerMessage(manfException.getClass().getName())
				.status(HttpStatus.BAD_REQUEST.value())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(vEDetails);	
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDetails> handlerItemsNotFound(ItemsNotFound infException) {
		ErrorDetails errorDescription = ItemsNotFoundDetails.builder()
			.title(infException.getMessage())
			.status(HttpStatus.NOT_FOUND.value())
			.detail(infException.getClass().getName())
			.timestamp(Calendar.getInstance().getTime().getTime())
			.developerMessage("Item(s) not found on the register")
			.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDescription);	
	}
}

package br.com.salesModule.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
	
	private String title;
	private int status;
	private String detail;
	private long timestamp;
	private String developerMessage;

}

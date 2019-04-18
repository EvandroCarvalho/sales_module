package br.com.salesModule.error;

import lombok.Builder;

public class ItemsNotFoundDetails extends ErrorDetails {
	
	@Builder
	public ItemsNotFoundDetails(String title, int status, String detail, 
			long timestamp, String developerMessage, String field, String fieldMessage) {
		super(title,status,detail,timestamp,developerMessage);
	}

}

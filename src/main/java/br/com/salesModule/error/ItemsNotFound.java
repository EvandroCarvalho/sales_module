package br.com.salesModule.error;

import javassist.NotFoundException;

public class ItemsNotFound extends NotFoundException {

	public ItemsNotFound(String msg) {
		super(msg);
	}
//	@Builder
//	public ItemsNotFound(String title, int status, String detail, 
//			long timestamp, String developerMessage) {
//		super(title,status,detail,timestamp,developerMessage);
//	}
//		
}

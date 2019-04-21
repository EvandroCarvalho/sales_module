package br.com.salesModule.error;

import javassist.NotFoundException;

public class ItemsNotFound extends NotFoundException {

	public ItemsNotFound(String msg) {
		super(msg);
	}	
}

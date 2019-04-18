package br.com.salesModule.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface AbstractEntity extends Serializable {
	
	public Long getId();

}

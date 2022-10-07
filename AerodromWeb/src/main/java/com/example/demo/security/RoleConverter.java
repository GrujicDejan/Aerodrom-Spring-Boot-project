package com.example.demo.security;


import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.repository.UlogaRepository;

import model.Uloga;


public class RoleConverter  implements Converter<String, Uloga> {
	
	UlogaRepository r;
	
	public RoleConverter(UlogaRepository r){
		this.r=r;
	}
	
	public Uloga convert(String source) {
			  int idRole=-1;
		       try{
		    	   idRole=Integer.parseInt(source);
		       }
		       catch(NumberFormatException e){
		    	   throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(Uloga.class),idRole, null);
		       }
		       Uloga role=r.getById(idRole);
		      return role;
		  }
}


package com.example.securingweb.validators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.securingweb.entities.Brand;
import com.example.securingweb.repository.BrandRepository;

@Component
public class BrandValidator implements Validator{
	
	@Autowired protected MessageSource messagesSource;
	@Autowired protected BrandRepository brandRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Brand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.debug("validate function has the error ---- REHAN in validate()");

		Brand brand = (Brand)target;
		String code = brand.getCode();
		Brand brandByCode = brandRepository.findByCode(code);
		if(brandByCode != null){
			logger.debug("validate function has the error ---- REHAN in validate() inside if");
			errors.rejectValue("code", "error.exists", new Object[]{code},"Code "+code+" already in use");
		}
	}

}

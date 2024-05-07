package com.example.securingweb.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.securingweb.entities.ShipClass;
import com.example.securingweb.repository.ShipClassRepository;

@Component
public class ShipClassValidator implements Validator{
	
	@Autowired protected MessageSource messagesSource;
	@Autowired protected ShipClassRepository shipClassRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		
		return ShipClass.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.debug("validate function has the error ---- REHAN in validate()");

		ShipClass shipClass = (ShipClass)target;
		String code = shipClass.getCode();
		ShipClass shipClassByCode = shipClassRepository.findByCode(code);
		if(shipClassByCode != null){
			logger.debug("validate function has the error ---- REHAN in validate() inside if");
			errors.rejectValue("code", "error.exists", new Object[]{code},"Code "+code+" already in use");
		}
	}

}

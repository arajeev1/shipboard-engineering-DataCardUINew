package com.example.securingweb.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.securingweb.entities.Port;
import com.example.securingweb.repository.PortRepository;

@Component
public class PortValidator implements Validator{
	
	@Autowired protected MessageSource messagesSource;
	@Autowired protected PortRepository portRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Port.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.debug("validate function has the error ---- REHAN in validate()");

		Port port = (Port)target;
		String code = port.getCode();
		Port portByCode = portRepository.findByCode(code);
		if(portByCode != null){
			logger.debug("validate function has the error ---- REHAN in validate() inside if");
			errors.rejectValue("code", "error.exists", new Object[]{code},"Code "+code+" already in use");
		}
	}

}

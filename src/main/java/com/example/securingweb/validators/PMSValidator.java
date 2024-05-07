package com.example.securingweb.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.securingweb.entities.PMS;
import com.example.securingweb.repository.PMSRepository;

@Component
public class PMSValidator implements Validator{
	
	@Autowired protected MessageSource messagesSource;
	@Autowired protected PMSRepository pmsRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		
		return PMS.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.debug("validate function has the error ---- REHAN in validate()");

		PMS pms = (PMS)target;
		String code = pms.getCode();
		PMS pmsByCode = pmsRepository.findByCode(code);
		if(pmsByCode != null){
			logger.debug("validate function has the error ---- REHAN in validate() inside if");
			errors.rejectValue("code", "error.exists", new Object[]{code},"Code "+code+" already in use");
		}
	}

}

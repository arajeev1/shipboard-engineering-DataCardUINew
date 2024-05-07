package com.example.securingweb.validators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.securingweb.entities.Team;
import com.example.securingweb.repository.TeamRepository;

@Component
public class TeamValidator implements Validator{
	
	@Autowired protected MessageSource messagesSource;
	@Autowired protected TeamRepository teamRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Team.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.debug("validate function has the error ---- REHAN in validate()");

		Team team = (Team)target;
		String name = team.getName();
		Team teamName = teamRepository.findByName(name);
		if(teamName != null){
			logger.debug("validate function has the error ---- REHAN in validate() inside if");
			errors.rejectValue("name", "error.exists", new Object[]{name},"Name "+name+" already in use");
		}
	}

}

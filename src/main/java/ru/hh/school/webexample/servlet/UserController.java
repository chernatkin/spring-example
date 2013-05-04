package ru.hh.school.webexample.servlet;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import ru.hh.school.webexample.entity.User;
import ru.hh.school.webexample.service.UserService;


@Controller
@RequestMapping("user")
public class UserController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void postConstruct(){
		objectMapper.registerModule(new Hibernate4Module());
	}
	
	@RequestMapping(method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public String getUser(@RequestParam(value = "id", required = true) final String id) throws HibernateException, ObjectNotFoundException, JsonProcessingException{
		if(!StringUtils.hasText(id)){ throw new IllegalArgumentException("Parameter {id} must not be empty"); }
		
		return objectMapper.writeValueAsString(userService.get(Long.parseLong(id)));
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces="application/json")
	@ResponseBody
	public String setUser(@RequestParam(value = "firstName", required = true) final String firstName,
						@RequestParam(value = "middleName", required = true) final String middleName,
						@RequestParam(value = "lastName", required = true) final String lastName) throws HibernateException, JsonProcessingException{
		return objectMapper.writeValueAsString(userService.save(new User(null, firstName, middleName, lastName)));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class})
	@ResponseBody
	public String badRequest(IllegalArgumentException e){
		LOGGER.warn(HttpStatus.BAD_REQUEST.toString(), e);
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseBody
	public void notFound(Exception e){	
		LOGGER.warn(HttpStatus.NOT_FOUND.toString(), e);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public void internalFail(Exception e){
		LOGGER.warn(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e);
	}
}

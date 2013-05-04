package ru.hh.school.webexample.servlet;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.hh.school.webexample.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class UserControllerTest {
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private SessionFactory factory;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
    public void userGetByIdTest() throws NumberFormatException, HibernateException, JsonProcessingException {
		final String user = userController.getUser("1");
		Assert.assertTrue(user != null);
    }
	
	@Test(expected = ObjectNotFoundException.class)
    public void userGetByNotExistentIdTest() {
		//userController.getUser("-1");
    }

	@Test(expected = IllegalArgumentException.class)
    public void userGetByNullIdTest() {
		//userController.getUser(null);
    }
	
	@Test(expected = NumberFormatException.class)
    public void userGetBySymbolicIdTest() {
		//userController.getUser("a");
    }
	
	@Test
    public void userPutTest() throws HibernateException, JsonProcessingException {
		final String userId = userController.setUser("abcd", "hyt", "gnb");
		Assert.assertTrue(StringUtils.hasText(userId));
		
		Assert.assertTrue(factory.openSession().get(User.class, Long.valueOf(userId)) != null);
    }
}

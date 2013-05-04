package ru.hh.school.webexample.servlet;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void userGetByIdTest() throws NumberFormatException, HibernateException, IOException {
		final Long ID = 1L;
		final String userJson = userController.getUser(ID.toString());
		Assert.assertNotNull(userJson);
		
		final User user = objectMapper.readValue(userJson, User.class);
		Assert.assertNotNull(user);
		Assert.assertEquals(ID, user.getId());
    }
	
	@Test(expected = ObjectNotFoundException.class)
    public void userGetByNotExistentIdTest() throws ObjectNotFoundException, HibernateException, JsonProcessingException {
		userController.getUser("-1");
    }

	@Test(expected = IllegalArgumentException.class)
    public void userGetByNullIdTest() throws ObjectNotFoundException, HibernateException, JsonProcessingException {
		userController.getUser(null);
    }
	
	@Test(expected = NumberFormatException.class)
    public void userGetBySymbolicIdTest() throws ObjectNotFoundException, HibernateException, JsonProcessingException {
		userController.getUser("a");
    }
	
	@Test
    public void userPutTest() throws HibernateException, IOException {
		final String userJson = userController.setUser("abcd", "hyt", "gnb");
		Assert.assertNotNull(userJson);
		
		final User user = objectMapper.readValue(userJson, User.class);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
		
		Assert.assertTrue(factory.openSession().get(User.class, user.getId()) != null);
    }
}

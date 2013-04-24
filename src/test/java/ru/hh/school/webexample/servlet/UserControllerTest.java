package ru.hh.school.webexample.servlet;

import org.hibernate.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class UserControllerTest {
	
	@Autowired
	private UserController userController;
	
	@Test
    public void userGetByIdTest() {
		final String user = userController.getUser("1");
		Assert.assertTrue(StringUtils.hasText(user));
    }
	
	@Test(expected = ObjectNotFoundException.class)
    public void userGetByNotExistentIdTest() {
		userController.getUser("-1");
    }

	@Test(expected = IllegalArgumentException.class)
    public void userGetByNullIdTest() {
		userController.getUser(null);
    }
	
	@Test(expected = NumberFormatException.class)
    public void userGetBySymbolicIdTest() {
		userController.getUser("a");
    }
}

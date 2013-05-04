package ru.hh.school.webexample.service;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.hh.school.webexample.entity.User;

@Component
@Transactional(rollbackFor = Throwable.class)
public class UserService {
	
	@Autowired
	private SessionFactory factory;

	@Transactional(readOnly = true)
	public User get(final long id) throws HibernateException{
		final User user = (User)factory.getCurrentSession().load(User.class, id);
		Hibernate.initialize(user);
		return user;
	}
	
	public Long save(final User user) throws HibernateException{
		return (Long)factory.getCurrentSession().save(user);
	}
	
}

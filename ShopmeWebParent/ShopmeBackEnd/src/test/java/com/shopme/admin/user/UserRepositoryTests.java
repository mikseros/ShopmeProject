package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userMikseros = new User("mmadynski@gmail.com", "123456", "Mikseros", "Mikseros");
		userMikseros.addRole(roleAdmin);
		
		User savedUser = repo.save(userMikseros);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		User userJohn = new User("john@doe.com", "123456", "John", "Doe");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userJohn.addRole(roleEditor);
		userJohn.addRole(roleAssistant);
		
		User savedUser = repo.save(userJohn);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userMikseros = repo.findById(1).get();
		System.out.println(userMikseros);
		assertThat(userMikseros).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userJohn = repo.findById(2).get();
		userJohn.setEnabled(true);
		userJohn.setEmail("johndoe@gmail.com");
		
		repo.save(userJohn);
	}
}

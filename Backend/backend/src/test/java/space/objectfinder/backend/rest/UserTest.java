/*******************************************************************************
 * Copyright 2017 Chris Deter, Arne Salveter, Sven Marquardt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package space.objectfinder.backend.rest;

import java.time.LocalDateTime;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.BeaconRepository;
import space.objectfinder.backend.service.RoleRepository;
import space.objectfinder.backend.service.UserRepository;

/**
 * @author Arne Salveter
 * @since 20.06.2017
 */
//@Ignore	
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { "dev" })
public class UserTest {

	@LocalServerPort
	int port;
	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	TestRestTemplate restTemplate;

	String userUrl = "/users";
	String roleUrl = "/roles";
	
	
	HttpHeaders headers = new HttpHeaders();

	@Before
	public void setUp() {
		this.headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@After
	public void after() {
		this.userRepo.deleteAll();
		this.roleRepo.deleteAll();
	}
	
	
	@Test
	public void ApostUserTest() {
		// post data
		Role role = new Role().name("Admin");
		HttpEntity<Role> roleRequest = new HttpEntity<>(role, this.headers);
		ResponseEntity<Role> roleEntity = this.restTemplate.postForEntity(roleUrl, roleRequest, Role.class);
		Assert.assertThat(roleEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		User user = new User().email("nase@base.de").name("Hannes").password("1234").role(roleEntity.getBody()).lastUpdate(LocalDateTime.now());
		HttpEntity<User> userRequest = new HttpEntity<>(user, this.headers);
		ResponseEntity<User> userEntity = this.restTemplate.postForEntity(userUrl, userRequest, User.class);
		Assert.assertThat(userEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Assert.assertThat(userEntity.getBody().getId(), CoreMatchers.notNullValue());
		Assert.assertThat(userEntity.getBody().getName(), CoreMatchers.is(user.getName()));
		Assert.assertThat(userEntity.getBody().getRole(), CoreMatchers.is(user.getRole()));
	}

	@Test
//	@Ignore
	public void BgetsUserTest() {
		// post data
		Role role = new Role().name("Admin");
		HttpEntity<Role> roleRequest = new HttpEntity<>(role, this.headers);
		ResponseEntity<Role> roleEntity = this.restTemplate.postForEntity(roleUrl, roleRequest, Role.class);
		Assert.assertThat(roleEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		User user = new User().email("nase@base.de").name("Hannes").password("1234").role(roleEntity.getBody()).lastUpdate(LocalDateTime.now());
		HttpEntity<User> userRequest = new HttpEntity<>(user, this.headers);
		ResponseEntity<User> userEntity = this.restTemplate.postForEntity(userUrl, userRequest, User.class);
		Assert.assertThat(userEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		// get Data
//		ResponseEntity<User[]> getUsers = this.restTemplate.getForEntity(userUrl , User[].class);
		userRequest = new HttpEntity<>(null, this.headers);
		ResponseEntity<User[]> getUser = this.restTemplate.exchange(userUrl,	HttpMethod.GET, userRequest, User[].class);
		Assert.assertThat(getUser.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		User body = getUser.getBody()[0];
		Assert.assertThat(body.getId(), CoreMatchers.notNullValue());
		Assert.assertThat(body.getName(), CoreMatchers.is(user.getName()));
		Assert.assertThat(body.getRole(), CoreMatchers.is(user.getRole()));
	}

	
	@Test
	public void CgetUserTest() {
		// post data
		Role role = new Role().name("Admin");
		HttpEntity<Role> roleRequest = new HttpEntity<>(role, this.headers);
		ResponseEntity<Role> roleEntity = this.restTemplate.postForEntity(roleUrl, roleRequest, Role.class);
		Assert.assertThat(roleEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		User user = new User().email("nase@base.de").name("Hannes").password("1234").role(roleEntity.getBody()).lastUpdate(LocalDateTime.now());
		HttpEntity<User> userRequest = new HttpEntity<>(user, this.headers);
		ResponseEntity<User> userEntity = this.restTemplate.postForEntity(userUrl, userRequest, User.class);
		Assert.assertThat(userEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));

		// get Data
		userRequest = new HttpEntity<>(null, this.headers);
		ResponseEntity<User> getUser = this.restTemplate.exchange(userUrl + "/" + userEntity.getBody().getId(),	HttpMethod.GET, userRequest, User.class);
		Assert.assertThat(getUser.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		User body = getUser.getBody();
		Assert.assertThat(body.getId(), CoreMatchers.notNullValue());
		Assert.assertThat(body.getName(), CoreMatchers.is(user.getName()));
		Assert.assertThat(body.getRole(), CoreMatchers.is(user.getRole()));
	}

	@Test
	public void DputBeaconTest() {
		// post data
		Role role = new Role().name("Admin");
		HttpEntity<Role> roleRequest = new HttpEntity<>(role, this.headers);
		ResponseEntity<Role> roleEntity = this.restTemplate.postForEntity(roleUrl, roleRequest, Role.class);
		Assert.assertThat(roleEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		User user = new User().email("nase@base.de").name("Hannes").password("1234").role(roleEntity.getBody()).lastUpdate(LocalDateTime.now());
		HttpEntity<User> userRequest = new HttpEntity<>(user, this.headers);
		ResponseEntity<User> userEntity = this.restTemplate.postForEntity(userUrl, userRequest, User.class);
		Assert.assertThat(userEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));

		// put Data
		user = user.email("user@gmail.com").name("Hannes").password("1234").role(roleEntity.getBody()).lastUpdate(LocalDateTime.now());
		userRequest = new HttpEntity<>(user, this.headers);
		ResponseEntity<User> putUser = this.restTemplate.exchange(userUrl + "/" + userEntity.getBody().getId(),	HttpMethod.PUT, userRequest, User.class);
		Assert.assertThat(putUser.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
	}

	
	@Test
	public void EdeleteLocationTest() {
		// post data
		Role role = new Role().name("Admin");
		HttpEntity<Role> roleRequest = new HttpEntity<>(role, this.headers);
		ResponseEntity<Role> roleEntity = this.restTemplate.postForEntity(roleUrl, roleRequest, Role.class);
		Assert.assertThat(roleEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		User user = new User().email("nase@base.de").name("Hannes").password("1234").role(roleEntity.getBody()).lastUpdate(LocalDateTime.now());
		HttpEntity<User> userRequest = new HttpEntity<>(user, this.headers);
		ResponseEntity<User> userEntity = this.restTemplate.postForEntity(userUrl, userRequest, User.class);
		Assert.assertThat(userEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		userRequest = new HttpEntity<>(null, this.headers);
		ResponseEntity<User> deleteUser = this.restTemplate.exchange(userUrl + "/" + userEntity.getBody().getId(),	HttpMethod.DELETE, userRequest, User.class);
		Assert.assertThat(deleteUser.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
	}
}
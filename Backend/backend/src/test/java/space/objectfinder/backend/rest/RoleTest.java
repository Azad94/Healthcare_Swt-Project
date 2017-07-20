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

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
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

import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.service.RoleRepository;

/**
 * @author Sven Marquardt
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { "dev" })
public class RoleTest {

	@LocalServerPort
	int port;
	@Autowired
	RoleRepository repository;

	@Autowired
	TestRestTemplate restTemplate;

	private final static String ROLEURL = "/roles";

	HttpHeaders headers = new HttpHeaders();

	@Before
	public void setUp() {
		this.headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@After
	public void after() {
		this.repository.deleteAll();
	}

	@Test
	public void testRolePost() {
		final Role role = new Role().name("Admin");
		final HttpEntity<Role> request = new HttpEntity<>(role, this.headers);
		final ResponseEntity<Role> entity = this.restTemplate.postForEntity(RoleTest.ROLEURL, request, Role.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		final Role buRole = entity.getBody();
		Assert.assertThat(buRole.getId(), CoreMatchers.notNullValue());
		Assert.assertThat(buRole.getName(), CoreMatchers.is(role.getName()));
	}

	@Test
	public void testRolePut() {
		final Role role = new Role().name("Admin");
		final HttpEntity<Role> request = new HttpEntity<>(role, this.headers);
		final ResponseEntity<Role> entity = this.restTemplate.postForEntity(RoleTest.ROLEURL, request, Role.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		final Role buffer = entity.getBody();
		buffer.setName("AdminHochZwei");
		final HttpEntity<Role> postEntity = new HttpEntity<>(buffer, this.headers);
		this.restTemplate.exchange(RoleTest.ROLEURL + "/" + buffer.getId(), HttpMethod.PUT, postEntity, Role.class)
				.getBody();
		final ResponseEntity<Role> geEntity = this.restTemplate.exchange(RoleTest.ROLEURL + "/" + buffer.getId(),
				HttpMethod.GET, postEntity, Role.class);
		Assert.assertThat(geEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		final Role role2 = geEntity.getBody();
		Assert.assertThat(role2.getName(), CoreMatchers.not(role.getName()));
		Assert.assertThat(role2.getId(), CoreMatchers.is(buffer.getId()));
		Assert.assertThat(role2.getName(), CoreMatchers.is(buffer.getName()));
	}
}

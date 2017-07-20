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

import java.io.IOException;
import java.time.LocalDateTime;

import org.hamcrest.CoreMatchers;
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
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonParseException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.domain.SyncBody;
import space.objectfinder.backend.domain.User;

/**
 * @author Arne Salveter
 * @since 20.06.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class CleaningTaskTest {

	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate restTemplate;
	HttpHeaders headers = new HttpHeaders();

	private User creator;

	private BeaconObject bObject;

	String url = "";
	
	@Before
	public void setUp() throws Exception {
		this.headers.setContentType(MediaType.APPLICATION_JSON);
		// create role
		final Role role = new Role().name("Admin");
		final HttpEntity<Role> entityRole = new HttpEntity<>(role, this.headers);
		final ResponseEntity<Role> rRole = this.restTemplate.postForEntity("/roles", entityRole, Role.class);
		Assert.assertTrue(rRole.getStatusCode().value() == 200);
		final Beacon beacon = new Beacon().major(1000).minor(1000);
		final HttpEntity<Beacon> beEntity = new HttpEntity<>(beacon, this.headers);
		final Beacon beacon2 = this.restTemplate.postForEntity("/beacons", beEntity, Beacon.class).getBody();
		final Location location = new Location().floor(1).building(1).room(1);
		final HttpEntity<Location> entity = new HttpEntity<>(location, this.headers);
		final Location location2 = this.restTemplate.postForEntity("/locations", entity, Location.class).getBody();
		final BeaconObject beaconObject = new BeaconObject().beacon(beacon2).location(location2).name("Test");
		beaconObject.setState(1);
		final HttpEntity<BeaconObject> bEntity = new HttpEntity<>(beaconObject, this.headers);
		final BeaconObject beaconObject2 = this.restTemplate
				.postForEntity("/beaconObjects", bEntity, BeaconObject.class).getBody();
		// create user
		final User user = new User().name("Admin").password("1234").email("lol@lol.de").lastUpdate(LocalDateTime.now())
				.role(rRole.getBody());
		final HttpEntity<User> entityUser = new HttpEntity<>(user, this.headers);
		final ResponseEntity<User> rUser = this.restTemplate.postForEntity("/users", entityUser, User.class);
		this.creator = rUser.getBody();
		this.bObject = beaconObject2;
	}

	@Test
	public void ApostCleaningTasksTest() throws JsonParseException, JsonMappingException, IOException {

		// create A CleaninigTask
		final CleaningTask cTask = new CleaningTask();
		cTask.setCreationTime(LocalDateTime.now());
		cTask.setCreator(this.creator);
		cTask.setDescription("NASE");
		cTask.setLevel(new Integer(1));
		cTask.setBeaconObject(this.bObject);
		final HttpEntity<CleaningTask> entityCleaningTask = new HttpEntity<>(cTask, this.headers);
		final ResponseEntity<CleaningTask> rCleaningTask = this.restTemplate.postForEntity("/cleaningtasks",
				entityCleaningTask, CleaningTask.class);
		Assert.assertTrue(rCleaningTask.getStatusCode().value() == 200);
	}

	@Test
//	@Ignore
	public void BpostCleaningTasksAppTest() throws Exception {

		// create role

		final Role role = new Role().name("Admin");
		final HttpEntity<Role> entityRole = new HttpEntity<>(role, this.headers);
		final ResponseEntity<Role> rRole = this.restTemplate.postForEntity("/roles", entityRole, Role.class);
		Assert.assertTrue(rRole.getStatusCode().value() == 200);

		// create user

		final User user = new User().name("Admin").password("1234").email("lol@lol.de").lastUpdate(LocalDateTime.now())
				.role(rRole.getBody());
		final HttpEntity<User> entityUser = new HttpEntity<>(user, this.headers);
		final ResponseEntity<User> rUser = this.restTemplate.postForEntity("/users", entityUser, User.class);
		Assert.assertTrue(rUser.getStatusCode().value() == 200);

		// http://localhost:8080/v1/cleaningtasksApp?limit=&before=&since=&open=&closed=&processing=&own=
		// 2016-06-01T12:12:12.111+01:00

		final SyncBody body = new SyncBody();
		body.setId(rUser.getBody().getId());
		body.setUserName(rUser.getBody().getName());
		final HttpEntity<SyncBody> entityCleaningTask = new HttpEntity<>(body, this.headers);
		final ResponseEntity<CleaningTask> rCleaningTask = this.restTemplate.exchange("/cleaningtasksApp",
				HttpMethod.POST, entityCleaningTask, CleaningTask.class);
		Assert.assertTrue(rCleaningTask.getStatusCode().value() == 200);
	}

	@Test
//	@Ignore
	public void CgetCleaningTasksTest(){
//		"http://localhost:8080/v1/cleaningtasks?limit=&before=&since=&open=&closed=&processing=&own="
		
//		/v1/cleaningtasksApp
		
		this.url = "/v1/cleaningtasks"
				+ "limit=1"
				+ "&before=2017-10-01T12:12:12.111+02:00[Europe/Berlin]"
				+ "&since=2016-06-01T12:12:12.111+02:00[Europe/Berlin]"
				+ "&open=true"
				+ "&closed=false"
				+ "&processing=true"
				+ "&own=false";
		
		
		this.url = "/v1/cleaningtasksApp";
		
		String body = "{"
				+ "\"userName\" : \"Admin\" , " 
				+ "\"id\" : 1"
				+ "}";
		
		HttpEntity<String> entityCleaningTask = new HttpEntity<String>(body, headers);
		ResponseEntity<String> rCleaningTask = restTemplate.exchange(createURLWithPort(url)	, HttpMethod.GET, entityCleaningTask, String.class);
		Assert.assertTrue(rCleaningTask.getStatusCode().value() == 200);
	}
	
	@Test
	public void DDeleteCleaningTasksTest(){
//		"http://localhost:8080/v1/cleaningtasks/{taskid}"
		this.url = "/v1/cleaningtasks/1";
		HttpEntity<CleaningTask> entityCleaningTask = new HttpEntity<CleaningTask>(null, headers);
		ResponseEntity<CleaningTask> rCleaningTask = restTemplate.exchange(createURLWithPort(url), HttpMethod.DELETE, entityCleaningTask, CleaningTask.class);
		Assert.assertTrue(rCleaningTask.getStatusCode().value() == 200);
	}
	
	
	
	
	
	private String createURLWithPort(final String uri) {
		return "http://localhost:" + port + uri;
//		final SyncBody body = new SyncBody();
//		body.setId(1L);
//		body.setUserName("Test");
//		final HttpEntity<SyncBody> entityCleaningTask = new HttpEntity<>(body, this.headers);
//		final ResponseEntity<String> rCleaningTask = this.restTemplate.exchange("/cleaningtasks", HttpMethod.GET,
//				entityCleaningTask, String.class);
//		Assert.assertThat(rCleaningTask.getStatusCode(), CoreMatchers.is(HttpStatus.BAD_REQUEST));

	}

}

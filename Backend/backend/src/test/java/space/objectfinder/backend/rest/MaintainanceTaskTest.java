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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.domain.User;

/**
 * @author Arne Salveter
 * @since 20.06.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MaintainanceTaskTest {

	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate restTemplate;

	HttpHeaders headers = new HttpHeaders();

	private User creator;

	private BeaconObject bObject;

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

	@Ignore
	@Test
	public void ApostMaintainanceTaskTest() throws JsonParseException, JsonMappingException, IOException {

		// create A CleaninigTask
		final MaintainanceTask mTask = new MaintainanceTask();
		mTask.setCreationTime(LocalDateTime.now());
		mTask.setCreator(this.creator);
		mTask.setDescription("NASE");
		mTask.setLevel(new Integer(1));
		mTask.setBeaconObject(this.bObject);
		final HttpEntity<MaintainanceTask> entityMaintainanceTask = new HttpEntity<>(mTask, this.headers);
		final ResponseEntity<MaintainanceTask> rMaintainanceTask = this.restTemplate.postForEntity("/maintainancetasks",
				entityMaintainanceTask, MaintainanceTask.class);
		Assert.assertTrue(rMaintainanceTask.getStatusCode().value() == 200);
	}

}

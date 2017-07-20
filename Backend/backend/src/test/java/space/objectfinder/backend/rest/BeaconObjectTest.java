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
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonParseException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.BeaconObjectRepository;
import space.objectfinder.backend.service.BeaconRepository;
import space.objectfinder.backend.service.LocationRepository;
import space.objectfinder.backend.service.RoleRepository;
import space.objectfinder.backend.service.UserRepository;

/**
 * @author Arne Salveter
 * @since 20.06.2017
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { "dev" })
public class BeaconObjectTest {

	@LocalServerPort
	int port;
	@Autowired
	BeaconRepository beaconRepo;
	
	@Autowired
	BeaconObjectRepository BeaconObjectRepo;
	
	@Autowired
	LocationRepository locationRepo;
	
	@Autowired
	TestRestTemplate restTemplate;

	String bUrl = "/beacons";
	String bOUrl = "/beaconObjects";
	String lUrl = "/locations";
	
	
	HttpHeaders headers = new HttpHeaders();

	@Before
	public void setUp() {
		this.headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@After
	public void after() {
		this.beaconRepo.deleteAll();
		this.BeaconObjectRepo.deleteAll();
	}
	
	
	@Test
	public void ApostBeaconObjectTest() {
		// post data
		Beacon b = new Beacon().major(new Integer(1)).minor(new Integer(2));
		HttpEntity<Beacon> bRequest = new HttpEntity<>(b, this.headers);
		ResponseEntity<Beacon> bEntity = this.restTemplate.postForEntity(bUrl, bRequest, Beacon.class);
		Assert.assertThat(bEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		Location l = new Location().building(new Integer(1)).floor(new Integer(2)).room(new Integer(3));
		HttpEntity<Location> lRequest = new HttpEntity<>(l, this.headers);
		ResponseEntity<Location> lEntity = this.restTemplate.postForEntity(lUrl, lRequest, Location.class);
		Assert.assertThat(lEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		BeaconObject bO = new BeaconObject().beacon(bEntity.getBody()).location(lEntity.getBody()).name("Testy").state(new Integer(1));
		HttpEntity<BeaconObject> bORequest = new HttpEntity<>(bO, this.headers);
		ResponseEntity<BeaconObject> entity = this.restTemplate.exchange(bOUrl,HttpMethod.POST, bORequest, BeaconObject.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));

		System.out.println("\n" + entity.getBody().toString());
		System.out.println("\n" + lEntity.getBody().toString());
		System.out.println("\n" + bEntity.getBody().toString());
		
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Assert.assertThat(entity.getBody().getBeacon(), CoreMatchers.is(bEntity.getBody()));
	}

	
	
	@Test
	@Ignore
	public void BgetsUserTest() {
		// post data
				Beacon b = new Beacon().major(new Integer(1)).minor(new Integer(2));
				HttpEntity<Beacon> bRequest = new HttpEntity<>(b, this.headers);
				ResponseEntity<Beacon> bEntity = this.restTemplate.postForEntity(bUrl, bRequest, Beacon.class);
				Assert.assertThat(bEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
				
				Location l = new Location().building(new Integer(1)).floor(new Integer(2)).room(new Integer(3));
				HttpEntity<Location> lRequest = new HttpEntity<>(l, this.headers);
				ResponseEntity<Location> lEntity = this.restTemplate.postForEntity(lUrl, lRequest, Location.class);
				Assert.assertThat(lEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
				
				BeaconObject bO = new BeaconObject().beacon(bEntity.getBody()).location(lEntity.getBody()).name("Testy").state(new Integer(1));
				HttpEntity<BeaconObject> bORequest = new HttpEntity<>(bO, this.headers);
				ResponseEntity<BeaconObject> entity = this.restTemplate.postForEntity(bUrl, bORequest, BeaconObject.class);
				Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		// get Data
		bORequest = new HttpEntity<>(null, this.headers);
		ResponseEntity<BeaconObject[]> getBeaconObjects = this.restTemplate.exchange(bOUrl,	HttpMethod.GET, bORequest, BeaconObject[].class);
		Assert.assertThat(getBeaconObjects.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		BeaconObject body = getBeaconObjects.getBody()[0];
		Assert.assertThat(body.getId(), CoreMatchers.notNullValue());
		Assert.assertThat(body.getName(), CoreMatchers.is(bO.getName()));
		Assert.assertThat(body.getBeacon(), CoreMatchers.is(bO.getBeacon()));
	}

}

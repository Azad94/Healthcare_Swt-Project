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

import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.service.LocationRepository;
import space.objectfinder.backend.service.RoleRepository;

/**
 * @author Arne Salveter
 * @since 20.06.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { "dev" })
public class LocationTest {


	@LocalServerPort
	int port;
	@Autowired
	LocationRepository repository;

	@Autowired
	TestRestTemplate restTemplate;

	String url = "/locations";

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
	public void ApostLocationTest() {
		Location loc = new Location().building(new Integer(1)).floor(new Integer(2)).room(new Integer(3));
		HttpEntity<Location> request = new HttpEntity<>(loc, this.headers);
		ResponseEntity<Location> entity = this.restTemplate.postForEntity(url, request, Location.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Location rLocation = entity.getBody();
		Assert.assertThat(rLocation.getId(), CoreMatchers.notNullValue());
		Assert.assertThat(rLocation.getBuilding(), CoreMatchers.is(loc.getBuilding()));
		Assert.assertThat(rLocation.getFloor(), CoreMatchers.is(loc.getFloor()));
		Assert.assertThat(rLocation.getRoom(), CoreMatchers.is(loc.getRoom()));
	}

	@Test
	public void BgetsLocationTest() {
		
		// post data
		Location loc = new Location().building(new Integer(1)).floor(new Integer(2)).room(new Integer(3));
		HttpEntity<Location> request = new HttpEntity<>(loc, this.headers);
		ResponseEntity<Location> entity = this.restTemplate.postForEntity(url, request, Location.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		// get Data
		ResponseEntity<Location[]> getLocations = this.restTemplate.getForEntity(url, Location[].class);
		Assert.assertThat(getLocations.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Location getLocation = getLocations.getBody()[0];
		Assert.assertThat(getLocation.getId(), CoreMatchers.notNullValue());
		Assert.assertThat(getLocation.getBuilding(), CoreMatchers.is(loc.getBuilding()));
		Assert.assertThat(getLocation.getFloor(), CoreMatchers.is(loc.getFloor()));
		Assert.assertThat(getLocation.getRoom(), CoreMatchers.is(loc.getRoom()));
	}

	
	
	
	@Test
	public void CgetLocationTest() {
		// post data
		Location loc = new Location().building(new Integer(1)).floor(new Integer(2)).room(new Integer(3));
		Location loc2 = new Location().building(new Integer(2)).floor(new Integer(3)).room(new Integer(4));
		
		HttpEntity<Location> request = new HttpEntity<>(loc, this.headers);
		ResponseEntity<Location> entity = this.restTemplate.postForEntity(url, request, Location.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		request = new HttpEntity<>(loc2, this.headers);
		entity = this.restTemplate.postForEntity(url, request, Location.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		
		// get Data
		ResponseEntity<Location> getLocations = this.restTemplate.getForEntity(url+"/"+entity
				.getBody().getId(), Location.class);
		Assert.assertThat(getLocations.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Location getLocation = getLocations.getBody();
		Assert.assertThat(getLocation.getId(), CoreMatchers.notNullValue());
		Assert.assertThat(getLocation.getBuilding(), CoreMatchers.is(loc2.getBuilding()));
		Assert.assertThat(getLocation.getFloor(), CoreMatchers.is(loc2.getFloor()));
		Assert.assertThat(getLocation.getRoom(), CoreMatchers.is(loc2.getRoom()));
	}

	@Test
	public void DputLocationTest() {
		// post data
		Location loc = new Location().building(new Integer(1)).floor(new Integer(2)).room(new Integer(3));
		HttpEntity<Location> request = new HttpEntity<>(loc, this.headers);
		ResponseEntity<Location> entity = this.restTemplate.postForEntity(url, request, Location.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));

		// put the location
		Location response = entity.getBody().building(99);		
		request = new HttpEntity<>(response, this.headers);
		entity = this.restTemplate.exchange(url + "/" + request.getBody().getId(),	HttpMethod.PUT, request, Location.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
	}

	
	@Test
	public void EdeleteLocationTest() {
		// post data
		Location loc = new Location().building(new Integer(1)).floor(new Integer(2)).room(new Integer(3));
		HttpEntity<Location> request = new HttpEntity<>(loc, this.headers);
		ResponseEntity<Location> entity = this.restTemplate.postForEntity(url, request, Location.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		request = new HttpEntity<>(null, this.headers);
		entity = this.restTemplate.exchange(url + "/" + entity.getBody().getId(),	HttpMethod.DELETE, request, Location.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
	}
}
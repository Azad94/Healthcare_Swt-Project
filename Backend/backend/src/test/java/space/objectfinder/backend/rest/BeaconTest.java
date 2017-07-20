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

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.service.BeaconRepository;
import space.objectfinder.backend.service.LocationRepository;

/**
 * @author Arne Salveter
 * @since 20.06.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { "dev" })
public class BeaconTest {

	@LocalServerPort
	int port;
	@Autowired
	BeaconRepository repository;

	@Autowired
	TestRestTemplate restTemplate;

	String url = "/beacons";

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
	public void ApostBeaconTest() {
		Beacon beacon = new Beacon().major(new Integer(1)).minor(new Integer(2));
		HttpEntity<Beacon> request = new HttpEntity<>(beacon, this.headers);
		ResponseEntity<Beacon> entity = this.restTemplate.postForEntity(url, request, Beacon.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		Beacon rBeacon = entity.getBody();
		Assert.assertThat(rBeacon.getUuid(), CoreMatchers.notNullValue());
		Assert.assertThat(rBeacon.getMajor(), CoreMatchers.is(beacon.getMajor()));
		Assert.assertThat(rBeacon.getMinor(), CoreMatchers.is(beacon.getMinor()));
	}

	@Test
	public void BgetsBeaconTest() {
		// post data
		Beacon b = new Beacon().major(new Integer(1)).minor(new Integer(2));
		HttpEntity<Beacon> request = new HttpEntity<>(b, this.headers);
		ResponseEntity<Beacon> entity = this.restTemplate.postForEntity(url, request, Beacon.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		// get Data
		ResponseEntity<Beacon[]> getBeacons = this.restTemplate.getForEntity(url, Beacon[].class);
		Assert.assertThat(getBeacons.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Beacon getB = getBeacons.getBody()[0];
		Assert.assertThat(getB.getUuid(), CoreMatchers.notNullValue());
		Assert.assertThat(getB.getMajor(), CoreMatchers.is(b.getMajor()));
		Assert.assertThat(getB.getMinor(), CoreMatchers.is(b.getMinor()));
	}

	
	@Test
	public void CgetBeaconTest() {
		// post data
		Beacon b = new Beacon().major(new Integer(1)).minor(new Integer(2));
		HttpEntity<Beacon> request = new HttpEntity<>(b, this.headers);
		ResponseEntity<Beacon> entity = this.restTemplate.postForEntity(url, request, Beacon.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));

		// get Data
		ResponseEntity<Beacon> getBeacons = this.restTemplate.getForEntity(url +"/" + entity.getBody().getUuid(), Beacon.class);
		Assert.assertThat(getBeacons.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Beacon getB = getBeacons.getBody();
		Assert.assertThat(getB.getUuid(), CoreMatchers.notNullValue());
		Assert.assertThat(getB.getMajor(), CoreMatchers.is(b.getMajor()));
		Assert.assertThat(getB.getMinor(), CoreMatchers.is(b.getMinor()));
	}

	@Test
	public void DputBeaconTest() {
		// post data
		Beacon b = new Beacon().major(new Integer(1)).minor(new Integer(2));
		HttpEntity<Beacon> request = new HttpEntity<>(b, this.headers);
		ResponseEntity<Beacon> entity = this.restTemplate.postForEntity(url, request, Beacon.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		// put the location
		Beacon response = entity.getBody().major(new Integer (11));		
		request = new HttpEntity<>(response, this.headers);
		entity = this.restTemplate.exchange(url + "/" + request.getBody().getUuid(),	HttpMethod.PUT, request, Beacon.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
	}

	
	@Test
	public void EdeleteLocationTest() {
		// post data
		Beacon b = new Beacon().major(new Integer(1)).minor(new Integer(2));
		HttpEntity<Beacon> request = new HttpEntity<>(b, this.headers);
		ResponseEntity<Beacon> entity = this.restTemplate.postForEntity(url, request, Beacon.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		
		request = new HttpEntity<>(null, this.headers);
		entity = this.restTemplate.exchange(url + "/" + entity.getBody().getUuid(),	HttpMethod.DELETE, request, Beacon.class);
		Assert.assertThat(entity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
	}
}
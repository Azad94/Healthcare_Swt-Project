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
import java.util.List;

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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.domain.SubTaskCheckbox;
import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.BeaconObjectRepository;
import space.objectfinder.backend.service.BeaconRepository;
import space.objectfinder.backend.service.LocationRepository;
import space.objectfinder.backend.service.MaintainanceTaskRepository;
import space.objectfinder.backend.service.RoleRepository;
import space.objectfinder.backend.service.UserRepository;

/**
 * @author Sven Marquardt
 * @since 19.06.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { "dev" })
public class SubTaskTestCheckbox {
	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate template;
	@Autowired
	UserRepository uRepository;
	@Autowired
	MaintainanceTaskRepository mRepository;
	@Autowired
	RoleRepository rRepository;
	@Autowired
	LocationRepository lRepository;
	@Autowired
	BeaconRepository bRepository;
	@Autowired
	BeaconObjectRepository boRepository;

	HttpHeaders headers = new HttpHeaders();
	ObjectMapper mapper = new ObjectMapper();
	private static String MAINTAINANCEURL = "/maintainancetasks/";
	private static String SUBTASKSURL = "/subtaskscheckbox";
	long id;

	@Before
	public void setUp() {
		this.headers.setContentType(MediaType.APPLICATION_JSON);
		Beacon beacon = new Beacon().major(1000).major(1000).uuid("1234");
		beacon = this.bRepository.save(beacon);
		Location location = new Location().building(1).room(1).floor(1);
		location = this.lRepository.save(location);
		Role role = new Role().name("Test");
		role = this.rRepository.save(role);
		User user = new User().email("Test@Test").lastUpdate(LocalDateTime.now()).name("sven").role(role)
				.password("starkes passwort");
		user = this.uRepository.save(user);
		BeaconObject beaconObject = new BeaconObject().beacon(beacon).location(location).name("Test").state(1)
				.beaconObjectType("Bett");
		beaconObject = this.boRepository.save(beaconObject);
		MaintainanceTask maintainanceTask = (MaintainanceTask) new MaintainanceTask().repeatTaskInDays(1)
				.beaconObect(beaconObject).creator(user).creationTime(LocalDateTime.now()).discription("Test").level(1)
				.name("Task").role(role).state(1);
		maintainanceTask = this.mRepository.save(maintainanceTask);
		this.id = maintainanceTask.getId();
	}

	@After
	public void after() {
		this.mRepository.deleteAll();
		this.boRepository.deleteAll();
		this.lRepository.deleteAll();
		this.bRepository.deleteAll();
		this.uRepository.deleteAll();
		this.rRepository.deleteAll();
	}

	@Test
	public void testSubtaskImagePost() throws IOException {
		final SubTaskCheckbox subTaskImage = new SubTaskCheckbox();
		subTaskImage.setValue(false);
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskCheckbox> entity = new HttpEntity<>(subTaskImage, this.headers);
		final ResponseEntity<SubTaskCheckbox> responseEntity = this.template.postForEntity(
				SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL, entity,
				SubTaskCheckbox.class);
		final SubTaskCheckbox reSubTaskCheckbox = responseEntity.getBody();
		Assert.assertThat(reSubTaskCheckbox, CoreMatchers.notNullValue());
		Assert.assertThat(reSubTaskCheckbox.getTitle(), CoreMatchers.is(subTaskImage.getTitle()));
		Assert.assertThat(reSubTaskCheckbox.isValue(), CoreMatchers.is(reSubTaskCheckbox.isValue()));
		Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
	}

	@Test
	public void testSubTaskCheckboxGet() {
		final SubTaskCheckbox subTaskImage = new SubTaskCheckbox();
		subTaskImage.setValue(false);
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskCheckbox> entity = new HttpEntity<>(subTaskImage, this.headers);
		final ResponseEntity<SubTaskCheckbox> responseEntity = this.template.postForEntity(
				SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL, entity,
				SubTaskCheckbox.class);
		final ResponseEntity<SubTaskCheckbox> responseEntity2 = this.template
				.getForEntity(SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL + "/"
						+ responseEntity.getBody().getId(), SubTaskCheckbox.class);
		final SubTaskCheckbox subTaskImage2 = responseEntity2.getBody();
		Assert.assertThat(responseEntity2.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Assert.assertThat(subTaskImage2.getId(), CoreMatchers.is(responseEntity.getBody().getId()));
		Assert.assertThat(subTaskImage2.getTitle(), CoreMatchers.is(subTaskImage.getTitle()));
		Assert.assertThat(subTaskImage2.isValue(), CoreMatchers.is(subTaskImage.isValue()));
	}

	@Test
	public void testSubtaskImagePut() {
		final SubTaskCheckbox subTaskImage = new SubTaskCheckbox();
		subTaskImage.setValue(false);
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskCheckbox> entity = new HttpEntity<>(subTaskImage, this.headers);
		final ResponseEntity<SubTaskCheckbox> responseEntity = this.template.postForEntity(
				SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL, entity,
				SubTaskCheckbox.class);
		final SubTaskCheckbox taskImage = responseEntity.getBody();
		taskImage.setValue(true);
		taskImage.setTitle("Neu");
		final HttpEntity<SubTaskCheckbox> entity2 = new HttpEntity<>(taskImage, this.headers);
		this.template.exchange(SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL + "/"
				+ taskImage.getId(), HttpMethod.PUT, entity2, SubTaskCheckbox.class);
		final ResponseEntity<SubTaskCheckbox> responseEntity2 = this.template
				.getForEntity(SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL + "/"
						+ taskImage.getId(), SubTaskCheckbox.class);
		Assert.assertThat(responseEntity2.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		final SubTaskCheckbox image = responseEntity2.getBody();
		Assert.assertThat(image.getTitle(), CoreMatchers.is(taskImage.getTitle()));
		Assert.assertThat(image.isValue(), CoreMatchers.is(taskImage.isValue()));
		Assert.assertThat(image.getTitle(), CoreMatchers.not(subTaskImage.getTitle()));
		Assert.assertThat(image.isValue(), CoreMatchers.not(subTaskImage.isValue()));
		Assert.assertThat(image, CoreMatchers.is(taskImage));
	}

	@Test
	public void testSubTaskCheckboxDelete() {
		final SubTaskCheckbox subTaskImage = new SubTaskCheckbox();
		subTaskImage.setValue(true);
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskCheckbox> entity = new HttpEntity<>(subTaskImage, this.headers);
		final ResponseEntity<SubTaskCheckbox> responseEntity = this.template.postForEntity(
				SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL, entity,
				SubTaskCheckbox.class);
		final SubTaskCheckbox taskImage = responseEntity.getBody();
		final ResponseEntity<Void> reEntity = this.template.exchange(SubTaskTestCheckbox.MAINTAINANCEURL + this.id
				+ SubTaskTestCheckbox.SUBTASKSURL + "/" + taskImage.getId(), HttpMethod.DELETE, entity, Void.class);
		Assert.assertThat(reEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		final ResponseEntity<SubTaskCheckbox> responseEntity2 = this.template
				.getForEntity(SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL + "/"
						+ taskImage.getId(), SubTaskCheckbox.class);
		Assert.assertThat(responseEntity2.getStatusCode(), CoreMatchers.is(HttpStatus.NOT_FOUND));
		Assert.assertThat(responseEntity2.getBody(), CoreMatchers.nullValue());
	}

	@Test
	public void testSubTaskCheckboxGetAll() {
		final SubTaskCheckbox subTaskImage = new SubTaskCheckbox();
		subTaskImage.setValue(true);
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskCheckbox> entity = new HttpEntity<>(subTaskImage, this.headers);
		this.template.postForEntity(SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL,
				entity, SubTaskCheckbox.class);
		final ResponseEntity<List> entity2 = this.template.getForEntity(
				SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL, List.class);
		Assert.assertThat(entity2.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Assert.assertThat(entity2.getBody(), CoreMatchers.notNullValue());
		Assert.assertThat(entity2.getBody().size(), CoreMatchers.is(1));
		Assert.assertFalse(entity2.getBody().isEmpty());
	}

	@Test
	public void testSubTaskCheckboxNotFoundPut() {
		final SubTaskCheckbox image = new SubTaskCheckbox();
		final HttpEntity<SubTaskCheckbox> entity = new HttpEntity<>(image, this.headers);
		final ResponseEntity<Void> responseEntity = this.template.exchange(
				SubTaskTestCheckbox.MAINTAINANCEURL + this.id + SubTaskTestCheckbox.SUBTASKSURL + "/" + 100,
				HttpMethod.PUT, entity, Void.class);
		Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testSubTaskCheckboxNotFoundPost() {
		final SubTaskCheckbox image = new SubTaskCheckbox();
		final HttpEntity<SubTaskCheckbox> entity = new HttpEntity<>(image, this.headers);
		final ResponseEntity<SubTaskCheckbox> responseEntity = this.template.postForEntity(
				SubTaskTestCheckbox.MAINTAINANCEURL + 1000 + SubTaskTestCheckbox.SUBTASKSURL, entity,
				SubTaskCheckbox.class);
		Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testSubTaskCheckboxNotFoundDelete() {
		final HttpEntity<Void> entity = new HttpEntity<>(this.headers);
		final ResponseEntity<Void> responseEntity = this.template.exchange(
				SubTaskTestCheckbox.MAINTAINANCEURL + 100 + SubTaskTestCheckbox.SUBTASKSURL + "/" + 1,
				HttpMethod.DELETE, entity, Void.class);
		Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.NOT_FOUND));
	}
}

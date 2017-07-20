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
import space.objectfinder.backend.domain.SubTaskImage;
import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.BeaconObjectRepository;
import space.objectfinder.backend.service.BeaconRepository;
import space.objectfinder.backend.service.LocationRepository;
import space.objectfinder.backend.service.MaintainanceTaskRepository;
import space.objectfinder.backend.service.RoleRepository;
import space.objectfinder.backend.service.UserRepository;

/**
 * @author "Sven Marquardt"
 * @since 29.06.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { "dev" })
public final class SubTaskImageTest {

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
	private static String SUBTASKSURL = "/subtasksimage";
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
		final SubTaskImage subTaskImage = new SubTaskImage();
		subTaskImage.setPicture("Abra".getBytes());
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskImage> entity = new HttpEntity<>(subTaskImage, this.headers);
		final ResponseEntity<SubTaskImage> responseEntity = this.template.postForEntity(
				SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL, entity, SubTaskImage.class);
		final SubTaskImage reSubTaskImage = responseEntity.getBody();
		Assert.assertThat(reSubTaskImage, CoreMatchers.notNullValue());
		Assert.assertThat(reSubTaskImage.getTitle(), CoreMatchers.is(subTaskImage.getTitle()));
		Assert.assertThat(reSubTaskImage.getPicture(), CoreMatchers.is(reSubTaskImage.getPicture()));
		Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
	}

	@Test
	public void testSubTaskImageGet() {
		final SubTaskImage subTaskImage = new SubTaskImage();
		subTaskImage.setPicture("Abra".getBytes());
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskImage> entity = new HttpEntity<>(subTaskImage, this.headers);
		final ResponseEntity<SubTaskImage> responseEntity = this.template.postForEntity(
				SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL, entity, SubTaskImage.class);
		final ResponseEntity<SubTaskImage> responseEntity2 = this.template.getForEntity(SubTaskImageTest.MAINTAINANCEURL
				+ this.id + SubTaskImageTest.SUBTASKSURL + "/" + responseEntity.getBody().getId(), SubTaskImage.class);
		final SubTaskImage subTaskImage2 = responseEntity2.getBody();
		Assert.assertThat(responseEntity2.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Assert.assertThat(subTaskImage2.getId(), CoreMatchers.is(responseEntity.getBody().getId()));
		Assert.assertThat(subTaskImage2.getTitle(), CoreMatchers.is(subTaskImage.getTitle()));
		Assert.assertThat(subTaskImage2.getPicture(), CoreMatchers.is(subTaskImage.getPicture()));
	}

	@Test
	public void testSubtaskImagePut() {
		final SubTaskImage subTaskImage = new SubTaskImage();
		subTaskImage.setPicture("Abra".getBytes());
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskImage> entity = new HttpEntity<>(subTaskImage, this.headers);
		final ResponseEntity<SubTaskImage> responseEntity = this.template.postForEntity(
				SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL, entity, SubTaskImage.class);
		final SubTaskImage taskImage = responseEntity.getBody();
		taskImage.setPicture("ahh".getBytes());
		taskImage.setTitle("Neu");
		final HttpEntity<SubTaskImage> entity2 = new HttpEntity<>(taskImage, this.headers);
		this.template.exchange(
				SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL + "/" + taskImage.getId(),
				HttpMethod.PUT, entity2, SubTaskImage.class);
		final ResponseEntity<SubTaskImage> responseEntity2 = this.template.getForEntity(
				SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL + "/" + taskImage.getId(),
				SubTaskImage.class);
		Assert.assertThat(responseEntity2.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		final SubTaskImage image = responseEntity2.getBody();
		Assert.assertThat(image.getTitle(), CoreMatchers.is(taskImage.getTitle()));
		Assert.assertThat(image.getPicture(), CoreMatchers.is(taskImage.getPicture()));
		Assert.assertThat(image.getTitle(), CoreMatchers.not(subTaskImage.getTitle()));
		Assert.assertThat(image.getPicture(), CoreMatchers.not(subTaskImage.getPicture()));
		Assert.assertThat(image, CoreMatchers.is(taskImage));
	}

	@Test
	public void testSubTaskImageDelete() {
		final SubTaskImage subTaskImage = new SubTaskImage();
		subTaskImage.setPicture("Abra".getBytes());
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskImage> entity = new HttpEntity<>(subTaskImage, this.headers);
		final ResponseEntity<SubTaskImage> responseEntity = this.template.postForEntity(
				SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL, entity, SubTaskImage.class);
		final SubTaskImage taskImage = responseEntity.getBody();
		final ResponseEntity<Void> reEntity = this.template.exchange(
				SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL + "/" + taskImage.getId(),
				HttpMethod.DELETE, entity, Void.class);
		Assert.assertThat(reEntity.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		final ResponseEntity<SubTaskImage> responseEntity2 = this.template.getForEntity(
				SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL + "/" + taskImage.getId(),
				SubTaskImage.class);
		Assert.assertThat(responseEntity2.getStatusCode(), CoreMatchers.is(HttpStatus.NOT_FOUND));
		Assert.assertThat(responseEntity2.getBody(), CoreMatchers.nullValue());
	}

	@Test
	public void testSubTaskImageGetAll() {
		final SubTaskImage subTaskImage = new SubTaskImage();
		subTaskImage.setPicture("Abra".getBytes());
		subTaskImage.setTitle("Test");
		final HttpEntity<SubTaskImage> entity = new HttpEntity<>(subTaskImage, this.headers);
		this.template.postForEntity(SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL, entity,
				SubTaskImage.class);
		final ResponseEntity<List> entity2 = this.template
				.getForEntity(SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL, List.class);
		Assert.assertThat(entity2.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
		Assert.assertThat(entity2.getBody(), CoreMatchers.notNullValue());
		Assert.assertThat(entity2.getBody().size(), CoreMatchers.is(1));
		Assert.assertFalse(entity2.getBody().isEmpty());
	}

	@Test
	public void testSubTaskImageNotFoundPut() {
		final SubTaskImage image = new SubTaskImage();
		final HttpEntity<SubTaskImage> entity = new HttpEntity<>(image, this.headers);
		final ResponseEntity<Void> responseEntity = this.template.exchange(
				SubTaskImageTest.MAINTAINANCEURL + this.id + SubTaskImageTest.SUBTASKSURL + "/" + 100, HttpMethod.PUT,
				entity, Void.class);
		Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testSubTaskImageNotFoundPost() {
		final SubTaskImage image = new SubTaskImage();
		final HttpEntity<SubTaskImage> entity = new HttpEntity<>(image, this.headers);
		final ResponseEntity<SubTaskImage> responseEntity = this.template.postForEntity(
				SubTaskImageTest.MAINTAINANCEURL + 1000 + SubTaskImageTest.SUBTASKSURL, entity, SubTaskImage.class);
		Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testSubTaskImageNotFoundDelete() {
		final HttpEntity<Void> entity = new HttpEntity<>(this.headers);
		final ResponseEntity<Void> responseEntity = this.template.exchange(
				SubTaskImageTest.MAINTAINANCEURL + 100 + SubTaskImageTest.SUBTASKSURL + "/" + 1, HttpMethod.DELETE,
				entity, Void.class);
		Assert.assertThat(responseEntity.getStatusCode(), CoreMatchers.is(HttpStatus.NOT_FOUND));
	}

}

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

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Arne Salveter
 * @since 20.06.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceTest {

	@LocalServerPort
	private int port;

	String url = "";

	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	@Before
	public void setUp() throws Exception {
		headers.setContentType(MediaType.APPLICATION_JSON);
		url = "/v1/service";
	}

	// @Test
	// public void ApostRoleTest() {
	//
	//
	//
	//
	//
	// String body = "{" +
	// "\"callObjects\" : \"Bett\" " +
	// "\"qauntity\" : 1 " +
	// "\"targetLocation\" + {" +
	// "\"building\" : 1 ," +
	// "\"floor\" : 1 ," +
	// "\"room\" : 1" +
	// "} ," +
	// "\"creator\" : {" +
	// "\"name\" : \"Hans\", " +
	// "\"password\" : \"123\", " +
	// "\"email\" : \"lol@lol.de\" " +
	// "\"lastUpdate\" :
	// "}" ;
	//
	//
	//
	// HttpEntity<String> entity = new HttpEntity<String>(body, headers);
	// ResponseEntity<String> response =
	// restTemplate.exchange(createURLWithPort(url), HttpMethod.POST, entity,
	// String.class);
	// testIt(response);
	// }

	@Test
	@Ignore
	public void BgetsRoleTest() {
		final HttpEntity<String> entity = new HttpEntity<>(null, headers);
		final ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(url), HttpMethod.GET, entity,
				String.class);
		testIt(response);
	}

	@Test
	@Ignore
	public void CgetRoleTest() {
		url = url + "/1";
		final HttpEntity<String> entity = new HttpEntity<>(null, headers);
		final ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(url), HttpMethod.GET, entity,
				String.class);
		testIt(response);
	}

	@Test
	@Ignore
	public void DputLocationTest() {
		url += "/1";
		final String body = "{  \"name\": \"Admin\" }";
		final HttpEntity<String> entity = new HttpEntity<>(body, headers);
		final ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(url), HttpMethod.PUT, entity,
				String.class);
		testIt(response);
	}

	@Test
	@Ignore
	public void EdeleteLocationTest() {
		url += "/1";
		final HttpEntity<String> entity = new HttpEntity<>(null, headers);
		final ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(url), HttpMethod.DELETE, entity,
				String.class);
		testIt(response);
	}

	private void testIt(final ResponseEntity<String> response) {
		System.out.println(response.getBody());
		System.out.println(response.getStatusCode().value());
		Assert.assertTrue(response.getStatusCode().value() == 200);
	}

	private String createURLWithPort(final String uri) {
		return "http://localhost:" + port + uri;
	}

}

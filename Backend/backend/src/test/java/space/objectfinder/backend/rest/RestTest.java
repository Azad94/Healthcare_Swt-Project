/**
 * /*******************************************************************************
 * Copyright 2017 Sven Marquardt
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
 ******************************************************************************
 */
package space.objectfinder.backend.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @author Sven Marquardt
 * @since 25.06.2017
 */
public class RestTest {

	private final HttpHeaders header = new HttpHeaders();

	public void setUp() {
		this.header.setContentType(MediaType.APPLICATION_JSON);
	}

	/**
	 * @return the header
	 */
	public HttpHeaders getHeader() {
		return this.header;
	}

}

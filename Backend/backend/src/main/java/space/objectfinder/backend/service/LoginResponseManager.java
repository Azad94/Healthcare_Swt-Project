/*******************************************************************************
 * Copyright 2017  Chris Deter, Arne Salveter, Sven Marquardt
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
package space.objectfinder.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.api.ResponseManagerForLogin;

/**
 * Führt einen durch und verfiziert diesen
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 * @see ResponseManagerForLogin
 */
@Component
public class LoginResponseManager implements ResponseManagerForLogin {
	@Autowired
	UserRepository repository;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResponseManager.class);

	@Override
	public ResponseEntity<User> post(final String userName, final String password) {
		LoginResponseManager.LOGGER.info("post AUF:{} mit userName:{} password:{}", this.getClass(), userName,
				password);
		final List<User> buffer = this.repository.findByName(userName);
		final ResponseEntity<User> response;
		if (!buffer.isEmpty()) {
			final User user = buffer.get(0);
			if (user.getName().equals(userName) && user.getPassword().equals(password)) {
				response = new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;

	}

}

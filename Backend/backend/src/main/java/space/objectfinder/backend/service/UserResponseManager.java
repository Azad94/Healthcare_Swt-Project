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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.api.ResponseManagerForDaos;

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@Component
public class UserResponseManager implements ResponseManagerForDaos<User, Long> {
	@Autowired
	UserRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserResponseManager.class);

	@Override
	public ResponseEntity<List<User>> get() {
		final List<User> buffer = new LinkedList<>();
		this.repository.findAll().forEach(buffer::add);
		return ResponseEntity.ok(buffer);
	}

	@Override
	public ResponseEntity<User> getId(final Long id) {
		UserResponseManager.LOGGER.info("GET ID auf: User mit id: {}", id);
		return this.optionalResponse(this.repository.findById(id));
	}

	@Override
	public ResponseEntity<User> post(final User object) {
		UserResponseManager.LOGGER.info("POST AUF: User mit Object: {}", object);
		return ResponseEntity.ok(this.repository.save(object));

	}

	@Override
	public ResponseEntity<Void> put(final Long id, final User object) {
		UserResponseManager.LOGGER.info("PUT AUF:User mit id:{} mit id:{}", id, object);
		final Optional<User> buffer = this.repository.findById(id);
		if (!buffer.isPresent()) {
			UserResponseManager.LOGGER.info("User nicht gefunden: {}", id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		final User u = buffer.get();
		u.setEmail(object.getEmail());
		u.setLastUpdate(object.getLastUpdate());
		u.setName(object.getName());
		u.setPassword(object.getPassword());
		u.setRole(object.getRole());
		this.repository.save(u);
		UserResponseManager.LOGGER.info("User gespeichert mit: {}", u);
		return ResponseEntity.ok().build();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.service.api.ResponseManagerForDaos#delete()
	 */
	@Override
	public ResponseEntity<Void> delete(final Long id) {
		UserResponseManager.LOGGER.info("Delete User mit id: {}", id);
		return this.deleteEntity(id, this.repository);
	}

}

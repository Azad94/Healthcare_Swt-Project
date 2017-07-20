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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.service.api.ResponseManagerForDaos;

/**
 * Führt nötige aktionen mit der datenbank aus um Objekte zu speichern und zu
 * lesen. Nur für {@link Role} objekte
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 * @see ResponseManagerForDaos
 */
@Component
public class RoleResponseManager implements ResponseManagerForDaos<Role, Long> {
	@Autowired
	RoleRepository repository;

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleResponseManager.class);

	@Override
	public ResponseEntity<List<Role>> get() {
		RoleResponseManager.LOGGER.info("GET AUF:User");
		final List<Role> buffer = new ArrayList<>();
		this.repository.findAll().forEach(buffer::add);
		RoleResponseManager.LOGGER.info("{} gefunden", buffer.size());
		return ResponseEntity.ok(buffer);
	}

	@Override
	public ResponseEntity<Role> getId(final Long id) {
		RoleResponseManager.LOGGER.info("GET ID AUF:{} mit id:{}", this.getClass(), id);
		return this.optionalResponse(this.repository.findById(id));
	}

	@Override
	public ResponseEntity<Role> post(final Role object) {
		RoleResponseManager.LOGGER.info("POST AUF:·{} mit:{}", this.getClass(), object);
		return new ResponseEntity<>(this.repository.save(object), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> put(final Long id, final Role object) {
		RoleResponseManager.LOGGER.info("PUT AUF:User mit id:{} body:{}", id, object);
		final Optional<Role> buffer = this.repository.findById(id);
		if (buffer.isPresent()) {
			RoleResponseManager.LOGGER.info("ROLE:{} gefunden", id);
			final Role r = buffer.get();
			r.setName(object.getName());
			this.repository.save(r);
			RoleResponseManager.LOGGER.info("{} in Datenbank gespeichert", r);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		RoleResponseManager.LOGGER.info("Nicht gefunden");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.service.api.ResponseManagerForDaos#delete()
	 */
	@Override
	public ResponseEntity<Void> delete(final Long id) {
		RoleResponseManager.LOGGER.info("DELETE AUF:{}", id);
		return this.deleteEntity(id, this.repository);
	}

}

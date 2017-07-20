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
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.service.api.ResponseManagerForDaos;

/**
 * Führt nötige aktionen mit der datenbank aus um Objekte zu speichern und zu
 * lesen. Nur für {@link Beacon} objekte
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 * @see ResponseManagerForDaos
 */
@Component
public class BeaconResponseManager implements ResponseManagerForDaos<Beacon, String> {
	@Autowired
	BeaconRepository repository;

	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BeaconResponseManager.class);

	@Override
	public ResponseEntity<List<Beacon>> get() {
		BeaconResponseManager.LOGGER.info("GET AUF:{}", this.getClass());
		final List<Beacon> buffer = new LinkedList<>();
		BeaconResponseManager.LOGGER.info("found {}", this.repository.findAll());
		this.repository.findAll().forEach(buffer::add);
		return ResponseEntity.ok(buffer);
	}

	@Override
	public ResponseEntity<Beacon> getId(final String id) {
		BeaconResponseManager.LOGGER.info("Beacon mit der Id:{} wird gesucht", id);

		return this.optionalResponse(this.repository.findByUuid(id));

	}

	@Override
	public ResponseEntity<Beacon> post(final Beacon object) {
		BeaconResponseManager.LOGGER.info("Versuche {} zu speichern. Für Beacon: {}. Object ist: {}",
				object.getClass(), object);
		final StringBuilder builder = new StringBuilder(32);
		final long id = this.repository.count() + 1;
		builder.append(String.format("%08d", id)).append('-').append("healthcarew123456789101");
		object.setUuid(builder.toString());
		this.repository.save(object);
		return ResponseEntity.ok(object);

	}

	@Override
	public ResponseEntity<Void> put(final String id, final Beacon body) {
		BeaconResponseManager.LOGGER.info("Versuche {} zu speichern. Für Beachon: {}. Object ist: {}", body.getClass(),
				body);

		final Optional<Beacon> bufferO = this.repository.findByUuid(id);
		if (bufferO.isPresent()) {
			final Beacon buffer = bufferO.get();
			if (body.getMajor() != null && !body.getMajor().equals(buffer.getMajor())) {
				buffer.setMajor(body.getMajor());
			}
			if (body.getMinor() != null && !body.getMinor().equals(buffer.getMinor())) {
				buffer.setMinor(body.getMinor());
			}
			if (body.getUuid() != null && !body.getUuid().equals(buffer.getUuid())) {
				buffer.setUuid(body.getUuid());
			}
			this.repository.save(buffer);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.service.api.ResponseManagerForDaos#delete()
	 */
	@Override
	public ResponseEntity<Void> delete(final String id) {
		BeaconResponseManager.LOGGER.info("Beacon mit der Id:{} wird gelöscht", id);
		return this.deleteEntity(id, this.repository);
	}

	/**
	 * Erhalte alle {@link Beacon} die zu keinem {@link BeaconObject} gehören
	 *
	 * @return
	 * @author Sven Marquardt, Chris Deter
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @since 21.06.2017
	 */
	public ResponseEntity<List<Beacon>> getFreeBeacons() throws InterruptedException, ExecutionException {
		BeaconResponseManager.LOGGER.info("Freie Beacons werden abgerufen");
		final List<Beacon> beaconReturn = this.repository.findFree();
		return new ResponseEntity<>(beaconReturn, HttpStatus.OK);

	}

}

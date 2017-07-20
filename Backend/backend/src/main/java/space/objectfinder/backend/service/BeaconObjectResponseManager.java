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
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.Task;
import space.objectfinder.backend.domain.TransportTask;
import space.objectfinder.backend.service.api.ResponseManagerForDaos;

/**
 * Führt nötige aktionen mit der datenbank aus um Objekte zu speichern und zu
 * lesen. Nur für {@link BeaconObject} objekte
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 * @see ResponseManagerForDaos
 */
@Component
public class BeaconObjectResponseManager implements ResponseManagerForDaos<BeaconObject, Long> {
	@Autowired
	BeaconObjectRepository repository;
	@Autowired
	MaintainanceTaskRepository mRepository;
	@Autowired
	TransportTaskRepository tRepository;
	@Autowired
	CleaningTaskRepository cRepository;
	@Autowired
	PictureOfObjectRepository pRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(BeaconObjectResponseManager.class);

	@Override
	public ResponseEntity<List<BeaconObject>> get() {
		final List<BeaconObject> buffer = new LinkedList<>();
		this.repository.findAll().forEach(buffer::add);
		return this.response(buffer);
	}

	@Override
	public ResponseEntity<BeaconObject> getId(final Long id) {
		final Optional<BeaconObject> buffer = this.repository.findById(id);
		BeaconObjectResponseManager.LOGGER.info("GET ID AUF:{} mit id:{}", this.getClass(), id);
		return this.optionalResponse(buffer);
	}

	@Override
	public ResponseEntity<BeaconObject> post(final BeaconObject object) {
		BeaconObjectResponseManager.LOGGER.info("POST AUF TASK: {} mit body:{}", this.getClass(), object);
		BeaconObjectResponseManager.LOGGER.info("Bild zuerst speichern");
		if (object.getPictureOfObject() != null) {
			object.setPictureOfObject(this.pRepository.save(object.getPictureOfObject()));
		}
		return new ResponseEntity<>(this.repository.save(object), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> put(final Long id, final BeaconObject body) {
		final Optional<BeaconObject> data = this.repository.findById(id);

		if (data.isPresent()) {
			final BeaconObject buffer = data.get();

			if (body.getLocation() != null && !body.getLocation().equals(buffer.getLocation())) {
				buffer.setLocation(body.getLocation());
			}
			if (body.getName() != null && !body.getName().equals(buffer.getName())) {
				buffer.setName(body.getName());
			}
			if (body.getPictureOfObject() != null && !body.getPictureOfObject().equals(buffer.getPictureOfObject())) {
				buffer.setPictureOfObject(body.getPictureOfObject());
			}
			if (buffer.getPictureOfObject() != null && buffer.getPictureOfObject().getId() == null) {
				buffer.setPictureOfObject(this.pRepository.save(buffer.getPictureOfObject()));
			}
			this.repository.save(buffer);

			BeaconObjectResponseManager.LOGGER.info("PUT AUF:{} mit id:{} und body:{}", this.getClass(), id, body);

			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.service.api.ResponseManagerForDaos#delete(java
	 * .lang.Object)
	 */
	@Override
	public ResponseEntity<Void> delete(final Long id) {
		BeaconObjectResponseManager.LOGGER.info("DELTE AUF:{} mit id:{}", this.getClass(), id);
		return this.deleteEntity(id, this.repository);
	}

	/**
	 * Finde alle tasks die zu einem {@link BeaconObject} gehören
	 *
	 * @param id
	 * @param taskType
	 * @return
	 * @author Sven Marquardt
	 * @since 21.06.2017
	 */
	public <T> ResponseEntity<List<T>> getTaskToThisBeacon(final Long id, final Class<T> taskType) {
		BeaconObjectResponseManager.LOGGER.info("GetTaskToThisBeacon {} mit id: {}", this.getClass(), id);
		final Optional<BeaconObject> beOptional = this.repository.findById(id);
		final List<T> taskList = new LinkedList<>();
		final Consumer<Task> taskListConsumer = t -> {
			if (t.getClass().isAssignableFrom(taskType)) {
				final T tBuffer = taskType.cast(t);
				taskList.add(tBuffer);
			}
		};
		if (beOptional.isPresent()) {
			if (taskType.isAssignableFrom(TransportTask.class)) {
				this.tRepository.findAllByBeaconObject(beOptional.get()).forEach(taskListConsumer);
			} else if (taskType.isAssignableFrom(CleaningTask.class)) {
				this.cRepository.findAllByBeaconObject(beOptional.get()).forEach(taskListConsumer);
			} else if (taskType.isAssignableFrom(MaintainanceTask.class)) {
				this.mRepository.findAllByBeaconObject(beOptional.get()).forEach(taskListConsumer);
			}
			return new ResponseEntity<>(taskList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}

/**
 * /*******************************************************************************
 * Copyright 2017 Chris Deter, Arne Salveter Sven Marquardt
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

import space.objectfinder.backend.domain.AbstractSubTask;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.SubTaskCheckbox;
import space.objectfinder.backend.domain.SubTaskImage;
import space.objectfinder.backend.domain.SubTaskSlider;
import space.objectfinder.backend.service.api.ResponseManagerForSubTasks;

/**
 * Implementiert die crud methoden für {@link AbstractSubTask}
 * 
 * @author Sven Marquardt
 * @since 19.06.2017
 * @see ResponseManagerForSubTasks
 */
@Component
public class SubTaskResponseManager<T extends AbstractSubTask> implements ResponseManagerForSubTasks<T> {
	@Autowired
	SubTaskBaseRepository<T> repository;

	@Autowired
	MaintainanceTaskRepository mRepository;

	private final static Logger LOGGER = LoggerFactory.getLogger(SubTaskResponseManager.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.service.api.ResponseManagerForDaos#get()
	 */
	@Override
	public ResponseEntity<List<T>> getAll(final Long taskId, final Class<T> clazz) {
		SubTaskResponseManager.LOGGER.info("GET ID AUF:{} mit und taskId:{}", this.getClass(), taskId);
		final List<T> buffer = new LinkedList<>();
		final Optional<MaintainanceTask> task = this.mRepository.findById(taskId);
		task.ifPresent(t -> t.getSubTasks().stream().filter(s -> clazz.isAssignableFrom(s.getClass())).map(clazz::cast)
				.forEach(buffer::add));
		return new ResponseEntity<>(buffer, HttpStatus.OK);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.service.api.ResponseManagerForDaos#getId(java.
	 * lang.Object)
	 */
	@Override
	public ResponseEntity<T> getId(final Long id, final Long taskId) {
		SubTaskResponseManager.LOGGER.info("GET ID AUF:{} mit id:{} und taskId:{}", this.getClass(), id, taskId);
		return this.optionalResponse(this.repository.findById(id));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.service.api.ResponseManagerForDaos#post(java.
	 * lang.Object)
	 */
	@Override
	public ResponseEntity<T> post(final T object, final Long taskId) {
		SubTaskResponseManager.LOGGER.info("Versuche {} zu speichern. Für Maintainancetask: {}. Object ist: {}",
				object.getClass(), taskId, object);
		final Optional<MaintainanceTask> mOptional = this.mRepository.findById(taskId);
		if (mOptional.isPresent()) {
			mOptional.get().getSubTasks().add(object);
			final T response = this.repository.save(object);
			SubTaskResponseManager.LOGGER.info("Object: {} gespeichert an Task {}", response, mOptional.get());
			this.mRepository.save(mOptional.get());
			SubTaskResponseManager.LOGGER.info("SPEICHER:{}", mOptional.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		SubTaskResponseManager.LOGGER.info("NOT FOUND :{}", taskId);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.service.api.ResponseManagerForDaos#put(java.
	 * lang.Object, java.lang.Object)
	 */
	@Override
	public ResponseEntity<Void> put(final Long id, final T object, Class<T> clazz) {
		SubTaskResponseManager.LOGGER.info("PUT AUF:{} mit id:{} und object:{}", this.getClass(), id, object);
		final Optional<T> maOptional = this.repository.findById(id);
		if (maOptional.isPresent()) {
			final Optional<T> buffer = this.saveChanges(maOptional.get(), object, clazz);
			if (buffer.isPresent()) {
				this.repository.save(buffer.get());
				SubTaskResponseManager.LOGGER.info("OBJEKT gespeichert:{}", buffer.get());
				return ResponseEntity.ok().build();
			}
			SubTaskResponseManager.LOGGER.error("Konnte objekt nicht casten:{}", maOptional.get());
			return ResponseEntity.notFound().build();
		}
		SubTaskResponseManager.LOGGER.info("Objekt nicht gespeichert");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Speichert die objekte von changes in databaseObject ohne dabei die id zu
	 * überschreiben
	 *
	 * @param databaseObject
	 *            das objekt in dem die änderungen gespeichert werden sollen
	 * @param changes
	 *            Die änderungen diegespeichert werden sollen
	 * @param clazz
	 *            Typ der objekte
	 * @return Wenn {@link Optional#empty()} ist ein fehler beim casten aufgetreten
	 * @author "Sven Marquardt"
	 * @since 01.07.2017
	 */
	private Optional<T> saveChanges(final T databaseObject, final T changes, Class<T> clazz) {
		Optional<T> buffer = Optional.empty();
		// Es wird sichergestellt das alle cast save cast sind. Heißt bei falschen
		// objekten schlägt alles fehl es führt aber nicht zum absturz
		if (databaseObject instanceof SubTaskImage) {
			final SubTaskImage bufferD = SubTaskImage.class.cast(databaseObject);
			final SubTaskImage bufferC = SubTaskImage.class.cast(changes);
			bufferD.setPicture(bufferC.getPicture());
			if (bufferD.getClass().isAssignableFrom(clazz)) {

				buffer = Optional.of(clazz.cast(bufferD));
			}
		} else if (databaseObject instanceof SubTaskCheckbox) {
			final SubTaskCheckbox bufferD = (SubTaskCheckbox) databaseObject;
			final SubTaskCheckbox bufferC = (SubTaskCheckbox) changes;
			bufferD.setValue(bufferC.isValue());
			if (bufferD.getClass().isAssignableFrom(clazz)) {
				buffer = Optional.of(clazz.cast(bufferD));
			}
		} else {
			final SubTaskSlider bufferD = (SubTaskSlider) databaseObject;
			final SubTaskSlider bufferC = (SubTaskSlider) changes;
			bufferD.setValue(bufferC.getValue());
			if (bufferD.getClass().isAssignableFrom(clazz)) {
				buffer = Optional.of(clazz.cast(bufferD));
			}
		}
		if (buffer.isPresent()) {
			buffer.get().setTitle(changes.getTitle());
		}
		return buffer;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.service.api.ResponseManagerForDaos#delete()
	 */
	@Override
	public ResponseEntity<Void> delete(final Long id, Long mId) {
		SubTaskResponseManager.LOGGER.info("DELETE AUF:{} mit id:{}", this.getClass(), id);
		final Optional<MaintainanceTask> buOptional = this.mRepository.findById(mId);
		if (!buOptional.isPresent()) {
			SubTaskResponseManager.LOGGER.info("Maintainancetask nicht gefunden");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		buOptional.get().getSubTasks().removeIf(s -> s.getId() == id);
		this.mRepository.save(buOptional.get());

		return this.deleteEntity(id, this.repository);
	}

}

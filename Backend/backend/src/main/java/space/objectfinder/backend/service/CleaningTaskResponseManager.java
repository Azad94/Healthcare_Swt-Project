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

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.SyncBody;
import space.objectfinder.backend.domain.Task.State;
import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.api.ResponseManagerForTasks;

/**
 * Führt nötige aktionen mit der datenbank aus um Objekte zu speichern und zu
 * lesen. Nur für {@link CleaningTask} objekte
 *
 * @author Sven Marquardt, Chris Deter
 * @since 21.06.2017
 * @see ResponseManagerForTasks
 */
@Component
public class CleaningTaskResponseManager implements ResponseManagerForTasks<CleaningTask> {

	/**
	 *
	 */
	@Autowired
	CleaningTaskRepository repository;

	@Autowired
	UserRepository uRepository;

	private final static Logger LOGGER = LoggerFactory.getLogger(CleaningTaskResponseManager.class);

	@Override
	public ResponseEntity<CleaningTask> postTask(final space.objectfinder.backend.domain.CleaningTask body) {
		CleaningTaskResponseManager.LOGGER.info("POST AUF TASK: {} mit body:{}", this.getClass(), body);
		body.setLastEdited(LocalDateTime.now());
		return new ResponseEntity<>(this.repository.save(body), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<space.objectfinder.backend.domain.CleaningTask> taskGetId(final Long id) {
		CleaningTaskResponseManager.LOGGER.info("GET ID AUF:{} mit id:{}", this.getClass(), id);
		return this.optionalResponse(this.repository.findById(id));
	}

	@Override
	public ResponseEntity<Long> taskGetSize(final SyncBody userName) {
		CleaningTaskResponseManager.LOGGER.info("GET SIZE AUF:{} mit body:{}", this.getClass(), userName);
		return ResponseEntity.ok(this.repository.count());
	}

	@Override
	public ResponseEntity<Void> taskIdPut(final Long id, final CleaningTask body) {
		final Optional<CleaningTask> buffer = this.repository.findById(id);
		CleaningTaskResponseManager.LOGGER.info("PUT AUF:{} mit id:{} und body:{}", this.getClass(), id, body);
		if (buffer.isPresent()) {
			if (body != null) {
				this.repository.save(this.updateCleaningTask(body, buffer.get()));
			}
			return ResponseEntity.ok().build();
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Aktualisiert einen CleaningTask in der DB
	 *
	 * @param putfromRest
	 * @param fromReop
	 */
	private CleaningTask updateCleaningTask(final CleaningTask putfromRest, final CleaningTask fromReop) {
		CleaningTaskResponseManager.LOGGER.info("Update AUF TASK: ...");
		return (CleaningTask) this.setNewValues(fromReop, putfromRest);
	}

	@Override
	public ResponseEntity<List<space.objectfinder.backend.domain.CleaningTask>> tasksGet(final int limit,
			final String before, final String since, final boolean open, final boolean closed, final boolean processing,
			final boolean own, final SyncBody body, final Integer building) {
		CleaningTaskResponseManager.LOGGER.info("Cleaning Anfrage mit {} {} {} {} {} {} {} {} {}", limit, before, since,
				open, closed, processing, own, body);
		final Optional<User> uOptional = this.uRepository.findById(body.getId());
		final Set<State> states = new HashSet<>();
		if (open) {
			states.add(State.OPEN);
		}
		if (closed) {
			states.add(State.CLOSED);
		}
		if (processing) {
			states.add(State.PROCESSING);
		}
		if (uOptional.isPresent()) {
			final User bUser = uOptional.get();
			final LocalDateTime start = since.isEmpty() ? LocalDateTime.MIN
					: ZonedDateTime.parse(since).toLocalDateTime();
			final LocalDateTime end = before.isEmpty() ? LocalDateTime.MAX
					: ZonedDateTime.parse(before).toLocalDateTime();
			CleaningTaskResponseManager.LOGGER.info("Anfrage user gefunden mit {} {} {} {} {} {} {} {} {}", limit,
					start, end, states, own, bUser, this.repository);
			final ResponseEntity<List<CleaningTask>> response = this.get(limit, end, start, states, own, bUser,
					this.repository, building);
			CleaningTaskResponseManager.LOGGER.info("Return {}", response.getBody());
			return response;
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.service.api.ResponseManagerForTasks#delete(
	 * java.lang.Long)
	 */
	@Override
	public ResponseEntity<Void> delete(final Long id) {
		CleaningTaskResponseManager.LOGGER.info("DELTE AUF:{} mit id:{}", this.getClass(), id);
		return this.deleteEntity(id, this.repository);
	}

}

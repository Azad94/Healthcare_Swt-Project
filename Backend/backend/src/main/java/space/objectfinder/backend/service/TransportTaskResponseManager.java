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

import space.objectfinder.backend.domain.SyncBody;
import space.objectfinder.backend.domain.Task.State;
import space.objectfinder.backend.domain.TransportTask;
import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.api.ResponseManagerForTasks;

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@Component
public class TransportTaskResponseManager implements ResponseManagerForTasks<TransportTask> {

	@Autowired
	TransportTaskRepository repository;

	@Autowired
	UserRepository uRepository;

	private final static Logger LOGGER = LoggerFactory.getLogger(CleaningTaskResponseManager.class);

	@Override
	public ResponseEntity<TransportTask> postTask(final TransportTask body) {
		TransportTaskResponseManager.LOGGER.info("POST AUF TASK: {} mit body:{}", this.getClass(), body);
		return new ResponseEntity<>(this.repository.save(body), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TransportTask> taskGetId(final Long id) {
		TransportTaskResponseManager.LOGGER.info("GET ID AUF:{} mit id:{}", this.getClass(), id);
		return this.optionalResponse(this.repository.findById(id));
	}

	@Override
	public ResponseEntity<Long> taskGetSize(final SyncBody userName) {
		TransportTaskResponseManager.LOGGER.info("GET SIZE AUF:{} mit body:{}", this.getClass(), userName);
		return ResponseEntity.ok(this.repository.count());
	}

	@Override
	public ResponseEntity<Void> taskIdPut(final Long id, final TransportTask body) {
		TransportTaskResponseManager.LOGGER.info("PUT AUF:{} mit id:{} und body:{}", this.getClass(), id, body);
		final Optional<TransportTask> buffer = this.repository.findById(id);
		if (!buffer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		TransportTask found = buffer.get();
		if (body != null) {
			found = this.updateTaskInRepo(body, found);
			this.repository.save(found);
		}
		return ResponseEntity.ok().build();

	}

	/**
	 * updated den Task in der Repo
	 *
	 * @param putfromRest
	 * @param fromReop
	 */
	public TransportTask updateTaskInRepo(final TransportTask putfromRest, final TransportTask fromReop) {
		TransportTask buffer = fromReop;
		buffer.targetLocation(putfromRest.getTargetLocation());
		buffer = (TransportTask) this.setNewValues(fromReop, putfromRest);
		return buffer;

	}

	@Override
	public ResponseEntity<List<TransportTask>> tasksGet(final int limit, final String before, final String since,
			final boolean open, final boolean closed, final boolean processing, final boolean own, final SyncBody body,
			final Integer building) {
		TransportTaskResponseManager.LOGGER.info("Anfrage mit {} {} {} {} {} {} {} {} {}", limit, before, since, open,
				closed, processing, own, body);
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
			TransportTaskResponseManager.LOGGER.info("Anfrage user gefunden mit {} {} {} {} {} {} {} {} {}", limit,
					start, end, states, own, bUser, this.repository);
			return this.get(limit, end, start, states, own, bUser, this.repository, building);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.service.api.ResponseManagerForTasks#delete(
	 * java.lang.Long)
	 */
	@Override
	public ResponseEntity<Void> delete(final Long id) {
		TransportTaskResponseManager.LOGGER.info("DELTE AUF:{} mit id:{}", this.getClass(), id);
		return this.deleteEntity(id, this.repository);
	}

}

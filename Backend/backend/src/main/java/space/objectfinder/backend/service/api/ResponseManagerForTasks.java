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
package space.objectfinder.backend.service.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import space.objectfinder.backend.domain.SyncBody;
import space.objectfinder.backend.domain.Task;
import space.objectfinder.backend.domain.Task.State;
import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.TaskBaseRepository;

/**
 * Interface um die Responses der Tasks besser zu managen. Sollte nur vom
 * jeweiligen Controller aus angesprochen werden um das generieren zu
 * vereinfachen
 *
 * @author Sven Marquardt
 *
 * @param <T>
 *            Welches Task Objekt
 */
public interface ResponseManagerForTasks<T extends Task> extends ResponseManager {
	/**
	 * Einen Task Löschen
	 *
	 * @param id
	 *            Welchen task zu löschen ist
	 * @return
	 * @author Sven Marquardt
	 * @since 21.06.2017
	 */
	public ResponseEntity<Void> delete(Long id);

	/**
	 * Erstelle einen neuen Task
	 *
	 * @param body
	 *            Body des neuen {@link Task}
	 * @return Den neu erstellten {@link Task} mit seiner id aus der datenbank
	 */
	ResponseEntity<T> postTask(T body);

	/**
	 * Einzelnen Task suchen und zurück geben
	 *
	 * @param id
	 *            Id des {@link Task}
	 * @return
	 */
	ResponseEntity<T> taskGetId(Long id);

	/**
	 * Anzahl der Tasks erhalten
	 *
	 * @param userName
	 *            {@link SyncBody} um gegebenfalls nur die größe der neuen tasks zu
	 *            geben
	 * @return
	 */
	ResponseEntity<Long> taskGetSize(SyncBody userName);

	/**
	 * Einzelnen Task bearbeiten
	 *
	 * @param id
	 *            Id des {@link Task}
	 * @param body
	 *            Neuen werte des {@link Task}
	 * @return
	 */
	ResponseEntity<Void> taskIdPut(Long id, T body);

	/**
	 * Erhallte alle tasks mit den gegebenen suchkriterien
	 *
	 * @param limit
	 *            Wie viele {@link Task} sollen geliefert werden
	 * @param before
	 *            Bis zu welchem datum soll gesucht werden
	 * @param since
	 *            Ab welchem datum soll gesucht werden
	 * @param open
	 *            {@link Task#getState()} == 2
	 * @param closed
	 *            {@link Task#getState()} ==3
	 * @param processing
	 *            {@link Task#getState()}==1
	 * @param own
	 *            Nur eigenen tasks suchen
	 * @param body
	 *            {@link SyncBody} mit zusätslichen informationen
	 * @param building
	 *            In welchem gebäude sollen nach task gesucht werden bei
	 *            <code>null</code> werden alle durchsucht
	 * @return
	 */
	ResponseEntity<List<T>> tasksGet(int limit, String before, String since, boolean open, boolean closed,
			boolean processing, boolean own, SyncBody body, Integer building);

	/**
	 * Liefert eine liste von {@link Task} die den gegeben kriterien entsprechen
	 *
	 * @param limit
	 *            Wie viele task sollen geliefert werden
	 * @param end
	 *            Bis zu welchem datum soll gesucht werden
	 * @param start
	 *            Ab welchem datum soll gesucht werden
	 * @param states
	 *            Welche staten dürfen diese {@link Task} haben
	 * @param own
	 *            Nur {@link Task} liefern die selbst erstellt wurden
	 * @param user
	 *            Falls nur eigenen geliefert werden sollen von welchem user
	 * @param repository
	 *            Das repository in dem gesucht wird
	 * @param building
	 *            Nur tasks von diesem gebäude suchen
	 * @return Ein {@link ResponseEntity} mit der {@link List} an {@link Task} die
	 *         gefunden wurden
	 * @author Sven Marquardt
	 * @since 25.06.2017
	 */
	default ResponseEntity<List<T>> get(final int limit, final LocalDateTime end, final LocalDateTime start,
			final Set<State> states, final boolean own, final User user, final TaskBaseRepository<T> repository,
			final Integer building) {
		final Logger log = LoggerFactory.getLogger(ResponseManagerForTasks.class);
		final List<T> all = StreamSupport.stream(repository.findAll().spliterator(), false)
				.filter(t -> t.getCreationTime().isAfter(start) && t.getCreationTime().isBefore(end))
				.collect(Collectors.toList());
		log.info("Anfrage {} states:{}", all, states);
		final List<T> response = all.stream().limit(limit).filter(t -> {
			boolean filter = true;
			if (!states.stream().anyMatch(s -> s.getValue() == t.getState())) {
				filter = false;
			}
			if (own && !user.equals(t.getCreator())) {
				filter = false;
			}
			if (building != null && !t.getBeaconObject().getLocation().getBuilding().equals(building)) {
				filter = false;
			}
			return filter;
		}).collect(Collectors.toList());
		log.info("Anfrage {}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param repository
	 * @return
	 * @author "Sven Marquardt"
	 * @since 30.06.2017
	 * @see ResponseManager#deleteEntity(java.io.Serializable, CrudRepository)
	 */
	default ResponseEntity<Void> deleteEntity(final Long id, final CrudRepository<T, Long> repository) {
		return this.deleteEntity(id, repository);
	}

	/**
	 * Setze die neuen werte für ein {@link Task}
	 *
	 * @param oldValues
	 *            Die alten werte die überschrieben werden
	 * @param newValues
	 *            Die neuen werte die den alten zugeschrieben werden
	 * @return Oldvalues mit den neuen werten nur die id bleibt gleich
	 * @author "Sven Marquardt"
	 * @since 30.06.2017
	 */
	default Task setNewValues(final Task oldValues, final Task newValues) {
		return oldValues.level(newValues.getLevel()).acceptedTime(newValues.getAcceptedTime())
				.beaconObect(newValues.getBeaconObject()).closedTime(newValues.getClosedTime())
				.creator(newValues.getCreator()).discription(newValues.getDescription()).editor(newValues.getEditor())
				.name(newValues.getName()).state(newValues.getState());
	}

}

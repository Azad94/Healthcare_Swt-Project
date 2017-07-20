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
package space.objectfinder.backend.web;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.SyncBody;

/**
 * Schnitstelle für {@link CleaningTask}
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
public interface CleaningtasksApi {
	/**
	 * Erhalte alle {@link CleaningTask} aus der Datenbank
	 *
	 * @param body Body mit informationen über den User der die anfrage stellt
	 * @param limit Wie viele {@link CleaningTask} sollen geliefert werden.
	 * @param before {@link CleaningTask} die seit diesem datum erstellt wurden
	 * @param since {@link CleaningTask} die vor diesem datum erstellt wurden
	 * @param open Nur {@link CleaningTask} mit dem status offen
	 * @param closed Nur {@link CleaningTask} mit dem status geschlossen
	 * @param processing Nur {@link CleaningTask} mit dem status in bearbeitung
	 * @param own Nur {@link CleaningTask} die von einem selber erstellt wurden
	 * @param building Nur {@link CleaningTask} von diesem gebäude
	 * @return Eine {@link List} von {@link CleaningTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/cleaningtasks", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<CleaningTask>> cleaningtasksGet(@RequestBody SyncBody body,
			@RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
			@RequestParam(value = "before", required = false, defaultValue = "") String before,
			@RequestParam(value = "since", required = false, defaultValue = "") String since,
			@RequestParam(value = "open", required = false, defaultValue = "true") Boolean open,
			@RequestParam(value = "closed", required = false, defaultValue = "true") Boolean closed,
			@RequestParam(value = "processing", required = false, defaultValue = "true") Boolean processing,
			@RequestParam(value = "own", required = false, defaultValue = "false") Boolean own,
			@RequestParam(value = "building", required = false) Integer building);

	/**
	 * Erstelle einen neuen {@link CleaningTask}
	 *
	 * @param body Informationen für den neuen {@link CleaningTask}
	 * @return Den neu erstellten {@link CleaningTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/cleaningtasks", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<CleaningTask> cleaningtasksPost(@RequestBody CleaningTask body);

	/**
	 * Erhalte die anzahl der {@link CleaningTask}
	 *
	 * @param newTasks Sollen nur neu erstellte {@link CleaningTask} gezählt werden?
	 * @param userName Informationen für sync
	 * @return Die anzahl der {@link CleaningTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/cleaningtasks/size", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<Long> cleaningtasksSizeGet(
			@NotNull @RequestParam(value = "newTasks", required = true) Boolean newTasks,
			@RequestBody SyncBody userName);

	/**
	 * Erhalte einen einzelnen {@link CleaningTask}
	 *
	 * @param taskid Id des objektes
	 * @return {@link CleaningTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/cleaningtasks/{taskid}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<CleaningTask> cleaningtasksTaskidGet(@PathVariable("taskid") Long taskid);

	/**
	 * Bearbeite einen {@link CleaningTask}
	 *
	 * @param taskid Id des {@link CleaningTask}
	 * @param body Neue werte des {@link CleaningTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/cleaningtasks/{taskid}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> cleaningtasksTaskidPut(@PathVariable("taskid") Long taskid, @RequestBody CleaningTask body);

	/**
	 * Lösche einen {@link CleaningTask}
	 *
	 * @param taskid Id des {@link CleaningTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/cleaningtasks/{taskid}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> cleaningtasksTaskidDelete(@PathVariable("taskid") Long taskid);

	/**
	 * Das gleiche wie
	 * {@link #cleaningtasksAppGet(SyncBody, Integer, String, String, Boolean, Boolean, Boolean, Boolean, Integer)}
	 * bloß für die app
	 *
	 * @param body {@link SyncBody}
	 * @param limit Wie viele {@link CleaningTask}
	 * @param before Ab welchem datum erstellt
	 * @param since Bis bestimmtes datum erstellt
	 * @param open Status offen
	 * @param closed Status geschlossen
	 * @param processing Status processing
	 * @param own Nur eigene
	 * @param building Von welchem gebäude
	 * @return {@link List} {@link CleaningTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 * @see #cleaningtasksAppGet(SyncBody, Integer, String, String, Boolean,
	 *      Boolean, Boolean, Boolean, Integer)
	 */
	@RequestMapping(value = "/cleaningtasksApp", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<List<CleaningTask>> cleaningtasksAppGet(@RequestBody SyncBody body,
			@RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
			@RequestParam(value = "before", required = false, defaultValue = "") String before,
			@RequestParam(value = "since", required = false, defaultValue = "") String since,
			@RequestParam(value = "open", required = false, defaultValue = "true") Boolean open,
			@RequestParam(value = "closed", required = false, defaultValue = "true") Boolean closed,
			@RequestParam(value = "processing", required = false, defaultValue = "true") Boolean processing,
			@RequestParam(value = "own", required = false, defaultValue = "false") Boolean own,
			@RequestParam(value = "building", required = false) Integer building);

	/**
	 * Das gleiche wie {@link #cleaningtasksAppSizeGet(Boolean, SyncBody)} bloß für
	 * app
	 *
	 * @param newTasks Nur neue {@link CleaningTask}
	 * @param userName {@link SyncBody}
	 * @return Anzahl der {@link CleaningTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 * @see #cleaningtasksSizeGet(Boolean, SyncBody)
	 */
	@RequestMapping(value = "/cleaningtasksApp/size", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<Long> cleaningtasksAppSizeGet(
			@NotNull @RequestParam(value = "newTasks", required = true) Boolean newTasks,
			@RequestBody SyncBody userName);

	/**
	 * Bearbeite eine gruppe von {@link CleaningTask}
	 *
	 * @param body Die {@link CleaningTask} als {@link List} die bearbeitet werden
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/cleaningtasks", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> cleaningtasksTaskPut(@RequestBody List<CleaningTask> body);

}

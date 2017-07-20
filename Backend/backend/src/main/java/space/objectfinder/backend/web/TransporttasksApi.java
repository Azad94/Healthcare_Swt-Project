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

import space.objectfinder.backend.domain.SyncBody;
import space.objectfinder.backend.domain.TransportTask;

/**
 * Schnittstelle zu {@link TransportTask}
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
public interface TransporttasksApi {
	/**
	 * Erhalte alle {@link TransportTask} aus der Datenbank
	 *
	 * @param body Body mit informationen über den User der die anfrage stellt
	 * @param limit Wie viele {@link TransportTask} sollen geliefert werden.
	 * @param before {@link TransportTask} die seit diesem datum erstellt wurden
	 * @param since {@link TransportTask} die vor diesem datum erstellt wurden
	 * @param open Nur {@link TransportTask} mit dem status offen
	 * @param closed Nur {@link TransportTask} mit dem status geschlossen
	 * @param processing Nur {@link TransportTask} mit dem status in bearbeitung
	 * @param own Nur {@link TransportTask} die von einem selber erstellt wurden
	 * @param building Nur {@link TransportTask} von diesem gebäude
	 * @return Eine {@link List} von {@link TransportTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/transporttasks", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<TransportTask>> transporttasksGet(@RequestBody SyncBody body,
			@RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
			@RequestParam(value = "before", required = false, defaultValue = "") String before,
			@RequestParam(value = "since", required = false, defaultValue = "") String since,
			@RequestParam(value = "open", required = false, defaultValue = "true") Boolean open,
			@RequestParam(value = "closed", required = false, defaultValue = "true") Boolean closed,
			@RequestParam(value = "processing", required = false, defaultValue = "true") Boolean processing,
			@RequestParam(value = "own", required = false, defaultValue = "false") Boolean own,
			@RequestParam(value = "building", required = false) Integer building);

	/**
	 * Erstelle einen neuen {@link TransportTask}
	 *
	 * @param body Informationen für den neuen {@link TransportTask}
	 * @return Den neu erstellten {@link TransportTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/transporttasks", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<TransportTask> transporttasksPost(@RequestBody TransportTask body);

	/**
	 * Erhalte die anzahl der {@link TransportTask}
	 *
	 * @param newTasks Sollen nur neu erstellte {@link TransportTask} gezählt
	 *            werden?
	 * @param userName Informationen für sync
	 * @return Die anzahl der {@link TransportTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/transporttasks/size", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<Long> transporttasksSizeGet(
			@NotNull @RequestParam(value = "newTasks", required = true) Boolean newTasks,
			@RequestBody SyncBody userName);

	/**
	 * Erhalte einen einzelnen {@link TransportTask}
	 *
	 * @param taskid Id des objektes
	 * @return {@link TransportTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/transporttasks/{taskid}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<TransportTask> transporttasksTaskidGet(@PathVariable("taskid") Long taskid);

	/**
	 * Bearbeite einen {@link TransportTask}
	 *
	 * @param taskid Id des {@link TransportTask}
	 * @param body Neue werte des {@link TransportTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/transporttasks/{taskid}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> transporttasksTaskidPut(@PathVariable("taskid") Long taskid,
			@PathVariable("body") TransportTask body);

	/**
	 * Lösche einen {@link TransportTask}
	 *
	 * @param taskid Id des {@link TransportTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/transporttasks/{taskid}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> transporttasksTaskidDelete(@PathVariable("taskid") Long taskid);

	/**
	 * Das gleiche wie {@link #transporttasksSizeGet(Boolean, SyncBody)} bloß für
	 * app
	 *
	 * @param newTasks Nur neue {@link TransportTask}
	 * @param userName {@link SyncBody}
	 * @return Anzahl der {@link TransportTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/transporttasksApp/size", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<Long> transporttasksAppSizeGet(
			@NotNull @RequestParam(value = "newTasks", required = true) Boolean newTasks,
			@RequestBody SyncBody userName);

	/**
	 * Erhalte alle {@link TransportTask} aus der Datenbank
	 *
	 * @param body Body mit informationen über den User der die anfrage stellt
	 * @param limit Wie viele {@link TransportTask} sollen geliefert werden.
	 * @param before {@link TransportTask} die seit diesem datum erstellt wurden
	 * @param since {@link TransportTask} die vor diesem datum erstellt wurden
	 * @param open Nur {@link TransportTask} mit dem status offen
	 * @param closed Nur {@link TransportTask} mit dem status geschlossen
	 * @param processing Nur {@link TransportTask} mit dem status in bearbeitung
	 * @param own Nur {@link TransportTask} die von einem selber erstellt wurden
	 * @param building Nur {@link TransportTask} von diesem gebäude
	 * @return Eine {@link List} von {@link TransportTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/transporttasksApp", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<List<TransportTask>> transporttasksAppGet(@RequestBody SyncBody body,
			@RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
			@RequestParam(value = "before", required = false, defaultValue = "") String before,
			@RequestParam(value = "since", required = false, defaultValue = "") String since,
			@RequestParam(value = "open", required = false, defaultValue = "true") Boolean open,
			@RequestParam(value = "closed", required = false, defaultValue = "true") Boolean closed,
			@RequestParam(value = "processing", required = false, defaultValue = "true") Boolean processing,
			@RequestParam(value = "own", required = false, defaultValue = "false") Boolean own,
			@RequestParam(value = "building", required = false) Integer building);

	/**
	 * Bearbeite eine gruppe von {@link TransportTask}
	 *
	 * @param body Die {@link TransportTask} als {@link List} die bearbeitet werden
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/transporttasks", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> transporttasksTaskPut(@PathVariable("body") List<TransportTask> body);
}

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

import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.SyncBody;

/**
 * Schnittstelle zu {@link MaintainanceTask}
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
public interface MaintainancetasksApi {
	/**
	 * Erhallte alle {@link MaintainanceTask} als {@link List}
	 *
	 * @param body {@link SyncBody}
	 * @param limit Wie viele {@link MaintainanceTask}
	 * @param before Seit welchem datum erstellt
	 * @param since Bis welchem datum erstellt
	 * @param open Nur offene tasks
	 * @param closed Nur geschlossene tasks
	 * @param processing Nur tasks die bearbeitet werden
	 * @param own Nur eigene tasks siehe {@link SyncBody}
	 * @param building Nur tasks von diesem gebäude
	 * @return {@link List} von {@link CleaningtasksApi}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<MaintainanceTask>> maintainancetasksGet(@RequestBody final SyncBody body,
			@RequestParam(value = "limit", required = false, defaultValue = "20") final Integer limit,
			@RequestParam(value = "before", required = false, defaultValue = "") final String before,
			@RequestParam(value = "since", required = false, defaultValue = "") final String since,
			@RequestParam(value = "open", required = false, defaultValue = "true") final Boolean open,
			@RequestParam(value = "closed", required = false, defaultValue = "true") final Boolean closed,
			@RequestParam(value = "processing", required = false, defaultValue = "true") final Boolean processing,
			@RequestParam(value = "own", required = false, defaultValue = "false") final Boolean own,
			@RequestParam(value = "building", required = false) Integer building);

	/**
	 * Erstelle einen neuen {@link MaintainanceTask}
	 *
	 * @param body Informationen des neuen tasks
	 * @return {@link MaintainanceTask} der neu erstellt wurde
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<MaintainanceTask> maintainancetasksPost(@RequestBody MaintainanceTask body);

	/**
	 * Anzahl an {@link MaintainanceTask}
	 *
	 * @param newTasks Nur neue tasks wird anhand des {@link SyncBody#getUserName()}
	 *            ausfindig gemacht
	 * @param userName {@link SyncBody} für sync und newTasks
	 * @return Anzahl der {@link MaintainanceTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/size", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<Long> maintainancetasksSizeGet(
			@NotNull @RequestParam(value = "newTasks", required = true) Boolean newTasks,
			@RequestBody SyncBody userName);

	/**
	 * Erhalte alle infos eines einzelnden {@link MaintainanceTask}
	 *
	 * @param taskid Id des tasks
	 * @return {@link MaintainanceTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{taskid}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<MaintainanceTask> maintainancetasksTaskidGet(@PathVariable("taskid") Long taskid);

	/**
	 * Bearbeite einen einzelnen {@link MaintainanceTask}
	 *
	 * @param taskid Id des {@link MaintainanceTask}
	 * @param body Neuen informationen des {@link MaintainanceTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{taskid}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> maintainancetasksTaskidPut(@PathVariable("taskid") Long taskid,
			@RequestBody MaintainanceTask body);

	/**
	 * Lösche einen einzelnen {@link MaintainanceTask}
	 *
	 * @param taskid Id des {@link MaintainanceTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{taskid}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> maintainancetasksTaskidDelete(@PathVariable("taskid") Long taskid);

	/**
	 * Erhallte alle {@link MaintainanceTask} als {@link List}
	 *
	 * @param body {@link SyncBody}
	 * @param limit Wie viele {@link MaintainanceTask}
	 * @param before Seit welchem datum erstellt
	 * @param since Bis welchem datum erstellt
	 * @param open Nur offene tasks
	 * @param closed Nur geschlossene tasks
	 * @param processing Nur tasks die bearbeitet werden
	 * @param own Nur eigene tasks siehe {@link SyncBody}
	 * @param building Nur tasks von diesem gebäude
	 * @return {@link List} von {@link CleaningtasksApi}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasksApp", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<List<MaintainanceTask>> maintainancetasksAppGet(@RequestBody final SyncBody body,
			@RequestParam(value = "limit", required = false, defaultValue = "20") final Integer limit,
			@RequestParam(value = "before", required = false, defaultValue = "") final String before,
			@RequestParam(value = "since", required = false, defaultValue = "") final String since,
			@RequestParam(value = "open", required = false, defaultValue = "true") final Boolean open,
			@RequestParam(value = "closed", required = false, defaultValue = "true") final Boolean closed,
			@RequestParam(value = "processing", required = false, defaultValue = "true") final Boolean processing,
			@RequestParam(value = "own", required = false, defaultValue = "false") Boolean own,
			@RequestParam(value = "building", required = false) Integer building);

	/**
	 * Anzahl an {@link MaintainanceTask}
	 *
	 * @param newTasks Nur neue tasks wird anhand des {@link SyncBody#getUserName()}
	 *            ausfindig gemacht
	 * @param userName {@link SyncBody} für sync und newTasks
	 * @return Anzahl der {@link MaintainanceTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasksApp/size", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<Long> maintainancetasksAppSizeGet(
			@NotNull @RequestParam(value = "newTasks", required = true) Boolean newTasks,
			@RequestBody SyncBody userName);

	/**
	 * Eine {@link List} von {@link MaintainanceTask} bearbeiten
	 *
	 * @param body Liste der {@link MaintainanceTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 * @see #maintainancetasksTaskidPut(Long, MaintainanceTask)
	 */
	@RequestMapping(value = "/maintainancetasks", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> maintainancetasksTaskPut(@RequestBody List<MaintainanceTask> body);
}

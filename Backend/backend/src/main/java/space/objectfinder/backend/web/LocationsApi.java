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

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.TransportTask;

/**
 * Schnitstelle für {@link Location}
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
public interface LocationsApi {
	/**
	 * Erhalte alle {@link Location} die verfügbar sind
	 *
	 * @param floor
	 *            Alle {@link Location} die diesen floor beinhalten
	 * @param building
	 *            Alle {@link Location} die dises building beinhalten
	 * @param room
	 *            Alle {@link Location} die diesen room beinhalten
	 * @return {@link List} von {@link Location}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/locations", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<Location>> locationsGet(@RequestParam(value = "floor", required = false) Integer floor,
			@RequestParam(value = "building", required = false) Integer building,
			@RequestParam(value = "room", required = false) Integer room);

	/**
	 * Erhalte alle informationen über eine einzelne {@link Location}
	 *
	 * @param locationId
	 *            Die id der {@link Location}
	 * @return {@link Location}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/locations/{locationId}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<Location> locationsLocationIdGet(@PathVariable("locationId") Long locationId);

	@RequestMapping(value = "/locations/{locationId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> locationsLocationIdPut(@PathVariable("locationId") Long locationId,
			@RequestBody Location body);

	/**
	 * Erstelle eine neue {@link Location}
	 *
	 * @param body
	 *            Informationen für die neue {@link Location}
	 * @return Die neu erstelle {@link Location} mit ihrer DB id
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/locations", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<Location> locationsPost(@RequestBody Location body);

	/**
	 * Lösche eine einzelne {@link Location}
	 *
	 * @param locationId
	 *            Id der {@link Location}
	 * @return {@link Void} also nichts
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/locations/{locationId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> locationsLocationIdDelete(@PathVariable("locationId") Long locationId);

	/**
	 * Erhalte alle {@link TransportTask} zu dieser {@link Location}
	 *
	 * @param floor
	 *            Welchen floor müssen diese haben
	 * @param building
	 *            Welches building müssen diese haben
	 * @param room
	 *            Welchen room müssen diese haben
	 * @return {@link List} von {@link TransportTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/locations/transporttasks", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<TransportTask>> locationsGetTransportTasks(
			@RequestParam(value = "floor", required = false) Integer floor,
			@RequestParam(value = "building", required = false) Integer building,
			@RequestParam(value = "room", required = false) Integer room);

	/**
	 * Erhalte alle {@link CleaningTask} zu einer {@link Location}
	 *
	 * @param floor
	 *            Welchen floor müssen diese haben
	 * @param building
	 *            Welches building müssen diese haben
	 * @param room
	 *            Welchen room müssen diese haben
	 * @return {@link List} von {@link CleaningTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/locations/cleangingtasks", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<CleaningTask>> locationsGetCleaningTask(
			@RequestParam(value = "floor", required = false) Integer floor,
			@RequestParam(value = "building", required = false) Integer building,
			@RequestParam(value = "room", required = false) Integer room);

	/**
	 * Erhalte alle {@link MaintainanceTask} zu einer {@link Location}
	 *
	 * @param floor
	 *            Welchen floor müssen diese haben
	 * @param building
	 *            Welches building müssen diese haben
	 * @param room
	 *            Welchen room müssen diese haben
	 * @return {@link List} von {@link MaintainanceTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/locations/maintainancetasks", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<MaintainanceTask>> locationsGetMaintainanceTasks(
			@RequestParam(value = "floor", required = false) Integer floor,
			@RequestParam(value = "building", required = false) Integer building,
			@RequestParam(value = "room", required = false) Integer room);

}

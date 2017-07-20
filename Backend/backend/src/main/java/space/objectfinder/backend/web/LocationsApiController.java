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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.TransportTask;
import space.objectfinder.backend.service.LocationResponseManager;

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@RestController
public class LocationsApiController implements LocationsApi {
	@Autowired
	LocationResponseManager response;
	private static final Logger log = LoggerFactory.getLogger(LocationsApiController.class);

	@Override
	public ResponseEntity<Location> locationsLocationIdGet(@PathVariable("locationId") final Long locationId) {
		return this.response.getId(locationId);
	}

	@Override
	public ResponseEntity<Void> locationsLocationIdPut(@PathVariable("locationId") final Long locationId,
			@RequestBody final Location body) {
		return this.response.put(locationId, body);
	}

	@Override
	public ResponseEntity<Location> locationsPost(@RequestBody final Location body) {
		return this.response.post(body);
	}

	@Override
	public ResponseEntity<List<Location>> locationsGet(
			@RequestParam(value = "floor", required = false) final Integer floor,
			@RequestParam(value = "building", required = false) final Integer building,
			@RequestParam(value = "room", required = false) final Integer room) {
		LocationsApiController.log.info("Anfrage Location {} {} {}", room, floor, building);
		if (floor == null && building == null && room == null) {
			return this.response.get();
		} else {
			return this.response.getWithFilter(building, floor, room);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.web.LocationsApi#locationsLocationIdDelete(
	 * java.lang.Long, space.objectfinder.backend.domain.Location)
	 */
	@Override
	public ResponseEntity<Void> locationsLocationIdDelete(@PathVariable("locationId") final Long locationId) {
		return this.response.delete(locationId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.web.LocationsApi#locationsGetTransportTasks(
	 * java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ResponseEntity<List<TransportTask>> locationsGetTransportTasks(
			@RequestParam(value = "floor", required = false) final Integer floor,
			@RequestParam(value = "building", required = false) final Integer building,
			@RequestParam(value = "room", required = false) final Integer room) {
		return this.response.getTasks(floor, building, room, TransportTask.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.web.LocationsApi#locationsGetCleaningTask(java
	 * .lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ResponseEntity<List<CleaningTask>> locationsGetCleaningTask(
			@RequestParam(value = "floor", required = false) final Integer floor,
			@RequestParam(value = "building", required = false) final Integer building,
			@RequestParam(value = "room", required = false) final Integer room) {
		return this.response.getTasks(floor, building, room, CleaningTask.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.web.LocationsApi#locationsGetMaintainanceTasks
	 * (java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ResponseEntity<List<MaintainanceTask>> locationsGetMaintainanceTasks(
			@RequestParam(value = "floor", required = false) final Integer floor,
			@RequestParam(value = "building", required = false) final Integer building,
			@RequestParam(value = "room", required = false) final Integer room) {
		return this.response.getTasks(floor, building, room, MaintainanceTask.class);
	}

}

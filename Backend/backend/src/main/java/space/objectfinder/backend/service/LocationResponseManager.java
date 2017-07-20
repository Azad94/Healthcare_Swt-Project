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

import java.util.ArrayList;
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
import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.Task;
import space.objectfinder.backend.domain.TransportTask;
import space.objectfinder.backend.service.api.ResponseManagerForDaos;

/**
 * Führt nötige aktionen mit der datenbank aus um Objekte zu speichern und zu
 * lesen. Nur für {@link Location} objekte
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 * @see ResponseManagerForDaos
 */
@Component
public class LocationResponseManager implements ResponseManagerForDaos<Location, Long> {
	@Autowired
	LocationRepository repository;

	@Autowired
	TransportTaskRepository tRepository;

	@Autowired
	CleaningTaskRepository cRepository;

	@Autowired
	MaintainanceTaskRepository mRepository;

	@Autowired
	BeaconObjectRepository bRepository;

	private final static Logger LOGGER = LoggerFactory.getLogger(LocationResponseManager.class);

	@Override
	public ResponseEntity<List<Location>> get() {
		final List<Location> buffer = new LinkedList<>();
		this.repository.findAll().forEach(buffer::add);
		return new ResponseEntity<>(buffer, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Location> getId(final Long id) {
		LocationResponseManager.LOGGER.info("GET ID AUF:{} mit id:{}", this.getClass(), id);
		return this.optionalResponse(this.repository.findById(id));
	}

	@Override
	public ResponseEntity<Location> post(final Location object) {
		LocationResponseManager.LOGGER.info("POST AUF : {} mit body:{}", this.getClass(), object);
		return new ResponseEntity<>(this.repository.save(object), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> put(final Long id, final Location body) {
		LocationResponseManager.LOGGER.info("POST AUF : {} mit id: {}, body:{}", this.getClass(), id, body);

		final Optional<Location> bufferO = this.repository.findById(id);
		if (bufferO.isPresent()) {
			final Location buffer = bufferO.get();
			if (body.getBuilding() != null && !body.getBuilding().equals(buffer.getBuilding())) {
				buffer.setBuilding(body.getBuilding());
			}
			if (body.getFloor() != null && !body.getFloor().equals(buffer.getFloor())) {
				buffer.setFloor(body.getFloor());
			}
			if (body.getRoom() != null && !body.getRoom().equals(buffer.getRoom())) {
				buffer.setRoom(body.getRoom());
			}
			this.repository.save(body);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Erhalte alle {@link Location} mit filter
	 *
	 * @param building Nur von diesem gebäude
	 * @param floor Nur von diesem floor
	 * @param room Nur von diesem raum
	 * @return {@link List} von {@link Location}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	public ResponseEntity<List<Location>> getWithFilter(final Integer building, final Integer floor,
			final Integer room) {
		LocationResponseManager.LOGGER.info("Suche Location mit room:{} floor:{} building:{}", room, floor, building);
		return ResponseEntity.ok(this.getLocations(floor, building, room));

	}

	private List<Location> convert(final List<Location> list) {
		final List<Location> buffer = new ArrayList<>(list.size());
		list.forEach(buffer::add);
		return buffer;
	}

	/**
	 * Erhalte alle {@link Location} die folgende Kriterien erfüllen
	 *
	 * @param floor nur von diesem floor
	 * @param building nur von diesem building
	 * @param room nur von diesem raum
	 * @return {@link Location} als {@link List}
	 * @author Sven Marquardt, Chris Deter
	 * @since 21.06.2017
	 */
	private List<Location> getLocations(final Integer floor, final Integer building, final Integer room) {
		final List<Location> buffer = new ArrayList<>();
		if (building == null) {
			this.getByRoomAndMaybeFloor(floor, room, buffer);
		} else {
			this.getByBuildingAndMaybeRoomAndFloor(floor, building, room, buffer);
		}
		return buffer;
	}

	public void getByBuildingAndMaybeRoomAndFloor(final Integer floor, final Integer building, final Integer room,
			final List<Location> buffer) {
		if (floor == null) {
			this.getByBuildingAndMaybeRoom(building, room, buffer);
		} else {
			this.getByFloorBuildingAndMaybeRoom(floor, building, room, buffer);
		}
	}

	public void getByFloorBuildingAndMaybeRoom(final Integer floor, final Integer building, final Integer room,
			final List<Location> buffer) {
		if (room == null) {
			buffer.addAll(this.convert(this.repository.findByFloorAndBuilding(floor, building)));
		} else {
			buffer.addAll(this.convert(this.repository.findByFloorAndBuildingAndRoom(floor, building, room)));
		}
	}

	public void getByBuildingAndMaybeRoom(final Integer building, final Integer room, final List<Location> buffer) {
		if (room == null) {
			buffer.addAll(this.convert(this.repository.findByBuilding(building)));
		} else {
			buffer.addAll(this.convert(this.repository.findByRoomAndBuilding(room, building)));
		}
	}

	public void getByRoomAndMaybeFloor(final Integer floor, final Integer room, final List<Location> buffer) {
		if (floor == null) {
			buffer.addAll(this.convert(this.repository.findByRoom(room)));
		} else {
			buffer.addAll(this.convert(this.repository.findByRoomAndFloor(room, floor)));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.service.api.ResponseManagerForDaos#delete()
	 */
	@Override
	public ResponseEntity<Void> delete(final Long id) {
		LocationResponseManager.LOGGER.info("DELTE AUF:{} mit id:{}", this.getClass(), id);
		return this.deleteEntity(id, this.repository);
	}

	/**
	 * Erhalte alle tasks die diese an diesen Locations sind
	 *
	 * @param floor nur mit diesem floor
	 * @param building nur mit diesem building
	 * @param room nur mit diesem room
	 * @param taskType nur vom diesem type
	 * @param <T> Typ des Tasks
	 * @return {@link List} von {@link Task}
	 * @author Sven Marquardt
	 * @since 21.06.2017
	 */
	public <T> ResponseEntity<List<T>> getTasks(final Integer floor, final Integer building, final Integer room,
			final Class<T> taskType) {
		LocationResponseManager.LOGGER.info("Suche alle tasks an location {} {} {}", floor, building, room);
		final List<Location> buffer = this.getLocations(floor, building, room);
		final List<BeaconObject> beaconObjects = this.bRepository.findAllByLocation(buffer);
		final List<T> taskList = new LinkedList<>();
		final Consumer<Task> taskListConsumer = t -> {
			if (t.getClass().isAssignableFrom(taskType)) {
				final T tBuffer = taskType.cast(t);
				taskList.add(tBuffer);
			}
		};
		if (taskType.isAssignableFrom(CleaningTask.class)) {
			this.cRepository.findAllByBeaconObject(beaconObjects).forEach(taskListConsumer);
		} else if (taskType.isAssignableFrom(TransportTask.class)) {
			this.tRepository.findAllByBeaconObject(beaconObjects).forEach(taskListConsumer);
		} else if (taskType.isAssignableFrom(MaintainanceTask.class)) {
			this.mRepository.findAllByBeaconObject(beaconObjects).forEach(taskListConsumer);
		}
		return new ResponseEntity<>(taskList, HttpStatus.OK);

	}

}

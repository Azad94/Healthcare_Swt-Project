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
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.Service;
import space.objectfinder.backend.domain.TransportTask;
import space.objectfinder.backend.service.api.ResponseManagerForServices;

/**
 *
 * @author Sven Marquardt
 * @since 22.06.2017
 * @see ResponseManagerForServices
 *
 */
@Component
public class ServiceResponseManager implements ResponseManagerForServices {
	@Autowired
	BeaconObjectRepository repository;
	@Autowired
	TransportTaskRepository tRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceResponseManager.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.service.api.ResponseManagerForServices#
	 * getFreeTasks()
	 */
	@Override
	public ResponseEntity<List<TransportTask>> getFreeTasks(final Service service) {
		/*
		 * Durchsucht die datenbank nach freien beaconobjects. Wenn welche gefunden
		 * werden, werden diese neue tansporttasks zugewiesen und mit default werten
		 * bestückt
		 */
		ServiceResponseManager.LOGGER.info("Suche Tasks:{} mit user:{}", service, service.getCreator());
		final List<BeaconObject> freeBeaconObjects = this.repository.findAllByBeaconObjectType(service.getCallObject());
		ServiceResponseManager.LOGGER.info("{} freie BeaconObjects gefunden", freeBeaconObjects.size());
		final List<TransportTask> newTransportTasks = new LinkedList<>();
		freeBeaconObjects.stream().limit(service.getQuantitiy()).forEach(b -> {
			final TransportTask t = new TransportTask();
			t.targetLocation(service.getTargetLocation()).creationTime(LocalDateTime.now()).beaconObect(b)
					.creator(service.getCreator())
					.discription("Schiebe bett von " + b.getLocation() + "nach " + service.getTargetLocation()).level(1)
					.state(1);
			newTransportTasks.add(t);
			ServiceResponseManager.LOGGER.info("SPEICHERE NEUEN TASK:{}", t);
			this.tRepository.save(t);
		});
		ServiceResponseManager.LOGGER.info("{} neue Tasks erstellt", newTransportTasks.size());
		return new ResponseEntity<>(newTransportTasks, HttpStatus.OK);
	}

}

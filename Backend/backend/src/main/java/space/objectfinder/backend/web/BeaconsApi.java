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

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.domain.BeaconObject;

/**
 * Schnitstelle für {@link Beacon}
 * 
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
public interface BeaconsApi {
	/**
	 * Erhallte alle informationen eines {@link Beacon}
	 *
	 * @param beaconId
	 *            Id des Objektes
	 * @return {@link Beacon}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/beacons/{beaconId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Beacon> beaconsBeaconIdGet(@PathVariable("beaconId") String beaconId);

	/**
	 * Bearbeite einen {@link Beacon}
	 *
	 * @param beaconId
	 *            Id des {@link Beacon} das bearbeitet werden soll
	 * @param body
	 *            Die neuen werte für das Objekt
	 * @return {@link ResponseEntity} mit {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/beacons/{beaconId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<Void> beaconsBeaconIdPut(@PathVariable("beaconId") String beaconId, @RequestBody Beacon body);

	/**
	 * Erhallte alle {@link Beacon}
	 *
	 * @param noBeaconObject
	 *            Sollen nur {@link Beacon} geliefert werden die zu keinem
	 *            {@link BeaconObject} gehören
	 * @return {@link List} mit {@link Beacon}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/beacons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<Beacon>> beaconsGet(
			@RequestParam(value = "noBeaconObject", required = false, defaultValue = "false") boolean noBeaconObject);

	/**
	 * Erstelle einen neuen {@link Beacon}
	 *
	 * @param body
	 *            Infomrationen für einen neuen {@link Beacon}
	 * @return Den neuen {@link Beacon} mit generierter UUID
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/beacons", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<Beacon> beaconsPost(@RequestBody Beacon body);

	/**
	 * Lösche einen {@link Beacon}
	 *
	 * @param beaconId
	 *            Id des {@link Beacon}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/beacons/{beaconId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<Void> beaconsBeaconIdDelete(@PathVariable("beaconId") String beaconId);
}

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

import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.TransportTask;

/**
 * Schnittstelle für {@link BeaconObject}
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
public interface BeaconObjectsApi {
	/**
	 * Erhalte alle cleaningtasks die zu einem {@link BeaconObject} gehören
	 *
	 * @param beaconObjectId
	 *            Id des {@link BeaconObject}
	 * @return {@link List} mit {@link CleaningTask}
	 * @author Sven Marquardt
	 * @since 02.07.2017
	 */
	@RequestMapping(value = "/beaconObjects/{beaconObjectId}/cleaningtasks", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<CleaningTask>> beaconObjectsBeaconObjectIdCleaningtasksGet(
			@PathVariable("beaconObjectId") Long beaconObjectId);

	/**
	 * Erhalte alle informationen über einen {@link BeaconObject}
	 *
	 * @param beaconObjectId
	 *            Id des {@link BeaconObject}
	 * @return Das BeaconObject
	 * @author Sven Marquardt
	 * @since 02.07.2017
	 */
	@RequestMapping(value = "/beaconObjects/{beaconObjectId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<BeaconObject> beaconObjectsBeaconObjectIdGet(@PathVariable("beaconObjectId") Long beaconObjectId);

	/**
	 * Liste aller {@link MaintainanceTask} die zu diesem {@link BeaconObject}
	 * gehören
	 *
	 * @param beaconObjektId
	 *            Id des {@link BeaconObject}
	 * @return {@link List} von {@link MaintainanceTask}
	 * @author Sven Marquardt
	 * @since 02.07.2017
	 */
	@RequestMapping(value = "/beaconObjects/{beaconObjectId}/maintainancetasks", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<MaintainanceTask>> beaconObjectsBeaconObjectIdMaintainancetasksGet(
			@PathVariable("beaconObjektId") Long beaconObjektId);

	/**
	 * Bearbeite ein {@link BeaconObject}
	 *
	 * @param beaconObjectId
	 *            Id des {@link BeaconObject}
	 * @param body
	 *            Neuen werte für das {@link BeaconObject}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 02.07.2017
	 */
	@RequestMapping(value = "/beaconObjects/{beaconObjectId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> beaconObjectsBeaconObjectIdPut(@PathVariable("beaconObjectId") Long beaconObjectId,
			@RequestBody BeaconObject body);

	/**
	 * Liste aller {@link TransportTask} die zu diesem {@link BeaconObject} gehören
	 *
	 * @param beaconObjectId
	 *            Id des {@link BeaconObject}
	 * @return {@link List} aller {@link TransportTask}
	 * @author Sven Marquardt
	 * @since 02.07.2017
	 */
	@RequestMapping(value = "/beaconObjects/{beaconObjectId}/transporttasks", produces = {
			"application/json" }, consumes = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<TransportTask>> beaconObjectsBeaconObjectIdTransporttasksGet(
			@PathVariable("beaconObjectId") Long beaconObjectId);

	/**
	 * Liste aller {@link BeaconObject} in der Datenbank
	 *
	 * @return {@link List} aller {@link BeaconObject}
	 * @author Sven Marquardt
	 * @since 02.07.2017
	 */
	@RequestMapping(value = "/beaconObjects", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<BeaconObject>> beaconObjectsGet();

	/**
	 * Erstelle ein neues {@link BeaconObject} in der Datenbank
	 *
	 * @param body
	 *            Daten des neuen {@link BeaconObject}
	 * @return Das neu erstellte {@link BeaconObject} mit der der id aus der
	 *         Datenbank
	 * @author Sven Marquardt
	 * @since 02.07.2017
	 */
	@RequestMapping(value = "/beaconObjects", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<BeaconObject> beaconObjectsPost(@RequestBody BeaconObject body);

	/**
	 * Lösche ein {@link BeaconObject} aus der Datenbank
	 *
	 * @param beaconObjectId
	 *            Id des {@link BeaconObject}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 02.07.2017
	 */
	@RequestMapping(value = "/beaconObjects/{beaconObjectId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> beaconObjectsBeaconObjectIdDelete(@PathVariable("beaconObjectId") Long beaconObjectId);

}

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.TransportTask;
import space.objectfinder.backend.service.BeaconObjectResponseManager;

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@Controller
public class BeaconObjectsApiController implements BeaconObjectsApi {
	@Autowired
	BeaconObjectResponseManager response;

	@Override
	public ResponseEntity<BeaconObject> beaconObjectsBeaconObjectIdGet(
			@PathVariable("beaconObjectId") final Long beaconObjectId) {
		return this.response.getId(beaconObjectId);
	}

	@Override
	public ResponseEntity<Void> beaconObjectsBeaconObjectIdPut(
			@PathVariable("beaconObjectId") final Long beaconObjectId, @RequestBody final BeaconObject body) {
		return this.response.put(beaconObjectId, body);
	}

	@Override
	public ResponseEntity<List<BeaconObject>> beaconObjectsGet() {
		return this.response.get();
	}

	@Override
	public ResponseEntity<BeaconObject> beaconObjectsPost(@RequestBody final BeaconObject body) {
		return this.response.post(body);
	}

	@Override
	public ResponseEntity<List<CleaningTask>> beaconObjectsBeaconObjectIdCleaningtasksGet(
			@PathVariable("beaconObjectId") final Long beaconObjectId) {
		return this.response.getTaskToThisBeacon(beaconObjectId, CleaningTask.class);
	}

	@Override
	public ResponseEntity<List<MaintainanceTask>> beaconObjectsBeaconObjectIdMaintainancetasksGet(
			@PathVariable("beaconObjectId") final Long beaconObjektId) {
		return this.response.getTaskToThisBeacon(beaconObjektId, MaintainanceTask.class);
	}

	@Override
	public ResponseEntity<List<TransportTask>> beaconObjectsBeaconObjectIdTransporttasksGet(
			@PathVariable("beaconObjectId") final Long beaconObjectId) {
		return this.response.getTaskToThisBeacon(beaconObjectId, TransportTask.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.BeaconObjectsApi#
	 * beaconObjectsBeaconObjectIdDelete(java.lang.Long,
	 * space.objectfinder.backend.domain.BeaconObject)
	 */
	@Override
	public ResponseEntity<Void> beaconObjectsBeaconObjectIdDelete(
			@PathVariable("beaconObjectId") final Long beaconObjectId) {
		return this.response.delete(beaconObjectId);
	}

}

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
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.service.BeaconResponseManager;

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@Controller
public class BeaconsApiController implements BeaconsApi {
	@Autowired
	BeaconResponseManager response;

	private static final Logger LOGGER = LoggerFactory.getLogger(BeaconsApiController.class);

	@Override
	public ResponseEntity<Beacon> beaconsBeaconIdGet(@PathVariable("beaconId") final String beaconId) {
		return this.response.getId(beaconId);

	}

	@Override
	public ResponseEntity<Void> beaconsBeaconIdPut(@PathVariable("beaconId") final String beaconId,
			@RequestBody final Beacon body) {
		return this.response.put(beaconId, body);
	}

	@Override
	public ResponseEntity<List<Beacon>> beaconsGet(
			@RequestParam(value = "noBeaconObject", required = false, defaultValue = "false") final boolean noBeaconObject) {
		if (!noBeaconObject) {
			return this.response.get();
		}
		try {
			return this.response.getFreeBeacons();
		} catch (InterruptedException | ExecutionException e) {
			BeaconsApiController.LOGGER.error("Error beim versuch freie beacons zu finden", e);
		}
		return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

	}

	@Override
	public ResponseEntity<Beacon> beaconsPost(@RequestBody final Beacon body) {
		return this.response.post(body);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.web.BeaconsApi#beaconsBeaconIdDelete(java.lang
	 * .String, space.objectfinder.backend.domain.Beacon)
	 */
	@Override
	public ResponseEntity<Void> beaconsBeaconIdDelete(@PathVariable("beaconId") final String beaconId) {
		return this.response.delete(beaconId);
	}

}

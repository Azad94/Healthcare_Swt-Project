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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import space.objectfinder.backend.domain.Service;
import space.objectfinder.backend.domain.TransportTask;

/**
 * Schnittstelle für {@link Service}
 *
 * @author Sven Marquardt
 * @since 22.06.2017
 */
public interface ServiceApi {
	/**
	 * Erfrage neue {@link TransportTask}
	 *
	 * Je nach {@link Service} werden die anzahl der {@link TransportTask} und
	 * dessen ziel bestimmt
	 *
	 * @param body Informationen für die anfrage
	 * @return {@link List} an {@link TransportTask}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/service", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<TransportTask>> getService(@RequestBody Service body);

}

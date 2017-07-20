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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import space.objectfinder.backend.domain.Service;
import space.objectfinder.backend.domain.TransportTask;
import space.objectfinder.backend.service.ServiceResponseManager;

/**
 * @author Sven Marquardt
 * @since 22.06.2017
 */
@Controller
public class ServiceApiController implements ServiceApi {
	@Autowired
	ServiceResponseManager response;
	private final static Logger LOGGER = LoggerFactory.getLogger(ServiceApiController.class);
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.web.ServiceApi#getService(space.objectfinder.
	 * backend.domain.Service)
	 */
	@Override
	public ResponseEntity<List<TransportTask>> getService(@RequestBody final Service body) {
		LOGGER.info("Anfrage service mit {} user",body.getCreator());
		return this.response.getFreeTasks(body);
	}

}

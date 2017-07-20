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

import space.objectfinder.backend.domain.SubTaskSlider;
import space.objectfinder.backend.service.SubTaskResponseManager;

/**
 *
 * @author Sven Marquardt
 * @since 24.06.2017
 */
@Controller
public class SubTaskSliderController implements SubTaskSliderApi {
	@Autowired
	SubTaskResponseManager<SubTaskSlider> response;

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.SubTaskSliderApi#subTaskPost(space.
	 * objectfinder.backend.domain.SubTaskSlider, java.lang.Long)
	 */
	@Override
	public ResponseEntity<SubTaskSlider> subTaskPost(@RequestBody final SubTaskSlider body,
			@PathVariable("maintainancetaskId") final Long taskId) {
		return this.response.post(body, taskId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.SubTaskSliderApi#subTaskIdGet(java.lang.
	 * Long, java.lang.Long)
	 */
	@Override
	public ResponseEntity<SubTaskSlider> subTaskIdGet(@PathVariable("subtaskId") final Long subtaskId,
			@PathVariable("maintainancetaskId") final Long taskId) {
		return this.response.getId(subtaskId, taskId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.SubTaskSliderApi#subTaskIdPut(java.lang.
	 * Long, space.objectfinder.backend.domain.SubTaskSlider, java.lang.Long)
	 */
	@Override
	public ResponseEntity<Void> subTaskIdPut(@PathVariable("subtaskId") final Long subtaskId,
			@RequestBody final SubTaskSlider body, @PathVariable("maintainancetaskId") final Long taskId) {
		return this.response.put(subtaskId, body, SubTaskSlider.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.web.SubTaskSliderApi#subTaskIdDelete(java.lang
	 * .Long)
	 */
	@Override
	public ResponseEntity<Void> subTaskIdDelete(@PathVariable("subtaskId") final Long subtaskId,
			@PathVariable("maintainancetaskId") Long mId) {
		return this.response.delete(subtaskId, mId);
	}

	@Override
	public ResponseEntity<List<SubTaskSlider>> subTaskIdGetAll(@PathVariable("maintainancetaskId") final Long taskId) {
		return this.response.getAll(taskId, SubTaskSlider.class);
	}

}

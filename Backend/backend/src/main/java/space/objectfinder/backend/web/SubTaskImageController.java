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

import space.objectfinder.backend.domain.SubTaskImage;
import space.objectfinder.backend.service.SubTaskResponseManager;

/**
 * @author Sven Marquardt
 * @since 19.06.2017
 */
@Controller
public class SubTaskImageController implements SubTaskImageApi {
	@Autowired
	SubTaskResponseManager<SubTaskImage> response;

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.SubTaskImageApi#rolesPost(space.
	 * objectfinder. backend.domain.SubTaskImage)
	 */
	@Override
	public ResponseEntity<SubTaskImage> subTaskPost(@RequestBody final SubTaskImage body,
			@PathVariable("maintainancetaskId") final Long taskId) {
		return this.response.post(body, taskId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.SubTaskImageApi#rolesRoleIdGet(java.lang.
	 * Long)
	 */
	@Override
	public ResponseEntity<SubTaskImage> subTaskIdGet(@PathVariable("subtaskId") final Long subtaskId,
			@PathVariable("maintainancetaskId") final Long taskId) {
		return this.response.getId(subtaskId, taskId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.SubTaskImageApi#rolesRoleIdPut(java.lang.
	 * Long, space.objectfinder.backend.domain.SubTaskImage)
	 */
	@Override
	public ResponseEntity<Void> subTaskIdPut(@PathVariable("subtaskId") final Long subtaskId,
			@RequestBody final SubTaskImage body, @PathVariable("maintainancetaskId") final Long taskId) {
		return this.response.put(subtaskId, body, SubTaskImage.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.SubTaskImageApi#rolesRoleIdDelete(java.
	 * lang. Long, space.objectfinder.backend.domain.SubTaskImage)
	 */
	@Override
	public ResponseEntity<Void> subTaskIdDelete(@PathVariable("subtaskId") final Long subtaskId,
			@PathVariable("maintainancetaskId") Long mId) {
		return this.response.delete(subtaskId, mId);
	}

	@Override
	public ResponseEntity<List<SubTaskImage>> subTaskIdGetAll(@PathVariable("maintainancetaskId") final Long taskId) {
		return this.response.getAll(taskId, SubTaskImage.class);
	}

}

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

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.SyncBody;
import space.objectfinder.backend.service.MaintainanceTaskResponseManager;

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@RestController
public class MaintainancetasksApiController implements MaintainancetasksApi {
	@Autowired
	MaintainanceTaskResponseManager response;

	@Override
	public ResponseEntity<List<MaintainanceTask>> maintainancetasksGet(@RequestBody final SyncBody body,
			@RequestParam(value = "limit", required = false, defaultValue = "20") final Integer limit,
			@RequestParam(value = "before", required = false, defaultValue = "") final String before,
			@RequestParam(value = "since", required = false, defaultValue = "") final String since,
			@RequestParam(value = "open", required = false, defaultValue = "true") final Boolean open,
			@RequestParam(value = "closed", required = false, defaultValue = "true") final Boolean closed,
			@RequestParam(value = "processing", required = false, defaultValue = "true") final Boolean processing,
			@RequestParam(value = "own", required = false, defaultValue = "false") final Boolean own,
			@RequestParam(value = "building", required = false) final Integer building) {
		return this.response.tasksGet(limit, before, since, open, closed, processing, own, body, building);
	}

	@Override
	public ResponseEntity<MaintainanceTask> maintainancetasksPost(@RequestBody final MaintainanceTask body) {
		return this.response.postTask(body);
	}

	@Override
	public ResponseEntity<Long> maintainancetasksSizeGet(
			@NotNull @RequestParam(value = "newTasks", required = true) final Boolean newTasks,
			@RequestBody final SyncBody userName) {
		return this.response.taskGetSize(userName);

	}

	@Override
	public ResponseEntity<MaintainanceTask> maintainancetasksTaskidGet(@PathVariable("taskid") final Long taskid) {
		return this.response.taskGetId(taskid);
	}

	@Override
	public ResponseEntity<Void> maintainancetasksTaskidPut(@PathVariable("taskid") final Long taskid,
			@RequestBody final MaintainanceTask body) {
		return this.response.taskIdPut(taskid, body);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.MaintainancetasksApi#
	 * maintainancetasksTaskidDelete(java.lang.Long,
	 * space.objectfinder.backend.domain.MaintainanceTask)
	 */
	@Override
	public ResponseEntity<Void> maintainancetasksTaskidDelete(@PathVariable("taskid") final Long taskid) {
		return this.response.delete(taskid);
	}

	@Override
	public ResponseEntity<List<MaintainanceTask>> maintainancetasksAppGet(@RequestBody final SyncBody body,
			@RequestParam(value = "limit", required = false, defaultValue = "20") final Integer limit,
			@RequestParam(value = "before", required = false, defaultValue = "") final String before,
			@RequestParam(value = "since", required = false, defaultValue = "") final String since,
			@RequestParam(value = "open", required = false, defaultValue = "true") final Boolean open,
			@RequestParam(value = "closed", required = false, defaultValue = "true") final Boolean closed,
			@RequestParam(value = "processing", required = false, defaultValue = "true") final Boolean processing,
			@RequestParam(value = "own", required = false, defaultValue = "false") final Boolean own,
			@RequestParam(value = "building", required = false) final Integer building) {
		return this.response.tasksGet(limit, before, since, open, closed, processing, own, body, building);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.MaintainancetasksApi#
	 * maintainancetasksAppSizeGet(java.lang.Boolean, java.lang.String)
	 */
	@Override
	public ResponseEntity<Long> maintainancetasksAppSizeGet(
			@NotNull @RequestParam(value = "newTasks", required = true) final Boolean newTasks,
			@RequestBody final SyncBody userName) {
		return this.response.taskGetSize(userName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.objectfinder.backend.web.MaintainancetasksApi#
	 * maintainancetasksTaskPut(java.util.List)
	 */
	@Override
	public ResponseEntity<Void> maintainancetasksTaskPut(@RequestBody final List<MaintainanceTask> body) {
		body.forEach(c -> this.response.taskIdPut(c.getId(), c));
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}

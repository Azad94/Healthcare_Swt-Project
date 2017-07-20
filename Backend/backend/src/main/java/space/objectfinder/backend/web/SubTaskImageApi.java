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

import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.SubTaskImage;

/**
 * Schnittstelle zu {@link SubTaskImage}
 *
 * @author Sven Marquardt
 * @since 19.06.2017
 */
public interface SubTaskImageApi {
	/**
	 * Erstelle ein neuen {@link SubTaskImage}
	 *
	 * @param body Informationen für {@link SubTaskImage}
	 * @param taskId id einer vorhandenen {@link MaintainancetasksApi}
	 * @return Neu erstellte {@link SubTaskImage}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksimage", produces = {
			"application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<SubTaskImage> subTaskPost(@RequestBody SubTaskImage body,
			@PathVariable("maintainancetaskId") Long taskId);

	/**
	 * Erhalte alle infos eines einzelnen {@link SubTaskImage}
	 *
	 * @param subtaskId Id der {@link SubTaskImage}
	 * @param taskId id der {@link MaintainanceTask}
	 * @return {@link SubTaskImage}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksimage/{subtaskId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<SubTaskImage> subTaskIdGet(@PathVariable("subtaskId") Long subtaskId,
			@PathVariable("maintainancetaskId") Long taskId);

	/**
	 * Bearbeite eine einzelne {@link SubTaskImage}
	 *
	 * @param subtaskId Id der {@link SubTaskImage}
	 * @param body neue informationen
	 * @param taskId id der {@link MaintainanceTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksimage/{subtaskId}", produces = {
			"application/json" }, consumes = { "application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> subTaskIdPut(@PathVariable("subtaskId") Long subtaskId, @RequestBody SubTaskImage body,
			@PathVariable("maintainancetaskId") Long taskId);

	/**
	 * Löscht einen {@link SubTaskImage}
	 *
	 * @param subtaskId Id der {@link SubTaskImage}
	 * @param mId Id der {@link MaintainanceTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksimage/{subtaskId}", produces = {
			"application/json" }, consumes = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> subTaskIdDelete(@PathVariable("subtaskId") Long subtaskId,
			@PathVariable("maintainancetaskId") Long mId);

	/**
	 * Erhalte alle {@link SubTaskImage} einer {@link MaintainanceTask}
	 *
	 * @param taskId Id der {@link MaintainanceTask}
	 * @return {@link List} mit {@link SubTaskImage}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksimage", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<SubTaskImage>> subTaskIdGetAll(@PathVariable("maintainancetaskId") Long taskId);
}

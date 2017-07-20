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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.SubTaskSlider;

/**
 * Definiert die web operationen, die auf einem {@link SubTaskSlider} angewand
 * werden können.
 *
 * @author Sven Marquardt
 * @since 24.06.2017
 */
public interface SubTaskSliderApi {
	/**
	 * Erstelle einen neuen {@link SubTaskSlider} im backend
	 *
	 * @param body Das neue Objekt
	 * @param taskId {@link MaintainanceTask#getId()} um den {@link SubTaskSlider}
	 *            einem task zuordnen zu können
	 * @return {@link ResponseEntity} mit dem neuen objekt als body oder
	 *         {@link HttpStatus#NOT_FOUND}
	 * @author Sven Marquardt
	 * @since 01.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksslider", produces = {
			"application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<SubTaskSlider> subTaskPost(@RequestBody SubTaskSlider body,
			@PathVariable("maintainancetaskId") Long taskId);

	/**
	 * Erhalte einen bestimmten {@link SubTaskSlider}
	 *
	 * @param subtaskId Id des {@link SubTaskSlider} die gesucht wird
	 * @param taskId Id des {@link MaintainanceTask}
	 * @return Das gesuchte objekt oder {@link HttpStatus#NOT_FOUND} wenn dieser
	 *         nicht gefunden werden konnte
	 * @author Sven Marquardt
	 * @since 01.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksslider/{subtaskId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<SubTaskSlider> subTaskIdGet(@PathVariable("subtaskId") Long subtaskId,
			@PathVariable("maintainancetaskId") Long taskId);

	/**
	 * Bearbeite eine einzelne {@link SubTaskSlider}
	 *
	 * @param subtaskId Id der {@link SubTaskSlider}
	 * @param body neue informationen
	 * @param taskId id der {@link MaintainanceTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksslider/{subtaskId}", produces = {
			"application/json" }, consumes = { "application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> subTaskIdPut(@PathVariable("subtaskId") Long subtaskId, @RequestBody SubTaskSlider body,
			@PathVariable("maintainancetaskId") Long taskId);

	/**
	 * Löscht einen {@link SubTaskSlider}
	 *
	 * @param subtaskId Id der {@link SubTaskSlider}
	 * @param mId Id der {@link MaintainanceTask}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksslider/{subtaskId}", produces = {
			"application/json" }, consumes = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> subTaskIdDelete(@PathVariable("subtaskId") Long subtaskId,
			@PathVariable("maintainancetaskId") Long mId);

	/**
	 * Erhalte alle {@link SubTaskSlider} einer {@link MaintainanceTask}
	 *
	 * @param taskId Id der {@link MaintainanceTask}
	 * @return {@link List} mit {@link SubTaskSlider}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/maintainancetasks/{maintainancetaskId}/subtasksslider", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<SubTaskSlider>> subTaskIdGetAll(@PathVariable("maintainancetaskId") Long taskId);
}

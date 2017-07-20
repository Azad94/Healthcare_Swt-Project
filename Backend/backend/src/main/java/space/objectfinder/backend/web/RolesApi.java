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

import space.objectfinder.backend.domain.Role;

/**
 * Schnittstelle zu {@link Role}
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
public interface RolesApi {
	/**
	 * Erhalte alle {@link Role}
	 *
	 * @return {@link List} von {@link Role}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/roles", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<Role>> rolesGet();

	/**
	 * Erstelle eine neue {@link Role}
	 *
	 * @param body Informatioen für die neue {@link Role}
	 * @return Die neu erstellte {@link Role}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/roles", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<Role> rolesPost(@RequestBody Role body);

	/**
	 * Erhalte alle infos einer {@link Role}
	 *
	 * @param roleId Die id der {@link Role}
	 * @return {@link Role}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/roles/{roleId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<Role> rolesRoleIdGet(@PathVariable("roleId") Long roleId);

	/**
	 * Bearbeite eine einzelne {@link Role}
	 *
	 * @param roleId Id der {@link Role}
	 * @param body Neue informationen der {@link Role}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/roles/{roleId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> rolesRoleIdPut(@PathVariable("roleId") Long roleId, @RequestBody Role body);

	/**
	 * Lösche eine einzelne {@link Role}
	 * 
	 * @param roleId Id der {@link Role}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/roles/{roleId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> rolesRoleIdDelete(@PathVariable("roleId") Long roleId);

}

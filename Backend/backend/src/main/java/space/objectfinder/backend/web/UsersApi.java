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

import space.objectfinder.backend.domain.User;

/**
 * Schnittstelle zu {@link User}
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
public interface UsersApi {
	/**
	 * Erhalte alle {@link User}
	 * 
	 * @return {@link List} mit {@link User}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/users", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<User>> usersGet();

	/**
	 * Erstelle einen neuen {@link User}
	 *
	 * @param body Die infos für den neuen {@link User}
	 * @return Den neu erstellten {@link User}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/users", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<User> usersPost(@RequestBody User body);

	/**
	 * Erhalte alle infos eines einzelnen {@link User}
	 *
	 * @param userId Id des {@link User}
	 * @return {@link User}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/users/{userId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<User> usersUserIdGet(@PathVariable("userId") Long userId);

	/**
	 * Bearbeite einen einzelnen {@link User}
	 *
	 * @param userId Id des {@link User}
	 * @param body Neue Informationen
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/users/{userId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> usersUserIdPut(@PathVariable("userId") Long userId, @RequestBody User body);

	/**
	 * Lösche einen {@link User}
	 *
	 * @param userId Id des {@link User}
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	@RequestMapping(value = "/users/{userId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> usersUserIdDelete(@PathVariable("userId") Long userId);

}

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
package space.objectfinder.backend.service.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import space.objectfinder.backend.domain.User;

/**
 * Responses für logins.
 * 
 * @author "Sven Marquardt"
 * @since 01.07.2017
 */
public interface ResponseManagerForLogin extends ResponseManager {

	/**
	 * Einen Login durchführen
	 *
	 * Gibt bei erfolg den {@link User} zurück wenn nicht ein
	 * {@link HttpStatus#UNAUTHORIZED} Wenn {@link User} nicht gefunden werden
	 * konnte gibt es ein {@link HttpStatus#NOT_FOUND} zurück
	 *
	 * @param userName
	 *            Username des {@link User}
	 * @param password
	 *            Passwort des {@link User}
	 * @return
	 *
	 * @author Sven Marquardt, Chris Deter, Arne Salveter
	 * @since 01.05.2017
	 */
	ResponseEntity<User> post(String userName, String password);

}

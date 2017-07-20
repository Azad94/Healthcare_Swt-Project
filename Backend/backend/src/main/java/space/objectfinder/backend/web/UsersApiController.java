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

import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.UserResponseManager;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-14T21:10:24.369+02:00")

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@Controller
public class UsersApiController implements UsersApi {
	@Autowired
	UserResponseManager resposne;

	@Override
	public ResponseEntity<List<User>> usersGet() {
		return resposne.get();
	}

	@Override
	public ResponseEntity<User> usersPost(@RequestBody final User body) {
		return resposne.post(body);
	}

	@Override
	public ResponseEntity<User> usersUserIdGet(@PathVariable("userId") final Long userId) {
		return resposne.getId(userId);
	}

	@Override
	public ResponseEntity<Void> usersUserIdPut(@PathVariable("userId") final Long userId,
			@RequestBody final User body) {
		return resposne.put(userId, body);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.web.UsersApi#usersUserIdDelete(java.lang.Long,
	 * space.objectfinder.backend.domain.User)
	 */
	@Override
	public ResponseEntity<Void> usersUserIdDelete(@PathVariable("userId") final Long userId) {
		return resposne.delete(userId);
	}

}

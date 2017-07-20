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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.service.RoleResponseManager;

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@RestController
public class RolesApiController implements RolesApi {
	@Autowired
	RoleResponseManager response;

	@Override
	public ResponseEntity<List<Role>> rolesGet() {
		return response.get();
	}

	@Override
	public ResponseEntity<Role> rolesPost(@RequestBody final Role body) {
		return response.post(body);
	}

	@Override
	public ResponseEntity<Role> rolesRoleIdGet(@PathVariable("roleId") final Long roleId) {
		return response.getId(roleId);
	}

	@Override
	public ResponseEntity<Void> rolesRoleIdPut(@PathVariable("roleId") final Long roleId,
			@RequestBody final Role body) {
		return response.put(roleId, body);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * space.objectfinder.backend.web.RolesApi#rolesRoleIdDelete(java.lang.Long,
	 * space.objectfinder.backend.domain.Role)
	 */
	@Override
	public ResponseEntity<Void> rolesRoleIdDelete(@PathVariable("roleId") final Long roleId) {
		return response.delete(roleId);
	}

}

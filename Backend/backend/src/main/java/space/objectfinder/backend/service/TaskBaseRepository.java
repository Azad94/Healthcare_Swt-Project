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
package space.objectfinder.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.Task;

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@NoRepositoryBean
public interface TaskBaseRepository<T extends Task> extends CrudRepository<T, Long> {
	List<T> findByAcceptedTime(LocalDateTime acceptedTime);

	List<T> findByClosedTime(LocalDateTime closedTime);

	List<T> findByCreationTime(LocalDateTime creationTime);

	Optional<T> findById(Long id);

	List<T> findAllByBeaconObject(List<BeaconObject> beaconObjects);

	List<T> findAllByBeaconObject(BeaconObject beaconObject);

}

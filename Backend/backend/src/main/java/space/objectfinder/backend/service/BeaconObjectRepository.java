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

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.Location;

/**
 * Repository des {@link BeaconObject}. Verbindung zur Datenbank für
 * {@link BeaconObject}
 * 
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 *
 */
@Repository
public interface BeaconObjectRepository extends CrudRepository<BeaconObject, Long>, JpaRepository<BeaconObject, Long> {
	Optional<BeaconObject> findById(Long id);

	List<BeaconObject> findAllByLocation(List<Location> locations);

	@Query("select b from BeaconObject b  where b.beaconObjectType = :type and b.id NOT IN ( SELECT t.beaconObject.id from Task t WHERE t.state = 1 OR t.state = 2)")
	List<BeaconObject> findAllByBeaconObjectType(@Param("type") String type);

}

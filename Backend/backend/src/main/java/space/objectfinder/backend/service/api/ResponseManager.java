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

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Operationen die mit jedem Objekt ausgeführt werden kann, die zur jeweiligen
 * aktion auch ein {@link ResponseEntity} zurück gibt. Alle anderen
 * ResponseManager sollten von dieser extenden um auf diese Methoden zugreifen
 * zu können.
 *
 * @author Sven Marquardt
 * @since 01.05.2017
 */
public interface ResponseManager {

	/**
	 * Wenn das Objekt null ist wird {@link HttpStatus#NOT_FOUND} als status
	 * angegeben ansonsten das Objekt als Response
	 *
	 * @param object
	 *            Welches überprüft und gegebenenfalls im response body liegt
	 * @return {@link ResponseEntity}
	 */
	default <T> ResponseEntity<T> optionalResponse(final Optional<T> object) {
		if (object.isPresent()) {
			return ResponseEntity.ok(object.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Liefert das objekt als einfaches {@link ResponseEntity} mit
	 * {@link HttpStatus#OK} zurück
	 *
	 * @param t
	 *            Objekt das im body der response vorkommt
	 * @return {@link ResponseEntity}
	 * @author "Sven Marquardt"
	 * @since 30.06.2017
	 */
	default <T> ResponseEntity<T> response(final T t) {
		return ResponseEntity.ok(t);
	}

	/**
	 * Liefert die Liste als einfaches {@link ResponseEntity} mit
	 * {@link HttpStatus#OK} zurück
	 *
	 * @param t
	 *            Liste die in dem Body der response gespeichert wird
	 * @return {@link ResponseEntity} mit {@link List} und {@link HttpStatus#OK}
	 * @author "Sven Marquardt"
	 * @since 30.06.2017
	 */
	default <T> ResponseEntity<List<T>> response(final List<T> t) {
		return ResponseEntity.ok(t);
	}

	/**
	 * Löscht ein objekt aus dem gegebenen repository
	 *
	 * @param id
	 *            Id des objektes das gelöscht wird
	 * @param repository
	 *            Das repository in dem das Objekt gesucht und gelöscht wird
	 * @return {@link ResponseEntity} mit {@link HttpStatus#OK}
	 * @author "Sven Marquardt"
	 * @since 30.06.2017
	 */
	default <T, S extends Serializable> ResponseEntity<Void> deleteEntity(final S id,
			final CrudRepository<T, S> repository) {
		repository.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

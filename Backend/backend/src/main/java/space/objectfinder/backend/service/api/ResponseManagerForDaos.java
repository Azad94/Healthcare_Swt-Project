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

import org.springframework.http.ResponseEntity;

/**
 * Einfache crud operationen für Objekte
 *
 * @author Sven Marquardt
 * @since 01.07.2017
 * @param <T> Type des Objektes
 * @param <S> Type der ids
 */
public interface ResponseManagerForDaos<T, S extends Serializable> extends ResponseManager {

	/**
	 * Lösche ein Dao objekt
	 *
	 * @param id Id des Objects
	 * @return {@link Void}
	 * @author Sven Marquardt
	 * @since 21.06.2017
	 */
	public ResponseEntity<Void> delete(S id);

	/**
	 * Eine Liste aller Objekte
	 *
	 * @return {@link List} von T
	 */
	public ResponseEntity<List<T>> get();

	/**
	 * Erhalte einzelnes Objekt
	 *
	 * @param id id des objektes
	 * @return T das Objekt
	 */
	public ResponseEntity<T> getId(S id);

	/**
	 * Bearbeite ein Objekt
	 *
	 * @param object Neues Objekt das erstellt wird
	 * @return T neu erstelltes Objekt
	 */
	public ResponseEntity<T> post(T object);

	/**
	 * Speichere ein Object in der Datenbank
	 *
	 * @param object Object informationen
	 * @param id id des objektes
	 * @return {@link Void}
	 */
	public ResponseEntity<Void> put(final S id, T object);

}

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

import java.util.List;

import org.springframework.http.ResponseEntity;

import space.objectfinder.backend.domain.AbstractSubTask;
import space.objectfinder.backend.domain.MaintainanceTask;

/**
 * methoden um crud operationen auf {@link AbstractSubTask} aus zu führen
 * 
 * @author Sven Marquardt
 * @since 22.06.2017
 */
public interface ResponseManagerForSubTasks<T extends AbstractSubTask> extends ResponseManager {

	/**
	 * Löscht eine subtask
	 *
	 * @param id
	 *            Id des subtasks
	 * @param mId
	 *            id des {@link MaintainanceTask}
	 * @return
	 * @author "Sven Marquardt"
	 * @since 29.06.2017
	 */
	public ResponseEntity<Void> delete(Long id, Long mId);

	/**
	 * Erhalte einzelnes Objekt
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity<T> getId(Long id, Long taskId);

	/**
	 * Alle task eines subtaskstype
	 *
	 * @param taskId
	 *            Maintainancetask id
	 * @param clazz
	 *            Welchen typ wird gefragt
	 * @return {@link List} von Subtasks
	 * @author "Sven Marquardt"
	 * @since 29.06.2017
	 */
	public ResponseEntity<List<T>> getAll(Long taskId, Class<T> clazz);

	/**
	 * Bearbeite ein Objekt
	 *
	 * @param taskId
	 *            Id des {@link MaintainanceTask}
	 * @param object
	 *            Zu speicherndes objekt
	 * @return
	 * @author "Sven Marquardt"
	 * @since 30.06.2017
	 */
	public ResponseEntity<T> post(T object, Long taskId);

	/**
	 * Ändere ein objekt in der Datenbank
	 *
	 * @param object
	 *            Das objekt das zu speichern ist
	 * @param id
	 *            Id des objekts
	 * @return
	 */
	public ResponseEntity<Void> put(final Long id, T object, Class<T> clazz);

	/**
	 *
	 * @param t
	 * @return
	 * @author "Sven Marquardt"
	 * @since 30.06.2017
	 * @see ResponseManager#response(Object)
	 */
	default ResponseEntity<T> response(final T t) {
		return this.response(t);
	}

}

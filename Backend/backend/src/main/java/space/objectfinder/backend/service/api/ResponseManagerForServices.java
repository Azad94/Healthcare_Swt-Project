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

import space.objectfinder.backend.domain.Service;
import space.objectfinder.backend.domain.TransportTask;

/**
 * Services sind angefragte {@link TransportTask} die automatisch generiert
 * werden.
 *
 * @author Sven Marquardt
 * @since 22.06.2017
 */
public interface ResponseManagerForServices {
	/**
	 * Erhalte neue {@link TransportTask} für eine Service anfrage
	 *
	 * Generiert neue {@link TransportTask} wie in {@link Service} beschrieben.
	 * Versucht dabei die kriterien in {@link Service} zu beachten.
	 *
	 * @param service
	 *            Kriterien für die neuen {@link TransportTask}
	 * @return Eine {@link List} der erstellten {@link TransportTask}. Kann leer
	 *         sein
	 * @author Sven Marquardt
	 * @since 25.06.2017
	 */
	public ResponseEntity<List<TransportTask>> getFreeTasks(Service service);

}

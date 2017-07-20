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
package space.objectfinder.backend;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * In dieser Util Klasse sind einige allgemein benötigte Zeitfunktionen.
 *
 * @author Sven Marquardt, Chris Deter
 * @since 01.05.2017
 */
public class Util {
	public final static DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
	public static final ZonedDateTime zoneId = ZonedDateTime.now();

	/**
	 * Diese Methode wandelt ein ZonedDateTimeObjekt in einen String für REST um.
	 *
	 * @param time ein ZonedDateTime Objekt welches umgewandelt werden soll
	 * @return einen Zeitstring
	 */
	public static String dateTimeToIsoString(final ZonedDateTime time) {
		return time.toString();
	}

	/**
	 * Diese Methode wandelt einen String in ein LocalDateTime Objekt um
	 * 
	 * @param time einen String mit der Zeit
	 * @return ein LocalDateTime Objekt welches dem String entspricht
	 */
	public static LocalDateTime stringToLoalDateTime(final String time) {
		return LocalDateTime.from(Util.formatter.parse(time));
	}

	private Util() {
	}
}

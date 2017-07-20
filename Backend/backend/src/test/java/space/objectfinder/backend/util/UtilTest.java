/*******************************************************************************
 * Copyright 2017 Chris Deter, Arne Salveter, Sven Marquardt
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
package space.objectfinder.backend.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import space.objectfinder.backend.Util;

/**
 * @author Chris Deter
 * @since 20.06.2017
 */
@Ignore
public class UtilTest {

	@Test
	public void testTime() {
		final String targetTime = "2017-06-22T15:48:30.755+02:00";
		final String targetTimeZoned = "2017-06-22T15:48:30.755+02:00[Europe/Berlin]";
		final ZonedDateTime zdt = ZonedDateTime.now();
		final LocalDateTime ltd = Util.stringToLoalDateTime(targetTime);
		System.out.println(Util.dateTimeToIsoString(ltd.atZone(zdt.getZone())));
		Assert.assertTrue(targetTimeZoned.equals(Util.dateTimeToIsoString(ltd.atZone(zdt.getZone()))));
	}
}

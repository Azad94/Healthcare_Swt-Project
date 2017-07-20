/**
 * /*******************************************************************************
 * Copyright 2017 Jonas Grams
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
 ******************************************************************************
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HealthcareApp.Converters
{
    /// <summary>
    /// Converts DateTime to ISO 8601 and vise versa
    /// </summary>
    class DateConverter
    {
        /// <summary>
        /// Converts DateTime to ISO 8601
        /// </summary>
        /// <param name="date">DateTime</param>
        /// <returns>Time as ISO 8601 String</returns>
        public String convertDateToString(DateTime date)
        {
            return date.ToString("yyyy-MM-ddTHH\\:mm\\:ss.fffffffzzz"); ; 
        }

        /// <summary>
        /// Converts ISO 8601 to DateTime
        /// </summary>
        /// <param name="dateTime">Time as ISO 8601 String</param>
        /// <returns>DateTime</returns>
        public DateTime convertStringToDate(String dateTime)
        {
            return DateTime.Parse(dateTime, null, System.Globalization.DateTimeStyles.RoundtripKind);
        }
    }
}

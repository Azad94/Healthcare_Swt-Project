/**
 * /*******************************************************************************
 * Copyright 2017 Niklas Klatt
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
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace HealthcareApp.Model.Entity
{
    /// <summary>
    /// Entity class for an subtask, with a checkbox
    /// Author: Niklas Klatt
    /// </summary>
    [Serializable]
    public class SubTaskCheckbox : AbstractSubTask
	{
        /// <summary>
        /// CheckBox value
        /// </summary>
        [JsonProperty("value")]
        public bool Value { get; set; }
        /// <summary>
        /// Creates a new SubTaskCheckbox
        /// </summary>
        /// <param name="Id">id</param>
        /// <param name="Title">title of subtask</param>
        /// <param name="Value">checkbox value</param>
        public SubTaskCheckbox(long Id, string Title, bool Value) : base(Id, Title, "SubTaskCheckbox")
        {
            this.Value = Value;
        }

    }
}

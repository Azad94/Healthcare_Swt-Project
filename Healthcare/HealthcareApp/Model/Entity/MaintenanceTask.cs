/**
 * /*******************************************************************************
 * Copyright 2017 Niklas Klatt & Malte Grebe
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
    /// Entity class for a MaintenanceTask
    /// </summary>
    [Serializable]
    public class MaintenanceTask : Task
    {
        /// <summary>
        /// the number of days until the task is repeated
        /// </summary>
        [JsonProperty("repeatTaskInDays")]
        public int RepeatTaskInDays { get; set; }
        /// <summary>
        /// the picture of the maintenancetask
        /// </summary>
        [JsonProperty("picture")]
        public PictureOfObject Picture { get; set; }
        /// <summary>
        /// all subtask of the maintenancetask
        /// </summary>
        [JsonIgnore]
        public List<AbstractSubTask> Subtasks { get; set; } = new List<AbstractSubTask>();
        [JsonIgnore]
        public bool consistMaintanceTask { get; set; } = false;
    }
}

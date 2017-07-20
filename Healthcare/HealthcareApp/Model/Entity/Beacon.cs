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

namespace HealthcareApp.Model.Entity
{
    /// <summary>
    /// Entity class for a Beacon
    /// </summary>
    [Serializable]
    public class Beacon
    {
        /// <summary>
        /// the uuid of the beacon
        /// </summary>
        [JsonProperty("uuid")]
        public String Uuid { get; set; }
        /// <summary>
        /// the major of the beacon
        /// </summary>
        [JsonProperty("major")]
        public int Major { get; set; }
        /// <summary>
        /// the minor of the beacon
        /// </summary>
        [JsonProperty("minor")]
        public int Minor { get; set; }
    }
}

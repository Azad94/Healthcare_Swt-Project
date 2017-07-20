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
    /// Entity class for a Location
    /// </summary>
    [Serializable]
    public class Location
    {
        /// <summary>
        /// the id of the Location
        /// </summary>
        [JsonProperty("id")]
        public long Id { get; set; }
        /// <summary>
        /// the Buildingnumber of the Location
        /// </summary>
        [JsonProperty("building")]
        public int Building { get; set; }
        /// <summary>
        /// the Floornumber of the Location
        /// </summary>
        [JsonProperty("floor")]
        public int Floor { get; set; }
        /// <summary>
        /// the Roomnumber of the Location
        /// </summary>
        [JsonProperty("room")]
        public int Room { get; set; }
        /// <summary>
        /// Creates a new Location
        /// </summary>
        /// <param name="Building">Building</param>
        /// <param name="Floor">Floor</param>
        /// <param name="Room">Room</param>
        public Location(int Building, int Floor, int Room)
        {
            this.Building = Building;
            this.Floor = Floor;
            this.Room = Room;
        }
        /// <summary>
        /// Creates a new Location
        /// </summary>
        public Location()
        {
        }
    }
}

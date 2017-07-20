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
    /// Entity class for a BeaconObject
    /// </summary>
    [Serializable]
    public class BeaconObject
    {
        /// <summary>
        /// the id of the beaconObject
        /// </summary>
        [JsonProperty("id")]
        public long Id { get; set; }
        /// <summary>
        /// the name of the beaconObject
        /// </summary>
        [JsonProperty("name")]
        public string Name { get; set; }
        /// <summary>
        /// the type of the beaconObject
        /// </summary>
        [JsonProperty("beaconObjectType")]
        public String BeaconObjectType { get; set; }
        /// <summary>
        /// the state of the beaconObject
        /// </summary>
        [JsonProperty("state")]
        public int Status { get; set; }
        /// <summary>
        /// the picture of the beaconObject
        /// </summary>
        [JsonProperty("pictureOfObject")]
        public PictureOfObject Picture { get; set; }
        [JsonProperty("location")]
        /// <summary>
        /// the location of the beaconObject
        /// </summary>
        public Location Location { get; set; }
        /// <summary>
        /// the beacon of the beaconObject
        /// </summary>
        [JsonProperty("beacon")]
        public Beacon Beacon { get; set; }
    }
}
﻿/**
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
    /// Entity class for a Role
    /// </summary>
    [Serializable]
    public class Role
    {
        /// <summary>
        /// the id of the role
        /// </summary>
        [JsonProperty("id")]
        public long Id { get; set; }
        /// <summary>
        /// the name of the role
        /// </summary>
        [JsonProperty("name")]
        public String Name { get; set; }
        /// <summary>
        /// new toString method
        /// </summary>
        public override string ToString()
        {
            return string.Format(this.Name);
        }
    }
}
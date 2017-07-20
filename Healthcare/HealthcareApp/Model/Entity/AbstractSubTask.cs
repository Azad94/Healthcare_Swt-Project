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

namespace HealthcareApp.Model.Entity
{
    /// <summary>
    /// Entity class for an abstract subtask
    /// Author: Niklas Klatt
    /// </summary>
    [Serializable]
    public abstract class AbstractSubTask
    {
        [JsonProperty("id")]
        public long Id { get; set; }
        /// <summary>
        /// title of subtask
        /// </summary>
        [JsonProperty("title")]
        public string Title { get; set; }
        /// <summary>
        /// Type of subtask for REST
        /// </summary>
        [JsonProperty("type")]
        public string Type { get; set; }

        /// <summary>
        /// Creates a new AbstractSubtask
        /// </summary>
        /// <param name="Id">id</param>
        /// <param name="Title">titleof subtask</param>
        /// <param name="type">Type of subtask for REST</param>
        public AbstractSubTask(long Id, string Title, string type)
        {
            this.Id = Id;
            this.Title = Title;
            this.Type = type;
        }

    }
}

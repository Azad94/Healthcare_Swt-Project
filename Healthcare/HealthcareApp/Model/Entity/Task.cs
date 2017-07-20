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
using SQLite;
using System;
using Newtonsoft;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace HealthcareApp.Model.Entity
{
    /// <summary>
    /// Entity class for a Task
    /// </summary>
    [Serializable]
    public abstract class Task
    {
        /// <summary>
        /// the id of the task
        /// </summary>
        [PrimaryKey]
        [JsonProperty("id")]
        public long Id { get; set; }
        /// <summary>
        /// the description of the task
        /// </summary>
        [JsonProperty("description")]
        public String Description { get; set; }
        /// <summary>
        /// the name of the task
        /// </summary>
        [JsonProperty("name")]
        public String Name { get; set; }
        /// <summary>
        /// the level of the task
        /// </summary>
        [JsonProperty("level")]
        public int Level { get; set; }
        /// <summary>
        /// the time when the task was created
        /// </summary>
        [JsonProperty("creationTime")]
        public String CreationTime { get; set; }
        /// <summary>
        /// the time when the task was accepted
        /// </summary>
        [JsonProperty("acceptedTime")]
        public String AcceptedTime { get; set; }
        /// <summary>
        /// the time when the task was finished
        /// </summary>
        [JsonProperty("closedTime")]
        public String ClosedTime { get; set; }
        /// <summary>
        /// the state of the task
        /// </summary>
        [JsonProperty("state")]
        public int Status { get; set; }
        /// <summary>
        /// the time when the task was last edited
        /// </summary>
        [JsonProperty("lastEdited")]
        public String LastEdited { get; set; }
        /// <summary>
        /// the type of the task
        /// </summary>
        [JsonProperty("type")]
        public String Type { get; set; }
        /// <summary>
        /// the suitable role for the task
        /// </summary>
        [JsonProperty("role")]
        public Role Role { get; set; }
        /// <summary>
        /// the creator of the task
        /// </summary>
        [JsonProperty("creator")]
        public User Creator { get; set; }
        /// <summary>
        /// the editor of the task
        /// </summary>
        [JsonProperty("editor")]
        public User Editor { get; set; }
        /// <summary>
        /// the beaconObject of the task
        /// </summary>
        [JsonProperty("beaconObject")]
        public BeaconObject BeaconObject { get; set; }
        /// <summary>
        /// Creates a new Task
        /// </summary>
        /// <param name="id">id</param>
        /// <param name="Description">description of the task</param>
        /// <param name="name">name of the task</param>
        /// <param name="level">urgency of the task</param>
        /// <param name="creationTime">time the task was created</param>
        /// <param name="acceptedTime">time the task was accepted</param>
        /// <param name="closedTime">time the task was finished</param>
        /// <param name="status">status of the task</param>
        /// <param name="lastEdited">last time the task was edited</param>
        /// <param name="type">Type of task</param>
        /// <param name="role">suitable role for the task</param>
        /// <param name="creator">worker who created the task</param>
        /// <param name="editor">worker who accepted the task</param>
        /// <param name="beaconObject">beacon object of the task</param>
        public Task(long id, string description, string name, int level, string creationTime, string acceptedTime, string closedTime, int status,
              string lastEdited, string type, Role role, User creator, User editor, BeaconObject beaconObject)
        {
            this.Id = id;
            this.Description = description;
            this.Name = name;
            this.Level = level;
            this.CreationTime = creationTime;
            this.AcceptedTime = acceptedTime;
            this.ClosedTime = closedTime;
            this.Status = status;
            this.LastEdited = lastEdited;
            this.Type = type;
            this.Role = role;
            this.Creator = creator;
            this.Editor = editor;
            this.BeaconObject = beaconObject;
        }
        /// <summary>
        /// Creates a new Task
        /// </summary>
        public Task(){}
    }
}

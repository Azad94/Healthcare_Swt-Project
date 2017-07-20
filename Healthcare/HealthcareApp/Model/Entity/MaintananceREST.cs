/**
 * /*******************************************************************************
 * Copyright 2017 Sheraz Azad
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
using Newtonsoft.Json;

namespace HealthcareApp.Model.Entity.RestEntity
{
    /// <summary>
    /// This class is the copy of the MaintananceTask class. It is only 
    /// used for the REST Service.
    /// </summary>
    [Serializable]
    public class MaintananceRest : Task
    {
		[JsonProperty("repeatTaskInDays")]
		public int RepeatTaskInDays { get; set; }
		[JsonProperty("picture")]
		public PictureOfObject Picture { get; set; }
		[JsonProperty("subTasks")]
		public List<AbstractSubTask> Subtasks { get; set; } = new List<AbstractSubTask>();

        public MaintananceRest(MaintenanceTask mT) : base(mT.Id, mT.Description, mT.Name, mT.Level, mT.CreationTime,
                                                          mT.AcceptedTime, mT.ClosedTime, mT.Status, mT.LastEdited, mT.Type, mT.Role,
                                                          mT.Creator, mT.Editor, mT.BeaconObject)
        {
            this.RepeatTaskInDays = mT.RepeatTaskInDays;
            this.Picture = mT.Picture;
            this.Subtasks = mT.Subtasks;

            foreach(AbstractSubTask aSub in Subtasks)
            {
                if(aSub is SubTaskImage subImg)
                {
                    this.Subtasks.Remove(subImg);
                }
            }
        }
    }
}


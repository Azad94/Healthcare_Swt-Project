/**
 * /*******************************************************************************
 * Copyright 2017 Daniel Dzimitrowicz
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


using HealthcareApp.Model.Entity;
using System;

namespace HealthcareApp.Model.DB.Entity
{
    /// <summary>
    /// DB substitute for <see cref="SubTaskImage"/>
    /// </summary>
    [Serializable]
    class DBImageTask : AbstractSubTask
    {
        public byte[] value { get; }

        /// <summary>
        /// Creates a new instance off <see cref="DBImageTask"/>
        /// </summary>
        /// <param name="Id"><paramref name="Id"/> of the subtask</param>
        /// <param name="Title"><paramref name="Title"/> of the subtask</param>
        /// <param name="value"><paramref name="value"/> of the subtask</param>
        public DBImageTask(long Id, string Title, byte[] value) : base(Id, Title, "SubTaskImage")
        {
            this.value = value;
        }
    }
}

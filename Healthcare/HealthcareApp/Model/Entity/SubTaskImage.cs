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
using System;
using System.IO;

using Xamarin.Forms;

namespace HealthcareApp.Model.Entity
{
    /// <summary>
    /// Entity class for an subtask, with a image
    /// Author: Niklas Klatt
    /// </summary>
    [Serializable]
    public class SubTaskImage : AbstractSubTask
    {
        /// <summary>
        /// Image for view
        /// </summary>
        [Newtonsoft.Json.JsonIgnore]
        public Image Value { get; }
        /// <summary>
        /// Image as byte[] for database and REST
        /// </summary>
        [Newtonsoft.Json.JsonProperty("picture")]
        public byte[] Image { get; set; }
        /// <summary>
        /// Creates a new SubTaskImage
        /// </summary>
        /// <param name="Title">title of task</param>
        /// <param name="Value">image for view</param>
        public SubTaskImage(string Title, Image Value) : base(0, Title, "SubTaskImage")
        {
            this.Value = Value;
        }
        /// <summary>
        /// Creates a new SubTaskImage
        /// </summary>
        /// <param name="Title">title of task</param>
        /// <param name="Image">Image as byte[] for database and REST</param>
        public SubTaskImage(string Title, byte[] Image) : base(0, Title, "SubTaskImage")
        {
            this.Image = Image;
            Value = new Image()
            {
                Source = ImageSource.FromStream(() => new MemoryStream(Image)) // convert back to Image object for view
            };
        }
        /// <summary>
        /// Creates a new SubTaskImage
        /// </summary>
        /// <param name="Title">title of task</param>
        /// <param name="Value">image for view</param>
        /// <param name="Image">Image as byte[] for database and REST</param>
        public SubTaskImage(string Title, Image Value, byte[] Image) : base(0, Title, "SubTaskImage")
        {
            this.Value = Value;
            this.Image = Image;
        }
    }

}
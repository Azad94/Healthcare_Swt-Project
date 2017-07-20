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

using System;
using System.IO;
using Xamarin.Forms;
using HealthcareApp.Droid;
using HealthcareApp.Model.DB;

[assembly: Dependency(typeof(FileHelper))]
namespace HealthcareApp.Droid
{
    /// <summary>
    /// Android Impl of the IFileHelper Interface.
    /// </summary>
    public class FileHelper : IFileHelper
    {

        /// <summary>
        /// Returns the file-path to the specified file in the specialFolder on Android
        /// </summary>
        /// <param name="filename">file the path is requested for</param>
        /// <returns>combined filepath</returns>
        public string GetLocalFilePath(string filename)
        {
            string path = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
            return Path.Combine(path, filename);
        }
    }
}
/**
 * /*******************************************************************************
 * Copyright 2017 Sheraz Azad, Jonas Grams & Niklas Klatt
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
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using HealthcareApp.Model.Entity.RestEntity;
using HealthcareApp.Converters;

namespace HealthcareApp.Model.REST
{
    /// <summary>
    /// This class contains all the Logic for the REST-Service
    /// </summary>
    class RestMng
    {
        String uri;

        /// <summary>
        /// Initializes a new instance of the <see cref="T:HealthcareApp.Model.REST.RestMng"/> class.
        /// Author: Jonas Grams
        /// </summary>
        /// <param name="uri">URI.</param>
        public RestMng(String uri)
        {
            this.uri = uri;
        }

		/// <summary>
		/// POST Service on Log-In for the Username and password
		/// Author: Jonas Grams
		/// </summary>
		/// <returns>The login.</returns>
		/// <param name="username">Username.</param>
		/// <param name="pwd">Pwd.</param>
		public async Task<User> PostLogin(String username, String pwd)
        {
            using (var client = new HttpClient())
            {
                try
                {
                    var content = new StringContent(("{\"name\":\"" + username + "\",\"password\":\"" + pwd + "\"}"), Encoding.UTF8, "application/json");
                    var result = await client.PostAsync(uri + "login", content).ConfigureAwait(false);
                    String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false);
                    User user = JsonConvert.DeserializeObject<User>(json);
                    return user;
                }
                catch (Exception ex)
                {
                    Debug.WriteLine("-------------------------POST LOGIN FAILED : " + ex.Message + "----------------------");
                    return null;
                }
            }
        }

		/// <summary>
		/// GET Service for the Cleaning Task
		/// Author: Jonas Grams
		/// </summary>
		/// <returns>The cleaning task async.</returns>
		/// <param name="id">Identifier.</param>
		/// <param name="username">Username.</param>
		public async Task<List<CleaningTask>> GetCleaningTaskAsync(long id, String username, Location location, DateTime time)
        {
            using (var client = new HttpClient())
            {
                try
                {
                    UriBuilder uriB = new UriBuilder(uri + "cleaningtasksApp");
                    uriB.Query += "?limit="+1000;
                    if(time != new DateTime() && false)
                    {
                        uriB.Query += "&since=" + new DateConverter().convertDateToString(time);
                    }
                    if (location != null)
                    {
                        uriB.Query += "&building=" + location.Building;
                    }
                    Uri url = uriB.Uri;

                    var content = new StringContent("{\"userName\":\"" + username + "\",\"id\":\"" + id + "\"}", Encoding.UTF8, "application/json");
                    var result = await client.PostAsync(url, content).ConfigureAwait(false); ;
                    String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false); ;

                    List<CleaningTask> cTs = JsonConvert.DeserializeObject<List<CleaningTask>>(json);

                    foreach(CleaningTask c in cTs)
                    {
                        Debug.WriteLine("Cleaning +++ " + c.Id);
                    }
                    return cTs;

                }
                catch (Exception ex)
                {
                    Debug.WriteLine("-------------------------POST GetCleaningTaskAsync FAILED : " + ex.Message + "----------------------");
                    return null;
                }
            }
        }

		/// <summary>
		/// PUT Service for the Cleaning Task.
		/// Author: Jonas Grams
		/// </summary>
		/// <returns>The cleaning task async.</returns>
		/// <param name="cTL">C tl.</param>
		public async Task<bool> PutCleaningTaskAsync(List<CleaningTask> cTL)
        {
            using (var client = new HttpClient())
            {
                UriBuilder uriB = new UriBuilder(uri + "cleaningtasks/");
                uriB.Query = uriB.Query;
                Uri url = uriB.Uri;

                var content = new StringContent(JsonConvert.SerializeObject(cTL), Encoding.UTF8, "application/json");
                var result = await client.PutAsync(url, content).ConfigureAwait(false);
                String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false);

                return result.IsSuccessStatusCode;
            }
        }

		/// <summary>
		/// GET Service for the Maintenance Task
        /// Author: Sheraz Azad und Niklas Klatt
		/// </summary>
		/// <returns>The maintenance task async.</returns>
		/// <param name="id">Identifier.</param>
		/// <param name="username">Username.</param>
		public async Task<List<MaintenanceTask>> GetMaintenanceTaskAsync(long id, String username, Location location, DateTime time)
        {
            using (var client = new HttpClient())
            {
                try
                {
                    UriBuilder uriB = new UriBuilder(uri + "maintainancetasksApp");
                    uriB.Query += "?limit=" + 1000;
                    if (time != new DateTime() && false)
                    {
                        uriB.Query += "&since=" + new DateConverter().convertDateToString(time);
                    }
                    if (location != null)
                    {
                        uriB.Query += "&building=" + location.Building;
                    }
                    Uri url = uriB.Uri;

                    var content = new StringContent("{\"userName\":\"" + username + "\",\"id\":\"" + id + "\"}", Encoding.UTF8, "application/json");
                    var result = await client.PostAsync(url, content).ConfigureAwait(false); ;
                    String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false); ;

                    List<MaintenanceTask> mTs = JsonConvert.DeserializeObject<List<MaintenanceTask>>(json);

                    foreach (MaintenanceTask m in mTs)
                    {
                        m.Subtasks = new List<AbstractSubTask>();
                        var checkBox = await this.GetSubTaskCheckbox(m.Id);
                        var slider = await this.GetSubTaskSlider(m.Id);
                        var image = await this.GetSubTaskImage(m.Id);

                        foreach (SubTaskCheckbox subCheckBox in checkBox)
                        {
                            m.Subtasks.Add(subCheckBox);
                        }

                        foreach (SubTaskSlider subSlider in slider)
                        {
                            m.Subtasks.Add(subSlider);
                        }

                        foreach (SubTaskImage subImage in image)
                        {
                            m.Subtasks.Add(subImage);
                        }

                    }

                    return mTs;
                }
                catch (Exception ex)
                {
                    Debug.WriteLine("-------------------------GET GetMaintenanceTaskAsync FAILED : " + ex.Message + "----------------------");
                    return null;
                }
            }
        }

		/// <summary>
		/// PUT Service for the Maintanance Task
		/// Autor: Sheraz Azad und Niklas Klatt
		/// </summary>
		/// <returns>The maintenance task async.</returns>
		/// <param name="mTL">M tl.</param>
		public async Task<bool> PutMaintenanceTaskAsync(List<MaintenanceTask> mTL)
        {
            using (var client = new HttpClient())
            {
                UriBuilder uriB = new UriBuilder(uri + "maintainancetasks/");
                uriB.Query = uriB.Query;
                Uri url = uriB.Uri;
                var mTList = new List<MaintananceRest>();

                foreach (MaintenanceTask maintanance in mTL)
                {
                    if (maintanance.consistMaintanceTask)
                    {
                        foreach (AbstractSubTask aSub in maintanance.Subtasks)
                        {
                            if (aSub is SubTaskImage subImg)
                            {
                                if (subImg.Id == -1)
                                {
                                    var imageResult = await this.PostSubtaskImage(maintanance.Id, subImg);
                                    subImg.Id = imageResult.Id;
                                    maintanance.consistMaintanceTask = false;
                                }
                            }

                            if (aSub is SubTaskSlider subSlider)
                            {
                                await this.PutSubTaskSlider(maintanance.Id, subSlider);
                            }

                            if (aSub is SubTaskCheckbox subCheck)
                            {
                                await this.PutSubTaskCheckbox(maintanance.Id, subCheck);
                            }
                        }
                    }
                    mTList.Add(new MaintananceRest(maintanance));
                }

                var content = new StringContent(JsonConvert.SerializeObject(mTList), Encoding.UTF8, "application/json");

                var result = await client.PutAsync(url, content).ConfigureAwait(false); ;
                String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false); ;

                return result.IsSuccessStatusCode;
            }
        }

		/// <summary>
		/// GET Service for the Transport Task
		/// Author: Jonas Grams
		/// </summary>
		/// <returns>The transport task async.</returns>
		/// <param name="id">Identifier.</param>
		/// <param name="username">Username.</param>
		public async Task<List<TransportTask>> GetTransportTaskAsync(long id, String username, Location location, DateTime time)
        {
            using (var client = new HttpClient())
            {
                try
                {
                    UriBuilder uriB = new UriBuilder(uri + "transporttasksApp");
                    uriB.Query += "?limit=" + 1000;
                    if (time != new DateTime() && false)
                    {
                        uriB.Query += "&since=" + new DateConverter().convertDateToString(time);
                    }
                    if (location != null)
                    {
                        uriB.Query += "&building=" + location.Building;
                    }
                    Uri url = uriB.Uri;

                    var content = new StringContent("{\"userName\":\"" + username + "\",\"id\":\"" + id + "\"}", Encoding.UTF8, "application/json");
                    var result = await client.PostAsync(url, content).ConfigureAwait(false); ;
                    String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false); ;

                    List<TransportTask> tTs = JsonConvert.DeserializeObject<List<TransportTask>>(json);
                    foreach (TransportTask c in tTs)
					{
						Debug.WriteLine("Cleaning +++ " + c.Id);
					}
                    return tTs;
                }
                catch (Exception ex)
                {
                    Debug.WriteLine("-------------------------POST GetTransportTaskAsync FAILED : " + ex.Message + "----------------------");
                    return null;
                }
            }
        }

		/// <summary>
		/// PUT Service for the Transport Task
		/// Autor: Jonas Grams
		/// </summary>
		/// <returns>The transport task async.</returns>
		/// <param name="tTL">T tl.</param>
		public async Task<bool> PutTransportTaskAsync(List<TransportTask> tTL)
        {
            using (var client = new HttpClient())
            {
                UriBuilder uriB = new UriBuilder(uri + "transporttasks/");
                uriB.Query = uriB.Query;
                Uri url = uriB.Uri;

                var content = new StringContent(JsonConvert.SerializeObject(tTL), Encoding.UTF8, "application/json");
                var result = await client.PutAsync(url, content).ConfigureAwait(false);
                String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false);

                return result.IsSuccessStatusCode;
            }

        }

		/// <summary>
		/// GET Service for the Location Task
		/// Autor: Jonas Grams
		/// </summary>
		/// <returns>The locations async.</returns>
		public async Task<List<Location>> GetLocationsAsync()
        {
            using (var client = new HttpClient())
            {
                UriBuilder uriB = new UriBuilder(uri + "locations/");
                uriB.Query = uriB.Query;
                Uri url = uriB.Uri;

                var result = await client.GetAsync(url).ConfigureAwait(false); ;
                String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false); ;

                List<Location> locationList = JsonConvert.DeserializeObject<List<Location>>(json);

                return locationList;
            }
        }

		/// <summary>
		/// GET Service for the SubTask Image
		/// Autor: Sheraz Azad und Niklas Klatt
		/// </summary>
		/// <returns>The sub task image.</returns>
		/// <param name="id">Identifier.</param>
		public async Task<List<SubTaskImage>> GetSubTaskImage(long id)
        {
            using (var client = new HttpClient())
            {
                try
                {
                    UriBuilder uriB = new UriBuilder(uri + "maintainancetasks/" + id + "/subtasksimage");
                    uriB.Query = uriB.Query;
                    Uri url = uriB.Uri;

                    var result = await client.GetAsync(url).ConfigureAwait(false); ;
                    String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false); ;

                    List<SubTaskImage> mTs = JsonConvert.DeserializeObject<List<SubTaskImage>>(json);

                    return mTs;
                }
                catch (Exception ex)
                {
                    Debug.WriteLine("-------------------------GET GetSubTaskImage FAILED : " + ex.Message + "----------------------");
                    return null;
                }
            }
        }

		/// <summary>
		/// GET Service for the SubTask Checkbox
		/// Autor: Sheraz Azad und Niklas Klatt
		/// </summary>
		/// <returns>The sub task checkbox.</returns>
		/// <param name="id">Identifier.</param>
		public async Task<List<SubTaskCheckbox>> GetSubTaskCheckbox(long id)
        {
            using (var client = new HttpClient())
            {
                try
                {
                    UriBuilder uriB = new UriBuilder(uri + "maintainancetasks/" + id + "/subtaskscheckbox");
                    uriB.Query = uriB.Query;
                    Uri url = uriB.Uri;


                    var result = await client.GetAsync(url).ConfigureAwait(false); ;
                    String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false); ;

                    List<SubTaskCheckbox> mTs = JsonConvert.DeserializeObject<List<SubTaskCheckbox>>(json);

                    return mTs;
                }
                catch (Exception ex)
                {
                    Debug.WriteLine("-------------------------GET GetSubTaskCheckBox FAILED : " + ex.Message + "----------------------");
                    return null;
                }
            }
        }

		/// <summary>
		/// GET Service for the SubTask Slider
		/// Autor: Sheraz Azad und Niklas Klatt
		/// </summary>
		/// <returns>The sub task slider.</returns>
		/// <param name="id">Identifier.</param>
		public async Task<List<SubTaskSlider>> GetSubTaskSlider(long id)
        {
            using (var client = new HttpClient())
            {
                try
                {
                    UriBuilder uriB = new UriBuilder(uri + "maintainancetasks/" + id + "/subtasksslider");
                    uriB.Query = uriB.Query;
                    Uri url = uriB.Uri;

                    var result = await client.GetAsync(url).ConfigureAwait(false); ;
                    String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false); ;

                    List<SubTaskSlider> mTs = JsonConvert.DeserializeObject<List<SubTaskSlider>>(json);

                    return mTs;
                }
                catch (Exception ex)
                {
                    Debug.WriteLine("-------------------------GET GetSubTaskSlider FAILED : " + ex.Message + "----------------------");
                    return null;
                }
            }
        }

		/// <summary>
		/// POST Service for the SubTask Image
		/// Autor: Sheraz Azad und Niklas Klatt
		/// </summary>
		/// <returns>The subtask image.</returns>
		/// <param name="id">Identifier.</param>
		/// <param name="image">Image.</param>
		public async Task<SubTaskImage> PostSubtaskImage(long id, SubTaskImage image)
        {
            using (var client = new HttpClient())
            {
                var content = new StringContent(JsonConvert.SerializeObject(image), Encoding.UTF8, "application/json");
                var result = await client.PostAsync(uri + "maintainacetasks/" + id + "/subtasksimage", content).ConfigureAwait(false); ;
                String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false); ;

                return (SubTaskImage)JsonConvert.DeserializeObject(json);
            }
        }

		/// <summary>
		/// PUT Service for the SubTask CheckBox
		/// Autor: Sheraz Azad und Niklas Klatt
		/// </summary>
		/// <returns>The sub task checkbox.</returns>
		/// <param name="maintananceID">Maintanance identifier.</param>
		/// <param name="checkBox">Check box.</param>
		public async Task<bool> PutSubTaskCheckbox(long maintananceID, SubTaskCheckbox checkBox)
        {
            using (var client = new HttpClient())
            {
                UriBuilder uriB = new UriBuilder(uri + "/maintainancetasks/" + maintananceID + "/subtaskscheckbox/" + checkBox.Id);
                uriB.Query = uriB.Query;
                Uri url = uriB.Uri;

                var content = new StringContent(JsonConvert.SerializeObject(checkBox), Encoding.UTF8, "application/json");
                var result = await client.PutAsync(url, content).ConfigureAwait(false);
                String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false);

                return result.IsSuccessStatusCode;
            }
        }

		/// <summary>
		/// PUT Service for the SubTask Image
		/// Autor: Sheraz Azad und Niklas Klatt
		/// </summary>
		/// <returns>The sub task image.</returns>
		/// <param name="maintananceID">Maintanance identifier.</param>
		/// <param name="image">Image.</param>
		public async Task<bool> PutSubTaskImage(long maintananceID, SubTaskImage image)
        {
            using (var client = new HttpClient())
            {
                UriBuilder uriB = new UriBuilder(uri + "/maintainancetasks/" + maintananceID + "/subtasksimage/" + image.Id);
                uriB.Query = uriB.Query;
                Uri url = uriB.Uri;

                var content = new StringContent(JsonConvert.SerializeObject(image), Encoding.UTF8, "application/json");
                var result = await client.PutAsync(url, content).ConfigureAwait(false);
                String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false);

                return result.IsSuccessStatusCode;
            }
        }

		/// <summary>
		/// PUT Service for the SubTask Slider
		/// Autor: Sheraz Azad und Niklas Klatt
		/// </summary>
		/// <returns>The sub task slider.</returns>
		/// <param name="maintananceID">Maintanance identifier.</param>
		/// <param name="slider">Slider.</param>
		public async Task<bool> PutSubTaskSlider(long maintananceID, SubTaskSlider slider)
        {
            using (var client = new HttpClient())
            {
                UriBuilder uriB = new UriBuilder(uri + "/maintainancetasks/" + maintananceID + "/subtasksslider/" + slider.Id);
                uriB.Query = uriB.Query;
                Uri url = uriB.Uri;

                var content = new StringContent(JsonConvert.SerializeObject(slider), Encoding.UTF8, "application/json");
                var result = await client.PutAsync(url, content).ConfigureAwait(false);
                String json = await result.Content.ReadAsStringAsync().ConfigureAwait(false);

                return result.IsSuccessStatusCode;
            }
        }
    }
}
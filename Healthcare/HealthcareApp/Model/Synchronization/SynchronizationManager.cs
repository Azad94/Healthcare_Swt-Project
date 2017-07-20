/**
 * /*******************************************************************************
 * Copyright 2017 Jonas Grams
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
using HealthcareApp.Model.Entity;
using HealthcareApp.Model.REST;
using SQLiteDatabase;
using Xamarin.Forms;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Diagnostics;
using HealthcareApp.Converters;

namespace HealthcareApp.Model.Synchronization
{
    /// <summary>
    /// Synchronizes the server and the internal data
    /// </summary>
    class SynchronizationManager
    {
        private AppCore appCore;
        private Database dataBase;
        private RestMng restMng;
        private DateTime lastContact;
        private bool serverConnection;
        public bool TaskIsRunning { get; set; }

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="appCore"></param>
        /// <param name="dataBase"></param>
        /// <param name="restMng"></param>
        public SynchronizationManager(AppCore appCore, Database dataBase, RestMng restMng)
        {
            this.appCore = appCore;
            this.dataBase = dataBase;
            this.restMng = restMng;
            this.appCore.setSynchMng(this);
            this.serverConnection = false;
            this.TaskIsRunning = false;
            MessagingCenter.Subscribe<AppCore, bool>(this, "ServerConnectionChange", (sender , serverConnection) => { SetServerConnection(serverConnection); });
        }

        /// <summary>
        /// Sends the login data of a user to the server and returns either the user or NULL
        /// </summary>
        /// <param name="username">username</param>
        /// <param name="password">password</param>
        /// <returns></returns>
        public User TryLogin(String username,String password)
        {
            Task<User> t = restMng.PostLogin(username, password);
            t.Wait();
            User user = t.Result;
            return user;
        }

        /// <summary>
        /// Synchronizes the Server and the internal database
        /// </summary>
        /// <param name="user"></param>
        /// <param name="workLocation"></param>
        /// <param name="time"></param>
        public void InitSynch(User user, Location workLocation, DateTime time)
        {
            Task<List<CleaningTask>> ct;
            Task<List<MaintenanceTask>> mt;
            Task<List<TransportTask>> tt;
            if (user != null)
            {
                switch (user.Role.Name)
                {
                    case Global.roleNameEmployee:
                        {
                            ct = restMng.GetCleaningTaskAsync(user.Id, user.Name, workLocation, time);
                            tt = restMng.GetTransportTaskAsync(user.Id, user.Name, workLocation, time);
                            ct.Wait();
                            tt.Wait();
                            dataBase.AddCleaningTasks(ct.Result);
                            dataBase.AddTransportTasks(tt.Result);
                            break;
                        }
                    case Global.roleNameTech:
                        {
                            mt = restMng.GetMaintenanceTaskAsync(user.Id, user.Name, workLocation, time);
                            mt.Wait();
                            dataBase.AddMaintenanceTasks(mt.Result);
                            break;
                        }
                    default: break;
                }
                UpdateLastContact();
            }
        }

        /// <summary>
        /// Starts the synchronization
        /// </summary>
        /// <param name="user"></param>
        /// <param name="workLocation"></param>
        public void StartSynch(User user, Location workLocation)
        {
            updateServer();
            InitSynch(user, workLocation, lastContact);
            appCore.InitData();
        }

        /// <summary>
        /// Sends clientside updtes to the server
        /// </summary>
        public void updateServer()
        {
            List<CleaningTask> cL = dataBase.getAllCleaningTasks();
            List<MaintenanceTask> mL = dataBase.getAllMaintenanceTasks();
            List<TransportTask> tL = dataBase.getAllTransportTasks();

            restMng.PutCleaningTaskAsync(filterCleaningTasks(cL));
            restMng.PutMaintenanceTaskAsync(filterMaintenanceTasks(mL));
            restMng.PutTransportTaskAsync(filterTransportTasks(tL));
        }

        /// <summary>
        /// Filters a list of CleaningTasks by its timestamp
        /// </summary>
        /// <param name="cL"></param>
        /// <returns></returns>
        private List<CleaningTask> filterCleaningTasks(List<CleaningTask> cL)
        {
            List<CleaningTask> l = new List<CleaningTask>();
            foreach(CleaningTask cT in cL)
            {
                if(cT.LastEdited != null && new DateConverter().convertStringToDate(cT.LastEdited).Subtract(lastContact).TotalMilliseconds > 0)
                {
                    l.Add(cT);
                }
            }
            return l;
        }


        /// <summary>
        /// Filters a list of MaintenanceTasks by its timestamp
        /// </summary>
        /// <param name="mL"></param>
        /// <returns></returns>
        private List<MaintenanceTask> filterMaintenanceTasks(List<MaintenanceTask> mL)
        {
            List<MaintenanceTask> l = new List<MaintenanceTask>();
            foreach (MaintenanceTask mT in mL)
            {
                if (mT.LastEdited != null && new DateConverter().convertStringToDate(mT.LastEdited).Subtract(lastContact).TotalMilliseconds > 0)
                {
                    l.Add(mT);
                }
            }
            return l;
        }

        /// <summary>
        /// Filters a list of TransportTasks by its timestamp
        /// </summary>
        /// <param name="tL"></param>
        /// <returns></returns>
        private List<TransportTask> filterTransportTasks(List<TransportTask> tL)
        {
            List<TransportTask> l = new List<TransportTask>();
            foreach (TransportTask tT in tL)
            {
                if (tT.LastEdited != null && new DateConverter().convertStringToDate(tT.LastEdited).Subtract(lastContact).TotalMilliseconds > 0)
                {
                    l.Add(tT);
                }
            }
            return l;
        }

        /// <summary>
        /// Resets the last server contact
        /// </summary>
        public void UpdateLastContact()
        {
            lastContact = DateTime.Now.ToLocalTime();
        }

        /// <summary>
        /// Sends an update of a Maintenancetask to the server , if there is a connection
        /// </summary>
        /// <param name="t"></param>
        internal void UpdateMaintenanceTask(MaintenanceTask t)
        {
            if(serverConnection)
            {
                List<MaintenanceTask> tL = new List<MaintenanceTask>();
                tL.Add(t);
                restMng.PutMaintenanceTaskAsync(tL);
            }
        }

        /// <summary>
        /// Sends an update of a TransportTask to the server , if there is a connection
        /// </summary>
        /// <param name="t"></param>
        internal void UpdateTransportTask(TransportTask t)
        {
            if (serverConnection)
            {
                List<TransportTask> tL = new List<TransportTask>();
                tL.Add(t);
                restMng.PutTransportTaskAsync(tL);
            }
        }

        /// <summary>
        /// Sends an update of a CleaningTask to the server , if there is a connection
        /// </summary>
        /// <param name="t"></param>
        internal void UpdateCleaningTask(CleaningTask t)
        {
            if (serverConnection)
            {
                List<CleaningTask> tL = new List<CleaningTask>();
                tL.Add(t);
                restMng.PutCleaningTaskAsync(tL);
            }
        }

        private void SetServerConnection(bool serverConnection)
        {
            this.serverConnection = serverConnection;
        }

        /// <summary>
        /// Returns all distinct buildings from the server database
        /// </summary>
        /// <param name="filterBuilding"></param>
        /// <returns>Locations</returns>
        public List<Location> GetFilteredLocations(bool filterBuilding)
        {
            Task<List<Location>> t = restMng.GetLocationsAsync();
            t.Wait();
            return filterLocations(t.Result, filterBuilding);
        }

        /// <summary>
        /// Returns a distinct list of Locations filtered by their building
        /// </summary>
        /// <param name="filterList"></param>
        /// <param name="filterBuilding"></param>
        /// <returns></returns>
        private List<Location> filterLocations(List<Location> filterList, bool filterBuilding)
        {
            List<Location> ret = new List<Location>();
            Boolean addToList = true;
            foreach (Location fL in filterList)
            {
                addToList = true;
                foreach (Location l in ret)
                {
                    if (fL.Building == l.Building)
                    {
                        addToList = false;
                    }
                }
                if (addToList)
                {
                    ret.Add(new Location(fL.Building, -1, -1));
                }
            }
            return ret;
        }

        /// <summary>
        /// Starts the synchronization cycle
        /// </summary>
        public void startSynchTimer()
        {
            synchTimer();
        }

        /// <summary>
        /// starts an asynch timer that is used for synchronization 
        /// and restarts itself aslong as there is a server connection 
        /// </summary>
        private void synchTimer()
        {
            if (serverConnection)
            {
                System.Threading.Tasks.Task.Run(async delegate
                {
                    await System.Threading.Tasks.Task.Delay(TimeSpan.FromMinutes(Global.synchIntervalInMin));
                    if (serverConnection)
                    {
                        TaskIsRunning = true;
                        if (appCore.user != null && appCore.workLocation != null)
                        {
                            TaskIsRunning = true;
                            StartSynch(appCore.user, appCore.workLocation);
                        }
                        else
                        {
                            TaskIsRunning = false;
                        }
                    }
                    else
                    {
                        TaskIsRunning = false;
                    }
                    synchTimer();
                });
            }
            else
            {
                TaskIsRunning = false;
            }
        }
    }
}

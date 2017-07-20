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
using System.Collections.Generic;
using HealthcareApp.Model.Enumeration;
using HealthcareApp.Model.Synchronization;
using HealthcareApp.Model.Entity;
using SQLiteDatabase;
using Xamarin.Forms;
using HealthcareApp.ViewModel;
using System.Diagnostics;
using HealthcareApp.Model.REST;
using HealthcareApp.Model.Enumeration;
using HealthcareApp.Converters;

namespace HealthcareApp.Model
{
    /// <summary>
    /// implemented as a Singleton. Represents the central model logic of the application
    /// </summary>
    class AppCore
    {
        public Database DataBase;
        public Task CurrentTask { get; set; }
        public User user { get; set; }
        public Location workLocation { get; set; }
        private WifiConnectionManager wifiMng;
        private SynchronizationManager synchMng;
        private RestMng restMng { get; set; }
        private List<Task> btTasks { get; set; }
        private List<Task> qrTasks { get; set; }
        private List<Task> openTasks { get; set; }
        private List<Task> acceptedTasks { get; set; }
        private List<Task> closedTasks { get; set; }
        private List<Location> workLocations { get; set; }
        private bool hasServerConnection;
        public bool hasLocation;
        private static AppCore instance;

        /// <summary>
        /// Singleton constructor
        /// </summary>
        public static AppCore Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new AppCore();
                }
                return instance;
            }
        }

        /// <summary>
        /// Constructor
        /// </summary>
        private AppCore()
        {
            this.btTasks = new List<Task>();
            this.qrTasks = new List<Task>();
            this.acceptedTasks = new List<Task>();
            this.openTasks = new List<Task>();
            this.closedTasks = new List<Task>();
            this.workLocations = new List<Location>();
            this.CurrentTask = null;
            this.workLocation = null;
            this.user = null;
            this.hasServerConnection = false;
            this.hasLocation = false;
            initSubscribes();
        }

        //
        // AUTH
        //
        
        /// <summary>
        /// Returns wether a user with the given username and password exists or not and 
        /// informs the required modules about the result.
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        public bool AuthUser(String username, String password)
        {
            bool ret = false;
            user = synchMng.TryLogin(username, password);
            workLocations = synchMng.GetFilteredLocations(true);
            ret = (user != null);
            if (ret)
            {
                MessagingCenter.Send<AppCore, List<Location>>(this, "ChooseWorkLocation", workLocations);
                MessagingCenter.Send<AppCore, Role>(this, "Auth", user.Role);
            }
            return ret;
        }

        //
        //INIT
        //
        /// <summary>
        /// Initializes a synchronization
        /// </summary>
        public void InitData()
        {
            synchMng.InitSynch(user, workLocation, new DateTime());
            GetTasks();
            if(!synchMng.TaskIsRunning && false)
            {
                synchMng.startSynchTimer();
            }
        }

        //
        //User
        //
        public Role GetUserRole()
        {
            return user.Role;
        }

        //
        //DB
        //
        public void SetDatabase(Database db)
        {
            this.DataBase = db;
        }

        public void SetRestMng(RestMng restMng)
        {
            this.restMng = restMng;
        }

        /// <summary>
        /// Updates a given Task in the internal database and the server if possible
        /// </summary>
        /// <param name="t"></param>
        /// <returns></returns>
        private bool updateTask(Task t)
        {
            bool ret = false;
            if (t.GetType() == typeof(CleaningTask))
            {
                DataBase.UpdateCleaningTask((CleaningTask)t);
                synchMng.UpdateCleaningTask((CleaningTask)t);
                ret = true;
            }
            else if (t.GetType() == typeof(TransportTask))
            {
                DataBase.UpdateTransportTask((TransportTask)t);
                synchMng.UpdateTransportTask((TransportTask)t);
                ret = true;
            }
            else if (t.GetType() == typeof(MaintenanceTask))
            {
                DataBase.UpdateMaintenanceTask((MaintenanceTask)t);
                synchMng.UpdateMaintenanceTask((MaintenanceTask)t);
                ret = true;
            }
            return ret;
        }

        //
        //TASKS
        //
        public void SetCurrentTask(Task t)
        {
            CurrentTask = t;
        }

        public Task GetCurrentTask()
        {
            return CurrentTask;
        }

        /// <summary>
        /// Resets the list of tasks that are in beaconrange 
        /// and adds all task referring to the given uuids that are OPEN or ACCEPTED
        /// </summary>
        /// <param name="beaconUuids"></param>
        public void changeBtTasks(List<String> beaconUuids)
        {
            List<Task> tempT = new List<Task>(btTasks);
            List<String> tempUuids = new List<String>(beaconUuids);
            List<String> remList = new List<string>();

            foreach (Task t in this.btTasks)
            {
                if (!tempUuids.Contains(t.BeaconObject.Beacon.Uuid))
                {
                    tempT.Remove(t);
                }
                else if (!remList.Contains(t.BeaconObject.Beacon.Uuid))
                {
                    remList.Add(t.BeaconObject.Beacon.Uuid);
                }
            }

            foreach (String uuid in remList)
            {
                if (tempUuids.Contains(uuid))
                {
                    tempUuids.Remove(uuid);
                }
            }

            this.btTasks = new List<Task>(tempT);

            foreach (String uuid in tempUuids)
            {
                Task t1 = null;
                Task t2 = null;
                Task t3 = null;
                switch (user.Role.Name)
                {
                    case Global.roleNameEmployee:
                        {
                            t1 = DataBase.GetCleaningTaskByUUID(uuid);
                            t3 = DataBase.GetTransportTaskByUUID(uuid);
                            break;
                        }
                    case Global.roleNameTech:
                        {
                            t2 = DataBase.GetMaintenanceTaskByUUID(uuid);
                            break;
                        }
                    default: break;
                }
                if (t1 != null)
                {
                    if (t1.Status != (int)TaskStatus.CLOSED)
                    {
                        btTasks.Add(t1);
                    }
                }
                if (t2 != null)
                {
                    if (t2.Status != (int)TaskStatus.CLOSED)
                    {
                        btTasks.Add(t2);
                    }
                }
                if (t3 != null)
                {
                    if (t3.Status != (int)TaskStatus.CLOSED)
                    {
                        btTasks.Add(t3);
                    }
                }
            }
            sendBtTasks();
        }

        /// <summary>
        /// sends a message with a list of tasks that are currently in beaconrange and OPEN or ACCEPTED
        /// </summary>
        private void sendBtTasks()
        {
            MessagingCenter.Send<AppCore, List<Task>>(this, "Bluetooth", this.btTasks);
        }

        /// <summary>
        /// resets the list of tasks belonging to the given uuid that was read from the last QR-tag
        /// </summary>
        /// <param name="uuid"></param>
        public void changeQRTasks(String uuid)
        {
            qrTasks.Clear();
            Task t1 = null;
            Task t2 = null;
            Task t3 = null;
            switch (user.Role.Name)
            {
                case Global.roleNameEmployee:
                    {
                        t1 = DataBase.GetCleaningTaskByUUID(uuid);
                        t3 = DataBase.GetTransportTaskByUUID(uuid);
                        break;
                    }
                case Global.roleNameTech:
                    {
                        t2 = DataBase.GetMaintenanceTaskByUUID(uuid);
                        break;
                    }
                default: break;
            }
            if (t1 != null)
            {
                if (t1.Status != (int)TaskStatus.CLOSED)
				{
                    qrTasks.Add(t1);
                }
            }
            if (t2 != null)
            {
                if (t2.Status != (int)TaskStatus.CLOSED)
                {
                    qrTasks.Add(t2);
                }
            }
            if (t3 != null)
            {
                if (t3.Status != (int)TaskStatus.CLOSED)
                {
                    qrTasks.Add(t3);
                }
            }
            sendQrTasks();
        }

        /// <summary>
        /// sends a message with the current list of tasks belonging to the last scanned QR-tag
        /// </summary>
        private void sendQrTasks()
        {
            MessagingCenter.Send<AppCore, List<Task>>(this, "QR", this.qrTasks);
        }

        /// <summary>
        /// resets all current lists
        /// </summary>
        public void GetTasks()
        {
            if (user != null)
            {
                acceptedTasks.Clear();
                closedTasks.Clear();
                openTasks.Clear();
                btTasks.Clear();
                switch (user.Role.Name)
                {
                    case Global.roleNameEmployee:
                        {

                            sortTasks(DataBase.getAllCleaningTasks());
                            sortTasks(DataBase.getAllTransportTasks());
                            break;
                        }
                    case Global.roleNameTech:
                        {
                            sortTasks(DataBase.getAllMaintenanceTasks());
                            break;
                        }
                    default: break;
                }
                reloadTasks();
            }
        }

        /// <summary>
        /// sorts a list of TransportTasks depending on theír state
        /// </summary>
        /// <param name="list"></param>
        private void sortTasks(List<TransportTask> list)
        {
            foreach (TransportTask t in list)
            {
                sortTask((Task)t);
            }
        }

        private void sortTasks(List<CleaningTask> list)
        {
            foreach (CleaningTask t in list)
            {
                sortTask((Task)t);
            }
        }

        /// <summary>
        /// sorts a list of MaintenanceTasks depending on theír state
        /// </summary>
        /// <param name="list"></param>
        private void sortTasks(List<MaintenanceTask> list)
        {
            foreach (MaintenanceTask t in list)
            {
                sortTask((Task)t);
            };
        }

        /// <summary>
        /// adds a given task to a list depending on its state
        /// </summary>
        /// <param name="t"></param>
        private void sortTask(Task t)
        {
            switch (t.Status)
            {
                case (int)TaskStatus.OPEN:
                    {
                        openTasks.Add(t);
                        break;
                    }
                case (int)TaskStatus.ACCEPTED:
                    {
                        acceptedTasks.Add(t);
                        break;
                    }
                case (int)TaskStatus.CLOSED:
                    {
                        closedTasks.Add(t);
                        break;
                    }
                default: break;
            }
        }

        /// <summary>
        /// changes the state of the give task to ACCEPTED
        /// </summary>
        /// <param name="t"></param>
        public void AcceptTask(Task t)
        {
            bool ret;
            Task temp = t;
            if (temp.Status != (int)TaskStatus.ACCEPTED)
            {
                updateInternalLists(t);
                temp.Status = (int)TaskStatus.ACCEPTED;
                temp.Editor = this.user;
                //temp.AcceptedTime = new DateConverter().convertDateToString(DateTime.Now.ToLocalTime());
                temp.LastEdited = temp.AcceptedTime;
                ret = updateTask(temp);
                acceptedTasks.Add(temp);
                CurrentTask = temp;
                reloadTasks();
            }
            CurrentTask = temp;
        }

        /// <summary>
        /// changes the state of a given task to CLOSED
        /// </summary>
        /// <param name="t"></param>
        public void CloseTask(Task t)
        {
            bool ret;
            Task temp = t;
            if (temp.Status != (int)TaskStatus.CLOSED && temp.Editor == this.user)
            {
                updateInternalLists(t);
                temp.Status = (int)TaskStatus.CLOSED;
                temp.Editor = this.user;
                //temp.ClosedTime = new DateConverter().convertDateToString(DateTime.Now.ToLocalTime());
                temp.LastEdited = temp.ClosedTime;
                ret = updateTask(temp);
                closedTasks.Add(temp);
                CurrentTask = temp;
                reloadTasks();
            }
            CurrentTask = temp;

        }

        /// <summary>
        /// changes the state of the given task to OPEN
        /// </summary>
        /// <param name="t"></param>
        public void OpenTask(Task t)
        {
            bool ret;
            Task temp = t;

            if (temp.Status != (int)TaskStatus.OPEN && temp.Editor == this.user)
            {
                updateInternalLists(t);
                temp.Status = (int)TaskStatus.OPEN;
                temp.Editor = this.user;
                temp.LastEdited = new DateConverter().convertDateToString(DateTime.Now.ToLocalTime());
                ret = updateTask(temp);
                openTasks.Add(temp);
                CurrentTask = temp;
                reloadTasks();
            }
            CurrentTask = temp;
        }

        /// <summary>
        /// saves the progress of the given task
        /// </summary>
        /// <param name="t"></param>
        public void SaveTask(Task t)
        {
            bool ret;
            t.Editor = this.user;
            ret = updateTask(t);
            CurrentTask = t;
        }

        /// <summary>
        /// removes the given task from all internal lists
        /// </summary>
        /// <param name="t"></param>
        private void updateInternalLists(Task t)
        {
            acceptedTasks.Remove(t);
            closedTasks.Remove(t);
            openTasks.Remove(t);
        }

        /// <summary>
        /// sends messages with the current lists of tasks
        /// </summary>
        private void reloadTasks()
        {
            MessagingCenter.Send<AppCore, List<Task>>(this, "OpenTasks", this.openTasks);
            MessagingCenter.Send<AppCore, List<Task>>(this, "ClosedTasks", this.closedTasks);
            MessagingCenter.Send<AppCore, List<Task>>(this, "AcceptedTasks", this.acceptedTasks);
        }

        //
        // LOCATION
        //

        /// <summary>
        /// resets the worklocation to the given location 
        /// and initializes all required steps for a resynchronization with the server
        /// </summary>
        /// <param name="workLocation"></param>
        public void ChangeWorkLocation(Location workLocation)
        {
            if (this.workLocation != workLocation)
            {
                this.workLocation = workLocation;
                DataBase.clearAllTasks();
                InitData();
				MessagingCenter.Send<AppCore, Role>(this, "StartBluetooth", user.Role);
            }
        }

        /// <summary>
        /// sends a message with a list of locations
        /// </summary>
        private void sendWorkLocations()
        {
            MessagingCenter.Send<AppCore, List<Location>>(this, "ChooseWorkLocation", workLocations);
        }

        /// <summary>
        /// initializes all required MessagingListeners
        /// </summary>
        private void initSubscribes()
        {
            MessagingCenter.Subscribe<ChangeWorklocationViewModel>(this, "ChangeWorklocationReady", (sender) => { sendWorkLocations(); sendWifiMessage(); });
            MessagingCenter.Subscribe<QRTaskPageViewModel>(this, "QRTasksRequest", (sender) => { sendQrTasks(); });
            MessagingCenter.Subscribe<BluetoothTaskPageViewModel>(this, "BTTasksRequest", (sender) => { sendBtTasks(); });
            MessagingCenter.Subscribe<LoginViewModel>(this, "LoginReady", (sender) => { sendWifiMessage(); });
            MessagingCenter.Subscribe<MainMenuViewModel>(this, "MainMenuReady", (sender) => { sendWifiMessage(); });
            MessagingCenter.Subscribe<TaskTabViewModel>(this, "TasksRequest", (sender) => { reloadTasks(); });
        }

        //
        //WIFI
        //
        /// <summary>
        /// resets the serverConnection value to the given value 
        /// and inform the other modules about the change
        /// </summary>
        /// <param name="hasServerConnection"></param>
        public void InformConnectionChange(bool hasServerConnection)
        {
            this.hasServerConnection = hasServerConnection;
            sendWifiMessage();
        }

        /// <summary>
        /// sends a message with the current serverconnection state
        /// </summary>
        private void sendWifiMessage()
        {
            MessagingCenter.Send<AppCore, bool>(this, "ServerConnectionChange", this.hasServerConnection);
        }

        //getter&setter
        public void setWifiMng(WifiConnectionManager wifiMng)
        {
            this.wifiMng = wifiMng;
        }

        public void setSynchMng(SynchronizationManager synchMng)
        {
            this.synchMng = synchMng;
        }
    }
}

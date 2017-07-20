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
using SQLite;
using System.Collections.Generic;
using System.Linq;
using Xamarin.Forms;
using HealthcareApp.Model.Serializer;
using HealthcareApp.Model.Entity;
using System.Diagnostics;
using HealthcareApp.Model.DB.Entity;
using HealthcareApp.Model.DB;
using HealthcareApp.Model;

namespace SQLiteDatabase
{
    /// <summary>
    /// Class for creating and handling the local database
    /// </summary>
    class Database
    {
        private readonly SQLiteConnection _connection;
        private readonly BinarySerializer binarySerializer = new BinarySerializer();

        /// <summary>
        /// Creates a new instance of <see cref="Database"/>
        /// </summary>
        public Database()
        {
            try
            {
                IFileHelper filehelper = DependencyService.Get<IFileHelper>();
                deleteDBFile();
                var test = filehelper.GetLocalFilePath("TaskDatabase.db3");
                _connection = new SQLiteConnection(test);
                _connection.CreateTable<DBCleaningTask>();
                _connection.CreateTable<DBMaintenanceTask>();
                _connection.CreateTable<DBTransportTask>();
                this.constraintTest();
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
        }

        /// <summary>
        /// Deletes the DB-File to ensure no old task/old table version remains
        /// </summary>
        private void deleteDBFile()
        {
            /// skip delete on release version
            if (Global.debug)
            {
                IFileHelper filehelper = DependencyService.Get<IFileHelper>();
                try
                {
                    System.IO.File.Delete(filehelper.GetLocalFilePath("TaskDatabase.db3"));
                }
                catch (Exception ex)
                {
                    Debug.WriteLine(ex.ToString());
                }
            }
        }
        // Cleaning Tasks

        /// <summary>
        /// Get a <see cref="List{T}"/> containing all <see cref="CleaningTask"/>
        /// </summary>
        /// <returns><see cref="List{T}"/> with all <see cref="CleaningTask"/></returns>
        public List<CleaningTask> getAllCleaningTasks()
        {
            List<CleaningTask> ctList = new List<CleaningTask>();
            try
            {
                foreach (DBCleaningTask task in _connection.Table<DBCleaningTask>().ToList())
                {
                    ctList.Add(binarySerializer.Deserialize<CleaningTask>(task.ObjectSerialization));
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
            return ctList;
        }

        /// <summary>
        /// Get <see cref="CleaningTask"/> with requested <paramref name="id"/>
        /// </summary>
        /// <param name="id">ID of the desired <see cref="CleaningTask"/></param>
        /// <returns><see cref="CleaningTask"/> if DB contains desired <paramref name="id"/>, <see langword="null"/> if DB does not contain <paramref name="id"/></returns>
        public CleaningTask GetCleaningTask(long id)
        {
            try
            {
                return binarySerializer.Deserialize<CleaningTask>(_connection.Table<DBCleaningTask>().FirstOrDefault(t => t.id == id).ObjectSerialization);
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return null;
            }
        }

        /// <summary>
        /// Get <see cref="List{T}"/> of type <see cref="CleaningTask"/> containing all requested tasks
        /// </summary>
        /// <param name="taskIds"><see cref="List{T}"/> of all requested ID's</param>
        /// <returns><see cref="List{T}"/> containing all found <see cref="CleaningTask"/> with requested ID's. Empty of none found</returns>
        public List<CleaningTask> GetCleaningTasks(List<long> taskIds)
        {
            List<CleaningTask> resultList = new List<CleaningTask>();
            try
            {
                foreach (long id in taskIds) resultList.Add(binarySerializer.Deserialize<CleaningTask>(_connection.Table<DBCleaningTask>().FirstOrDefault(t => t.id == id).ObjectSerialization));
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
            return resultList;
        }

        /// <summary>
        /// Get <see cref="CleaningTask"/> with requested <paramref name="uuid"/>
        /// </summary>
        /// <param name="uuid"><paramref name="uuid"/> of the requested <see cref="CleaningTask"/></param>
        /// <returns><see cref="CleaningTask"/> if DB contains desired <paramref name="uuid"/><see langword="null"/> if DB does not contain <paramref name="uuid"/></returns>
        public CleaningTask GetCleaningTaskByUUID(String uuid)
        {
            try
            {
                return binarySerializer.Deserialize<CleaningTask>(_connection.Table<DBCleaningTask>().FirstOrDefault(t => t.uuid == uuid).ObjectSerialization);
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return null;
            }
        }

        /// <summary>
        /// Get <see cref="List{T}"/> of type <see cref="CleaningTask"/> containing all requested tasks
        /// </summary>
        /// <param name="taskUuids"><see cref="List{T}"/> of all requested ID's</param>
        /// <returns><see cref="List{T}"/> containing all found <see cref="CleaningTask"/> with requested UUID's. Empty of none found</returns>
        public List<CleaningTask> GetCleaningTasksByUUID(List<String> taskUuids)
        {
            List<CleaningTask> resultList = new List<CleaningTask>();
            try
            {
                foreach (String uuid in taskUuids) resultList.Add(binarySerializer.Deserialize<CleaningTask>(_connection.Table<DBCleaningTask>().FirstOrDefault(t => t.uuid == uuid).ObjectSerialization));
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
            return resultList;
        }

        /// <summary>
        /// Delete <see cref="CleaningTask"/> with <paramref name="id"/>
        /// </summary>
        /// <param name="id"><paramref name="id"/> of the <see cref="CleaningTask"/> that will be deleted</param>
        /// <returns><c>true</c> if deleted, otherwise <c>false</c></returns>
        public bool DeleteCleaningTask(long id)
        {
            try
            {
                _connection.Delete<DBCleaningTask>(id);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Deletes all <see cref="CleaningTask"/> with matching ID's from <paramref name="taskIds"/>
        /// </summary>
        /// <param name="taskIds"><see cref="List{T}"/> of all ID's that will be deleted</param>
        /// <returns><c>true</c> if all <see cref="CleaningTask"/> that matched the ID's were deleted, otherwise <c>false</c></returns>
        public bool DeleteCleaningTasks(List<long> taskIds)
        {
            try
            {
                foreach (long id in taskIds) _connection.Delete<DBCleaningTask>(id);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Add the <paramref name="task"/> to the local db. <para />
        /// Failsafe: If <paramref name="task"/> is already in db, calls <see cref="UpdateCleaningTask(CleaningTask)"/> instead
        /// </summary>
        /// <param name="task">to be added <see cref="CleaningTask"/></param>
        /// <returns><c>true</c> if added, otherwise <c>false</c></returns>
        public bool AddCleaningTask(CleaningTask task)
        {
            try
            {
                DBCleaningTask dbct = new DBCleaningTask();
                dbct.id = task.Id;
                dbct.uuid = task.BeaconObject.Beacon.Uuid;
                dbct.ObjectSerialization = binarySerializer.Serialize(task);
                _connection.Insert(dbct);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                Debug.WriteLine("Converts to PUT");
                return this.tryPut(task);
            }
        }

        /// <summary>
        /// Adds all <see cref="CleaningTask"/> contained in <paramref name="tasks"/> <para />
        /// Failsafe: If a given <see cref="CleaningTask"/> is already in db, calls <see cref="UpdateCleaningTask(CleaningTask)"/> instead
        /// </summary>
        /// <param name="tasks"><see cref="List{T}"/> of <see cref="CleaningTask"/> that have to be added to the db.</param>
        /// <returns></returns>
        public bool AddCleaningTasks(List<CleaningTask> tasks)
        {
            foreach (CleaningTask task in tasks)
            {
                try
                {
                    DBCleaningTask dbtt = new DBCleaningTask();
                    dbtt.id = task.Id;
                    dbtt.uuid = task.BeaconObject.Beacon.Uuid;
                    dbtt.ObjectSerialization = binarySerializer.Serialize(task);
                    _connection.Insert(dbtt);
                }
                catch (Exception ex)
                {
                    Debug.WriteLine(ex.ToString());
                    Debug.WriteLine("Converts to PUT");
                    this.tryPut(task);
                }
            }
            return true;
        }

        /// <summary>
        /// Updates matching <see cref="CleaningTask"/> contained in db
        /// </summary>
        /// <param name="task">new version of the contained <see cref="CleaningTask"/></param>
        /// <returns><c>true</c> if successful, otherwise <c>false</c></returns>
        public bool UpdateCleaningTask(CleaningTask task)
        {
            try
            {
                DBCleaningTask dbct = new DBCleaningTask();
                dbct.id = task.Id;
                dbct.uuid = task.BeaconObject.Beacon.Uuid;
                dbct.ObjectSerialization = binarySerializer.Serialize(task);
                _connection.Update(dbct);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Updates all matching <see cref="CleaningTask"/> from <paramref name="tasks"/>
        /// </summary>
        /// <param name="tasks"><see cref="List{T}"/> containing new versions of <see cref="CleaningTask"/></param>
        /// <returns><c>true</c> when all <see cref="CleaningTask"/> were updated, otherwise <c>false</c></returns>
        public bool UpdateCleaningTasks(List<CleaningTask> tasks)
        {
            try
            {
                foreach (CleaningTask task in tasks)
                {
                    DBCleaningTask dbct = new DBCleaningTask();
                    dbct.id = task.Id;
                    dbct.uuid = task.BeaconObject.Beacon.Uuid;
                    dbct.ObjectSerialization = binarySerializer.Serialize(task);
                    _connection.Update(dbct);
                }
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Deletes all <see cref="CleaningTask"/>
        /// </summary>
        /// <returns><c>true</c> if delete was successful, otherwise <c>false</c></returns>
        public bool clearAllCleaningTasks()
        {
            try
            {
                _connection.DropTable<DBCleaningTask>();
                _connection.CreateTable<DBCleaningTask>();
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        // Maintenance Tasks

        /// <summary>
        /// Get a <see cref="List{T}"/> containing all <see cref="MaintenanceTask"/>
        /// </summary>
        /// <returns><see cref="List{T}"/> with all <see cref="MaintenanceTask"/></returns>
        public List<MaintenanceTask> getAllMaintenanceTasks()
        {
            List<MaintenanceTask> resultList = new List<MaintenanceTask>();
            try
            {
                foreach (DBMaintenanceTask task in _connection.Table<DBMaintenanceTask>().ToList())
                {
                    MaintenanceTask mTask = binarySerializer.Deserialize<MaintenanceTask>(task.ObjectSerialization);
                    List<AbstractSubTask> nList = new List<AbstractSubTask>();
                    foreach (AbstractSubTask sub in mTask.Subtasks)
                    {
                        if (sub.GetType() == typeof(DBImageTask))
                        {
                            SubTaskImage img = new SubTaskImage(sub.Title, ((DBImageTask)sub).value);
                            nList.Add(img);
                        }
                        else
                        {
                            nList.Add(sub);
                        }
                    }
                    mTask.Subtasks = nList;
                    resultList.Add(mTask);
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
            return resultList;
        }

        /// <summary>
        /// Get <see cref="MaintenanceTask"/> with requested <paramref name="id"/>
        /// </summary>
        /// <param name="id">ID of the desired <see cref="MaintenanceTask"/></param>
        /// <returns><see cref="MaintenanceTask"/> if DB contains desired <paramref name="id"/>, <see langword="null"/> if DB does not contain <paramref name="id"/></returns>
        public MaintenanceTask GetMaintenanceTask(long id)
        {
            try
            {
                MaintenanceTask mTask = binarySerializer.Deserialize<MaintenanceTask>(_connection.Table<DBMaintenanceTask>().FirstOrDefault(t => t.id == id).ObjectSerialization);
                List<AbstractSubTask> nList = new List<AbstractSubTask>();
                foreach (AbstractSubTask sub in mTask.Subtasks)
                {
                    if (sub.GetType() == typeof(DBImageTask))
                    {
                        SubTaskImage img = new SubTaskImage(sub.Title, ((DBImageTask)sub).value);
                        nList.Add(img);
                    }
                    else
                    {
                        nList.Add(sub);
                    }
                }
                mTask.Subtasks = nList;
                return mTask;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return null;
            }
        }

        /// <summary>
        /// Get <see cref="List{T}"/> of type <see cref="MaintenanceTask"/> containing all requested tasks
        /// </summary>
        /// <param name="taskIds"><see cref="List{T}"/> of all requested ID's</param>
        /// <returns><see cref="List{T}"/> containing all found <see cref="MaintenanceTask"/> with requested ID's. Empty of none found</returns>
        public List<MaintenanceTask> GetMaintenanceTasks(List<long> taskIds)
        {
            List<MaintenanceTask> resultList = new List<MaintenanceTask>();
            try
            {
                foreach (long id in taskIds)
                {
                    MaintenanceTask mTask = binarySerializer.Deserialize<MaintenanceTask>(_connection.Table<DBMaintenanceTask>().FirstOrDefault(t => t.id == id).ObjectSerialization);
                    List<AbstractSubTask> nList = new List<AbstractSubTask>();
                    foreach (AbstractSubTask sub in mTask.Subtasks)
                    {
                        if (sub.GetType() == typeof(DBImageTask))
                        {
                            SubTaskImage img = new SubTaskImage(sub.Title, ((DBImageTask)sub).value);
                            nList.Add(img);
                        }
                        else
                        {
                            nList.Add(sub);
                        }
                    }
                    mTask.Subtasks = nList;
                    resultList.Add(mTask);
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
            return resultList;
        }

        /// <summary>
        /// Get <see cref="MaintenanceTask"/> with requested <paramref name="uuid"/>
        /// </summary>
        /// <param name="uuid"><paramref name="uuid"/> of the requested <see cref="MaintenanceTask"/></param>
        /// <returns><see cref="MaintenanceTask"/> if DB contains desired <paramref name="uuid"/><see langword="null"/> if DB does not contain <paramref name="uuid"/></returns>
        public MaintenanceTask GetMaintenanceTaskByUUID(String uuid)
        {
            try
            {
                MaintenanceTask mTask = binarySerializer.Deserialize<MaintenanceTask>(_connection.Table<DBMaintenanceTask>().FirstOrDefault(t => t.uuid == uuid).ObjectSerialization);
                List<AbstractSubTask> nList = new List<AbstractSubTask>();
                foreach (AbstractSubTask sub in mTask.Subtasks)
                {
                    if (sub.GetType() == typeof(DBImageTask))
                    {
                        SubTaskImage img = new SubTaskImage(sub.Title, ((DBImageTask)sub).value);
                        nList.Add(img);
                    }
                    else
                    {
                        nList.Add(sub);
                    }
                }
                mTask.Subtasks = nList;
                return mTask;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return null;
            }
        }

        /// <summary>
        /// Get <see cref="List{T}"/> of type <see cref="MaintenanceTask"/> containing all requested tasks
        /// </summary>
        /// <param name="taskUuids"><see cref="List{T}"/> of all requested ID's</param>
        /// <returns><see cref="List{T}"/> containing all found <see cref="MaintenanceTask"/> with requested UUID's. Empty of none found</returns>
        public List<MaintenanceTask> GetMaintenanceTasksByUUID(List<String> taskUuids)
        {
            List<MaintenanceTask> resultList = new List<MaintenanceTask>();
            try
            {
                foreach (String uuid in taskUuids)
                {
                    MaintenanceTask mTask = binarySerializer.Deserialize<MaintenanceTask>(_connection.Table<DBMaintenanceTask>().FirstOrDefault(t => t.uuid == uuid).ObjectSerialization);
                    List<AbstractSubTask> nList = new List<AbstractSubTask>();
                    foreach (AbstractSubTask sub in mTask.Subtasks)
                    {
                        if (sub.GetType() == typeof(DBImageTask))
                        {
                            SubTaskImage img = new SubTaskImage(sub.Title, ((DBImageTask)sub).value);
                            nList.Add(img);
                        }
                        else
                        {
                            nList.Add(sub);
                        }
                    }
                    mTask.Subtasks = nList;
                    resultList.Add(mTask);
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
            return resultList;
        }

        /// <summary>
        /// Delete <see cref="MaintenanceTask"/> with <paramref name="id"/>
        /// </summary>
        /// <param name="id"><paramref name="id"/> of the <see cref="MaintenanceTask"/> that will be deleted</param>
        /// <returns><c>true</c> if deleted, otherwise <c>false</c></returns>
        public bool DeleteMaintenanceTask(long id)
        {
            try
            {
                _connection.Delete<DBMaintenanceTask>(id);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Deletes all <see cref="MaintenanceTask"/> with matching ID's from <paramref name="taskIds"/>
        /// </summary>
        /// <param name="taskIds"><see cref="List{T}"/> of all ID's that will be deleted</param>
        /// <returns><c>true</c> if all <see cref="MaintenanceTask"/> that matched the ID's were deleted, otherwise <c>false</c></returns>
        public bool DeleteMaintenanceTasks(List<long> taskIds)
        {
            try
            {
                foreach (long id in taskIds) _connection.Delete<DBMaintenanceTask>(id);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Add the <paramref name="task"/> to the local db. <para />
        /// Failsafe: If <paramref name="task"/> is already in db, calls <see cref="UpdateMaintenanceTask(MaintenanceTask)"/> instead
        /// </summary>
        /// <param name="task">to be added <see cref="MaintenanceTask"/></param>
        /// <returns><c>true</c> if added, otherwise <c>false</c></returns>
        public bool AddMaintenanceTask(MaintenanceTask task)
        {
            try
            {
                DBMaintenanceTask dbmt = new DBMaintenanceTask();
                List<AbstractSubTask> nList = new List<AbstractSubTask>();
                dbmt.id = task.Id;
                dbmt.uuid = task.BeaconObject.Beacon.Uuid;
                foreach (AbstractSubTask sub in task.Subtasks)
                {
                    if (sub.GetType() == typeof(SubTaskImage))
                    {
                        DBImageTask img = new DBImageTask(sub.Id, sub.Title, ((SubTaskImage)sub).Image);
                        nList.Add(img);
                    } else {
                        nList.Add(sub);
                    }
                }
                task.Subtasks = nList;
                dbmt.ObjectSerialization = binarySerializer.Serialize(task);
                _connection.Insert(dbmt);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                Debug.WriteLine("Converts to PUT");
                return this.tryPut(task);
            }
        }

        /// <summary>
        /// Adds all <see cref="MaintenanceTask"/> contained in <paramref name="tasks"/> <para />
        /// Failsafe: If a given <see cref="MaintenanceTask"/> is already in db, calls <see cref="UpdateMaintenanceTask(MaintenanceTask)"/> instead
        /// </summary>
        /// <param name="tasks"><see cref="List{T}"/> of <see cref="MaintenanceTask"/> that have to be added to the db.</param>
        /// <returns></returns>
        public bool AddMaintenanceTasks(List<MaintenanceTask> tasks)
        {
            foreach (MaintenanceTask task in tasks)
            {
                try
                {
                    DBMaintenanceTask dbtt = new DBMaintenanceTask();
                    List<AbstractSubTask> nList = new List<AbstractSubTask>();
                    dbtt.id = task.Id;
                    dbtt.uuid = task.BeaconObject.Beacon.Uuid;
                    foreach (AbstractSubTask sub in task.Subtasks)
                    {
                        if (sub.GetType() == typeof(SubTaskImage))
                        {
                            DBImageTask img = new DBImageTask(sub.Id, sub.Title, ((SubTaskImage)sub).Image);
                            nList.Add(img);
                        }
                        else
                        {
                            nList.Add(sub);
                        }
                    }
                    task.Subtasks = nList;
                    dbtt.ObjectSerialization = binarySerializer.Serialize(task);
                    _connection.Insert(dbtt);
                }
                catch (Exception ex)
                {
                    Debug.WriteLine(ex.ToString());
                    Debug.WriteLine("Converts to PUT");
                    this.tryPut(task);
                }
            }
            return true;
        }

        /// <summary>
        /// Updates matching <see cref="MaintenanceTask"/> contained in db
        /// </summary>
        /// <param name="task">new version of the contained <see cref="MaintenanceTask"/></param>
        /// <returns><c>true</c> if successful, otherwise <c>false</c></returns>
        public bool UpdateMaintenanceTask(MaintenanceTask task)
        {
            try
            {
                DBMaintenanceTask dbmt = new DBMaintenanceTask();
                List<AbstractSubTask> nList = new List<AbstractSubTask>();
                dbmt.id = task.Id;
                dbmt.uuid = task.BeaconObject.Beacon.Uuid;
                foreach (AbstractSubTask sub in task.Subtasks)
                {
                    if (sub.GetType() == typeof(SubTaskImage))
                    {
                        DBImageTask img = new DBImageTask(sub.Id, sub.Title, ((SubTaskImage)sub).Image);
                        nList.Add(img);
                    }
                    else
                    {
                        nList.Add(sub);
                    }
                }
                task.Subtasks = nList;
                dbmt.ObjectSerialization = binarySerializer.Serialize(task);
                _connection.Update(dbmt);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Updates all matching <see cref="MaintenanceTask"/> from <paramref name="tasks"/>
        /// </summary>
        /// <param name="tasks"><see cref="List{T}"/> containing new versions of <see cref="MaintenanceTask"/></param>
        /// <returns><c>true</c> when all <see cref="MaintenanceTask"/> were updated, otherwise <c>false</c></returns>
        public bool UpdateMaintenanceTasks(List<MaintenanceTask> tasks)
        {
            try
            {
                foreach (MaintenanceTask task in tasks)
                {
                    DBMaintenanceTask dbmt = new DBMaintenanceTask();
                    List<AbstractSubTask> nList = new List<AbstractSubTask>();
                    dbmt.id = task.Id;
                    dbmt.uuid = task.BeaconObject.Beacon.Uuid;
                    foreach (AbstractSubTask sub in task.Subtasks)
                    {
                        if (sub.GetType() == typeof(SubTaskImage))
                        {
                            DBImageTask img = new DBImageTask(sub.Id, sub.Title, ((SubTaskImage)sub).Image);
                            nList.Add(img);
                        }
                        else
                        {
                            nList.Add(sub);
                        }
                    }
                    task.Subtasks = nList;
                    dbmt.ObjectSerialization = binarySerializer.Serialize(task);
                    _connection.Update(dbmt);
                }
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Deletes all <see cref="MaintenanceTask"/>
        /// </summary>
        /// <returns><c>true</c> if delete was successful, otherwise <c>false</c></returns>
        public bool clearAllMaintenanceTasks()
        {
            try
            {
                _connection.DropTable<DBMaintenanceTask>();
                _connection.CreateTable<DBMaintenanceTask>();
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }


        // Transport Task

        /// <summary>
        /// Get a <see cref="List{T}"/> containing all <see cref="TransportTask"/>
        /// </summary>
        /// <returns><see cref="List{T}"/> with all <see cref="TransportTask"/></returns>
        public List<TransportTask> getAllTransportTasks()
        {
            List<TransportTask> ctList = new List<TransportTask>();
            try
            {
                foreach (DBTransportTask task in _connection.Table<DBTransportTask>().ToList())
                {
                    ctList.Add(binarySerializer.Deserialize<TransportTask>(task.ObjectSerialization));
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
            return ctList;
        }

        /// <summary>
        /// Get <see cref="TransportTask"/> with requested <paramref name="id"/>
        /// </summary>
        /// <param name="id">ID of the desired <see cref="TransportTask"/></param>
        /// <returns><see cref="TransportTask"/> if DB contains desired <paramref name="id"/>, <see langword="null"/> if DB does not contain <paramref name="id"/></returns>
        public TransportTask GetTransportTask(long id)
        {
            try
            {
                return binarySerializer.Deserialize<TransportTask>(_connection.Table<DBTransportTask>().FirstOrDefault(t => t.id == id).ObjectSerialization);
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return null;
            }
        }

        /// <summary>
        /// Get <see cref="List{T}"/> of type <see cref="TransportTask"/> containing all requested tasks
        /// </summary>
        /// <param name="taskIds"><see cref="List{T}"/> of all requested ID's</param>
        /// <returns><see cref="List{T}"/> containing all found <see cref="TransportTask"/> with requested ID's. Empty of none found</returns>
        public List<TransportTask> GetTransportTasks(List<long> taskIds)
        {
            List<TransportTask> resultList = new List<TransportTask>();
            try
            {
                foreach (long id in taskIds) resultList.Add(binarySerializer.Deserialize<TransportTask>(_connection.Table<DBTransportTask>().FirstOrDefault(t => t.id == id).ObjectSerialization));
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
            return resultList;
        }


        /// <summary>
        /// Get <see cref="TransportTask"/> with requested <paramref name="uuid"/>
        /// </summary>
        /// <param name="uuid"><paramref name="uuid"/> of the requested <see cref="TransportTask"/></param>
        /// <returns><see cref="TransportTask"/> if DB contains desired <paramref name="uuid"/><see langword="null"/> if DB does not contain <paramref name="uuid"/></returns>
        public TransportTask GetTransportTaskByUUID(String uuid)
        {
            try
            {
                return binarySerializer.Deserialize<TransportTask>(_connection.Table<DBTransportTask>().FirstOrDefault(t => t.uuid == uuid).ObjectSerialization);
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return null;
            }
        }

        /// <summary>
        /// Get <see cref="List{T}"/> of type <see cref="TransportTask"/> containing all requested tasks
        /// </summary>
        /// <param name="taskUuids"><see cref="List{T}"/> of all requested ID's</param>
        /// <returns><see cref="List{T}"/> containing all found <see cref="TransportTask"/> with requested UUID's. Empty of none found</returns>
        public List<TransportTask> GetTransportTasksByUUID(List<String> taskUuids)
        {
            List<TransportTask> resultList = new List<TransportTask>();
            try
            {
                foreach (String uuid in taskUuids) resultList.Add(binarySerializer.Deserialize<TransportTask>(_connection.Table<DBTransportTask>().FirstOrDefault(t => t.uuid == uuid).ObjectSerialization));
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }
            return resultList;
        }

        /// <summary>
        /// Delete <see cref="TransportTask"/> with <paramref name="id"/>
        /// </summary>
        /// <param name="id"><paramref name="id"/> of the <see cref="TransportTask"/> that will be deleted</param>
        /// <returns><c>true</c> if deleted, otherwise <c>false</c></returns>
        public bool DeleteTransportTask(long id)
        {
            try
            {
                _connection.Delete<DBTransportTask>(id);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Deletes all <see cref="TransportTask"/> with matching ID's from <paramref name="taskIds"/>
        /// </summary>
        /// <param name="taskIds"><see cref="List{T}"/> of all ID's that will be deleted</param>
        /// <returns><c>true</c> if all <see cref="TransportTask"/> that matched the ID's were deleted, otherwise <c>false</c></returns>
        public bool DeleteTransportTasks(List<long> taskIds)
        {
            try
            {
                foreach (long id in taskIds) _connection.Delete<DBTransportTask>(id);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Add the <paramref name="task"/> to the local db. <para />
        /// Failsafe: If <paramref name="task"/> is already in db, calls <see cref="UpdateTransportTask(TransportTask)"/> instead
        /// </summary>
        /// <param name="task">to be added <see cref="TransportTask"/></param>
        /// <returns><c>true</c> if added, otherwise <c>false</c></returns>
        public bool AddTransportTask(TransportTask task)
        {
            try
            {
                DBTransportTask dbtt = new DBTransportTask();
                dbtt.id = task.Id;
                dbtt.uuid = task.BeaconObject.Beacon.Uuid;
                dbtt.ObjectSerialization = binarySerializer.Serialize(task);
                _connection.Insert(dbtt);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                Debug.WriteLine("Converts to PUT");
                return this.tryPut(task);
            }
        }

        /// <summary>
        /// Adds all <see cref="TransportTask"/> contained in <paramref name="tasks"/> <para />
        /// Failsafe: If a given <see cref="TransportTask"/> is already in db, calls <see cref="UpdateTransportTask(TransportTask)"/> instead
        /// </summary>
        /// <param name="tasks"><see cref="List{T}"/> of <see cref="TransportTask"/> that have to be added to the db.</param>
        /// <returns></returns>
        public bool AddTransportTasks(List<TransportTask> tasks)
        {
            foreach (TransportTask task in tasks)
            {
                try
                {
                    DBTransportTask dbtt = new DBTransportTask();
                    dbtt.id = task.Id;
                    dbtt.uuid = task.BeaconObject.Beacon.Uuid;
                    dbtt.ObjectSerialization = binarySerializer.Serialize(task);
                    _connection.Insert(dbtt);
                } catch (Exception ex)
                {
                    Debug.WriteLine(ex.ToString());
                    Debug.WriteLine("Converts to PUT");
                    this.tryPut(task);
                }
            }
            return true;
        }

        /// <summary>
        /// Updates matching <see cref="TransportTask"/> contained in db
        /// </summary>
        /// <param name="task">new version of the contained <see cref="TransportTask"/></param>
        /// <returns><c>true</c> if successful, otherwise <c>false</c></returns>
        public bool UpdateTransportTask(TransportTask task)
        {
            try
            {
                DBTransportTask dbtt = new DBTransportTask();
                dbtt.id = task.Id;
                dbtt.uuid = task.BeaconObject.Beacon.Uuid;
                dbtt.ObjectSerialization = binarySerializer.Serialize(task);
                _connection.Update(dbtt);
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Updates all matching <see cref="TransportTask"/> from <paramref name="tasks"/>
        /// </summary>
        /// <param name="tasks"><see cref="List{T}"/> containing new versions of <see cref="TransportTask"/></param>
        /// <returns><c>true</c> when all <see cref="TransportTask"/> were updated, otherwise <c>false</c></returns>
        public bool UpdateTransportTasks(List<TransportTask> tasks)
        {
            try
            {
                foreach (TransportTask task in tasks)
                {
                    DBTransportTask dbtt = new DBTransportTask();
                    dbtt.id = task.Id;
                    dbtt.uuid = task.BeaconObject.Beacon.Uuid;
                    dbtt.ObjectSerialization = binarySerializer.Serialize(task);
                    _connection.Update(dbtt);
                }
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Deletes all <see cref="TransportTask"/>
        /// </summary>
        /// <returns><c>true</c> if delete was successful, otherwise <c>false</c></returns>
        public bool clearAllTransportTasks()
        {
            try
            {
                _connection.DropTable<DBTransportTask>();
                _connection.CreateTable<DBTransportTask>();
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        // All Tasks


        /// <summary>
        /// Deletes all <see cref="Task"/> in db
        /// </summary>
        /// <returns><c>true</c> if delete was successful, otherwise <c>false</c></returns>
        public bool clearAllTasks()
        {
            try
            {
                _connection.DropTable<DBCleaningTask>();
                _connection.CreateTable<DBCleaningTask>();

                _connection.DropTable<DBMaintenanceTask>();
                _connection.CreateTable<DBMaintenanceTask>();

                _connection.DropTable<DBTransportTask>();
                _connection.CreateTable<DBTransportTask>();
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                return false;
            }
        }

        /// <summary>
        /// Get <see cref="List{T}"/> of type <see cref="Task"/> containing all requested tasks
        /// </summary>
        /// <param name="taskIds"><see cref="List{T}"/> of all requested ID's</param>
        /// <returns><see cref="List{T}"/> containing all found <see cref="Task"/> with requested ID's. Empty of none found</returns>
        public List<Task> getTasks(List<long> taskIds)
        {
            List<Task> taskList = new List<Task>();
            foreach (Task tsk in GetCleaningTasks(taskIds))
            {
                taskList.Add(tsk);
            }
            foreach (Task tsk in GetMaintenanceTasks(taskIds))
            {
                taskList.Add(tsk);
            }
            foreach (Task tsk in GetTransportTasks(taskIds))
            {
                taskList.Add(tsk);
            }
            return taskList;
        }

        /// <summary>
        /// Get a <see cref="List{T}"/> containing all <see cref="Task"/>
        /// </summary>
        /// <returns><see cref="List{T}"/> with all <see cref="Task"/></returns>
        public List<Task> getAllTasks()
        {
            List<Task> taskList = new List<Task>();
            foreach (Task task in getAllCleaningTasks())
            {
                taskList.Add(task);
            }
            foreach (Task task in getAllMaintenanceTasks())
            {
                taskList.Add(task);
            }
            foreach (Task task in getAllTransportTasks())
            {
                taskList.Add(task);
            }
            return taskList;
        }

        /// <summary>
        /// Runs a self-test on the DB. Tries to test all methods and checks if Add and Get function correctly
        /// </summary>
        /// <returns><c>true</c> when the self-test was successful, otherwise <c>false</c></returns>

        //[Conditional("db_test")]
        public bool testDB()
        {
            clearAllTasks();
            CleaningTask testCT = new CleaningTask();
            MaintenanceTask testMT = new MaintenanceTask();
            TransportTask testTT = new TransportTask();

            // Prefill test Tasks

            testCT.Id = testMT.Id = testTT.Id = 1;

            testCT.Status = 1;

            testCT.Name = testMT.Name = testTT.Name = "Task Test Name";
            testCT.Level = testMT.Level = testTT.Level = 1;
            testCT.Description = testMT.Description = testTT.Description = "Test Text";

            testCT.Creator = new User();
            testMT.Creator = new User();
            testTT.Creator = new User();

            testCT.Creator.Role = new Role();
            testMT.Creator.Role = new Role();
            testTT.Creator.Role = new Role();

            testCT.Editor = new User();
            testMT.Editor = new User();
            testTT.Editor = new User();

            testCT.Editor.Role = new Role();
            testMT.Editor.Role = new Role();
            testTT.Editor.Role = new Role();

            testCT.Creator.Password = "";
            testCT.Editor.Password = "";

            testCT.Creator.Name = testMT.Creator.Name = testTT.Creator.Name = "Creator Name";
            testCT.Creator.Email = testMT.Creator.Email = testTT.Creator.Email = "Test@creator.mail.com";
            testCT.Creator.LastUpdate = testMT.Creator.LastUpdate = testTT.Creator.LastUpdate = "Time thingie";
            testCT.Creator.Role.Name = testMT.Creator.Role.Name = testTT.Creator.Role.Name = "Test Role Creator Name";

            testCT.Editor.Name = testMT.Editor.Name = testTT.Editor.Name = "Editor Name";
            testCT.Editor.Email = testMT.Editor.Email = testTT.Editor.Email = "Test@editor.mail.com";
            testCT.Editor.LastUpdate = testMT.Editor.LastUpdate = testTT.Editor.LastUpdate = "Time thingie";
            testCT.Editor.Role.Name = testMT.Editor.Role.Name = testTT.Editor.Role.Name = "Test Role Editor Name";

            SubTaskCheckbox check = new SubTaskCheckbox(1,"Test Checkbox", true);

            SubTaskSlider slider = new SubTaskSlider(3,"Test Slider", 50);

            testMT.Subtasks = new List<AbstractSubTask>();
            testMT.Subtasks.Add(check);
            testMT.Subtasks.Add(slider);

            testMT.BeaconObject = new BeaconObject();
            testCT.BeaconObject = new BeaconObject();
            testTT.BeaconObject = new BeaconObject();

            testMT.BeaconObject.Beacon = new Beacon();
            testCT.BeaconObject.Beacon = new Beacon();
            testTT.BeaconObject.Beacon = new Beacon();

            // Begin selftest

            try
            {
                Debug.Assert(AddCleaningTask(testCT), "Insert cleaning task failed");
                testCT.Name = "New Task Test Name";
                Debug.Assert(UpdateCleaningTask(testCT), "Update cleaning task failed");
                Debug.Assert(GetCleaningTask(testCT.Id).Name == testCT.Name, "Update did not work correctly!");
                Debug.Assert(DeleteCleaningTask(testCT.Id) == true, "Delete cleaning task failed");
                testCT.Name = "Task Test Name";


                Debug.Assert(AddCleaningTask(testCT), "Insert cleaning task failed");
                Debug.Assert(AddMaintenanceTask(testMT), "Insert maintenance task failed");
                Debug.Assert(AddTransportTask(testTT), "Insert transport task failed");

                // i can be set to self-test N-many Tasks
                for (int i = 2; i < 3; i++)
                {
                    testCT.Id = i;
                    testMT.Id = i;
                    testTT.Id = i;
                    AddCleaningTask(testCT);
                    AddMaintenanceTask(testMT);
                    AddTransportTask(testTT);
                }

            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message);
                Debug.WriteLine(ex.GetBaseException());
                Debug.WriteLine(ex.ToString());
                return false;
            }

            try
            {
                CleaningTask dbCT = this.GetCleaningTask(1);
                MaintenanceTask dbMT = this.GetMaintenanceTask(1);
                TransportTask dbTT = this.GetTransportTask(1);

                Debug.Assert(GetCleaningTask(testCT.Id) == null ? false : true, "Get cleaning task failed");
                Debug.Assert(GetMaintenanceTask(testMT.Id) == null ? false : true, "Get maintenance task failed");
                Debug.Assert(GetTransportTask(testTT.Id) == null ? false : true, "Get transport task failed");

                // i has to be lower or equal to the earlier i
                for (int i = 2; i < 3; i++)
                {
                    dbCT = this.GetCleaningTask(i);
                    dbMT = this.GetMaintenanceTask(i);
                    dbTT = this.GetTransportTask(i);
                }

                // Check every field. If not equal, Add or Get failed

                Debug.Assert(dbCT.Name == testCT.Name, "CT names failed");
                Debug.Assert(dbMT.Name == testMT.Name, "MT names failed");
                Debug.Assert(dbTT.Name == testTT.Name, "TT names failed");
                Debug.Assert((dbCT.Name == dbMT.Name) && (dbCT.Name == dbTT.Name) && (dbMT.Name == dbTT.Name), "db task names failed");

                Debug.Assert(dbCT.Level == testCT.Level, "CT levels failed");
                Debug.Assert(dbMT.Level == testMT.Level, "MT levels failed");
                Debug.Assert(dbTT.Level == testTT.Level, "TT levels failed");
                Debug.Assert((dbCT.Level == dbMT.Level) && (dbCT.Level == dbTT.Level) && (dbMT.Level == dbTT.Level), "db task levels failed");

                Debug.Assert(dbCT.Description == testCT.Description, "CT discription failed");
                Debug.Assert(dbMT.Description == testMT.Description, "MT discription failed");
                Debug.Assert(dbTT.Description == testTT.Description, "TT discription failed");
                Debug.Assert((dbCT.Description == dbMT.Description) && (dbCT.Description == dbTT.Description) && (dbMT.Description == dbTT.Description), "db task discriptions failed");
                
                Debug.Assert(dbCT.Creator.Name == testCT.Creator.Name, "CT creator name failed");
                Debug.Assert(dbMT.Creator.Name == testMT.Creator.Name, "MT creator name failed");
                Debug.Assert(dbTT.Creator.Name == testTT.Creator.Name, "TT creator name failed");
                Debug.Assert((dbCT.Creator.Name == dbMT.Creator.Name) && (dbCT.Creator.Name == dbTT.Creator.Name) && (dbMT.Creator.Name == dbTT.Creator.Name), "db creators names failed");

                Debug.Assert(dbCT.Creator.Email == testCT.Creator.Email, "CT creator email failed");
                Debug.Assert(dbMT.Creator.Email == testMT.Creator.Email, "MT creator email failed");
                Debug.Assert(dbTT.Creator.Email == testTT.Creator.Email, "TT creator email failed");
                Debug.Assert((dbCT.Creator.Email == dbMT.Creator.Email) && (dbCT.Creator.Email == dbTT.Creator.Email) && (dbMT.Creator.Email == dbTT.Creator.Email), "db creators email failed");

                Debug.Assert(dbCT.Creator.LastUpdate == testCT.Creator.LastUpdate, "CT creator last update failed");
                Debug.Assert(dbMT.Creator.LastUpdate == testMT.Creator.LastUpdate, "MT creator last update failed");
                Debug.Assert(dbTT.Creator.LastUpdate == testTT.Creator.LastUpdate, "TT creator last update failed");
                Debug.Assert((dbCT.Creator.LastUpdate == dbMT.Creator.LastUpdate) && (dbCT.Creator.LastUpdate == dbTT.Creator.LastUpdate) && (dbMT.Creator.LastUpdate == dbTT.Creator.LastUpdate), "db creators last update failed");

                Debug.Assert(dbCT.Creator.Role.Name == testCT.Creator.Role.Name, "CT creator role name failed");
                Debug.Assert(dbMT.Creator.Role.Name == testMT.Creator.Role.Name, "MT creator role name failed");
                Debug.Assert(dbTT.Creator.Role.Name == testTT.Creator.Role.Name, "TT creator role name  failed");
                Debug.Assert((dbCT.Creator.Role.Name == dbMT.Creator.Role.Name) && (dbCT.Creator.Role.Name == dbTT.Creator.Role.Name) && (dbMT.Creator.Role.Name == dbTT.Creator.Role.Name), "db creators role names failed");

                Debug.Assert(dbCT.Editor.Name == testCT.Editor.Name, "CT creator name failed");
                Debug.Assert(dbMT.Editor.Name == testMT.Editor.Name, "MT creator name failed");
                Debug.Assert(dbTT.Editor.Name == testTT.Editor.Name, "TT creator name failed");
                Debug.Assert((dbCT.Editor.Name == dbMT.Editor.Name) && (dbCT.Editor.Name == dbTT.Editor.Name) && (dbMT.Editor.Name == dbTT.Editor.Name), "db editors names failed");

                Debug.Assert(dbCT.Editor.Email == testCT.Editor.Email, "CT creator email failed");
                Debug.Assert(dbMT.Editor.Email == testMT.Editor.Email, "MT creator email failed");
                Debug.Assert(dbTT.Editor.Email == testTT.Editor.Email, "TT creator email failed");
                Debug.Assert((dbCT.Editor.Email == dbMT.Editor.Email) && (dbCT.Editor.Email == dbTT.Editor.Email) && (dbMT.Editor.Email == dbTT.Editor.Email), "db editors email failed");

                Debug.Assert(dbCT.Editor.LastUpdate == testCT.Editor.LastUpdate, "CT creator last update failed");
                Debug.Assert(dbMT.Editor.LastUpdate == testMT.Editor.LastUpdate, "MT creator last update failed");
                Debug.Assert(dbTT.Editor.LastUpdate == testTT.Editor.LastUpdate, "TT creator last update failed");
                Debug.Assert((dbCT.Editor.LastUpdate == dbMT.Editor.LastUpdate) && (dbCT.Editor.LastUpdate == dbTT.Editor.LastUpdate) && (dbMT.Editor.LastUpdate == dbTT.Editor.LastUpdate), "db editors last update failed");

                Debug.Assert(dbCT.Editor.Role.Name == testCT.Editor.Role.Name, "CT creator role name failed");
                Debug.Assert(dbMT.Editor.Role.Name == testMT.Editor.Role.Name, "MT creator role name failed");
                Debug.Assert(dbTT.Editor.Role.Name == testTT.Editor.Role.Name, "TT creator role name  failed");
                Debug.Assert((dbCT.Editor.Role.Name == dbMT.Editor.Role.Name) && (dbCT.Editor.Role.Name == dbTT.Editor.Role.Name) && (dbMT.Editor.Role.Name == dbTT.Editor.Role.Name), "db editors role names failed");

                Debug.Assert(((SubTaskCheckbox)dbMT.Subtasks.ElementAt(0)).Title == check.Title, "MT checkbox subtask name failed");
                Debug.Assert(((SubTaskCheckbox)dbMT.Subtasks.ElementAt(0)).Value == check.Value, "MT checkbox subtask value failed");

                Debug.Assert(((SubTaskSlider)dbMT.Subtasks.ElementAt(2)).Title == slider.Title, "MT slider subtask name failed");
                Debug.Assert(((SubTaskSlider)dbMT.Subtasks.ElementAt(2)).Value == slider.Value, "MT slider subtask value failed");

                // Try AddTasks for every Task Type

                // CleaningTask

                List<CleaningTask> ctList = new List<CleaningTask>();
                clearAllTasks();
                CleaningTask listCTo = new CleaningTask();
                CleaningTask listCTz = new CleaningTask();
                CleaningTask listCTd = new CleaningTask();
                listCTo.Id = 1;
                listCTo.Name = testCT.Name;
                ctList.Add(listCTo);
                listCTz.Id = 2;
                listCTz.Name = testCT.Name;
                ctList.Add(listCTz);
                listCTd.Id = 3;
                listCTd.Name = testCT.Name;
                ctList.Add(listCTd);
                Debug.Assert(AddCleaningTasks(ctList) == true ? true : false, "add cleaning tasks failed");

                // MaintenanceTask

                List<MaintenanceTask> mtList = new List<MaintenanceTask>();
                MaintenanceTask listMTo = new MaintenanceTask();
                MaintenanceTask listMTz = new MaintenanceTask();
                MaintenanceTask listMTd = new MaintenanceTask();
                listMTo.Id = 1;
                listMTo.Name = testMT.Name;
                mtList.Add(listMTo);
                listMTz.Id = 2;
                listMTz.Name = testMT.Name;
                mtList.Add(listMTz);
                listMTd.Id = 3;
                listMTd.Name = testMT.Name;
                mtList.Add(listMTd);
                Debug.Assert(AddMaintenanceTasks(mtList) == true ? true : false, "add cleaning tasks failed");

                // TransportTask

                List<TransportTask> ttList = new List<TransportTask>();
                TransportTask listTTo = new TransportTask();
                TransportTask listTTz = new TransportTask();
                TransportTask listTTd = new TransportTask();
                listTTo.Id = 1;
                listTTo.Name = testTT.Name;
                ttList.Add(listTTo);
                listTTz.Id = 2;
                listTTz.Name = testTT.Name;
                ttList.Add(listTTz);
                listTTd.Id = 3;
                listTTd.Name = testTT.Name;
                ttList.Add(listTTd);
                Debug.Assert(AddTransportTasks(ttList) == true ? true : false, "add cleaning tasks failed");

                // Lists of ID's for GetTasks

                List<long> ctidlist = new List<long>();
                List<long> mtidlist = new List<long>();
                List<long> ttidlist = new List<long>();


                CleaningTask svct = GetCleaningTask(3);
                MaintenanceTask svmt = GetMaintenanceTask(3);
                TransportTask svtt = GetTransportTask(3);

                Debug.Assert(svct.Name == testCT.Name, "get cleaning task failed");
                Debug.Assert(svmt.Name == testMT.Name, "get maintenance task failed");
                Debug.Assert(svtt.Name == testTT.Name, "get transport task failed");

                ctidlist.Add(1);
                ctidlist.Add(2);
                ctidlist.Add(3);

                mtidlist.Add(1);
                mtidlist.Add(2);
                mtidlist.Add(3);

                ttidlist.Add(1);
                ttidlist.Add(2);
                ttidlist.Add(3);

                // GetCleaningTasks and check first Task for consitency

                CleaningTask ctsktst = GetCleaningTasks(ctidlist).ElementAt(0);
                Debug.Assert(ctsktst.Name == testCT.Name, "Get cleaning tasks failed");

                // GetMaintenanceTasks and check first Task for consitency

                MaintenanceTask mtsktst = GetMaintenanceTasks(mtidlist).ElementAt(0);
                Debug.Assert(mtsktst.Name == testMT.Name, "Get maintenance tasks failed");

                // GetTransportTasks and check first Task for consitency

                TransportTask ttsktst = GetTransportTasks(ttidlist).ElementAt(0);
                Debug.Assert(ttsktst.Name == testTT.Name, "Get transport tasks failed");

                // Create Lists for Multi-Update

                List<CleaningTask> ctdifflist = new List<CleaningTask>();
                List<MaintenanceTask> mtdifflist = new List<MaintenanceTask>();
                List<TransportTask> ttdifflist = new List<TransportTask>();

                // Pre-Fill new Different Tasks

                CleaningTask listCTodiff = new CleaningTask();
                CleaningTask listCTzdiff = new CleaningTask();
                CleaningTask listCTddiff = new CleaningTask();

                MaintenanceTask listMTodiff = new MaintenanceTask();
                MaintenanceTask listMTzdiff = new MaintenanceTask();
                MaintenanceTask listMTddiff = new MaintenanceTask();

                TransportTask listTTodiff = new TransportTask();
                TransportTask listTTzdiff = new TransportTask();
                TransportTask listTTddiff = new TransportTask();

                listCTodiff.Id = 1;
                listMTodiff.Id = 1;
                listTTodiff.Id = 1;


                listCTodiff.Name = "Diff";
                ctdifflist.Add(listCTodiff);
                listMTodiff.Name = "Diff";
                mtdifflist.Add(listMTodiff);
                listTTodiff.Name = "Diff";
                ttdifflist.Add(listTTodiff);

                listCTzdiff.Id = 2;
                listMTzdiff.Id = 2;
                listTTzdiff.Id = 2;

                listCTzdiff.Name = "Diff2";
                ctdifflist.Add(listCTzdiff);
                listMTzdiff.Name = "Diff2";
                mtdifflist.Add(listMTzdiff);
                listTTzdiff.Name = "Diff2";
                ttdifflist.Add(listTTzdiff);

                listCTddiff.Id = 3;
                listMTddiff.Id = 3;
                listTTddiff.Id = 3;

                listCTddiff.Name = "Diff3";
                ctdifflist.Add(listCTddiff);
                listMTddiff.Name = "Diff3";
                mtdifflist.Add(listMTddiff);
                listTTddiff.Name = "Diff3";
                ttdifflist.Add(listTTddiff);

                // TODO Add lastEdited Values for Update!!

                Debug.Assert(UpdateCleaningTask(testCT) == true ? true : false, "update cleaning task failed");
                Debug.Assert(UpdateMaintenanceTask(testMT) == true ? true : false, "update maintenance task failed");
                Debug.Assert(UpdateTransportTask(testTT) == true ? true : false, "update transport task failed");


                Debug.Assert(UpdateCleaningTasks(ctdifflist) == true ? true : false, "update cleaning tasks failed");
                CleaningTask lstct = GetCleaningTask(3);
                Debug.Assert(lstct.Name == "Diff3", "upadte cleaning tasks failed");
                bool bt = UpdateMaintenanceTasks(mtdifflist);
                Debug.Assert(bt, "update maintenance tasks failed");
                MaintenanceTask lstmt = GetMaintenanceTask(3);
                Debug.Assert(lstmt.Name == "Diff3", "upadte maintenance tasks failed");
                Debug.Assert(UpdateTransportTasks(ttdifflist) == true ? true : false, "update transport tasks failed");
                TransportTask lsttt = GetTransportTask(3);
                Debug.Assert(lsttt.Name == "Diff3", "upadte transport tasks failed");

                // Create Lists for empty check

                List<CleaningTask> emptyCT = new List<CleaningTask>();
                List<TransportTask> emptyTT = new List<TransportTask>();
                List<MaintenanceTask> emptyMT = new List<MaintenanceTask>();

                List<long> deleteIds = new List<long>();
                deleteIds.Add(1);
                deleteIds.Add(2);
                deleteIds.Add(3);

                // Delete the ID's and check for empty return.

                bool deleted = DeleteCleaningTasks(deleteIds);
                emptyCT = GetCleaningTasks(deleteIds);
                Debug.Assert(emptyCT.Count() == 0, "delete cleaning tasks failed");

                deleted = DeleteTransportTasks(deleteIds);
                emptyTT = GetTransportTasks(deleteIds);
                Debug.Assert(emptyTT.Count() == 0, "delete cleaning tasks failed");

                deleted = DeleteMaintenanceTasks(deleteIds);
                emptyMT = GetMaintenanceTasks(deleteIds);
                Debug.Assert(emptyMT.Count() == 0, "delete cleaning tasks failed");

            }
            catch (Exception ex)
            {
                this.clearAllTasks();
                Debug.WriteLine(ex.ToString());
                return false;
            }
            this.clearAllTasks();
            Debug.WriteLine("Test successful");
            return true;
        }


        /// <summary>
        /// If in Debug-Mode, can be used to Pre-Fill DB with tasks without the need of a real online DB
        /// </summary>
        /// <param name="nelems">Number of <see cref="Task"/> that wish to be added. (N*3 elements will be added, since every <see cref="Task"/> type will be created N times)</param>
        /// <returns><c>true</c> when pre-fill was successful, otherwise <c>false</c></returns>
        public bool prefillDB(int nelems)
        {
            Console.WriteLine(System.Environment.NewLine);
            Console.WriteLine(System.Environment.NewLine);
            Console.WriteLine("++++++++++++++++++++++++++++ DB PREFILL");
            Console.WriteLine(System.Environment.NewLine);
            Console.WriteLine(System.Environment.NewLine);
            clearAllTasks();
            CleaningTask ct;
            MaintenanceTask mt;
            TransportTask tt;
            BeaconObject bO;
            Beacon b;
            List<AbstractSubTask> subT = new List<AbstractSubTask>();
            SubTaskSlider subS = new SubTaskSlider(1,"Slider",5);
            SubTaskCheckbox subC = new SubTaskCheckbox(2,"Check", false);

            subT.Add(subS);
            subT.Add(subC);

            nelems *= 3;
            Random r = new Random();
            for (int i = 0; i < nelems; i += 3)
            {
                ct = new CleaningTask();
                mt = new MaintenanceTask();
                mt.Subtasks = new List<AbstractSubTask>(subT);

                tt = new TransportTask();

                b = new Beacon();
                b.Uuid = "" + i;
                b.Major = 1;
                b.Minor = 1;
                bO = new BeaconObject();
                bO.BeaconObjectType = "Schau an ein Type";
                bO.Beacon = b;
                bO.Name = "Beacon" + i;
                bO.Picture = null;
                bO.Location = new Location();

                ct.BeaconObject = mt.BeaconObject = tt.BeaconObject = bO;

                // Offset in ID since real DB wont have same id on 2 diff. taks types. 
                // Consistent PrimKey
                ct.Id = i;
                mt.Id = i + 1;
                tt.Id = i + 2;
                ct.Name = "Cleaning Task" + ct.Id;
                mt.Name = "Maintenance Task" + mt.Id;
                tt.Name = "Transport Task" + tt.Id;
                ct.Level = mt.Level = tt.Level = i + 1;
                tt.TargetLocation = new Location();

                ct.Status = mt.Status = tt.Status = 1;

                ct.Description = mt.Description = tt.Description = "Test discription for this task";
                ct.Creator = new User();
                mt.Creator = new User();
                tt.Creator = new User();

                ct.Creator.Name = mt.Creator.Name = tt.Creator.Name = "Test Creator Name";

                ct.Creator.Email = mt.Creator.Email = tt.Creator.Email = "creator@test.mail.com";
                ct.Creator.Password = mt.Creator.Password = tt.Creator.Password = "admin";
                ct.Creator.Role = new Role();
                mt.Creator.Role = new Role();
                tt.Creator.Role = new Role();

                ct.Creator.Role.Name = mt.Creator.Role.Name = tt.Creator.Role.Name = "Test Role Name";

                ct.Editor = new User();
                mt.Editor = new User();
                tt.Editor = new User();

                ct.Editor.Name = mt.Editor.Name = tt.Editor.Name = "Test Editor Name";

                ct.Editor.Email = mt.Editor.Email = tt.Editor.Email = "editor@test.mail.com";
                ct.Editor.Password = mt.Editor.Password = tt.Editor.Password = "admin";
                ct.Editor.Role = new Role();
                mt.Editor.Role = new Role();
                tt.Editor.Role = new Role();

                ct.Editor.Role.Name = mt.Editor.Role.Name = tt.Editor.Role.Name = "Test Role Name";
                Debug.WriteLine("DATABASEprefill........................................id" + ct.Id + " uuid:" + ct.BeaconObject.Beacon.Uuid);
                Debug.WriteLine("DATABASEprefill........................................id" + mt.Id + " uuid:" + mt.BeaconObject.Beacon.Uuid);
                Debug.WriteLine("DATABASEprefill........................................id" + tt.Id + " uuid:" + tt.BeaconObject.Beacon.Uuid);
                try
                {
                    Debug.Assert(AddCleaningTask(ct), "failed adding cleaning task in loop");
                    Debug.Assert(AddMaintenanceTask(mt), "failed adding maintenance task in loop");
                    Debug.Assert(AddTransportTask(tt), "failed adding transport task in loop");
                }
                catch (Exception ex)
                {
                    Debug.WriteLine(ex.ToString());
                    return false;
                }
            }
            try  // Quick self-test to see if pre-fill failed
            {
                CleaningTask savect;
                MaintenanceTask savemt;
                TransportTask savett;

                Debug.Assert((savect = GetCleaningTask(0)) != null, "filling failed");
                Debug.Assert((savemt = GetMaintenanceTask(1)) != null, "filling failed");
                Debug.Assert((savett = GetTransportTask(2)) != null, "filling failed");
                Debug.WriteLine(savect.Name);
                Debug.WriteLine(savemt.Name);
                Debug.WriteLine(savett.Name);

                List<CleaningTask> testList = getAllCleaningTasks();
                List<MaintenanceTask> testListt = getAllMaintenanceTasks();
                List<TransportTask> testListtt = getAllTransportTasks();
                List<Task> testListttt = getAllTasks();
                clearAllTasks();
                return true;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
                clearAllTasks();
                return false;
            }
        }

        /// <summary>
        /// Small modular self-test to check for DB Constraint Violations
        /// </summary>
        [Conditional("Integration")]
        private void constraintTest()
        {
            clearAllTasks();
            // Insert small modular self test here
            clearAllTasks();
        }

        /// <summary>
        /// Failsafe in case a <see cref="Task"/> that was tried to be added to the <see cref="Database"/> was already contained in it.
        /// </summary>
        /// <param name="task"><see cref="Task"/> which caused a PrimKey violation</param>
        /// <returns><c>true</c> if update was successful, otherwise <c>false</c></returns>
        private bool tryPut(Task task)
        {
            // If task was never edited, can't be newer
            if (task.LastEdited == null) return false;
            Task temp;
            HealthcareApp.Converters.DateConverter conv = new HealthcareApp.Converters.DateConverter();
            // Since Violation is caused generic, check offendind type of Task
            if (task.GetType() == typeof(CleaningTask))
            {
                temp = this.GetCleaningTask(task.Id);
                if (temp.LastEdited == null) return UpdateCleaningTask((CleaningTask)task);
                else
                {
                    DateTime oldTask = conv.convertStringToDate(temp.LastEdited);
                    DateTime newTask = conv.convertStringToDate(task.LastEdited);
                    if (newTask.CompareTo(oldTask) == 1) return UpdateCleaningTask((CleaningTask)task);
                    return false;
                }
            }
            else if (task.GetType() == typeof(MaintenanceTask))
            {
                temp = this.GetMaintenanceTask(task.Id);
                if (temp.LastEdited == null) return UpdateMaintenanceTask((MaintenanceTask)task);
                else
                {
                    DateTime oldTask = conv.convertStringToDate(temp.LastEdited);
                    DateTime newTask = conv.convertStringToDate(task.LastEdited);
                    if (oldTask.CompareTo(newTask) == 1) return UpdateMaintenanceTask((MaintenanceTask)task);
                    return false;
                }
            }
            else
            {
                temp = this.GetTransportTask(task.Id);
                if (temp.LastEdited == null) return UpdateTransportTask((TransportTask)task);
                else
                {
                    DateTime oldTask = conv.convertStringToDate(temp.LastEdited);
                    DateTime newTask = conv.convertStringToDate(task.LastEdited);
                    if (oldTask.CompareTo(newTask) == 1) return UpdateTransportTask((TransportTask)task);
                    return false;
                }
            }
        }
    }
}
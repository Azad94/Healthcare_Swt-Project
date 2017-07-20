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
using System.ComponentModel;
using System.Windows.Input;

using Xamarin.Forms;

using HealthcareApp.Model;
using HealthcareApp.Model.Entity;
using HealthcareApp.Model.Enumeration;

namespace HealthcareApp.ViewModel
{
    /// <summary>
    /// ViewModel for TaskView
    /// Author: Niklas Klatt
    /// </summary>
    public class TaskPageViewModel : INotifyPropertyChanged
    {
        /// <summary>
        /// Current Task, which view is displaying
        /// </summary>
        private Task task;
        /// <summary>
        /// View
        /// </summary>
        private ContentPage page;
        /// <summary>
        /// Change event
        /// </summary>
        public event PropertyChangedEventHandler PropertyChanged;
        /// <summary>
        /// Command for handling acceptButton
        /// </summary>
        public ICommand AcceptCommand { get; protected set; }
        /// <summary>
        /// Command for handling finishedButton
        /// </summary>
        public ICommand FinishedCommand { get; protected set; }

        /*
         * Data Bindings. For more information see bindings in xaml file.
         */
        private String taskName;
        public String TaskName
        {
            get
            {
                return taskName;
            }
            set
            {
                taskName = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(TaskName)));
                }
            }
        }
        private int level;
        public int Level
        {
            get
            {
                return level;
            }
            set
            {
                level = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(Level)));
                }
            }
        }
        private int currentBuilding;
        public int CurrentBuilding
        {
            get
            {
                return currentBuilding;
            }
            set
            {
                currentBuilding = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(CurrentBuilding)));
                }
            }
        }
        private int currentFloor;
        public int CurrentFloor
        {
            get
            {
                return currentFloor;
            }
            set
            {
                currentFloor = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(CurrentFloor)));
                }
            }
        }
        private int currentRoom;
        public int CurrentRoom
        {
            get
            {
                return currentRoom;
            }
            set
            {
                currentRoom = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(CurrentRoom)));
                }
            }
        }
        private int targetBuilding;
        public int TargetBuilding
        {
            get
            {
                return targetBuilding;
            }
            set
            {
                targetBuilding = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(TargetBuilding)));
                }
            }
        }
        private int targetFloor;
        public int TargetFloor
        {
            get
            {
                return targetFloor;
            }
            set
            {
                targetFloor = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(TargetFloor)));
                }
            }
        }
        private int targetRoom;
        public int TargetRoom
        {
            get
            {
                return targetRoom;
            }
            set
            {
                targetRoom = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(TargetRoom)));
                }
            }
        }
        private String discription;
        public String Discription
        {
            get
            {
                return discription;
            }
            set
            {
                discription = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(Discription)));
                }
            }
        }
        private bool targetVisibility;
        public bool TargetVisibility
        {
            get
            {
                return targetVisibility;
            }
            set
            {
                targetVisibility = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(TargetVisibility)));
                }
            }
        }
        private bool maintenanceVisibility;
        public bool MaintenanceVisibility
        {
            get
            {
                return maintenanceVisibility;
            }
            set
            {
                maintenanceVisibility = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(MaintenanceVisibility)));
                }
            }
        }
        private bool acceptButtonEnable;
        public bool AcceptButtonEnable
        {
            get
            {
                return acceptButtonEnable;
            }
            set
            {
                acceptButtonEnable = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(AcceptButtonEnable)));
                }
            }
        }
        private bool finishedButtonEnable;
        public bool FinishedButtonEnable
        {
            get
            {
                return finishedButtonEnable;
            }
            set
            {
                finishedButtonEnable = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(FinishedButtonEnable)));
                }
            }
        }
        /// <summary>
        /// Creates a new TaskPageViewModel.
        /// </summary>
        /// <param name="page">view</param>
        public TaskPageViewModel(ContentPage page)
        {
            this.page = page;
            AcceptCommand = new Command(async () => await this.HandleAcceptCommand());
            FinishedCommand = new Command(async () => await this.HandleFinishedCommand());
            task = AppCore.Instance.CurrentTask;

            if (task != null)
            {
                this.ChangeVisibility(task);
                this.SetData(task);
                this.SetButtons(task);
            }
        }
        /// <summary>
        /// Changes the view for the given Task
        /// </summary>
        /// <param name="t">Task</param>
        private void ChangeVisibility(Task t)
        {
            if (t is TransportTask)
            {
                this.TargetVisibility = true;
                this.MaintenanceVisibility = false;
            }
            if (t is CleaningTask)
            {
                this.TargetVisibility = false;
                this.MaintenanceVisibility = false;
            }
            if (t is MaintenanceTask)
            {
                this.MaintenanceVisibility = true;
                this.TargetVisibility = false;
            }
        }

        /// <summary>
        /// Adds task specific information to view from given task 
        /// </summary>
        /// <param name="t">Task</param>
        private void SetData(Task t)
        {
            this.SetGeneralData(t);
            if (t is TransportTask)
            {
                TransportTask tt = t as TransportTask;
                this.TargetBuilding = tt.TargetLocation.Building;
                this.TargetFloor = tt.TargetLocation.Floor;
                this.TargetRoom = tt.TargetLocation.Room;
            }
            if (t is MaintenanceTask)
            {
                MaintenanceTask mt = t as MaintenanceTask;
            }
        }

        /// <summary>
        /// Adds all general information to view taken from given task
        /// </summary>
        /// <param name="t">Task</param>
        private void SetGeneralData(Task t)
        {
            this.TaskName = t.Name;
            this.Level = t.Level;
            this.CurrentBuilding = t.BeaconObject.Location.Building;
            this.CurrentFloor = t.BeaconObject.Location.Floor;
            this.CurrentRoom = t.BeaconObject.Location.Room;
            this.Discription = t.Description;
        }
        /// <summary>
        /// Set Buttons based on Task status
        /// </summary>
        /// <param name="t">Task</param>
        private void SetButtons(Task t)
        {
            if (t.Status == (int)TaskStatus.OPEN)
            {
                this.AcceptButtonEnable = true;
                this.FinishedButtonEnable = false;
            }
            if (t.Status == (int)TaskStatus.ACCEPTED)
            {
                this.AcceptButtonEnable = false;
                this.FinishedButtonEnable = true;
            }
        }
        /// <summary>
        /// Definde actions for AcceptCommand
        /// </summary>
        private async System.Threading.Tasks.Task HandleAcceptCommand()
        {
            AppCore.Instance.AcceptTask(task);
            this.AcceptButtonEnable = false;
            this.FinishedButtonEnable = true;
            await page.Navigation.PopAsync();
        }
        /// <summary>
        /// Define actions for finishedCommand
        /// </summary>
        private async System.Threading.Tasks.Task HandleFinishedCommand()
        {
            AppCore.Instance.CloseTask(task);
            this.AcceptButtonEnable = false;
            this.FinishedButtonEnable = false;
            await page.Navigation.PopAsync();
        }
        /// <summary>
        /// PropertyChanged implementation
        /// </summary>
        /// <param name="propertyName"> property name</param>
        protected void OnPropertyChanged(string propertyName)
        {
            var handler = PropertyChanged;
            if (handler != null)
                handler(this, new PropertyChangedEventArgs(propertyName));
        }
    }

}

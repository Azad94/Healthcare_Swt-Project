/**
 * /*******************************************************************************
 * Copyright 2017 Malte Grebe
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
using HealthcareApp.Model;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using Xamarin.Forms;
using HealthcareApp.Model.Entity;
using System;
using System.Windows.Input;
using HealthcareApp.View;
using Plugin.Vibrate;

namespace HealthcareApp.ViewModel
{
    /// <summary>
    /// ViewModel for BluetoothTaskPage
    /// Author: Malte Grebe
    /// </summary>
    public class BluetoothTaskPageViewModel : INotifyPropertyChanged
    {
        /// <summary>
        /// Change event
        /// </summary>
        public event PropertyChangedEventHandler PropertyChanged;
        /// <summary>
        /// View
        /// </summary>
        private ContentPage page;
        /// <summary>
        /// Instance of Model
        /// </summary>
        private AppCore appcore;
        /// <summary>
        /// Command for the selected Item
        /// </summary>
        public ICommand ItemSelectedCommand { get; private set; }
        /// <summary>
        /// Binded property for the ItemSource of the ListView
        /// </summary>
        private ObservableCollection<Task> bluetoothTasks;
        public ObservableCollection<Task> BluetoothTasks
        {
            get
            {
                return bluetoothTasks;
            }
            set
            {
                bluetoothTasks = value;
                OnPropertyChanged(nameof(BluetoothTasks));
            }
        }
        /// <summary>
        /// Binded property for the Name of the Task Object which is displayed by the View
        /// </summary>
        private String name;
        public String Name
        {
            get
            {
                return this.name;
            }
            set
            {
                name = value;
                OnPropertyChanged(nameof(Name));
            }
        }
        /// <summary>
        /// Creates a new BluetoothTaskViewModel
        /// </summary>
        /// <param name="page"> BluetoothTaskPage</param>
        public BluetoothTaskPageViewModel(ContentPage page)
        {
            this.page = page;
            ItemSelectedCommand = new Command<Model.Entity.Task>(OutputItemSelected);
            appcore = AppCore.Instance;
            this.bluetoothTasks = new ObservableCollection<Model.Entity.Task>();
            MessagingCenter.Subscribe<AppCore, List<Task>>(this, "Bluetooth", (sender, list) => { checkList(list); });
            MessagingCenter.Send<BluetoothTaskPageViewModel>(this, "BTTasksRequest");
        }
        /// <summary>
        /// refreshes the list for the view
        /// </summary>
        /// <param name="list"> the new list for the view</param>
        private void checkList(List<Task> list)
        {
            this.bluetoothTasks.Clear();

            foreach (Model.Entity.Task t in list)
            {
                this.bluetoothTasks.Add(t);
            }

            if (list.Count != 0)
            {
                CrossVibrate.Current.Vibration(1000);
            }
        }
        /// <summary>
        /// set the selected Task in the model and load the Task Detail View
        /// </summary>
        /// <param name="task">the selected task</param>
        private void OutputItemSelected(Model.Entity.Task task)
        {
            this.appcore.SetCurrentTask(task);

            if (task is Model.Entity.MaintenanceTask)
            {
                this.page.Navigation.PushAsync(new MaintenanceDetailPage());
            }
            else
            {
                this.page.Navigation.PushAsync(new TaskPage());
            }
        }
        /// <summary>
        /// PropertyChanded implementation
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
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
using System.Diagnostics;

namespace HealthcareApp.ViewModel
{
    /// <summary>
    /// ViewModel for QRTaskPage
    /// Author: Malte Grebe
    /// </summary>
    public class QRTaskPageViewModel : INotifyPropertyChanged
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
        private ObservableCollection<Task> qrTasks;
        public ObservableCollection<Task> QrTasks
        {
            get
            {
                return qrTasks;
            }
            set
            {
                qrTasks = value;
                OnPropertyChanged(nameof(QrTasks));
            }
        }
        /// <summary>
        /// Binded property for the Name of Task Objects to display in the view
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
        /// Creates a new QRTaskPageViewModel
        /// </summary>
        /// <param name="page"> QRTaskPage</param>
        public QRTaskPageViewModel(ContentPage page)
        {
            this.page = page;
            ItemSelectedCommand = new Command<Model.Entity.Task>(OutputItemSelected);
            appcore = AppCore.Instance;
            this.qrTasks = new ObservableCollection<Model.Entity.Task>();
            MessagingCenter.Subscribe<AppCore, List<Task>>(this, "QR", (sender, list) => { checkList(list); });
            MessagingCenter.Send<QRTaskPageViewModel>(this, "QRTasksRequest");
        }
        /// <summary>
        /// refreshes the list for the view
        /// </summary>
        /// <param name="list"> new list of Task objects</param>
        private void checkList(List<Task> list)
        {
            this.qrTasks.Clear();

            foreach (Model.Entity.Task t in list)
            {
                this.qrTasks.Add(t);
            }
        }
        /// <summary>
        /// Set the selected Task in the model and load the Task Detail View
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
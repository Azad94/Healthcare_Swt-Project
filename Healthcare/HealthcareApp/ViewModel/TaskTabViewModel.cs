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
using HealthcareApp.Model.Entity;
using HealthcareApp.View;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Xamarin.Forms;

namespace HealthcareApp.ViewModel
{
    /// <summary>
    /// ViewModel for TaskTab
    /// Author: Malte Grebe
    /// </summary>
    public class TaskTabViewModel : INotifyPropertyChanged
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
        /// the title of the page, to distinguish the tabs
        /// </summary>
        private String title;
        /// <summary>
        /// Binded property for the Name of Task objects to display in the view
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
        /// Binded property for the ItemSource of the ListView
        /// </summary>
        private ObservableCollection<Model.Entity.Task> allTasks;
        public ObservableCollection<Model.Entity.Task> AllTasks
        {
            get
            {
                return allTasks;
            }
            set
            {
                allTasks = value;
                OnPropertyChanged(nameof(AllTasks));
            }
        }
        /// <summary>
        /// Creates a new TaskTabViewModel
        /// </summary>
        /// <param name="page"> TaskTab</param>
        public TaskTabViewModel(ContentPage page)
        {
            this.page = page;
            this.title = this.page.Title;

            //ItemSelectedCommand = new Command<Model.Entity.Task>(OutputItemSelected);

            this.allTasks = new ObservableCollection<Model.Entity.Task>();
            this.appcore = AppCore.Instance;
            initLists();
            MessagingCenter.Send<TaskTabViewModel>(this, "TasksRequest");
        }
        /// <summary>
        /// Set the selected Task in the model and load the Task Detail View
        /// </summary>
        /// <param name="task"> the selected task from the listview</param>
        public void OutputItemSelected(Model.Entity.Task task)
        {
            this.appcore.SetCurrentTask(task);

            if (task is Model.Entity.MaintenanceTask)
            {
                if (task.Status == 2)
                {
                    this.page.Navigation.PushAsync(new MaintenanceDetailPage());
                }
                else
                {
                    this.page.Navigation.PushAsync(new TaskPage());
                }
            }
            else
            {
                this.page.Navigation.PushAsync(new TaskPage());
            }


        }
        /// <summary>
        /// Distinguish Tabs to load the correct List with Tasks
        /// </summary>
        private void initLists()
        {
            switch (title)
            {
                case "Offen":
                    MessagingCenter.Subscribe<AppCore, List<Model.Entity.Task>>(this, "OpenTasks", (sender, openTasks) =>
                    {
                        Debug.WriteLine("Empfangene Liste Offen " + title + " Größe " + openTasks.Count);
                        this.allTasks.Clear();
                        foreach (Model.Entity.Task t in openTasks)
                        {
                            this.allTasks.Add(t);
                        }
                    });
                    break;
                case "Abgeschlossen":
                    MessagingCenter.Subscribe<AppCore, List<Model.Entity.Task>>(this, "ClosedTasks", (sender, closedTasks) =>
                    {
                        Debug.WriteLine("Empfangene Liste Closed " + title + " Größe " + closedTasks.Count);
                        this.allTasks.Clear();
                        foreach (Model.Entity.Task t in closedTasks)
                        {
                            this.allTasks.Add(t);
                        }
                    });
                    break;
                case "Angenommen":
                    MessagingCenter.Subscribe<AppCore, List<Model.Entity.Task>>(this, "AcceptedTasks", (sender, acceptedTasks) =>
                    {
                        Debug.WriteLine("Empfangene Liste Accepted " + title + " Größe " + acceptedTasks.Count);
                        this.allTasks.Clear();
                        foreach (Model.Entity.Task t in acceptedTasks)
                        {
                            this.allTasks.Add(t);
                        }
                    });
                    break;
                default:
                    Debug.WriteLine("Kategorie nicht gefunden!");
                    break;
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

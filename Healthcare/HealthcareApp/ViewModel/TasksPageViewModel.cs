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
using HealthcareApp.Model.Entity;
using HealthcareApp.View;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;
using System.Windows.Input;
using Xamarin.Forms;

namespace HealthcareApp.ViewModel
{
    /// <summary>
    /// ViewModel for TasksPage
    /// Author: Malte Grebe
    /// </summary>
    public class TasksPageViewModel : INotifyPropertyChanged
    {
        /// <summary>
        /// Change event
        /// </summary>
		public event PropertyChangedEventHandler PropertyChanged;
        /// <summary>
        /// View
        /// </summary>
        private TabbedPage page;
        /// <summary>
        /// Creates a new TasksPageViewModel
        /// </summary>
        /// <param name="page"> TasksPage</param>
        public TasksPageViewModel(TabbedPage page)
        {
            this.page = page;
            this.page.Children.Add(new TaskTab("Offen") { Title = "Offen" });
            this.page.Children.Add(new TaskTab("Angenommen") { Title = "Angenommen" });
            this.page.Children.Add(new TaskTab("Abgeschlossen") { Title = "Abgeschlossen" });
            this.page.Children.Add(new BluetoothTaskPage() { Title = "Bluetooth" });
            this.page.Children.Add(new QRTaskPage() { Title = "QR" });
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

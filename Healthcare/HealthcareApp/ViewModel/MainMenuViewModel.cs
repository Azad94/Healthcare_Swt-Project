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
using System;
using System.Collections.Generic;
using System.Text;
using HealthcareApp.Model.Entity;
using System.ComponentModel;
using System.Windows.Input;
using Xamarin.Forms;
using HealthcareApp.Model;
using HealthcareApp.Model.Synchronization;
using HealthcareApp.View;
using System.Diagnostics;

namespace HealthcareApp.ViewModel
{
    /// <summary>
    /// ViewModel for MainMenu
    /// Author: Malte Grebe
    /// </summary>
    public class MainMenuViewModel : INotifyPropertyChanged
    {
        /// <summary>
        /// Change event
        /// </summary>
        public event PropertyChangedEventHandler PropertyChanged;
        /// <summary>
        /// View
        /// </summary>
        public ContentPage page;
        /// <summary>
        /// Instance of Model
        /// </summary>
        private AppCore appcore;
        /// <summary>
        /// Command for the Task Button
        /// </summary>
        public ICommand TaskCommand { protected set; get; }
        /// <summary>
        /// Command for the QR-Code Button
        /// </summary>
        public ICommand QRCommand { protected set; get; }
        /// <summary>
        /// Command for the Location Button
        /// </summary>
        public ICommand LcCommand { get; private set; }
        /// <summary>
        /// Binded property for the Wlan Status
        /// </summary>
        private bool wlanCon;
        public bool WlanCon
        {
            get
            {
                return wlanCon;
            }
            set
            {
                this.wlanCon = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(WlanCon)));
                }
            }
        }
        /// <summary>
        /// Binded property for the Wlan Status
        /// </summary>
        private Color wlan;
        public Color Wlan
        {
            get
            {
                return wlan;
            }
            set
            {
                this.wlan = value;
                if (PropertyChanged != null)
                {
                    PropertyChanged(this, new PropertyChangedEventArgs(nameof(Wlan)));
                }
            }
        }
        /// <summary>
        /// Creates a new MainMenuViewModel
        /// </summary>
        /// <param name="page"> MainMenu</param>
        public MainMenuViewModel(ContentPage page)
        {
            this.page = page;
            this.appcore = AppCore.Instance;
            QRCommand = new Command(async () => this.HandleQRCode());
            LcCommand = new Command(async () => this.LocationChange());
            TaskCommand = new Command(async () => this.TaskList());

            this.Wlan = Color.Red;
            MessagingCenter.Subscribe<AppCore, bool>(this, "ServerConnectionChange", (sender, hasConnection) => {

                if (hasConnection)
                {
                    this.WlanCon = true;
                    this.Wlan = Color.Green;
                }
                else { this.Wlan = Color.Red; this.WlanCon = false; }
            });

            MessagingCenter.Send<MainMenuViewModel>(this, "MainMenuReady");
        }
        /// <summary>
        /// PropertyChanded implementation
        /// </summary>
        /// <param name="propertyName"> property name</param>
        private void NotifyPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }
        /// <summary>
        /// Handle navigation if user press QR-Code button
        /// </summary>
        private async void HandleQRCode()
        {
            await page.Navigation.PushAsync(new ScanPage());
        }
        /// <summary>
        /// Handle navigation if user press Location button
        /// </summary>
        private async void LocationChange()
        {
            if (this.appcore.hasLocation)
            {
                var answer = await this.page.DisplayAlert("Achtung!", "Der Vorgang wird einen Augenblick dauern und benötigt eine stabile Internetverbindung", "Fortfahren", "Abbrechen");
                Debug.WriteLine("Answer: " + answer);
                if (answer)
                {
                    await page.Navigation.PopAsync();
                }

            }
        }
        /// <summary>
        /// Handle navigation if user press Aufträge button
        /// </summary>
        private async void TaskList()
        {
            await page.Navigation.PushAsync(new TasksPage());
        }
    }
}

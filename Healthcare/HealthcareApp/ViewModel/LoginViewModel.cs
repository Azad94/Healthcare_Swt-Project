/**
 * /*******************************************************************************
 * Copyright 2017 Niklas Klatt & Malte Grebe
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
using HealthcareApp.View;

namespace HealthcareApp.ViewModel
{
    /// <summary>
    /// ViewModel for Login Page.
    /// Author: Malte Grebe, Niklas Klatt
    /// </summary>
    public class LoginViewModel : INotifyPropertyChanged
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
        /// typed in username
        /// </summary>
        public String Username { get; set; }
        /// <summary>
        /// typed in password
        /// </summary>
        public String Password { get; set; }
        /// <summary>
        /// Visibilty of process indicator
        /// </summary>
        private bool indicatorVisible;
        /// <summary>
        /// Binded property for visibilty of process indicator
        /// </summary>
        public bool IndicatorVisible
        {
            get
            {
                return indicatorVisible;
            }
            set
            {
                this.indicatorVisible = value;
                OnPropertyChanged(nameof(IndicatorVisible));
            }
        }
        private bool wlanCon;
        /// <summary>
        /// Binded property for wlanConnection
        /// </summary>
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
        private Color wlan;
        /// <summary>
        /// Binded property for wlanConnection
        /// </summary>
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
        /// Command for login button
        /// </summary>
        public ICommand LoginCommand { protected set; get; }
        /// <summary>
        /// Creates a new LoginViewModel
        /// </summary>
        /// <param name="page"> LoginPage</param>
        /// <param name="model">Model</param>
        public LoginViewModel(ContentPage page)
        {
            this.page = page;
            this.LoginCommand = new Command(() => HandleLoginAsync());
            MessagingCenter.Subscribe<AppCore, bool>(this, "ServerConnectionChange", (sender, hasConnection) =>
            {

                if (hasConnection)
                {
                    this.Wlan = Color.Green; this.WlanCon = true;
                }
                else { this.Wlan = Color.Red; this.WlanCon = false; }
            });

            MessagingCenter.Send<LoginViewModel>(this, "LoginReady");

            appcore = AppCore.Instance;
        }
        /// <summary>
        /// Handle login process if user press login button
        /// </summary>
        /// <returns></returns>
        async void HandleLoginAsync()
        {
            if (appcore.AuthUser(this.Username, this.Password))
            {
                await page.Navigation.PushAsync(new ChangeWorklocation());

            }
            else
            {
                await page.DisplayAlert("Login fehlgeschlagen", "Username oder Passwort falsch", "Ok");
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

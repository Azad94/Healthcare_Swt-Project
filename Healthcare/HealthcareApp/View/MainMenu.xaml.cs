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
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HealthcareApp.ViewModel;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace HealthcareApp.View
{
    /// <summary>
    /// MainMenu for HealthcareApp
    /// </summary>
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MainMenu : ContentPage
    {
        /// <summary>
        /// MainMenu ViewModel
        /// </summary>
        HealthcareApp.ViewModel.MainMenuViewModel vm;
        /// <summary>
        /// Creates a new MainMenu, based on xaml file
        /// </summary>
        public MainMenu()
        {
            InitializeComponent();
            BindingContext = this.vm = new MainMenuViewModel(this);
        }
        /// <summary>
        /// Notify AppCore that logout is clicked
        /// </summary>
        private void logout_Clicked(object sender, EventArgs e)
        {
         

            MessagingCenter.Send<MainMenu>(this, "Logout");
            Navigation.PopToRootAsync();
        }
        /// <summary>
        /// Disabled hardwarebutton
        /// </summary>
        protected override bool OnBackButtonPressed()
        {
            return true;
        }
    }
}

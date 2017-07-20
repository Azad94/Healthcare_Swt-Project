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
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace HealthcareApp.View
{
    /// <summary>
    /// LoginPage for HealthcareApp
    /// </summary>
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class LoginPage : ContentPage
    {
        /// <summary>
        /// LoginPage ViewModel
        /// </summary>
        private HealthcareApp.ViewModel.LoginViewModel vm;

        /// <summary>
        /// Creates a new LoginPage, based on xaml file
        /// </summary>
        public LoginPage()
        {
            InitializeComponent();
            BindingContext = vm = new HealthcareApp.ViewModel.LoginViewModel(this);
            indicator.SetBinding(IsVisibleProperty, nameof(vm.IndicatorVisible));
        }
    }
}

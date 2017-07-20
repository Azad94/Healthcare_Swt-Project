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
using HealthcareApp.ViewModel;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace HealthcareApp.View
{
    /// <summary>
    /// Feedback TabPage for a MaintenanceTask.
    /// Author: Niklas Klatt
    /// </summary>
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MaintenanceFeedbackPage : ContentPage
    {
        /// <summary>
        /// MaintenanceFeedbackPage ViewModel
        /// </summary>
        private MaintenanceFeedbackViewModelcs vm;
        /// <summary>
        /// Creates a new MaintenanceFeedbackPage based on xaml file.
        /// </summary>
        public MaintenanceFeedbackPage()
        {
            InitializeComponent();
            vm = new MaintenanceFeedbackViewModelcs();
            BindingContext = vm;
            MaintenanceDetailsView.ItemsSource = vm.Items;
            MaintenanceDetailsView.ItemSelected += (sender, arg) =>
            {
                ((ListView)sender).SelectedItem = null;
            };
        }
        /// <summary>
        /// Event for changing text in entry field
        /// </summary>
        /// <param name="sender"> sender</param>
        /// <param name="e"> event args</param>
        private void TextChanged(object sender, TextChangedEventArgs e)
        {
            if (e.NewTextValue.Length == 0)
            {
                vm.ButtonActive = false;
            }
            else
            {
                vm.ButtonActive = true;
            }
        }
    }
}
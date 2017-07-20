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
    /// ChangeWorklocation for HealthcareApp
    /// </summary>
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ChangeWorklocation : ContentPage
    {
        /// <summary>
        /// ChangeWorklocation ViewModel
        /// </summary>
        HealthcareApp.ViewModel.ChangeWorklocationViewModel vm;
        /// <summary>
        /// Creates a new ChangeWorklocation, based on xaml file
        /// </summary>
        public ChangeWorklocation()
        {
            InitializeComponent();
            BindingContext = this.vm = new HealthcareApp.ViewModel.ChangeWorklocationViewModel(this);
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

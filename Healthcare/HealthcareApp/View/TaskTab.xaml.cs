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
using HealthcareApp.ViewModel;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace HealthcareApp.View
{
    /// <summary>
    /// TaskTabPage for HealthcareApp
    /// </summary>
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class TaskTab : ContentPage
    {
        /// <summary>
        /// TaskTab ViewModel
        /// </summary>
        HealthcareApp.ViewModel.TaskTabViewModel vm;
        /// <summary>
        /// Creates a new TaskTab, based on xaml file
        /// </summary>
        public TaskTab(String s)
        {
            InitializeComponent();
            this.Title = s;
            BindingContext = this.vm = new ViewModel.TaskTabViewModel(this);
            AllView.ItemSelected += OnSelection;
        }
        /// <summary>
        /// Handles the selection from the listview
        /// </summary>
        public void OnSelection(object sender, SelectedItemChangedEventArgs e)
        {
            if (e.SelectedItem == null) return;
            ((ListView)sender).SelectedItem = null;
            vm.OutputItemSelected((Model.Entity.Task)e.SelectedItem);
        }

    }
}
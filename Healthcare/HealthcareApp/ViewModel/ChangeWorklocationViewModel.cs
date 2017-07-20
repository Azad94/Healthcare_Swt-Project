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
using System.ComponentModel;
using System.Diagnostics;
using System.Text;
using Xamarin.Forms;

namespace HealthcareApp.ViewModel
{
    /// <summary>
    /// ViewModel for ChangeWorklocation
    /// Author: Malte Grebe
    /// </summary>
    public class ChangeWorklocationViewModel : INotifyPropertyChanged
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
        /// Binded property for the selected Item from the Picker
        /// Also set the worklocation in the model
        /// </summary>
        private Location selectedBuilding = new Location(0, 0, 0);
        public Location SelectedBuilding
        {
            get
            {
                return selectedBuilding;
            }
            set
            {
                if (selectedBuilding != value)
                {
                    selectedBuilding = value;
                    OnPropertyChanged(nameof(SelectedBuilding));

                    this.appcore.ChangeWorkLocation(selectedBuilding);
                    if (!this.appcore.hasLocation)
                    {
                        this.appcore.hasLocation = true;
                    }
                    this.page.Navigation.PushAsync(new MainMenu());
                }
            }
        }
        /// <summary>
        /// Binded property for the ItemSource of the ListView
        /// </summary>
        private List<Location> buildings = new List<Location>();
        public List<Location> Buildings => buildings;
        /// <summary>
        /// Binded property for the Index of the selectedItem
        /// </summary>
        int buildingsselectedIndex;
        public int BuildingsSelectedIndex
        {
            get
            {
                return buildingsselectedIndex;
            }
            set
            {
                if (buildingsselectedIndex != value)
                {
                    buildingsselectedIndex = value;

                    OnPropertyChanged(nameof(BuildingsSelectedIndex));
                    //SelectedBuilding = Buildings[buildingsselectedIndex];
                }
            }
        }
        /// <summary>
        /// PropertyChanded implementation
        /// </summary>
        /// <param name="propertyName"> property name</param>
        private void OnPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }
        /// <summary>
        /// Creates a new ChangeWorklocationViewModel
        /// </summary>
        /// <param name="page"> ChangeWorklocation</param>
        public ChangeWorklocationViewModel(ContentPage page)
        {
            this.page = page;
            this.appcore = AppCore.Instance;

            MessagingCenter.Subscribe<AppCore, List<Model.Entity.Location>>(this, "ChooseWorkLocation", (sender, buildingList) =>
            {
                Debug.WriteLine("Empfangene Liste Größe " + buildingList.Count);
                this.buildings.Clear();
                foreach (Model.Entity.Location t in buildingList)
                {
                    this.buildings.Add(t);
                }
            });


            MessagingCenter.Send<ChangeWorklocationViewModel>(this, "ChangeWorklocationReady");

        }

    }
}

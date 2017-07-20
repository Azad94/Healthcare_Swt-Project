/**
 * /*******************************************************************************
 * Copyright 2017 Sheraz Azad
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
using System.Diagnostics;
using Xamarin.Forms;

namespace HealthcareApp.Model.Service
{
	/// <summary>
    /// Class for creating the Dependency Service and communicating with the View.
    /// </summary>
	public class BluetoothManager
	{
        public List<string> BeaconList;
        public HashSet<string> OldList;
        private Entity.Role role;
        private AppCore appcore;

        /// <summary>
        /// Initializes a new instance of the <see cref="T:HealthcareApp.Model.Service.BluetoothManager"/> class.
        /// </summary>
		public BluetoothManager()
		{
            BeaconList = new List<string>();
            appcore = AppCore.Instance;
            OldList = new HashSet<string>();
            WaitForLogin();
		}

        /// <summary>
        /// Waits for a user to log in before intializing the service.
        /// </summary>
        private void WaitForLogin()
        {
			MessagingCenter.Subscribe<AppCore, Entity.Role>(this, "StartBluetooth", (sender, role) =>
			{
				this.role = role;
				Init();
			});
        }

		/// <summary>
        /// Initializes the Bluetooth/Beacon Service and sets the Eventhandler for the View.
        /// </summary>
		private void Init()
		{
			var beaconService = DependencyService.Get<IBeaconService>();

			beaconService.UpdateListEvent += (sender, e) =>
            {
                BeaconList = e.BeaconList;
                if (SendList())
                {
					appcore.changeBtTasks(BeaconList);
                }
			};

            //waits for the user to log out, to stop the Bluetooth Service
			MessagingCenter.Subscribe<View.MainMenu>(this, "Logout", (sender) =>
			{
                beaconService.StopBLEService();
                beaconService = null;
                WaitForLogin();
			});

            beaconService.InitializeService(this.role, Global.beaconRange);
		}

        /// <summary>
        /// Checks whether the state of the list has been changed, in order to decide
        /// whether to send the list to the view or not.
        /// </summary>
        /// <returns><c>true</c>, if list was sent, <c>false</c> otherwise.</returns>
        private bool SendList()
        {
            HashSet<string> newList = new HashSet<string>(BeaconList);

            if(OldList.Count == 0 && newList.Count > 0)
            {
                OldList = newList;
                return true;
            }

            if(OldList.Count != newList.Count)
            {
                OldList = newList;
                return true;
            }

            if(OldList.Count == newList.Count)
            {
                foreach(string id in newList)
                {
                    if(!OldList.Contains(id))
                    {
                        OldList = newList;
                        return true;
                    }
                }
                OldList = newList;
                return false;
            }
            OldList = newList;
            return false;
        }
	}
}
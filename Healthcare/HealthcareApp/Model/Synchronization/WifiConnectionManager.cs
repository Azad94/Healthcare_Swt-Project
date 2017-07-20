/**
 * /*******************************************************************************
 * Copyright 2017 Jonas Grams
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
using Plugin.Connectivity;
using System.Diagnostics;
using Plugin.Connectivity.Abstractions;
using HealthcareApp.Model;
using SQLiteDatabase;

namespace HealthcareApp.Model.Synchronization
{
    /// <summary>
    /// Handles wifi-state changes and reports them to the AppCore
    /// </summary>
	class WifiConnectionManager
	{
		private AppCore appCore;
		private SynchronizationManager synchMng;
		private String server;
		private int port;
		private int timeout;
		private bool hasServerConnection;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="appCore"></param>
        /// <param name="synchMng"></param>
        public WifiConnectionManager(AppCore appCore, SynchronizationManager synchMng)
        {
            CrossConnectivity.Current.ConnectivityChanged += onConnectionChange;
            this.synchMng = synchMng;
            server = Global.server;
            port = Global.port;
            timeout = Global.timeout;
            this.appCore = appCore;
            this.appCore.setWifiMng(this);
            CheckConnectionAsync();
        }

        /// <summary>
        /// Initiates a connectivity check. This method should be called by an EventListener.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
		private void onConnectionChange(object sender, ConnectivityChangedEventArgs e)
		{
			CheckConnectionAsync();
		}

        /// <summary>
        /// Checks if the serveraddress that is defined in Global.cs is reachable or not 
        /// and informs AppCore about the current wifistate.
        /// The plugin "Xam.Plugin.Connectivity" was used: https://github.com/jamesmontemagno/ConnectivityPlugin
        /// </summary>
		private async void CheckConnectionAsync()
		{
			IEnumerable<ConnectionType> connectionTypes = CrossConnectivity.Current.ConnectionTypes;
			hasServerConnection = false;

			foreach (ConnectionType cT in connectionTypes)
			{
				if (cT == ConnectionType.WiFi)
				{
					if (Global.serverIsInLocalNetwork)
					{
						hasServerConnection = await CrossConnectivity.Current.IsReachable(server, timeout);
					}
					else
					{
						hasServerConnection = await CrossConnectivity.Current.IsRemoteReachable(server, port, timeout);
					}
				}
			}
            appCore.InformConnectionChange(hasServerConnection);
            if (hasServerConnection)
			{
				synchMng.StartSynch(appCore.user,appCore.workLocation);
			}
		}

		//getter and setter
		public void SetAppCore(AppCore appCore)
		{
			this.appCore = appCore;
		}

		public AppCore GetAppCore()
		{
			return this.appCore;
		}

		public bool GetHasServerConnection()
		{
			return this.hasServerConnection;
		}
	}
}

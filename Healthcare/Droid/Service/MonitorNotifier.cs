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
using AltBeaconOrg.BoundBeacon;

namespace HealthcareApp.Droid.Service
{
    /// <summary>
    /// Event for monitoring.
    /// </summary>
	public class EventMonitor : EventArgs
	{
		public Region Region { get; set; }
		public int State { get; set; }
	}

	/// <summary>
    /// Notifier for  monitoring, primarly used for debugging. Could be enhanced
    /// for the proper Notification usage.
    /// </summary>
	public class MonitorNotifier : Java.Lang.Object, IMonitorNotifier
	{
		public event EventHandler<EventMonitor> DetermineStateForRegionComplete;
		public event EventHandler<EventMonitor> EnterRegionComplete;
		public event EventHandler<EventMonitor> ExitRegionComplete;

		/// <summary>
        /// Calls method to handle the Event.
        /// </summary>
        /// <param name="state">State.</param>
        /// <param name="region">Region.</param>
		public void DidDetermineStateForRegion(int state, Region region)
		{
			OnDetermineStateForRegionComplete(state, region);
		}

		/// <summary>
		/// Calls method to handle the Event.
		/// </summary>
		/// <param name="region">Region.</param>
		public void DidEnterRegion(Region region)
		{
			OnEnterRegionComplete(region);
		}

		/// <summary>
		/// Calls method to handle the Event.
		/// </summary>
		/// <param name="region">Region.</param>
		public void DidExitRegion(Region region)
		{
			OnExitRegionComplete(region);
		}

		/// <summary>
		/// Handles the Event.
		/// </summary>
		/// <param name="state">State.</param>
		/// <param name="region">Region.</param>
		private void OnDetermineStateForRegionComplete(int state, Region region)
		{
			DetermineStateForRegionComplete?.Invoke(this, new EventMonitor { State = state, Region = region });
		}

		/// <summary>
		/// Handles the Event.
		/// </summary>
		/// <param name="region">Region.</param>
		private void OnEnterRegionComplete(Region region)
		{
			EnterRegionComplete?.Invoke(this, new EventMonitor { Region = region });
		}

		/// <summary>
		/// Handles the Event.
		/// </summary>
		/// <param name="region">Region.</param>
		private void OnExitRegionComplete(Region region)
		{
			ExitRegionComplete?.Invoke(this, new EventMonitor { Region = region });
		}
	}
}


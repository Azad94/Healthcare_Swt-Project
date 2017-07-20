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

using AltBeaconOrg.BoundBeacon;
using System;
using System.Collections.Generic;

namespace HealthcareApp.Droid.Service
{
	/// <summary>
	/// Event for the Range.
	/// </summary>
	public class EventRange : EventArgs
	{
		public Region Region { get; set; }
		public ICollection<Beacon> Beacons { get; set; }
	}

	/// <summary>
	/// Notifier for the Range
	/// </summary>
	public class RangeNotifier : Java.Lang.Object, IRangeNotifier
	{
		public event EventHandler<EventRange> DidRangeBeaconsInRegionComplete;

		/// <summary>
		/// Calls method to handle the Event.
		/// </summary>
		/// <param name="beacons">Beacons.</param>
		/// <param name="region">Region.</param>
		public void DidRangeBeaconsInRegion(ICollection<Beacon> beacons, Region region)
		{
			OnDidRangeBeaconsInRegion(beacons, region);
		}

		/// <summary>
		/// Handles the Event
		/// </summary>
		/// <param name="beacons">Beacons.</param>
		/// <param name="region">Region.</param>
		private void OnDidRangeBeaconsInRegion(ICollection<Beacon> beacons, Region region)
		{
			DidRangeBeaconsInRegionComplete?.Invoke(this, new EventRange { Beacons = beacons, Region = region });
		}
	}
}


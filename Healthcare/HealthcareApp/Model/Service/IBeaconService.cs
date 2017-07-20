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

namespace HealthcareApp.Model.Service
{
    /// <summary>
    /// Interface for the Beacon Service which is implemented in both Droid and iOS
    /// folder trhough Dependency Services.
    /// </summary>
	public interface IBeaconService
	{
        /// <summary>
        /// Initializes the service.
        /// </summary>
        /// <param name="role">Role of the current user.</param>
        /// <param name="range">Range in which the beacons should be searched.</param>
		void InitializeService(Entity.Role role, double range);

        /// <summary>
        /// Starts the monitoring.
        /// </summary>
		void StartMonitoring();

        /// <summary>
        /// Starts the ranging.
        /// </summary>
		void StartRanging();

        /// <summary>
        /// Stops the BLE Service.
        /// </summary>
        void StopBLEService();

        /// <summary>
        /// Occurs when update list event, is called.
        /// </summary>
        event EventHandler<Model.Service.EventListUpdate> UpdateListEvent;
	}
}

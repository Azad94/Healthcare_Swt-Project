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

using HealthcareApp.Model.Service;
using HealthcareApp.Model.Enumeration;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using System.Diagnostics;
using Estimote;

[assembly: Xamarin.Forms.Dependency(typeof(HealthcareApp.iOS.Service.BeaconService))]
namespace HealthcareApp.iOS.Service
{
	/// <summary>
	/// This class contains the implementation of the Beacon Service for Android devices.
	/// The class is used through the Dependency Service, which is called in the shared
	/// folder.
	/// </summary>
	public class BeaconService : IBeaconService
    {
        BeaconManager beaconManager;
        NearableManager nearableManager;
        //private Region region;

        private HashSet<Beacon> compareSet;
        private HashSet<Beacon> beaconSet;
        private HashSet<String> setLongId;

        private Model.Entity.Role role;
        private double beaconRange;
        private int currentRole;
		//used for checking whether the update can be called, to avoid race conditions
		private bool mutex;

        public event EventHandler<Model.Service.EventListUpdate> UpdateListEvent;

        /// <summary>
        /// Initializes a new instance of the <see cref="T:HealthcareApp.iOS.Service.BeaconService"/> class.
        /// </summary>
        public BeaconService()
        {
            beaconSet = new HashSet<Beacon>();
            setLongId = new HashSet<string>();
            compareSet = new HashSet<Beacon>();
            mutex = false;
        }

		/// <summary>
		/// Calls the BeaconManager Intialize method.
		/// </summary>
		/// <value>The beacon manager impl.</value>
		public BeaconManager BeaconManagerImpl
        {
            get
            {
                if (beaconManager == null)
                {
                    beaconManager = InitializeBeaconManager();
                }
                return beaconManager;
            }
        }

		/// <summary>
		/// Initializes the beacon manager, sets the Monitoring and Ranging Regions.
		/// Furthermore the standard of the Beacon which is suppose to be found, is 
		/// set here. In the current context iBeacon standard beacons are searched/found.
		/// </summary>
		/// <returns>The beacon manager.</returns>
		private BeaconManager InitializeBeaconManager()
        {
            BeaconManager bm = new BeaconManager();
            beaconManager.ReturnAllRangedBeaconsAtOnce = true;
            //region = new Region(null, null, null, null);
            //beaconManager.StartRangingBeaconsInRegion(region);
            //beaconManager.StartMonitoringForRegion(region);

            beaconManager.AuthorizationStatusChanged += (sender, e) =>
              {
                  StartRanging();
              };

            StartRanging();
            return bm;
        }

		/// <summary>
		/// If a beacon is in range this method is triggered, which checks if the beacon
		/// in range is either relevant for the logged in user and if the object is of any
		/// interest of the logged in user. If both are verfied another method for updating
		/// the list of beacons is called.
		/// </summary>
		public async void StartRanging()
        {
            var beaconsInRange = new List<Beacon>();

            beaconManager.RangedBeacons += (sender, e) =>
            {

                if (e.Beacons.Count() > 0)
                {
                    foreach (var beacon in e.Beacons)
                    {
                        if (beacon.Rssi < beaconRange)
                        {
       //                     if ((beacon.Major.GetHashCode()) == 58009)

       //                             && currentRole == Major.EMPLOYEE.GetHashCode())
							//{
       //                         beaconsInRange.Add(beacon);
       //                     }
							//else if (beacon.Id2.ToInt() == 10
                            //         && currentRole == Major.TECHNICIAN.GetHashCode())
                            //{
                            //    beaconsInRange.Add(beacon);
                            //}
                            //else if (beacon.Id2.ToInt() == currentRole)
                            //{
                            //    if (currentRole == Major.TECHNICIAN.GetHashCode())
                            //    {
                            //        beaconsInRange.Add(beacon);
                            //    }
                            //    else if (currentRole == Major.EMPLOYEE.GetHashCode())
                            //    {
                            //        if (beacon.Id3.ToInt() == Minor.FIREDOOR.GetHashCode())
                            //        {
                            //            beaconsInRange.Add(beacon);
                            //        }
                            //        else if (beacon.Id3.ToInt() == Minor.BED.GetHashCode())
                            //        {
                            //            beaconsInRange.Add(beacon);
                            //        }
                            //    }
                            //}
                        }
                    }
                }
            };

            if (!mutex)
            {
                mutex = true;
                await UpdateListOfBeacons(beaconsInRange);
                beaconsInRange.Clear();
                mutex = false;
            }
        }

		/// <summary>
		/// Starts the monitoring.
		/// </summary>
		public void StartMonitoring()
        {
			            /*
			    base.ViewDidLoad ();

			    TableView.WeakDataSource = this;
			    TableView.WeakDelegate = this;

			    manager = new NearableManager ();

			    manager.RangedNearables += (sender, e) => 
			    {
			        new UIAlertView("Nearables Found", "Just found: " + e.Nearables.Length + " nearables.", null, "OK").Show();
			    };

			    manager.StartRanging (NearableType.All);
			}*/
        }

		/// <summary>
		/// Intializes the Interface Service Method whcih is called through
		/// Dependency Service in the shared folder.
		/// </summary>
		/// <param name="role">Role.</param>
		/// <param name="range">Range.</param>
		public void InitializeService(Model.Entity.Role role, double range)
        {
            this.role = role;
            this.beaconRange = range;
            setRole();
        }

		/// <summary>
		/// Sets the current logged in role of the user.
		/// </summary>
		public void setRole()
        {
            if (role.ToString().Equals("Techniker"))
            {
                currentRole = Major.TECHNICIAN.GetHashCode();
            }
            else if (role.ToString().Equals("Schieber"))
            {
                currentRole = Major.EMPLOYEE.GetHashCode();
            }
        }

		/// <summary>
		/// Destructor for the Beacon Service.
		/// </summary>
		public void StopBLEService()
        {
            beaconManager.StopAdvertising();
            beaconManager.StopMonitoringForAllRegions();
            beaconManager.StopRangingBeaconsInAllRegions();
            compareSet.Clear();
            beaconSet.Clear();
            setLongId.Clear();
            beaconManager = null;
            role = null;
            mutex = true;
            beaconRange = -1;
            currentRole = -1;
        }

		/// <summary>
		/// Inserts the found beacon into a list and calls a method which converts the
		/// list of beacons for further usage.
		/// </summary>
		/// <returns>The list of beacons.</returns>
		/// <param name="beacons">Beacons.</param>
		private async Task UpdateListOfBeacons(List<Beacon> beacons)
        {
            await Task.Run(() =>
            {
                var newBeacons = new List<Beacon>();
                var listForLoop = beacons;

                try
                {
                    foreach (var beacon in listForLoop.ToList())
                    {
                        beaconSet.Add(beacon);
                    }
                    SendList();
                    listForLoop.Clear();
                }
                catch (InvalidOperationException ioe)
                {
                    Console.WriteLine(ioe);
                }
            });
        }

		/// <summary>
		/// Sends the list of beacons through a eventhandler to the shared folder, where
		/// the list of ID's is furhter processed.
		/// </summary>
        private void SendList()
        {
            var handler = UpdateListEvent;
            if (beaconSet.Count != compareSet.Count)
            {
                ConvertList();
                DisplayLongSet();
                handler(this, new EventListUpdate(setLongId.ToList()));
                compareSet = new HashSet<Beacon>(beaconSet);
                setLongId.Clear();
            }
            beaconSet.Clear();
            setLongId.Clear();
        }

		/// <summary>
		/// Converts the list of beacons into a list of ID's.
		/// </summary>
		private void ConvertList()
        {
            foreach (Beacon beacon in beaconSet)
            {
                if (beacon.Major.GetHashCode() == 59009)
                {
                    setLongId.Add("" + 0);
                }
                else if (beacon.Minor.GetHashCode() == 10)
                {
                    setLongId.Add("" + 3);
                }
                else
                {
                    setLongId.Add(beacon.ProximityUUID.ToString());
                }
            }
        }

		/// <summary>
		/// Displays the Set of ID's on the console.
		/// (Used for debugging purposes only)
		/// </summary>
		private void DisplayLongSet()
        {
            Console.Write(System.Environment.NewLine + "-------- SET LONG ID" + System.Environment.NewLine + "{");
            foreach (String l in setLongId)
            {
                Console.Write("{0}", l);
            }
            Console.Write("}" + System.Environment.NewLine + "-------------");
            Debug.WriteLine(System.Environment.NewLine
               + System.Environment.NewLine);
        }
    }
}

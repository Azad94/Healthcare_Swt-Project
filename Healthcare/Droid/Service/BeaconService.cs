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
using HealthcareApp.Model.Service;
using HealthcareApp.Model.Enumeration;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using System.Diagnostics;

[assembly: Xamarin.Forms.Dependency(typeof(HealthcareApp.Droid.Service.BeaconService))]

namespace HealthcareApp.Droid.Service
{
    /// <summary>
    /// This class contains the implementation of the Beacon Service for Android devices.
    /// The class is used through the Dependency Service, which is called in the shared
    /// folder.
    /// </summary>
    public class BeaconService : Java.Lang.Object, IBeaconService
    {
        private readonly MonitorNotifier monitorNotifier;
        private readonly RangeNotifier rangeNotifier;

        private BeaconManager beaconManager;
        private Region region;

        List<Beacon> beaconsInRange;
        private HashSet<Beacon> compareSet;
        private HashSet<Beacon> beaconSet;
        private HashSet<String> setLongId;

        private Model.Entity.Role role;
        private double beaconRange;
        private int currentRole;
        //used for checking whether the update can be called, to avoid race conditions
        private bool mutex;
        private int sendEmptyListCounter;
        private int resetValue;

        public event EventHandler<EventListUpdate> UpdateListEvent;

        /// <summary>
        /// Initializes a new instance of the <see cref="T:HealthcareApp.Droid.Service.BeaconService"/> class.
        /// </summary>
        public BeaconService()
        {
            monitorNotifier = new MonitorNotifier();
            rangeNotifier = new RangeNotifier();
            beaconsInRange = new List<Beacon>();
            beaconSet = new HashSet<Beacon>();
            setLongId = new HashSet<string>();
            compareSet = new HashSet<Beacon>();
            mutex = false;
            sendEmptyListCounter = 2;
            resetValue = 2;
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
            beaconManager = InitializeBeaconManager();
        }

        /// <summary>
        /// Sets the current logged in role of the user.
        /// </summary>
        private void setRole()
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
        /// Initializes the beacon manager, sets the Monitoring and Ranging Regions.
        /// Furthermore the standard of the Beacon which is suppose to be found, is 
        /// set here. In the current context iBeacon standard beacons are searched/found.
        /// </summary>
        /// <returns>The beacon manager.</returns>
        private BeaconManager InitializeBeaconManager()
        {
            // Enable the BeaconManager 
            BeaconManager bm = BeaconManager.GetInstanceForApplication(Xamarin.Forms.Forms.Context);

            var iBeaconParser = new BeaconParser();
            //  sets the requirement for the Beacons (here Estimote > 2013)
            iBeaconParser.SetBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24");
            bm.BeaconParsers.Add(iBeaconParser);

            monitorNotifier.EnterRegionComplete += EnteredRegion;
            monitorNotifier.ExitRegionComplete += ExitedRegion;
            monitorNotifier.DetermineStateForRegionComplete += DeterminedStateForRegionComplete;
            rangeNotifier.DidRangeBeaconsInRegionComplete += RangingBeaconsInRegion;

            region = new Region("beaconId", null, null, null);

            bm.SetBackgroundMode(true);
            bm.Bind((IBeaconConsumer)Xamarin.Forms.Forms.Context);
            return bm;
        }

        /// <summary>
        /// Starts the monitoring and sets the time for background and active scanning.
        /// </summary>
        public void StartMonitoring()
        {
            //duration of scan, when app is in background 1 seconds
            BeaconManagerImpl.SetForegroundScanPeriod(10000L);
            //duration between each scan while app is opened 1 seconds
            BeaconManagerImpl.SetForegroundBetweenScanPeriod(10000L);

            BeaconManagerImpl.SetBackgroundMode(true);
            //duration of scan, when app is in background 1 seconds
            BeaconManagerImpl.SetBackgroundScanPeriod(10000L);
            //duration between each scane, while app iws in background 1 seconds
            BeaconManagerImpl.SetBackgroundBetweenScanPeriod(10000L);

            BeaconManagerImpl.SetMonitorNotifier(monitorNotifier);
            beaconManager.StartMonitoringBeaconsInRegion(region);
        }

        /// <summary>
        /// Starts the monitoring and sets the time for background and active scanning.
        /// </summary>
        public void StartRanging()
        {
            //duration of scan, when app is in background 1 seconds
            BeaconManagerImpl.SetForegroundScanPeriod(10000L);
            //duration between each scan while app is opened 1 seconds
            BeaconManagerImpl.SetForegroundBetweenScanPeriod(10000L);

            BeaconManagerImpl.SetBackgroundMode(true);
            //duration of scan, when app is in background 1 seconds
            BeaconManagerImpl.SetBackgroundScanPeriod(10000L);
            //duration between each scane, while app is in background 1 seconds
            BeaconManagerImpl.SetBackgroundBetweenScanPeriod(10000L);

            BeaconManagerImpl.SetRangeNotifier(rangeNotifier);
            beaconManager.StartRangingBeaconsInRegion(region);
        }

        /// <summary>
        /// Destructor for the Beacon Service.
        /// </summary>
		public void StopBLEService()
        {
            BeaconManagerImpl.StopRangingBeaconsInRegion(region);
            BeaconManagerImpl.StopMonitoringBeaconsInRegion(region);
            compareSet.Clear();
            beaconSet.Clear();
            setLongId.Clear();
            beaconManager = null;
            region = null;
            role = null;
            mutex = true;
            beaconRange = -1;
            currentRole = -1;
        }

        /// <summary>
        /// Determineds the state for region complete.
        /// (Console purpose only, could be enhanced for notifications, from folder Helper with class Notifier)
        /// </summary>
        /// <param name="sender">Sender.</param>
        /// <param name="e">E.</param>
        private void DeterminedStateForRegionComplete(object sender, EventMonitor e)
        {
            Console.WriteLine("DeterminedStateForRegionComplete");
        }

        /// <summary>
        /// Exited the region.
        /// (Console purpose only, could be enhanced for notifications, from folder Helper with class Notifier)
        /// </summary>
        /// <param name="sender">Sender.</param>
        /// <param name="e">E.</param>
        private void ExitedRegion(object sender, EventMonitor e)
        {
            Console.WriteLine("ExitedRegion");
        }

        /// <summary>
        /// Entered the region.
        /// (Console purpose only, could be enhanced for notifications, from folder Helper with class Notifier)
        /// </summary>
        /// <param name="sender">Sender.</param>
        /// <param name="e">E.</param>
        private void EnteredRegion(object sender, EventMonitor e)
        {
            Console.WriteLine("EnteredRegion");
        }

        /// <summary>
        /// If a beacon is in range this method is triggered, which checks if the beacon
        /// in range is either relevant for the logged in user and if the object is of any
        /// interest of the logged in user. If both are verfied another method for updating
        /// the list of beacons is called.
        /// </summary>
        /// <param name="sender">Sender.</param>
        /// <param name="e">E.</param>
        async void RangingBeaconsInRegion(object sender, EventRange e)
        {
            var sortedList = new List<Beacon>();

            if (!mutex)
            {
                if (e.Beacons.Count > 0)
                {
                    foreach (var beacon in e.Beacons)
                    {
                        if (beacon.Distance < beaconRange)
                        {
                            //Beacons by Professor are internally mapped to Employee or Technician
                            if ((beacon.Id2.ToInt() == 58009)
                                && currentRole == Major.EMPLOYEE.GetHashCode())
                            {
                                beaconsInRange.Add(beacon);
                            }
                            //Beacons by Professor are internally mapped to Employee or Technician
                            else if (beacon.Id2.ToInt() == 10
                                     && currentRole == Major.EMPLOYEE.GetHashCode())
                            {
                                beaconsInRange.Add(beacon);
                            }
                            else if (beacon.Id2.ToInt() == currentRole)
                            {
                                if (currentRole == Major.TECHNICIAN.GetHashCode())
                                {
                                    beaconsInRange.Add(beacon);
                                }
                                else if (currentRole == Major.EMPLOYEE.GetHashCode())
                                {
                                    if (beacon.Id3.ToInt() == Minor.FIREDOOR.GetHashCode())
                                    {
                                        beaconsInRange.Add(beacon);
                                    }
                                    else if (beacon.Id3.ToInt() == Minor.BED.GetHashCode())
                                    {
                                        beaconsInRange.Add(beacon);
                                    }
                                }
                            }
                        }
                    }
                }
                mutex = true;
                await UpdateListOfBeacons(beaconsInRange);
                beaconsInRange.Clear();
                e.Beacons.Clear();
                mutex = false;
            }
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

                    if (beaconSet.Count == 0 && sendEmptyListCounter == resetValue)
                    {
                        SendList();
                        sendEmptyListCounter = 0;
                    }
                    else if (beaconSet.Count != 0)
                    {
                        SendList();
                        sendEmptyListCounter = 0;
                    }
                    if (beaconSet.Count == 0)
                    {
                        if (sendEmptyListCounter > resetValue)
                        {
                            sendEmptyListCounter = 0;
                        }
                        sendEmptyListCounter++;
                    }
                    listForLoop.Clear();
                }
                catch (InvalidOperationException ioe)
                {
                    Console.WriteLine(ioe);
                }
            });
        }

        /// <summary>
        /// Sends the list of beacons through a eventhandler to the shared folder, 
        /// where the list of ID's is furhter processed.
        /// </summary>
        private void SendList()
        {
            var handler = UpdateListEvent;
            if (beaconSet.Count != compareSet.Count)
            {
                ConvertList();
                DisplayLongSet();
                handler(this, new EventListUpdate(setLongId.ToList()));

                if (setLongId.Count != 0)
                {
                    if (setLongId.Count == 1)
                    {
                        Helper.Notifier.Notify("Es ist ein Auftrag in der Nähe, der bearbeitet werden muss.");
                    }
                    else
                    {
                        Helper.Notifier.Notify("Es ist sind" + setLongId.Count() + "Aufträge in der Nähe, die bearbeitet werden müssen.");
                    }
                }

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
                //The Id's matching to the Beacons by Professor are
                //coded into source due to mapping reasons
                if (beacon.Id2.ToInt() == 58009)
                {
                    setLongId.Add("00000010-healthcarew123456789101");
                }
                else if (beacon.Id2.ToInt() == 10)
                {
                    setLongId.Add("00000009-healthcarew123456789101");
                }
                //the other beacons are handled in the correct way
                else
                {
                    setLongId.Add(beacon.Id1.ToString());
                }
            }
        }

        /// <summary>
        /// Displays the Set of ID's on the console.
        /// (Used for debugging purposes only)
        /// </summary>
        private void DisplayLongSet()
        {
            Debug.WriteLine(System.Environment.NewLine + System.Environment.NewLine);
            Console.Write(System.Environment.NewLine + "Beacon ID's found" + System.Environment.NewLine + "{");
            foreach (String l in setLongId)
            {
                Console.Write("{0}", l);
            }
            Console.Write("}" + System.Environment.NewLine + "-------------");
            Debug.WriteLine(System.Environment.NewLine + System.Environment.NewLine);
        }
    }
}

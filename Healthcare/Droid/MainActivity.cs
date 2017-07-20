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

using Android.App;
using Android.Content.PM;
using Android.OS;
using AltBeaconOrg.BoundBeacon;
using AltBeaconOrg.BoundBeacon.Powersave;
using AltBeaconOrg.BoundBeacon.Startup;

namespace HealthcareApp.Droid
{
	[Activity(Label = "HealthcareApp", Icon = "@drawable/icon", Theme = "@style/MainTheme", MainLauncher = true, ConfigurationChanges = ConfigChanges.ScreenSize | ConfigChanges.Orientation)]
	public class MainActivity : global::Xamarin.Forms.Platform.Android.FormsAppCompatActivity, IBootstrapNotifier, IBeaconConsumer
	{
        BackgroundPowerSaver backgroundPowerSaver;

        /// <summary>
        /// Initializes some classes and plugins on creation.
        /// </summary>
        /// <param name="bundle">Bundle.</param>
		protected override void OnCreate(Bundle bundle)
		{
			TabLayoutResource = Resource.Layout.Tabbar;
			ToolbarResource = Resource.Layout.Toolbar;
            backgroundPowerSaver = new BackgroundPowerSaver(this);
			ZXing.Net.Mobile.Forms.Android.Platform.Init();
			base.OnCreate(bundle);

			global::Xamarin.Forms.Forms.Init(this, bundle);
			LoadApplication(new HealthcareApp.App());
		}

		/// <summary>
		/// Used for debugging purposes only but could be used for Notifications.
		/// </summary>
		/// <param name="state">State.</param>
		/// <param name="region">Region.</param>
		public void DidDetermineStateForRegion(int state, Region region)
		{
			System.Diagnostics.Debug.WriteLine($"DidDetermineStateForRegion {state}, {region}");
		}

		/// <summary>
		///  Used for debugging purposes only but could be used for Notifications.
		/// </summary>
		/// <param name="region">Region.</param>
		public void DidEnterRegion(Region region)
		{
			System.Diagnostics.Debug.WriteLine($"DidEnterRegion {region}");
		}

		/// <summary>
		///  Used for debugging purposes only but could be used for Notifications.
		/// </summary>
		/// <param name="region">Region.</param>
		public void DidExitRegion(Region region)
		{
			System.Diagnostics.Debug.WriteLine($"DidExitRegion {region}");
		}

		/// <summary>
		/// Initializes the Interface.
		/// </summary>
		public void OnBeaconServiceConnect()
		{
            var beaconService = Xamarin.Forms.DependencyService.Get<HealthcareApp.Model.Service.IBeaconService>();

			beaconService.StartMonitoring();
			beaconService.StartRanging();
		}
	}
}

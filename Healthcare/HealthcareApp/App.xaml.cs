/**
 * /*******************************************************************************
 * Copyright 2017 Sheraz Azad, Daniel Dzimitrowicz, Jonas Grams, Malte Grebe & Niklas Klatt
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
using HealthcareApp.Model.Synchronization;
using SQLiteDatabase;
using HealthcareApp.Model.Service;
using Xamarin.Forms;
using HealthcareApp.Model.REST;

namespace HealthcareApp
{
	public partial class App : Application
	{
		private Database dataBase;
		private SynchronizationManager synchMng;
		private WifiConnectionManager wifiMng;
		private AppCore appCore;
        private BluetoothManager bluetoothManager;
        private RestMng restMng;

		public App()
		{
            InitializeComponent();
			MainPage = new NavigationPage(new HealthcareApp.View.LoginPage());
		}

        protected override void OnStart()
        {
            initAppComponents();
        }

        protected override void OnSleep()
        {
        }

        protected override void OnResume()
        {
        }

        /// <summary>
        /// Initiation of all application-modules
        /// </summary>
		private void initAppComponents()
		{
            restMng = new RestMng(Global.restAd);

            dataBase = new Database();
            bluetoothManager = new BluetoothManager();

            appCore = AppCore.Instance;
            appCore.SetDatabase(dataBase);
            appCore.SetRestMng(restMng);
            synchMng = new SynchronizationManager(appCore, dataBase, restMng);
			wifiMng = new WifiConnectionManager(appCore, synchMng);
		}
	}
}

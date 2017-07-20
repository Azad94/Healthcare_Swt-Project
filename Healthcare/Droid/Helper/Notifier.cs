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
using Android.App;
using Android.Content;
using System.Runtime.CompilerServices;
using Xamarin.Forms;

namespace HealthcareApp.Droid.Helper
{
    /// <summary>
    /// Notification class for creating a notification if beacons are in range.
    /// (Could be enhanced with sound notification)
    /// </summary>
	internal static class Notifier
	{
		internal static void Notify(string text)
		{
            Notification.Builder builder = new Notification.Builder(Forms.Context)
                            .SetContentTitle("Aufträge in der Nähe")
                            .SetContentText(text)
							.SetSmallIcon(Resource.Drawable.icon);

			Notification notification = builder.Build();

			NotificationManager notificationManager =
					Forms.Context.GetSystemService(Context.NotificationService) as NotificationManager;
			const int notificationId = 11;

			notificationManager.Notify(notificationId, notification);
		}
	}
}

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

using HealthcareApp.Model;
using HealthcareApp.Model.Enumeration;
using System;
using Xamarin.Forms;
using ZXing.Net.Mobile.Forms;

namespace HealthcareApp.View
{
    /// <summary>
    /// This class contains the Qr Scanner implementation.
    /// </summary>
    public class ScanPage : ZXingScannerPage
    {
		AppCore appcore;
		Model.Entity.Role role;
  
		string message;
        string stringId;

        char charMajor;
        char charMinor;

        int major;
        int minor;
        int currentRole;

        /// <summary>
        /// Initializes a new instance of the <see cref="T:HealthcareApp.View.ScanPage"/> class.
        /// When a Qr Code is scanned the result is handled in different methods.
        /// </summary>
        public ScanPage() : base()
        {
            appcore = AppCore.Instance;
            setRole();
            Title = "QR Code scannen";
            OnScanResult += (result) =>
            {
                IsScanning = false;
                HandleResult(result.ToString());

                Device.BeginInvokeOnMainThread(() =>
                {
                    //Navigation.PopAsync();
                    DisplayAlert("Scanned QR Code", result.ToString(), "Close");
                    appcore.changeQRTasks(stringId);
                    Navigation.PushAsync(new TasksPage().CurrentPage = new QRTaskPage());
                });
            };
        }

        /// <summary>
        /// Sets the current role of the logged in user.
        /// </summary>
        private void setRole()
        {
            role = appcore.GetUserRole();
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
        /// Processes the result read from the Qr Scanner.
        /// </summary>
        /// <param name="result">Result.</param>
        private void HandleResult(String result)
        {
            if (result.ToCharArray().Length != 34)
            {
                message = "Qr Code ist mit dieser App nicht kompatibel.";
            }
            else
            {
                stringId = result.Substring(0, 31);
                charMajor = result[32];
                major = Convert.ToInt32(charMajor.ToString());

                charMinor = result[33];
                minor = Convert.ToInt32(charMinor.ToString());

                if (!IsRelevant())
                {
                   message = "Keine Autorisation für diesen QR Code";
                }
            }
        }

        /// <summary>
        /// Checks whether the Qr Scanned is relevant for the current user.
        /// </summary>
        /// <returns><c>true</c>, if relevant was ised, <c>false</c> otherwise.</returns>
        private bool IsRelevant()
        {
            if (major == currentRole)
            {
                if (currentRole == Major.TECHNICIAN.GetHashCode())
                {
                    //message = "Scan was successful.";
                    message = stringId;
                    return true;
                }
                else if (currentRole == Major.EMPLOYEE.GetHashCode())
                {
                    if (minor == Minor.FIREDOOR.GetHashCode())
                    {
                        message = stringId;
                        //message = "Scan was successful.";
                        return true;
                    }
                    else if (minor == Minor.BED.GetHashCode())
                    {
                        message = "Scan was successful.";
                        return true;
                    }
                }
            }
            message = "Scan failed.";
            return false;
        }
    }
}

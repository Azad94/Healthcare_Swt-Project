using System;
using Xamarin.Forms;

namespace HealthcareApp.Model.Entity.RestEntity
{
    [Serializable]
    public class SubTaskImageJSON : AbstractSubTaskJSON
    {
        public Image value { get; set; }

        public SubTaskImageJSON(SubTaskImage sC) : base(sC)
        {
           this.value = sC.Value;
        }
    }
}

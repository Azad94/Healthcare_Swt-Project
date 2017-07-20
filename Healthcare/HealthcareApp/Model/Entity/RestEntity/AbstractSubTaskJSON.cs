using System;
namespace HealthcareApp.Model.Entity.RestEntity
{
    [Serializable]
    public class AbstractSubTaskJSON
    {
        public string title { get; set; }
        public AbstractSubTaskJSON(AbstractSubTask sT) 
        {
            this.title = sT.Title;
        }
    }
}

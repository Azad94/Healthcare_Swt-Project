using System;
namespace HealthcareApp.Model.Entity.RestEntity
{
    [Serializable]
    public class PictureOfObjectJSON
    {
        public byte[] picture { get; set; }

        public PictureOfObjectJSON(PictureOfObject p)
        {
            this.picture = p.Picture;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HealthcareApp.Model.Entity.Rest
{
    class IdentifierJSON
    {
        private String name;
        private long id; 
        public IdentifierJSON(long id, String name)
        {
            this.name = name;
            this.id = id;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HealthcareApp.Model.Entity.Rest
{
    class TransportTasksJSON
    {
        private IdentifierJSON identifier;
        private List<TransportTask> tTL;

        public TransportTasksJSON(IdentifierJSON id, List<TransportTask> ttL)
        {
            this.identifier = id;
            this.tTL = ttL;
        }
    }
}

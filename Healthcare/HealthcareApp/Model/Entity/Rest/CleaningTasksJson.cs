using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HealthcareApp.Model.Entity.Rest
{
    class CleaningTasksJSON
    {
        private IdentifierJSON identifierJSON;
        private List<CleaningTask> cTL;

        public CleaningTasksJSON(IdentifierJSON identifierJSON, List<CleaningTask> cTL)
        {
            this.identifierJSON = identifierJSON;
            this.cTL = cTL;
        }
    }
}

using System.Collections.Generic;

namespace HealthcareApp.Model.Entity.Rest
{
    class MaintenanceTasksJSON
    {
        private IdentifierJSON identifier;
        private List<MaintenanceTask> mT;

        public MaintenanceTasksJSON(IdentifierJSON id, List<MaintenanceTask> mt)
        {
            this.identifier = id;
            this.mT = mt;
        }
    }
}

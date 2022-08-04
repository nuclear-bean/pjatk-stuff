using System;
using System.Collections.Generic;

namespace Cw10.ModelsFromDb
{
    public partial class Studies
    {
        public Studies()
        {
            Enrollment = new HashSet<Enrollment>();
        }

        public int IdStudy { get; set; }
        public string Name { get; set; }

        public virtual ICollection<Enrollment> Enrollment { get; set; }
    }
}

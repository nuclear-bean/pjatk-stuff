using System;

using System.Collections.Generic;

using System.Linq;

using System.Threading.Tasks;



namespace Kolokwium1.Models

{

    public class Prescriptions

    {

        public int IdPerscription { get; set; }

        public DateTime Date { get; set; }

        public DateTime DueTime { get; set; }

        public int IdPatient { get; set; }

        public int IdDoctor { get; set; }

    }

}

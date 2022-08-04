using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
namespace Kolokwium1.Models
{
    public class PrescriptionsMedicament
    {
        public int IdPerscription { get; set; }
        public int IdMedicament { get; set; }
        public int Dose { get; set; }
        public string Details { get; set; }
    }
}


using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
namespace Kolokwium1.DTOs
{
    public class PrescriptionResponse
    {
        public DateTime date { get; set; }
        public DateTime dueDate { get; set; }
        public int idPatient { get; set; }
        public int idDoctor { get; set; }
        public int dose { get; set; }
        public string details { get; set; }
        public string medName { get; set; }
        public string medDescription { get; set; }
        public string medType { get; set; }
        public List<string> meds { get; set; }
    }
}

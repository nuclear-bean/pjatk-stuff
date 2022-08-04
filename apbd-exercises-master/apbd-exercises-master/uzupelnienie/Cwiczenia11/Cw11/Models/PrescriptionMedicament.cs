using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace Cw11.Models
{
    [Table("Recepta_Lekarstwo")]
    public class PrescriptionMedicament
    {
        [Required]
        public int IdMedicament { get; set; }
        [Required]
        public int IdPrescription { get; set; }
        public int? Dose { get; set; }
        [Required]
        [MaxLength(50)]
        public string Details { get; set; }
    }
}

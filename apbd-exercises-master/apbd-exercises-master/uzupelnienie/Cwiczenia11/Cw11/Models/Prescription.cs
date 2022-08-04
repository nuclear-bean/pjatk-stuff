using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace Cw11.Models
{
    [Table("Recepta")]
    public class Prescription
    {
        [Key]
        [Required]
        public int IdPrescription { get; set; }

        [Required]
        [ForeignKey("Patient")]
        public int IdPatient { get; set; }

        [Required]
        [ForeignKey("Doctor")]
        public int IdDoctor { get; set; }

        [Required]
        public DateTime Date { get; set; }

        [Required]
        public DateTime DueDate { get; set; }
       
    }
}

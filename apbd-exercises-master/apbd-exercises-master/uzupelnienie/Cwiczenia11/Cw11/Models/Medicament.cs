using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace Cw11.Models
{
    [Table("Lekarstwo")]
    public class Medicament
    {
        [Key]
        [Required]
        public int IdMedicament { get; set; }
        [Required]
        [MaxLength(50)]
        public string Name { get; set; }
        [Required]
        [MaxLength(50)]
        public string Description { get; set; }
        [Required]
        [MaxLength(50)]
        public string Type { get; set; }
    }
}

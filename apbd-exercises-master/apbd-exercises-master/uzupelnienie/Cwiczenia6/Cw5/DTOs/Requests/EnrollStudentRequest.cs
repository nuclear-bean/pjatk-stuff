using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw5.DTOs.Requests
{
    public class EnrollStudentRequest
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string IndexNumber { get; set; }
        public string BirthDate { get; set; }
        public string Studies { get; set; }
    }
}

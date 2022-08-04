using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw10.DTOs.Requests
{
    public class PromoteStudentRequest
    {
        public int Semester { get; set; }
        public string Studies { get; set; }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw5.DTOs.Responses
{
    public class EnrollStudentResponse
    {
        public int Semester { get; set; }
        public int IdEnrollment { get; set; }
        public int IdStudy { get; set; }
        public DateTime StartDate { get; set; }
    }
}

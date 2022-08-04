using Cw5.DTOs;
using Cw5.DTOs.Requests;
using Cw5.DTOs.Responses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw5.Services
{
    public interface StudentService
    {
        public EnrollStudentResult EnrollStudent(EnrollStudentRequest request);

        public EnrollStudentResponse PromoteStudents(PromoteStudentRequest request);
    }
}

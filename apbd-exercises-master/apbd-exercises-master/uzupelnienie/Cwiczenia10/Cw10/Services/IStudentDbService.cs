using Cw10.DTOs.Requests;
using Cw10.DTOs.Responses;
using Cw10.DTOs.Results;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw10.Services
{
    public interface IStudentDbService
    {
        public EnrollStudentResult EnrollStudent(EnrollStudentRequest request);

        public IEnumerable<PromoteStudentResponse> PromoteStudent(PromoteStudentRequest request);
    }
}
using Cw10.DTOs.Requests;
using Cw10.DTOs.Results;
using Cw10.Services;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw10.Controllers
{
    [Route("api/enrollments")]
    [ApiController]
    public class EnrollmentsController : ControllerBase
    {

        private IStudentDbService service;

        public EnrollmentsController(IStudentDbService service)
        {
            this.service = service;
        }

        [HttpPost]
        public IActionResult EntrollStudent(EnrollStudentRequest request)
        {
            var result = service.EnrollStudent(request);

            return Ok("Student został zapisany");


        }

        [HttpPost("promotions")]
        public IActionResult PromoteStudent(PromoteStudentRequest request)
        {


            var result = service.PromoteStudent(request);
            return Ok("Student został awansowany");
        }
    }
}

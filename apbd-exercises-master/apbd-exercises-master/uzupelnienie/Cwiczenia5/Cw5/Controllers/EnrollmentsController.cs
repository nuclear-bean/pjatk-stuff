using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Cw5.DTOs;
using Cw5.DTOs.Requests;
using Cw5.DTOs.Responses;
using Cw5.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace Cw5.Controllers
{

    [Route("api/enrollments")]
    [ApiController]
    public class EnrollmentsController : ControllerBase
    {
        private StudentService service;
     
        public EnrollmentsController(StudentService service){
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
            var result = service.PromoteStudents(request);
            return Ok("Student został awansowany");
        }
    }
}

using Cw10.DTOs.Requests;
using Cw10.Services;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw10.Controllers
{
    [Route("api/promote")]
    [ApiController]
    public class PromoteController : ControllerBase
    {

        private IStudentDbService service;

        public PromoteController(IStudentDbService service)
        {
            this.service = service;
        }

        [HttpPost]
        public IActionResult PromoteStudent(PromoteStudentRequest request)
        { 

            var result = service.PromoteStudent(request);

            return Created("",result);
        }
    }
}

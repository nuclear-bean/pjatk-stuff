using Cw10.ModelsFromDb;
using Cw10.Services;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw10.Controllers
{
    [Route("api/student")]
    [ApiController]
    public class StudentController : ControllerBase
    {

        private IEfDbService service;

        public StudentController(IEfDbService service)
        {
            this.service = service;
        }

        [HttpGet]
        [Route("get")]
        public IActionResult GetStudent()
        {
            var result = service.GetStudents();
            return Ok(result);
        }

        [HttpPost]
        [Route("update")]
        public IActionResult UpdateStudent(Student student)
        {
            var result = service.UpdateStudents(student);

                return Ok(result);
        }

        [HttpPost]
        [Route("insert")]
        public IActionResult InsertStudent(Student student)
        {
            var result = service.InsertStudents(student);

            if (result == null)
            {
                return BadRequest("Student o takim indeksie juz istnieje!");
            }

            return Ok("Student został dodany!");

        }

        [HttpDelete]
        [Route("delete")]
        public IActionResult DeleteStudent(Student student)
        {
            var result = service.DeleteStudents(student);

            if (result == null)
            {
                return BadRequest("Nie istnieje student o podanym indeksie!");
            }

            return Ok("Student o podanym indeksie został usunięty!");

        }
    }
}

using System;
using Cw3.DAL;
using Cw3.Models;
using Microsoft.AspNetCore.Mvc;

namespace Cw3
{
    [ApiController]
    [Route("api/students")]

    public class StudentsController : ControllerBase
    {
        private readonly IDbService _dbService;

        public StudentsController(IDbService dbService)
        {
            _dbService = dbService;
        }

        [HttpGet]
        public IActionResult GetStudents(string orderBy)
        {
            return Ok(_dbService.GetStudents());
        }

        //public string GetStudent(string orderBy)
        //{
        //    //if (id == 1)
        //    //{
        //    //    return Ok("Kowalski");
        //    //}
        //    //else if (id == 2) {
        //    //    return Ok("Malewski");
        //    //}

        //    //return NotFound("Nie znaleziono studenta");

        //    return $"Kowalski, Malewski, Andrzejewski sortowanie={orderBy}";
        //}

        [HttpPost]
        public IActionResult CreateStudent(Student student)
        {
            //generate index number
            student.IndexNumber = $"s{new Random().Next(1, 20000)}";
            return Ok(student);
        }

        [HttpPut("{id}")]
        public IActionResult UpdateStudent(int id)
        {
            //update student
            

            return Ok($"zaktualizowano studenta o id {id}");
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteStudent(int id)
        {
            //delete student


            return Ok($"usunieto studenta o id {id}");
        }
    }
}
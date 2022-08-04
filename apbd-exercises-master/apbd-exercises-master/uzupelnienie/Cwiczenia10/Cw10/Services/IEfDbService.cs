using Cw10.ModelsFromDb;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw10.Services
{
    public interface IEfDbService
    {
        public List<Student> GetStudents();

        public Student UpdateStudents(Student student);

        public Student DeleteStudents(Student student);

        public Student InsertStudents(Student student);
    }
}

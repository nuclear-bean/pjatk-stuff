using Cw10.ModelsFromDb;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw10.Services
{
    public class EfDbService : IEfDbService
    {
        private readonly s18830Context context;

        public EfDbService(s18830Context context)
        {
            this.context = context;
        }

        public Student DeleteStudents(Student student)
        {         
            var result = context.Student.FirstOrDefault(s => s.IndexNumber == student.IndexNumber);

            if (result == null)
            {
                return null;
            }

            context.Student.Remove(result);
            context.SaveChanges();
            return result;            

        }

        public List<Student> GetStudents()
        {
            return context.Student.ToList();
        }

        public Student InsertStudents(Student student)
        {
            var result = context.Student.FirstOrDefault(e => e.IndexNumber == student.IndexNumber);

            if (result == null)
            {
                context.Student.Add(student);
                context.SaveChanges();
                return student;
            }

            return null;
        }

        public Student UpdateStudents(Student student)
        {                          
            try
            {              
                context.Attach(student);
                context.Entry(student).State = EntityState.Modified;
                context.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                return null;
            }
            catch (Exception)
            {

                throw;
            }                     

            return student;
        }
    }
}

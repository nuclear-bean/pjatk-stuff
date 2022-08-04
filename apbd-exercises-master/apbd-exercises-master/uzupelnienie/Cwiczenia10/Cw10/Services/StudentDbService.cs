using Cw10.DTOs.Requests;
using Cw10.DTOs.Responses;
using Cw10.DTOs.Results;
using Cw10.ModelsFromDb;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading.Tasks;

namespace Cw10.Services
{
    public class StudentDbService : IStudentDbService
    {
        private readonly s18830Context context;

        public StudentDbService(s18830Context context)
        {
            this.context = context;


        public IEnumerable<PromoteStudentResponse> PromoteStudent(PromoteStudentRequest request)
        {
            var responsesList = new List<PromoteStudentResponse>();
            //szukamy kierunku o podanej nazwie
            var study = context.Studies.Where(s => s.Name == request.Studies).FirstOrDefault();
            //szukamy wpisu dla danego kierunku na dany semester
            var enrollment = context.Enrollment.Where(e => e.Semester == request.Semester && e.IdStudy == study.IdStudy).First();
            //szukamy wpisu dla danego kierunku na następny semestr
            var newEnrollment = context.Enrollment.Where(e => e.Semester == request.Semester + 1 && e.IdStudy == study.IdStudy).FirstOrDefault();
            //identyfikujemy ostatnie id w bazie i zwiększamy o 1
            var newIdEnrollment = context.Enrollment.Max(e => e.IdEnrollment) + 1;
            if (newEnrollment == null)
            {
                context.Enrollment.Add(new Enrollment
                {
                    IdEnrollment = newIdEnrollment,
                    Semester = request.Semester + 1,
                    IdStudy = study.IdStudy,
                    StartDate = DateTime.Now
                });
                //wyszukujemy dodany właśnie obiekt
                newEnrollment = context.Enrollment.Where(e => e.Semester == request.Semester + 1 && e.IdStudy == study.IdStudy).FirstOrDefault();
            }
            //szukamy studentów, którym będziemy chcieli dodać wpis na nowy semestr
            var studentsList = context.Student.Where(student => student.IdEnrollment == enrollment.IdEnrollment).ToList();

            foreach (var student in studentsList)
            {
                student.IdEnrollment = newEnrollment.IdEnrollment;
            }

            //tworzymy obiekt do zwrócenia i dodajemy go do listy
            var psresponse = new PromoteStudentResponse
            {
                IdEnrollment = newEnrollment.IdEnrollment,
                Semester = request.Semester + 1,
                IdStudy = study.IdStudy,
                StartDate = DateTime.No
            };
            responsesList.Add(psresponse);
            return responsesList;
        }
        
        public EnrollStudentResult EnrollStudent(EnrollStudentRequest request)
        {
            EnrollStudentResult result = new EnrollStudentResult();

            var studies = context.Studies.FirstOrDefault(e => e.Name == request.Studies);

            context.SaveChanges();
                if (studies == null)
                {                                       
                    return result;
                }
            int idStudy = studies.IdStudy;       

            var bigSelect = context.Student.Any(x => x.IndexNumber == "" && x.IdEnrollmentNavigation.Semester == 1);

                if (bigSelect)
                {                    
                    return result;
                }

            var maxSelect = context.Enrollment.Max(e => e.IdEnrollment);
            int maxId = maxSelect + 1;     
            DateTime startDate = DateTime.Now;

            context.Enrollment.Add(new Enrollment
            {
                IdEnrollment = maxId,
                Semester = 1,
                IdStudy = idStudy,
                StartDate = startDate,

            });

            var nameSelect = context.Student.Any(e => e.IndexNumber == request.IndexNumber);
                if (nameSelect)
                {                    
                    return result;
                }

            DateTime date = DateTime.Parse(request.BirthDate);
            context.Student.Add(new Student
            {
                IndexNumber = request.IndexNumber,
                FirstName = request.FirstName,
                LastName = request.LastName,
                BirthDate = date,
                IdEnrollment = maxId
            });             

            var response = new EnrollStudentResponse
            {
                IdEnrollment = maxId,
                IdStudy = idStudy,
                Semester = 1,
                StartDate = startDate
            };

            
            result.Response = response;
            return result;
        }
    }
}

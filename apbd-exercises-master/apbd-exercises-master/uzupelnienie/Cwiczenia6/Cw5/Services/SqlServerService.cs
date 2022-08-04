using Cw5.DTOs;
using Cw5.DTOs.Requests;
using Cw5.DTOs.Responses;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;

namespace Cw5.Services
{
    public class SqlServerService : StudentService
    {

        public EnrollStudentResult EnrollStudent(EnrollStudentRequest request)
        {
            EnrollStudentResult result = new EnrollStudentResult();            

            using (var con = new SqlConnection("Data Source=db-mssql;Initial Catalog=s18830;Integrated Security=True"))
            using (var com = new SqlCommand())
            {
                com.Connection = con;
                con.Open();

                var tran = con.BeginTransaction();
                com.Transaction = tran;

                com.CommandText = "select IdStudy from Studies where Name = @name";
                com.Parameters.AddWithValue("name", request.Studies);

                var dr = com.ExecuteReader();

                if (!dr.Read())
                {
                    dr.Close();
                    tran.Rollback();
                    throw new Exception("Nie ma kierunku o takiej nazwie!");
                }

                int idStudy = (int)dr["IdStudy"];

                dr.Close();

                com.CommandText = "select e.StartDate, e.IdEnrollment from Enrollment e join Student s on e.IdEnrollment = s.IdEnrollment " +
                    "where e.Semester = 1 and s.IndexNumber = @IndexNumber " +
                    "order by StartDate desc";

                com.Parameters.AddWithValue("IndexNumber", request.IndexNumber);

                dr = com.ExecuteReader();
                if (dr.Read())
                {
                    dr.Close();
                    tran.Rollback();
                    throw new Exception("Student został zapisany wcześniej");
                }
                dr.Close();

                com.CommandText = "select max(IdEnrollment) from Enrollment";
                int maxId = (int)com.ExecuteScalar() + 1;
                DateTime startDate = DateTime.Now;

                com.CommandText = "Insert into Enrollment (IdEnrollment, Semester, IdStudy, StartDate) values (@IdEnrollment, @Semester, @IdStudy, @StartDate)";
                com.Parameters.AddWithValue("IdEnrollment", maxId);
                com.Parameters.AddWithValue("Semester", 1);
                com.Parameters.AddWithValue("IdStudy", idStudy);
                com.Parameters.AddWithValue("StartDate", startDate);

                com.ExecuteNonQuery();

                com.CommandText = "select FirstName from Student where IndexNumber = @IndexNumber";
                com.Parameters.AddWithValue("IndexNumber", request.IndexNumber);

                dr = com.ExecuteReader();
                if (dr.Read())
                {
                    dr.Close();
                    tran.Rollback();
                    throw new Exception("Ten student jest już w systemie!");
                }
                dr.Close();

                com.CommandText = "Insert into Student (IndexNumber, FirstName, LastName, BirthDate, IdEnrollment) values (@Index, @Fname, @LName, @Date, @IdEnroll)";

                com.Parameters.AddWithValue("Index", request.IndexNumber);
                com.Parameters.AddWithValue("Fname", request.FirstName);
                com.Parameters.AddWithValue("Lname", request.LastName);
                com.Parameters.AddWithValue("Date", request.BirthDate);
                com.Parameters.AddWithValue("IdEnroll", maxId);

                com.ExecuteNonQuery();

                tran.Commit();

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

        public EnrollStudentResponse PromoteStudents(PromoteStudentRequest request)
        {
            using (var con = new SqlConnection("Data Source=db-mssql;Initial Catalog=s18830;Integrated Security=True"))
            using (var com = new SqlCommand())
            {
                com.CommandType = CommandType.StoredProcedure;
                com.Connection = con;
                con.Open();

                com.CommandText = "PromoteStudents";

                com.Parameters.AddWithValue("@Name", SqlDbType.NVarChar).Value = request.Studies;
                com.Parameters.AddWithValue("@Semester", SqlDbType.Int).Value = request.Semester;

                com.Parameters.Add("@IdStudies", SqlDbType.Int).Direction = ParameterDirection.Output;
                com.Parameters.Add("@IdEnrollment", SqlDbType.Int).Direction = ParameterDirection.Output;               
                com.Parameters.Add("@StartDate", SqlDbType.DateTime).Direction = ParameterDirection.Output;

                com.ExecuteNonQuery();

                var response = new EnrollStudentResponse
                {
                    IdEnrollment = (int)com.Parameters["@IdEnrollment"].Value,
                    IdStudy = (int)com.Parameters["@IdStudies"].Value,
                    Semester = request.Semester + 1,
                    StartDate = (DateTime)com.Parameters["@StartDate"].Value
                };

                return response;
            }
        }

    }
}

using Cw11.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw11.Services
{
    public class DbService : IDbService
    {

        private readonly MedicamentContext context;

        public DbService(MedicamentContext context)
        {
            this.context = context;
        }
        public List<Doctor> GetDoctors()
        {
            return context.Doctors.ToList();
        }
        public Doctor InsertDoctors(Doctor doctor)
        {
            var result = context.Doctors.FirstOrDefault(e => e.IdDoctor == doctor.IdDoctor);

            if (result == null)
            {
                context.Doctors.Add(doctor);
                context.SaveChanges();
                return doctor;
            }

            return null;
        }

        public Doctor UpdateDoctors(Doctor doctor)
        {
            context.Attach(doctor);
            context.Entry(doctor).State = EntityState.Modified;
            context.SaveChanges();

            return doctor;
        }
        public Doctor DeleteDoctors(Doctor doctor)
        {
            var result = context.Doctors.FirstOrDefault(s => s.IdDoctor == doctor.IdDoctor);

            if (result == null)
            {
                return null;
            }

            context.Doctors.Remove(result);
            context.SaveChanges();
            return result;
        }
        
    }
}

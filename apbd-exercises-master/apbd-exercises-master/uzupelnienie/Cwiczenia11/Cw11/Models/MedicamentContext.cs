using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Cw11.Models
{
    public class MedicamentContext : DbContext
    {
        public DbSet<Doctor> Doctors { get; set; }
        public DbSet<Patient> Patients { get; set; }
        public DbSet<Prescription> Prescriptions { get; set; }
        public DbSet<Medicament> Medicaments { get; set; }
        public DbSet<PrescriptionMedicament> Prescription_Medicament { get; set; }

        public MedicamentContext(DbContextOptions options) : base(options)
        {
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<PrescriptionMedicament>()
           .HasKey(e => new { e.IdMedicament, e.IdPrescription });

            SeedData(modelBuilder);
        }

        public void SeedData(ModelBuilder modelBuilder)
        {
            var doctors = new List<Doctor>();

            doctors.Add(new Doctor
            {
                IdDoctor = 1,
                FirstName = "Kamil",
                LastName = "Nowak",
                Email = "nowak@wp.pl"
            });


            doctors.Add(new Doctor
            {
                IdDoctor = 2,
                FirstName = "Lukasz",
                LastName = "Malpa",
                Email = "malpa@gmail.com"
            });

            modelBuilder.Entity<Doctor>().HasData(doctors);

            var patients = new List<Patient>();

            patients.Add(new Patient
            {
                IdPatient = 1,
                FirstName = "Agnieszka",
                LastName = "Filipowicz",
                Birthdate = DateTime.Parse("1996-11-29")
            });

            patients.Add(new Patient
            {
                IdPatient = 2,
                FirstName = "Grzegorz",
                LastName = "Kolderka",
                Birthdate = DateTime.Parse("2000-05-12")
            });

            modelBuilder.Entity<Patient>().HasData(patients);

            var medicaments = new List<Medicament>();

            medicaments.Add(new Medicament
            {
                IdMedicament = 1,
                Name = "Strepsils",
                Description = "Na gardlo",
                Type = "Tabletki",
            });

            medicaments.Add(new Medicament
            {
                IdMedicament = 2,
                Name = "Ibuprom",
                Description = "Na ból głowy",
                Type = "Tabletki",
            });

            modelBuilder.Entity<Medicament>().HasData(medicaments);

            var prescriptions = new List<Prescription>();

            prescriptions.Add(new Prescription
            {
                IdPrescription = 1,
                Date = DateTime.Now,
                DueDate = DateTime.Now.AddMonths(1),
                IdPatient = 1,
                IdDoctor = 1

            });

            prescriptions.Add(new Prescription
            {
                IdPrescription = 2,
                Date = DateTime.Now,
                DueDate = DateTime.Now.AddMonths(1),
                IdPatient = 2,
                IdDoctor = 2
            });

            modelBuilder.Entity<Prescription>().HasData(prescriptions);

            var prescriptions_medicaments = new List<PrescriptionMedicament>();

            prescriptions_medicaments.Add(new PrescriptionMedicament
            {
                IdPrescription = 1,
                IdMedicament = 1,
                Dose = 2,
                Details = "Przed posiłkiem"
            });

            prescriptions_medicaments.Add(new PrescriptionMedicament
            {
                IdPrescription = 2,
                IdMedicament = 2,
                Details = "W trakcie posiłku"
            });

            modelBuilder.Entity<PrescriptionMedicament>().HasData(prescriptions_medicaments);
        }
    }
}

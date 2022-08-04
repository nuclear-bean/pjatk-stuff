namespace Kolokwium1.Controllers
{
    [Route("api/prescriptions")]
    [ApiController]
    public class PrescriptionsController : ControllerBase
    {
        [HttpGet("{id}")]
        public IActionResult GetPerscription(int id)
        {
        using (SqlConnection con = new SqlConnection("Data Source = db-mssql; Initial Catalog=s18830; Integrated Security=True"))
                com.Connection = con;
                using (SqlCommand com = new SqlCommand())
                {
                try
                {
                    var list = new List<Prescriptions>();
                    var lista = new List<PrescriptionsMedicament>();
                    com.Parameters.AddWithValue("IdPrescription", id);

                    com.CommandText = "select Name Type,Date ,DueDate ,
Description ,Dose , IdPatient,IdDoctor from Prescription_Medicament
JOIN Prescription on Prescription.IdPrescription = Prescription_Medicament.IdPrescription
JOIN Medicament on Medicament.IdMedicament = Prescription_Medicament.IdMedicament where IdPrescription =@id";
                    con.Open();

                    SqlDataReader dr = com.ExecuteReader();
                 
                    while (dr.Read()){
                        var recepta = new Prescriptions();
                        recepta.IdPerscription = (int)dr["IdPrescription"];
                        recepta.Date = DateTime.Parse(dr["Date"].ToString());
                        recepta.DueTime = DateTime.Parse(dr["DueDate"].ToString());
                        recepta.IdPatient = (int)dr["IdPatient"];
                        recepta.IdDoctor = (int)dr["IdDoctor"];
                        var permed = new PrescriptionsMedicament();
                        var listaMed = new List<Medicament>();
                        permed.IdPerscription = (int)dr["IdPrescription"]; ;
                        permed.IdMedicament = (int)dr["IdMedicament"];
                        listaMed.Dose = (int)dr["Dose"];
                        listaMed.Details = (dr["Details"].ToString());
                        lista.Add(permed);
                        list.Add(recepta);   
                    }
                    return Ok(list + "\n" + lista);
                }
                catch (SqlException sql)
                {
                    return BadRequest("Wysłano nieprawidłowe zapytanie");
                }
        }
        
    }
    
}

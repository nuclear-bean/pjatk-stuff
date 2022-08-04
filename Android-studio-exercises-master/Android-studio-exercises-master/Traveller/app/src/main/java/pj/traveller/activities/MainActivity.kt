package pj.traveller.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Criteria
import android.location.Geocoder
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import pj.traveller.IteamAdapter
import pj.traveller.databinding.ActivityMainBinding
import pj.traveller.db.IteamsDB
import pj.traveller.shared.Holder
import java.text.SimpleDateFormat
import java.util.*
private const val REQUEST_EDIT_TRANSFER = 2
class MainActivity : AppCompatActivity() {
    private val binding by lazy {

        ActivityMainBinding.inflate(layoutInflater)
    }

    val iteamAdapter by lazy { IteamAdapter(this) }
    companion object { lateinit var database: IteamsDB }
    lateinit var myloc: String
    val locman by lazy { getSystemService(LocationManager::class.java) }
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

    //...
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)


    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        database = IteamsDB.getState(applicationContext)

        getLoc()
        binding.expenses.apply {
            adapter = iteamAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }



        binding.add.setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }


        binding.settings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }

    }




        //for (photo in Holder.photoList)
        //print(photo.photoUri)

    override fun onResume() {
        super.onResume()

        iteamAdapter.refresh()
        checkLoc()
    }



    fun checkLoc(){

        for (photo in Holder.photoList)
            if(photo.gps == myloc)
                Toast.makeText(applicationContext, "Rember? ${photo.note}", Toast.LENGTH_SHORT).show()

    }







    @SuppressLint("MissingPermission")
    fun getLoc(): String {

        val criteria = Criteria().apply {
            accuracy = Criteria.ACCURACY_FINE
        }

        val best = locman.getBestProvider(criteria, true) ?: ""
        var loc = locman.getLastKnownLocation(best)

        // var locc:String =  loc?.latitude.toString() + " "+loc?.longitude.toString()


        var latitude = loc!!.latitude
        var longitude = loc!!.longitude


        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        val address: String = addresses[0]
            .getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        val city: String = addresses[0].getLocality()
        val country: String = addresses[0].getCountryName()
        val fullAd= city+" "+country
        myloc= fullAd

        return city + " " + country

    }

}



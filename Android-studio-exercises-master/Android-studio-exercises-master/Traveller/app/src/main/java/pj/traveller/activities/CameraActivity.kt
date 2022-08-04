package pj.traveller.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.location.Address
import android.location.Criteria
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.toColorInt
import androidx.core.view.drawToBitmap
import com.google.android.gms.location.FusedLocationProviderClient
import pj.traveller.Photo
import pj.traveller.databinding.ActivityCameraBinding
import pj.traveller.db.Iteam
import pj.traveller.db.IteamsDB
import pj.traveller.shared.Holder
import pj.traveller.shared.Settings
import java.io.*
import java.io.File.separator
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


@Suppress("DEPRECATION")
class CameraActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCameraBinding.inflate(layoutInflater)
    }

    private val db by lazy { IteamsDB.getState(applicationContext) }

    val locman by lazy { getSystemService(LocationManager::class.java) }
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())


    @RequiresApi(Build.VERSION_CODES.O)
    var nowDate: LocalDate = LocalDate.now()
    var note: String? = null;
    var imageUri: Uri? = null
    var index = -1
    var locc: String? = null
    var mybitmap: Bitmap? = null;
    lateinit var file: File


    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var date: LocalDate = LocalDate.now()
        nowDate = date
        locc = oldGetLast()


        binding.takePhoto.setOnClickListener {
            var uri = generateUri()
           // imageUri = uri

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).let {
                it.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
            startActivityForResult(intent, 1)
          // startActivityForResult(intent, 1)

        }

        binding.saveButton.setOnClickListener {

            note = binding.noteText.text.toString()
            val noteMax = if (note!!.length < 500) note else note!!.substring(0, 501)


            var photo: Photo = Photo(imageUri.toString(), nowDate, locc, noteMax)
          //  var iteamsDB = Iteam(noteMax.toString(), imageUri.toString(), locc.toString(), nowDate.toString())

            Holder.photoList.add(photo)
//                db.iteamsDao.insert(iteamsDB)

            startActivity(Intent(this, MainActivity::class.java))

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateUri(): Uri {

        file = filesDir.resolve("img_${timeStamp}.jpg").also {
            it.writeText("")
        }

         var x = FileProvider.getUriForFile(
            this,
            "pj.traveller.FileProvider",
            file
        )
       // imageUri = x
        return x
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        addExtra()
        if (requestCode == 1 && resultCode == RESULT_OK) {

            // bitmap.setImageBitmap(binding.imageView2)
            binding.imageView2.setImageBitmap(
                mybitmap
                // BitmapFactory.decodeFile(
                //filesDir.resolve("img_${timeStamp}.jpg").absolutePath
            )

            mybitmap?.let { saveImage(it,this,"traveller") }
            //)

        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onResume() {
        super.onResume()


    }

    override fun onStop() {

        super.onStop()


    }

    @SuppressLint("MissingPermission")
    fun oldGetLast(): String {

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



        return city + " " + country

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun addExtra(): Bitmap {

        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        val newBitmap = bitmap.copy(bitmap.config, true)
        val can = Canvas(newBitmap)

        Paint().apply {
            this.color = Settings.textColor
            this.textSize = Settings.textSize.toFloat()
            typeface = Typeface.DEFAULT

            var text = locc + " " + nowDate

            can.drawText(text, 40f, can.height - 90f, this)

            mybitmap = newBitmap


            return newBitmap
        }


    }

    private fun saveImage(bitmap: Bitmap, context: Context, folderName: String) {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName)
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            // RELATIVE_PATH and IS_PENDING are introduced in API 29.

            val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
                imageUri = uri
            }
        } else {
            val directory = File(Environment.getExternalStorageDirectory().toString() + separator + folderName)
            // getExternalStorageDirectory is deprecated in API 29

            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName = System.currentTimeMillis().toString() + ".png"
            val file = File(directory, fileName)
            imageUri = Uri.fromFile(file)
            saveImageToStream(bitmap, FileOutputStream(file))
            if (file.absolutePath != null) {
                val values = contentValues()
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                // .DATA is deprecated in API 29
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            }
        }
    }

    private fun contentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}
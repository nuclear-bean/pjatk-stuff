package pj.traveller.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.FileProvider
import pj.traveller.databinding.ActivityCameraBinding
import pj.traveller.databinding.ActivityMainBinding
import pj.traveller.databinding.ActivityPhotoBinding

class PhotoActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityPhotoBinding.inflate(layoutInflater)
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}
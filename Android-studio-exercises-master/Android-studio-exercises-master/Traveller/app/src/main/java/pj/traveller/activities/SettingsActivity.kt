package pj.traveller.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pj.traveller.databinding.ActivitySettingsBinding
import pj.traveller.shared.Settings

class SettingsActivity : AppCompatActivity() {


    private val binding by lazy {

        ActivitySettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.editTextFont.setText(Settings?.textSize.toString())
        binding.editTextColor.setText(Settings.textColor.toString())
        binding.editTextNumber.setText(Settings?.locationRadius.toString())


        binding.backbutton.setOnClickListener {
            saveSettings()
            startActivity(Intent(this, MainActivity::class.java)) }

    }

    private fun saveSettings() {
        Settings.textSize = binding.editTextFont.text.toString().toInt()


        if(binding.editTextColor.text.toString()=="Black"){
            Settings.textColor = Color.BLACK
        }else if(binding.editTextColor.text.toString()=="Red"){
            Settings.textColor = Color.RED
        }else if(binding.editTextColor.text.toString()=="Blue") {
            Settings.textColor = Color.BLUE
        }
            else if(binding.editTextColor.text.toString()=="Green") {
            Settings.textColor = Color.GREEN
        }else if(binding.editTextColor.text.toString()=="White"){
            Settings.textColor = Color.WHITE
            }


        Settings.locationRadius = binding.editTextNumber.text.toString().toInt()

        finish()


    }
}
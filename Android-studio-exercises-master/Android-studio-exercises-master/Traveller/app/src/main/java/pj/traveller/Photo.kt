package pj.traveller

import android.location.LocationManager
import java.time.LocalDate


data class Photo(
    var photoUri: String,
    var date: LocalDate,
    var gps: String?,
    var note: String?
)
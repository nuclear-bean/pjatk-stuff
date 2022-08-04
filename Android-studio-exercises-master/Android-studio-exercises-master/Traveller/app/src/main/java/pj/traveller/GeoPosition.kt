package pj.traveller

data class GeoPosition (val country: String, val city: String){
        override fun toString(): String {
            return "{\"country\":$country,\"city\":$city}"
        }
    }
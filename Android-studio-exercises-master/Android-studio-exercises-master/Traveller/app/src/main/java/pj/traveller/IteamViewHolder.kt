package pj.traveller

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import pj.traveller.databinding.IteamBinding

class IteamViewHolder(
    private val layoutBinding: IteamBinding
) : RecyclerView.ViewHolder(layoutBinding.root){


    fun bind(photo: Photo) = with(layoutBinding) {


        data.text = photo.date.toString()
        locc.text = photo.gps
        note.text = photo.note.toString()
        val uri: Uri? = Uri.parse(photo.photoUri)
         imageView2.setImageURI(uri)



    }




}
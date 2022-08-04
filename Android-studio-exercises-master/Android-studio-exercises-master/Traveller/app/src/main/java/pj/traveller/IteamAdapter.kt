package pj.traveller

import android.app.AlertDialog
import android.os.Build
import android.os.SharedMemory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import pj.traveller.activities.MainActivity
import pj.traveller.databinding.IteamBinding
import pj.traveller.shared.Holder

class IteamAdapter (private val mainActivity: MainActivity) : RecyclerView.Adapter<IteamViewHolder>() {


    private var iteamList = Holder.photoList


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IteamViewHolder {
        val binding = IteamBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IteamViewHolder(binding).also { holder ->
            binding.root.setOnLongClickListener { remove(holder.layoutPosition, parent) }
            binding.root.setOnClickListener{ showNote(holder.layoutPosition,parent) }



        }
    }

    private fun showNote(layoutPosition: Int, parent: ViewGroup) : Boolean {


       var note = Holder.photoList.get(layoutPosition).note

        val builder = AlertDialog.Builder(parent.context)
        builder.setMessage(note)
            .setCancelable(true)
            .setNegativeButton("Thanks") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
        refresh()


        return true


    }

    override fun getItemCount(): Int = iteamList.size

    override fun onBindViewHolder(holder: IteamViewHolder, position: Int) {

            holder.bind(iteamList[position])

       // var expense = Holder.iteamList.get(position)

    }

    fun refresh() = notifyDataSetChanged()

    fun add(photo: Photo) {
        iteamList.add(photo)
        notifyItemInserted(iteamList.size - 1)
    }


    fun remove(position: Int, parent: ViewGroup): Boolean {

        val builder = AlertDialog.Builder(parent.context)
        builder.setMessage("Delete this postion?")
            .setCancelable(true)
            .setPositiveButton("Yes") { i, w ->
                Holder.photoList.removeAt(position)
             //   MainActivity.database.iteamsDao.delete(Holder.photoList.get(position).photoUri)
                notifyItemRemoved(position)
                notifyItemChanged(position)

            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
        refresh()


        return true
    }






}
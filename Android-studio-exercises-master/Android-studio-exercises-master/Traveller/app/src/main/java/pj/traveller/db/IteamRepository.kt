package pj.traveller.db

import androidx.lifecycle.LiveData

class IteamRepository(private val iteamDao: IteamDao){
  //  val readAllData: LiveData<List<Iteam>> =  iteamDao.readAllData()

    suspend fun addIteam(iteam: Iteam){
       // iteamDao.addIteam(iteam)
    }
}
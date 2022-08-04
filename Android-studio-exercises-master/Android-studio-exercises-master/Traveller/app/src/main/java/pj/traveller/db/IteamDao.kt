package pj.traveller.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IteamDao {

    @Query("SELECT * FROM iteams")
    fun getAll(): LiveData<List<Iteam>>

    @Query("SELECT * from iteams WHERE photoUri = :key")
    fun getIteam(key: String): Iteam?

    @Insert
    fun insert(iteam: Iteam)

    @Query("DELETE from iteams where photoUri = :key")
    fun delete(key: String)
}
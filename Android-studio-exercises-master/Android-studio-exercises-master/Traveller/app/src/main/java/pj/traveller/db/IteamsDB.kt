package pj.traveller.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase


@Database(entities = [Iteam::class], version = 1,exportSchema = false)
abstract class IteamsDB : RoomDatabase() {

    abstract val iteamsDao: IteamDao

    companion object{

        @Volatile
        private  var INSTANCE: IteamsDB? = null
        
        fun getState(context: Context): IteamsDB {

            synchronized(this){
                var state = INSTANCE
                if (state == null){
                    state = databaseBuilder(context.applicationContext,IteamsDB::class.java,"database").fallbackToDestructiveMigration().build()
                    INSTANCE = state
                    return state

                }
                return state
            }
        }
    }

}
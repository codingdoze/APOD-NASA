package wale.nasa.apod.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import wale.nasa.apod.data.db.entities.PictureOfTheDay

@Dao
interface PictureOfTheDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: PictureOfTheDay?): Long

    @Query("SELECT * FROM PhotoOfTheDay")
    fun getPhotoOfTheDay(): LiveData<List<PictureOfTheDay?>?>
}
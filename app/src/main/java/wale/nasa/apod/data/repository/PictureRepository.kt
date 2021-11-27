package wale.nasa.apod.data.repository

import android.content.Context
import com.goldmedal.hrapp.data.db.AppDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import wale.nasa.apod.R
import wale.nasa.apod.data.db.entities.PictureOfTheDay
import wale.nasa.apod.data.network.PictureOfTheDayApi
import javax.inject.Inject

class PictureRepository @Inject constructor(
    private val api: PictureOfTheDayApi,
    private val db: AppDatabase,
   @ApplicationContext private val context: Context
) : BaseRepository(api) {


    /* In a production App Api key can be encrypted for better security */

    suspend fun getPictureOfTheDay() = safeApiCall { api.getPictureOfTheDay(context.getString(R.string.api_key)) }

    suspend fun savePhotoOfTheDay(photo: PictureOfTheDay?) = db.getPhotoOfTheDayDao().upsert(photo)

    fun getPhotoOfTheDay() = db.getPhotoOfTheDayDao().getPhotoOfTheDay()

}
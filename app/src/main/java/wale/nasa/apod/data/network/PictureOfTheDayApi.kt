package wale.nasa.apod.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import wale.nasa.apod.data.db.entities.PictureOfTheDay

interface PictureOfTheDayApi : BaseApi {


    @GET("planetary/apod")
   suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String)
            : PictureOfTheDay


}
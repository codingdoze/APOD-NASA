package wale.nasa.apod.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhotoOfTheDay")
data class PictureOfTheDay(
    @PrimaryKey
    val title: String = "",
    val copyright: String?,
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val url: String?
)
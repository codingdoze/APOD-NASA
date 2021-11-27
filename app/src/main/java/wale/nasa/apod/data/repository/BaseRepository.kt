package wale.nasa.apod.data.repository

import wale.nasa.apod.data.network.BaseApi
import wale.nasa.apod.data.network.SafeApiCall

abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {
    /* Common Impl can be found here
    *
    *
    * */
}
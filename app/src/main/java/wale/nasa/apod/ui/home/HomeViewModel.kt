package wale.nasa.apod.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import wale.nasa.apod.data.db.entities.PictureOfTheDay
import wale.nasa.apod.data.network.Resource
import wale.nasa.apod.data.repository.PictureRepository
import wale.nasa.apod.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PictureRepository
) : BaseViewModel(repository) {

    fun getPictureOfTheDayFromDb() = repository.getPhotoOfTheDay()

   suspend fun savePictureOfTheDay(photo: PictureOfTheDay?) = repository.savePhotoOfTheDay(photo)


    private val _pictureOfDay: MutableLiveData<Resource<PictureOfTheDay>> = MutableLiveData()
    val pictureOfDay: LiveData<Resource<PictureOfTheDay>>


        get() = _pictureOfDay
    fun getPictureOfTheDay() = viewModelScope.launch {
        _pictureOfDay.value = Resource.Loading
        _pictureOfDay.value = repository.getPictureOfTheDay()
    }

}
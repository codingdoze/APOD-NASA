package wale.nasa.apod.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import wale.nasa.apod.R
import wale.nasa.apod.data.db.entities.PictureOfTheDay
import wale.nasa.apod.data.network.Resource
import wale.nasa.apod.databinding.FragmentHomeBinding
import wale.nasa.apod.ui.snackbar
import wale.nasa.apod.ui.visible

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.progressbar.visible(false)


        viewModel.getPictureOfTheDay()

        viewModel.pictureOfDay.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    lifecycleScope.launch {
                        viewModel.savePictureOfTheDay(it.value)
                    }
                    bindPhotoUi(it.value)
                }
                is Resource.Loading -> {
                  binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    binding.progressbar.visible(false)
                    handleApiError(it)
                }
            }
        })

    }
    private fun bindPhotoUi(value: PictureOfTheDay?) {
        val url: String? = value?.url
        if (url.isNullOrEmpty()) {
            // Photo is not present
        } else {
            with(binding) {
                imageViewPhotoOfDay.load(url)
                textViewMessage.text = value.explanation
                textViewTitle.text = value.title
            }
        }
    }

    private fun handleApiError(
        failure: Resource.Failure,
        retry: (() -> Unit)? = null
    ) {
        when {
            failure.isNetworkError -> {
                requireView().snackbar(
                    "We are not connected to the internet, showing you the last image we have.",
                    retry
                )
                viewModel.getPictureOfTheDayFromDb().observe(viewLifecycleOwner, {
                    if (!it.isNullOrEmpty()) {
                        bindPhotoUi(it[0])
                    }

                })
            }
            else -> {
                val error = failure.errorBody?.string().toString()
                requireView().snackbar(error)
            }
        }
    }
}
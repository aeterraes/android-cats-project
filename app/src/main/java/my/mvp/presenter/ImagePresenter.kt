package my.mvp.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.mvp.api.Breed
import my.mvp.model.ImageRepository
import my.mvp.view.ImageViewInterface

class ImagePresenter(private val view: ImageViewInterface, private val repository: ImageRepository) {
    fun loadImagesForBreeds(breedIds: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val images = breedIds.flatMap { breedId ->
                    repository.fetchImages(breedId).take(1).map { image ->
                        val actualBreed = image.breeds.firstOrNull() ?: Breed(breedId, "Unknown Breed")
                        image.copy(breeds = listOf(actualBreed))
                    }
                }
                withContext(Dispatchers.Main) { view.showImages(images) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { view.showError(e.message ?: "Error") }
            }
        }
    }

    fun loadImagesForSingleBreed(breedId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val images = repository.fetchImages(breedId).take(10)
                withContext(Dispatchers.Main) { view.showImages(images) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { view.showError(e.message ?: "Error") }
            }
        }
    }
}


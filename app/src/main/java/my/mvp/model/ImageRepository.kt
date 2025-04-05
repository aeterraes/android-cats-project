package my.mvp.model

import my.mvp.api.ApiService
import my.mvp.api.ImageResponse

class ImageRepository(private val apiService: ApiService) {
    suspend fun fetchImages(breedId: String): List<ImageResponse> {
        return apiService.getImagesByBreed(limit = 8, breedId = breedId)
    }
}

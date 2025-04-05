package my.mvp.model

import my.mvp.api.ApiService
import my.mvp.api.ImageResponse

/*
class ImageRepository(private val context: Context) {
    fun getCategories(): List<ImageItem> {
        val assetManager = context.assets
        val categoryFolders = assetManager.list("categories") ?: return emptyList()

        return categoryFolders.mapNotNull { category ->
            val images = assetManager.list(
                "categories/$category") ?: return@mapNotNull null
            if (images.isNotEmpty()) {
                ImageItem(
                    name = category.replace("_", " ")
                        .replaceFirstChar { if (it.isLowerCase())
                            it.titlecase(Locale.ROOT) else it.toString() },
                    path = "categories/$category/${images.first()}",
                    category = category
                )
            } else {
                null
            }
        }
    }
}

 */
class ImageRepository(private val apiService: ApiService) {
    suspend fun fetchImages(breedId: String): List<ImageResponse> {
        return apiService.getImagesByBreed(limit = 8, breedId = breedId)
    }
}


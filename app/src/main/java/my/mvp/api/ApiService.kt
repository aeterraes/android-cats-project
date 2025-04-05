package my.mvp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("images/search")
    suspend fun getImagesByBreed(
        @Query("limit") limit: Int = 8,
        @Query("breed_ids") breedId: String,
    ): List<ImageResponse>
}

data class ImageResponse(
    val url: String,
    val breeds: List<Breed> = emptyList()
)

data class Breed(
    val id: String,
    val name: String
)

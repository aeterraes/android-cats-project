package my.mvp.view

import my.mvp.api.ImageResponse

interface ImageViewInterface {
    fun showImages(images: List<ImageResponse>)
    fun showError(message: String)
}
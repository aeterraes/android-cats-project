package my.mvp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.mvp.api.ApiClient
import my.mvp.api.ImageResponse
import my.mvp.model.ImageRepository
import my.mvp.presenter.ImagePresenter
import my.mvp.view.ImageAdapter
import my.mvp.view.ImageViewInterface

class MainActivity : AppCompatActivity(), ImageViewInterface {
    private lateinit var presenter: ImagePresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        progressBar = findViewById(R.id.progressBar)

        adapter = ImageAdapter(emptyList(), onClick = { image ->
            val breedId = image.breeds.firstOrNull()?.id
            if (breedId != null) {
                startActivity(GalleryActivity.newIntent(this, breedId))
            }
        }, showBreedName = true)

        recyclerView.adapter = adapter

        val repository = ImageRepository(ApiClient.create())
        presenter = ImagePresenter(this, repository)
        showLoading(true)
        presenter.loadImagesForBreeds(listOf("abys", "bsho", "munc", "beng", "java", "chau", "orie", "sfol"))
    }

    override fun showImages(images: List<ImageResponse>) {
        showLoading(false)
        adapter.updateImages(images)
    }

    override fun showError(message: String) {
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}

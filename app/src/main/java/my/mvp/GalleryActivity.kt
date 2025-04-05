package my.mvp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
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

class GalleryActivity : AppCompatActivity(), ImageViewInterface {
    private lateinit var presenter: ImagePresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val breedId = intent.getStringExtra(EXTRA_BREED_ID)
        if (breedId == null) {
            finish()
            return
        }

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = ImageAdapter(emptyList(), onClick = {}, showBreedName = false)
        recyclerView.adapter = adapter

        val repository = ImageRepository(ApiClient.create())
        presenter = ImagePresenter(this, repository)
        showLoading(true)
        presenter.loadImagesForSingleBreed(breedId)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
            applyTransition()
        }
    }

    override fun showImages(images: List<ImageResponse>) {
        showLoading(false)
        adapter.updateImages(images)
    }

    override fun showError(message: String) {
        println("Error: $message")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        applyTransition()
    }

    private fun applyTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(
                Activity.OVERRIDE_TRANSITION_CLOSE,
                R.anim.fade_in,
                R.anim.fade_out
            )
        } else {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    companion object {
        private const val EXTRA_BREED_ID = "breed_id"
        fun newIntent(context: Activity, breedId: String): Intent {
            return Intent(context, GalleryActivity::class.java).apply {
                putExtra(EXTRA_BREED_ID, breedId)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}

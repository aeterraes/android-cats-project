package my.mvp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.mvp.R
import my.mvp.api.ImageResponse

class ImageAdapter(private var images: List<ImageResponse>, private val onClick: (ImageResponse) -> Unit,
                   private val showBreedName: Boolean = true) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: CustomView = view.findViewById(R.id.imageView)
        val breedNameText: TextView = view.findViewById(R.id.breedNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        val breedName = if (image.breeds.isNotEmpty()) {
            image.breeds[0].name
        } else {
            "Unknown Breed"
        }

        if (showBreedName) {
            holder.breedNameText.text = image.breeds.firstOrNull()?.name ?: "Unknown"
            holder.breedNameText.visibility = View.VISIBLE
        } else {
            holder.breedNameText.visibility = View.GONE
        }
        Glide.with(holder.imageView.context)
            .load(image.url)
            .into(holder.imageView)

        holder.breedNameText.text = breedName
        holder.itemView.setOnClickListener { onClick(image) }
    }

    override fun getItemCount() = images.size

    fun updateImages(newImages: List<ImageResponse>) {
        val diffCallback = DiffUtilCallback(images, newImages)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        images = newImages
        diffResult.dispatchUpdatesTo(this)
    }
}

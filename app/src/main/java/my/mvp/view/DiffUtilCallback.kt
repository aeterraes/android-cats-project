package my.mvp.view

import androidx.recyclerview.widget.DiffUtil
import my.mvp.api.ImageResponse

class DiffUtilCallback(
    private val oldList: List<ImageResponse>,
    private val newList: List<ImageResponse>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].url == newList[newItemPosition].url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
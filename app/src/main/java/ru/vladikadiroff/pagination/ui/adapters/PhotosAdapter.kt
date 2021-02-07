package ru.vladikadiroff.pagination.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import ru.vladikadiroff.pagination.presentation.models.PhotosViewEvent
import ru.vladikadiroff.pagination.ui.adapters.viewholders.PhotoViewHolder
import javax.inject.Inject

@ActivityRetainedScoped
class PhotosAdapter @Inject constructor() :
    PagingDataAdapter<PhotoModel, PhotoViewHolder>(DiffUtilCallback()) {

    private var listener: ((PhotosViewEvent) -> Unit)? = null

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let { model -> holder.bind(model) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(parent, listener)
    }

    fun addEventListener(listener: (PhotosViewEvent) -> Unit) {
        this.listener = listener
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<PhotoModel>() {
        override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
            return oldItem.likes == newItem.likes && oldItem.photoThumbnail == newItem.photoThumbnail
        }
    }

}
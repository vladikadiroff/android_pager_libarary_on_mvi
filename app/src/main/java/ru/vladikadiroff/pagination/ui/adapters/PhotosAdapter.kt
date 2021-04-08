package ru.vladikadiroff.pagination.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.vladikadiroff.pagination.presentation.models.*
import ru.vladikadiroff.pagination.ui.adapters.viewholders.PhotosViewHoldersFactory
import ru.vladikadiroff.pagination.ui.adapters.viewholders.PhotosViewHoldersFactory.*
import ru.vladikadiroff.pagination.utils.abstracts.BaseViewHolder

class PhotosAdapter(private val listener: (PhotosViewEvent) -> Unit) :
    PagingDataAdapter<PhotoModel, BaseViewHolder>(PhotosAdapterDiffUtill()) {

    override fun getItemViewType(position: Int) = getRequireItem(position).viewType

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is PhotoHeaderViewHolder -> holder.bind(getRequireItem(position) as PhotoHeaderModel)
            is PhotoImageViewHolder -> holder.bind(getRequireItem(position) as PhotoImageModel)
            is PhotoFooterViewHolder -> holder.bind(getRequireItem(position) as PhotoFooterModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotosViewHoldersFactory.create(parent, viewType, listener)

    private fun getRequireItem(position: Int) =
        getItem(position) ?: throw IllegalArgumentException("Item in position $position not found")

    class PhotosAdapterDiffUtill : DiffUtil.ItemCallback<PhotoModel>() {

        override fun areItemsTheSame(
            oldItem: PhotoModel,
            newItem: PhotoModel
        ): Boolean {
            return newItem.id == oldItem.id && newItem.viewType == oldItem.viewType
        }

        override fun areContentsTheSame(
            oldItem: PhotoModel,
            newItem: PhotoModel
        ): Boolean {
            return true
        }

    }

}
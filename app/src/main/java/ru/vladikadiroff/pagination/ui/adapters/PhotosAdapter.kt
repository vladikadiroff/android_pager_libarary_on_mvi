package ru.vladikadiroff.pagination.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import ru.vladikadiroff.pagination.presentation.models.PhotosViewEvent
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterFooterModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterHeaderModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterImageModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel
import ru.vladikadiroff.pagination.ui.adapters.viewholders.*
import ru.vladikadiroff.pagination.utils.abstracts.BaseViewHolder
import ru.vladikadiroff.pagination.utils.helpers.SingleEvent

class PhotosAdapter(private val eventListener: (PhotosViewEvent) -> Unit) :
    PagingDataAdapter<PhotosAdapterModel, BaseViewHolder>(DiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.viewType?.value ?:
        throw IllegalArgumentException("Items Not Found")
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when(holder) {
            is PhotoHeaderViewHolder -> holder.bind(getItem(position)!! as PhotosAdapterHeaderModel)
            is PhotoImageViewHolder -> holder.bind(getItem(position)!! as PhotosAdapterImageModel)
            is PhotoFooterViewHolder -> holder.bind(getItem(position)!! as PhotosAdapterFooterModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return PhotosViewHoldersFabric.create(parent, viewType)
    }


    class DiffUtilCallback : DiffUtil.ItemCallback<PhotosAdapterModel>() {
        override fun areItemsTheSame(
            oldItem: PhotosAdapterModel,
            newItem: PhotosAdapterModel
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: PhotosAdapterModel,
            newItem: PhotosAdapterModel
        ): Boolean {
            return true
        }
    }

}
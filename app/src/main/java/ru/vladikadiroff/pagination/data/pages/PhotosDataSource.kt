package ru.vladikadiroff.pagination.data.pages

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.vladikadiroff.pagination.data.api.UnsplashService
import ru.vladikadiroff.pagination.data.mappers.PhotoModelConverter
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel

class PhotosDataSource (
    private val service: UnsplashService,
    private val converter: PhotoModelConverter
) : PagingSource<Int, PhotosAdapterModel>() {

    private val startingPageIndex = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosAdapterModel> {
        val position = params.key ?: startingPageIndex
        return try {
            val photos = service.getPhoto(position).flatMap(converter::map2)
            val nextKey = if (photos.isEmpty()) null else position + 1
            LoadResult.Page(data = photos, prevKey = null, nextKey = nextKey)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotosAdapterModel>): Int? {
        return null
    }

}
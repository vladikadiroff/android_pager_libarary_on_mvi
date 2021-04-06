package ru.vladikadiroff.pagination.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.vladikadiroff.pagination.data.api.UnsplashService
import ru.vladikadiroff.pagination.data.mappers.PhotoInfoModelConverter
import ru.vladikadiroff.pagination.data.mappers.PhotoModelConverter
import ru.vladikadiroff.pagination.data.pages.PhotosDataSource
import ru.vladikadiroff.pagination.di.annotations.DispatcherIO
import ru.vladikadiroff.pagination.utils.PAGE_LOADING_SIZE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val service: UnsplashService,
    private val pagerConverter: PhotoModelConverter,
    private val photoInfoConverter: PhotoInfoModelConverter,
    @DispatcherIO private val defaultDispatcher: CoroutineDispatcher
) : Repository {

    override suspend fun getPhotoInfo(id: String) = withContext(defaultDispatcher) {
        photoInfoConverter.map(service.getPhotoInfo(id))
    }

    override fun getPhotos() = Pager(
        config = PagingConfig(
            pageSize = PAGE_LOADING_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PhotosDataSource(service, pagerConverter) }
    ).flow

}
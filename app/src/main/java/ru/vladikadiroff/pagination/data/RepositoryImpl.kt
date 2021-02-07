package ru.vladikadiroff.pagination.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import ru.vladikadiroff.pagination.data.api.UnsplashService
import ru.vladikadiroff.pagination.data.pages.PhotosDataSource
import ru.vladikadiroff.pagination.domain.mappers.PhotoModelConverter
import ru.vladikadiroff.pagination.utils.PAGE_LOADING_SIZE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val service: UnsplashService,
    private val pagerConverter: PhotoModelConverter
) : Repository {

    override suspend fun getPhotoInfo(id: String) = service.getPhotoInfo(id)

    override fun getPhotos() = Pager(
        config = PagingConfig(
            pageSize = PAGE_LOADING_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PhotosDataSource(service, pagerConverter) }
    ).flow

}
package ru.vladikadiroff.pagination.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.vladikadiroff.pagination.data.models.PhotoInfoRequestModel
import ru.vladikadiroff.pagination.data.models.PhotoRequestModel
import ru.vladikadiroff.pagination.utils.PAGE_LOADING_SIZE

interface UnsplashService {

    @GET("/photos")
    suspend fun getPhoto(
        @Query("page") page: Int,
        @Query("per_page") items: Int = PAGE_LOADING_SIZE
    ): List<PhotoRequestModel>

    @GET("/photos/{id}/")
    suspend fun getPhotoInfo(@Path("id") id: String): PhotoInfoRequestModel

}
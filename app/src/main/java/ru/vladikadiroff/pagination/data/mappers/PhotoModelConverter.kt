package ru.vladikadiroff.pagination.data.mappers

import ru.vladikadiroff.pagination.data.models.PhotoRequestModel
import ru.vladikadiroff.pagination.data.models.PhotoUserRequestModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterFooterModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterHeaderModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterImageModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel
import java.util.*
import javax.inject.Inject

class PhotoModelConverter @Inject constructor() {

    fun map2(api: PhotoRequestModel) : List<PhotosAdapterModel> {
        val header = PhotosAdapterHeaderModel(
            id = api.id,
            userName = api.user.name,
            userAccount = getUserAccount(api.user),
            userProfileImage = api.user.avatar.medium
        )
        val image = PhotosAdapterImageModel(
            id = api.id,
            image = api.photoUrls.regular,
            ratio = api.height.toFloat() / api.width.toFloat(),
        )
        val footer = PhotosAdapterFooterModel(
            id = api.id,
            likes = api.likes,
            photoDownload = api.downloadUrl.urlDownload ?: api.photoUrls.regular,
        )
        return listOf(header, image, footer)
    }

    private fun getUserAccount(api: PhotoUserRequestModel): String {
        var account = ""
        account = when {
            api.instagram?.isNotEmpty() == true -> api.instagram
            api.twitter.isNullOrEmpty() -> api.username
            else -> api.twitter
        }
        return "@${account.toLowerCase(Locale.ROOT)}"
    }

}
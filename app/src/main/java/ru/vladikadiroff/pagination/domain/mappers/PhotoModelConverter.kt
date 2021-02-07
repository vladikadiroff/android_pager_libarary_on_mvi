package ru.vladikadiroff.pagination.domain.mappers

import ru.vladikadiroff.pagination.data.models.PhotoRequestModel
import ru.vladikadiroff.pagination.data.models.PhotoUserRequestModel
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import java.util.*
import javax.inject.Inject

class PhotoModelConverter @Inject constructor() {
    fun map(api: PhotoRequestModel) = PhotoModel(
        id = api.id,
        ratio = api.height.toFloat() / api.width.toFloat(),
        likes = api.likes,
        photoThumbnail = api.photoUrls.regular,
        photoLarge = api.photoUrls.full,
        photoDownload = api.downloadUrl.urlDownload ?: api.photoUrls.regular,
        userName = api.user.name,
        userAccount = getUserAccount(api.user),
        userProfileImage = api.user.avatar.medium
    )

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
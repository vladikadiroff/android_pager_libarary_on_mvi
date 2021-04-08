package ru.vladikadiroff.pagination.data.mappers

import ru.vladikadiroff.pagination.data.models.PhotoRequestModel
import ru.vladikadiroff.pagination.data.models.PhotoUserRequestModel
import ru.vladikadiroff.pagination.presentation.models.PhotoFooterModel
import ru.vladikadiroff.pagination.presentation.models.PhotoHeaderModel
import ru.vladikadiroff.pagination.presentation.models.PhotoImageModel
import java.util.*
import javax.inject.Inject

class PhotoModelConverter @Inject constructor() {

    fun map(api: PhotoRequestModel) = listOf(
        PhotoHeaderModel(
            id = api.id,
            userName = api.user.name,
            userAccount = getUserAccount(api.user),
            userProfileImage = api.user.avatar.medium
        ),
        PhotoImageModel(
            id = api.id,
            image = api.photoUrls.regular,
            ratio = api.height.toFloat() / api.width.toFloat(),
        ),
        PhotoFooterModel(
            id = api.id,
            likes = api.likes,
            image = api.photoUrls.regular,
            imageDownload = api.downloadUrl.urlDownload ?: api.photoUrls.regular,
        )
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
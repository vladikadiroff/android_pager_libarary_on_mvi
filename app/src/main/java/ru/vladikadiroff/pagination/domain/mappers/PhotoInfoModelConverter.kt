package ru.vladikadiroff.pagination.domain.mappers

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.data.models.CameraInfoRequestModel
import ru.vladikadiroff.pagination.data.models.PhotoInfoRequestModel
import ru.vladikadiroff.pagination.domain.models.PhotoInfoModel
import ru.vladikadiroff.pagination.utils.extensions.fromApiToStringData
import ru.vladikadiroff.pagination.utils.extensions.withSeparatorBetweenThousands
import javax.inject.Inject

class PhotoInfoModelConverter @Inject constructor(@ApplicationContext context: Context) {

    private val resources = context.resources

    fun map(api: PhotoInfoRequestModel): PhotoInfoModel = PhotoInfoModel(
        published = getPublishedData(api),
        downloads = api.downloads.withSeparatorBetweenThousands(),
        dimensions = "${api.width} × ${api.height}",
        views = api.views.withSeparatorBetweenThousands(),
        camera = mapToCameraModel(api.cameraInfo)
    )

    private fun mapToCameraModel(api: CameraInfoRequestModel) =
        PhotoInfoModel.CameraInfoModel(
            camera = api.camera ?: getUknownContentString(),
            model = api.model ?: getUknownContentString(),
            aperture = api.aperture ?: getUknownContentString(),
            apertureAbbreviature = if (api.aperture != null) R.string.detail_apperture_f else null,
            shutter = if (api.shutter != null) "${api.shutter}s" else getUknownContentString(),
            focal = if (api.focal != null) "${api.focal}mm" else getUknownContentString(),
            iso = api.iso ?: getUknownContentString()
        )

    private fun getPublishedData(api: PhotoInfoRequestModel): String {
        if (api.published != null) return resources.getString(
            R.string.detail_published,
            api.published.fromApiToStringData()
        )
        if (api.updated != null) return resources.getString(
            R.string.detail_published,
            api.updated.fromApiToStringData()
        )
        if (api.created != null) return resources.getString(
            R.string.detail_published,
            api.created.fromApiToStringData()
        )
        return getUknownContentString()
    }

    private fun getUknownContentString() = resources.getString(R.string.detail_unknown_content)

}
package ru.vladikadiroff.pagination.domain.mappers

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.data.models.CameraInfoRequestModel
import ru.vladikadiroff.pagination.data.models.PhotoInfoRequestModel
import ru.vladikadiroff.pagination.domain.models.PhotoInfoModel
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Inject

class PhotoInfoModelConverter @Inject constructor(@ApplicationContext context: Context) {

    private val resources = context.resources
    private val unknownContent = resources.getString(R.string.detail_unknown_content)

    fun map(api: PhotoInfoRequestModel): PhotoInfoModel = PhotoInfoModel(
        published = getPublishedData(api),
        downloads = makeSeparatorBetweenThousands(api.downloads),
        dimensions = "${api.width} × ${api.height}",
        views = makeSeparatorBetweenThousands(api.views),
        camera = mapToCameraModel(api.cameraInfo)
    )

    private fun mapToCameraModel(api: CameraInfoRequestModel) =
        PhotoInfoModel.CameraInfoModel(
            camera = api.camera ?: unknownContent,
            model = api.model ?: unknownContent,
            aperture = api.aperture ?: unknownContent,
            apertureAbbreviature = if (api.aperture != null) R.string.detail_apperture_f else null,
            shutter = if (api.shutter != null) "${api.shutter}s" else unknownContent,
            focal = if (api.focal != null) "${api.focal}mm" else unknownContent,
            iso = api.iso ?: unknownContent
        )

    private fun getPublishedData(api: PhotoInfoRequestModel): String {
        if (api.published != null) return resources.getString(
            R.string.detail_published,
            makeDataFromApi(api.published)
        )
        if (api.updated != null) return resources.getString(
            R.string.detail_published,
            makeDataFromApi(api.updated)
        )
        if (api.created != null) return resources.getString(
            R.string.detail_published,
            makeDataFromApi(api.created)
        )
        return unknownContent
    }

    private fun makeSeparatorBetweenThousands(str: String): String {
        return try {
            val formatter = DecimalFormat()
            val symbols: DecimalFormatSymbols = DecimalFormatSymbols.getInstance()
            symbols.groupingSeparator = ' '
            formatter.decimalFormatSymbols = symbols
            formatter.format(str.toLong()).toString()
        } catch (e: Exception) {
            str
        }
    }

    private fun makeDataFromApi(str: String): String {
        return try {
            val data = str.substringBefore("T")
            val year = data.substringBefore("-")
            val monthNumber = data.substring(5).substringBefore("-")
            val day = data.substring(8)
            val month = DateFormatSymbols().months[monthNumber.toInt() - 1]
            "$month $day, $year"
        } catch (e: Exception) {
            str
        }
    }

}
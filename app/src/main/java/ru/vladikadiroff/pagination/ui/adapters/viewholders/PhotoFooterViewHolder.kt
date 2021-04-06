package ru.vladikadiroff.pagination.ui.adapters.viewholders

import android.view.ViewGroup
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.ItemFooterBinding
import ru.vladikadiroff.pagination.presentation.models.PhotosViewEvent
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterFooterModel
import ru.vladikadiroff.pagination.utils.abstracts.BaseViewHolder
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.FieldPosition
import kotlin.math.ln
import kotlin.math.pow

class PhotoFooterViewHolder(parent: ViewGroup): BaseViewHolder(R.layout.item_footer, parent) {

    private val binding = ItemFooterBinding.bind(itemView)

    fun bind(model: PhotosAdapterFooterModel) = with(binding){
        //shareButton.setOnClickListener { listener?.invoke(PhotosViewEvent.ItemShareClick(model)) }
        //infoButton.setOnClickListener { listener?.invoke(PhotosViewEvent.ItemInfoClick(model)) }
        likeButton.setOnCheckedChangeListener { _, checked ->
            model.likeUser = checked
            likesCount.text = countLikes(model)
            //if (checked) onLike.invoke(position)
        }
        likesCount.text = countLikes(model)
        likeButton.isChecked = model.likeUser
    }

    private fun countLikes(model: PhotosAdapterFooterModel): String {
        val likes = model.likes.toInt() + if (model.likeUser) 1 else 0
        return truncateNumber(likes)
    }

    private fun truncateNumber(value: Int): String {
        return try {
            val number = value.toDouble()
            val suffixChars = "KMGTPE"
            val formatter = DecimalFormat("###.#").also { it.roundingMode = RoundingMode.DOWN }
            if (number < 1000.0) formatter.format(number)
            else {
                val exp = (ln(number) / ln(1000.0)).toInt()
                formatter.format(number / 1000.0.pow(exp.toDouble())) + suffixChars[exp - 1]
            }
        } catch (e: Exception){
            value.toString()
        }
    }

}
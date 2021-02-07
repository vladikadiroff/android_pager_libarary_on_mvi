package ru.vladikadiroff.pagination.utils.helpers

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class ImageViewWithAspectRatio : AppCompatImageView {

    var aspectRatio = 0.5f

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val newHeightMeasureSpec =
            MeasureSpec.makeMeasureSpec((width * aspectRatio).toInt(), MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

}

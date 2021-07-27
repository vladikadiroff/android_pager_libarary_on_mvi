package ru.vladikadiroff.pagination.ui.adapters.decorators

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OffsetItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val metrics = context.resources.displayMetrics
    private val marginLeftRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, metrics).toInt()
    private val marginTopBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, metrics).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if(position == RecyclerView.NO_POSITION) return
        if (position == 0) marginTopBottom
        if (position % 3 == 0) outRect.bottom = marginTopBottom
        outRect.left = marginLeftRight
        outRect.right = marginLeftRight
    }
}
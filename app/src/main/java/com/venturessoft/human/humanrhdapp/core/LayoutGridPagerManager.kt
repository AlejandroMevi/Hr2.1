package com.venturessoft.human.humanrhdapp.core

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt
class LayoutGridPagerManager(context: Context, spanCount:Int,orientation: Int, reverseLayout: Boolean, private val itemsPerPage: Int) : GridLayoutManager(context,spanCount,orientation, reverseLayout) {
    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        return super.checkLayoutParams(lp) && (lp!!.width == getItemSize()|| lp.height == getItemSize())
    }
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return setProperItemSize(super.generateDefaultLayoutParams())
    }
    override fun generateLayoutParams(lp: ViewGroup.LayoutParams): RecyclerView.LayoutParams {
        return setProperItemSize(super.generateLayoutParams(lp))
    }
    private fun setProperItemSize(lp: RecyclerView.LayoutParams): RecyclerView.LayoutParams {
        val itemSize = getItemSize()
        if (orientation == HORIZONTAL) {
            lp.width = itemSize
        } else {
            lp.height = itemSize
        }
        return lp
    }
    private fun getItemSize(): Int {
        val pageSize = if (orientation == HORIZONTAL) width else height
        return (pageSize.toFloat() / itemsPerPage).roundToInt()
    }
}
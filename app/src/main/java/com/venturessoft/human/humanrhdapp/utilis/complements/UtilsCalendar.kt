package com.venturessoft.human.humanrhdapp.utilis.complements

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun View.makeVisible() {
    visibility = View.VISIBLE
}
internal fun Context.getDrawableCompat(@DrawableRes drawable: Int): Drawable = requireNotNull(ContextCompat.getDrawable(this, drawable))
internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)
internal fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))


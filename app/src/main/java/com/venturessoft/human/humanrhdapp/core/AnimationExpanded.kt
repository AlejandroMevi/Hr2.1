package com.venturessoft.human.humanrhdapp.core

import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
class AnimationExpanded {
    private val duration:Long = 250
    fun toggleArrow(view: View, isExpanded: Boolean): Boolean {
        return if (isExpanded) {
            view.animate().setDuration(duration).rotation(180f).interpolator = AccelerateDecelerateInterpolator()
            true
        } else {
            view.animate().setDuration(duration).rotation(0f).interpolator = AccelerateDecelerateInterpolator()
            false
        }
    }
    fun expand(view: View) {
        val animation = expandAction(view)
        view.startAnimation(animation)
    }
    private fun expandAction(view: View): Animation {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val actualheight = view.measuredHeight
        view.layoutParams.height = 0
        view.visibility = View.VISIBLE
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                view.layoutParams.height = if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (actualheight * interpolatedTime).toInt()
                view.requestLayout()
            }
        }
        animation.duration = duration
        animation.interpolator = AccelerateDecelerateInterpolator()
        view.startAnimation(animation)
        return animation
    }
    fun collapse(view: View) {
        val actualHeight = view.measuredHeight
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    view.visibility = View.GONE
                }else{
                    view.layoutParams.height = actualHeight - (actualHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }
        }
        animation.duration = duration
        animation .interpolator = AccelerateDecelerateInterpolator()
        view.startAnimation(animation)
    }
}
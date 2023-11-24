package com.venturessoft.human.humanrhdapp.ui.home

interface MainInterface {
    fun setTextToolbar(text:String)
    fun showLottie(isVisible: Boolean)
    fun showLoading(isShowing: Boolean = false)
    fun showFilter(isShowing: Boolean = false,filterType:Int)
    fun showBadgeDrawable(isShowing: Boolean = false)
    fun showStations(gafete:Long)
}
package com.venturessoft.human.humanrhdapp.utilis.calendar

import androidx.annotation.ColorRes
import java.time.LocalDateTime
data class CustomDay(
    val time: LocalDateTime,
    @ColorRes val color: Int,
    val marca : String? = null
)

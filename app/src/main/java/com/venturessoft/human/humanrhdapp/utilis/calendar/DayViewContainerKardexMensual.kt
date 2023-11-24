package com.venturessoft.human.humanrhdapp.utilis.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.view.ViewContainer
import com.venturessoft.human.humanrhdapp.databinding.CalendarDayLayoutBinding
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities


@SuppressLint("ClickableViewAccessibility")
class DayViewContainerKardexMensual(view: View, context: Context) : ViewContainer(view)  {
    private var caC: CalendarioCustom = CalendarioCustom()
    lateinit var day: CalendarDay
    var textView = CalendarDayLayoutBinding.bind(view).calendarDayText
    var texto = CalendarDayLayoutBinding.bind(view).calendarKardexMensual
    val binding = CalendarDayLayoutBinding.bind(view)
    init {
        /*
        view.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!view.isClickable) {
                        val text = caC.validaTxt(textView.text as String, context, 1)
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            v?.onTouchEvent(event) ?: true
        }*/
        view.setOnLongClickListener {
            val text = caC.validaTxt(textView.text as String, context, 1)
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            Utilities.vibrate(context)
            true }
    }
}
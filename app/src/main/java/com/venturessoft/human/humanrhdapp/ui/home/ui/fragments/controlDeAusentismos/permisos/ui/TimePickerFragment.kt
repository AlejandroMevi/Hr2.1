package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment(val listener: (String) -> Unit) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val tz = TimeZone.getTimeZone("America/Mexico_City")
        val calendar: Calendar = Calendar.getInstance(tz)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(activity as Context, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfday: Int, minute: Int) {
        if (hourOfday < 10 && minute < 10) {
            listener("0$hourOfday:0$minute")
        } else if (hourOfday < 10) {
            listener("0$hourOfday:$minute")
        } else if (minute < 10) {
            listener("$hourOfday:0$minute")
        } else {
            listener("$hourOfday:$minute")
        }
    }
}
package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.decapitalize
import com.google.android.material.button.MaterialButton
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.COUNTRYES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.LENGUAGEes
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.whiteelephant.monthpicker.MonthPickerDialog
import java.util.Calendar
import java.util.Locale
class DateIndicator (
    private val context: Context,
    private val button:MaterialButton,
    private val textBefore:Button?=null,
    private val textAfter:Button?=null
) {
    companion object{
        val calendar: Calendar = Calendar.getInstance(Locale(LENGUAGEes, COUNTRYES))
        var monthData = calendar.get(Calendar.MONTH)
        var yearData = calendar.get(Calendar.YEAR)
    }
    fun createDialogWithoutDateField(function: (() -> Unit?)? = null) {
        val builder = MonthPickerDialog.Builder(context, { _, _ -> }, Calendar.YEAR, Calendar.MONTH)
        builder
            .setActivatedMonth(monthData)
            .setMinYear(calendar.get(Calendar.YEAR)-5)
            .setActivatedYear(yearData)
            .setMaxYear(calendar.get(Calendar.YEAR)+5)
            .setMinMonth(Calendar.JANUARY)
            .setTitle("Seleccione una Fecha")
            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
            .setOnMonthChangedListener {month->
                monthData = month
                setDate(month)
                function?.invoke()
            }.setOnYearChangedListener {year->
                yearData = year
                setDate(monthData)
                function?.invoke()
            }.build().show()
    }
    @SuppressLint("SetTextI18n")
    fun setDate(monthSet:Int){
        monthData = monthSet
        if (monthSet > 11){
            yearData += 1
            monthData = 0
        }
        if (monthSet < 0){
            yearData -= 1
            monthData = 11
        }
        button.text = "${getMonthName(monthData)} / $yearData"
        textBefore?.text = getMonthName(monthData-1)
        textAfter?.text = getMonthName(monthData+1)
    }
    private fun getMonthName(index: Int): String {
        val calendar = Calendar.getInstance(Locale(LENGUAGEes, COUNTRYES))
        calendar[Calendar.MONTH] = index
        calendar[Calendar.DAY_OF_MONTH] = 1
        return java.lang.String.format(Locale(LENGUAGEes, COUNTRYES), "%tb", calendar).replaceFirstChar { it.uppercase(Locale(LENGUAGEes, COUNTRYES)) }
    }
}
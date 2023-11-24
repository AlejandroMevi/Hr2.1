package com.venturessoft.human.humanrhdapp.core

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui.Permisos.Companion.horaFinal
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui.Permisos.Companion.horaInicial
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui.vm.EstacionesLibresViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.COUNTRYES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.LENGUAGEes
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.getDateFromMiliseconds
import com.whiteelephant.monthpicker.MonthPickerDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
class Pickers {
    private var ca: Calendario = Calendario()
    val calendar: Calendar = Calendar.getInstance(Locale(LENGUAGEes, COUNTRYES))
    private fun String.formatoDiag(): String {
        return this.replace("/", "-")
    }
    companion object {
        var fechaInicio: String = ""
        var fechaInicioValidate: String = ""
        var fechaInicioService: String = ""
        var fechaFin: String = ""
        var fechaFinValidate: String = ""
        var fechaFinService: String = ""
        val keySelect = ArrayList<Int>()
    }
    fun selecDefaultDateMonday(dateFrom: EditText) {
        val lunes = ca.selecLunesDefault(ca.getDiaSemana())
        dateFrom.setText(lunes)
        fechaInicioValidate = lunes
        fechaInicio = lunes
        fechaInicioService = fechaInicioValidate.formatoDiag()
    }
    fun selecDefaultDateSunday(dateUp: EditText) {
        val domingo = ca.selecDefaultDateSunday(ca.getDiaSemana())
        dateUp.setText(domingo)
        fechaFinValidate = domingo
        fechaFin = domingo
        fechaFinService = fechaFinValidate.formatoDiag()
    }
    fun dataPickerFrom(fragment: Fragment, dateFrom: EditText?=null, id: Int, format: String, vm: EstacionesLibresViewModel? = null) {
        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .setTheme(R.style.DialogCalendarRHD)
            .setTitleText(fragment.getString(R.string.selecciona_fecha))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        datePicker.show(fragment.childFragmentManager, "picker")
        datePicker.addOnPositiveButtonClickListener {
            val date = getDateFromMiliseconds(it, format)
            val dateView = getDateFromMiliseconds(it, "dd/MM/yyyy")
            dateFrom?.setText(dateView)
            if (id == 1) {
                fechaInicioService = date
                fechaInicioValidate = dateView
                fechaInicio = date
            } else {
                fechaFinValidate = dateView
                fechaFinService = date
                fechaFin = date
            }
            if (vm != null) {
                vm.validateFilters.value = true
            }
        }
    }
    fun dataEditTextFrom(fechaString: String, inputformat: String, outformat: String,id:Int) {
        val fecha: LocalDate?
        val formatoEntrada = DateTimeFormatter.ofPattern(inputformat)
        fecha = LocalDate.parse(fechaString, formatoEntrada)
        val formatoSalida: DateTimeFormatter? = DateTimeFormatter.ofPattern(outformat)
        if (id == 1) {
            fechaInicioService = fecha.format(formatoSalida)
            fechaInicio = fecha.format(formatoSalida)
        } else {
            fechaFinService = fecha.format(formatoSalida)
            fechaFin = fecha.format(formatoSalida)
        }
    }

    fun dialogYear(context: Context, ilAnio: MaterialButton) {
        val builder = MonthPickerDialog.Builder(context, { _, _ -> }, Calendar.YEAR, Calendar.MONTH)
        builder
            .showYearOnly()
            .setYearRange(1900, 2050)
            .setActivatedYear(calendar.get(Calendar.YEAR))
            .setTitle(context.getString(R.string.seleccion_anio))
            .setOnYearChangedListener { year ->
                calendar.set(Calendar.YEAR, year)
                setDate(ilAnio, year)
            }
            .build()
            .show()
    }
    private fun setDate(ilAnio: MaterialButton, year: Int) {
        ilAnio.text = "$year"
    }
    fun timePickerText(requireActivity: FragmentActivity, txt: EditText, id: Int) {
        val tz = TimeZone.getTimeZone("America/Mexico_City")
        val calendar: Calendar = Calendar.getInstance(tz)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val picker = MaterialTimePicker.Builder()
            .setTheme(R.style.TimePicker)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setHour(hour)
            .setMinute(minute)
            .setTitleText(requireActivity.getString(R.string.selecciona_hora))
            .build()
        picker.addOnPositiveButtonClickListener {
            var hora = ""
            val newHour = picker.hour
            val newMinute = picker.minute
            if (newHour < 10) {
                hora = "0$newHour:$newMinute"
            }
            if (newMinute < 10) {
                hora = "$newHour:0$newMinute"
            }
            if (newMinute < 10 && newHour < 10) {
                hora = "0$newHour:0$newMinute"
            }
            if (newMinute >= 10 && newHour >= 10) {
                hora = "$newHour:$newMinute"
            }
            onTimeSelectedUp(txt, hora, id)
        }
        picker.show(requireActivity.supportFragmentManager, "")
    }
    private fun onTimeSelectedUp(txt: EditText, time: String, id: Int) {
        txt.setText(time)
        if (id == 1) horaInicial = time else horaFinal = time
    }
    fun seleccionMultiple(value: ArrayList<String>, requireContext: Context, key: ArrayList<String>, txtView: Button, message: String, title: String) {
        if (value.isEmpty()) {
            Toast.makeText(requireContext, message, Toast.LENGTH_SHORT).show()
        } else {
            val checkedItems = BooleanArray(value.size)
            val selectedItems = mutableListOf(*value.toTypedArray())
            val position = ArrayList<Int>()
            val builder = MaterialAlertDialogBuilder(requireContext, R.style.AlertDialogCustom)
            builder.setTitle(title)
            builder.setMultiChoiceItems(value.toTypedArray(), checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
                selectedItems[which]
                if (checkedItems[which]) {
                    position.add(which)
                } else {
                    position.remove(which)
                }
            }
            builder.setCancelable(false)
            builder.setPositiveButton(requireContext.getString(R.string.list_company_positive)) { _, _ ->
                for (i in position.indices) {
                    keySelect.add(key[position[i]].toInt())
                }
                /*for (i in checkedItems.indices) {
                    if (checkedItems[i]) {
                        println(selectedItems[i])
                    }
                }*/
                pintarVista(txtView, position)
            }
            builder.setNegativeButton("CANCEL") { _, _ -> }
            builder.create()
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun pintarVista(txtView: Button, position: ArrayList<Int>) {
        if (position.size == 1) {
            txtView.text = "${position.size} Concepto seleccionado"
        } else {
            txtView.text = "${position.size} Conceptos seleccionados"
        }
    }
}
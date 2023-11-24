package com.venturessoft.human.humanrhdapp.core

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.COUNTRYES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.LENGUAGEes
import java.text.SimpleDateFormat
import java.util.*

class Calendario {
    private val c = Calendar.getInstance()
    private val m = c.get(Calendar.MONTH)
    private var dia = c.get(Calendar.DAY_OF_MONTH)
    private var mes = m + 1
    private var anio = c.get(Calendar.YEAR)
    private var diaSemana = c.get(Calendar.DAY_OF_WEEK)
    private var hora = c.get(Calendar.HOUR_OF_DAY)
    internal fun getDia(): Int {
        return this.dia
    }
    internal fun getDiaSemana(): Int {
        return this.diaSemana
    }
    internal fun getMes(): Int {
        return this.mes
    }
    internal fun getAnio(): Int {
        return this.anio
    }
    internal fun getHora(): Int {
        return this.hora
    }
    internal fun setDia(d: Int) {
        this.dia = d
    }
    internal fun setMes(m: Int) {
        this.mes = m
    }
    internal fun setAnio(a: Int) {
        this.anio = a
    }
    fun kardexTitleCustom(context: Context, mes : Int): String {
        var txtMes = ""
        when (mes) {
            1 -> txtMes = context.getString(R.string.month1)
            2 -> txtMes = context.getString(R.string.month2)
            3 -> txtMes = context.getString(R.string.month3)
            4 -> txtMes = context.getString(R.string.month4)
            5 -> txtMes = context.getString(R.string.month5)
            6 -> txtMes = context.getString(R.string.month6)
            7 -> txtMes = context.getString(R.string.month7)
            8 -> txtMes = context.getString(R.string.month8)
            9 -> txtMes = context.getString(R.string.month9)
            10 -> txtMes = context.getString(R.string.month10)
            11 -> txtMes = context.getString(R.string.month11)
            12 -> txtMes = context.getString(R.string.month12)
        }
        return txtMes
    }
    fun barCMonth(context: Context, mes : Int): String {
        var txtMes = ""
        when (mes) {
            1 -> txtMes = context.getString(R.string.ene)
            2 -> txtMes = context.getString(R.string.feb)
            3 -> txtMes = context.getString(R.string.mar)
            4 -> txtMes = context.getString(R.string.abr)
            5 -> txtMes = context.getString(R.string.may)
            6 -> txtMes = context.getString(R.string.jun)
            7 -> txtMes = context.getString(R.string.jul)
            8 -> txtMes = context.getString(R.string.ago)
            9 -> txtMes = context.getString(R.string.sep)
            10 -> txtMes = context.getString(R.string.oct)
            11 -> txtMes = context.getString(R.string.nov)
            12 -> txtMes = context.getString(R.string.dic)
        }
        return txtMes
    }
    fun selecLunesDefault(diaSemana: Int): String {
        val c = Calendar.getInstance()
        val m = c.get(Calendar.MONTH)
        var dia = c.get(Calendar.DAY_OF_MONTH)
        val mes = m + 1
        val anio = c.get(Calendar.YEAR)
        var fecha = ""
        when (diaSemana) {
            1 -> {
                dia -= 6
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            2 -> {
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            3 -> {
                dia -= 1
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            4 -> {
                dia -= 2
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            5 -> {
                dia -= 3
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            6 -> {
                dia -= 4
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            7 -> {
                dia -= 5
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            else -> {}
        }
        return fecha
    }
    fun selecDefaultDateSunday(diaSemana: Int): String {
        val c = Calendar.getInstance()
        val m = c.get(Calendar.MONTH)
        var dia = c.get(Calendar.DAY_OF_MONTH)
        val mes = m + 1
        val anio = c.get(Calendar.YEAR)
        var fecha = ""
        when (diaSemana) {
            1 -> {
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            2 -> {
                dia += 6
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            3 -> {
                dia += 5
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            4 -> {
                dia += 4
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            5 -> {
                dia += 3
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            6 -> {
                dia += 2
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
            7 -> {
                dia += 1
                fecha = mostrarResultadoFrom(anio, mes, dia)
            }
        }
        return fecha
    }
    private fun mostrarResultadoFrom(year: Int, month: Int, dayOfMonth: Int): String {
        var fechaCompletaFrom = "$dayOfMonth/$month/$year"
        val validaUltimoDia = validaUltimoDiaSemana(dayOfMonth, month, year)
        val validaPrimerDia = validaPrimerDiaSemana(dayOfMonth, month, year)
        if (validaUltimoDia.isNotEmpty()) return validaUltimoDia
        else if (validaPrimerDia.isNotEmpty()) return validaPrimerDia
        if (month < 10) {
            fechaCompletaFrom = "$dayOfMonth/0$month/$year"
        }
        if (dayOfMonth < 10) {
            fechaCompletaFrom = "0$dayOfMonth/$month/$year"
        }
        if (dayOfMonth < 10 && month < 10) {
            fechaCompletaFrom = "0$dayOfMonth/0$month/$year"
        }
        return fechaCompletaFrom
    }
    @SuppressLint("SetTextI18n")
    private fun validaPrimerDiaSemana(dayOfMonth: Int, month: Int, year: Int): String {
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, -1)
        c.set(Calendar.MONTH - 1, c.getMaximum(Calendar.DAY_OF_MONTH))
        val firstDayOfTheMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        var fechaCompletaUp = ""
        val newYear: Int
        if (dayOfMonth <= 0) {
            val newDay = firstDayOfTheMonth + dayOfMonth
            val newMonth = month - 1
            newYear = if (month == 1) {
                year - 1
            } else {
                year
            }
            fechaCompletaUp = ("$newDay/$newMonth/$newYear")
            if (newMonth < 10) {
                fechaCompletaUp = "$newDay/0$newMonth/$newYear"
            }
            if (newDay < 10) {
                fechaCompletaUp = "0$newDay/$newMonth/$newYear"
            }
            if (newDay < 10 && newMonth < 10) {
                fechaCompletaUp = "0$newDay/0$newMonth/$newYear"
            }
        }
        return fechaCompletaUp
    }
    @SuppressLint("SetTextI18n")
    private fun validaUltimoDiaSemana(dayOfMonth: Int, month: Int, year: Int): String {
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, -1)
        c.set(Calendar.MONTH, c.getMaximum(Calendar.DAY_OF_MONTH))
        val lastDayOfTheMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        var fechaCompletaUp = ""
        val newYear: Int
        if (lastDayOfTheMonth < dayOfMonth) {
            val newDay = dayOfMonth - lastDayOfTheMonth
            val newMonth = month + 1
            newYear = if (month == 12) {
                year + 1
            } else {
                year
            }
            fechaCompletaUp = ("$newDay/$newMonth/$newYear")
            if (newMonth < 10) {
                fechaCompletaUp = "$newDay/0$newMonth/$newYear"
            }
            if (newDay < 10) {
                fechaCompletaUp = "0$newDay/$newMonth/$newYear"
            }
            if (newDay < 10 && newMonth < 10) {
                fechaCompletaUp = "0$newDay/0$newMonth/$newYear"
            }

        }
        return fechaCompletaUp
    }
    fun fechaActual(year: Int, month: Int, dayOfMonth: Int): String {
        var fechaCompletaFrom = ("$dayOfMonth/$month/$year")
        if (month < 10) {
            fechaCompletaFrom = "$dayOfMonth/0$month/$year"
        }
        if (dayOfMonth < 10) {
            fechaCompletaFrom = "0$dayOfMonth/$month/$year"
        }
        if (dayOfMonth < 10 && month < 10) {
            fechaCompletaFrom = "0$dayOfMonth/0$month/$year"
        }
        return fechaCompletaFrom
    }
    fun validaFechaMenor(fechaInicio: String, fechaFin: String): String {
        val dateUp: Date =
            SimpleDateFormat("dd/MM/yyyy", Locale(LENGUAGEes, COUNTRYES)).parse(fechaFin) as Date
        val dateFromConvertDate: Date =
            SimpleDateFormat("dd/MM/yyyy",Locale(LENGUAGEes, COUNTRYES)).parse(fechaInicio) as Date
        return if (dateUp.compareTo(dateFromConvertDate).toString() == "-1") {
            "menor"
        } else {
            "correcto"
        }
    }
    fun validaFechaMenorAusentismos(fechaInicio: String, fechaFin: String, fragment: Fragment): String {
        val resp: String ?
        if (fechaFin == fragment.getString(R.string.fvSeleccionarFecha)) {
            val dateUp: Date =
                SimpleDateFormat("dd/MM/yyyy",Locale(LENGUAGEes, COUNTRYES)).parse(fechaInicio) as Date
            val dateFromConvertDate: Date =
                SimpleDateFormat("dd/MM/yyyy",Locale(LENGUAGEes, COUNTRYES)).parse(fechaInicio) as Date
            resp =  if (dateUp.compareTo(dateFromConvertDate).toString() == "-1") {
                "menor"
            } else {
                "correcto"
            }
        }else{
            val dateUp: Date =
                SimpleDateFormat("dd/MM/yyyy",Locale(LENGUAGEes, COUNTRYES)).parse(fechaFin) as Date
            val dateFromConvertDate: Date =
                SimpleDateFormat("dd/MM/yyyy",Locale(LENGUAGEes, COUNTRYES)).parse(fechaInicio) as Date
            resp =  if (dateUp.compareTo(dateFromConvertDate).toString() == "-1") {
                "menor"
            } else {
                "correcto"
            }
        }
        return resp
    }
}
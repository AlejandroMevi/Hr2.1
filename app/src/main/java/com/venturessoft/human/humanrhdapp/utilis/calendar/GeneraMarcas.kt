package com.venturessoft.human.humanrhdapp.utilis.calendar

import com.venturessoft.human.humanrhdapp.R
import java.time.YearMonth

class GeneraMarcas {
    fun listaKardex(dias: ArrayList<Int>): ArrayList<Int> = dias
    fun listaKardexMeses(meses: ArrayList<Int>): ArrayList<Int> = meses
    fun listaKardexMarcas(marcas: ArrayList<String>): ArrayList<String> = marcas
    fun generateVacaciones(dias: List<Int>, meses: List<Int>): List<CustomDay> = buildList {
        val currentMonth = YearMonth.now()
        dias as ArrayList<Int>
        meses as ArrayList<Int>
        for (i in meses.indices) {
            currentMonth.withMonth(meses[i]).atDay(dias[i]).also { date ->
                add(CustomDay(date.atTime(7, 30), R.color.principal))
            }
        }
    }
    fun generateDiaAusDesc(dias: List<Int>, meses: List<Int>): List<CustomDay> = buildList {
        val currentMonth = YearMonth.now()
        dias as ArrayList<Int>
        meses as ArrayList<Int>
        for (i in meses.indices) {
            currentMonth.withMonth(meses[i]).atDay(dias[i]).also { date ->
                add(CustomDay(date.atTime(7, 30), R.color.principal))
            }
        }
    }
    fun generateDiaPermHr(dias: List<Int>, meses: List<Int>): List<CustomDay> = buildList {
        val currentMonth = YearMonth.now()
        dias as ArrayList<Int>
        meses as ArrayList<Int>
        for (i in meses.indices) {
            currentMonth.withMonth(meses[i]).atDay(dias[i]).also { date ->
                add(CustomDay(date.atTime(7, 30), R.color.principal))
            }
        }
    }
    fun generateFaltas(dias: List<Int>, meses: List<Int>): List<CustomDay> = buildList {
        val currentMonth = YearMonth.now()
        dias as ArrayList<Int>
        meses as ArrayList<Int>
        for (i in meses.indices) {
            currentMonth.withMonth(meses[i]).atDay(dias[i]).also { date ->
                add(CustomDay(date.atTime(7, 30), R.color.principal))
            }
        }
    }
    fun generateDescansos(dias: List<Int>, meses: List<Int>): List<CustomDay> = buildList {
        val currentMonth = YearMonth.now()
        dias as ArrayList<Int>
        meses as ArrayList<Int>
        for (i in meses.indices) {
            currentMonth.withMonth(meses[i]).atDay(dias[i]).also { date ->
                add(CustomDay(date.atTime(7, 30), R.color.letraD))
            }
        }
    }
    fun generateFestivos(dias: List<Int>, meses: List<Int>): List<CustomDay> = buildList {
        val currentMonth = YearMonth.now()
        dias as ArrayList<Int>
        meses as ArrayList<Int>
        for (i in meses.indices) {
            currentMonth.withMonth(meses[i]).atDay(dias[i]).also { date ->
                add(CustomDay(date.atTime(7, 30), R.color.letrasF))
            }
        }
    }
    fun generateAusentismos(dias: List<Int>, meses: List<Int>): List<CustomDay> = buildList {
        val currentMonth = YearMonth.now()
        dias as ArrayList<Int>
        meses as ArrayList<Int>
        for (i in meses.indices) {
            currentMonth.withMonth(meses[i]).atDay(dias[i]).also { date ->
                add(CustomDay(date.atTime(7, 30), R.color.letrasA))
            }
        }
    }
    fun generateRetardos(dias: List<Int>, meses: List<Int>): List<CustomDay> = buildList {
        val currentMonth = YearMonth.now()
        dias as ArrayList<Int>
        meses as ArrayList<Int>
        for (i in meses.indices) {
            currentMonth.withMonth(meses[i]).atDay(dias[i]).also { date ->
                add(CustomDay(date.atTime(7, 30), R.color.letraR))
            }
        }
    }

    fun generateOthers(dias: List<Int>, meses: List<Int>, marcas: List<String>): List<CustomDay> = buildList {
        val currentMonth = YearMonth.now()
        dias as ArrayList<Int>
        meses as ArrayList<Int>
        for (i in meses.indices) {
            currentMonth.withMonth(meses[i]).atDay(dias[i]).also { date ->
                add(CustomDay(date.atTime(7, 30), R.color.letraOthers, marcas[i]))
            }
        }
    }
}
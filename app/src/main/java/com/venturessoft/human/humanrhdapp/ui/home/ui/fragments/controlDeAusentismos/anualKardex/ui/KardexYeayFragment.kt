package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.FragmentKardexYearBinding
import com.venturessoft.human.humanrhdapp.databinding.ItemKardexBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.MarcaItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansoItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
class KardexYeayFragment(
    private val kardexAnualData: List<MarcaItem>,
    private val diasFestivosItems: List<DiasFestivosItem>,
    private val diasDescansoItems: List<DiasDescansoItem>,
    private val yearSelected: Int
) : Fragment() {
    private lateinit var binding: FragmentKardexYearBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKardexYearBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView(){
        binding.tvYear.text = yearSelected.toString()
        kardexAnualData.forEach {
            getDataList(it.fecha,it.marca)
        }
        diasFestivosItems.forEach {
            getDataList(it.fecha,it.marca)

        }
        diasDescansoItems.forEach {
            getDataList(it.fecha,it.marca)
        }
    }

    private fun getDataList(fecha: String, marca: String) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(fecha, formatter)
        setDataInTable(date.monthValue,date.dayOfMonth,marca)
    }

    private fun setDataInTable(month: Int,day:Int,value:String){
        val monthItem = when (month) {
            1 -> binding.tableKardex.month1
            2 -> binding.tableKardex.month2
            3 -> binding.tableKardex.month3
            4 -> binding.tableKardex.month4
            5 -> binding.tableKardex.month5
            6 -> binding.tableKardex.month6
            7 -> binding.tableKardex.month7
            8 -> binding.tableKardex.month8
            9 -> binding.tableKardex.month9
            10-> binding.tableKardex.month10
            11 -> binding.tableKardex.month11
            else -> binding.tableKardex.month12
        }
        val dayItem = when (day) {
            1 -> monthItem.day1
            2 -> monthItem.day2
            3 -> monthItem.day3
            4 -> monthItem.day4
            5 -> monthItem.day5
            6 -> monthItem.day6
            7 -> monthItem.day7
            8 -> monthItem.day8
            9 -> monthItem.day9
            10 -> monthItem.day10
            11 -> monthItem.day11
            12 -> monthItem.day12
            13 -> monthItem.day13
            14 -> monthItem.day14
            15 -> monthItem.day15
            16 -> monthItem.day16
            17 -> monthItem.day17
            18 -> monthItem.day18
            19 -> monthItem.day19
            20 -> monthItem.day20
            21 -> monthItem.day21
            22 -> monthItem.day22
            23 -> monthItem.day23
            24 -> monthItem.day24
            25 -> monthItem.day25
            26 -> monthItem.day26
            27 -> monthItem.day27
            28 -> monthItem.day28
            29 -> monthItem.day29
            30 -> monthItem.day30
            else -> monthItem.day31
        }
        when(value){
            "F" -> {
                validateText(dayItem,value)
                dayItem.tvValue.setBackgroundColor(requireContext().getColor(R.color.letrasF))
            }
            "D" -> {
                validateText(dayItem, value)
                dayItem.tvValue.setBackgroundColor(requireContext().getColor(R.color.letraD))
            }
            "A" -> {
                validateText(dayItem, value)
                dayItem.tvValue.setBackgroundColor(requireContext().getColor(R.color.letrasA))
            }
            else ->  {
                dayItem.tvValue.text = value
                dayItem.tvValueGost.text = value
            }
        }
        dayItem.clItemDay.setOnClickListener { view ->
            val constraint = view as ConstraintLayout
            val item = (constraint.getChildAt(1) as MaterialTextView).text
            Toast.makeText(requireContext(),item,Toast.LENGTH_SHORT).show()
        }
    }
    private fun validateText(text: ItemKardexBinding, value: String) {
        if (text.tvValue.text.isNotEmpty()){
            text.tvValue.setTextColor(requireContext().getColor(R.color.white))
        }
        text.tvValueGost.text = value
    }
}
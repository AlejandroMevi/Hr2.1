package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.Calendario
import com.venturessoft.human.humanrhdapp.core.HeaderUser
import com.venturessoft.human.humanrhdapp.databinding.FragmentKardexMenualBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.MonthViewContainer
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.ui.vm.KardexMensualViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.ui.vm.KardexAnualViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.utilis.calendar.CalendarioCustom
import com.venturessoft.human.humanrhdapp.utilis.calendar.CustomDay
import com.venturessoft.human.humanrhdapp.utilis.calendar.DayViewContainerKardexMensual
import com.venturessoft.human.humanrhdapp.utilis.calendar.GeneraMarcas
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.getColorCompat
import com.venturessoft.human.humanrhdapp.utilis.complements.setTextColorRes
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

class KardexMensualFragment : Fragment() {
    private lateinit var binding: FragmentKardexMenualBinding
    private val welcomeFragmentViewModel: WelcomeFragmentViewModel by activityViewModels()
    private val kardexMensualViewModel = KardexMensualViewModel()
    private val kardexAnualViewModel = KardexAnualViewModel()
    private var caC: CalendarioCustom = CalendarioCustom()
    private var g: GeneraMarcas = GeneraMarcas()
    private var ca: Calendario = Calendario()
    private var names: String? = null
    private var numEmp: String? = null
    private val currentMonth = YearMonth.now()!!
    private val today = LocalDate.now()
    private var selectedDate: LocalDate? = null
    private var kardexMensualAusentismos: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualRetardos: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualVacaciones: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualAusDesc: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualPermHrs: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualFaltas: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualOthers: Map<LocalDate, List<CustomDay>>? = null
    private var descansos: Map<LocalDate, List<CustomDay>>? = null
    private var festivos: Map<LocalDate, List<CustomDay>>? = null
    private lateinit var diaVaciones: ArrayList<Int>
    private lateinit var mesVacaciones: ArrayList<Int>
    private lateinit var diaAusDesc: ArrayList<Int>
    private lateinit var mesAusDesc: ArrayList<Int>
    private lateinit var diaPermHr: ArrayList<Int>
    private lateinit var mesPermHr: ArrayList<Int>
    private var mainInterface: MainInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKardexMenualBinding.inflate(inflater, container, false)
        welcomeFragmentViewModel.idMenu.observe(viewLifecycleOwner) {dataUser->
            HeaderUser(requireContext(),dataUser,binding.headerUser)
            names = dataUser.nombreCompleto
            numEmp = dataUser.numEmp
            loadService()
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainInterface) {
            mainInterface = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mainInterface = null
    }

    private fun loadService() {
        corutineGetKardexAnual(ca.getAnio().toString())
    }

    private fun corutineGetKardexAnual(anio: String) {
        kardexAnualViewModel.data.value = null
        val kardexAnualRequest =
            KardexAnualRequest(User.numCia.toString(), numEmp!!, anio, motivo = "Todos")
        kardexAnualViewModel.kardexAnual(User.token, kardexAnualRequest)
        kardexAnualViewModel.data.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val fecha = ArrayList<String>()
                    val marcas = ArrayList<String>()
                    for (i in 0 until response.skardexAnual.marca.size) {
                        response.skardexAnual.marca[i].fecha.let { fecha.add(it) }
                        response.skardexAnual.marca[i].marca.let { marcas.add(it) }
                    }
                    separateDayKardexMensual(fecha, marcas)
                    getDiasFestivosService(anio)
                }
            }
        }
        statusObserve()
    }

    private fun statusObserve() {
        kardexAnualViewModel.status.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }

                    is ApiResponceStatus.Success -> {
                        clearService()
                    }

                    is ApiResponceStatus.Error -> {
                        getDiasFestivosService(ca.getAnio().toString())
                        clearService()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        println("ApiResponceStatus.Error $errorCode")
                    }
                }
            }
        }
    }

    private fun clearService() {
        kardexAnualViewModel.status.value = null
        kardexAnualViewModel.data.removeObservers(viewLifecycleOwner)
        kardexAnualViewModel.status.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }

    private fun separateDayKardexMensual(fechas: ArrayList<String>, marcas: ArrayList<String>) {
        diaVaciones = caC.aDiaVacaciones(fechas, marcas)
        mesVacaciones = caC.aMesesVacaciones(fechas, marcas)
        diaAusDesc = caC.aDiaAusDesc(fechas, marcas)
        mesAusDesc = caC.aMesesAusDesc(fechas, marcas)
        diaPermHr = caC.aDiaPermHr(fechas, marcas)
        mesPermHr = caC.aMesesPermHr(fechas, marcas)
        val diaAusentismos = caC.aDiaAusentismos(fechas, marcas)
        val mesAusentismos = caC.aMesesAusentismos(fechas, marcas)
        val diaRetardos = caC.aDiaRetardos(fechas, marcas)
        val mesRetardos = caC.aMesesRetardos(fechas, marcas)
        val diaOthers = caC.aDiaOthers(fechas, marcas)
        val mesOthers = caC.aMesesOthers(fechas, marcas)
        val diaFaltas = caC.aDiaFaltas(fechas, marcas)
        val mesFaltas = caC.aMesesFaltas(fechas, marcas)
        val marcasOthers = caC.aMarcasOthers(fechas, marcas)
        if (diaVaciones.isNotEmpty()) g.generateVacaciones(diaVaciones, mesVacaciones)
        if (diaAusDesc.isNotEmpty()) g.generateDiaAusDesc(diaAusDesc, mesAusDesc)
        if (diaPermHr.isNotEmpty()) g.generateDiaPermHr(diaPermHr, mesPermHr)
        if (diaAusentismos.isNotEmpty()) g.generateAusentismos(diaAusentismos, mesAusentismos)
        if (diaRetardos.isNotEmpty()) g.generateRetardos(diaRetardos, mesRetardos)
        if (diaOthers.isNotEmpty()) g.generateOthers(diaOthers, mesOthers, marcasOthers)
        if (diaFaltas.isNotEmpty()) g.generateFaltas(diaFaltas, mesFaltas)

        kardexMensualVacaciones =
            g.generateVacaciones(
                g.listaKardex(diaVaciones), g.listaKardexMeses(mesVacaciones)
            ).groupBy { it.time.toLocalDate() }

        kardexMensualAusDesc =
            g.generateDiaAusDesc(
                g.listaKardex(diaAusDesc), g.listaKardexMeses(mesAusDesc)
            ).groupBy { it.time.toLocalDate() }

        kardexMensualPermHrs =
            g.generateDiaPermHr(
                g.listaKardex(diaPermHr), g.listaKardexMeses(mesPermHr)
            ).groupBy { it.time.toLocalDate() }

        kardexMensualAusentismos =
            g.generateAusentismos(
                g.listaKardex(diaAusentismos), g.listaKardexMeses(mesAusentismos)
            ).groupBy { it.time.toLocalDate() }
        kardexMensualRetardos =
            g.generateRetardos(
                g.listaKardex(diaRetardos), g.listaKardexMeses(mesRetardos)
            ).groupBy { it.time.toLocalDate() }

        kardexMensualFaltas =
            g.generateFaltas(
                g.listaKardex(diaFaltas), g.listaKardexMeses(mesFaltas)
            ).groupBy { it.time.toLocalDate() }
        kardexMensualOthers =
            g.generateOthers(
                g.listaKardex(diaOthers),
                g.listaKardexMeses(mesOthers),
                g.listaKardexMarcas(marcasOthers)
            ).groupBy { it.time.toLocalDate() }

    }

    private fun getDiasFestivosService(anio: String) {
        kardexMensualViewModel.dataGetDiasFestivos.value = null
        val diasFestivosRequest = DiasFestivosRequest(User.numCia.toString(), anio)
        kardexMensualViewModel.getDiasFestivos(User.token, diasFestivosRequest)
        statusObserveDiasFestivos()
        kardexMensualViewModel.dataGetDiasFestivos.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val fecha = ArrayList<String>()
                    val marca = ArrayList<String>()
                    for (i in 0 until response.diasFestivos.size) {
                        response.diasFestivos[i].fecha.let { fecha.add(it) }
                        response.diasFestivos[i].marca.let { marca.add(it) }
                    }
                    separateDayFestivos(fecha)
                    getDiasDescansosService(anio)
                }
            }
        }
    }

    private fun statusObserveDiasFestivos() {
        kardexMensualViewModel.statusGetDiasFestivos.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                        Log.i("Progress", requireContext().javaClass.name)
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceDiasFestivos()
                    }

                    is ApiResponceStatus.Error -> {
                        clearServiceDiasFestivos()
                        getDiasDescansosService(ca.getAnio().toString())
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        println("ApiResponceStatus.Error $errorCode")
                    }
                }
            }
        }
    }

    private fun clearServiceDiasFestivos() {
        kardexMensualViewModel.statusGetDiasFestivos.value = null
        kardexMensualViewModel.dataGetDiasFestivos.removeObservers(viewLifecycleOwner)
        kardexMensualViewModel.statusGetDiasFestivos.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }

    private fun separateDayFestivos(fechas: ArrayList<String>) {
        g.generateFestivos(caC.aDiasFestivos(fechas), caC.aMesesFestivos(fechas))
        festivos =
            g.generateFestivos(
                g.listaKardex(caC.aDiasFestivos(fechas)),
                g.listaKardexMeses(caC.aMesesFestivos(fechas))
            ).groupBy { it.time.toLocalDate() }
    }

    private fun getDiasDescansosService(anio: String) {
        kardexMensualViewModel.dataGetDiasDescanso.value = null
        val diasDescansosRequest =
            DiasDescansosRequest(User.numCia.toString(), anio, null, numEmp.toString())
        kardexMensualViewModel.getDiasDescansos(User.token, diasDescansosRequest)
        kardexMensualViewModel.dataGetDiasDescanso.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val fecha = ArrayList<String>()
                    val marca = ArrayList<String>()
                    for (i in 0 until response.diasDescanso.size) {
                        response.diasDescanso[i].fecha.let { fecha.add(it) }
                        response.diasDescanso[i].marca.let { marca.add(it) }
                        separateDayDescansos(fecha)
                        customCalendar()
                    }
                }
            }
        }
        statusObserveDiasDescansos()
    }

    private fun statusObserveDiasDescansos() {
        kardexMensualViewModel.statusGetDiasDescanso.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                        Log.i("Progress", requireContext().javaClass.name)
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceAddPermisos()
                    }

                    is ApiResponceStatus.Error -> {
                        customCalendar()
                        clearServiceAddPermisos()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        println("ApiResponceStatus.Error $errorCode")
                    }
                }
            }
        }
    }

    private fun clearServiceAddPermisos() {
        kardexMensualViewModel.statusGetDiasDescanso.value = null
        kardexMensualViewModel.dataGetDiasDescanso.removeObservers(viewLifecycleOwner)
        kardexMensualViewModel.statusGetDiasDescanso.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }

    private fun separateDayDescansos(fechas: ArrayList<String>) {
        g.generateDescansos(caC.aDiasDescansos(fechas), caC.aMesesDescansos(fechas))
        descansos = g.generateDescansos(
            g.listaKardex(caC.aDiasDescansos(fechas)),
            g.listaKardexMeses(caC.aMesesDescansos(fechas))
        ).groupBy { it.time.toLocalDate() }
    }

    @SuppressLint("SetTextI18n")
    private fun customCalendar() {
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.SUNDAY)

        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainerKardexMensual> {
            override fun create(view: View) = DayViewContainerKardexMensual(view, requireContext())
            override fun bind(container: DayViewContainerKardexMensual, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }

        binding.calendarView.setup(
            currentMonth.withMonth(1),
            currentMonth.withMonth(12),
            daysOfWeek.first()
        )
        binding.calendarView.scrollToMonth(currentMonth)

        binding.calendarView.monthScrollListener = {
            ca.setMes(it.yearMonth.monthValue)
            val mes = ca.kardexTitleCustom(requireContext(), ca.getMes())
            binding.dateMonth.text = " $mes / ${it.yearMonth.year}"
            binding.calendarView.notifyCalendarChanged()
            /*
                        try {
                validateDays(it.yearMonth.monthValue)
            }catch (e:Exception){
                println(e)
            }
             */
        }

        binding.nextMonth.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.let {
                binding.calendarView.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }

        binding.previusMonth.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.let {
                binding.calendarView.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainerKardexMensual> {
            override fun create(view: View) = DayViewContainerKardexMensual(view, requireContext())
            override fun bind(container: DayViewContainerKardexMensual, data: CalendarDay) {
                container.day = data
                container.textView.text = data.date.dayOfMonth.toString()
                container.texto.text = data.date.dayOfMonth.toString()
                container.binding.calendarKardexMensual.background = null
                container.binding.calendarDayText.background = null

                if (data.position == DayPosition.MonthDate) {
                    container.textView.setTextColor(Color.WHITE)
                    container.texto.setTextColorRes(R.color.puesto)
                    container.binding.dayLayout.setBackgroundResource(if (selectedDate == data.date) R.color.black else 0)

                    val kardexMensualVacaciones = kardexMensualVacaciones?.get(data.date)
                    val kardexMensualAusDesc = kardexMensualAusDesc?.get(data.date)
                    val kardexMensualPermHrs = kardexMensualPermHrs?.get(data.date)
                    val kardexMensualFaltas = kardexMensualFaltas?.get(data.date)
                    val kardexMensualOthers = kardexMensualOthers?.get(data.date)
                    val descansos = descansos?.get(data.date)
                    val festivos = festivos?.get(data.date)
                    val ausentismos = kardexMensualAusentismos?.get(data.date)
                    val retardos = kardexMensualRetardos?.get(data.date)

                    if (data.date.isBefore(today)) {
                        container.texto.setTextColorRes(R.color.chart_color_text)
                    }
                    if (data.date == today){
                        container.texto.setTextColor(requireContext().getColor(R.color.principal))
                        container.texto.setBackgroundResource(R.drawable.selected_today)
                        container.binding.dayLayout.setBackgroundColor(
                            requireContext().getColorCompat(
                                R.color.white
                            )
                        )
                    }

                    if (kardexMensualVacaciones != null) {
                        caC.pintaVacaciones(container.binding.calendarDayText, requireContext(), container.binding.calendarKardexMensual)
                    }
                    if (kardexMensualAusDesc != null) {
                        caC.pintaAusDesc(container.binding.calendarDayText, requireContext())
                    }
                    if (kardexMensualPermHrs != null) {
                        caC.pintaPermHrs(container.binding.calendarDayText, requireContext())
                    }
                    if (kardexMensualFaltas != null) {
                        caC.pintaFaltas(container.binding.calendarDayText, requireContext())
                    }
                    if (kardexMensualOthers != null) {
                        caC.pintaOthers(
                            container.binding.calendarDayText,
                            requireContext(),
                            kardexMensualOthers
                        )
                    }

                    if (retardos != null) {
                        caC.pintaRetardos(
                            container.binding.calendarDayText,
                            requireContext()
                        )
                    }
                    if (descansos != null) {
                        caC.pintaDescansos(
                            container.binding.calendarDayText,
                            requireContext(),
                            descansos
                        )
                    }
                    if (festivos != null) {
                        caC.pintaFestivos(
                            container.binding.calendarDayText,
                            requireContext(),
                            festivos
                        )
                    }
                    if (ausentismos != null) {
                        caC.pintaAusentismos(
                            container.binding.calendarDayText,
                            requireContext(),
                            ausentismos
                        )
                    }


                } else {
                    container.texto.setTextColorRes(R.color.numDays)
                    container.textView.setTextColorRes(R.color.white)
                    container.binding.dayLayout.setBackgroundResource(R.color.white)
                }
            }
        }
        binding.calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)

            override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                if (container.titlesContianer.tag == null) {
                    container.titlesContianer.tag = data.yearMonth
                    container.titlesContianer.children.map { it as TextView }.forEachIndexed { index, textView ->
                        val dayOfWeek = daysOfWeek[index]
                        val title = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale(Constants.LENGUAGEes, Constants.COUNTRYES))
                        textView.text = title.uppercase()
                    }
                }
            }
        }
    }
/*
    private fun validateDays(month: Int){
        val sizeVacaciones = ArrayList<Int>()
        val sizeAusentismos = ArrayList<Int>()
        val sizePermisoHrs = ArrayList<Int>()
        for (i in mesVacaciones.indices) {
            if (mesVacaciones[i] == month) {
                sizeVacaciones.add(mesVacaciones[i])
            }
        }
        for (i in mesAusDesc.indices) {
            if (mesAusDesc[i] == month) {
                sizeAusentismos.add(mesAusDesc[i])
            }
        }
        for (i in mesPermHr.indices) {
            if (mesPermHr[i] == month) {
                sizePermisoHrs.add(mesPermHr[i])
            }
        }
        binding.txtNumVacaciones.text = sizeVacaciones.size.toString()
        binding.txtNumAusentismos.text = sizeAusentismos.size.toString()
        binding.txtNumPermisoHrs.text = sizePermisoHrs.size.toString()
    }

 */
}
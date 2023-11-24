package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.Calendario
import com.venturessoft.human.humanrhdapp.core.HeaderUser
import com.venturessoft.human.humanrhdapp.databinding.CalendarDayLayoutBinding
import com.venturessoft.human.humanrhdapp.databinding.FragmentProgAnualVacBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.MonthViewContainer
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.ui.vm.KardexMensualViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.AddProgAnualVacRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.vm.ProgAnualVacacionesViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.ui.vm.KardexAnualViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.utilis.calendar.*
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.getColorCompat
import com.venturessoft.human.humanrhdapp.utilis.complements.getDrawableCompat
import com.venturessoft.human.humanrhdapp.utilis.complements.makeVisible
import com.venturessoft.human.humanrhdapp.utilis.complements.setTextColorRes
import es.dmoral.toasty.Toasty
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

class ProgAnualVacFragment : Fragment() {
    private lateinit var binding: FragmentProgAnualVacBinding
    private val welcomeFragmentViewModel: WelcomeFragmentViewModel by activityViewModels()
    private val progAnualVacacionesViewModel: ProgAnualVacacionesViewModel by viewModels()
    private val kardexMensualViewModel = KardexMensualViewModel()
    private val kardexAnualViewModel = KardexAnualViewModel()
    private var caC: CalendarioCustom = CalendarioCustom()
    private var g: GeneraMarcas = GeneraMarcas()
    private var ca: Calendario = Calendario()
    private val today = LocalDate.now()
    private var names: String? = null
    private var numEmp: String? = null
    private val currentMonth = YearMonth.now()!!
    private var selectedDate: LocalDate? = null
    private var kardexMensualAusentismos: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualRetardos: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualVacaciones: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualFaltas: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualOthers: Map<LocalDate, List<CustomDay>>? = null
    private var descansos: Map<LocalDate, List<CustomDay>>? = null
    private var festivos: Map<LocalDate, List<CustomDay>>? = null
    private val selectedDates = mutableSetOf<LocalDate>()
    private val fecha = ArrayList<String>()
    private var mainInterface: MainInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProgAnualVacBinding.inflate(inflater, container, false)
        welcomeFragmentViewModel.idMenu.observe(viewLifecycleOwner) {dataUser->
            HeaderUser(requireContext(),dataUser,binding.headerUser)
            names = dataUser.nombreCompleto
            numEmp = dataUser.numEmp
            loadService()
        }
        return binding.root
    }
    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    private fun viewCustomization() {
        binding.save.button.setOnClickListener { addProgAnualVacService() }
        binding.btnListaSolicitudes.setOnClickListener { findNavController().navigate(R.id.to_listaSolicitudesFragment) }
        binding.txtSelectDays.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.txtSelectDays.right - binding.txtSelectDays.compoundDrawables[2].bounds.width())) {
                    clear()
                }
            }
            false
        }
    }
    internal fun loadTxtSelect(dateSize: Int) {
        val text: String = when (dateSize) {
            0 -> "$dateSize ${getString(R.string.dias_seleccionados)}"
            1 -> "$dateSize ${getString(R.string.dia_seleccionado)}"
            else -> "$dateSize ${getString(R.string.dias_seleccionados)}"
        }
        binding.txtSelectDays.setText(text)
    }
    private fun loadService() {
        corutineGetKardexAnual(ca.getAnio().toString())
    }
    @SuppressLint("SetTextI18n")
    private fun clear(){
        selectedDates.clear()
        fecha.clear()
        binding.calendarView.notifyCalendarChanged()
        binding.txtSelectDays.setText("0 ${getString(R.string.dias_seleccionados)}")
    }
    private fun corutineGetKardexAnual(anio: String) {
        kardexAnualViewModel.data.value = null
        val kardexAnualRequest = KardexAnualRequest(User.numCia.toString(), numEmp!!, anio, motivo = "Todos")
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
        val diaVaciones = caC.aDiaVacaciones(fechas, marcas)
        val mesVacaciones = caC.aMesesVacaciones(fechas, marcas)
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
        if (diaAusentismos.isNotEmpty()) g.generateAusentismos(diaAusentismos, mesAusentismos)
        if (diaRetardos.isNotEmpty()) g.generateRetardos(diaRetardos, mesRetardos)
        if (diaOthers.isNotEmpty()) g.generateOthers(diaOthers, mesOthers, marcasOthers)
        if (diaFaltas.isNotEmpty()) g.generateFaltas(diaFaltas, mesFaltas)

        kardexMensualVacaciones = g.generateVacaciones(
            g.listaKardex(diaVaciones), g.listaKardexMeses(mesVacaciones)
        ).groupBy { it.time.toLocalDate() }

        kardexMensualAusentismos = g.generateAusentismos(
            g.listaKardex(diaAusentismos), g.listaKardexMeses(mesAusentismos)
        ).groupBy { it.time.toLocalDate() }

        kardexMensualRetardos = g.generateRetardos(
            g.listaKardex(diaRetardos), g.listaKardexMeses(mesRetardos)
        ).groupBy { it.time.toLocalDate() }

        kardexMensualFaltas = g.generateFaltas(
            g.listaKardex(diaFaltas), g.listaKardexMeses(mesFaltas)
        ).groupBy { it.time.toLocalDate() }

        kardexMensualOthers = g.generateOthers(
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
                    }
                    is ApiResponceStatus.Success -> {
                        clearServiceDiasFestivos()
                    }
                    is ApiResponceStatus.Error -> {
                        clearServiceDiasFestivos()
                        getDiasDescansosService(ca.getAnio().toString())
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
        festivos = g.generateFestivos(g.listaKardex(caC.aDiasFestivos(fechas)),
            g.listaKardexMeses(caC.aMesesFestivos(fechas))
        ).groupBy { it.time.toLocalDate() }
    }
    private fun getDiasDescansosService(anio: String) {
        kardexMensualViewModel.dataGetDiasDescanso.value = null
        val diasDescansosRequest = DiasDescansosRequest(User.numCia.toString(), anio, null, numEmp.toString())
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
                        viewCustomization()
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
                    }
                    is ApiResponceStatus.Success -> {
                        clearServiceDiasDescansos()
                    }
                    is ApiResponceStatus.Error -> {
                        viewCustomization()
                        customCalendar()
                        clearServiceDiasDescansos()
                    }
                }
            }
        }
    }
    private fun clearServiceDiasDescansos() {
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
    private fun addProgAnualVacService() {
        progAnualVacacionesViewModel.dataPAVacaciones.value = null
        val validate = checkFields()
        if (validate == "true") {
            val addProgAnualVacRequest = AddProgAnualVacRequest(
                User.numCia.toString(),
                numEmp!!.toString(),
                fecha,
                User.usuario
            )
            progAnualVacacionesViewModel.addPAVacaciones(User.token, addProgAnualVacRequest)
            progAnualVacacionesViewModel.dataPAVacaciones.observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    if (response.codigo == "0") {
                        val bundle = Bundle()
                        bundle.putInt(Constants.OPCION, 2)
                        findNavController().navigate(R.id.to_succesFragment, bundle)
                    }
                }
            }
            statusObserveAddVacaciones()
        } else {
            Toasty.warning(requireContext(), validate, Toast.LENGTH_SHORT).show()
        }
    }
    private fun statusObserveAddVacaciones() {
        progAnualVacacionesViewModel.statusAddPAVacaciones.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }
                    is ApiResponceStatus.Success -> {
                        clearServiceAddVacaciones()
                    }
                    is ApiResponceStatus.Error -> {
                        clearServiceAddVacaciones()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.loadMessageError(errorCode, requireActivity())
                    }
                }
            }
        }
    }
    private fun clearServiceAddVacaciones() {
        progAnualVacacionesViewModel.statusAddPAVacaciones.value = null
        progAnualVacacionesViewModel.dataPAVacaciones.removeObservers(viewLifecycleOwner)
        progAnualVacacionesViewModel.statusAddPAVacaciones.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }
    private fun checkFields(): String {
        if (fecha.isEmpty()) {
            return getString(R.string.fvSeleccionarFecha)
        }
        return "true"
    }
    @SuppressLint("SetTextI18n")
    private fun customCalendar() {
        val ctx = requireContext()
        val singleBackground = ctx.getDrawableCompat(R.drawable.example_4_single_selected_bg)
        @SuppressLint("ClickableViewAccessibility")
        class DarViewContainerProgAnual(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            var textView = CalendarDayLayoutBinding.bind(view).calendarDayText
            var texto = CalendarDayLayoutBinding.bind(view).calendarKardexMensual
            val binding = CalendarDayLayoutBinding.bind(view)
            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate && (day.date == today || day.date.isAfter(today))) {
                        val text = caC.validaTxt(textView.text as String, requireContext(), 2)
                        if (text == "else") {
                            dateClicked(date = day.date)
                            loadTxtSelect(selectedDates.size)
                        } else {
                            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.SUNDAY)
        binding.calendarView.dayBinder = object : MonthDayBinder<DarViewContainerProgAnual> {
            override fun create(view: View) = DarViewContainerProgAnual(view)
            override fun bind(container: DarViewContainerProgAnual, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }
        binding.calendarView.setup(
            currentMonth.withMonth(1).withYear(2023),
            currentMonth.withMonth(12).withYear(2024), daysOfWeek.first()
        )
        binding.calendarView.scrollToMonth(currentMonth)

        val titleContainer = binding.titlesContainer
        binding.calendarView.monthScrollListener = {
            ca.setMes(it.yearMonth.monthValue)
            val mes = ca.kardexTitleCustom(requireContext(), ca.getMes())
            titleContainer.text = "$mes ${it.yearMonth.year}"
        }

        binding.calendarView.dayBinder = object : MonthDayBinder<DarViewContainerProgAnual> {
            override fun create(view: View) = DarViewContainerProgAnual(view)

            @SuppressLint("SetTextI18n")
            override fun bind(container: DarViewContainerProgAnual, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
                container.texto.text = data.date.dayOfMonth.toString()
                container.binding.calendarDayText.background = null
                container.day = data
                val customKardexMensual = container.binding.calendarKardexMensual
                val context = container.binding.root.context
                customKardexMensual.background = null
                if (data.position == DayPosition.MonthDate) {
                    container.textView.setTextColor(Color.WHITE)
                    container.binding.dayLayout.setBackgroundResource(if (selectedDate == data.date) R.color.black else 0)

                    val kardexMensualVacaciones = kardexMensualVacaciones?.get(data.date)
                    val kardexMensualFaltas = kardexMensualFaltas?.get(data.date)
                    val kardexMensualOthers = kardexMensualOthers?.get(data.date)
                    val descansos = descansos?.get(data.date)
                    val festivos = festivos?.get(data.date)
                    val ausentismos = kardexMensualAusentismos?.get(data.date)
                    val retardos = kardexMensualRetardos?.get(data.date)

                    if (data.date.isBefore(today)) {
                        container.texto.setTextColor(context.getColor(R.color.proganual_4_grey))
                        container.texto.isClickable = false
                    } else {
                        when {
                            selectedDates.contains(data.date) -> {
                                customKardexMensual.setTextColorRes(R.color.white)
                                customKardexMensual.applyBackground(singleBackground)
                            }
                            today == data.date -> {
                                container.texto.setTextColor(context.getColor(R.color.black))
                                container.texto.setBackgroundResource(R.drawable.example_3_selected_bg)
                                container.binding.dayLayout.setBackgroundColor(
                                    context.getColorCompat(
                                        R.color.white
                                    )
                                )
                            }
                            else -> customKardexMensual.setTextColorRes(R.color.black)
                        }
                    }
                    if (kardexMensualVacaciones != null) {
                        caC.pintaVacaciones(container.binding.calendarDayText, requireContext(), container.binding.calendarDayText)
                    }
                    if (kardexMensualFaltas != null) {
                        caC.pintaFaltas(container.binding.calendarDayText, requireContext())
                    }
                    if (kardexMensualOthers != null) {
                        caC.pintaOthers(container.binding.calendarDayText, requireContext(), kardexMensualOthers)
                    }
                    if (descansos != null) {
                        caC.pintaDescansos(container.binding.calendarDayText, requireContext(), descansos)
                    }
                    if (festivos != null) {
                        caC.pintaFestivos(container.binding.calendarDayText, requireContext(), festivos)
                    }
                    if (ausentismos != null) {
                        caC.pintaAusentismos(container.binding.calendarDayText, requireContext(), ausentismos)
                    }
                    if (retardos != null) {
                        caC.pintaRetardos(container.binding.calendarDayText, requireContext())
                    }
                } else {
                    container.texto.setBackgroundColor(context.getColor(R.color.fondovacaciones))
                    container.textView.setTextColor(Color.BLACK)
                    container.textView.setTextColor(Color.WHITE)
                }
            }
            private fun View.applyBackground(drawable: Drawable) {
                makeVisible(); background = drawable
            }
        }
        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
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
    internal fun dateClicked(date: LocalDate) {
        if (selectedDates.contains(date)) {
            selectedDates.remove(date)
            fecha.remove(date.toString())
        } else {
            selectedDates.add(date)
            fecha.add(date.toString())
        }
        binding.calendarView.notifyMonthChanged(date.yearMonth)
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
}
package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.venturessoft.human.humanrhdapp.utilis.complements.getColorCompat
import com.venturessoft.human.humanrhdapp.utilis.calendar.CustomDay
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.Calendario
import com.venturessoft.human.humanrhdapp.core.HeaderUser
import com.venturessoft.human.humanrhdapp.databinding.CalendarDayLayoutBinding
import com.venturessoft.human.humanrhdapp.databinding.FragmentAusentismos2Binding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.MonthViewContainer
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.vm.AusentismosViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.ui.vm.KardexAnualViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.ui.vm.KardexMensualViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.calendar.CalendarioCustom
import com.venturessoft.human.humanrhdapp.utilis.calendar.ContinuousSelectionHelper
import com.venturessoft.human.humanrhdapp.utilis.calendar.DateSelection
import com.venturessoft.human.humanrhdapp.utilis.calendar.GeneraMarcas
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.getDrawableCompat
import com.venturessoft.human.humanrhdapp.utilis.complements.makeVisible
import com.venturessoft.human.humanrhdapp.utilis.complements.setTextColorRes
import es.dmoral.toasty.Toasty
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.*

class AusentismosFragment : Fragment() {

    private lateinit var binding: FragmentAusentismos2Binding
    private val welcomeFragmentViewModel: WelcomeFragmentViewModel by activityViewModels()
    private val ausentismosViewModel: AusentismosViewModel by activityViewModels()
    private val timeManagementVM: TimeManagementVM by activityViewModels()
    private val kardexMensualViewModel = KardexMensualViewModel()
    private val kardexAnualViewModel = KardexAnualViewModel()
    private var caC: CalendarioCustom = CalendarioCustom()
    private var selection = DateSelection()
    private var g: GeneraMarcas = GeneraMarcas()
    private var ca: Calendario = Calendario()
    private var names: String? = null
    private var numEmp: String? = null
    private val currentMonth = YearMonth.now()!!
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private var kardexMensualAusentismos: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualRetardos: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualVacaciones: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualFaltas: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualOthers: Map<LocalDate, List<CustomDay>>? = null
    private var descansos: Map<LocalDate, List<CustomDay>>? = null
    private var festivos: Map<LocalDate, List<CustomDay>>? = null
    private var mainInterface: MainInterface? = null
    private var dateSelected: String = ""
    private var opc: Int = 1
    private var motivo: String? = null
    private var motivoDescripcion: String? = null
    private var dias: String? = null
    private val formatoVista = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val formatoEnviado = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    private var fechaInicio: String = ""
    private var fechaFin: String = ""
    private var fecha = mutableListOf<String>()
    private var mesView: Int = 0
    private var mesViewEnd: Int? = null
    private var starSelected: LocalDate? = null
    private var endSelected: LocalDate? = null
    private var anioSelected: Int? = null
    private lateinit var spinnerCatalog: SpinnerCatalog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        arguments?.let { bundle ->
            motivo = bundle.getString(Constants.MOTIVO, "")
            dateSelected = bundle.getString(Constants.FECHA_INICIAL, "")
            motivoDescripcion = bundle.getString(Constants.MOTIVO_DESCRIPCION, "")
            bundle.getString(Constants.ESTATUS, "")
            dias = bundle.getString(Constants.DIAS, "")
            bundle.getString(Constants.NUM_EMPLEADO, "")
            opc = bundle.getInt(Constants.OPCION, 0)
            SpinnerCatalog.reason = motivo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAusentismos2Binding.inflate(inflater, container, false)
        welcomeFragmentViewModel.idMenu.observe(viewLifecycleOwner) { dataUser ->
            HeaderUser(requireContext(), dataUser, binding.headerUser)
            names = dataUser.nombreCompleto
            numEmp = dataUser.numEmp
            loadService()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerCatalog = SpinnerCatalog(timeManagementVM, this)
        setHintSpinner()
        if (opc == 0) loadSelected()
    }

    override fun onDestroy() {
        super.onDestroy()
        fecha.clear()
        motivo = null
        SpinnerCatalog.reason = null
    }

    @SuppressLint("SetTextI18n")
    private fun loadSelected() {
        starSelected = convertToLocalDate(dateSelected)
        endSelected = convertToLocalDate(dateSelected).plusDays(dias!!.toLong() - 1)
        selection.startDate = starSelected; selection.endDate = endSelected
        mesViewEnd = endSelected?.monthValue
        mesView = starSelected?.monthValue!!
        anioSelected = starSelected?.year
        binding.texviewNuevaSolicitudAusent.text = getString(R.string.edit_solicitud)
        binding.save.button.text = getString(R.string.actualizar)
    }

    private fun setHintSpinner() {
        binding.spMotivos.inputLayout.hint = getString(R.string.faMotivo)
    }

    private fun convertToLocalDate(date: String): LocalDate {
        val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.parse(date, formatoFecha)
    }

    @SuppressLint("SetTextI18n")
    private fun initializeView() {
        if (opc == 0) {
            val texto: String =
                if (dias?.toInt()!! > 1) getString(R.string.faDias_sp) else getString(R.string.faDia)
            binding.txtDias2.text = texto
            binding.txtDias2.text = texto
        } else binding.txtDias2.text = getString(R.string.fvSeleccionarFecha)

        binding.save.button.setOnClickListener {
            val validate = checkFields()
            if (validate == "true") {
                daysBetweem(fechaInicio.formatoDiag(), fechaFin.formatoDiag())
                addAusentismo()
            } else {
                Toasty.warning(requireContext(), validate, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadService() {
        spinnerCatalog.getCatalogoReasons(binding.spMotivos.spinner, null, opc, motivoDescripcion)
        corutineGetKardexAnual(ca.getAnio().toString())
    }

    private fun dateNow() {
        if (opc == 0) {
            binding.FechaInicio.text = Utilities.cambiarFormatoFecha(starSelected.toString(), false)
            binding.FechaFinal.text = Utilities.cambiarFormatoFecha(endSelected.toString(), false)
            fechaInicio = starSelected.toString()
            fechaFin = endSelected.toString()
            fechaInicio = Utilities.cambiarFormatoFecha(fechaInicio, false)
            fechaFin = Utilities.cambiarFormatoFecha(fechaFin, false)
        } else {
            val txtfecha = caC.mostrarFecha(ca.getAnio(), ca.getMes(), ca.getDia())
            binding.FechaInicio.text = txtfecha
            binding.FechaFinal.text = txtfecha
            val fechaFormato = caC.asignarFecha(ca.getAnio(), ca.getMes(), ca.getDia())
            fechaInicio = fechaFormato
            fechaFin = fechaFormato
            fechaInicio = fechaInicio.formatoDiag()
            fechaFin = fechaFin.formatoDiag()
        }
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
        kardexMensualVacaciones =
            g.generateVacaciones(g.listaKardex(diaVaciones), g.listaKardexMeses(mesVacaciones))
                .groupBy { it.time.toLocalDate() }
        kardexMensualAusentismos =
            g.generateAusentismos(g.listaKardex(diaAusentismos), g.listaKardexMeses(mesAusentismos))
                .groupBy { it.time.toLocalDate() }
        kardexMensualRetardos =
            g.generateRetardos(g.listaKardex(diaRetardos), g.listaKardexMeses(mesRetardos))
                .groupBy { it.time.toLocalDate() }
        kardexMensualFaltas =
            g.generateFaltas(g.listaKardex(diaFaltas), g.listaKardexMeses(mesFaltas))
                .groupBy { it.time.toLocalDate() }
        kardexMensualOthers = g.generateOthers(
            g.listaKardex(diaOthers),
            g.listaKardexMeses(mesOthers),
            g.listaKardexMarcas(marcasOthers)
        )
            .groupBy { it.time.toLocalDate() }
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
        festivos = g.generateFestivos(
            g.listaKardex(caC.aDiasFestivos(fechas)),
            g.listaKardexMeses(caC.aMesesFestivos(fechas))
        )
            .groupBy { it.time.toLocalDate() }
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
                        initializeView()
                        customCalendar()
                        dateNow()
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
                        initializeView()
                        customCalendar()
                        dateNow()
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
        )
            .groupBy { it.time.toLocalDate() }
    }

    private fun addAusentismo() {
        ausentismosViewModel.dataAddAusentismos.value = null
        val addAusentismosRequest = AddAusentismosRequest(
            User.numCia,
            numEmp!!.toLong(),
            SpinnerCatalog.reason?.toLong() ?: 0,
            User.usuario,
            "A",
            fecha
        )
        ausentismosViewModel.addAusentismos(User.token, addAusentismosRequest)
        ausentismosViewModel.dataAddAusentismos.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val bundle = Bundle()
                    bundle.putInt(Constants.OPCION, 3)
                    findNavController().navigate(R.id.to_succesFragment, bundle)
                    fecha.clear()
                }
            }
        }
        statusObserveAddAusentismos()
    }

    private fun statusObserveAddAusentismos() {
        ausentismosViewModel.statusAddAusentismos.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                        Log.i("Progress", requireContext().javaClass.name)
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceAddAusentismos()
                    }

                    is ApiResponceStatus.Error -> {
                        clearServiceAddAusentismos()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.loadMessageError(errorCode, requireActivity())
                        fecha.clear()
                        binding.calendarView
                        fechaInicio = binding.FechaInicio.text.toString()
                        fechaFin = binding.FechaFinal.text.toString()
                        println("FECHA INICIO : FECHA FIN $fechaInicio $fechaFin")
                    }
                }
            }
        }
    }

    private fun clearServiceAddAusentismos() {
        ausentismosViewModel.statusAddAusentismos.value = null
        ausentismosViewModel.dataAddAusentismos.removeObservers(viewLifecycleOwner)
        ausentismosViewModel.statusAddAusentismos.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }

    private fun daysBetweem(fI: String, fF: String? = null) {
        if (fF.isNullOrEmpty() || fF == getString(R.string.fvSeleccionarFecha)) {
            fecha.add(fI)
            fecha.distinct().toMutableList()
        } else {
            val partsFi =
                fI.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val dayOne = partsFi[0]
            val monthOne = partsFi[1]
            val yearOne = partsFi[2]
            val partsFf =
                fF.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val daytwo = partsFf[0]
            val monthtwo = partsFf[1]
            val yeartwo = partsFf[2]
            val fIn = LocalDate.of(yearOne.toInt(), monthOne.toInt(), dayOne.toInt())
            val fFin = LocalDate.of(yeartwo.toInt(), monthtwo.toInt(), daytwo.toInt())

            val diasTotales = ChronoUnit.DAYS.between(fIn, fFin) + 1

            val listaDias = mutableListOf<String>()

            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            for (i in 0 until diasTotales) {
                val diaActual = fIn.plusDays(i)
                listaDias.add(diaActual.format(formatter))
            }

            println("El total de días entre las fechas es: $diasTotales")
            println("Lista de días:")
            for (dia in listaDias) {
                println(dia)
            }
            fecha = listaDias
        }
        fecha.distinct().toMutableList()
        println("FECHA $fecha")
    }

    @SuppressLint("SetTextI18n")
    private fun customCalendar() {
        val clipLevelHalf = 5000
        val ctx = requireContext()
        val rangeStartBackground =
            ctx.getDrawableCompat(R.drawable.example_4_continuous_selected_bg_start)
                .also { it.level = clipLevelHalf }
        val rangeEndBackground =
            ctx.getDrawableCompat(R.drawable.example_4_continuous_selected_bg_end)
                .also { it.level = clipLevelHalf }
        val rangeMiddleBackground =
            ctx.getDrawableCompat(R.drawable.example_4_continuous_selected_bg_middle)
        val singleBackground = ctx.getDrawableCompat(R.drawable.example_4_single_selected_bg)

        @SuppressLint("ClickableViewAccessibility")
        class DayViewContainerAusentismos(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            var textView = CalendarDayLayoutBinding.bind(view).calendarDayText
            var texto = CalendarDayLayoutBinding.bind(view).calendarKardexMensual
            val binding = CalendarDayLayoutBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        val text = caC.validaTxt(textView.text as String, requireContext(), 2)
                        if (text == "else") {
                            selection = ContinuousSelectionHelper.getSelection(
                                clickedDate = day.date,
                                dateSelection = selection,
                            )
                            this@AusentismosFragment.binding.calendarView.notifyCalendarChanged()
                            bindSummaryViews()
                        } else {
                            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.SUNDAY)
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainerAusentismos> {
            override fun create(view: View) = DayViewContainerAusentismos(view)
            override fun bind(container: DayViewContainerAusentismos, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }

        binding.calendarView.setup(
            currentMonth.withMonth(1).withYear(2023),
            currentMonth.withMonth(12).withYear(2024), daysOfWeek.first()
        )

        if (opc == 0) binding.calendarView.scrollToMonth(
            currentMonth.withMonth(mesView).withYear(anioSelected!!)
        )
        else binding.calendarView.scrollToMonth(currentMonth)
        binding.calendarView.notifyCalendarChanged()
        binding.calendarView.monthScrollListener = {
            ca.setMes(it.yearMonth.monthValue)
            val mes = ca.kardexTitleCustom(requireContext(), ca.getMes())
            binding.titlesContainer.text = "$mes ${it.yearMonth.year}"
        }
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainerAusentismos> {
            override fun create(view: View) = DayViewContainerAusentismos(view)

            @SuppressLint("SetTextI18n")
            override fun bind(container: DayViewContainerAusentismos, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
                container.texto.text = data.date.dayOfMonth.toString()
                container.binding.calendarDayText.background = null
                container.day = data
                val context = container.binding.root.context
                val customKardexMensual = container.binding.calendarKardexMensual
                val (startDate, endDate) = selection
                customKardexMensual.background = null
                if (data.position == DayPosition.MonthDate) {
                    container.textView.setTextColor(Color.WHITE)
                    container.binding.dayLayout.setBackgroundResource(if (selectedDate == data.date) R.color.white else 0)
                    val kardexMensualVacaciones = kardexMensualVacaciones?.get(data.date)
                    val kardexMensualFaltas = kardexMensualFaltas?.get(data.date)
                    val kardexMensualOthers = kardexMensualOthers?.get(data.date)
                    val descansos = descansos?.get(data.date)
                    val festivos = festivos?.get(data.date)
                    val ausentismos = kardexMensualAusentismos?.get(data.date)
                    val retardos = kardexMensualRetardos?.get(data.date)
                    when {
                        startDate == data.date && endDate == null -> {
                            customKardexMensual.setTextColorRes(R.color.white)
                            customKardexMensual.applyBackground(singleBackground)
                            if (opc == 0) {
                                val texto: String =
                                    if (dias?.toInt()!! > 1) getString(R.string.faDias_sp) else getString(
                                        R.string.faDia
                                    )
                                binding.txtDias2.text = texto
                            } else {
                                binding.FechaFinal.text = getString(R.string.fvSeleccionarFecha)
                                binding.txtDias2.text = getString(R.string.faDia)
                                fechaInicio = fechaInicio.formatoDiag()
                                fechaFin = fechaInicio.formatoDiag()
                            }
                        }

                        data.date == startDate -> {
                            customKardexMensual.setTextColorRes(R.color.white)
                            customKardexMensual.applyBackground(rangeStartBackground)
                            customKardexMensual.applyBackground(singleBackground)
                        }

                        startDate != null && endDate != null && (data.date > startDate && data.date < endDate) -> {
                            customKardexMensual.setTextColorRes(R.color.white)
                            customKardexMensual.applyBackground(rangeMiddleBackground)
                        }

                        data.date == endDate -> {
                            customKardexMensual.setTextColorRes(R.color.white)
                            customKardexMensual.applyBackground(rangeEndBackground)
                            customKardexMensual.applyBackground(singleBackground)
                        }

                        data.date == today -> {
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
                    if (kardexMensualVacaciones != null) {
                        caC.pintaVacaciones(
                            container.binding.calendarDayText,
                            requireContext(), container.binding.calendarDayText
                        )
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
                    if (retardos != null) {
                        caC.pintaRetardos(
                            container.binding.calendarDayText,
                            requireContext()
                        )
                    }
                } else {
                    container.texto.setBackgroundColor(context.getColor(R.color.fondovacaciones))
                    container.textView.setTextColor(Color.WHITE)
                }
            }

            private fun View.applyBackground(drawable: Drawable) {
                makeVisible()
                background = drawable
            }
        }
        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                if (container.titlesContianer.tag == null) {
                    container.titlesContianer.tag = data.yearMonth
                    container.titlesContianer.children.map { it as TextView }
                        .forEachIndexed { index, textView ->
                            val dayOfWeek = daysOfWeek[index]
                            val title = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale(Constants.LENGUAGEes, Constants.COUNTRYES))
                            textView.text = title.uppercase()
                        }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    internal fun bindSummaryViews() {
        binding.FechaInicio.apply {
            if (selection.startDate != null) {
                text = formatoVista.format(selection.startDate)
                fechaInicio = formatoEnviado.format(selection.startDate)
                fechaInicio = fechaInicio.formatoDiag()
            }
        }
        binding.FechaFinal.apply {
            if (selection.endDate != null) {
                text = formatoVista.format(selection.endDate)
                fechaFin = formatoEnviado.format(selection.endDate)
                fechaFin = fechaFin.formatoDiag()
            }
        }
        if (binding.FechaInicio.text.isNotEmpty() && binding.FechaFinal.text.isNotEmpty() && binding.FechaFinal.text != getString(
                R.string.fvSeleccionarFecha
            )
        ) {
            val validateDate = ca.validaFechaMenorAusentismos(
                binding.FechaInicio.text as String,
                binding.FechaFinal.text as String, this
            )
            if (validateDate == "correcto") {
                val d = caC.diferenciaFechas(fechaInicio.formatoGuion(), fechaFin.formatoGuion())
                binding.txtDias2.text = "$d ${getString(R.string.faDias_sp)}"
            } else {
                binding.txtDias2.text = getString(R.string.faDia)
                binding.FechaFinal.text = getString(R.string.fvSeleccionarFecha)
                fechaFin = ""
            }
        } else if (fechaInicio.isNotEmpty() && fechaFin.isNotEmpty()) {
            binding.txtDias2.text = getString(R.string.faDia)
        }
        binding.calendarView.notifyCalendarChanged()
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

    internal fun String.formatoDiag(): String {
        return this.replace("-", "/")
    }

    private fun String.formatoGuion(): String {
        return this.replace("/", "-")
    }

    private fun checkFields(): String {
        if (SpinnerCatalog.reason.toString() == "null") {
            return getString(R.string.seleccionar_motivo)
        }
        if (fechaInicio.isEmpty()) {
            return getString(R.string.selecciona_fecha)
        }
        return "true"
    }
}
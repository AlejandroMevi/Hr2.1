package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui

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
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.venturessoft.human.humanrhdapp.utilis.complements.getColorCompat
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.Calendario
import com.venturessoft.human.humanrhdapp.core.HeaderUser
import com.venturessoft.human.humanrhdapp.core.Pickers
import com.venturessoft.human.humanrhdapp.databinding.CalendarDayLayoutBinding
import com.venturessoft.human.humanrhdapp.databinding.FragmentPermisosBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.MonthViewContainer
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.ui.vm.KardexAnualViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.ui.vm.KardexMensualViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.AddPermisosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui.vm.PermisosViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.calendar.CalendarioCustom
import com.venturessoft.human.humanrhdapp.utilis.calendar.CustomDay
import com.venturessoft.human.humanrhdapp.utilis.calendar.GeneraMarcas
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.CONCEPTO
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.CONCEPTO_PERMISO
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.COUNTRYES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.DATE_SELECTED
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.HORA_FINAL
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.HORA_INICIAL
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.LENGUAGEes
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.OBSERVACIONES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.OPCION
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class Permisos : Fragment() {
    private lateinit var binding: FragmentPermisosBinding
    private val welcomeFragmentViewModel: WelcomeFragmentViewModel by activityViewModels()
    private val permisosViewModel: PermisosViewModel by activityViewModels()
    private val timeManagementVM: TimeManagementVM by activityViewModels()
    private val kardexMensualViewModel = KardexMensualViewModel()
    private val kardexAnualViewModel = KardexAnualViewModel()
    private var caC: CalendarioCustom = CalendarioCustom()
    private var g: GeneraMarcas = GeneraMarcas()
    private var p: Pickers = Pickers()
    private var ca: Calendario = Calendario()
    private val currentMonth = YearMonth.now()
    private var today = LocalDate.now()
    private var datePermiso = LocalDate.now()
    private var names: String? = null
    private var numEmp: String? = null
    private var selectedDate: LocalDate? = null
    private var kardexMensualAusentismos: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualRetardos: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualVacaciones: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualFaltas: Map<LocalDate, List<CustomDay>>? = null
    private var kardexMensualOthers: Map<LocalDate, List<CustomDay>>? = null
    private var descansos: Map<LocalDate, List<CustomDay>>? = null
    private var festivos: Map<LocalDate, List<CustomDay>>? = null
    private var fechaPermiso: String = ""
    private var mainInterface: MainInterface? = null
    private var conceptoPermiso : String = ""
    private var concepto : String = ""
    private var dateSelected: String = ""
    private var horaInicialSelected : String? = null
    private var horaFinalSelected : String? = null
    private var observacionesSelected : String = ""
    private var opc: Int = 1
    private var mesView: Int = 0
    private lateinit var spinnerCatalog: SpinnerCatalog
    companion object {
        var horaInicial: String = ""
        var horaFinal: String = ""
        var comentarios: String = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        arguments?.let { bundle ->
            concepto = bundle.getString(CONCEPTO, "")
            conceptoPermiso = bundle.getString(CONCEPTO_PERMISO, "")
            dateSelected = bundle.getString(DATE_SELECTED, "")
            horaInicialSelected = bundle.getString(HORA_INICIAL, "")
            horaFinalSelected = bundle.getString(HORA_FINAL, "")
            observacionesSelected = bundle.getString(OBSERVACIONES, "")
            bundle.getString(Constants.ESTATUS, "")
            bundle.getString(Constants.HORAS_PERMISOS, "")
            bundle.getString(Constants.MINUTOS_PERMISOS, "")
            bundle.getString(Constants.NUM_EMPLEADO, "")
            opc = bundle.getInt(OPCION, 0)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermisosBinding.inflate(inflater, container, false)
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
        if (opc == 0) mainInterface?.setTextToolbar(getString(R.string.editar_permisos))
        else mainInterface?.setTextToolbar(getString(R.string.alta_permisos))
        if (opc == 0) binding.save.button.text = getString(R.string.guardar)
        if (opc == 0) binding.txtDiasVacaciones.text = getString(R.string.actualizar_solicitud)
        if (opc == 0 ) SpinnerCatalog.permisos = conceptoPermiso
    }
    private fun loadSelected(){
        mesView = dateSelected.subSequence(5, 7).toString().toInt()
        binding.etInput.editText.setText(horaInicialSelected)
        binding.etOutput.editText.setText(horaFinalSelected)
        binding.etComentarios.setText(observacionesSelected)
        horaInicial = horaInicialSelected.toString()
        horaFinal = horaFinalSelected.toString()
        comentarios = observacionesSelected
        binding.txtDiasVacaciones.text = getString(R.string.edit_solicitud)
        binding.save.button.text = getString(R.string.actualizar)
    }
    private fun setHintSpinner() {
        binding.spSpinner.inputLayout.hint = getString(R.string.fpConcepto)
        binding.etInput.inputLayout.hint = getString(R.string.fpHoraInicio)
        binding.etOutput.inputLayout.hint = getString(R.string.fpHoraFin)
    }
    private fun initializeView() {
        Utilities.comentEdit(binding.etComentarios)
        binding.etInput.editText.setOnClickListener {
            p.timePickerText(requireActivity(), binding.etInput.editText, 1)
        }
        binding.etOutput.editText.setOnClickListener {
            p.timePickerText(requireActivity(), binding.etOutput.editText, 2)
        }
        binding.save.button.setOnClickListener { addPermiso() }
    }

    override fun onResume() {
        super.onResume()
        if (opc != 0) clearView()
    }
    private fun loadService() {
        spinnerCatalog.getCatalogoPermisos(binding.spSpinner.spinner ,null, opc, concepto)
        corutineGetKardexAnual(ca.getAnio().toString())
    }
    private fun clearView() {
        SpinnerCatalog.permisos = null
        SpinnerCatalog.permisos = "null"
        horaInicial = ""
        horaFinal = ""
        comentarios = ""
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
    private fun addPermiso() {
        permisosViewModel.dataAddPermisos.value = null
        val validate = checkFields()
        if (validate == "true") {
            val addPermisoRequest = AddPermisosRequest(
                User.numCia.toString(),
                numEmp!!.toString(),
                SpinnerCatalog.permisos.toString(),
                fechaPermiso,
                horaInicial,
                horaFinal,
                minutosPermiso = "0",
                horasPermiso = "0",
                estatus = "A",
                comentarios,
                User.usuario
            )
            permisosViewModel.addPermisos(User.token, addPermisoRequest)
            permisosViewModel.dataAddPermisos.observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    if (response.codigo == "0") {
                        val bundle = Bundle()
                        bundle.putInt(OPCION, 4)
                        findNavController().navigate(R.id.to_succesFragment, bundle)
                    }
                }
            }
            statusObserveAddPermiso()
        } else {
            Toasty.warning(requireContext(), validate, Toast.LENGTH_SHORT).show()
        }
    }
    private fun statusObserveAddPermiso() {
        permisosViewModel.statusAddPermisos.observe(viewLifecycleOwner) { status ->
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
                        clearServiceAddPermisos()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.loadMessageError(errorCode, requireActivity())
                    }
                }
            }
        }
    }

    private fun clearServiceAddPermisos() {
        permisosViewModel.statusAddPermisos.value = null
        permisosViewModel.dataAddPermisos.removeObservers(viewLifecycleOwner)
        permisosViewModel.statusAddPermisos.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }

    private fun checkFields(): String {
        when(opc){
            0 ->{
                if (conceptoPermiso.isEmpty()) {
                    return getString(R.string.selecciona_concepto)
                }
            }
            1 ->{
                if (SpinnerCatalog.permisos.toString() == "null") {
                    return getString(R.string.selecciona_concepto)
                }
            }
        }

        if (horaInicial.isEmpty()) {
            return getString(R.string.seleccionar_hora_inicial)
        }
        if (horaFinal.isEmpty()) {
            return getString(R.string.seleccionar_hora_final)
        }
        if (comentarios.isEmpty()) {
            return getString(R.string.ingresar_comentario)
        }
        if (fechaPermiso.isEmpty()) {
            return getString(R.string.fvSeleccionarFecha)
        }
        return "true"
    }
    internal fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendarView.notifyDateChanged(it) }
            binding.calendarView.notifyDateChanged(date)

            //Formato inicial.
            val formato = SimpleDateFormat("yyyy-MM-dd",Locale(LENGUAGEes, COUNTRYES))
            val d: Date = formato.parse(date.toString()) as Date
            formato.applyPattern("dd/MM/yyyy")
            val nuevoFormato: String = formato.format(d)
            if (opc == 0){
                val dateString = dateSelected
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val localDate = LocalDate.parse(dateString, formatter) as LocalDate
                datePermiso = localDate
                val format = SimpleDateFormat("yyyy-MM-dd",Locale(LENGUAGEes, COUNTRYES))
                val de: Date = format.parse(datePermiso.toString()) as Date
                formato.applyPattern("dd/MM/yyyy")
                val nuevoFormat: String = formato.format(de)
                binding.FechaInicio.text = nuevoFormat
                formato.applyPattern("yyyy-MM-dd")
                val nuevoFormatoRequest: String = formato.format(de)
                fechaPermiso = nuevoFormatoRequest
            }else{
                binding.FechaInicio.text = nuevoFormato
                formato.applyPattern("yyyy-MM-dd")
                val nuevoFormatoRequest: String = formato.format(d)
                fechaPermiso = nuevoFormatoRequest
            }
        }
        binding.calendarView.notifyCalendarChanged()
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
                        initializeView()
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
                        initializeView()
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
    @SuppressLint("SetTextI18n")
    private fun customCalendar() {
        @SuppressLint("ClickableViewAccessibility")
        class DayViewContainerPermisos(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            var textView = CalendarDayLayoutBinding.bind(view).calendarDayText
            var texto = CalendarDayLayoutBinding.bind(view).calendarKardexMensual
            val binding = CalendarDayLayoutBinding.bind(view)
            init {
                view.setOnClickListener {
                    if (opc == 1) {
                        if (day.position == DayPosition.MonthDate) {
                            val text = caC.validaTxt(textView.text as String, requireContext(), 2)
                            if (text == "else") {
                                if (texto.isSelected) {
                                    //
                                } else {
                                    selectDate(day.date)
                                    this@Permisos.binding.calendarView.notifyCalendarChanged()
                                }
                            } else {
                                Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        println("LA OPCION ES $opc")
                    }
                }
            }
        }
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.SUNDAY)
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainerPermisos> {
            override fun create(view: View) = DayViewContainerPermisos(view)
            override fun bind(container: DayViewContainerPermisos, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }
        if (opc == 0 ){
            binding.calendarView.setup(
                currentMonth.withMonth(mesView),
                currentMonth.withMonth(mesView), daysOfWeek.first()
            )
        }else{
            binding.calendarView.setup(
                currentMonth.withMonth(1).withYear(2023),
                currentMonth.withMonth(12).withYear(2024), daysOfWeek.first()
            )
        }
        binding.calendarView.scrollToMonth(currentMonth)
        binding.calendarView.notifyCalendarChanged()
        binding.calendarView.post { selectDate(today) }
        binding.calendarView.monthScrollListener = {
            ca.setMes(it.yearMonth.monthValue)
            val mes = ca.kardexTitleCustom(requireContext(), ca.getMes())
            binding.titlesContainer.text = "$mes ${it.yearMonth.year}"
        }

        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainerPermisos> {
            override fun create(view: View) = DayViewContainerPermisos(view)
            override fun bind(container: DayViewContainerPermisos, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
                container.texto.text = data.date.dayOfMonth.toString()
                container.binding.calendarDayText.background = null
                container.day = data
                val context = container.binding.root.context
                val customKardexMensual = container.binding.calendarKardexMensual
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
                    if (opc == 0) {
                    val dateString = dateSelected
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val localDate = LocalDate.parse(dateString, formatter) as LocalDate
                    datePermiso = localDate }
                    when (data.date) {
                        today -> {
                            container.texto.setTextColor(context.getColor(R.color.black))
                            container.texto.setBackgroundResource(R.drawable.example_3_selected_bg)
                            container.binding.dayLayout.setBackgroundColor(
                                context.getColorCompat(
                                    R.color.white
                                )
                            )
                        }
                        datePermiso ->{
                            container.texto.setTextColor(context.getColor(R.color.white))
                            container.texto.setBackgroundResource(R.drawable.example_4_single_selected_bg)
                        }
                        selectedDate -> {
                            container.texto.setTextColor(context.getColor(R.color.white))
                            container.texto.setBackgroundResource(R.drawable.example_4_single_selected_bg)
                        }

                        else -> container.texto.setTextColor(context.getColor(R.color.black))
                    }
                    if (kardexMensualVacaciones != null) {
                        caC.pintaVacaciones(
                            container.binding.calendarDayText,
                            requireContext(),container.binding.calendarDayText
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
        }
        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)

            override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                if (container.titlesContianer.tag == null) {
                    container.titlesContianer.tag = data.yearMonth
                    container.titlesContianer.children.map { it as TextView }.forEachIndexed { index, textView ->
                        val dayOfWeek = daysOfWeek[index]
                        val title = dayOfWeek.getDisplayName(TextStyle.NARROW,Locale(LENGUAGEes, COUNTRYES))
                        textView.text = title.uppercase()
                    }
                }
            }
        }
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
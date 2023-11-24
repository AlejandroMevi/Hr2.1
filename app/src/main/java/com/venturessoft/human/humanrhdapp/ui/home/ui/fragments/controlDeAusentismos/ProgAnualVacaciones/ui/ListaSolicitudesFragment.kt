package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.Calendario
import com.venturessoft.human.humanrhdapp.core.HeaderUser
import com.venturessoft.human.humanrhdapp.databinding.FragmentListaSolicitudesBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.VacacionesProgramadasItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.vm.ProgAnualVacacionesViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.DialogListener
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class ListaSolicitudesFragment : Fragment() {

    private lateinit var binding: FragmentListaSolicitudesBinding
    private lateinit var listaSolicitud: ArrayList<VacacionesProgramadasItem>
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom_anim
        )
    }
    internal val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom_anim
        )
    }
    private val welcomeFragmentViewModel: WelcomeFragmentViewModel by activityViewModels()
    private val progAnualVacacionesViewModel: ProgAnualVacacionesViewModel by viewModels()
    private var mainInterface: MainInterface? = null
    private var ca: Calendario = Calendario()
    private var names: String? = null
    private var numEmp: String? = null
    companion object {
        lateinit var usuariosListaSolicitudesArrayResponse: java.util.ArrayList<VacacionesProgramadasItem>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListaSolicitudesBinding.inflate(inflater, container, false)
        welcomeFragmentViewModel.idMenu.observe(viewLifecycleOwner) { dataUser ->
            HeaderUser(requireContext(), dataUser, binding.headerUser)
            names = dataUser.nombreCompleto
            numEmp = dataUser.numEmp
            listaUsuariosService()
        }
        return binding.root
    }
    override fun onPause() {
        super.onPause()
        ListSolicitudesAdapter.listSolicitudSelect.value = null
        ListSolicitudesAdapter.solicitudesSelect.clear()
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
    private fun loadEmptyVacaciones(){
        with(binding){
            emptyVacaciones.isVisible = true
            txtSinRegistros.isVisible = true
            txtPorElMomento.isVisible = true
            crearSolicitud.isVisible = false
            txtDiasVacaciones.isVisible = false
            listaSolicitudes.isVisible = false
        }
    }
    private fun showConfirmation(){
        Utilities.showDialog(
            title = "Aviso",
            message = "¿Desea autorizar las vacaciones programadas?",
            positiveButtonText = "Aceptar",
            negativeButtonText = "Cancelar",
            context = requireContext(),
            listener = object : DialogListener {
                override fun onPositiveButtonClicked() {
                    var listUser: List<Long> = listOf()
                    if (!ListSolicitudesAdapter.listSolicitudSelect.value.isNullOrEmpty()) {
                        listUser = ListSolicitudesAdapter.listSolicitudSelect.value!!
                    }
                    println(listUser)
                }
                override fun onNegativeButtonClicked() {}
            }
        )
    }
    private fun listaUsuariosService() {
        progAnualVacacionesViewModel.dataListaSolicitudes.value = null
        progAnualVacacionesViewModel.listaSolicitudes(
            User.token,
            User.numCia,
            numEmp!!.toLong(),
            ca.getAnio().toLong()
        )
        progAnualVacacionesViewModel.dataListaSolicitudes.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    if (response.vacacionesProgramadas?.isEmpty() == true) {
                        loadEmptyVacaciones()
                    } else {
                        val list = java.util.ArrayList<VacacionesProgramadasItem>()
                        for (i in response.vacacionesProgramadas!!.indices) {
                            val dataModel = VacacionesProgramadasItem()
                            dataModel.grupo = response.vacacionesProgramadas[i]?.grupo
                            dataModel.secuencia = response.vacacionesProgramadas[i]?.secuencia
                            dataModel.fechaInicio = response.vacacionesProgramadas[i]?.fechaInicio?.formatDate()
                            dataModel.fechaTermino = response.vacacionesProgramadas[i]?.fechaTermino?.formatDate()
                            dataModel.diasTomados = response.vacacionesProgramadas[i]?.diasTomados
                            list.add(dataModel)
                            usuariosListaSolicitudesArrayResponse = list
                            listaSolicitud = usuariosListaSolicitudesArrayResponse
                            setDataKardex(listaSolicitud)
                        }
                    }
                }
            }
        }
        statusObserveAddVacaciones()
    }
    private fun setDataKardex(listaUsuarios: ArrayList<VacacionesProgramadasItem>) {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale(Constants.LENGUAGEes, Constants.COUNTRYES))
        listaUsuarios.sortByDescending {
            it.fechaInicio?.let { it1 -> dateFormat.parse(it1) }
        }
        binding.listaSolicitudes.adapter = ListSolicitudesAdapter(listaUsuarios)
    }
    private fun statusObserveAddVacaciones() {
        progAnualVacacionesViewModel.statusListaSolicitudes.observe(viewLifecycleOwner) { status ->
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
        progAnualVacacionesViewModel.statusListaSolicitudes.value = null
        progAnualVacacionesViewModel.dataListaSolicitudes.removeObservers(viewLifecycleOwner)
        progAnualVacacionesViewModel.statusListaSolicitudes.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }
    private fun String.formatDate(): String {
        return this.replace("-", "/")
    }
}
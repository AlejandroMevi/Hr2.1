package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.Calendario
import com.venturessoft.human.humanrhdapp.core.HeaderUser
import com.venturessoft.human.humanrhdapp.databinding.FragmentListaSolicitudesPeBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.ListaSolicitudesItems
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui.vm.PermisosViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.CONCEPTO
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.CONCEPTO_PERMISO
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.COUNTRYES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.DATE_SELECTED
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.DIAS
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.ESTATUS
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.FECHA_INICIAL
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.HORAS_PERMISOS
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.HORA_FINAL
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.HORA_INICIAL
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.LENGUAGEes
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.MINUTOS_PERMISOS
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.MOTIVO
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.MOTIVO_DESCRIPCION
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.NUM_EMPLEADO
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.OBSERVACIONES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.OPCION
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import java.text.SimpleDateFormat
import java.util.Locale

class ListaSolicitudesPeFragment : Fragment(), ListaSolicitudesGenericAdapter.OnClickListener {

    private lateinit var binding: FragmentListaSolicitudesPeBinding
    private lateinit var listaSolicitud: ArrayList<ListaSolicitudesItems>
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom_anim
        )
    }
    private val welcomeFragmentViewModel: WelcomeFragmentViewModel by activityViewModels()
    private val permisosViewModel: PermisosViewModel by activityViewModels()
    private var mainInterface: MainInterface? = null
    private var ca: Calendario = Calendario()
    private var names: String? = null
    private var numEmp: String? = null
    private var id: Int = 0

    companion object {
        lateinit var usuariosListaSolicitudesArrayResponse: java.util.ArrayList<ListaSolicitudesItems>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaSolicitudesPeBinding.inflate(inflater, container, false)
        welcomeFragmentViewModel.idMenu.observe(viewLifecycleOwner) { dataUser ->
            HeaderUser(requireContext(), dataUser, binding.headerUser)
            names = dataUser.nombreCompleto
            numEmp = dataUser.numEmp
            id = dataUser.id
            if (id == R.id.item_3) binding.txtDiasVacaciones.text = getString(R.string.lista_de_ausentismos)
            else if (id == R.id.item_4) binding.txtDiasVacaciones.text = getString(R.string.lista_de_permisos)
            if (id == R.id.item_3) listaAusentismosService()
            else if (id == R.id.item_4) listaPermisosService()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadButtons()
        viewCustomization()

    }

    private fun loadButtons() {
        binding.floatingActionButton1.setOnClickListener {
            var nav: Unit? = null
            when (id) {
                R.id.item_3 -> {
                    nav = findNavController().navigate(R.id.to_ausentismosFragment)
                }

                R.id.item_4 -> {
                    nav = findNavController().navigate(R.id.to_permisos)
                }
            }
            nav
        }
    }

    private fun viewCustomization() {
        binding.floating.setOnClickListener {
                var nav: Unit? = null
                when (id) {
                    R.id.item_3 -> {
                        nav = findNavController().navigate(R.id.to_ausentismosFragment)
                    }

                    R.id.item_4 -> {
                        nav = findNavController().navigate(R.id.to_permisos)
                    }
                }
                nav
        }
        binding.crearSolicitud.setOnClickListener {
            var nav: Unit? = null
            when (id) {
                R.id.item_3 -> {
                    nav = findNavController().navigate(R.id.to_ausentismosFragment)
                }

                R.id.item_4 -> {
                    nav = findNavController().navigate(R.id.to_permisos)
                }
            }
            nav
        }
    }

    private fun animateButton(){
        val anim: Animation = AnimationUtils.loadAnimation(
            binding.floating.context,
            R.anim.shake
        )
        anim.duration = 140L
        binding.floating.startAnimation(anim)
    }
    private fun loadEmptyAus(){
        with(binding){
            emptyAusentismos.isVisible = true
            txtSinRegistros.isVisible = true
            txtPorElMomento.isVisible = true
            crearSolicitud.isVisible = true
            txtDiasVacaciones.isVisible = false
            listaSolicitudes.isVisible = false
            floating.isVisible = false
        }
    }
    private fun loadEmptyPermisos(){
        with(binding){
            emptyPermisos.isVisible = true
            txtSinRegistros.isVisible = true
            txtSinRegistros.text = getString(R.string.sin_registros_permisos)
            txtPorElMomento.isVisible = true
            txtPorElMomento.text = getString(R.string.por_el_momento_permisos)
            crearSolicitud.isVisible = true
            crearSolicitud.text = getString(R.string.crear_permisos)
            txtDiasVacaciones.isVisible = false
            listaSolicitudes.isVisible = false
            floating.isVisible = false
        }
    }
    private fun scrollListener() {
        binding.listaSolicitudes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.floating.shrink()
                    //binding.headerUser.root.isVisible = false
                    //if (binding.clFloatingButton.isVisible) {
                    //    binding.clFloatingButton.startAnimation(toBottom)
                    //}
                    //binding.clFloatingButton.isVisible = false
                } else {
                    //binding.headerUser.root.isVisible = true
                    binding.floating.extend()
                }
            }
        })
    }

    private fun onAddButton() {
        if (!binding.clFloatingButton.isVisible) {
            binding.clFloatingButton.startAnimation(fromBottom)
        } else {
            binding.clFloatingButton.startAnimation(toBottom)
        }
        binding.clFloatingButton.isVisible = !binding.clFloatingButton.isVisible
    }

    private fun listaPermisosService() {
        permisosViewModel.dataGetPermisos.value = null
        permisosViewModel.getPermisos(
            User.token,
            User.numCia,
            ca.getAnio().toLong(),
            numEmp!!.toLong(),
        )
        permisosViewModel.dataGetPermisos.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    if (response.itemPermisos?.isEmpty() == true) {
                        loadEmptyPermisos()
                    } else {
                        scrollListener()
                        //animateButton()
                        val list = java.util.ArrayList<ListaSolicitudesItems>()
                        for (i in response.itemPermisos!!.indices) {
                            val dataModel = ListaSolicitudesItems()
                            dataModel.horaInicial = response.itemPermisos[i]?.horaInicial
                            dataModel.conceptoPermiso = response.itemPermisos[i]?.conceptoPermiso
                            dataModel.estatus = response.itemPermisos[i]?.estatus
                            dataModel.fechaPermiso = response.itemPermisos[i]?.fechaPermiso
                            dataModel.horaFinal = response.itemPermisos[i]?.horaFinal
                            dataModel.minutosPermiso = response.itemPermisos[i]?.minutosPermiso
                            dataModel.concepto = response.itemPermisos[i]?.concepto
                            dataModel.observaciones = response.itemPermisos[i]?.observaciones
                            dataModel.numEmp = response.itemPermisos[i]?.numEmp
                            dataModel.horasPermiso = response.itemPermisos[i]?.horasPermiso
                            list.add(dataModel)
                            usuariosListaSolicitudesArrayResponse = list
                            listaSolicitud = usuariosListaSolicitudesArrayResponse
                            setDataKardex(listaSolicitud)
                        }
                    }
                }
            }
        }
        statusObserveListaPermisos()
    }

    private fun setDataKardex(listaUsuarios: ArrayList<ListaSolicitudesItems>) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale(LENGUAGEes, COUNTRYES))
        listaUsuarios.sortByDescending {
            it.fechaPermiso?.let { it1 -> dateFormat.parse(it1) }
        }
        binding.listaSolicitudes.adapter =
            ListaSolicitudesGenericAdapter(listaUsuarios, viewLifecycleOwner, this)
    }

    private fun statusObserveListaPermisos() {
        permisosViewModel.statusGetPermisos.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceListaPermisos()
                    }

                    is ApiResponceStatus.Error -> {
                        clearServiceListaPermisos()
                        loadEmptyPermisos()
                    }
                }
            }
        }
    }

    private fun clearServiceListaPermisos() {
        permisosViewModel.statusGetPermisos.value = null
        permisosViewModel.dataGetPermisos.removeObservers(viewLifecycleOwner)
        permisosViewModel.statusGetPermisos.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }

    private fun listaAusentismosService() {
        permisosViewModel.dataGetListaAusentismos.value = null
        permisosViewModel.getListaAusentismos(User.token, User.numCia, numEmp!!.toLong())
        permisosViewModel.dataGetListaAusentismos.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    if (response.itemAusentismos?.isEmpty() == true) {
                        loadEmptyAus()
                    } else {
                        scrollListener()
                        //animateButton()
                        val list = java.util.ArrayList<ListaSolicitudesItems>()
                        for (i in response.itemAusentismos!!.indices) {
                            val dataModel = ListaSolicitudesItems()
                            dataModel.motivo = response.itemAusentismos[i]?.motivo
                            dataModel.fechaInicial = response.itemAusentismos[i]?.fechaInicial
                            dataModel.motivoDescripcion =
                                response.itemAusentismos[i]?.motivoDescripcion
                            dataModel.estatus = response.itemAusentismos[i]?.estatus
                            dataModel.dias = response.itemAusentismos[i]?.dias
                            dataModel.numEmp = response.itemAusentismos[i]?.numEmp
                            list.add(dataModel)
                            usuariosListaSolicitudesArrayResponse = list
                            listaSolicitud = usuariosListaSolicitudesArrayResponse
                            setDataKardexAusentismos(listaSolicitud)
                        }
                    }
                }
            }
        }
        statusObserveListAusentismos()
    }

    private fun setDataKardexAusentismos(listaUsuarios: ArrayList<ListaSolicitudesItems>) {

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale(LENGUAGEes, COUNTRYES))
        listaUsuarios.sortByDescending {
            it.fechaInicial?.let { it1 -> dateFormat.parse(it1) }
        }

        binding.listaSolicitudes.adapter =
            ListaSolicitudesGenericAdapter(listaUsuarios, viewLifecycleOwner, this)
    }

    private fun statusObserveListAusentismos() {
        permisosViewModel.statusGetListaAusentismos.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceListaAusentismos()
                    }

                    is ApiResponceStatus.Error -> {
                        clearServiceListaAusentismos()
                        loadEmptyAus()
                    }
                }
            }
        }
    }

    private fun clearServiceListaAusentismos() {
        permisosViewModel.statusGetListaAusentismos.value = null
        permisosViewModel.dataGetListaAusentismos.removeObservers(viewLifecycleOwner)
        permisosViewModel.statusGetListaAusentismos.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }

    private fun String.formatDate(): String {
        return this.replace("-", "/")
    }

    override fun onClick(item: ListaSolicitudesItems, position: Int) {
        when (id) {
            R.id.item_3 -> {
                val bundle = Bundle()
                bundle.putInt(OPCION, 0)
                bundle.putString(MOTIVO, item.motivo)
                bundle.putString(FECHA_INICIAL, item.fechaInicial)
                bundle.putString(MOTIVO_DESCRIPCION, item.motivoDescripcion.toString())
                bundle.putString(ESTATUS, item.estatus)
                bundle.putString(DIAS, item.dias.toString())
                bundle.putString(NUM_EMPLEADO, item.numEmp.toString())
                findNavController().navigate(R.id.to_ausentismosFragment, bundle)
            }

            R.id.item_4 -> {
                val bundle = Bundle()
                bundle.putInt(OPCION, 0)
                bundle.putString(CONCEPTO, item.concepto)
                bundle.putString(CONCEPTO_PERMISO, item.conceptoPermiso.toString())
                bundle.putString(DATE_SELECTED, item.fechaPermiso)
                bundle.putString(ESTATUS, item.estatus)
                bundle.putString(HORA_INICIAL, item.horaInicial)
                bundle.putString(HORA_FINAL, item.horaFinal)
                bundle.putString(HORAS_PERMISOS, item.horasPermiso.toString())
                bundle.putString(MINUTOS_PERMISOS, item.minutosPermiso.toString())
                bundle.putString(NUM_EMPLEADO, item.numEmp.toString())
                bundle.putString(OBSERVACIONES, item.observaciones)
                findNavController().navigate(R.id.to_permisos, bundle)
            }
        }
    }
}
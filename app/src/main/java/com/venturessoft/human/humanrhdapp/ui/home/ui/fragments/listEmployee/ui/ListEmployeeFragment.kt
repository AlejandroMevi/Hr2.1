package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.databinding.FragmentListEmployeeBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.HomeActivity.Companion.showButtonBar
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.ListEmployeeAdapter.Companion.listEmployeSelect
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.vm.ListEmployeeViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.User

class ListEmployeeFragment : Fragment(), ListEmployeeAdapter.OnClickListener {
    private lateinit var binding: FragmentListEmployeeBinding
    private lateinit var searchList: ArrayList<ItemItem>
    private val listEmployeeViewModel: ListEmployeeViewModel by activityViewModels()
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    private var mainInterface: MainInterface? = null
    private var id: Int = 0
    private var mr: Boolean = false
    companion object {
        lateinit var usuariosListaArrayResponse: java.util.ArrayList<ItemItem>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            mr = bundle.getBoolean(Constants.MR, false)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listEmployeSelect.value = null
        if (mr) {
            setDataKardex(User.listUsuTrue)
        } else {
            setDataKardex(User.listUsuFalse)
        }
        searchEmploye()
    }
    override fun onResume() {
        super.onResume()
        showButtonBar.value = true
        welcomeVM.idMenu.observe(viewLifecycleOwner) {
            mainInterface?.setTextToolbar(it.name)
            id = it.id
        }
    }
    private fun searchEmploye() {
        binding.etFilter.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                corutineEmployListService(text.toString())
            } else {
                if (mr) {
                    setDataKardex(User.listUsuTrue)
                } else {
                    setDataKardex(User.listUsuFalse)
                }
            }
        }
    }
    private fun corutineEmployListService(newText: String) {
        listEmployeeViewModel.busquedaEmpleado(
            User.token,
            User.numCia,
            User.usuario,
            size = 300,
            mr,
            newText
        )
        statusObserve()
        listEmployeeViewModel.dataBusquedaEmpleado.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val list = java.util.ArrayList<ItemItem>()
                    for (i in response.items.item.indices) {
                        val dataModel = ItemItem()
                        dataModel.nombreCompleto = response.items.item[i].nombreCompleto
                        dataModel.puesto = response.items.item[i].puesto
                        dataModel.fotografia = response.items.item[i].fotografia
                        dataModel.numEmp = response.items.item[i].numEmp
                        list.add(dataModel)
                        usuariosListaArrayResponse = list
                        searchList = usuariosListaArrayResponse
                        setDataKardex(searchList)
                    }
                }
                listEmployeeViewModel.dataBusquedaEmpleado.removeObservers(viewLifecycleOwner)
            }
        }
    }
    private fun setDataKardex(listaUsuarios: ArrayList<ItemItem>) {
        binding.listEmployee.adapter = ListEmployeeAdapter(listaUsuarios, viewLifecycleOwner, this,false)
    }
    private fun statusObserve() {
        listEmployeeViewModel.status.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        binding.progressIn.visibility = View.VISIBLE
                    }

                    is ApiResponceStatus.Success -> {
                        clearService()
                    }

                    is ApiResponceStatus.Error -> {
                        clearService()
                    }
                }
            }
        }
    }
    private fun clearService() {
        listEmployeeViewModel.status.value = null
        listEmployeeViewModel.status.removeObservers(viewLifecycleOwner)
        binding.progressIn.visibility = View.INVISIBLE
    }
    override fun onClick(item: ItemItem, position: Int) {
        welcomeVM.idMenu.value = MenuModel(
            welcomeVM.idMenu.value?.id ?: 0,
            welcomeVM.idMenu.value?.name ?: "",
            welcomeVM.idMenu.value?.resource ?: 0,
            item.puesto,
            item.numEmp,
            item.nombreCompleto,
            item.fotografia
        )
        when (id) {
            R.id.item_1 -> findNavController().navigate(R.id.to_alta_vacaciones)
            R.id.item_2 -> findNavController().navigate(R.id.to_progAnualVacFragment)
            R.id.item_3 -> findNavController().navigate(R.id.to_listaSolicitudesPeFragment)
            R.id.item_4 -> findNavController().navigate(R.id.to_listaSolicitudesPeFragment)
            R.id.item_5 -> findNavController().navigate(R.id.to_kardexAnualFragment)
            R.id.item_6 -> findNavController().navigate(R.id.to_kardexMensualFragment)
            R.id.item_8 -> findNavController().navigate(R.id.to_informacionGeneralFragment)
            R.id.item_9 -> findNavController().navigate(R.id.to_negociacionHorariosFragment)
            R.id.item_10 -> findNavController().navigate(R.id.to_entradasSalidasFragment)
            R.id.item_11 -> findNavController().navigate(R.id.to_preautorizacionTiemposFragment)
            R.id.item_12 -> findNavController().navigate(R.id.to_preautorizacionTiemposFragment)
        }
        showButtonBar.value = false
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
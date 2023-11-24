package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.databinding.FragmentGeneralStationsBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.AddStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.AddStations
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.EditStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.Station
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.vm.GeneralInformationVM
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.DialogListener
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
class GeneralStationsFragment : Fragment() {
    lateinit var binding: FragmentGeneralStationsBinding
    private val generalInformationVM: GeneralInformationVM by activityViewModels()
    private val viewModel: TimeManagementVM by activityViewModels()
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    private var mainInterface: MainInterface? = null
    private var addStationRequest = AddStationRequest()
    private var badge:Long = 0
    private var indexCatalog = 0
    private var listFilter = mutableListOf<Station>()
    private var listCatalogCombined = mutableListOf<Station>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            badge = bundle.getLong(Constants.DATA_KEY)
        }
    }
    companion object{
        var listFirstSations = mutableListOf<Station>()
        var listStations = mutableListOf<Station>()
        var listAdd = mutableListOf<Station>()
        var lisEdit = mutableListOf<Station>()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGeneralStationsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listCatalogCombined.clear()
        listFirstSations.clear()
        listStations.clear()
        listFilter.clear()
        listAdd.clear()
        lisEdit.clear()
        welcomeVM.idMenu.observeOnce(viewLifecycleOwner){ menu->
            addStationRequest.numCia = User.numCia
            addStationRequest.numEmp = menu.numEmp.toLong()
            addStationRequest.usuario = User.usuario
            addStationRequest.gafete = badge
            statusObserve(false)
            generalInformationVM.getStation(menu.numEmp.toLong(),badge)
        }
        generalInformationVM.dataResponceStation.observeOnce(viewLifecycleOwner) { listStation ->
            if (listStation?.estaciones != null) {
                listStation.estaciones.forEachIndexed { index, station ->
                    indexCatalog = index
                    station.id = indexCatalog
                    station.service = 1
                    if(station.status == "A" || station.gafete == 0L){
                        if (!listStations.any{ it.estacion == station.estacion}){
                            listStations.add(station)
                        }
                    }else{
                        if (!listFilter.any{ it.estacion == station.estacion}){
                            station.status = "B"
                            listFilter.add(station)
                        }
                    }
                    listFirstSations.add(station)
                }
                Log.i("Estacion Completa",Gson().toJson(listFirstSations))
                indexCatalog += 1
                viewModel.dataStation.observeOnce(viewLifecycleOwner) { catalogStation ->
                    if (catalogStation?.resp != null){
                        val listCatalog = mutableListOf<Station>()
                        listCatalog.clear()
                        catalogStation.resp.forEach{ catalog->
                            if (!listStations.any{ it.estacion == catalog.key}){
                                listCatalog.add(Station(id= indexCatalog,estacion = catalog.key, descripcion = catalog.value, gafete = addStationRequest.gafete, tipo = "A", service = 2, status = "B"))
                                indexCatalog += 1
                            }
                        }
                        val setCombined = HashSet(listCatalog)
                        setCombined.addAll(listFilter)
                        listCatalogCombined.addAll(setCombined.sortedBy{it.id}.distinctBy { it.estacion })
                        setAddStations()
                    }
                }
                deleteAndUndo()
            }
        }
        binding.save.button.setOnClickListener {
            showConfirmation(1, null)
        }
        searchEmploye()
    }
    internal fun setAddStations() {
        binding.miLista.adapter = StationAdapter(listStations)
        val liststationCatalogString = mutableListOf<String>()
        listCatalogCombined.forEach {
            liststationCatalogString.add(it.descripcion)
        }
        binding.btnAdd.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogCustom)
                .setTitle("Agregar Estacion")
                .setItems(liststationCatalogString.toTypedArray()) { _, which ->
                    val newStatio = Station(
                        id = listCatalogCombined[which].id,
                        estacion = listCatalogCombined[which].estacion,
                        descripcion = listCatalogCombined[which].descripcion,
                        tipo = listCatalogCombined[which].tipo,
                        gafete = listCatalogCombined[which].gafete,
                        status = "A",
                        service = listCatalogCombined[which].service
                    )
                    listStations.add(newStatio)
                    addToService(newStatio)
                    listCatalogCombined.remove(listCatalogCombined[which])
                    setAddStations()
                }.show()
        }
    }
    private fun searchEmploye() {
        binding.etFilter.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                binding.miLista.adapter = StationAdapter(listStations)
            } else {
                val listFilter = listStations.filter {
                   it.descripcion.contains(text)
                }
                binding.miLista.adapter = StationAdapter(listFilter)
            }
        }
    }
    internal fun showConfirmation(type: Int, position: Int?) {
        val title: String = when (type) {
            0 -> "¿Desea eliminar la estación?"
            1 -> "¿Desea confirmar los cambios?"
            else -> ""
        }
        Utilities.showDialog(
            title = "Aviso",
            message = title,
            positiveButtonText = "Aceptar",
            negativeButtonText = "Cancelar",
            context = requireContext(),
            listener = object : DialogListener {
                override fun onPositiveButtonClicked() {
                    when (type) {
                        0 -> {
                            position?.let {
                                val newStatio = Station(
                                    id = listStations[position].id,
                                    estacion = listStations[position].estacion,
                                    descripcion = listStations[position].descripcion,
                                    tipo = listStations[position].tipo,
                                    gafete = listStations[position].gafete,
                                    status = "B",
                                    service = listStations[position].service
                                )
                                listCatalogCombined.add(newStatio)
                                addToService(newStatio)
                                listStations.removeAt(position)
                                setAddStations()
                            }
                        }
                        1->{
                            saveStations()
                        }
                    }
                }
                override fun onNegativeButtonClicked() {
                    position?.let {
                        binding.miLista.adapter?.notifyItemChanged(position)
                    }
                }
            }
        )
    }
    internal fun addToService(newStatio: Station) {
        when (newStatio.service){
            1->{
                if (!listFirstSations.toList().contains(newStatio)){
                    try {
                        lisEdit.remove(lisEdit.find { it.estacion == newStatio.estacion})
                    }catch (_:Exception){ }
                    lisEdit.add(newStatio)
                }else{
                    try {
                        lisEdit.remove(lisEdit.find { it.estacion == newStatio.estacion})
                    }catch (_:Exception){ }
                }
                Log.i("Estacion Edit",Gson().toJson(lisEdit))
            }
            2->{
                try {
                    listAdd.remove(listAdd.find { it.estacion == newStatio.estacion})
                }catch (_:Exception){ }
                listAdd.add(newStatio)
                Log.i("Estacion Add",Gson().toJson(listAdd))
            }
        }
    }
    internal fun saveStations(){
        if(listAdd.isEmpty() && lisEdit.isEmpty()){
            Utilities.showToastyGeneral(requireContext(), "No han habido cambios")
        }else{
            statusObserve(true)
            generalInformationVM.addStations(setAddStationRequest(),setEditStationrequest())
        }
    }
    private fun setAddStationRequest():AddStationRequest{
        val listStationAdd = mutableListOf<AddStations>()
        listAdd.forEach {
            listStationAdd.add(AddStations(it.estacion,it.tipo))
        }
        addStationRequest.estaciones = listStationAdd
        return addStationRequest
    }
    private fun setEditStationrequest():List<EditStationRequest>{
        val listStationEdit = mutableListOf<EditStationRequest>()
        lisEdit.forEach {
            listStationEdit.add(EditStationRequest(it.estacion,it.tipo,it.status))
        }
        return listStationEdit
    }
    private fun statusObserve(whitFinish:Boolean) {
        generalInformationVM.status.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }
                    is ApiResponceStatus.Success -> {
                        clearService()
                        if (whitFinish){
                            val bundle = Bundle()
                            bundle.putInt(Constants.OPCION, 6)
                            findNavController().navigate(R.id.to_succesFragment, bundle)
                        }
                    }
                    is ApiResponceStatus.Error -> {
                        clearService()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.showToastyGeneral(requireContext(), errorCode)
                    }
                }
            }
        }
    }
    private fun clearService() {
        generalInformationVM.status.removeObservers(viewLifecycleOwner)
        generalInformationVM.dataResponceStation.removeObservers(viewLifecycleOwner)
        generalInformationVM.status.value = null
        generalInformationVM.dataResponceStation.value = null
        mainInterface?.showLoading(false)
    }
    private fun deleteAndUndo() {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id: Int = (viewHolder as StationAdapter.ViewHolder).bindingAdapterPosition
                if (listStations[id].gafete == 0L){
                    Utilities.showToastyGeneral(requireContext(), "No se puede modifiar esta estacion")
                    binding.miLista.adapter?.notifyItemChanged(id)
                }else{
                    showConfirmation(0, id)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.miLista)
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
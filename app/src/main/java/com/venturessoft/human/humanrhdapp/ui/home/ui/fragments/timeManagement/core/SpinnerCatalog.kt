package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.uiFragment.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.Resp
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce

class SpinnerCatalog(private var viewModel: TimeManagementVM, private val fragment: Fragment) {
    companion object {
        var rol: Int = 0
        var turno: Int = 0
        var supervisor: String? = null
        var zona: Long = 0
        var zonaSelected: Boolean? = false
        var reason: String? = null
        var permisos: String? = null
        var category: String = ""
        var categorySelected: Boolean? = false
        var calendar: Long = 0
        var departament: String = ""
        var departamentSelected: Boolean? = false
        var codid: Long? = null
        var station: String = ""
        var status: String = ""
        var grupos: String? = null
        var proceso: String = ""
    }
    fun getCatalogoRolService(spinner: AutoCompleteTextView, keyData: Int? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<Int>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add(0)
        viewModel.dataGetCatalogoRol.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.rolHorario!!.indices) {
                        arrayCatalog.add(response.rolHorario[i]?.descripcionRolHorario ?: "")
                        response.rolHorario[i]?.claveRolHorario?.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
            rol = key[keyposition]
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            rol = key[position]
        }
    }
    fun getCatalogoTurnoService(spinner: AutoCompleteTextView, keyData: Int? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<Int>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add(0)
        viewModel.dataGetCatalogoTurno.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.turno!!.indices) {
                        arrayCatalog.add(response.turno[i]?.descripcionTurno ?: "")
                        response.turno[i]?.claveTurno?.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
            turno = key[keyposition]
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            turno = key[position]
        }
    }
    fun supervisorService(spinner: MaterialAutoCompleteTextView, keyData: String? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String?>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add(null)
        viewModel.dataSupervisor.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        arrayCatalog.add(response.resp[i].value)
                        response.resp[i].key.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            supervisor = key[position]
        }
    }
    fun getCatalogoZonaService(spinner: MaterialAutoCompleteTextView, keyData: String? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add("0")
        viewModel.dataZona.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        arrayCatalog.add(response.resp[i].value)
                        response.resp[i].key.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
            zona = key[keyposition].toLong()
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            zona = key[position].toLong()
            zonaSelected = true
        }
    }
    fun getCatalogoReasons(spinner: MaterialAutoCompleteTextView, keyData: String? = null, opc : Int? = null, motivo : String ? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String?>()
        var keyposition = 0
        if (opc == 0 ) arrayCatalog.add(motivo?: fragment.getString(R.string.none)) else arrayCatalog.add(fragment.getString(R.string.none))
        key.add(null)
        viewModel.dataReasons.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        arrayCatalog.add(response.resp[i].value)
                        response.resp[i].key.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            reason = key[position]
        }
    }
    fun getCatalogoCalendar(spinner: AutoCompleteTextView, keyData: Int? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<Int>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add(0)
        viewModel.dataCalendar.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.calendario.indices) {
                        arrayCatalog.add(response.calendario[i].descripcionCalendario)
                        response.calendario[i].claveCalendario.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
            calendar = key[keyposition].toLong()
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            calendar = key[position].toLong()
        }
    }
    fun getCatalogoDepartament(spinner: AutoCompleteTextView, keyData: String? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add("")
        viewModel.dataDepartament.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        arrayCatalog.add(response.resp[i].value)
                        response.resp[i].key.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
            departament = key[keyposition]
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            departament = key[position]
            departamentSelected = true
        }
    }
    fun getCatalogoCategory(spinner: AutoCompleteTextView, keyData: String? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add("")
        viewModel.dataCategory.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        arrayCatalog.add(response.resp[i].value)
                        response.resp[i].key.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
            category = key[keyposition]
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            category = key[position]
            categorySelected = true
        }
    }
    fun codidService(spinner: MaterialAutoCompleteTextView, keyData: String? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String?>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add(null)
        viewModel.dataCodid.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        arrayCatalog.add(response.resp[i].value)
                        response.resp[i].key.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            codid = key[position]?.toLong()
        }
    }
    fun getCatalogoPermisos(spinner: MaterialAutoCompleteTextView, keyData: String? = null, opc : Int? = null, concepto : String ? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String?>()
        var keyposition = 0
        if (opc == 0 ) arrayCatalog.add(concepto?: fragment.getString(R.string.none)) else arrayCatalog.add(fragment.getString(R.string.none))
        key.add(null)
        viewModel.dataCatalogoPermisos.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        response.resp[i].value.let { arrayCatalog.add(it) }
                        response.resp[i].key.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            permisos = key[position]
        }
    }
    fun getCatalogStation(spinner: MaterialAutoCompleteTextView, keyData: String? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add("")
        viewModel.dataStation.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        arrayCatalog.add(response.resp[i].value)
                        if ( response.resp[i].value == keyData) {
                            keyposition = i + 1
                        }
                        key.add(response.resp[i].key)
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
            station = key[keyposition]
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            station = key[position]
        }
    }
    fun getCatalogoGrupos(spinner: MaterialAutoCompleteTextView, keyData: String? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String?>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add(null)
        viewModel.dataCatalogoGrupos.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        arrayCatalog.add(response.resp[i].value)
                        response.resp[i].key.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
            grupos = key[keyposition]
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            grupos = key[position]
        }
    }

    fun getCatalogoProceso(spinner: MaterialAutoCompleteTextView, keyData: String? = null) {
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add("")
        viewModel.dataCatalogoProceso.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    for (i in response.resp.indices) {
                        arrayCatalog.add(response.resp[i].value)
                        response.resp[i].key.let { it1 ->
                            if (it1 == keyData) {
                                keyposition = i + 1
                            }
                            key.add(it1)
                        }
                    }
                }
            }
            val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
            spinner.setAdapter(arrayAdap)
            spinner.setText(arrayCatalog[keyposition], false)
            proceso = key[keyposition]
        }
        spinner.setOnItemClickListener { _, _, position, _ ->
            proceso = key[position]
        }
    }
    fun getCatalogStatus(spinner: MaterialAutoCompleteTextView, keyData: String? = null) {
        val response = listOf(Resp("Aceptado","A"),Resp("Rechazado","B"))
        val arrayCatalog = ArrayList<String>()
        val key = ArrayList<String>()
        var keyposition = 0
        arrayCatalog.add(fragment.getString(R.string.none))
        key.add("")
        for (i in response.indices) {
            arrayCatalog.add(response[i].value)
            if ( response[i].key == keyData) {
                keyposition = i + 1
            }
            key.add( response[i].key)
        }
        val arrayAdap = ArrayAdapter(fragment.requireActivity(), android.R.layout.simple_spinner_dropdown_item, arrayCatalog)
        spinner.setAdapter(arrayAdap)
        spinner.setText(arrayCatalog[keyposition], false)
        status = key[keyposition]
        spinner.setOnItemClickListener { _, _, position, _ ->
            status = key[position]
        }
    }
    //Todo Area centro linea
    private var lisSpinner: List<MaterialAutoCompleteTextView> = listOf()
    private var posArea = -1
    private var posCentro = -1
    private val listArea = mutableListOf<Resp>()
    private val listCentro = mutableListOf<Resp>()
    private val listLinea = mutableListOf<Resp>()
    private val listSelection = mutableListOf<Resp>()
    private var typeAreaCentroLinea = Constants.AREA
    fun getListSelect(): List<Resp> {
        if (listSelection.size != 3) {
            for (i in listSelection.size..2) {
                listSelection.add(Resp("", ""))
            }
        }
        return listSelection
    }
    fun loadServiceArea(lisSpinnerFragment: List<MaterialAutoCompleteTextView>) {
        viewModel.getAreaCentroLinea()
        lisSpinner = lisSpinnerFragment
        typeAreaCentroLinea = Constants.AREA
        getAreaCentroLinea()
        setSpinnerData(listOf(), listCentro, lisSpinner[1])
        setSpinnerData(listOf(), listCentro, lisSpinner[2])
    }
    private fun loadSpinerArea() {
        clearArea()
        lisSpinner[0].setOnItemClickListener { _, _, position, _ ->
            if (posArea != position) {
                if (position == 0) {
                    clearArea()
                    posArea = 0
                } else {
                    if (listArea.isNotEmpty()) {
                        listSelection.clear()
                        posArea = position
                        clearArea()
                        listSelection.add(0, listArea[position])
                        loadServiceCentro(listArea[position].key)
                    }
                }
            }
        }
    }
    private fun clearArea() {
        listCentro.clear()
        listLinea.clear()
        lisSpinner[1].setText("")
        lisSpinner[2].setText("")
        lisSpinner[1].setAdapter(null)
        lisSpinner[2].setAdapter(null)
        posCentro = -1
        listSelection.clear()
        setSpinnerData(listOf(), listCentro, lisSpinner[1])
        setSpinnerData(listOf(), listLinea, lisSpinner[2])
    }
    private fun loadServiceCentro(keyArea: String) {
        typeAreaCentroLinea = Constants.CENTRO
        viewModel.getAreaCentroLinea(keyArea)
        getAreaCentroLinea(keyArea)
    }
    private fun loadSpinerCentro(keyArea: String?) {
        lisSpinner[1].setOnItemClickListener { _, _, position, _ ->
            if (posCentro != position) {
                if (position == 0) {
                    clearCentro()
                    posCentro = 0
                } else {
                    if (listCentro.isNotEmpty()) {
                        posCentro = position
                        clearCentro()
                        if (listSelection.size >= 2) {
                            listSelection.removeAt(1)
                        }
                        listSelection.add(1, listCentro[position])
                        loadServiceLinea(keyArea, listCentro[position].key)
                    }
                }
            }
        }
    }
    private fun clearCentro() {
        listLinea.clear()
        lisSpinner[2].setText("")
        lisSpinner[2].setAdapter(null)
        if (listSelection.size >= 3) {
            listSelection.removeAt(2)
        }
        if (listSelection.size >= 2) {
            listSelection.removeAt(1)
        }
        setSpinnerData(listOf(), listLinea, lisSpinner[2])
    }
    private fun loadServiceLinea(keyArea: String?, keyCentro: String) {
        typeAreaCentroLinea = Constants.LINEA
        viewModel.getAreaCentroLinea(keyArea, keyCentro)
        getAreaCentroLinea()
    }
    private fun loadSpinerLinea() {
        lisSpinner[2].setOnItemClickListener { _, _, position, _ ->
            if (listLinea.isNotEmpty()) {
                if (listSelection.size >= 3) {
                    listSelection.removeAt(2)
                }
                listSelection.add(2, listLinea[position])
            }
        }
    }
    private fun getAreaCentroLinea(keyArea: String? = null) {
        viewModel.dataAreaCentroLinea.observeOnce(fragment) { response ->
            if (response != null) {
                when (typeAreaCentroLinea) {
                    Constants.AREA -> {
                        setSpinnerData(response.resp, listArea, lisSpinner[0])
                        loadSpinerArea()
                    }
                    Constants.CENTRO -> {
                        setSpinnerData(response.resp, listCentro, lisSpinner[1])
                        loadSpinerCentro(keyArea)
                    }
                    Constants.LINEA -> {
                        setSpinnerData(response.resp, listLinea, lisSpinner[2])
                        loadSpinerLinea()
                    }
                }
            }
        }
    }
    private fun setSpinnerData(resp: List<Resp>, list: MutableList<Resp>, spinner: MaterialAutoCompleteTextView) {
        list.clear()
        val listSpinner = mutableListOf<String>()
        list.add(Resp("", ""))
        listSpinner.add(fragment.getString(R.string.none))
        for (i in resp.indices) {
            list.add(resp[i])
            listSpinner.add(resp[i].value)
        }
        if (list.isEmpty()) {
            Toast.makeText(fragment.requireContext(), fragment.getString(R.string.sin_infomacion), Toast.LENGTH_SHORT).show()
        } else {
            val arrayAdp = ArrayAdapter(fragment.requireContext(), android.R.layout.simple_list_item_1, listSpinner)
            spinner.setAdapter(arrayAdp)
            spinner.setText(listSpinner[0], false)
        }
    }
}
package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.reports

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.FragmentReportesBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.HomeActivity.Companion.showButtonBar
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.MenuReportsAcontrolAdapter
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.User

class ReportesFragment : Fragment(), MenuReportsAcontrolAdapter.OnClickListener {
    private lateinit var binding: FragmentReportesBinding
    private var mainInterface: MainInterface? = null
    private var menuModel = MenuModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        arguments?.let {bundle->
            menuModel = bundle.getSerializable(Constants.MENU_KEY) as MenuModel
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentReportesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataMenu()
    }
    private fun setDataMenu() {
        val data = listOf(
            getString(R.string.ATentradas_salidas),
            getString(R.string.ATentradas_salidas_x_concepto),
            getString(R.string.ATtarjeta_empleados),
            getString(R.string.ATAusentismo),
            getString(R.string.ATHoras_laboradas),
            getString(R.string.ATAsistencia_Organigrama),
            getString(R.string.ATControl_Asistencia),
            getString(R.string.ATDias_por_Disfrutar),
            getString(R.string.ATSeguridad_Vigilancia)
        )
        binding.listView.adapter = MenuReportsAcontrolAdapter(data,this)
    }
    override fun onResume() {
        super.onResume()
        showButtonBar.value = true
        mainInterface?.setTextToolbar(getString(R.string.menu_title))
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

    override fun onClick(item: String) {
        showButtonBar.value = false
        val bundle = Bundle()
        bundle.putString(Constants.STRING_KEY,item)
        findNavController().navigate(R.id.to_reporteAdministracionTIemposFragment,bundle)
    }
}
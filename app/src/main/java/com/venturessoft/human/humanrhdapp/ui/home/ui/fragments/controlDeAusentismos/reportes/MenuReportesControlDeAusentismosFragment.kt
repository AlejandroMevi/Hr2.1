package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.FragmentMenuReportesControlDeAusentismosBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.HomeActivity.Companion.showButtonBar
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.STRING_KEY
import com.venturessoft.human.humanrhdapp.utilis.complements.User

class MenuReportesControlDeAusentismosFragment : Fragment(),
    MenuReportsAcontrolAdapter.OnClickListener {

    private lateinit var binding: FragmentMenuReportesControlDeAusentismosBinding
    private var mainInterface: MainInterface? = null
    private var menuModel = MenuModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {bundle->
            menuModel = bundle.getSerializable(Constants.MENU_KEY) as MenuModel
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMenuReportesControlDeAusentismosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataMenu()
    }
    private fun setDataMenu() {
        val data = listOf(getString(R.string.menu_vacaciones), getString(R.string.menu_incapacidades), getString(R.string.menu_ausentismos), getString(R.string.menu_permisos))
        binding.listView.adapter = MenuReportsAcontrolAdapter(data,this)
        binding.listView.isClickable = true
    }
    override fun onResume() {
        super.onResume()
        mainInterface?.setTextToolbar(getString(R.string.menu_title))
        showButtonBar.value = true
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
        val bundle = Bundle()
        bundle.putString(STRING_KEY,item)
        findNavController().navigate(R.id.to_reportAbsenteeismControlFragment,bundle)
        showButtonBar.value = false
    }
}
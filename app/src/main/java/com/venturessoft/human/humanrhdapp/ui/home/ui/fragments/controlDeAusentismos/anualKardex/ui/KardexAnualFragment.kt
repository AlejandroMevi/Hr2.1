package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.HeaderUser
import com.venturessoft.human.humanrhdapp.databinding.FragmentKardexAnualBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.ui.vm.KardexAnualViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
import java.time.LocalDate

class KardexAnualFragment : Fragment() {

    private lateinit var binding: FragmentKardexAnualBinding
    private val welcomeFragmentViewModel: WelcomeFragmentViewModel by activityViewModels()
    private val kardexAnualViewModel: KardexAnualViewModel by viewModels()
    private var mainInterface: MainInterface? = null
    private var yearSelected = LocalDate.now().year
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentKardexAnualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView(){
        welcomeFragmentViewModel.idMenu.observe(viewLifecycleOwner) { dataUser->
            HeaderUser(requireContext(),dataUser,binding.headerUser)

            val responce = mutableListOf<KardexAnualRequest>()
            for (i in 1..3){
                responce.add(KardexAnualRequest(User.numCia.toString(), dataUser.numEmp, yearSelected.toString(), motivo = "Todos"))
                yearSelected -= 1
            }
            getData(responce)
            kardexAnualViewModel.dataKardexYear.observeOnce(viewLifecycleOwner){kardex->
                if (kardex != null){
                    yearSelected = LocalDate.now().year
                    val myAdapter = KardexAnualViewPagerAdapternb(childFragmentManager, lifecycle)
                    kardex.kardex.forEachIndexed { index, _ ->
                        myAdapter.addFragment(KardexYeayFragment(kardex.kardex[index],kardex.holydays[index],kardex.daysoff[index],yearSelected))
                        yearSelected -= 1
                    }
                    binding.vpCardex.adapter = myAdapter
                    clearService()
                }
            }
        }
    }
    private fun statusObserve() {
        kardexAnualViewModel.status.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }
                    is ApiResponceStatus.Success -> {
                    }
                    is ApiResponceStatus.Error -> {
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Toast.makeText(requireActivity(), errorCode, Toast.LENGTH_SHORT).show()
                        clearService()
                    }
                }
            }
        }
    }
    private fun clearService() {
        kardexAnualViewModel.status.value = null
        kardexAnualViewModel.dataKardexYear.removeObservers(viewLifecycleOwner)
        kardexAnualViewModel.status.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }
    private fun getData(request: MutableList<KardexAnualRequest>){
        kardexAnualViewModel.getKardexAnual(User.token, request,LocalDate.now().year)
        statusObserve()
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
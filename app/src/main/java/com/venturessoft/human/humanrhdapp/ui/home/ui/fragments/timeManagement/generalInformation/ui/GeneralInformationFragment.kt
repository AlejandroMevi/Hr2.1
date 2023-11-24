package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.databinding.FragmentGeneralInformationBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.ListaMaestroReloj
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.vm.GeneralInformationVM
import com.venturessoft.human.humanrhdapp.utilis.StickyHeader.StickyHeaderDecoration
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce

class GeneralInformationFragment : Fragment(){
    lateinit var binding: FragmentGeneralInformationBinding
    private var mainInterface: MainInterface? = null
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    private val generalInformationVM: GeneralInformationVM by activityViewModels()
    private lateinit var bookAdapter : GeneralInformationAgrupedAdaper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGeneralInformationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        welcomeVM.idMenu.observeOnce(viewLifecycleOwner){ user->
            generalInformationVM.getMR(user.numEmp.toLong())
            statusObserve()
        }
        binding.floating.setOnClickListener {
            findNavController().navigate(R.id.to_generalInformationDetailsFragment)
        }

        binding.rvGeneralInformation.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0){
                    binding.floating.shrink()
                } else {
                    binding.floating.extend()
                }
            }
        })

        generalInformationVM.dataMR.observeOnce(viewLifecycleOwner){ dataResponce->
            if (dataResponce?.listaMaestroReloj != null){
                if (dataResponce.listaMaestroReloj.isNotEmpty()){
                    val listData = dataResponce.listaMaestroReloj
                    bookAdapter = GeneralInformationAgrupedAdaper()
                    val groupedBooks: Map<String, List<ListaMaestroReloj>> = listData.groupBy { bookData ->
                        Utilities.formatYear(bookData.fechaAplicacion)?:""
                    }
                    bookAdapter.bookData = groupedBooks.toSortedMap(compareByDescending { it })
                    binding.rvGeneralInformation.addItemDecoration(StickyHeaderDecoration(bookAdapter, binding.root))
                    binding.rvGeneralInformation.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvGeneralInformation.adapter = bookAdapter
                    binding.tvDataEmpty.root.isVisible = false
                    binding.rvGeneralInformation.isVisible = true
                }else{
                    binding.tvDataEmpty.root.isVisible = true
                    binding.rvGeneralInformation.isVisible = false
                }
            }else{
                binding.tvDataEmpty.root.isVisible = true
                binding.rvGeneralInformation.isVisible = false
            }
        }
    }
    private fun statusObserve() {
        generalInformationVM.status.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
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
        generalInformationVM.status.removeObservers(viewLifecycleOwner)
        generalInformationVM.dataMR.removeObservers(viewLifecycleOwner)
        generalInformationVM.status.value = null
        mainInterface?.showLoading(false)
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
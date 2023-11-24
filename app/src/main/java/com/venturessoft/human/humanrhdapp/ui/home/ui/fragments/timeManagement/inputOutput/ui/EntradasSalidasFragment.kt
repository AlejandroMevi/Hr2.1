package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.AnimationExpanded
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.databinding.FragmentEntradasSalidas2Binding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.DateIndicator
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.SwipeToDeleteCallback
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutput
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.ui.vm.InputOutputVM
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.COUNTRYES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.DATA_KEY
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.LENGUAGEes
import com.venturessoft.human.humanrhdapp.utilis.complements.DialogListener
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EntradasSalidasFragment : Fragment(), InputOutputAdapter.OnClickListener {

    private lateinit var binding: FragmentEntradasSalidas2Binding
    private val calendar: Calendar = Calendar.getInstance(Locale(LENGUAGEes, COUNTRYES))
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    internal val inputOutputVM: InputOutputVM by activityViewModels()
    private var inputOutputRequest = InputOutputRequest()
    private var mainInterface: MainInterface? = null
    private var isPeriod = false
    private var isExpanded = false
    private var listInputOutput = mutableListOf<InputOutput>()
    private var userData = MenuModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntradasSalidas2Binding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dateIndicator = DateIndicator(requireContext(), binding.btnDate)
        dateIndicator.setDate(calendar.get(Calendar.MONTH)).toString()
        statusObserve()
        welcomeVM.idMenu.observeOnce(viewLifecycleOwner) { user ->
            userData = user
            inputOutputRequest.cia = User.numCia.toString()
            inputOutputRequest.usuario = User.usuario
            inputOutputRequest.numEmp = userData.numEmp
            inputOutputRequest.periodo = false
            inputOutputRequest.mes = (calendar.get(Calendar.MONTH) + 1).toString()
            inputOutputRequest.anio = calendar.get(Calendar.YEAR).toString()
            inputOutputVM.getInputOutput(inputOutputRequest)
        }
        inputOutputVM.dataInputOutput.observe(viewLifecycleOwner) { data ->
            if (data?.slista != null && data.slista.isNotEmpty()) {
                listInputOutput.clear()
                listInputOutput.addAll(data.slista)
                binding.tvDataEmpty.root.visibility = View.INVISIBLE
                binding.rvInputOutput.visibility = View.VISIBLE
                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale(LENGUAGEes, COUNTRYES))
                listInputOutput.sortByDescending {
                    it.fecha.let { it1 -> dateFormat.parse(it1) }
                }
                binding.rvInputOutput.adapter = InputOutputAdapter(listInputOutput, this)
                deleteAndUndo()
            } else {
                binding.tvDataEmpty.root.visibility = View.VISIBLE
                binding.rvInputOutput.visibility = View.INVISIBLE
            }
        }
        binding.btnDate.setOnClickListener {
            dateIndicator.createDialogWithoutDateField()
        }
        binding.floating.setOnClickListener {
            findNavController().navigate(R.id.to_inputOutputDettailsFragment)
        }
        binding.rgType.setOnCheckedChangeListener { radioGroup, _ ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.rb_month -> isPeriod = false
                R.id.rb_period -> isPeriod = true
            }
        }
        binding.btnFilter.setOnClickListener {
            inputOutputRequest.cia = User.numCia.toString()
            inputOutputRequest.numEmp = userData.numEmp
            inputOutputRequest.periodo = isPeriod
            inputOutputRequest.mes = (DateIndicator.monthData + 1).toString()
            inputOutputRequest.anio = DateIndicator.yearData.toString()
            inputOutputVM.getInputOutput(inputOutputRequest)
        }
        binding.rvInputOutput.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.floating.shrink()
                    if (isExpanded) {
                        val show: Boolean =
                            toggleLayout(false, binding.viewMoreBtn, binding.layoutExpand)
                        isExpanded = show
                    }
                } else {
                    binding.floating.extend()
                }
            }
        })
        binding.cardviewlista.setOnClickListener {
            val show: Boolean = toggleLayout(!isExpanded, binding.viewMoreBtn, binding.layoutExpand)
            isExpanded = show
        }
    }
    internal fun toggleLayout(isExpanded: Boolean, v: View, layoutExpand: View): Boolean {
        AnimationExpanded().toggleArrow(v, isExpanded)
        if (isExpanded) {
            AnimationExpanded().expand(layoutExpand)
        } else {
            AnimationExpanded().collapse(layoutExpand)
        }
        return isExpanded
    }
    override fun onClick(inputOutput: InputOutput) {
        val bundle = Bundle()
        bundle.putSerializable(DATA_KEY, inputOutput)
        findNavController().navigate(R.id.to_inputOutputDettailsFragment, bundle)
    }
    internal fun statusObserve() {
        inputOutputVM.status.observe(viewLifecycleOwner) { status ->
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
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.showToastyGeneral(requireContext(), errorCode)
                    }
                }
            }
        }
    }
    private fun clearService() {
        inputOutputVM.dataEntradasSalidas.removeObservers(viewLifecycleOwner)
        inputOutputVM.status.removeObservers(viewLifecycleOwner)
        inputOutputVM.status.value = null
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
    private fun deleteAndUndo() {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id: Int = (viewHolder as InputOutputAdapter.ViewHolder).absoluteAdapterPosition
                showConfirmation(listInputOutput[id], id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvInputOutput)
    }
    internal fun showConfirmation(inputOutput: InputOutput, position: Int) {
        Utilities.showDialog(
            title = "Aviso",
            message = "Desea eliminar el registro?",
            positiveButtonText = "Aceptar",
            negativeButtonText = "Cancelar",
            context = requireContext(),
            listener = object : DialogListener {
                override fun onPositiveButtonClicked() {
                    var str = inputOutput.fecha
                    val target = '/'
                    str = str.replace(target, '-')
                    inputOutputVM.dataEntradasSalidas.value = null
                    inputOutputVM.deleteInputOutput(
                        User.numCia.toString(),
                        userData.numEmp,
                        str,
                        inputOutput.sec.toString(),
                        User.usuario
                    )
                    statusObserve()
                    inputOutputVM.dataEntradasSalidas.observeOnce(viewLifecycleOwner) { response ->
                        if (response != null) {
                            if (response.codigo == "0") {
                                listInputOutput.removeAt(position)
                                binding.rvInputOutput.adapter?.notifyItemRemoved(position)
                            } else {
                                binding.rvInputOutput.adapter?.notifyItemChanged(position)
                            }
                        } else {
                            binding.rvInputOutput.adapter?.notifyItemChanged(position)
                        }
                    }
                }
                override fun onNegativeButtonClicked() {
                    binding.rvInputOutput.adapter?.notifyItemChanged(position)
                }
            }
        )
    }
}
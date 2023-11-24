package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.welcome

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.Calendario
import com.venturessoft.human.humanrhdapp.databinding.FragmentWelcomeBinding
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.vm.ListEmployeeViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.COUNTRYES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.LENGUAGEes
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
import java.text.SimpleDateFormat
import java.util.*

class WelcomeFragment : Fragment(), OnChartValueSelectedListener {
    private lateinit var binding: FragmentWelcomeBinding
    private val welcomeFragmentViewModel: WelcomeFragmentViewModel by activityViewModels()
    private val w: WelcomeFragmentViewModel by activityViewModels()
    private val listEmployeeViewModel: ListEmployeeViewModel by activityViewModels()
    private var mainInterface: MainInterface? = null
    private var ca: Calendario = Calendario()
    private var times = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        corutineDashBoardService()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCustomization()
        setDataKardex(User.listUsuFalse)
        addObservers()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainInterface) {
            mainInterface = context
        }
    }

    override fun onResume() {
        super.onResume()
        mainInterface?.setTextToolbar("")
        w.idMenu.value = null
    }

    override fun onDetach() {
        super.onDetach()
        mainInterface = null
    }

    private fun setDataKardex(listaUsuarios: ArrayList<ItemItem>) {
        binding.listEmployee.adapter =
            ListEmployeeRetardosAdapter(listaUsuarios)
    }

    @SuppressLint("SetTextI18n")
    private fun viewCustomization() {
        val mes =
            ca.barCMonth(requireContext(), Calendar.getInstance().get(Calendar.MONTH) + 1)

        binding.welcomeName.text = User.razonSocial
        binding.welcomeEstadistica.text = "${getString(R.string.welcome_est)} ${
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        }/$mes - ${
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 5
        }/$mes"
        binding.btnRefresh.setOnClickListener {
            Utilities.vibrate(requireContext())
            val animation = RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            animation.duration = 1000
            animation.repeatCount = Animation.INFINITE
            binding.btnRefresh.startAnimation(animation)
            corutineDashBoardService()
            addObservers()
        }
    }

    private fun corutineDashBoardService() {
        welcomeFragmentViewModel.dataDashBoard.value = null
        welcomeFragmentViewModel.dashBoard(User.token, User.usuario, 7, User.numCia, true)
    }

    private fun addObservers(){
        welcomeFragmentViewModel.dataDashBoard.observeOnce(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    binding.btnRefresh.clearAnimation()
                    val dias = ArrayList<String>()
                    val asistencia = ArrayList<Float>()
                    val asistenciaEmp = ArrayList<Double>()
                    for (i in response.entradasSalidas!!.indices) {
                        response.entradasSalidas[i]?.fecha?.let { dias.add(it) }
                        response.entradasSalidas[i]?.asistencia?.let { asistencia.add(it) }
                        asistenciaEmp.add(
                            response.entradasSalidas[i]?.asistencia.toString().toDouble()
                        )
                    }
                    loadBarChart(dias, asistencia, asistenciaEmp)
                }
            }
        }
        statusObserveDashBoard()
    }
    private fun statusObserveDashBoard() {
        welcomeFragmentViewModel.statusDashBoard.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        //binding.btnRefresh.clearAnimation()
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceDashBoard()
                        binding.btnRefresh.clearAnimation()
                    }

                    is ApiResponceStatus.Error -> {
                        clearServiceDashBoard()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.showToastyGeneral(requireContext(), errorCode)
                    }
                }
            }
        }
    }

    private fun clearServiceDashBoard() {
        mainInterface?.showLoading(false)
        binding.btnRefresh.clearAnimation()
        welcomeFragmentViewModel.statusDashBoard.removeObservers(viewLifecycleOwner)
        welcomeFragmentViewModel.statusDashBoard.value = null
        welcomeFragmentViewModel.dataDashBoard.removeObservers(viewLifecycleOwner)
    }

    private fun loadBarChart(
        dias: ArrayList<String>,
        asistencia: ArrayList<Float>,
        asistenciaEmp: ArrayList<Double>
    ) {
        val noOfEmp = ArrayList<BarEntry>()
        val year = ArrayList<String>()
        val dateFormatOrigin = SimpleDateFormat("yyyy-MM-dd", Locale(LENGUAGEes, COUNTRYES))
        val dateFormatTarget = SimpleDateFormat("dd/MM", Locale(LENGUAGEes, COUNTRYES))
        for (i in 0..6) {
            noOfEmp.add(BarEntry(asistencia[i], i, asistenciaEmp[i]))
            val dtStart = dias[i]
            val date = dateFormatOrigin.parse(dtStart)
            date?.let { dateFormatTarget.format(it) }?.let { year.add(it) }
        }
        val bardataset = BarDataSet(noOfEmp, getString(R.string.asistencia))
        if (times == 0 ){
            binding.graficChard.animateY(5000)
            times = 1
        }
        binding.graficChard.setVisibleXRangeMaximum(7f)
        binding.graficChard.setVisibleYRangeMaximum(100f, YAxis.AxisDependency.LEFT)
        binding.graficChard.axisLeft.setLabelCount(5, true)
        binding.graficChard.axisLeft.mAxisRange = 25f
        binding.graficChard.axisLeft.setAxisMinValue(0f)
        binding.graficChard.axisLeft.setAxisMaxValue(100f)
        binding.graficChard.axisRight.setLabelCount(5, true)
        binding.graficChard.axisRight.mAxisRange = 25f
        binding.graficChard.axisRight.setAxisMinValue(0f)
        binding.graficChard.axisRight.setAxisMaxValue(100f)
        binding.graficChard.xAxis.setLabelsToSkip(0)
        binding.graficChard.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.graficChard.isScaleXEnabled = false
        binding.graficChard.isScaleYEnabled = false
        val colors = intArrayOf(
            Color.rgb(115, 98, 236)
        )
        binding.graficChard.axisRight.isEnabled = false
        val l = binding.graficChard.legend
        l.formSize = 10f
        l.form = Legend.LegendForm.CIRCLE
        l.setCustom(colors, arrayOf(getString(R.string.porcentaje_de_asistencia)))
        binding.graficChard.legend.textSize = 10F
        binding.graficChard.setDescription("")
        val data = BarData(year, bardataset)
        val color =
            ContextCompat.getColor(requireActivity().applicationContext, R.color.chart_color_back)
        val highColor =
            ContextCompat.getColor(requireActivity().applicationContext, R.color.chart_color)
        val textColor =
            ContextCompat.getColor(requireActivity().applicationContext, R.color.chart_color_text)
        val textColorhigh =
            ContextCompat.getColor(requireActivity().applicationContext, R.color.puesto)
        binding.graficChard.notifyDataSetChanged()
        bardataset.color = color
        bardataset.highLightColor = highColor
        bardataset.valueTextColor = textColor
        val listColor = mutableStateListOf<Int>()
        listColor.add(textColor)
        listColor.add(textColor)
        listColor.add(textColor)
        listColor.add(textColor)
        listColor.add(textColor)
        listColor.add(textColorhigh)
        listColor.add(textColor)
        bardataset.setValueTextColors(listColor)
        binding.graficChard.data = data
        binding.graficChard.data.setValueFormatter(PercentFormatter())
        binding.graficChard.highlightValue(5, 0)
        binding.graficChard.notifyDataSetChanged()
        binding.graficChard.setOnChartValueSelectedListener(this)
    }

    override fun onValueSelected(e: Entry?, dataSetIndex: Int, h: Highlight?) {
        binding.graficChard.highlightValue(h)
        if (e != null) {
            Utilities.showErrorDialog(
                getString(R.string.hay) + " " + e.data.toString() + "%" + " " + getString(
                    R.string.empleados_asistencia
                ),
                requireActivity()
            )
        }
    }

    override fun onNothingSelected() {}

    override fun onPause() {
        super.onPause()
        listEmployeeViewModel.status.removeObservers(viewLifecycleOwner)
    }


}
package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.FragmentSuccesBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.HomeActivity.Companion.progressIsShow
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
class SuccesFragment : Fragment() {

    private lateinit var binding: FragmentSuccesBinding
    private var mainInterface: MainInterface? = null
    private var opc: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            opc = bundle.getInt(Constants.OPCION, 0)
        }
        progressIsShow = true
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSuccesBinding.inflate(inflater, container, false)
        mainInterface?.showLottie(false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        progressIsShow = false
    }
    private fun initView(){
        binding.lottieSuccess.setAnimation("lottieanimation/checkmark-.json")
        binding.lottieSuccess.playAnimation()
        binding.lottieSuccess.repeatMode
        Handler(Looper.getMainLooper()).postDelayed({ backView() }, Constants.SUCCES_ANIMOTIONLOTTIE_TIMEOUT)
    }
    private fun backView(){
        when(opc){
            1 -> findNavController().navigate(R.id.to_alta_vacaciones)
            2 -> findNavController().navigate(R.id.to_progAnualVacFragment)
            33 -> findNavController().navigate(R.id.to_vacationAuthorizationFragment)
            3 -> findNavController().navigate(R.id.to_listaSolicitudesPeFragment)
            4 -> findNavController().navigate(R.id.to_listaSolicitudesPeFragment)
            5 -> findNavController().navigate(R.id.to_entradasSalidasFragment)
            55 -> findNavController().navigate(R.id.to_entradasSalidasMasivasFragment)
            6 -> findNavController().navigate(R.id.to_informacionGeneralFragment)
            7 -> findNavController().navigate(R.id.to_preautorizacionTiemposFragment)
            8 -> findNavController().navigate(R.id.to_negociacionHorariosFragment)
            else -> findNavController().navigate(R.id.to_listEmployeeFragment)
        }
        mainInterface?.showLottie(true)
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
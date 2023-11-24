package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.about

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.venturessoft.human.humanrhdapp.BuildConfig
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.FragmentAboutUsBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface

class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding
    private var mainInterface: MainInterface? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        binding.tvVersion.text = BuildConfig.VERSION_NAME
        binding.tvCompilation.text = BuildConfig.VERSION_CODE.toString()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCustomization()
    }
    private fun viewCustomization(){
        mainInterface?.setTextToolbar(getString(R.string.submenu_13))
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
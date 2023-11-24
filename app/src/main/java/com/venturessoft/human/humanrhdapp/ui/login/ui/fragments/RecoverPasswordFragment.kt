package com.venturessoft.human.humanrhdapp.ui.login.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.venturessoft.human.humanrhdapp.ui.login.LoginInterface
import com.venturessoft.human.humanrhdapp.ui.login.ui.vm.LoginActivityViewModel
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.databinding.FragmentRecoverPasswordBinding
import com.venturessoft.human.humanrhdapp.utilis.complements.DialogListener
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities

class RecoverPasswordFragment : Fragment() {

    private lateinit var binding: FragmentRecoverPasswordBinding
    private var loginInterface: LoginInterface? = null
    private val loginActivityViewModel: LoginActivityViewModel by activityViewModels()
    private var email: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecoverPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun initializeView(){
        with(binding){
            recoverButton.setOnClickListener{ clickPassword() }
        }
    }
    private fun clickPassword() {
        email = binding.emailRecover.editText?.text.toString().trim()
        val checkFieldsMessage = checkFields()
        if (checkFieldsMessage == "true") {
            corutineServiceForgotNip()
        } else {
            Toast.makeText(requireContext(), checkFieldsMessage, Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkFields(): String {
        if (email.isEmpty()) {
            return getString(R.string.a_login_error_email_empty)
        }
        return "true"
    }

    private fun corutineServiceForgotNip(){
        loginActivityViewModel.getForgotnNip(email)
        statusObserve()
        loginActivityViewModel.dataForgotNip.observe(viewLifecycleOwner){ response ->
            if (response != null) {
                if (response.resultado == "0"){
                    Utilities.showDialog(
                        title = getString(R.string.fna_title_dialog),
                        message = getString(R.string.fna_message_dialog),
                        positiveButtonText = getString(R.string.fna_positive),
                        context = requireContext(),
                        listener = object : DialogListener {
                            override fun onPositiveButtonClicked() {
                                requireActivity().onBackPressedDispatcher.onBackPressed()
                            }
                            override fun onNegativeButtonClicked() {}
                        })
                }
            }
        }
    }
    private fun statusObserve(){
        loginActivityViewModel.status.observe(viewLifecycleOwner){status->
            if (status != null){
                when(status){
                    is ApiResponceStatus.Loading -> {
                        loginInterface?.showLoading(true)
                        Log.i("Progress",requireContext().javaClass.name)
                    }
                    is ApiResponceStatus.Success -> {
                        clearService()
                    }
                    is ApiResponceStatus.Error -> {
                        clearService()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.showDialog(title = getString(R.string.fna_title_dialog), message = errorCode,
                            positiveButtonText = getString(R.string.fna_positive), context = requireContext(),
                            listener = object : DialogListener {
                                override fun onPositiveButtonClicked() {
                                    requireActivity().onBackPressedDispatcher.onBackPressed()
                                }
                                override fun onNegativeButtonClicked() {}
                            })
                    }
                }
            }
        }
    }
    private fun clearService(){
        loginActivityViewModel.status.value = null
        loginActivityViewModel.dataForgotNip.removeObservers(this)
        loginActivityViewModel.status.removeObservers(this)
        loginInterface?.showLoading(false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginInterface) {
            loginInterface = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        loginInterface = null
    }
}
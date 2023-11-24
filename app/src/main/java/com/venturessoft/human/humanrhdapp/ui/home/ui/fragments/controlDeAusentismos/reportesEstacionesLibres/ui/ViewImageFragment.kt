package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.venturessoft.human.humanrhdapp.databinding.FragmentViewImageBinding
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.base64StringToBitmap

class ViewImageFragment(private val photo: String? = null) : DialogFragment() {

    private lateinit var binding: FragmentViewImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        toolbar()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window!!.setLayout(width, height)
        }
    }
    private fun toolbar() {
        with(binding) {
            toolbar.setNavigationOnClickListener { dismiss() }
        }
    }

    private fun loadData() {
        if (photo != null) {
            val photoBitmap = base64StringToBitmap(photo)
            binding.idImg.setImageBitmap(photoBitmap)
        }

    }
}
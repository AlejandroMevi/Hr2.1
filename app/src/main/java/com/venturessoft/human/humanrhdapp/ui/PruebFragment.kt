package com.venturessoft.human.humanrhdapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.venturessoft.human.humanrhdapp.databinding.FragmentPruebBinding

private lateinit var binding: FragmentPruebBinding
private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

class PruebFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPruebBinding.inflate(inflater, container, false)
        bottom()
        binding.bottom.previusMonth.setOnClickListener {
            Toast.makeText(requireActivity(), "Previus", Toast.LENGTH_SHORT).show() }
        binding.bottom.nextMonth.setOnClickListener {
            Toast.makeText(requireActivity(), "Next", Toast.LENGTH_SHORT).show() }
        return binding.root
    }


    private fun bottom() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottom.bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> collapsed()

                    BottomSheetBehavior.STATE_EXPANDED -> expanded()

                    BottomSheetBehavior.STATE_DRAGGING -> dragging()

                    BottomSheetBehavior.STATE_SETTLING -> println("STATE_SETTLING")

                    BottomSheetBehavior.STATE_HIDDEN -> println("STATE_HIDDEN")

                    else -> {
                        println("OTHER_STATE")
                    }
                }
            }
        })

        binding.btnAccion.setOnClickListener {

        }
    }

    internal fun collapsed() {
        binding.bottom.collapsed.isVisible = true
    }

    internal fun dragging() {
        binding.bottom.collapsed.isVisible = true
    }

    internal fun expanded() {
        binding.bottom.collapsed.isVisible = false
    }

}
package com.venturessoft.human.humanrhdapp.utilis.complements

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.venturessoft.human.humanrhdapp.R

class GenericDialog : Dialog {

    private var mListener: DialogListener? = null
    private var title: String
    private var message: String
    private var positiveButtonText: String
    private var negativeButtonText: String? = null
    private var layout = -1
    private val button2 = R.layout.view_generic_dialog_2_button
    private val button1 = R.layout.view_generic_dialog_1_button
    constructor(title: String, message: String, positiveButtonText: String, context: Context, listener: DialogListener?) : super(context) {
        mListener = listener
        this.title = title
        this.message = message
        this.positiveButtonText = positiveButtonText
    }
    constructor(title: String, message: String, positiveButtonText: String, negativeButtonText: String, context: Context, listener: DialogListener?) : super(context) {
        mListener = listener
        this.title = title
        this.message = message
        this.positiveButtonText = positiveButtonText
        this.negativeButtonText = negativeButtonText
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        layout = if (negativeButtonText != null) button2 else button1
        setContentView(layout)
        val titleView = findViewById<TextView>(R.id.dialogTitle)
        val messageView = findViewById<TextView>(R.id.dialogMessage)
        val positiveButton = findViewById<Button>(R.id.dialogPositiveButton)
        val negativeButton = findViewById<Button>(R.id.dialogNegativeButton)
        titleView.text = title
        messageView.text = message
        positiveButton.setOnClickListener {
            mListener?.onPositiveButtonClicked()
            dismiss()
        }
        negativeButton?.setOnClickListener {
            mListener?.onNegativeButtonClicked()
            dismiss()
        }
    }
}

interface DialogListener {
    fun onPositiveButtonClicked()
    fun onNegativeButtonClicked()
}


package com.venturessoft.human.humanrhdapp.core

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.core.content.ContextCompat
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.GeneralHeaderUserBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
class HeaderUser(
    private val context: Context,
    private val dataUser: MenuModel,
    private val  headerUser: GeneralHeaderUserBinding
) {
    init {
        setData()
    }
    private fun setData(){
        val numero = dataUser.numEmp.toLong()
        if (dataUser.fotografia.contains("File not foundjava", ignoreCase = true) || dataUser.fotografia.isEmpty()) {
            headerUser.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_icon_big))
        } else {
            val imagenBase64 = Base64.decode(dataUser.fotografia, Base64.DEFAULT)
            val imagenconverBitmap = BitmapFactory.decodeByteArray(imagenBase64, 0, imagenBase64.size)
            headerUser.image.setImageBitmap(imagenconverBitmap)
        }
        headerUser.position.text = if (dataUser.puesto == "null") "" else dataUser.puesto
        headerUser.name.text = dataUser.nombreCompleto
        headerUser.idEmploye.text = numero.toString()
    }
}
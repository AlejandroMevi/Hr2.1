package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data

import android.content.Context
import com.venturessoft.human.humanrhdapp.R
class MenuData(private val context: Context) {
    fun setAssistControl():List<MenuModel>{
        val listAssistControl = mutableListOf<MenuModel>()
        listAssistControl.add(MenuModel(1, context.getString(R.string.submenu_1),R.drawable.icono_vacaciones_8__1_))
        listAssistControl.add(MenuModel(2, context.getString(R.string.submenu_2),R.drawable.icono_vacaciones_8__1_))
        listAssistControl.add(MenuModel(3, context.getString(R.string.submenu_3),R.drawable.icono_ausentismos_8))
        listAssistControl.add(MenuModel(4, context.getString(R.string.submenu_4),R.drawable.icono_permisos_8))
        listAssistControl.add(MenuModel(5, context.getString(R.string.submenu_5),R.drawable.icono_kardex_8))
        listAssistControl.add(MenuModel(6, context.getString(R.string.submenu_6),R.drawable.icono_kardex_8__1_))
        listAssistControl.add(MenuModel(7, context.getString(R.string.submenu_7),R.drawable.icono_reportes_8__1_))
        return listAssistControl
    }
    fun setTimeManagement():List<MenuModel>{
        val listTimeManagement = mutableListOf<MenuModel>()
        listTimeManagement.add(MenuModel(8, context.getString(R.string.submenu_8),R.drawable.icono_informacion_general_8))
        listTimeManagement.add(MenuModel(9, context.getString(R.string.submenu_9),R.drawable.icono_negociacion_horarios_8))
        listTimeManagement.add(MenuModel(10, context.getString(R.string.submenu_10),R.drawable.icono_entradas_salidas_8))
        listTimeManagement.add(MenuModel(11, context.getString(R.string.submenu_11),R.drawable.icono_tiempos_extra_8))
        listTimeManagement.add(MenuModel(12, context.getString(R.string.submenu_12),R.drawable.icono_reportes_8__1_))
        return listTimeManagement
    }
    fun setSettings():List<MenuModel>{
        val listSettings = mutableListOf<MenuModel>()
        listSettings.add(MenuModel(13, context.getString(R.string.submenu_13),R.drawable.icon_abouts))
        listSettings.add(MenuModel(14, context.getString(R.string.submenu_14),R.drawable.icon_logout))
        //listSettings.add(MenuModel(15, "pruebas",R.drawable.icon_logout))
        return listSettings
    }
}
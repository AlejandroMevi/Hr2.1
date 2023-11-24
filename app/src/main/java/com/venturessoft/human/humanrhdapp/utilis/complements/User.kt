package com.venturessoft.human.humanrhdapp.utilis.complements

import android.app.Application
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem

class User : Application() {
    companion object{
        var ambiente = Constants.PREPRODUCTIVO
        var foto: String = ""
        var numCia: Long = 0
        var organigrama: Boolean = false
        var razonSocial: String = ""
        var nombreSupervisor: String = ""
        var token: String = ""
        var usuario: String = ""
        var listUsuTrue: ArrayList<ItemItem> = arrayListOf()
        var listUsuFalse: ArrayList<ItemItem> = arrayListOf()
    }
}
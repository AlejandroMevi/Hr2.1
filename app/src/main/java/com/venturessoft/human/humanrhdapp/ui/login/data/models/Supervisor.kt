package com.venturessoft.human.humanrhdapp.ui.login.data.models

import com.google.gson.annotations.SerializedName

class supervisor {
    @SerializedName("nombre")
    var nombre : String? = ""
    @SerializedName("scia")
    var scia : ArrayList<scia>? = null
}
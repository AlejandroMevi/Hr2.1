package com.venturessoft.human.humanrhdapp.ui.login.data.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("codigo")
    var codigo : String? = "",
    @SerializedName("resultado")
    var resultado : String? = "",
    @SerializedName("foto")
    var foto : String? = "",
    @SerializedName("token")
    var token : String? = "",
    @SerializedName("ssupervisor")
    var ssupervisor : supervisor? = null
)

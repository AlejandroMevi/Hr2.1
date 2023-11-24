package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models

import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansoItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosItem

data class KardexAnualModel (
    var kardex:List<List<MarcaItem>> = arrayListOf(),
    var holydays:List<List<DiasFestivosItem>> = arrayListOf(),
    var daysoff:List<List<DiasDescansoItem>> = arrayListOf(),
)
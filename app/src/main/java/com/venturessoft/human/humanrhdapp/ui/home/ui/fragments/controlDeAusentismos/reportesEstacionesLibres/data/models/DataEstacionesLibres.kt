package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models

import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.EstacionesLibresItem

class DataEstacionesLibres {
    fun getInfo(): List<EstacionesLibresItem> {
        return listOf(
            EstacionesLibresItem(
                1,
                "Alex Mejia",
                "Developer",
                "26/06/2023",
                "12:15",
                "ASD123AS",
                fotografia = "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",
                19.41714292310908,
                -98.94526686594112,
                "E"
            ),
            EstacionesLibresItem(
                2,
                "Edgar Gutierrez",
                "Developer",
                "29/09/2023",
                "18:05",
                "SAA123AS",
                fotografia = "",
                19.39607624187243,
                -99.0917723950457,
                "E"
            ),
            EstacionesLibresItem(
                3,
                "Sandra Sanchez",
                "Developer",
                "27/09/2023",
                "19:35",
                "3D123AR",
                fotografia = "",
                19.42171697476252,
                -99.14312519538224,
                "S"
            ),
            EstacionesLibresItem(
                4,
                "Raquel de la Rosa",
                "Developer",
                "26/06/2023",
                "23:22",
                "123QWEAS",
                fotografia = "",
                19.468466926651367,
                -99.14021828446594,
                "E"
            ),
            EstacionesLibresItem(
                5,
                "Joselyn Ramirez Cordero",
                "Developer",
                "06/08/2023",
                "08:47",
                "ASD123AS",
                fotografia = "",
                19.452902158414766,
                -99.1754984530336,
                "S"
            ),
            EstacionesLibresItem(
                5,
                "Eduardo Solis",
                "Developer",
                "12/09/2023",
                "14:23",
                "ASD123AS",
                fotografia = "",
                19.48004968756267,
                -99.06533220812317,
                "S"
            ),
            EstacionesLibresItem(
                7,
                "Jair Santiago",
                "Developer",
                "02/11/2023",
                "10:56",
                "FKMDLASD1",
                fotografia = "",
                19.339888219701535,
                -99.19058585734265,
                "E"
            )
        )
    }
}

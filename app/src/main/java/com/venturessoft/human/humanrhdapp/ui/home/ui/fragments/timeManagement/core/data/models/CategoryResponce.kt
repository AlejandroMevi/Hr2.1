package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models

data class CategoryResponce(
    val error: String,
    val mensaje: String,
    val codigo: String,
    val resp:List<Category>
)

data class Category(
    val key: String,
    val value: String,
)

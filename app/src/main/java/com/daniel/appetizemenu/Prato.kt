package com.daniel.appetizemenu

data class Prato(
    val nome: String,
    val valor: Double,
    val tempoPreparo: String,
    var isChecked: Boolean = false
)

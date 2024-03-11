package com.daniel.appetizemenu

interface OnCheckedChangeListener {
    fun onItemSelected(prato: Prato, isSelected: Boolean)
    fun onTotalUpdated(total: Double)
}
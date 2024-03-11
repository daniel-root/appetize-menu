package com.daniel.appetizemenu

import PratosAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentSobremesas : Fragment(), OnCheckedChangeListener {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    lateinit var pratosAdapter: PratosAdapter

    override fun onItemSelected(prato: Prato, isSelected: Boolean) {
        calcular()
        getItensSelecionados()
    }

    fun clearCheckBoxSelections() {
        pratosAdapter.clearSelections()
    }

    override fun onTotalUpdated(total: Double) {
        TODO("Not yet implemented")
    }

    private fun calcular() {
        val total = pratosAdapter.calcularTotal()
        (activity as? OnCheckedChangeListener)?.onTotalUpdated(total)
    }

    fun getItensSelecionados(): List<Prato> {
        return pratosAdapter.getItensSelecionados()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_menu_category4, container, false)

        initializeRecyclerView()

        return rootView
    }
    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerViewEntradas)
        pratosAdapter = PratosAdapter(getListaDePratos(),this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = pratosAdapter
    }

    private fun getListaDePratos(): List<Prato> {
        // Retorna uma lista de objetos Prato com as informações desejadas
        // Substitua isso com suas informações reais
        return listOf(
            Prato("Prato Teste 1", 20.00, "15 minutos"),
            Prato("Prato Teste 2", 15.00, "10 minutos"),
            Prato("Prato Teste 3", 25.00, "20 minutos"),
            Prato("Prato Teste 4", 20.00, "15 minutos"),
            Prato("Prato Teste 5", 15.00, "10 minutos"),
            Prato("Prato Teste 6", 25.00, "20 minutos")

            )
    }

    fun calcularTotal(): Double {
        return pratosAdapter.calcularTotal()
    }

}
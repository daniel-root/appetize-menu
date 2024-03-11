package com.daniel.appetizemenu

import PratosAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentPratosPrincipais : Fragment(), OnCheckedChangeListener {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var pratosAdapter: PratosAdapter

    fun clearCheckBoxSelections() {
        pratosAdapter.clearSelections()
    }

    override fun onItemSelected(prato: Prato, isSelected: Boolean) {
        print("Cliquei")
        calcular()
        getItensSelecionados()
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

    fun calcularTotal(): Double {
        return pratosAdapter.calcularTotal()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_menu_category2, container, false)

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
            Prato("Prato Teste 6", 25.00, "20 minutos"),
            Prato("Prato Teste 7", 20.00, "15 minutos"),
            Prato("Prato Teste 8", 15.00, "10 minutos"),
            Prato("Prato Teste 9", 25.00, "20 minutos"),
            Prato("Prato Teste 10", 20.00, "15 minutos"),
            Prato("Prato Teste 11", 15.00, "10 minutos"),
            Prato("Prato Teste 12", 25.00, "20 minutos"),
            Prato("Prato Teste 12", 20.00, "15 minutos"),
            Prato("Prato Teste 14", 15.00, "10 minutos"),
            Prato("Prato Teste 15", 25.00, "20 minutos"),
            Prato("Prato Teste 16", 25.00, "20 minutos"),
            Prato("Prato Teste 17", 20.00, "15 minutos"),
            Prato("Prato Teste 18", 15.00, "10 minutos"),
            Prato("Prato Teste 19", 25.00, "20 minutos"),

        )
    }
}
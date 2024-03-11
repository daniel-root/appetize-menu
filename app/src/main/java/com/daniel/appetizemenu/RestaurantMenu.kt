package com.daniel.appetizemenu

import PratosAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import androidx.viewpager2.widget.ViewPager2
import com.daniel.appetizemenu.databinding.ActivityRestaurantMenuBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RestaurantMenu : AppCompatActivity(), OnCheckedChangeListener  {
    private lateinit var binding: ActivityRestaurantMenuBinding
    private var itensSelecionados = mutableListOf<Prato>()
    private var Total: Double = 0.0
    private lateinit var sessionManager: SessionManager
    private val logoutHandler = Handler(Looper.getMainLooper())
    private val logoutRunnable = Runnable {
        sessionManager.clearSession()
        finish()
    }

    override fun onItemSelected(prato: Prato, isSelected: Boolean) {
    }

    override fun onTotalUpdated(total: Double) {
        // Atualize o TextViewTotal com o novo valor total
        val totalEntradas = (supportFragmentManager.findFragmentByTag("f0") as? FragmentEntradas)?.calcularTotal() ?: 0.0
        val totalSobremesas = (supportFragmentManager.findFragmentByTag("f3") as? FragmentSobremesas)?.calcularTotal() ?: 0.0
        val totalPrincipais = (supportFragmentManager.findFragmentByTag("f1") as? FragmentPratosPrincipais)?.calcularTotal() ?: 0.0
        val totalBebidas = (supportFragmentManager.findFragmentByTag("f2") as? FragmentBebidas)?.calcularTotal() ?: 0.0
        val itensEntradas = (supportFragmentManager.findFragmentByTag("f0") as? FragmentEntradas)?.getItensSelecionados() ?: emptyList()
        val itensSobremesas = (supportFragmentManager.findFragmentByTag("f3") as? FragmentSobremesas)?.getItensSelecionados() ?: emptyList()
        val itensPrincipais = (supportFragmentManager.findFragmentByTag("f1") as? FragmentPratosPrincipais)?.getItensSelecionados() ?: emptyList()
        val itensBebidas = (supportFragmentManager.findFragmentByTag("f2") as? FragmentBebidas)?.getItensSelecionados() ?: emptyList()
        itensSelecionados = mutableListOf<Prato>().apply {
            addAll(itensEntradas)
            addAll(itensSobremesas)
            addAll(itensPrincipais)
            addAll(itensBebidas)
        }
        val totalGeral = totalSobremesas + totalEntradas + totalBebidas + totalPrincipais
        Total = totalGeral
        binding.textViewTotal.text = "Total: R$ $totalGeral"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        sessionManager = SessionManager(this)
        startLogoutTimer()

        val btnExit: ImageButton = findViewById(R.id.image_btn_exit)
        val receivedToken = sessionManager.checkSavedCredentials()

        binding.textToken.text = "Sua mesa é a $receivedToken"

        btnExit.setOnClickListener {
            showExitConfirmationDialog()
        }



        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)



        binding.finish.setOnClickListener {
            val itensSelecionadosText = itensSelecionados.joinToString("\n") { "${it.nome}: R$ ${it.valor}" }
            val view = layoutInflater.inflate(R.layout.layout_alert_dialog, null)
            val editTextObservacoesAlert: EditText = view.findViewById(R.id.editTextObservacoesAlert)
            if (Total > 0) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Detalhes do Pedido")
                    .setMessage("Itens Selecionados:\n$itensSelecionadosText\nTotal: R$ $Total")
                    .setView(view)
                    .setPositiveButton("Ok") { _, _ ->

                        val observacoes = editTextObservacoesAlert.text.toString()

                        clearCheckBoxSelections()
                        Toast.makeText(this, "“Seu pedido foi enviado para o balcão do restaurante", Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .show()
            }else {
                Toast.makeText(
                    this,
                    "Adicione ao menos um item antes de finalizar seu pedido.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }




        // Configurar o adaptador para o ViewPager
        val pagerAdapter = MenuPagerAdapter(this)

        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Entradas"
                1 -> "Pratos Principais"
                2 -> "Bebidas"
                3 -> "Sobremesas"
                else -> ""
            }
        }.attach()




    }
    override fun onUserInteraction() {
        // Reinicia o contador de tempo quando houver interação do usuário
        super.onUserInteraction()
        resetLogoutTimer()
    }
    private fun clearCheckBoxSelections() {
        // Limpar as seleções nos fragments correspondentes
        (supportFragmentManager.findFragmentByTag("f0") as? FragmentEntradas)?.clearCheckBoxSelections()
        (supportFragmentManager.findFragmentByTag("f3") as? FragmentSobremesas)?.clearCheckBoxSelections()
        (supportFragmentManager.findFragmentByTag("f1") as? FragmentPratosPrincipais)?.clearCheckBoxSelections()
        (supportFragmentManager.findFragmentByTag("f2") as? FragmentBebidas)?.clearCheckBoxSelections()
        binding.textViewTotal.text = "Total: R$ 0.0"
    }

    private fun startLogoutTimer() {
        // Inicia o contador de tempo para sair automaticamente após 4 horas
        logoutHandler.postDelayed(logoutRunnable, 1 * 60 * 60 * 1000) // 4 horas em milissegundos
    }

    private fun resetLogoutTimer() {
        // Reinicia o contador de tempo
        logoutHandler.removeCallbacks(logoutRunnable)
        startLogoutTimer()
    }

    fun showExitConfirmationDialog() {
        logoutHandler.removeCallbacks(logoutRunnable)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
            .setMessage("Tem certeza que deseja sair?")
            .setPositiveButton("Sim") { dialog, which ->
                // Lógica para sair
                sessionManager.clearSession()
                finish()
            }
            .setNegativeButton("Não") { dialog, which ->
                // Nenhuma ação ou lógica necessária se o usuário escolher não sair
            }
            .show()
    }
    override fun onBackPressed() {
        resetLogoutTimer()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
        builder.setMessage("Tem certeza que deseja sair?")

        builder.setPositiveButton("Sim") { _, _ ->
            // Ação a ser realizada se o usuário confirmar
            sessionManager.clearSession()
            finish()
            super.onBackPressed() // Chama a implementação padrão para voltar
        }

        builder.setNegativeButton("Não") { _, _ ->
            // Ação a ser realizada se o usuário cancelar
        }

        val dialog = builder.create()
        dialog.show()
    }
}
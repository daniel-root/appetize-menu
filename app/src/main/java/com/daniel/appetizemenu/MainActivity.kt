 package com.daniel.appetizemenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.daniel.appetizemenu.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mesasUtilizadas = mutableSetOf<String>()
    private lateinit var sessionManager: SessionManager
    private lateinit var editPassword: EditText
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mesasUtilizadas.addAll(setOf("1001","2003","3004"))

        editPassword = findViewById(R.id.edit_password)
        progressBar = findViewById(R.id.progressBar)

        sessionManager = SessionManager(this)

        val savedToken = sessionManager.checkSavedCredentials()

        if (!savedToken.isNullOrBlank()) {
            sessionManager.saveToken(savedToken)
            //Redirecionar para a tela principal
            val intent = Intent(this, RestaurantMenu::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            showLoading(true)

            val password = binding.editPassword.text.toString()

            if (validateFields(password)) {

                if (validateToken(password)) {
                    if (VerificarMesaUtilizada(password)) {
                        showLoading(false)
                        val intent = Intent(this@MainActivity, RestaurantMenu::class.java)
                        sessionManager.saveToken(password)
                        startActivity(intent)
                    } else {
                        showLoading(false)
                        Toast.makeText(this, "Está mesa está ocupada", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    showLoading(false)
                    Toast.makeText(this, "Verifique o número da mesa", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                showLoading(false)
                Toast.makeText(this, "Preencha o campo", Toast.LENGTH_SHORT).show()
            }
        }

    }
     private fun showLoading(show: Boolean) {
         //visibilidade do ProgressBar
         progressBar.visibility = if (show) View.VISIBLE else View.GONE

         //habilitação dos elementos de UI
         editPassword.isEnabled = !show
         binding.buttonLogin.isEnabled = !show
     }

     private fun validateFields(password: String): Boolean {
         return !(password.isEmpty())
     }
     private fun validateToken(password: String): Boolean {
         val listaMesas = listOf("1001", "1002", "1003", "1004", "1005", "1006",
             "2001", "2002", "2003", "2004", "2005", "2006",
             "3001", "3002", "3003", "3004", "3005", "3006",)
         return listaMesas.contains(password)
     }
     private fun VerificarMesaUtilizada(numeroMesa: String): Boolean {
         return !mesasUtilizadas.contains(numeroMesa)
     }
     private fun marcarMesaUtilizada(numeroMesa: String) {
         mesasUtilizadas.add(numeroMesa)
     }

     private fun marcarMesaDisponivel(numeroMesa: String) {
         mesasUtilizadas.remove(numeroMesa)
     }
}
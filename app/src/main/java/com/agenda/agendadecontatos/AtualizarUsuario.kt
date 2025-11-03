package com.agenda.agendadecontatos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agenda.agendadecontatos.dao.UsuarioDao
import com.agenda.agendadecontatos.databinding.ActivityAtualizarUsuarioBinding
import com.agenda.agendadecontatos.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtualizarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarUsuarioBinding
    private lateinit var usuarioDao: UsuarioDao
    private var uid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuarioDao = AppDatabase.getInstance(this).usuarioDao()

        // Recupera dados enviados pela intent
        uid = intent.extras?.getInt("uid") ?: 0
        val nomeRecuperado = intent.extras?.getString("nome") ?: ""
        val sobrenomeRecuperado = intent.extras?.getString("sobrenome") ?: ""
        val idadeRecuperada = intent.extras?.getString("idade") ?: ""
        val celularRecuperado = intent.extras?.getString("celular") ?: ""

        binding.editNome.setText(nomeRecuperado)
        binding.editSobrenome.setText(sobrenomeRecuperado)
        binding.editIdade.setText(idadeRecuperada)
        binding.editCelular.setText(celularRecuperado)

        binding.btAtualizar.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val sobrenome = binding.editSobrenome.text.toString()
            val idade = binding.editIdade.text.toString()
            val celular = binding.editCelular.text.toString()

            if (nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || celular.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                atualizarUsuario(uid, nome, sobrenome, idade, celular)
            }
        }
    }

    private fun atualizarUsuario(uid: Int, nome: String, sobrenome: String, idade: String, celular: String) {
        val usuario = Usuario(nome, sobrenome, idade, celular).apply { this.uid = uid }

        CoroutineScope(Dispatchers.IO).launch {
            usuarioDao.atualizarUsuario(usuario)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@AtualizarUsuario, "Usuário atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

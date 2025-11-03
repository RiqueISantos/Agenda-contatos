package com.agenda.agendadecontatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nomeRecuperado = intent.extras?.getString("nome")
        val sobrenomeRecuperado = intent.extras?.getString("sobrenome")
        val idadeRecuperada = intent.extras?.getString("idade")
        val celularRecuperado = intent.extras?.getString("celular")
        val emailRecuperado = intent.extras?.getString("email") // novo campo
        val uid = intent.extras!!.getInt("uid")

        binding.editNome.setText(nomeRecuperado)
        binding.editSobrenome.setText(sobrenomeRecuperado)
        binding.editIdade.setText(idadeRecuperada)
        binding.editCelular.setText(celularRecuperado)
        binding.editEmail.setText(emailRecuperado) // novo campo

        binding.btAtualizar.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val sobrenome = binding.editSobrenome.text.toString()
            val idade = binding.editIdade.text.toString()
            val celular = binding.editCelular.text.toString()
            val email = binding.editEmail.text.toString()

            if (nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || celular.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                atualizarContato(uid, nome, sobrenome, idade, celular, email)
            }
        }
    }

    private fun atualizarContato(uid: Int, nome: String, sobrenome: String, idade: String, celular: String, email: String) {
        usuarioDao = AppDatabase.getInstance(this).usuarioDao()
        val usuarioAtualizado = Usuario(nome, sobrenome, idade, celular, email)
        usuarioAtualizado.uid = uid

        CoroutineScope(Dispatchers.IO).launch {
            usuarioDao.atualizarUsuario(usuarioAtualizado)

            withContext(Dispatchers.Main) {
                Toast.makeText(this@AtualizarUsuario, "Usuário atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

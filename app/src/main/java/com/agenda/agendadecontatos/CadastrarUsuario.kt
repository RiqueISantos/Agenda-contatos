package com.agenda.agendadecontatos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agenda.agendadecontatos.dao.UsuarioDao
import com.agenda.agendadecontatos.databinding.ActivityCadastrarUsuarioBinding
import com.agenda.agendadecontatos.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastrarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityCadastrarUsuarioBinding
    private lateinit var usuarioDao: UsuarioDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuarioDao = AppDatabase.getInstance(this).usuarioDao()

        // Se estiver editando um usuário existente
        val usuarioEdicao = intent.getParcelableExtra<Usuario>("usuario")
        usuarioEdicao?.let {
            binding.editNome.setText(it.nome)
            binding.editSobrenome.setText(it.sobrenome)
            binding.editIdade.setText(it.idade)
            binding.editCelular.setText(it.celular)
        }

        binding.btCadastrar.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val sobrenome = binding.editSobrenome.text.toString()
            val idade = binding.editIdade.text.toString()
            val celular = binding.editCelular.text.toString()

            if (nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || celular.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                cadastrarOuAtualizar(usuarioEdicao, nome, sobrenome, idade, celular)
            }
        }
    }

    private fun cadastrarOuAtualizar(
        usuarioEdicao: Usuario?,
        nome: String,
        sobrenome: String,
        idade: String,
        celular: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            if (usuarioEdicao != null) {
                // Atualiza usuário existente
                usuarioEdicao.nome = nome
                usuarioEdicao.sobrenome = sobrenome
                usuarioEdicao.idade = idade
                usuarioEdicao.celular = celular
                usuarioDao.atualizarUsuario(usuarioEdicao) // ✅ usar atualizarUsuario
            } else {
                // Cria novo usuário
                val novoUsuario = Usuario(nome, sobrenome, idade, celular)
                usuarioDao.inserir(listOf(novoUsuario))
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

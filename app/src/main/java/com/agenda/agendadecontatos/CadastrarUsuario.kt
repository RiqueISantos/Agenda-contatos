package com.agenda.agendadecontatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        binding.btCadastrar.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val sobrenome = binding.editSobrenome.text.toString()
            val idade = binding.editIdade.text.toString()
            val celular = binding.editCelular.text.toString()
            val email = binding.editEmail.text.toString()

            if(nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || celular.isEmpty() || email.isEmpty()){
                Toast.makeText(this,"Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                val usuario = Usuario(nome, sobrenome, idade, celular, email)
                CoroutineScope(Dispatchers.IO).launch {
                    usuarioDao.inserir(listOf(usuario))
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@CadastrarUsuario,"Usuário cadastrado com sucesso!",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}

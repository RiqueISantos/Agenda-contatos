package com.agenda.agendadecontatos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agenda.agendadecontatos.adapter.ContatoAdapter
import com.agenda.agendadecontatos.databinding.ActivityMainBinding
import com.agenda.agendadecontatos.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contatoAdapter: ContatoAdapter
    // A lista de usuários agora pode ser uma simples MutableList gerenciada pelo Adapter
    private var listaUsuarios = mutableListOf<Usuario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Configurar o RecyclerView e o Adapter APENAS UMA VEZ.
        setupRecyclerView()

        // 2. Configurar o clique do botão de cadastro
        binding.btCadastrar.setOnClickListener {
            val navegarTelaCadastro = Intent(this, CadastrarUsuario::class.java)
            startActivity(navegarTelaCadastro)
        }
    }

    override fun onResume() {
        super.onResume()
        // 3. Toda vez que a tela se torna visível, carregamos os dados mais recentes.
        carregarContatos()
    }

    private fun setupRecyclerView() {
        // Inicializa o adapter com uma lista vazia.
        contatoAdapter = ContatoAdapter(this, listaUsuarios)
        binding.recyclerViewContatos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = contatoAdapter
        }
    }

    private fun carregarContatos() {
        // Inicia a corrotina para buscar dados em uma thread de background (IO).
        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getInstance(applicationContext).usuarioDao()
            val usuariosDoBanco = dao.get() // Assume que get() retorna List<Usuario>

            // Após buscar os dados, volta para a thread principal (Main) para atualizar a UI.
            withContext(Dispatchers.Main) {
                // Usa a função que criamos no adapter para atualizar a lista.
                contatoAdapter.atualizarLista(usuariosDoBanco)
            }
        }
    }
}

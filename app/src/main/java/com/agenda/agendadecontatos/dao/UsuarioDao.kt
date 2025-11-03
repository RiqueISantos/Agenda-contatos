package com.agenda.agendadecontatos.dao

import androidx.room.*
import com.agenda.agendadecontatos.model.Usuario

@Dao
interface UsuarioDao {

    // Inserir um ou mais usuários
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserir(listaUsuarios: List<Usuario>)

    // Buscar todos os usuários ordenados pelo nome
    @Query("SELECT * FROM tabela_usuarios ORDER BY nome ASC")
    fun get(): List<Usuario>

    // Atualizar um usuário existente
    @Update
    fun atualizarUsuario(usuario: Usuario)

    // Deletar usuário pelo uid
    @Query("DELETE FROM tabela_usuarios WHERE uid = :id")
    fun deletar(id: Int)
}

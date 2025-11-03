package com.agenda.agendadecontatos.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.agenda.agendadecontatos.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    fun inserir(listaUsuarios: List<Usuario>)

    @Query("SELECT * FROM tabela_usuarios ORDER BY nome ASC")
    fun get(): List<Usuario>

    @Update
    fun atualizarUsuario(usuario: Usuario)

    @Query("DELETE FROM tabela_usuarios WHERE uid = :id")
    fun deletar(id: Int)
}

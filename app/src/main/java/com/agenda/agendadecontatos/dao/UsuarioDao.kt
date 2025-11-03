package com.agenda.agendadecontatos.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.agenda.agendadecontatos.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    fun inserir(listaUsuarios: MutableList<Usuario>)

    @Query("SELECT * FROM tabela_usuarios ORDER BY nome ASC")
    fun get(): MutableList<Usuario>

    @Query("UPDATE tabela_usuarios SET nome = :nome, sobrenome = :sobrenome, idade = :idade, celular = :celular WHERE uid = :uid")
    fun atualizar(uid: Int, nome: String, sobrenome: String, idade: String, celular: String)



    @Query("DELETE FROM tabela_usuarios WHERE uid = :id")
    fun deletar(id: Int)
}
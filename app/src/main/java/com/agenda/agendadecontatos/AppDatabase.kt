package com.agenda.agendadecontatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.agenda.agendadecontatos.dao.UsuarioDao
import com.agenda.agendadecontatos.model.Usuario

@Database(entities = [Usuario::class], version = 5) // aumente a versão
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "agenda.db"
                )
                    .fallbackToDestructiveMigration() // apaga o banco antigo
                    .build()
            }
            return INSTANCE!!
        }
    }
}
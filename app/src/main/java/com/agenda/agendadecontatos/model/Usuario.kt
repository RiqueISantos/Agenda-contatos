package com.agenda.agendadecontatos.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tabela_usuarios")
data class Usuario(
    @ColumnInfo(name = "nome") var nome: String,
    @ColumnInfo(name = "sobrenome") var sobrenome: String,
    @ColumnInfo(name = "idade") var idade: String,
    @ColumnInfo(name = "celular") var celular: String,
    @ColumnInfo(name = "email") val email: String

) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

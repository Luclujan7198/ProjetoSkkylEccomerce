package br.com.orion.skkylEccomerce

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "produto")
class Produto: Serializable {

    @PrimaryKey
    var id : Long = 0
    var nome = ""
    var foto = ""
    var cor = ""
    var tamanho = ""


    override fun toString(): String {
        return "Produto(id=$id, nome='$nome', foto='$foto', cor='$cor', tamanho='$tamanho')"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}
package br.com.orion.skkylEccomerce

import android.arch.persistence.room.Room

object DatabaseManager {
    // singleton
    private var dbInstance: SkkylDatabase
    init {
        val appContext = SkkylEccomerce.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
                appContext, // contexto global
                SkkylDatabase::class.java, // Referência da classe do banco
                "Skkyl.sqlite" // nome do arquivo do banco
        ).build()
    }

    fun getProdutoDAO(): ProdutoDAO{
        return dbInstance.produtoDAO()
    }

}
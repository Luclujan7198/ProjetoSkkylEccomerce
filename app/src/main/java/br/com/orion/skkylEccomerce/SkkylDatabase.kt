package br.com.orion.skkylEccomerce

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Produto::class), version = 1)
abstract class SkkylDatabase: RoomDatabase() {
    abstract fun produtoDAO(): ProdutoDAO
}
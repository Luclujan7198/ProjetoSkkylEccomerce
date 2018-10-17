package br.com.orion.skkylEccomerce

import android.content.Context

object ProdutoService {

    fun getProdutos (context: Context): List<Produto>{
        val produto = mutableListOf<Produto>()

        for(i in 1 .. 10){
            val prod = Produto()

            prod.nome = "Camisa $i"
            prod.foto = "https://i2-oakley.a8e.net.br/gg/camiseta-masc-mod-black-tape-tee_73456350_888896487651.jpg"
            prod.tamanho = "M"
            prod.cor = "Azul"
            produto.add(prod)
        }
        return produto
    }

}
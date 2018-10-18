package br.com.orion.skkylEccomerce

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cadastro_produto.*

class ProdutoCadastroActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_produto)
        setTitle("Novo produto")

        salvarProduto.setOnClickListener {
            val produto = Produto()
            produto.nome = nomeProduto.text.toString()
            produto.cor = corProduto.text.toString()
            produto.tamanho = tamanhoProduto.text.toString()
            produto.foto = urlProduto.text.toString()

            taskAtualizar(produto)
        }
    }

    private fun taskAtualizar(produto: Produto) {
        Thread {
            ProdutoService.save(produto)
            runOnUiThread {
                finish()
            }
        }.start()
    }
}
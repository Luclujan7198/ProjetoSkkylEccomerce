package br.com.orion.skkylEccomerce

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.support.v7.widget.RecyclerView
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread

class ProdutoActivity : DebugActivity() {

    private val context: Context get() = this
    var produto: Produto? = null
    var recyclerProduto: RecyclerView? = null
    private var produtos = listOf<Produto>()
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE= 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.produtos)




        produto = intent.getSerializableExtra("produto") as Produto

        val args:Bundle? = intent.extras

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = produto?.nome

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var texto = findViewById<TextView>(R.id.nomeProduto)
        texto.text = produto?.nome
        var imagem = findViewById<ImageView>(R.id.imagemProduto)
        Picasso.with(this).load(produto?.foto).fit().into(imagem,
                object: com.squareup.picasso.Callback{
                    override fun onSuccess() {}

                    override fun onError() { }
                })



        recyclerProduto = findViewById<RecyclerView>(R.id.recyclerProduto)
        recyclerProduto?.layoutManager = LinearLayoutManager(context)
        recyclerProduto?.itemAnimator = DefaultItemAnimator()
        recyclerProduto?.setHasFixedSize(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // id do item clicado
        val id = item?.itemId
        // verificar qual item foi clicado
        // remover a disciplina no WS
        if  (id == R.id.action_remover) {
            // alerta para confirmar a remeção
            // só remove se houver confirmação positiva
            AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage("Deseja excluir o produto")
                    .setPositiveButton("Sim") {
                        dialog, which ->
                        dialog.dismiss()
                        taskExcluir()
                    }.setNegativeButton("Não") {
                        dialog, which -> dialog.dismiss()
                    }.create().show()
        }
        // botão up navigation
        else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        // task para recuperar as disciplinas
        taskProdutos()
    }

    fun taskProdutos() {
        Thread {
            this.produtos = ProdutoService.getProdutos(context)
            runOnUiThread {
                recyclerProduto?.adapter = ProdutoAdapter(produtos) {onClickProduto(it)}
            }
        }.start()


    }

    private fun taskExcluir() {
        if (this.produto != null && this.produto is Produto) {
            Thread {
                ProdutoService.delete(this.produto as Produto)
                runOnUiThread {
                    // após remover, voltar para activity anterior
                    finish()
                }
            }.start()
        }
    }

    // tratamento do evento de clicar em uma disciplina
    fun onClickProduto(produto: Produto) {
        Toast.makeText(context, "Clicou no produto ${produto.nome}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ProdutoActivity::class.java)
        intent.putExtra("produto", produto)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

    // esperar o retorno do cadastro da disciplina
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CADASTRO || requestCode == REQUEST_REMOVE ) {
            // atualizar lista de disciplinas
            taskProdutos()
        }
    }

}

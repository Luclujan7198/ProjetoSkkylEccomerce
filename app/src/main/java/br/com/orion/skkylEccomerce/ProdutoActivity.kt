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



        if (intent.getSerializableExtra("produto") is Produto)
            produto = intent.getSerializableExtra("produto") as Produto?

        val args:Bundle? = intent.extras

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = produto?.nome

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        var texto = findViewById<TextView>(R.id.nomeProduto)
        texto?.text = produto?.nome
        var imagem = findViewById<ImageView>(R.id.imagemProduto)
        if(imagem != null){
            Picasso.with(this).load(produto?.foto).fit().into(imagem,
                object: com.squareup.picasso.Callback{
                    override fun onSuccess() {}

                    override fun onError() { }
                })
        }




        recyclerProduto = findViewById<RecyclerView>(R.id.recyclerProduto)
        recyclerProduto?.layoutManager = LinearLayoutManager(context)
        recyclerProduto?.itemAnimator = DefaultItemAnimator()
        recyclerProduto?.setHasFixedSize(true)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_produto, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if  (id == R.id.action_remover) {
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
        else if (id == android.R.id.home) {
            finish()
        }
        else if (id == R.id.action_adicionar) {
            onClickProdutoAdicionar()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        taskProdutos()
    }

    fun taskProdutos() {
        Thread {
            this.produtos = ProdutoService.getProdutos(context)
            runOnUiThread {
                recyclerProduto?.adapter = ProdutoAdapter(produtos) {onClickProduto(it)}

                enviaNotificacao(this.produtos.get(0))
            }
        }.start()


    }
    fun taskAdicionar() {
        Thread {
            this.produtos = ProdutoService.getProdutos(context)
            runOnUiThread {
                recyclerProduto?.adapter = ProdutoAdapter(produtos) {onClickProdutoAdicionar()}

                enviaNotificacao(this.produtos.get(0))
            }
        }.start()


    }

    fun enviaNotificacao(produto: Produto) {
        val intent = Intent(this, ProdutoActivity::class.java)
        intent.putExtra("produto", produto)
        NotificationUtil.create(this, 1, intent, "SkkylEccomerce", "Novo Produto:  ${produto.nome}")
    }

    private fun taskExcluir() {
        if (this.produto != null && this.produto is Produto) {
            Thread {
                ProdutoService.delete(this.produto as Produto)
                runOnUiThread {
                    finish()
                }
            }.start()
        }
    }

    fun onClickProduto(produto: Produto) {
        Toast.makeText(context, "Clicou no produto ${produto.nome}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ProdutoActivity::class.java)
        intent.putExtra("produto", produto)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

    fun onClickProdutoAdicionar() {
        val intent = Intent(context, ProdutoCadastroActivity::class.java)
//        intent.putExtra("produto", produto)
        startActivity(intent)
//        startActivityForResult(intent, REQUEST_CADASTRO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CADASTRO || requestCode == REQUEST_REMOVE ) {
            taskProdutos()
        }
    }

}

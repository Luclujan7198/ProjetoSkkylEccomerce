package br.com.orion.skkylEccomerce

import android.content.Context
import android.os.Bundle
import android.widget.*

class ProdutoActivity : DebugActivity() {

    private val context: Context get() = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.produtos)

        val args = intent.extras

        val produtos = args.getString("produtos")
        Toast.makeText(context, produtos, Toast.LENGTH_LONG).show()


        //implementar cards mais para frente!!!

    }
}

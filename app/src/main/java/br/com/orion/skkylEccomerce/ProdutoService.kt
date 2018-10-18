package br.com.orion.skkylEccomerce

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object ProdutoService {

    val host = "http://luclujan.pythonanywhere.com"
    val TAG = "SkkylEcomerce"

    fun getProdutos (context: Context): List<Produto> {
        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/produtos"
            val json = HttpHelper.get(url)
            return parserJson(json)
        } else {
            return ArrayList<Produto>()
        }
    }

    fun save(produto: Produto): Response {
        val json = HttpHelper.post("$host/produtos", produto.toJson())
        return parserJson(json)
    }

    fun delete(produto: Produto): Response {
        Log.d(TAG, produto.id.toString())
        val url = "$host/produtos/${produto.id}"
        val json = HttpHelper.delete(url)
        Log.d(TAG, json)
        return parserJson(json)
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }

}
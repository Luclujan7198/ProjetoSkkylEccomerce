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
        var produtos = ArrayList<Produto>()
        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/produtos"
            val json = HttpHelper.get(url)
            produtos =  parserJson(json)
            for (prod in produtos) {
                saveOffline(prod)
            }
            return produtos
        } else {
            val dao = DatabaseManager.getProdutoDAO()
            val disciplinas = dao.findAll()
            return produtos

        }
    }

    fun save(produto: Produto): Response {
        val json = HttpHelper.post("$host/produtos", produto.toJson())
        return parserJson(json)
    }

    fun delete(produto: Produto): Response {

        if (AndroidUtils.isInternetDisponivel(SkkylEccomerce.getInstance().applicationContext)) {
            val url = "$host/produtos/${produto.id}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        } else {
            val dao = DatabaseManager.getProdutoDAO()
            dao.delete(produto)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }

    }
    fun saveOffline(produto: Produto) : Boolean {
        val dao = DatabaseManager.getProdutoDAO()

        if (! existeDisciplina(produto)) {
            dao.insert(produto)
        }

        return true

    }

    fun existeDisciplina(produto: Produto): Boolean {
        val dao = DatabaseManager.getProdutoDAO()
        return dao.getById(produto.id) != null
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }

}
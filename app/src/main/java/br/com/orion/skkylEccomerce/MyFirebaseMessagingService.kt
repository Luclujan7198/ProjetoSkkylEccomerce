package br.com.orion.skkylEccomerce

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    val TAG = "firebase"

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d(TAG, "Novo token: $token")
        // guarda token em SharedPreferences
        Prefs.setString("FB_TOKEN", token!!)
    }

    // recebe a notificação de push
    override fun onMessageReceived(mensagemRemota: RemoteMessage?) {
        Log.d(TAG, "onMessageReceived")

        // verifica se a mensagem recebida do firebase é uma notificação
        if (mensagemRemota?.notification != null) {
            val titulo = mensagemRemota?.notification?.title
            val corpo = mensagemRemota?.notification?.body
            Log.d(TAG, "Titulo da mensagem: $titulo")
            Log.d(TAG, "Corpo da mensagem: $corpo")

            showNotification(mensagemRemota)
        }
    }

    private fun showNotification(mensagemRemota: RemoteMessage) {

        // Intent para abrir quando clicar na notificação
        val intent = Intent(this, ProdutoActivity::class.java)
        // se título for nulo, utilizar nome no app
        val titulo = mensagemRemota?.notification?.title?: getString(R.string.app_name)
        var mensagem = mensagemRemota?.notification?.body!!

        // verificar se existem dados enviados no push
        if(mensagemRemota?.data.isNotEmpty()) {
            val produtoId = mensagemRemota.data.get("produtoId")?.toLong()!!
            mensagem += ""
            // recuperar disciplina no WS
            val disciplina = ProdutoService.getProduto(this, produtoId)
            intent.putExtra("produto", produtoId)
        }
        NotificationUtil.create(this, 1, intent, titulo, mensagem)
    }
}
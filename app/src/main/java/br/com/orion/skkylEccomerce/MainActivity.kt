package br.com.orion.skkylEccomerce

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.login.*

class MainActivity : DebugActivity() {


    private val context: Context get() = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val imagem = findViewById<ImageView>(R.id.campo_imagem)
        imagem.setImageResource(R.drawable.skkyl)

        val texto = findViewById<TextView>(R.id.texto_login)
        texto.text = getString(R.string.mensagem_login)


        val botaoLogin = findViewById<Button>(R.id.botao_login)

        botaoLogin.setOnClickListener {onClickLogin() }

        var lembrar = Prefs.getBoolean("lembrar")
        if (lembrar) {
            val campoUsuario = findViewById<EditText>(R.id.campo_usuario)
            val campoSenha = findViewById<EditText>(R.id.campo_senha)
            var lembrarNome  = Prefs.getString("lembrarNome")
            var lembrarSenha  = Prefs.getString("lembrarSenha")
            campoUsuario.setText(lembrarNome)
            campoSenha.setText(lembrarSenha)
            checkBoxLogin.isChecked = lembrar

        }

    }

    fun onClickLogin(){
        val campoUsuario = findViewById<EditText>(R.id.campo_usuario)
        val campoSenha = findViewById<EditText>(R.id.campo_senha)
        val valorUsuario = campoUsuario.text.toString()
        val valorSenha = campoSenha.text.toString()

//         criar intent
        Prefs.setBoolean("lembrar", checkBoxLogin.isChecked)
        if (checkBoxLogin.isChecked) {
            Prefs.setString("lembrarNome", valorUsuario)
            Prefs.setString("lembrarSenha", valorSenha)
        } else{
            Prefs.setString("lembrarNome", "")
            Prefs.setString("lembrarSenha", "")
        }

        if(valorUsuario=="aluno" && valorSenha=="impacta" ){
            val intent = Intent(context, TelaInicialActivity::class.java)
            val params = Bundle()
            params.putString("nome", valorUsuario)
            intent.putExtras(params)
            intent.putExtra("numero", 10)
            startActivityForResult(intent, 1)
        }else{
            Toast.makeText(context, "Usuario ou senha incorretos", Toast.LENGTH_LONG).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            val result = data?.getStringExtra("result")
            Toast.makeText(context, "$result", Toast.LENGTH_LONG).show()
        }
    }

}

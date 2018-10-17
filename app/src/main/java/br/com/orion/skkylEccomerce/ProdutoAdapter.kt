package br.com.orion.skkylEccomerce

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class ProdutoAdapter (
    val produto: List<Produto>,
    val onClick: (Produto) -> Unit): RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

        // ViewHolder com os elemetos da tela
        class ProdutoViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val cardNome: TextView
            val cardImg : ImageView
            var cardProgress: ProgressBar
            var cardView: CardView

            init {
                cardNome = view.findViewById<TextView>(R.id.cardNome)
                cardImg = view.findViewById<ImageView>(R.id.cardImg)
                cardProgress = view.findViewById<ProgressBar>(R.id.cardProgress)
                cardView = view.findViewById<CardView>(R.id.card_produto)

            }

        }
}
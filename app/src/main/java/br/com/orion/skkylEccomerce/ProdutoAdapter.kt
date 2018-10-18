package br.com.orion.skkylEccomerce

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Picasso

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

    override fun getItemCount() = this.produto.size
    // inflar layout do adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        // infla view no adapter
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_produto, parent, false)

        // retornar ViewHolder
        val holder = ProdutoViewHolder(view)
        return holder
    }

    // bind para atualizar Views com os dados

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val context = holder.itemView.context

        // recuperar objeto disciplina
        val produto = produto[position]

        // atualizar dados de disciplina

        holder.cardNome.text = produto.nome
        holder.cardProgress.visibility = View.VISIBLE

        // download da imagem
        Picasso.with(context).load(produto.foto).fit().into(holder.cardImg,
                object: com.squareup.picasso.Callback{
                    override fun onSuccess() {
                        holder.cardProgress.visibility = View.GONE
                    }

                    override fun onError() {
                        holder.cardProgress.visibility = View.GONE
                    }
                })

        // adiciona evento de clique
        holder.itemView.setOnClickListener {onClick(produto)}
    }

}
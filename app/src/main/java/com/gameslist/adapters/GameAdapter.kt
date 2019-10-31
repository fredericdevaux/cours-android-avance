package com.gameslist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.gameslist.R
import com.gameslist.network.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.game_list_item.view.*

class GameViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class GameAdapter(var gamesList: ArrayList<Game>, val click: (game: Game) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<GameViewHolder>() {

    override fun getItemCount(): Int {
        return gamesList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.game_list_item, parent, false)
        return GameViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game: Game = gamesList[position]
        val gameImgView: ImageView = holder.view.findViewById(R.id.gameImg)


        holder.view.textView.text = game.name
        Picasso.get().load(game.img).resize(100, 100).centerCrop().into(gameImgView)
        holder.view.gameCard.setOnClickListener { click(game) }
    }
}

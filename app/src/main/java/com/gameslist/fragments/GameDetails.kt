package com.gameslist.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.gameslist.LinkActivity
import com.gameslist.R
import com.gameslist.adapters.GameAdapter
import com.gameslist.network.Game
import com.gameslist.network.ResultGames
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_game_details.*
import kotlinx.android.synthetic.main.fragment_games_list.*
import org.json.JSONArray
import org.json.JSONObject


class GameDetails : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onStart() {
        super.onStart()
        val game = arguments?.getParcelable<Game>("GAME")
        if (game != null) {
            handleJson(game)
        }

        gameLink.setOnClickListener {
            val amount = retreviedSaved()
            increment()
            if (amount !== null && amount % 2 == 0) {
                val openURL = Intent(android.content.Intent.ACTION_VIEW)
                openURL.data = Uri.parse(game?.link)
                startActivity(openURL)
            } else if (amount !== null && amount % 2 != 0) {
                val intent = Intent(activity, LinkActivity::class.java)
                intent.putExtra("gameURL", game?.link)
                startActivity(intent)
            }
        }
    }

    private fun handleJson(game: Game) {
        val gameImgView: ImageView? = view?.findViewById(R.id.gameImg)
        gameName.text = game.name
        gameDescription.text = game.description
        Picasso.get().load(game.img).into(gameImgView)
    }

    private fun increment() {
        val pref = context?.getSharedPreferences("COUNT_LINK", Context.MODE_PRIVATE)
        retreviedSaved()?.plus(1)?.let { pref?.edit()?.putInt("LINK_COUNTER_AMOUNT", it)?.apply() }
    }

    private fun retreviedSaved(): Int? {
        val pref = context?.getSharedPreferences("COUNT_LINK", Context.MODE_PRIVATE)
        return pref?.getInt("LINK_COUNTER_AMOUNT", 0)
    }
}
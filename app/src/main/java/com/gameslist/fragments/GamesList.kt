package com.gameslist.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.gameslist.R
import com.gameslist.adapters.GameAdapter
import com.gameslist.network.Game
import kotlinx.android.synthetic.main.fragment_game_details.*
import kotlinx.android.synthetic.main.fragment_games_list.*
import org.json.JSONArray
import org.json.JSONObject


class GamesList : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_games_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refreshGamesList.setOnRefreshListener {
            activity?.applicationContext?.let { fetchGames(it) }
        }
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager


        recyclerView.adapter = GameAdapter(ArrayList()) { game ->
            val detailsFragment = GameDetails()
            val arguments = Bundle()
            arguments.putParcelable("GAME", game)
            detailsFragment.arguments = arguments
            this.activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentContainer, detailsFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        activity?.applicationContext?.let { fetchGames(it) }
    }

    private fun handleJson(jsonArray: JSONArray?) {
        val list = ArrayList<Game>()
        var x = 0

        if (jsonArray != null) {
            while (x < jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(x)

                list.add(
                    Game(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("description"),
                        jsonObject.getString("link"),
                        jsonObject.getString("img")
                    )
                )

                x++
            }

            val adapter = recyclerView.adapter as GameAdapter
            adapter.gamesList = list
            recyclerView.adapter?.notifyDataSetChanged()
            refreshGamesList.isRefreshing = false
        }
    }

    private fun fetchGames(context: Context) {
        val queue = Volley.newRequestQueue(context)
        val request = JsonArrayRequest(Request.Method.GET, URL, null,
            Response.Listener<JSONArray> { response: JSONArray ->
                handleJson(response)
            }, Response.ErrorListener { error -> Log.e("test", error.localizedMessage) })

        queue.add(request)
    }

    companion object {
        const val URL = "https://my-json-server.typicode.com/bgdom/cours-android/games"
    }

}
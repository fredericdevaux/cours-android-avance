package com.gameslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gameslist.fragments.GamesList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val fragmentGamesList = GamesList()
        transaction.add(R.id.fragmentContainer, fragmentGamesList)
        transaction.commit()
    }
}

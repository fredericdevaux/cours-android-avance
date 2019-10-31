package com.gameslist

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class LinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val url = intent.getStringExtra("gameURL")
        val myWebView = WebView(applicationContext)
        myWebView.loadUrl(url)
        setContentView(myWebView)
    }
}
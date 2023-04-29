package com.bignerdranch.android.myprice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.bignerdranch.android.myprice.databinding.ActivityWebviewBinding

class WebviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl("https://www.google.com/")
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
    }
}
package br.com.siso.financasapp

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o layout principal a partir do arquivo main_activity.xml
        setContentView(R.layout.activity_main)

        // Configura a webview e habilita o JavaScript
        webView = findViewById(R.id.webView)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Expõe a classe NativeBridge como objeto na window da WebView
        val nativeBridgeForJavascript = NativeBridge(this)
        webView.addJavascriptInterface(nativeBridgeForJavascript, "NativeBridge")
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        // Carrega o frontend remoto (10.0.2.2 aponta para a maquina hospedeira)
        webView.loadUrl("https://luizimcpi.github.io/sisopwa/")
        webView.webViewClient = WebViewClient()
    }
}
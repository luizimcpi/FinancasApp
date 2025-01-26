package br.com.siso.financasapp

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.asLiveData
import br.com.siso.financasapp.network.ConnectivityFlowObserver
import br.com.siso.financasapp.network.NetworkStatusTracker

private const val INTERNET_CONNECTION_OK = "Internet connection ok"
private const val INTERNET_CONNECTION_DOWN = "Lost Internet connection"

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView
    private lateinit var connectivityFlowObserver: ConnectivityFlowObserver
    private lateinit var networkStatusTracker: NetworkStatusTracker

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

        //https://khush7068.medium.com/how-to-observe-internet-connectivity-in-android-modern-way-with-kotlin-flow-7868a322c806
//        connectivityFlowObserver = ConnectivityFlowObserver(this)
//        connectivityFlowObserver.isConnected.asLiveData().observe(this) { isOnline ->
//            if (isOnline) {
//                Log.d("MainActivity", INTERNET_CONNECTION_OK)
//                Toast.makeText(this, INTERNET_CONNECTION_OK, Toast.LENGTH_SHORT).show()
//            } else {
//                Log.d("MainActivity", INTERNET_CONNECTION_DOWN)
//                Toast.makeText(this, INTERNET_CONNECTION_DOWN, Toast.LENGTH_SHORT).show()
//            }
//        }
        //https://markonovakovic.medium.com/android-better-internet-connection-monitoring-with-kotlin-flow-feac139e2a3
        networkStatusTracker = NetworkStatusTracker(this)
        networkStatusTracker.isConnected.asLiveData().observe(this) {isOnline ->
            if (isOnline) {
                Log.d("MainActivity", INTERNET_CONNECTION_OK)
                Toast.makeText(this, INTERNET_CONNECTION_OK, Toast.LENGTH_SHORT).show()
            } else {
                Log.d("MainActivity", INTERNET_CONNECTION_DOWN)
                Toast.makeText(this, INTERNET_CONNECTION_DOWN, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.example.mangoplate_mock_aos_radi.src.payment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.databinding.ActivityPaymentWebBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealDetailsFragment.Companion.AMOUNT
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealDetailsFragment.Companion.BUYER_NAME
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealDetailsFragment.Companion.DEAL_NAME
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealDetailsFragment.Companion.MERCHANT_UID
import java.net.URISyntaxException
import kotlin.properties.Delegates


@Suppress("CAST_NEVER_SUCCEEDS")
class PaymentWebActivity: Activity() {

    companion object {
        private const val APP_SCHEME = "iamportapp"
    }

    lateinit var binding: ActivityPaymentWebBinding

    var payMerchantUid by Delegates.notNull<Int>()
    lateinit var payBuyerName: String
    lateinit var payDealName: String
    var payAmount by Delegates.notNull<Int>()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 받기
        if (intent.hasExtra(MERCHANT_UID)) payMerchantUid = intent.getIntExtra(MERCHANT_UID, 0)
        if (intent.hasExtra(BUYER_NAME)) payBuyerName = intent.getStringExtra(BUYER_NAME).toString()
        if (intent.hasExtra(DEAL_NAME)) payDealName = intent.getStringExtra(DEAL_NAME).toString()
        if (intent.hasExtra(AMOUNT)) payAmount = intent.getIntExtra(AMOUNT, 0)



        val settings = binding.paymentWeb.settings
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true
        settings.allowFileAccess = true
//        settings.builtInZoomControls = true
//        settings.loadWithOverviewMode = true
//        settings.defaultTextEncodingName = "UTF-8"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            val cookieManager: CookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(binding.paymentWeb, true)
        }

        binding.paymentWeb.setNetworkAvailable(true)
        binding.paymentWeb.webChromeClient = WebChromeClient()
        binding.paymentWeb.webViewClient = WebClient()
        binding.paymentWeb.addJavascriptInterface(JSHandlar(), "Android")
        binding.paymentWeb.loadUrl("file:///android_asset/payment.html")

//        binding.paymentWeb.loadUrl("javascript:request_pay.request_pay()")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val url = intent.toString()
        if (url.startsWith(Companion.APP_SCHEME)) {
            // "iamportapp://https://pgcompany.com/foo/bar"와 같은 형태로 들어옴
            val redirectURL = url.substring(Companion.APP_SCHEME.length + "://".length)
            binding.paymentWeb.loadUrl(redirectURL)
            Log.d(TAG, "onNewIntent: called")
        }
    }

    private inner class WebClient : WebViewClient() {
        @SuppressWarnings("deprecation")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
                var intent: Intent? = null
                return try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME) //IntentURI처리
                    val uri: Uri = Uri.parse(intent.dataString)
//                    ContextCompat.startActivity(Intent(Intent.ACTION_VIEW, uri)) //해당되는 Activity 실행
                    startActivity(Intent(Intent.ACTION_VIEW, uri)) //해당되는 Activity 실행
                    true
                } catch (ex: URISyntaxException) {
                    false
                } catch (e: ActivityNotFoundException) {
                    if (intent == null) return false
//                if (handleNotFoundPaymentScheme(intent.scheme)) return true //설치되지 않은 앱에 대해 사전 처리(Google Play이동 등 필요한 처리)
                    val packageName = intent.getPackage()
                    if (packageName != null) { //packageName이 있는 경우에는 Google Play에서 검색을 기본
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                        return true
                    }
                    false
                }
            }
            return false
        }

        // From api level 24
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
            // Get the tel: url
            val url = request.url.toString()
            if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
                var intent: Intent? = null
                return try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME) //IntentURI처리
                    val uri: Uri = Uri.parse(intent.dataString)
                    startActivity(Intent(Intent.ACTION_VIEW, uri)) //해당되는 Activity 실행
                    true
                } catch (ex: URISyntaxException) {
                    false
                } catch (e: ActivityNotFoundException) {
                    if (intent == null) return false
//                if (handleNotFoundPaymentScheme(intent.scheme)) return true //설치되지 않은 앱에 대해 사전 처리(Google Play이동 등 필요한 처리)
                    val packageName = intent.getPackage()
                    if (packageName != null) { //packageName이 있는 경우에는 Google Play에서 검색을 기본
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                        return true
                    }
                    false
                }
            }
            return false
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        /* 실시간 계좌이체 인증 후 후속처리 루틴 */
        Log.d(TAG, "onActivityResult: $requestCode")
        Log.d(TAG, "onActivityResult: $resultCode")
        Log.d(TAG, "onActivityResult: $data")
        Log.d(TAG, "onActivityResult: called")
    }

    private inner class JSHandlar {

        @android.webkit.JavascriptInterface
        fun getMerchantUid(): Int {
            return payMerchantUid
        }
        @android.webkit.JavascriptInterface
        fun getBuyerName(): String {
            return payBuyerName
        }
        @android.webkit.JavascriptInterface
        fun getDealName(): String {
            return payDealName
        }
        @android.webkit.JavascriptInterface
        fun getAmount(): Int {
            return payAmount
        }
        @android.webkit.JavascriptInterface
        fun showToastSuccess() {
            Handler().post {
                Log.d(TAG, "showToastSuccess:")
                Toast.makeText(applicationContext, "결제 성공", Toast.LENGTH_SHORT).show()
            }
            onBackPressed()
        }
        @android.webkit.JavascriptInterface
        fun showToastFailure() {
            Handler().post {
                Log.d(TAG, "showToastFailure:")
                Toast.makeText(applicationContext, "결제 실패 ", Toast.LENGTH_SHORT).show()
            }
            onBackPressed()

        }

    }

}








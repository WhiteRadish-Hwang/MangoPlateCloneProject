package com.example.mangoplate_mock_aos_radi.config

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.mangoplate_mock_aos_radi.src.main.location.model.LocSelectRecyclerItems
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class ApplicationClass : Application() {
    val API_URL = "https://test.sungjun.site"
    private val gson = GsonBuilder().setLenient().create()

    // 테스트 서버 주소
    // val API_URL = "http://dev-api.test.com/"

    // 실 서버 주소
    // val API_URL = "http://api.test.com/"

    // 코틀린의 전역변수 문법
    companion object {
        val TAG = "로그"
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferences
        val MANGO_PLATE_APP = "MANGO_PLATE_APP"
        // Token Header 키 값
        var X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

        // Retrofit 인스턴스, 앱 실행시 한번만 생성하여 사용합니다.
        lateinit var sRetrofit: Retrofit

        lateinit var instance: ApplicationClass
        private set

        // 변수
        val KAKAO_ID = "kakao_id"
        val KAKAO_IMG = "kakao_img"
        val KAKAO_LOGIN = "kakao_login"
        val FB_ID = "fb_id"
        val FB_LOGIN = "fb_login"
        var sortPivotSelect = "평점순"

        var user_name: String? = null
        var user_id: String? = null
        var profileImageUrl: String? = null
        var isKakaoLogin: Boolean = false
        var isFacebookLogin: Boolean = false
        var restaurantListSize by Delegates.notNull<Int>()
        var topListSize by Delegates.notNull<Int>()
        var isLogin: Boolean = false
        var isGetMyInfo: Boolean = false

        //news filter 변수
        var isGreat: Boolean = true
        var isGood: Boolean = true
        var isBad: Boolean = true
        var fGreat = 0
        var fGood = 0
        var fBad = 0

        var recentLocList = ArrayList<LocSelectRecyclerItems>()

        var isOnFragment: Boolean = false

        val getUserInfo = "/users/:${user_id}"
    }

    // 앱이 처음 생성되는 순간, SP를 새로 만들어주고, 레트로핏 인스턴스를 생성합니다.
    override fun onCreate() {
        super.onCreate()
        instance = this

        KakaoSdk.init(this, "e09a9818400185704ff8efecda465d5a")

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        sSharedPreferences = applicationContext.getSharedPreferences(MANGO_PLATE_APP, MODE_PRIVATE)
        // 레트로핏 인스턴스 생성
        initRetrofitInstance()
    }

    // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
    // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .writeTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}
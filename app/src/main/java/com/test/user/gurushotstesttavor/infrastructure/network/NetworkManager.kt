package com.test.user.tavorapplication.infrastructure.network


import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.test.user.gurushotstesttavor.BuildConfig


import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier

class NetworkManager {

    companion object {
        private var instanceNetworkManager: NetworkManager? = null
        const val X_API_VERSION = "X-API-VERSION"
        const val TWENTY = "20"
        const val X_ENV ="X-ENV"
        const val ANDROID ="ANDROID"

        val instance: NetworkManager
            get() {

                if (instanceNetworkManager == null) instanceNetworkManager = NetworkManager()
                return instanceNetworkManager!!
            }
    }

    fun <T> registerController(controller: ApiController<T>): T {
        return getBaseApi(controller.getEndpoint()).create(controller.getApiClass())
    }

    /**
     * Function that init the retrofit object
     * @param url
     */
    private fun getBaseApi(url: String): Retrofit {

        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(getHeadersInterceptor( ))
            .addNetworkInterceptor( StethoInterceptor())

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            okHttpBuilder.addInterceptor(logging)
       }
        val client = okHttpBuilder.build()

        val gson = GsonBuilder()
            .serializeNulls()
            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        val builder = Retrofit.Builder()

        builder.baseUrl(url)

        return builder
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .addCallAdapterFactory(ObserveOnMainCallAdapterFactory(AndroidSchedulers.mainThread()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    /**
     *  Add headers to the request
     */
    private fun getHeadersInterceptor(): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded")
                builder.addHeader(X_API_VERSION, TWENTY)
                builder.addHeader(X_ENV, ANDROID)
            chain.proceed(builder.build())
        }
    }
}
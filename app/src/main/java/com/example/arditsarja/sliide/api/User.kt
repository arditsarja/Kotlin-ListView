package com.example.arditsarja.sliide.api

import android.os.SystemClock
import com.example.arditsarja.sliide.App
import com.example.arditsarja.sliide.model.Response
import com.example.arditsarja.sliide.model.ResponseCreate
import com.example.arditsarja.sliide.user.UserCreate
import io.reactivex.Observable
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*


interface User {

    @GET("users")
    @Headers("Content-Type: application/json")
    fun search(@Query("page") page: Int): Observable<Response>

    @POST("users")
    @Headers("Content-Type: application/json")
    fun create(@Body user: UserCreate): Observable<ResponseCreate>

    @DELETE("users/{id}")
    @Headers("Content-Type: application/json")
    fun delete(@Path("id") id: Int): Observable<Objects>

    companion object {
        fun create(): User {

            val builder = OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", App.Token).build()
                chain.proceed(request)
            }
            val dispatcher = Dispatcher()

            dispatcher.maxRequests = 1

            val interceptor = Interceptor { chain ->
                SystemClock.sleep(2550)
                chain.proceed(chain.request())
            }

            builder.addNetworkInterceptor(interceptor)
            builder.dispatcher(dispatcher)

            var client = builder.build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(App.API)
                    .client(client)
                    .build()

            return retrofit.create(User::class.java)
        }
    }

}
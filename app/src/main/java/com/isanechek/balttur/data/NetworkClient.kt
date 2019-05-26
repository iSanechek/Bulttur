package com.isanechek.balttur.data

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume

interface NetworkClient {
    suspend fun request(url: String): String?
}

class NetworkClientImpl(private val client: OkHttpClient) : NetworkClient {

    override suspend fun request(url: String): String? = suspendCancellableCoroutine { c ->
        val r = Request.Builder().url(url).build()
        client.newCall(r).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                c.resume(null)
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string()
                if (result != null) {
                    c.resume(result)
                } else {
                    c.resume(null)
                }
            }
        })
    }
}
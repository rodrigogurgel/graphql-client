package br.com.rodrigogurgel.clients

import br.com.rodrigogurgel.GraphQLHttpClient
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class GraphQLOkHttpClient(
    private val okHttpClient: OkHttpClient,
) : GraphQLHttpClient {

    override fun execute(url: String, requestBody: String, headers: Map<String, String>?): String {
        val requestHeaders = Headers.Builder().let { builder ->
            headers?.forEach {
                builder.add(it.key, it.value)
            }
            builder
        }.build()

        val okHttpRequest = Request.Builder()
            .url(url)
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .headers(requestHeaders)
            .build()

        val response = okHttpClient.newCall(okHttpRequest).execute()

        return response.body?.string() ?: throw Exception()
    }
}
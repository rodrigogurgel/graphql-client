package br.com.rodrigogurgel.clients

import br.com.rodrigogurgel.GraphQLHttpClient
import br.com.rodrigogurgel.GraphQLParser
import br.com.rodrigogurgel.GraphQLRequest
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

internal class GraphQLOkHttpClient(
    private val okHttpClient: OkHttpClient,
    private val parser: GraphQLParser,
) : GraphQLHttpClient {

    override fun execute(url: String, request: GraphQLRequest, headers: Map<String, String>?): String {
        val requestHeaders = Headers.Builder().let { builder ->
            headers?.forEach {
                builder.add(it.key, it.value)
            }
            builder
        }.build()

        val requestBody = parser.parseRequestBody(request).toRequestBody("application/json".toMediaType())

        val okHttpRequest = Request.Builder()
            .url(url)
            .post(requestBody)
            .headers(requestHeaders)
            .build()

        val response = okHttpClient.newCall(okHttpRequest).execute()

        return response.body?.string() ?: throw Exception()
    }
}
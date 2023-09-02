package br.com.rodrigogurgel

interface GraphQLHttpClient {
    fun execute(url: String, requestBody: String, headers: Map<String, String>?): String
}

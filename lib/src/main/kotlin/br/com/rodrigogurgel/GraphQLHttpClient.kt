package br.com.rodrigogurgel

interface GraphQLHttpClient {
    fun execute(url: String, request: GraphQLRequest, headers: Map<String, String>?): String
}

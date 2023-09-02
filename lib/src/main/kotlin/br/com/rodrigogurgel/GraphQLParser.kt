package br.com.rodrigogurgel

interface GraphQLParser {
    fun parseRequestBody(request: GraphQLRequest): String
    fun <T> parseResponse(type: Class<T>, responseBody: String): GraphQLResponse<T>
}

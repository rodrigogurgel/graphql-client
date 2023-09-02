package br.com.rodrigogurgel

data class GraphQLResponse<T>(
    val data: T? = null,
    val extensions: Map<String, Any>? = null,
)
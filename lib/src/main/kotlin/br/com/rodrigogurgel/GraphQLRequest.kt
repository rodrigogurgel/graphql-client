package br.com.rodrigogurgel

data class GraphQLRequest(
    val query: String,
    val variables: Map<String, Any>? = null,
)
package br.com.rodrigogurgel

class GraphQLClient internal constructor(
    private val graphQLHttpClient: GraphQLHttpClient,
    private val parser: GraphQLParser,
) {

    fun <T> execute(
        type: Class<T>,
        url: String,
        request: GraphQLRequest,
        headers: Map<String, String>? = null,
    ): GraphQLResponse<T> {
        val response = graphQLHttpClient.execute(url, request.parseRequestBody(), headers)
        return parser.parseResponse(type, response)
    }

    inline fun <reified T> execute(
        url: String,
        request: GraphQLRequest,
        headers: Map<String, String>? = null,
    ): GraphQLResponse<T> = execute(T::class.java, url, request, headers)

    private fun GraphQLRequest.parseRequestBody(): String {
        return parser.parseRequestBody(this)
    }

    class Builder {
        private lateinit var graphQLHttpClient: GraphQLHttpClient
        private lateinit var graphQLParser: GraphQLParser

        fun build(): GraphQLClient {
            return GraphQLClient(graphQLHttpClient, graphQLParser)
        }

        fun graphQLHttpClient(graphQLHttpClient: GraphQLHttpClient): Builder {
            this.graphQLHttpClient = graphQLHttpClient
            return this
        }

        fun graphQLParser(graphQLParser: GraphQLParser): Builder {
            this.graphQLParser = graphQLParser
            return this
        }
    }
}
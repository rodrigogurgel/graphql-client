package br.com.rodrigogurgel

import br.com.rodrigogurgel.clients.GraphQLOkHttpClient
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import br.com.rodrigogurgel.parsers.GraphQLObjectMapper

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
        val response = graphQLHttpClient.execute(url, request, headers)
        return parser.parseResponse(type, response)
    }

    inline fun <reified T> execute(
        url: String,
        request: GraphQLRequest,
        headers: Map<String, String>? = null,
    ): GraphQLResponse<T> = execute(T::class.java, url, request, headers)

    class OkHttpBuilder {
        private var okHttpClient = OkHttpClient()
        private var graphQLParser: GraphQLParser = GraphQLObjectMapper(jacksonObjectMapper())

        fun build(): GraphQLClient {
            return GraphQLClient(GraphQLOkHttpClient(okHttpClient, graphQLParser), graphQLParser)
        }

        fun okhttpClient(okHttpClient: OkHttpClient): OkHttpBuilder {
            this.okHttpClient = okHttpClient
            return this
        }

        fun graphQLParser(graphQLParser: GraphQLParser): OkHttpBuilder {
            this.graphQLParser = graphQLParser
            return this
        }
    }
}
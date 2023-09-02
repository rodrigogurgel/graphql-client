package br.com.rodrigogurgel.parsers

import br.com.rodrigogurgel.GraphQLParser
import br.com.rodrigogurgel.GraphQLRequest
import br.com.rodrigogurgel.GraphQLResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class GraphQLObjectMapper(private val objectMapper: ObjectMapper) : GraphQLParser {
    override fun parseRequestBody(request: GraphQLRequest): String {
        return objectMapper.writeValueAsString(request)
    }

    override fun <T> parseResponse(type: Class<T>, responseBody: String): GraphQLResponse<T> {
        val graphQLResponse = objectMapper.readValue(responseBody, object : TypeReference<GraphQLResponse<T>>() {})
        val data = objectMapper.convertValue(graphQLResponse.data, type)
        return graphQLResponse.copy(data = data)
    }
}
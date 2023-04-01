package com.kantakap.auctionfilehandler.gql;

import java.util.Map;

public class GraphQLRequest {
    private final String query;
    private final Map<String, Object> variables;

    public GraphQLRequest(String query, Map<String, Object> variables) {
        this.query = query;
        this.variables = variables;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"query\": \"").append(query).append("\"");
        if (!variables.isEmpty()) {
            sb.append(", variables: ").append(variables);
        }
        sb.append("}");
        return sb.toString();
    }
}

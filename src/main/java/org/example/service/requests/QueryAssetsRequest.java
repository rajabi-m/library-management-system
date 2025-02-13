package org.example.service.requests;

public class QueryAssetsRequest extends Request{
    private final String query;

    public QueryAssetsRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}

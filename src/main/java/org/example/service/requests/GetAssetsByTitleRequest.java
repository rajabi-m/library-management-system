package org.example.service.requests;

public class GetAssetsByTitleRequest extends Request{
    private final String title;

    public GetAssetsByTitleRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

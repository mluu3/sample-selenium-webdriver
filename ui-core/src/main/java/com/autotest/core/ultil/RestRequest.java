package com.autotest.core.ultil;

import org.apache.http.client.methods.*;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public class RestRequest {

    public static HttpGet initGetRequest(String uri) {
        HttpGet getMethod = new HttpGet(uri);
        setAcceptHeader(getMethod);
        return getMethod;
    }

    public static HttpDelete initDeleteRequest(String uri) {
        HttpDelete deleteMethod = new HttpDelete(uri);
        setAcceptHeader(deleteMethod);
        return deleteMethod;
    }

    public static HttpPost initPostRequest(String uri, String content) {
        HttpPost postMethod = new HttpPost(uri);
        postMethod.setEntity(getEntity(content));
        setAcceptHeader(postMethod);
        return postMethod;
    }

    public static HttpPut initPutRequest(String uri, String content) {
        HttpPut putMethod = new HttpPut(uri);
        putMethod.setEntity(getEntity(content));
        setAcceptHeader(putMethod);
        return putMethod;
    }

    private static AbstractHttpEntity getEntity(String content) {
        return new StringEntity(content, ContentType.APPLICATION_JSON);
    }

    private static void setAcceptHeader(HttpRequestBase requestBase) {
        requestBase.addHeader("Accept", ContentType.APPLICATION_JSON.getMimeType());
    }
}

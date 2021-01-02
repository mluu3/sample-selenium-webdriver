package test.java.ultil;

import static java.util.Objects.isNull;
import static test.java.ultil.RestRequest.initGetRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class CommonRestRequest {

    private static final Logger log = Logger.getLogger(CommonRestRequest.class.getName());

    protected RestClient restClient;

    public CommonRestRequest(RestClient restClient, String projectId) {
        this.restClient = restClient;
    }

    public CommonRestRequest(RestClient restClient) {
        this.restClient = restClient;
    }

    public RestClient getRestClient() {
        return restClient;
    }

    /**
     * Execute request
     *
     * @param request
     * @return status code
     */
    public int executeRequest(HttpRequestBase request) {
        try {
            HttpResponse response = restClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            EntityUtils.consumeQuietly(response.getEntity());
            return statusCode;
        } finally {
            request.releaseConnection();
        }
    }

    /**
     * Execute request with expected status code
     *
     * @param request
     * @param expectedStatusCode
     */
    public void executeRequest(HttpRequestBase request, HttpStatus expectedStatusCode) {
        try {
            final HttpResponse response = restClient.execute(request, expectedStatusCode);
            EntityUtils.consumeQuietly(response.getEntity());
        } finally {
            request.releaseConnection();
        }
    }

    /**
     * Get resource from request with expected status code
     *
     * @param request
     * @param setupRequest        setup request before executing like configure header, ...
     * @param expectedStatusCode
     * @return entity from response in String form
     */
    public String getResource(HttpRequestBase request, Consumer<HttpRequestBase> setupRequest,
                              HttpStatus expectedStatusCode) throws ParseException, IOException {
        setupRequest.accept(request);
        try {
            final HttpResponse response = restClient.execute(request, expectedStatusCode);
            final HttpEntity entity = response.getEntity();

            final String ret = isNull(entity) ? "" : EntityUtils.toString(entity);
            EntityUtils.consumeQuietly(entity);
            return ret;

        } finally {
            request.releaseConnection();
        }
    }

    /**
     * Get resource from request with expected status code
     *
     * @param request
     * @param expectedStatusCode
     * @return entity from response in json form
     */
    public String getResource(HttpRequestBase request, HttpStatus expectedStatusCode) throws ParseException, IOException {
        return getResource(request, req -> req.setHeader(
                "Accept", ContentType.APPLICATION_JSON.getMimeType()), expectedStatusCode);
    }

    /**
     * Get resource from uri with expected status code
     *
     * @param uri
     * @param expectedStatusCode
     * @return entity from response in json form
     */
    public String getResource(String uri, HttpStatus expectedStatusCode) throws ParseException, IOException {
        return getResource(initGetRequest(uri), req -> req.setHeader(
                "Accept", ContentType.APPLICATION_JSON.getMimeType()), expectedStatusCode);
    }
}

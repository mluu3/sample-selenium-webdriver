package test.java.ultil;

import static java.lang.String.format;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class RestClient {

    private final static int DEFAULT_PORT = 443;

    private RestProfile profile;
    private HttpClient client;

    public RestClient(RestProfile profile) {
        this(profile, null);
    }

    public RestClient(RestProfile profile, HttpRequestRetryHandler handler) {
        this.profile = profile;

        client = getGooddataHttpClient(profile.getUsername(), profile.getPassword(), handler);
    }

    public HttpResponse execute(HttpRequestBase request) {
        try {
            return client.execute(profile.getHost(), request);
        } catch (IOException e) {
            throw new RuntimeException("Cannot execute request", e);
        }
    }

    public HttpResponse execute(HttpRequestBase request, int expectedStatusCode) {
        try {
            ResponseHandler<HttpResponse> handler = response -> {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != expectedStatusCode) {
                    String errorMsg = format("%s expected code [%d], but got [%d]",
                            request.getURI(), expectedStatusCode, statusCode);

                    // get error msg in response if any
                    String content = getResponseError(response);
                    if (StringUtils.isNotEmpty(content)) {
                        errorMsg += "\n" + content;
                    }
                    System.out.println(errorMsg);
                }
                return response;
            };

            return client.execute(profile.getHost(), request, handler);

        } catch (IOException e) {
            throw new RuntimeException("Cannot execute request", e);
        }
    }

    public HttpResponse execute(HttpRequestBase request, HttpStatus expectedStatus) {
        return execute(request, expectedStatus.value());
    }

    private static HttpHost parseHost(String host) {
        String[] parts = host.split(":");
        String hostName = parts[0];
        int port;
        if (parts.length == 2) {
            port = Integer.parseInt(parts[1]);
        } else {
            port = DEFAULT_PORT;
        }
        return new HttpHost(hostName, port, "https");
    }

    private HttpClient getGooddataHttpClient(String user, String password,
                                             HttpRequestRetryHandler retryHandler) {
        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setRetryHandler(retryHandler);
        httpClientBuilder.setSSLHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        final CredentialsProvider provider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        httpClientBuilder.setDefaultCredentialsProvider(provider);
        return httpClientBuilder.build();
    }

    private String getResponseError(HttpResponse response) {
        String content = "";
        try {
            content = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            JSONObject jsonObject = new JSONObject(content);
            if (jsonObject.has("error")) {
                return content;
            }
            return "";
        } catch (IOException ioe) {
            throw new RuntimeException("There is an error while reading response", ioe);
        } catch (JSONException je) {
            // Incase a bad request(400) returned, response content is html not json
            return content;
        }
    }

    public static class RestProfile {
        private HttpHost host;
        private String username;
        private String password;

        public RestProfile(String host, String username, String password) {
            this.host = parseHost(host);
            this.username = username;
            this.password = password;
        }

        public void setHost(String host) {
            this.host = parseHost(host);
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public HttpHost getHost() {
            return host;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}

package test.java.weather;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import test.java.abstracts.AbstractProjectTest;
import test.java.ultil.RestRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class APITest extends AbstractProjectTest {

    @Test(dependsOnGroups = "createProject")
    public void searchAPITest() throws IOException {
        //Public credentials & basic case, so that don't design security
        HttpGet httpGet = RestRequest.initGetRequest("https://api.openweathermap.org/data/2.5/weather?q=Nha+Trang&appid=c6d2227a28557c41495c6f56dbd9c978");
        HttpResponse httpResponse = getRestClient().execute(httpGet);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.OK.value());

        String content = IOUtils.toString(httpResponse.getEntity().getContent(), "UTF-8");
        JSONObject jsonObject = new JSONObject(content);
        //stable data
        assertEquals(jsonObject.get("name"), "Nha Trang");
        assertEquals(jsonObject.getJSONObject("coord").get("lon"), 109.1833);
        assertEquals(jsonObject.getJSONObject("coord").get("lat"), 12.25);
        //unstable data
        assertTrue(jsonObject.has("weather"), "Weather should be included");
        assertTrue(jsonObject.has("clouds"), "Clouds should be included");
        assertTrue(jsonObject.has("wind"), "Wind should be included");
    }

    @Test(dependsOnGroups = "createProject")
    public void searchWithOutResultAPITest() throws IOException {
        //Public credentials & basic case, so that don't design security
        HttpGet httpGet = RestRequest.initGetRequest("https://api.openweathermap.org/data/2.5/weather?q=ab&appid=c6d2227a28557c41495c6f56dbd9c978");
        HttpResponse httpResponse = getRestClient().execute(httpGet);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.NOT_FOUND.value());

        String content = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(content);
        assertEquals(jsonObject.get("message"), "city not found");
    }
}

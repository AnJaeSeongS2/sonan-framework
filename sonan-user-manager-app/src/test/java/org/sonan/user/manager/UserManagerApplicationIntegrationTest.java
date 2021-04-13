package org.sonan.user.manager;

import org.sonan.framework.container.lifecycle.StaticLifeCycleEventBus;
import org.sonan.framework.container.throwable.BootingFailException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserManagerApplicationIntegrationTest {

    @Test
    @BeforeAll
    public static void startApplication() throws InterruptedException, BootingFailException {
        CountDownLatch latch = new CountDownLatch(1);
        StaticLifeCycleEventBus.put(lifeCycle -> latch.countDown());
        UserManagerApplication.main(new String[] {});
        latch.await();
    }

    @Test
    @DisplayName("Integration Sample")
    void homeTest() throws InterruptedException, IOException, BootingFailException {
        HttpGet request = new HttpGet("http://localhost:8080");
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);
    }


    @Test
    @DisplayName("Post x 4, Get, Put, Delete, GetAll")
    void crudTest() throws InterruptedException, IOException, BootingFailException {
        HttpPost postRequest = new HttpPost("http://localhost:8080/users");
        String postBody = "{\n" +
                "    \"name\": \"created1\",\n" +
                "    \"address\": \"created1\"\n" +
                "}";
        StringEntity requestEntity = new StringEntity(postBody, "utf-8");
        requestEntity.setContentType(new BasicHeader("Content-Type", "application/json"));
        postRequest.setEntity(requestEntity);
        CloseableHttpResponse postResponse = HttpClientBuilder.create().build().execute(postRequest);
        assertEquals(HttpStatus.SC_OK, postResponse.getStatusLine().getStatusCode());

        HttpClientBuilder.create().build().execute(postRequest);
        HttpClientBuilder.create().build().execute(postRequest);
        HttpClientBuilder.create().build().execute(postRequest);

        HttpGet getRequest = new HttpGet("http://localhost:8080/users/@1");
        CloseableHttpResponse getResponse = HttpClientBuilder.create().build().execute(getRequest);
        assertEquals(HttpStatus.SC_OK, getResponse.getStatusLine().getStatusCode());

        HttpPut putRequest = new HttpPut("http://localhost:8080/users/@2");
        String putBody = "{\n" +
                "    \"name\": \"updated2\",\n" +
                "    \"address\": \"updated2\"\n" +
                "}";
        StringEntity requestEntityPut = new StringEntity(putBody, "utf-8");
        requestEntityPut.setContentType(new BasicHeader("Content-Type", "application/json"));
        putRequest.setEntity(requestEntityPut);
        CloseableHttpResponse putResponse = HttpClientBuilder.create().build().execute(putRequest);
        assertEquals(HttpStatus.SC_OK, putResponse.getStatusLine().getStatusCode());

        HttpDelete deleteRequest = new HttpDelete("http://localhost:8080/users/@1");
        CloseableHttpResponse deleteResponse = HttpClientBuilder.create().build().execute(deleteRequest);
        assertEquals(HttpStatus.SC_OK, deleteResponse.getStatusLine().getStatusCode());

        HttpGet getRequestAll = new HttpGet("http://localhost:8080/users");
        CloseableHttpResponse getResponseAll = HttpClientBuilder.create().build().execute(getRequestAll);
        assertEquals(HttpStatus.SC_OK, getResponseAll.getStatusLine().getStatusCode());
    }
}
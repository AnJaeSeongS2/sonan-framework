package com.mission.your;

import com.woowahan.framework.container.lifecycle.StaticLifeCycleEventBus;
import com.woowahan.framework.container.throwable.BootingFailException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissionIntegrationTest {

    @Test
    @BeforeAll
    public static void startApplication() throws InterruptedException, BootingFailException {
        CountDownLatch latch = new CountDownLatch(1);
        StaticLifeCycleEventBus.put(lifeCycle -> latch.countDown());
        MissionApplication.main(new String[] {});
        latch.await();
    }

    @Test
    @DisplayName("Integration Sample")
    void mission1() throws InterruptedException, IOException, BootingFailException {
        HttpGet request = new HttpGet("http://localhost:8080");
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);
    }


    @Test
    @DisplayName("Post x 4, Get, Put, Delete, GetAll")
    void crudTest() throws InterruptedException, IOException, BootingFailException {
        HttpPost postRequest = new HttpPost("http://localhost:8080/shops");
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

        HttpGet getRequest = new HttpGet("http://localhost:8080/shops/@1");
        CloseableHttpResponse getResponse = HttpClientBuilder.create().build().execute(getRequest);
        assertEquals(HttpStatus.SC_OK, postResponse.getStatusLine().getStatusCode());

        // TODO: make tests
    }
}
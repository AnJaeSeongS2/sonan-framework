package com.mission.your;

import com.woowahan.framework.container.lifecycle.StaticLifeCycleEventBus;
import com.woowahan.framework.container.throwable.BootingFailException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class MissionIntegrationTest {

    @Test
    @DisplayName("Integration Sample")
    void mission1() throws InterruptedException, IOException, BootingFailException {
        startApplication();

        HttpGet request = new HttpGet("http://localhost:8080");
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);
    }

    private void startApplication() throws InterruptedException, BootingFailException {
        CountDownLatch latch = new CountDownLatch(1);
        StaticLifeCycleEventBus.put(lifeCycle -> latch.countDown());
        MissionApplication.main(new String[] {});
        latch.await();
    }
}
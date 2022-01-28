package com.github.messengers.configs;

import com.github.messengers.services.InfobipService;
import com.infobip.ApiClient;
import com.infobip.api.SendSmsApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@ConditionalOnExpression(value = "${messengers.infobip.enable:true}")
public class InfobipConfig {

    @Value(value = "${messengers.infobip.url-base-path}")
    private String urlBasePath;

    @Value(value = "${messengers.infobip.client-id}")
    private String clientId;

    @Value(value = "${messengers.infobip.client-secret}")
    private String clientSecret;

    @Bean
    public ApiClient apiClient() {
        ApiClient client = new ApiClient(this.urlBasePath, this.clientId, this.clientSecret, Collections.emptyMap());
        com.infobip.Configuration.setDefaultApiClient(client);
        return client;
    }

    @Bean
    public InfobipService infobipService(ApiClient apiClient) {
        return new InfobipService(new SendSmsApi(apiClient));
    }

}

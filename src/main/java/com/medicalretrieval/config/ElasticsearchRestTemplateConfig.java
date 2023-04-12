package com.medicalretrieval.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * <pre>elasticsearch集群的配置信息</pre>
 * @author 梁宏凯
 *
 */
@Configuration
public class ElasticsearchRestTemplateConfig extends AbstractElasticsearchConfiguration {

    @Value("${spring.elasticsearch.rest.uris}")
    private String[] uris;


    @Override
    public @NotNull RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(uris)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}

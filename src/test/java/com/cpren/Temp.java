package com.cpren;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class Temp {
//    @Autowired
//    private ObjectMapper objectMapper;

    @Test
    public void rcp() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

            // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest request = new CreateIndexRequest("twitter");

        // 2、设置索引的settings
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );

            // 3、设置索引的mappings
        request.mapping(
                "{\n" +
                        "  \"properties\": {\n" +
                        "    \"message\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);

        try {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//            System.out.println(objectMapper.writeValueAsString(createIndexResponse));
            System.out.println(createIndexResponse.index());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
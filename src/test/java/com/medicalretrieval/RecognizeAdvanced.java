// This file is auto-generated, don't edit it. Thanks.
package com.medicalretrieval;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.ocr_api20210707.*;
import com.aliyun.sdk.service.ocr_api20210707.models.*;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;

import java.util.concurrent.CompletableFuture;

public class RecognizeAdvanced {
    public static void main(String[] args) throws Exception {

        // HttpClient Configuration

        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId("LTAI5t8dqBhJNhj1gci9MCMq")
                .accessKeySecret("Y5S4wCiaIAMhOvLvU2yL0RQ3I4Ecny")
                //.securityToken("<your-token>") // use STS token
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("ocr-api.cn-hangzhou.aliyuncs.com")
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();

        // Parameter settings for API request
        RecognizeAdvancedRequest recognizeAdvancedRequest = RecognizeAdvancedRequest.builder()
                .url("https://uploadfile.bizhizu.cn/2017/0722/20170722032928988.jpg")
                .outputCharInfo(false)
                .outputTable(true)
                .needSortPage(false)
                .outputFigure(true)
                .noStamp(true)
                .paragraph(false)
                .row(true)
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        // Asynchronously get the return value of the API request
        CompletableFuture<RecognizeAdvancedResponse> response = client.recognizeAdvanced(recognizeAdvancedRequest);
        // Synchronously get the return value of the API request
        RecognizeAdvancedResponse resp = response.get();
        System.out.println(new Gson().toJson(resp));
        JSONObject jsonObject = JSONObject.parseObject(new Gson().toJson(resp));

        String str = (String) jsonObject.getJSONObject("body").get("data");
        JSONObject jsonObject1 = JSONObject.parseObject(str);
        System.out.println(jsonObject1.get("content"));
        // Asynchronous processing of return values

        // Finally, close the client
        client.close();
    }

}
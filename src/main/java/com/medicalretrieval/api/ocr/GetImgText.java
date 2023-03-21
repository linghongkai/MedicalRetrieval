package com.medicalretrieval.api.ocr;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.ocr_api20210707.AsyncClient;
import com.aliyun.sdk.service.ocr_api20210707.models.RecognizeAdvancedRequest;
import com.aliyun.sdk.service.ocr_api20210707.models.RecognizeAdvancedResponse;
import com.google.gson.Gson;
import com.medicalretrieval.pojo.elasticsearch.ImgInfo;
import darabonba.core.client.ClientOverrideConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <pre>获取图片中的文本，调用静态方法</pre>
 * @author 梁宏凯
 *
 */
final public class GetImgText {

    /**
     * <pre>一页pdf中可能有多张图片。所以是...Imgs</pre>
     * @param Imgs 一组图片的url地址。
     * @return 以链表的形式返回这组图片的文本。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    //调用客户端
    @Contract(pure = true)
    public static @NotNull List<ImgInfo> getText(List<String>Imgs) throws ExecutionException, InterruptedException {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId("LTAI5t8dqBhJNhj1gci9MCMq")
                .accessKeySecret("Y5S4wCiaIAMhOvLvU2yL0RQ3I4Ecny")
                .build());
        // Region ID
        AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("ocr-api.cn-hangzhou.aliyuncs.com")
                )
                .build();
        List<ImgInfo> imgInfos = new ArrayList<ImgInfo>();
        int cnt = 1;
        for (String img:
             Imgs) {
            RecognizeAdvancedRequest recognizeAdvancedRequest = RecognizeAdvancedRequest.builder()

                    .url(img)
                    .outputCharInfo(false)
                    .outputTable(true)
                    .needSortPage(false)
                    .outputFigure(true)
                    .noStamp(true)
                    .paragraph(false)
                    .row(true)
                    .build();

            CompletableFuture<RecognizeAdvancedResponse> response = client.recognizeAdvanced(recognizeAdvancedRequest);
            RecognizeAdvancedResponse resp = response.get();
            JSONObject jsonObject = JSONObject.parseObject(new Gson().toJson(resp));

            String str = (String) jsonObject.getJSONObject("body").get("data");
            JSONObject jsonObject1 = JSONObject.parseObject(str);

            System.out.println(jsonObject1.get("content"));//图片文本的主体
            imgInfos.add(new ImgInfo((long) cnt++,img,(String) jsonObject1.get("content")));
        }
        client.close();
        return imgInfos;
    }

}

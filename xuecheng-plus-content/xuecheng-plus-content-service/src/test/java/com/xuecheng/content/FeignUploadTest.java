package com.xuecheng.content;

import com.xuecheng.content.config.MultipartSupportConfig;
import com.xuecheng.content.feignclient.MediaServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@SpringBootTest(properties = {"xxl.job.enabled=false"})
public class FeignUploadTest {

    @Autowired
    private MediaServiceClient mediaServiceClient;

    @Test
    public void test() {
        // 将file转成MultipartFile
        MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(new File("D:\\develop\\upload\\120.html"));
        // 远程调用
        mediaServiceClient.uploadFile(multipartFile,"course/120.html");
    }

}

package com.xuecheng.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 测试 MinIo
 */
public class MinioTest {
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.65:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
    // 上传文件
    @Test
    public void upload() {
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".txt");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流
        if(extensionMatch!=null){
            mimeType = extensionMatch.getMimeType();
            System.out.println(mimeType);
        }
        try {
            UploadObjectArgs testbucket = UploadObjectArgs.builder()
                    .bucket("testbucket")
                    .object("test001.txt")
                    .object("001/test001.txt") //添加子目录
                    .filename("D:\\work\\xuecheng-doc\\testMinIo.txt")
                    .contentType(mimeType).build(); //默认根据扩展名确定文件内容类型，也可以指定
            minioClient.uploadObject(testbucket);
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }
    }

    // 删除文件
    @Test
    public void delete() {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket("testbucket").object("001/test001.txt").build());
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除失败");
        }
    }

    // 查询文件
    @Test
    public void getFile() {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("test001.txt").build();
        try (
                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
                FileOutputStream outputStream = new FileOutputStream(new File("D:\\\\develop\\\\upload\\\\1_2.txt"));
        ) {
            IOUtils.copy(inputStream,outputStream);
//            //校验文件的完整性对文件的内容进行md5
//            FileInputStream fileInputStream1 = new FileInputStream(new File("D:\\develop\\upload\\1.txt"));
//            String source_md5 = DigestUtils.md5Hex(fileInputStream1);
//            FileInputStream fileInputStream = new FileInputStream(new File("D:\\develop\\upload\\1a.txt"));
//            String local_md5 = DigestUtils.md5Hex(fileInputStream);
//            if(source_md5.equals(local_md5)){
//                System.out.println("下载成功");
//            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

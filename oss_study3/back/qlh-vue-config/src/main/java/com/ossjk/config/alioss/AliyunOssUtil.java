package com.ossjk.config.alioss;


import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSUtils;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.config.alioss
 * @ClassName: AliyunOssUtil
 * @Description: 阿里云Oss工具
 * @author: chair
 * @date: 2021/10/6 23:50
 */
@Component
@EnableConfigurationProperties({AliyunOssProperties.class})
@Slf4j
public class AliyunOssUtil {
    @Autowired
    private AliyunOssProperties aliyunOssProperties;


    /**
     * 创建bucket
     *
     * @param bucket
     */
    public String createBucket(String bucket) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        // 创建存储空间。
        ossClient.createBucket(bucket);
        log.info("创建" + bucket + "Bucket成功。");
        ossClient.shutdown();
        return bucket;
    }

    /**
     * 删除bucket
     *
     * @param bucket
     */
    public void deleteBucket(String bucket) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        ossClient.deleteBucket(bucket);
        ossClient.shutdown();
        log.info("删除" + bucket + "Bucket成功。");
    }


    /**
     * 上传文件
     *
     * @param folder
     * @param file
     * @return
     */
    public String putObject(String folder, File file) {
        return this.putObject(aliyunOssProperties.getBucketName(), folder, file, null);
    }

    /**
     * 上传文件
     *
     * @param folder
     * @param file
     * @param metadata
     * @return
     */
    public String putObject(String folder, File file, ObjectMetadata metadata) {
        return this.putObject(aliyunOssProperties.getBucketName(), folder, file, metadata);
    }


    /**
     * 上传文件
     *
     * @param folder
     * @param file
     * @return
     */
    public String putObject(String bucketName, String folder, File file) {
        return this.putObject(bucketName, folder, file, null);
    }

    /**
     * 上传文件
     *
     * @param folder
     * @param file
     * @param metadata
     * @return
     */
    public String putObject(String bucketName, String folder, File file, ObjectMetadata metadata) {
        try {
            return this.putObject(bucketName, folder, file.getName(), new FileInputStream(file), metadata);
        } catch (FileNotFoundException e) {
            log.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return null;
    }


    /**
     * 上传文件
     *
     * @param folder
     * @param fileName
     * @param inputStream
     * @return
     */
    public String putObject(String folder, String fileName, InputStream inputStream) {
        return this.putObject(aliyunOssProperties.getBucketName(), folder, fileName, inputStream, null);
    }

    /**
     * 上传文件
     *
     * @param folder
     * @param fileName
     * @param inputStream
     * @param metadata
     * @return
     */
    public String putObject(String folder, String fileName, InputStream inputStream, ObjectMetadata metadata) {
        return this.putObject(aliyunOssProperties.getBucketName(), folder, fileName, inputStream, metadata);
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param folder
     * @param fileName
     * @param inputStream
     * @return
     */
    public String putObject(String bucketName, String folder, String fileName, InputStream inputStream) {
        return this.putObject(bucketName, folder, fileName, inputStream, null);
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param folder
     * @param fileName
     * @param inputStream
     * @param metadata
     * @return
     */
    public String putObject(String bucketName, String folder, String fileName, InputStream inputStream, ObjectMetadata metadata) {
        String resultStr = null;
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
            if (StrUtil.isNotBlank(folder)) {
                if (folder.indexOf("/") == 0) {
                    folder = folder.replaceFirst("/", "");
                }
                if (folder.lastIndexOf("/") != (folder.length() - 1)) {
                    folder += "/";
                }
            }
            ossClient.putObject(bucketName, folder + fileName, inputStream, metadata);
            resultStr = generateFileUrl(bucketName, folder, fileName);
        } catch (Exception e) {
            log.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return resultStr;
    }

    /**
     * 生成阿里云Oss地址
     *
     * @param bucketName
     * @return
     */
    public String generateFileUrl(String bucketName, String folder, String fileName) {
        return generateRealUrl(bucketName) + "/" + folder + fileName;
    }

    /**
     * 生成阿里云Oss地址
     *
     * @param bucketName
     * @return
     */
    public String generateRealUrl(String bucketName) {
        return "https://" + bucketName + "." + aliyunOssProperties.getEndpoint();
    }

    /**
     * 通过文件Url返回BucketName
     *
     * @param fileUrl
     * @return
     */
    public String getBucketNameWithFileUrl(String fileUrl) {
        return StrUtil.removeAll(fileUrl.substring(0, fileUrl.indexOf(".")), "https://");

    }


    /**
     * 获取文件资源路径
     *
     * @param fileUrl
     * @return
     */
    public String getResourcePath(String fileUrl) {
        String realUrl = generateRealUrl(getBucketNameWithFileUrl(fileUrl));
        String tmp = fileUrl.substring(fileUrl.indexOf(realUrl) + realUrl.length());
        return StrUtil.removePrefix(tmp, "/");
    }


    /**
     * 获取资源流
     *
     * @param fileUrl
     * @return
     */
    public InputStream getResourceInputStream(String fileUrl) {
        OSS ossClient = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        OSSObject ossObject = ossClient.getObject(getBucketNameWithFileUrl(fileUrl), getResourcePath(fileUrl));
        ossClient.shutdown();
        return ossObject.getObjectContent();
    }

    /**
     * 删除oss内容
     *
     * @param fileUrl
     */
    public void deleteObject(String fileUrl) {
        OSS ossClient = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        ossClient.deleteObject(getBucketNameWithFileUrl(fileUrl), getResourcePath(fileUrl));
        ossClient.shutdown();
    }


}

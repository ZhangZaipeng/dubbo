package com.example.common.bean;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.BucketInfo;
import com.example.common.utils.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class OSSFileUploadUtils {
  private static final Log logger= LogFactory.getLog(OSSFileUploadUtils.class);

  private static String fileMaxSize;
  private static String imgFileExtensiom;
  /**
   * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
   */
  private static String accessKeyId;
  private static String accessKeySecret;
  private static String bucketName;

  @Value("${oss.file.maxsize}")
  public void setFileMaxSize(String fileMaxSize) {
    OSSFileUploadUtils.fileMaxSize = fileMaxSize;
  }

  @Value("${oss.file.file_img_extension}")
  public void setImgFileExtensiom(String imgFileExtensiom) {
    OSSFileUploadUtils.imgFileExtensiom = imgFileExtensiom;
  }

  @Value("${oss.accessKeyId}")
  public void setAccessKeyId(String accessKeyId) {
    OSSFileUploadUtils.accessKeyId = accessKeyId;
  }

  @Value("${oss.accessKeySecret}")
  public void setAccessKeySecret(String accessKeySecret) {
    OSSFileUploadUtils.accessKeySecret = accessKeySecret;
  }

  @Value("${oss.bucketName}")
  public void setBucketName(String bucketName) {
    OSSFileUploadUtils.bucketName = bucketName;
  }

  /**
   * Endpoint以杭州为例，其它Region请按实际情况填写。
   */
  private static final String END_POINT = "http://oss-cn-hongkong.aliyuncs.com";

  /**
   * 定义文件名
   */
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

  /**
   * 上传图片
   *
   * @return
   * @throws IOException
   */
  public static UploadResult ossUploadImage(MultipartFile multipartFile) throws IOException {

    if (multipartFile == null || multipartFile.getSize() == 0) {
      throw new IOException("上传文件不存在");
    }

    // 原始文件名
    String fileName = multipartFile.getOriginalFilename();
    if (StringUtils.isNullOrEmpty(fileName)) {
      throw new IOException("非法文件");
    }

    // 文件大小
    long fileSize = multipartFile.getSize();
    if (multipartFile.getSize() > Integer.parseInt(fileMaxSize) * 1024) {
      throw new IOException("上传文件不能超过  " + fileMaxSize + " K");
    }

    String fileExt = "";
    int suffixPos = fileName.lastIndexOf(".");
    if (suffixPos > 0) {
      fileExt = fileName.substring(suffixPos + 1);
    }

    // 格式判断
    if (!imgFileExtensiom.contains(fileExt.toLowerCase())) {
      throw new IOException("非法文件格式" + fileExt.toLowerCase());
    }

    // 判断日期得到文件名
    String baseFilePath = sdf.format(new Date());
    String filePath =  UUID.randomUUID().toString().replace("-", "") + "." + fileExt;
    if (StringUtils.isNullOrEmpty(fileName) || StringUtils.isNullOrEmpty(baseFilePath)) {
      throw new IOException("非法文件路径 " + baseFilePath + "/" +filePath );
    }

    //创建oss实例
    String path = baseFilePath + "/" +filePath;
    ossUpload(path,multipartFile);


    // 网络访问路径
    String fullRelative = "http://" + bucketName +".oss-cn-hongkong.aliyuncs.com/" + baseFilePath + "/" + filePath;

    UploadResult ret = new UploadResult();
    ret.setDfsFilePath(baseFilePath + "/" + filePath);
    ret.setFileName(fileName);
    ret.setFullRelative(fullRelative);
    ret.setFileSize(fileSize);

    logger.error("upload file to server path success: " + fullRelative);

    return ret;
  }

  /**
   * 上传文件
   * @param multipartFile
   * @param allowExtends
   * @return
   * @throws IOException
   */
  public static UploadResult ossUploadFile(MultipartFile multipartFile,
      List<String> allowExtends) throws IOException {
    if (multipartFile == null || multipartFile.getSize() == 0) {
      throw new IOException("上传文件不存在");
    }

    // 原始文件名
    String fileName = multipartFile.getOriginalFilename();
    if (StringUtils.isNullOrEmpty(fileName)) {
      throw new IOException("非法文件");
    }

    // 文件大小
    long fileSize = multipartFile.getSize();
    if (multipartFile.getSize() > Integer.parseInt(fileMaxSize) * 1024) {
      throw new IOException("上传文件不能超过  " + fileMaxSize + " K");
    }

    String fileExt = "";
    int suffixPos = fileName.lastIndexOf(".");
    if (suffixPos > 0) {
      fileExt = fileName.substring(suffixPos + 1);
    }
    if (!allowExtends.contains(fileExt.toLowerCase())) {
      throw new IOException("非法文件格式" + fileExt.toLowerCase());
    }

    // 判断日期得到文件名
    String baseFilePath = sdf.format(new Date());
    String filePath =  UUID.randomUUID().toString().replace("-", "") + "." + fileExt;
    if (StringUtils.isNullOrEmpty(fileName) || StringUtils.isNullOrEmpty(baseFilePath)) {
      throw new IOException("非法文件路径 " + baseFilePath + "/" +filePath );
    }

    String path = baseFilePath + "/" +filePath;
    ossUpload(path,multipartFile);


    // 网络访问路径https://ex-file.oss-cn-hongkong.aliyuncs.com/coin-ae.png
    String fullRelative = "https://" + bucketName +".oss-cn-hongkong.aliyuncs.com/" + baseFilePath + "/" + filePath;

    UploadResult ret = new UploadResult();
    ret.setDfsFilePath(path);
    ret.setFileName(fileName);
    ret.setFullRelative(fullRelative);
    ret.setFileSize(fileSize);

    logger.error("upload file to server path success: " + fullRelative);

    return ret;
  }

  /**
   * 批量上传图片
   *
   * @return
   * @throws IOException
   */
  public static List<UploadResult> ossUploadMultImages(MultipartFile[] multipartFiles) throws IOException {

    List<UploadResult> listResult = new ArrayList<UploadResult>();
    for (MultipartFile multipartFile : multipartFiles) {
      if (multipartFile == null || multipartFile.getSize() == 0) {
        throw new IOException("上传文件不存在");
      }

      // 原始文件名
      String fileName = multipartFile.getOriginalFilename();
      if (StringUtils.isNullOrEmpty(fileName)) {
        throw new IOException("非法文件");
      }

      // 文件大小
      long fileSize = multipartFile.getSize();
      if (multipartFile.getSize() > Integer.parseInt(fileMaxSize) * 1024) {
        throw new IOException("上传文件不能超过  " + fileMaxSize + " K");
      }

      String fileExt = "";
      int suffixPos = fileName.lastIndexOf(".");
      if (suffixPos > 0) {
        fileExt = fileName.substring(suffixPos + 1);
      }

      // 格式判断
      if (!imgFileExtensiom.contains(fileExt.toLowerCase())) {
        throw new IOException("非法文件格式" + fileExt.toLowerCase());
      }

      // 判断日期得到文件名
      String baseFilePath = sdf.format(new Date());
      String filePath =  UUID.randomUUID().toString().replace("-", "") + "." + fileExt;
      if (StringUtils.isNullOrEmpty(fileName) || StringUtils.isNullOrEmpty(baseFilePath)) {
        throw new IOException("非法文件路径 " + baseFilePath + "/" +filePath );
      }

      String path = baseFilePath + "/" +filePath;
      ossUpload(path,multipartFile);


      // 网络访问路径https://ex-file.oss-cn-hongkong.aliyuncs.com/coin-ae.png
      String fullRelative = "https://" + bucketName +".oss-cn-hongkong.aliyuncs.com/" + baseFilePath + "/" + filePath;

      UploadResult ret = new UploadResult();
      ret.setDfsFilePath(path);
      ret.setFileName(fileName);
      ret.setFullRelative(fullRelative);
      ret.setFileSize(fileSize);
      listResult.add(ret);
    }

    return listResult;
  }

  /**
   * 创建oss实例进行上传
   * @param path
   * @param multipartFile
   */
  private static void ossUpload(String path, MultipartFile multipartFile){
    //创建oss实例
    OSSClient ossClient = new OSSClient(END_POINT, accessKeyId, accessKeySecret);
    if(ossClient == null){
      throw new OSSException("创建oss实例异常 ");
    }

    try {

      if (ossClient.doesBucketExist(bucketName)) {
        System.out.println("您已经创建Bucket：" + bucketName + "。");
      } else {
        System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
        ossClient.createBucket(bucketName);
      }

      // 判断Bucket是否存在，存在则上传文件
      BucketInfo bucketInfo = ossClient.getBucketInfo(bucketName);
      if(bucketInfo != null){
        // 上传文件
        ossClient.putObject(bucketName, path , new ByteArrayInputStream(multipartFile.getBytes()));
      }

    } catch (OSSException oe) {
      oe.printStackTrace();
    } catch (ClientException ce) {
      ce.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ossClient.shutdown();
    }
  }

  /**
   *
   * @param storagePath  文件的全部路径 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
   * @return -1失败,0成功
   * @throws IOException
   * @throws Exception
   */
  public static Integer ossDelete_file(String storagePath) throws IOException{
    //创建oss实例
    OSSClient ossClient = new OSSClient(END_POINT, accessKeyId, accessKeySecret);
    if(ossClient == null){
      throw new OSSException("创建oss实例异常 ");
    }

    try {
      // 判断Bucket是否存在，存在则上传文件
      BucketInfo bucketInfo = ossClient.getBucketInfo(bucketName);
      if(bucketInfo != null){
        ossClient.deleteObject(bucketName, storagePath);
        return 1;
      }
    } catch (OSSException oe) {
      oe.printStackTrace();
    } catch (ClientException ce) {
      ce.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ossClient.shutdown();
    }
    return -1;
  }

  public static void main(String[] args) {

    // 创建OSSClient实例。
    OSSClient ossClient = new OSSClient(END_POINT, accessKeyId, accessKeySecret);

    // 上传文件。
    String content = "Hello OSS";
    String objectName = "test/999";
    ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));

    // 关闭OSSClient。
    ossClient.shutdown();
  }

  public static class UploadResult {

    // dfs 文件路径
    private String dfsFilePath;

    // 网络访问路径
    private String fullRelative;

    // 原始文件名
    private String fileName;

    // 文件大小
    private long   fileSize;

    public String getDfsFilePath() {
      return dfsFilePath;
    }

    public void setDfsFilePath(String dfsFilePath) {
      this.dfsFilePath = dfsFilePath;
    }

    public String getFileName() {
      return fileName;
    }

    public void setFileName(String fileName) {
      this.fileName = fileName;
    }

    public long getFileSize() {
      return fileSize;
    }

    public void setFileSize(long fileSize) {
      this.fileSize = fileSize;
    }

    public String getFullRelative() {
      return fullRelative;
    }

    public void setFullRelative(String fullRelative) {
      this.fullRelative = fullRelative;
    }

  }
}

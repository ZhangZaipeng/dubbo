package com.example.common.utils;

import com.example.common.crypt.Base64;
import com.example.common.crypt.MD5Utils;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Describe
 * <p>
 * User: yangguang Date: 2017/12/26 Time: 上午10:22
 */
public class ThressDescUtils {

  private static Logger logger = LoggerFactory.getLogger(ThressDescUtils.class);

  // 加解密统一使用的编码方式
  private final static String encoding = "utf-8";

  private final static String key = "1q2w3e4r5t6y7u8i9o0p!@#$%^";
  private final static String iv = "civ12vic";

  /**
   * 3DES加密
   *
   * @param plainText 普通文本
   */
  public static String encode(String plainText, String iv, String secretKey) {
    byte[] encryptData;
    try {
      DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
      SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
      Key deskey = keyfactory.generateSecret(spec);
      Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
      IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
      encryptData = cipher.doFinal(plainText.getBytes(encoding));
      return Base64.encode(encryptData);
    } catch (Exception e) {
      logger.info("des encode error", e);
      return "";
    }
  }

  /**
   * 3DES解密
   *
   * @param encryptText 加密文本
   */
  public static String decode(String encryptText, String iv, String secretKey) {
    try {
      DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
      SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
      Key deskey = keyfactory.generateSecret(spec);
      Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
      IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
      byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
      return new String(decryptData, encoding);
    } catch (Exception e) {
      logger.info("des decode error encryptText:{};iv:{};secretKey:{}", e, encryptText, iv,
          secretKey);
      return "";
    }
  }


  public static String decodeHotAddress(String hotAddress) {
    return decode(hotAddress, iv, key);
  }

  public static String decodeHotPwd(String hotPwd) {
    return decode(hotPwd, iv, key);
  }

  public static String encodeHotPwd(String hotPwd) {
    return encode(hotPwd, iv, key);
  }

  public static String encodeHotAddress(String hotAddress) {
    return encode(hotAddress, iv, key);
  }

  public static String encodeClodAddress(String clodAddress) {
    return encode(clodAddress, iv, key);
  }

  public static String decodeClodAddress(String clodAddress) {
    return decode(clodAddress, iv, key);
  }

  public static String encodeAssetAddress(String address) {
    String md5 = MD5Utils.MD5("1q2w3e4r$1");
    return encode(address, iv, Base64.encode(md5.getBytes()));
  }


  public static String decodeAssetAddress(String address) {
    String md5 = MD5Utils.MD5("1q2w3e4r$1");
    return decode(address, iv, Base64.encode(md5.getBytes()));
  }

  public static String encodeAssetPwd(String pwd, String iv) {
    String md5 = MD5Utils.MD5(iv);
    return encode(pwd, iv, Base64.encode(md5.getBytes()));
  }


  public static String decodeAssetPwd(String pwd, String iv) {
    String md5 = MD5Utils.MD5(iv);
    return decode(pwd, iv, Base64.encode(md5.getBytes()));
  }

  public static void main(String[] args) {

    String encode = decodeAssetAddress("+eBNdg6ZDOQDYSrBVN0k4oCROLx31Hj8wmeEPRK5wQfRRakQ==+eBNdg6ZDOQDYSrBVN0k4oCROLx31Hj8wmeEPRK5wQfRRakQ==");
    System.out.println(encode);

    String decode = ThressDescUtils.encodeAssetAddress("0x184a563e091d5027b887df6a8b523e601e51f13e");
    System.out.println(decode);

  }

}

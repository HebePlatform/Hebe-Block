package hebe.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Base64算法
 *
 * @version 2008-6-18 11:48:08
 */
public class Base64
{
	/**
	 * 图片base64
	 * @param imgurl
	 * @return
	 */
	public static String GetImageStr(String imgurl) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = imgurl;// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encode(data);// 返回Base64编码过的字节数组字符串
	}
	
    /**
     * Base64编码加密
     *
     * @param data 待加密字节数组
     * @return 加密后字符串
     */
    public static String encode(byte[] data)
    {
        if (data == null) return null;
        return new String(org.bouncycastle.util.encoders.Base64.encode(data));
    }

    /**
     * Base64编码解密
     *
     * @param data 待解密字符串
     * @return 解密后字节数组
     * @throws Exception 异常
     */
    public static byte[] decode(String data) throws Exception
    {
        if (data == null) return null;
        try
        {
            return org.bouncycastle.util.encoders.Base64.decode(data.getBytes());
        } catch (RuntimeException e)
        {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println(Base64.encode("a和法国和a".getBytes()));
        System.out.println(new String(Base64.decode("YeWSjOazleWbveWSjGE=")));
    }
}
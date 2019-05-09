package hebe.util;


import java.security.*;

import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


/**
 * 1.先打开生成公钥和私钥 开关，输出公钥和私钥，然后公钥发给对方用来将明文加密，私钥留在自己程序，用来解密。
 * 2.然后关闭生成公钥和私钥的开关，接收传过来的密文，进行解密。
 */


public class RSAUtil {
	private static final Provider provider = new BouncyCastleProvider();
	private static final String alg = "RSA";
	private static final String providerStr = "BC";
	private static final String padding = "RSA/ECB/PKCS1Padding";

	private int keysize = 1024;

	private PrivateKey privateKey; // 私钥
	private PublicKey publicKey; // 公钥

	public RSAUtil() {
		Security.addProvider(provider);
	}

	/**
	 * 生成密钥对
	 * 
	 * @return KeyPair
	 * @throws Exception
	 *             异常
	 */
	public KeyPair genKeyPair() throws Exception {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(alg,
					providerStr);
			kpg.initialize(1024);
			KeyPair keyPair = kpg.generateKeyPair();
			this.setPrivateKey(keyPair.getPrivate());
			this.setPublicKey(keyPair.getPublic());
			return keyPair;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * 加密，支持超长内容
	 * 
	 * @param key
	 *            Key，公钥或者私钥都可，应该使用本类内部生成的
	 * @param in
	 *            被加密数据，支持超长数据
	 * @return 加密后数据
	 * @throws Exception
	 */
	public static byte[] encode(Key key, byte[] in) throws Exception {
		try {
			Cipher cip = Cipher.getInstance(padding, provider);
			cip.init(Cipher.ENCRYPT_MODE, key);
			byte[] encodedByteArray = new byte[] {};
			for (int i = 0; i < in.length; i += 100) {
				byte[] subarray = ArrayUtils.subarray(in, i, i + 100);
				byte[] doFinal = cip.doFinal(subarray);
				encodedByteArray = ArrayUtils.addAll(encodedByteArray, doFinal);
			}

			return encodedByteArray;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 解密，支持超长内容
	 * 
	 * @param key
	 *            Key，公钥或者私钥都可，应该使用本类内部生成的
	 * @param in
	 *            待加密数据，支持超长数据
	 * @return 解密后数据
	 * @throws Exception
	 */
	public static byte[] decode(Key key, byte[] in) throws Exception {
		try {
			Cipher cip = Cipher.getInstance(padding, provider);
			cip.init(Cipher.DECRYPT_MODE, key);

			byte[] decodeByteArray = new byte[] {};
			for (int i = 0; i < in.length; i += 128) {
				byte[] subarray = ArrayUtils.subarray(in, i, i + 128);
				byte[] doFinal = cip.doFinal(subarray);
				decodeByteArray = ArrayUtils.addAll(decodeByteArray, doFinal);
			}

			return decodeByteArray;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = Base64.encode(keyBytes);
		return s;
	}

	/**
	 * 得到密钥字符串（经过hex编码）
	 * 
	 * @return
	 */
	public static String getKeyStringhex(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = new String(
				org.bouncycastle.util.encoders.Hex.encode(keyBytes));
		return s;
	}

	// 私钥解密
	public static String decode(String prikeyStr, String data) throws Exception {
		PrivateKey prikey = getPrivateKey(prikeyStr);
		return new String(decode(prikey, Base64.decode(data)));
	}

	// 公钥加密
	public static String encode(String pubkeyStr, String data) throws Exception {
		PublicKey pubkey = getPublicKey(pubkeyStr);
		return Base64.encode(encode(pubkey, data.getBytes()));
	}
}

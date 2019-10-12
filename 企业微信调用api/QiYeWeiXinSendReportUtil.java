package com.xintuo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import smartbi.net.sf.json.JSONObject;

/**
 * 企业微信推送报表的工具类
 */
public class QiYeWeiXinSendReportUtil {
	private static final Logger LOG = Logger.getLogger(QiYeWeiXinSendReportUtil.class);

	private QiYeWeiXinSendReportUtil() {
	}

	/**
	 * 获取access_token
	 * @param corpid 
	 * 				企业微信的企业ID
	 * @param corpsecret 
	 * 				应用管理中的应用的Secret
	 * @return
	 */
	public static String getAccessToken(String corpid, String corpsecret) {
		String accessToken = "";
		try {
			// 构造url路径
			String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret;
			// 创建一个url
			URL u = new URL(url);
			// 打开一个连接
			HttpsURLConnection hConn = (HttpsURLConnection) u.openConnection();
			// 创建一个接收输入流的字符流
			BufferedReader br = null;
			// 创建一个StringBuilder
			StringBuilder sb = new StringBuilder();
			// 建立连接
			hConn.connect();
			// 获取数据
			br = new BufferedReader(new InputStreamReader(hConn.getInputStream(), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			if (br != null) {
				br.close();
			}
			// 获取返回的json数据中的access_token
			JSONObject jsonObject = new JSONObject();
			jsonObject = JSONObject.fromObject(sb.toString());
			accessToken = jsonObject.getString("access_token");
		} catch (Exception e) {
			LOG.error("获取企业微信access_token失败", e);
		}
		return accessToken;
	}
	
	/**
	 * 获取请求
	 * @param requestUrl	
	 * 					请求的url
	 * @param requestMethod	
	 * 					请求方式
	 * @param outputStr		
	 * 					请求的数据
	 * @return				
	 * 					返回json对象
	 * @throws Exception
	 */
	public static JSONObject getResult(String requestUrl, String requestMethod, String outputStr) throws Exception {
		return request(requestUrl, requestMethod, outputStr);
	}
	
	/**
	 * 请求的具体实现
	 * @param requestUrl	
	 * 					请求的url
	 * @param requestMethod	
	 * 					请求方式
	 * @param outputStr		
	 * 					请求的数据
	 * @return				
	 * 					返回json对象
	 * @throws Exception
	 */
	private static JSONObject request(String requestUrl, String requestMethod, String outputStr) throws Exception {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象
			SSLContext sc = SSLContext.getInstance("SSL");
			// 初始化,并指定X590TrustedManager作为信任管理器和指定不可预测的安全随机数生成器SecureRandom
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			// 返回该上下文的 SocketFactory对象 
			SSLSocketFactory socketFactory = sc.getSocketFactory();
			// 创建主机名验证的基接口
			TrustAnyHostnameVerifier hostnameVerifier = new TrustAnyHostnameVerifier();
			// 创建一个url
			URL url = new URL(requestUrl);
			// 打开一个连接
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			// 设置当此实例为安全 https URL 连接创建套接字时使用的 SSLSocketFactory
			httpUrlConn.setSSLSocketFactory(socketFactory);
			// 设置url的主机名和服务器的标识不匹配时允许连接
			httpUrlConn.setHostnameVerifier(hostnameVerifier);
			// 使用 URL连接进行输出，则将 DoOutput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 false。 
			httpUrlConn.setDoOutput(true);
			// 使用 URL 连接进行输入，则将 DoInput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 true。 
			httpUrlConn.setDoInput(true);
			// 连接中的 UseCaches 标志为 true，则允许连接使用任何可用的缓存。如果为 false，则忽略缓存。默认值来自 DefaultUseCaches，它默认为 true。 
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				// 获取输出流
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 设置编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				// 关闭输出流
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			// 关闭连接
			httpUrlConn.disconnect();
			jsonObject = new JSONObject(buffer.toString());
		} catch (Exception e) {
			LOG.error(e.getMessage() + "QiYeWeiXinSendReportUtil request方法请求失败", e);
		}
		return jsonObject;
	}
	
	/**
	 * TrustAnyTrustManager 
	 * 						信任管理器 管理X509证书，验证远程安全套接字
	 */
	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	/**
	 * HostnameVerifier 
	 * 					主机名验证的基接口 
	 * 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
	 */
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

}

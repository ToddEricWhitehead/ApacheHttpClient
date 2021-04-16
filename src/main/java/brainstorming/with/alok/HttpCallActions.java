package brainstorming.with.alok;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;

//import javax.security.cert.X509Certificate;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
//import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

public class HttpCallActions {
	// https://reqres.in/
	public static String baseURI = "https://reqres.in/";
	private static HttpResponse response;
	
	public static void POST(String endPoint, String jsonBody, CloseableHttpClient httpClient) {
		try {
			URI url = new URIBuilder(baseURI + endPoint).build();
			// post request
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("content-type", "application/json");
			StringEntity stringEntity = new StringEntity(jsonBody);
			httpPost.setEntity(stringEntity);
			// execute the request
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse != null) {
				response = httpResponse;
			}
			
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	
	//Get method
	public static void GET(String endpoint, CloseableHttpClient httpClient) {		
		try {
			URI uri = new URIBuilder(baseURI+endpoint).setParameter("page", "1").build();
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if(httpResponse != null) {
				response = httpResponse;
			}
		}
		catch (Exception e) {
			e.getMessage();
		}
	}
	
	public static HttpResponse getResponse() {
		return response;
	}
	
	public static CloseableHttpClient getDefaultClient() {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		return closeableHttpClient;
	}
	
	public static CloseableHttpClient getSSLCustomClient() {
		HttpClientBuilder clientBuilder = HttpClients.custom();
		clientBuilder.setSSLSocketFactory(getSSLContext());
		CloseableHttpClient client = clientBuilder.build();
		return client;
	}
	
	
	public static CloseableHttpClient getConcurrentClient(int threadPoolCount) {
		// create the pool connection manager
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		// set thr pool size
		connectionManager.setMaxTotal(threadPoolCount);
		
		// Make the client builder
		HttpClientBuilder clientBuilder = HttpClients.custom();
		// set the connection manager
		clientBuilder.setConnectionManager(connectionManager);
		// build the client
		CloseableHttpClient client = clientBuilder.build();
		return client;
	}
	
	public static SSLConnectionSocketFactory getSSLContext() {
		TrustStrategy trustStrategy = new TrustStrategy() {
			
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				
				return false;
			}
		};
		
		HostnameVerifier allVerifier = new NoopHostnameVerifier();
		SSLConnectionSocketFactory connFactory = null;

		try {
			connFactory = new SSLConnectionSocketFactory(
					SSLContextBuilder.create().loadTrustMaterial(trustStrategy).build(), allVerifier);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return connFactory;
	}
}

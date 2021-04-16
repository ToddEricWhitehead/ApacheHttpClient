package brainstorming.with.alok;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public class MultiThreadClientExecution {

	public static void main(String[] args) throws InterruptedException, IOException {
		// number of threads
		int numberOfThread=10;
		// create URL with endPoint
		URI url = URI.create("https://reqres.in/api/users/2");
		CloseableHttpClient httpClient = HttpCallActions.getConcurrentClient(20);
		
		CountDownLatch countDownLatch = new CountDownLatch(numberOfThread);
		try {
			getRequests(httpClient, url.toString(), numberOfThread, countDownLatch);
			countDownLatch.await();			
		} finally {
			httpClient.close();
		}
	}
	
	public static void getRequests(CloseableHttpClient closeableHttpClient, String url, int threadCount, CountDownLatch countDownLatch) {
		try {
			for(int i = 0; i < threadCount; i++) {
				// create request
				HttpGet httpGet = new HttpGet(url);
				HttpGetRequestUsingThread httpGetRequestUsingThread = new HttpGetRequestUsingThread(closeableHttpClient,
						httpGet, i+10, countDownLatch);
				// start the thread
				httpGetRequestUsingThread.start();
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

}

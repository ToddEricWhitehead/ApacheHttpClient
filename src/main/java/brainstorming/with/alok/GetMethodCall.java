package brainstorming.with.alok;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.apache.http.HttpResponse;

public class GetMethodCall {

	public static void main(String[] args) throws URISyntaxException, IOException {
//		String endpoint = "api/users/1";		
		String endpoint = "api/users";		
		HttpCallActions.GET(endpoint, HttpCallActions.getSSLCustomClient());		
		HttpResponse httpResponse = HttpCallActions.getResponse();		
		System.out.println(httpResponse.getStatusLine().getStatusCode());

		Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
		while(scanner.hasNext()) {
			System.out.println(scanner.next());
		}
	}

}

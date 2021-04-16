package brainstorming.with.alok;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpResponse;

public class PostMethodCall {

	public static void main(String[] args) {
		Scanner scanner = null;
		try {
			String endPoint = "api/users";
			String jsonBody = "{\n"+
					" \"name\": \"morpheus\",\n"+
					" \"job\": \"leader\"\n" +
					"}";
			// call the request
			HttpCallActions.POST(endPoint, jsonBody, HttpCallActions.getSSLCustomClient());
			// get response
			HttpResponse response = HttpCallActions.getResponse();
			System.out.println("Status-code => "+response.getStatusLine().getStatusCode());

			// print response
			scanner = new Scanner(response.getEntity().getContent());
			while(scanner.hasNext()) {
				System.out.println(scanner.next());
			}
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

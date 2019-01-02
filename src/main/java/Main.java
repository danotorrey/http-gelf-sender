import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;

import java.util.Random;

public class Main {

    static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 100000; i++) {

            Thread.sleep(new Random().nextInt(400));

            // '{"short_message":"Hello there", "host":"example.org", "facility":"test", "_foo":"bar"}'
            String ipAddress = randomIp();
//            ipAddress = "104.131.72.189";
            DnsRequestPackage content = new DnsRequestPackage(ipAddress, "houstonvoicepiano.com");
            content.setHost(ipAddress);
            content.setShortMessage(ipAddress);
            sendRequest(new Gson().toJson(content));
        }
    }

    private static void sendRequest(String content) throws InterruptedException, java.util.concurrent.ExecutionException {

        content = "{\n" +
                  "  \"version\": \"1.1\",\n" +
                  "  \"host\": \"example.org\",\n" +
                  "  \"short_message\": \"A short message that helps you identify what is going on\",\n" +
                  "  \"full_message\": \"Backtrace here\\n\\nmore stuff\",\n" +
                  "  \"timestamp\": 1385053862.3072,\n" +
                  "  \"level\": 1,\n" +
                  "  \"_user_id\": 9001,\n" +
                  "  \"_some_info\": \"foo\",\n" +
                  "  \"_some_env_var\": \"bar\"\n" +
                  "}";

        ListenableFuture<Response> future = asyncHttpClient.preparePost("http://127.0.0.1:16000/gelf")
                                                           .setBody(content)
                                                           .execute(new AsyncCompletionHandler<Response>() {

                                                               @Override
                                                               public Response onCompleted(Response response) throws Exception {
                                                                   // Do something with the Response
                                                                   // ...
                                                                   return response;
                                                               }

                                                               @Override
                                                               public void onThrowable(Throwable t) {
                                                                   // Something wrong happened.
                                                                   System.out.println(t);
                                                               }
                                                           });




        Response response = future.get();
        System.out.println(response.getStatusCode());
    }

    private static String randomIp() {
        Random r = new Random();
        return r.nextInt(191) + "." + r.nextInt(254) + "." + r.nextInt(254) + "." + r.nextInt(254);
    }
}

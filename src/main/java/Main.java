import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;

import java.util.Random;

public class Main {

    public static final String GRYLOG_INPUT_URI = "http://127.0.0.1:16000/gelf";
    static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 20000; i++) {

            // Random delay to slow sending down a bit.
            Thread.sleep(new Random().nextInt(400));

            String content = "{\n" +
                             "  \"version\": \"1.1\",\n" +
                             "  \"host\": \"example.org\",\n" +
                             "  \"short_message\": \"A short message that helps you identify what is going on\",\n" +
                             "  \"full_message\": \"Backtrace here\\n\\nmore stuff\",\n" +
                             "  \"level\": 1,\n" +
                             "  \"_user_id\": 9001,\n" +
                             "  \"_some_info\": \"foo\",\n" +
                             "  \"_source_address\": \"10.0.0.0\",\n" +
                             "  \"_some_env_var\": \"bar\"\n" +
                             "}";

            sendRequest(content);
        }
    }

    private static void sendRequest(String content) throws InterruptedException, java.util.concurrent.ExecutionException {

        ListenableFuture<Response> future = asyncHttpClient.preparePost(GRYLOG_INPUT_URI)
                                                           .setBody(content)
                                                           .execute(new AsyncCompletionHandler<Response>() {

                                                               @Override
                                                               public Response onCompleted(Response response) throws Exception {
                                                                   // Do nothing with the response
                                                                   return response;
                                                               }

                                                               @Override
                                                               public void onThrowable(Throwable t) {
                                                                   // Something bad happened.
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

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Main {

    private static final String GRYLOG_INPUT_URI = "http://127.0.0.1:16000/gelf";
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 10; i++) {

            // Random delay to slow sending down a bit.
            Thread.sleep(new Random().nextInt(400));
            String content = buildContent();
            sendRequest(content);
        }
    }

    private static String buildContent() {

        JSONObject json = new JSONObject();
        json.put("version", "1.1");
        json.put("host", "example.org");
        json.put("short_message", "A sample message to test TOR and Reverse DNS lookups");
        json.put("full_message", "More stuff here");
        json.put("level", 1);
        json.put("_user_id", 9001);
        json.put("_some_info", "foo");
        json.put("_src_addr", "10.0.0.1");
        json.put("_mac_addr", "ACBCC1212");
        json.put("_dest_addr", "" + randomIp());
        json.put("_user_id", randomInt());
        json.put("_some_env_var", "bar");
        LOG.info("Request JSON {}", json);

        return json.toString();
    }

    private static void sendRequest(String content) throws InterruptedException, java.util.concurrent.ExecutionException {

        ListenableFuture<Response> future = asyncHttpClient.preparePost(GRYLOG_INPUT_URI)
                                                           .setBody(content)
                                                           .execute(new AsyncCompletionHandler<Response>() {

                                                               @Override
                                                               public Response onCompleted(Response response) {
                                                                   // Do nothing with the response
                                                                   return response;
                                                               }

                                                               @Override
                                                               public void onThrowable(Throwable t) {
                                                                   System.out.println("Something bad happened");
                                                               }
                                                           });

        Response response = future.get();
        LOG.info("Response code {}", response.getStatusCode());
    }

    private static String randomIp() {
        Random r = new Random();
        return r.nextInt(191) + "." + r.nextInt(254) + "." + r.nextInt(254) + "." + r.nextInt(254);
    }

    private static int randomInt() {
        Random r = new Random();
        return r.nextInt(1000);
    }
}

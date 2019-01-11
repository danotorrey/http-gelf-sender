
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

public class Main {

    private static final int RANDOM_DELAY_BOUND = 400;
    private static final String GRAYLOG_INPUT_URI = "http://127.0.0.1:16001/gelf";

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 100000; i++) {

            // Random delay to slow sending down a bit.
            Thread.sleep(new Random().nextInt(RANDOM_DELAY_BOUND));

            // Build the JSON string
            String postBody = buildJson();

            // Set the content type, so Graylog knows we're going to send JSON.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Prepare and send the request.
            HttpEntity<String> entity = new HttpEntity<String>(postBody, headers);
            RestTemplate restTemplate = new RestTemplate();

            // Send it.
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(GRAYLOG_INPUT_URI, entity, String.class);

            // Handle response.
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                LOG.info("Success: Response code [{}]", responseEntity.getStatusCodeValue());
            } else {
                LOG.info("Error: response code [{}], reason [{}].",
                         responseEntity.getStatusCodeValue(),
                         responseEntity.getStatusCode().getReasonPhrase());
            }
        }
    }

    private static String buildJson() {

        JSONObject json = new JSONObject();
        json.put("version", "1.1");
        json.put("host", "example.org");
        json.put("short_message", "AA sample message to test TOR and Reverse DNS lookups");
        json.put("full_message", "More stuff here");
        json.put("level", 1);
        json.put("_user_id", 9001);
        json.put("_some_info", "foo");
        json.put("_src_addr", "10.0.0.1");
        json.put("_mac_addr", "ACBCC1212");
        json.put("_dest_addr", "" + randomIp());
        json.put("_user_id", randomInt());

        // LOG.info("Request JSON {}", json);

        return json.toString();
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

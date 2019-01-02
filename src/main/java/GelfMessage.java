import com.google.gson.annotations.SerializedName;

public class GelfMessage {

    @SerializedName("version")
    private String version;

    @SerializedName("host")
    private String host;

    @SerializedName("short_message")
    private String shortMessage;


    public GelfMessage() {
        this.version = "1";
        this.host = "test.com";
        this.shortMessage = "short message";
    }

    public GelfMessage(String version, String host, String shortMessage) {
        this.version = version;
        this.host = host;
        this.shortMessage = shortMessage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }
}

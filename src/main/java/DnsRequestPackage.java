import com.google.gson.annotations.SerializedName;

public class DnsRequestPackage extends GelfMessage {

    @SerializedName("_ip_address")
    private String ipAddress;

    @SerializedName("_host_name")
    private String hostName;

    public DnsRequestPackage(String ipAddress, String hostName) {
        this.ipAddress = ipAddress;
        this.hostName = hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}

import java.util.Objects;

public class PortProtocolPair {
    private final String _port;
    private final String _protocol;

    public PortProtocolPair(String port, String protocolNumber) {
        this._port = port;
        this._protocol = _getProtocolFromNumber(protocolNumber);
    }

    @Override
    public String toString() {
        return this._port + "," + this._protocol;
    }


    private String _getProtocolFromNumber(String protocolNumber) {
        switch (protocolNumber) {
            case "6":
                return "tcp";
            case "17":
                return "udp";
            case "1":
                return "icmp";
            default:
                return "others";
        }
    }

}

import java.util.Objects;

public class PortProtocolPair {
    private final String _port;
    private final String _protocol;

    public PortProtocolPair(String port, String protocolNumber) {
        this._port = port;
        this._protocol = protocolNumber;
    }

    @Override
    public String toString() {
        return this._port + "," + this._protocol;
    }

    public String getPort() {
        return _port;
    }



}
